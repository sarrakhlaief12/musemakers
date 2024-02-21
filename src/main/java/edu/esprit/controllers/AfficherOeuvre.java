package edu.esprit.controllers;

import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceOeuvre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.nio.file.Paths;
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
    private TableColumn<Oeuvre,String> image_id;

    @FXML
    private TableColumn<Oeuvre, String> nom_id;

    @FXML
    private TableColumn<Oeuvre, Float> prix_id;



    private Oeuvre selectedOeuvre;

    @FXML
    private Button nouvelajout_id;
    @FXML
    private final ServiceOeuvre PS=new ServiceOeuvre();

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
        description_id.setCellValueFactory(new PropertyValueFactory<>("description"));
        image_id.setCellValueFactory(new PropertyValueFactory<>("image"));
        prix_id.setCellValueFactory(new PropertyValueFactory<>("prix"));

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

        // Create the fields and populate with existing data
        TextField nomField = new TextField(oeuvre.getNom());
        TextField categorieField = new TextField(oeuvre.getCategorie());
        TextField prixField = new TextField(String.valueOf(oeuvre.getPrix()));
        TextField dateCreationField = new TextField(oeuvre.getDateCreation().toString());
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
       // grid.add(new Label("Image:"), 0, 5);
       // grid.add(imageField, 1, 5);
        grid.add(new Label("Image:"), 0, 5);
        grid.add(imageField, 1, 5);
        grid.add(browseButton, 2, 5);


        dialog.getDialogPane().setContent(grid);

        // Convert the result to an Oeuvre when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                oeuvre.setNom(nomField.getText());
                oeuvre.setCategorie(categorieField.getText());
                oeuvre.setPrix(Float.parseFloat(prixField.getText()));
                oeuvre.setDateCreation(Date.valueOf(dateCreationField.getText()));
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
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterOeuvre.fxml"));
        Parent root=loader.load();
//        Scene scene = new Scene(root);
//
//        // Create a new stage (window)
//        Stage stage = new Stage();
//        stage.setTitle("Exhibition List"); // Set a title for the new window
//        stage.setScene(scene);

        // Show the new stage
//        stage.show();
        TableView.getScene().setRoot(root);
    }



}

