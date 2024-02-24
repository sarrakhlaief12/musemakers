package edu.esprit.controllers;

import edu.esprit.entities.Oeuvre;
import edu.esprit.entities.Avis;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceOeuvre;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AfficherOeuvre {

    @FXML
    private TableView<Oeuvre> TableView;

    @FXML
    private TableColumn<Oeuvre, String> categorie_id;

    @FXML
    private TableColumn<Oeuvre, Date> datecreation_id;

    @FXML
    private TableColumn<Oeuvre,String> description_id;

    @FXML
    private ImageView image_id;


    @FXML
    private TableColumn<Oeuvre, String> nom_id;

    @FXML
    private TableColumn<Oeuvre, Float> prix_id;

    @FXML
    private Button button_avis;

    private Oeuvre selectedOeuvre;

    @FXML
    private Button nouvelajout_id;
    @FXML
    private final ServiceOeuvre PS=new ServiceOeuvre();

    @FXML
    private final ServiceAvis serviceAvis=new ServiceAvis();
    private File selectedFile;


    @FXML
    void initialize()  {

        TableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                handleTableViewDoubleClick();
            }
        });

        List<Oeuvre> Ps= new ArrayList<>(PS.getAll());
        ObservableList<Oeuvre> observableList = FXCollections.observableList(Ps);
        TableView.setItems(observableList);

        nom_id.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie_id.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        datecreation_id.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        //description_id.setCellValueFactory(new PropertyValueFactory<>("description"));
       // image_id.setCellValueFactory(new PropertyValueFactory<>("image"));
        prix_id.setCellValueFactory(new PropertyValueFactory<>("prix"));

        button_avis.setOnAction(event -> {
            Oeuvre selectedOeuvre = TableView.getSelectionModel().getSelectedItem();
            if (selectedOeuvre != null) {
                showAvisDialog(selectedOeuvre);
            }
        });

        // Créez un Tooltip pour afficher l'image de l'oeuvre
        Tooltip tooltip = new Tooltip();

        ImageView imageView = new ImageView();
        imageView.setFitHeight(100); // Ajustez la taille de l'image comme vous le souhaitez
        imageView.setFitWidth(100);


        tooltip.setGraphic(imageView);

        // Configurez la factory de ligne pour afficher l'image de l'oeuvre dans l'Tooltip
        TableView.setRowFactory(tv -> {
            TableRow<Oeuvre> row = new TableRow<>();

            row.setOnMouseEntered(event -> {
                if (!row.isEmpty()) {
                    Oeuvre oeuvre = row.getItem();
                    if (oeuvre != null) {
                        String imageURL = oeuvre.getImage();
                        if (imageURL != null && !imageURL.isEmpty()) {
                            Image image = new Image(new File(imageURL).toURI().toString());
                            imageView.setImage(image);
                            image_id.setImage(image);
                        }
                    }
                }
            });

            row.setOnMouseExited(event -> {
                // Masquez le Tooltip lorsque la souris quitte la ligne
                imageView.setImage(null);
                image_id.setImage(null);
            });

            return row;
        });
    }
    @FXML
    private void delete(ActionEvent event) throws SQLException {
        Oeuvre selectedRec = (Oeuvre) TableView.getSelectionModel().getSelectedItem();
        if (selectedRec != null) {
            // Delete from database
            PS.supprimer(selectedRec.getId());

            // Refresh table view
            initialize();
        }
    }

    private void openEditDialog(Oeuvre oeuvre) {

        System.out.println("Row double-clicked");
        Dialog<Oeuvre> dialog = new Dialog<>();
        dialog.setTitle("Modifier Oeuvre");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // champ categorie
        ChoiceBox<String> categorieField = new ChoiceBox<>();
        categorieField.getItems().addAll("Peinture", "Sculpture", "Scene");
        // Sélectionner la valeur actuelle
        categorieField.setValue(oeuvre.getCategorie());

        //champ date
        DatePicker dateCreationField = new DatePicker();
        dateCreationField.setValue(oeuvre.getDateCreation().toLocalDate());


        // Create the fields and populate with existing data
        TextField nomField = new TextField(oeuvre.getNom());
        //TextField categorieField = new TextField(oeuvre.getCategorie());
        TextField prixField = new TextField(String.valueOf(oeuvre.getPrix()));
        // TextField dateCreationField = new TextField(oeuvre.getDateCreation().toString());
        TextField descriptionField = new TextField(oeuvre.getDescription());
        TextField imageField = new TextField(oeuvre.getImage());
        Button browseButton = new Button("Parcourir");


        // Layout for dialog
        GridPane grid = new GridPane();
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Catégorie:"), 0, 1);
        grid.add(categorieField, 1, 1);
        grid.add(new Label("Prix:"), 0, 2);
        grid.add(prixField, 1, 2);
        grid.add(new Label("Date de Création:"), 0, 3);
        grid.add(dateCreationField, 1, 3);
        grid.add(new Label("Description:"), 0, 4);
        grid.add(descriptionField, 1, 4);
        grid.add(new Label("Image:"), 0, 5);
        grid.add(imageField, 1, 5);
        grid.add(browseButton, 2, 5);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to an Oeuvre when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                oeuvre.setNom(nomField.getText());
                //oeuvre.setCategorie(categorieField.getText());
                oeuvre.setCategorie(categorieField.getValue());
                oeuvre.setPrix(Float.parseFloat(prixField.getText()));
                // oeuvre.setDateCreation(Date.valueOf(dateCreationField.getText()));
                oeuvre.setDateCreation(Date.valueOf(dateCreationField.getValue())); // on a utuliser getValue pour recuperer la date
                oeuvre.setDescription(descriptionField.getText());
                oeuvre.setImage(imageField.getText());
                return oeuvre;
            }
            return null;
        });
        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File selectedFile = fileChooser.showOpenDialog(dialog.getDialogPane().getScene().getWindow());
            if (selectedFile != null) {
                imageField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Show dialog and get result
        Optional<Oeuvre> result = dialog.showAndWait();

        result.ifPresent(updatedOeuvre -> {
            // Update the database
            PS.modifier(updatedOeuvre);

            // Refresh table view
            TableView.getItems().clear();
            TableView.getItems().addAll(PS.getAll());
        });
    }



    private void handleTableViewDoubleClick() {
        Oeuvre selectedRec = TableView.getSelectionModel().getSelectedItem();
        if (selectedRec != null) {
            openEditDialog(selectedRec);
        }
    }

    @FXML
    void AfficherAjoutOeuvre(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/admin/AjouterOeuvre.fxml"));
        Parent root=loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setTitle("Exhibition List"); // Set a title for the new window
//        stage.setScene(scene);
//        stage.show();
        TableView.getScene().setRoot(root);
    }

    private void showAvisDialog(Oeuvre oeuvre) {
        // Créez une nouvelle fenêtre (Stage) pour afficher les avis
        Stage stage = new Stage();
        stage.setTitle("Avis sur " + oeuvre.getNom());

        stage.setMinWidth(800);

        // Créez une TableView pour afficher les avis
        TableView<Avis> tableView = new TableView<>();

        // Créez des colonnes pour la TableView
        TableColumn<Avis, String> userColumn = new TableColumn<>("Utilisateur");
        userColumn.setCellValueFactory(new PropertyValueFactory<>("client"));

        TableColumn<Avis, String> commentColumn = new TableColumn<>("Commentaire");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

        TableColumn<Avis, Integer> noteColumn = new TableColumn<>("Note");
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Ajoutez les colonnes à la TableView
        tableView.getColumns().add(userColumn);
        tableView.getColumns().add(commentColumn);
        tableView.getColumns().add(noteColumn);

        // Récupérez les avis de la base de données
        List<Avis> avisList = serviceAvis.getAvisByOeuvre(oeuvre);

        // Ajoutez les avis à la TableView
        tableView.getItems().addAll(avisList);

        // Créez une Scene avec la TableView et ajoutez-la à la Stage
        Scene scene = new Scene(tableView);
        stage.setScene(scene);

        // Affichez la Stage
        stage.show();
    }
}

