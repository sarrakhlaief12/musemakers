package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.services.ServiceExposition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AfficherExposition {
    @FXML
    private TableView<Exposition>  TableView;

    @FXML
    private TableColumn<Exposition, Timestamp> date_debut;

    @FXML
    private TableColumn<Exposition, Timestamp> date_fin;
    @FXML
    private TableColumn<Exposition, String> imgDisplay;



    @FXML
    private TableColumn<Exposition, String> img;

    @FXML
    private TableColumn<Exposition, String> nom_expo;

    @FXML
    private TableColumn<Exposition, String> theme;
    @FXML
    private ServiceExposition expo=new ServiceExposition();
    @FXML
    private Exposition e=new Exposition();


    @FXML
    void initialize() {
        TableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                handleTableViewDoubleClick();
            }
        });
        try {
            List<Exposition> ex= new ArrayList<>(expo.getAll());
            ObservableList<Exposition> observableList = FXCollections.observableList(ex);
            TableView.setItems(observableList);
        }
        catch (SQLException e){}

        nom_expo.setCellValueFactory(new PropertyValueFactory<>("nom"));
        date_debut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        theme.setCellValueFactory(new PropertyValueFactory<>("theme"));
        img.setCellValueFactory(new PropertyValueFactory<>("image"));

    }


    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        Exposition selectedRec = (Exposition) TableView.getSelectionModel().getSelectedItem();
        if (selectedRec != null) {
            // Delete from database
            expo.supprimer(selectedRec.getId());

            // Refresh table view
            initialize();
        }
    }
//    @FXML
//    private void modifier(ActionEvent event) throws SQLException {
//        Exposition selectedExpo = (Exposition) TableView.getSelectionModel().getSelectedItem();
//        if (selectedExpo != null) {
//            // Get the new values from the user. You can use a form or a dialog for this.
//            String newNom = selectedExpo.getNom(); // replace with code to get new nom
//            Timestamp newDateDebut = selectedExpo.getDateDebut(); // replace with code to get new date debut
//            Timestamp newDateFin = selectedExpo.getDateFin(); // replace with code to get new date fin
//            String newDescription = selectedExpo.getDescription(); // replace with code to get new description
//            String newTheme = selectedExpo.getTheme(); // replace with code to get new theme
//            String newImage = selectedExpo.getImage(); // replace with code to get new image path
//
//            // Create a new Exposition object with the new values
//            Exposition newExpo = new Exposition(newNom, newDateDebut, newDateFin, newDescription, newTheme, newImage);
//
//            // Update the database
//            expo.modifier(newExpo);
//
//            // Refresh table view
//            initialize();
//        }

    private void openEditDialog(Exposition exposition) {
        Dialog<Exposition> dialog = new Dialog<>();
        dialog.setTitle("Modifier Exposition");
        dialog.setHeaderText(null);

        // Customize the controls in the dialog for editing
        TextField nomField = new TextField(exposition.getNom());
        TextField dateDebutField = new TextField(exposition.getDateDebut().toString());
        TextField dateFinField = new TextField(exposition.getDateFin().toString());
        TextArea descriptionField = new TextArea(exposition.getDescription());
        TextField themeField = new TextField(exposition.getTheme());
        TextField imageField = new TextField(exposition.getImage());

        GridPane grid = new GridPane();
        grid.add(new Label("Nom de l'exposition:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Date Début:"), 0, 1);
        grid.add(dateDebutField, 1, 1);
        grid.add(new Label("Date Fin:"), 0, 2);
        grid.add(dateFinField, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionField, 1, 3);
        grid.add(new Label("Thème:"), 0, 4);
        grid.add(themeField, 1, 4);
        grid.add(new Label("Image:"), 0, 5);

        HBox imageBox = new HBox();
        imageBox.getChildren().addAll(imageField, createBrowseButton(imageField));
        grid.add(imageBox, 1, 5);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == buttonTypeOk) {
                // Retrieve updated data from the controls
                String newNom = nomField.getText();
                Timestamp newDateDebut = Timestamp.valueOf(dateDebutField.getText());
                Timestamp newDateFin = Timestamp.valueOf(dateFinField.getText());
                String newDescription = descriptionField.getText();
                String newTheme = themeField.getText();
                String newImage = imageField.getText();

                Exposition updatedExpo = new Exposition(newNom, newDateDebut, newDateFin, newDescription, newTheme, newImage);

                try {
                    // Update the existing Exposition object with the new values
                    exposition.setNom(newNom);
                    exposition.setDateDebut(newDateDebut);
                    exposition.setDateFin(newDateFin);
                    exposition.setDescription(newDescription);
                    exposition.setTheme(newTheme);
                    exposition.setImage(newImage);

                    // Update the database
                    expo.modifier(exposition);

                    // Refresh the table view
                    int index = TableView.getItems().indexOf(exposition);
                    TableView.getItems().set(index, exposition);
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private Button createBrowseButton(TextField imageField) {
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(event -> browseImage(imageField));
        return browseButton;
    }

    private void browseImage(TextField imageField) {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            // Enregistrez le chemin du fichier dans le champ de texte pathimageid
            imageField.setText(selectedFile.getPath());

            // Chargez l'image depuis le fichier sélectionné
            try {
                // Utilisez la classe Paths pour obtenir un chemin de fichier correct
                String imagePath = Paths.get(selectedFile.getPath()).toUri().toString();
                Image image = new Image(imagePath);

                // Affichez l'image dans l'ImageView si nécessaire
                // imageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
                // Gérer l'exception, par exemple, afficher une image par défaut
            }
        }
    }


    private void handleTableViewDoubleClick() {
        Exposition selectedRec = TableView.getSelectionModel().getSelectedItem();
        if (selectedRec != null) {
            openEditDialog(selectedRec);
        }
    }

    @FXML
    void Afficher(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/afficherExpo.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source (button) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    @FXML
    void ajouterNav(ActionEvent event) throws IOException {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/ajouterexpo.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source (button) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
@FXML
    void demandeNav(ActionEvent event) throws IOException {
    try {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/demandeReser.fxml"));
        Parent root = loader.load();

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the stage from the event source (button) and set the new scene
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        // Show the stage
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        // Handle the exception (e.g., show an error message)
    }

    }
    @FXML
    void histoAdminNav(ActionEvent event) throws IOException {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/listeReser.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source (button) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    }





