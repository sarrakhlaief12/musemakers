package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.services.ServiceExposition;
import edu.esprit.services.ServicePersonne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.io.IOException;
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
    private TableColumn<Exposition, String> description;

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
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
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
        TextField descriptionField = new TextField(exposition.getDescription());
        TextField themeField = new TextField(exposition.getTheme());
        TextField imageField = new TextField(exposition.getImage());

        GridPane grid = new GridPane();
        grid.add(new Label("Nom:"), 0, 0);
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
        grid.add(imageField, 1, 5);

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

    private void handleTableViewDoubleClick() {
        Exposition selectedRec = TableView.getSelectionModel().getSelectedItem();
        if (selectedRec != null) {
            openEditDialog(selectedRec);
        }
    }


    }



