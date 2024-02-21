package edu.esprit.controllers;
import edu.esprit.entities.Avis;
import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceOeuvre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.sql.Date;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;



public class HistoriqueAvis {

    @FXML
    private TableColumn<Avis, Integer> Note_id;

    @FXML
    private TableView<Avis> TableView;

    @FXML
    private TableColumn<Avis, String> commentaire_id;

    @FXML
    private TableColumn<Avis, Date> dateexp_id;

    @FXML
    private TableColumn<Avis, String> nomoeuvre_id;

    @FXML
    private Button buttonsupprimer_id;


    ServiceAvis serviceAvis = new ServiceAvis();
    @FXML
    public void initialize() {

        TableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
               handleTableViewDoubleClick();
            }
        });
        // Get the user's reviews

        ObservableList<Avis> avisList = FXCollections.observableArrayList(serviceAvis.getAvisByUserId(4)); // Replace 4 with the actual user ID

        // Set up the columns in the table
        Note_id.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("note"));
        commentaire_id.setCellValueFactory(new PropertyValueFactory<Avis, String>("commentaire"));
        dateexp_id.setCellValueFactory(new PropertyValueFactory<Avis, Date>("dateExperience"));
        nomoeuvre_id.setCellValueFactory(new PropertyValueFactory<Avis, String>("oeuvre"));

        // Load the data into the table
        TableView.setItems(avisList);
    }
    @FXML
    private void delete(ActionEvent event) throws SQLException {
        Avis selectedRec = (Avis) TableView.getSelectionModel().getSelectedItem();
        if (selectedRec != null) {
            // Delete from database
            serviceAvis.supprimer(selectedRec.getIdAvis());

            // Refresh table view
            initialize();
        }
    }

    private void openEditDialog(Avis avis) {

        System.out.println("Row double-clicked");
        Dialog<Avis> dialog = new Dialog<>();
        dialog.setTitle("Modifier votre avis");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the fields and populate with existing data
        TextField commentaireField = new TextField(avis.getCommentaire());
        TextField noteField = new TextField(String.valueOf(avis.getNote()));
        TextField dateexperienceField = new TextField(avis.getDateExperience().toString());



        // Layout for dialog
        GridPane grid = new GridPane();
        grid.add(new Label("Commentaire:"), 0, 0);
        grid.add(commentaireField, 1, 0);
        grid.add(new Label("Note:"), 0, 1);
        grid.add(noteField, 1, 1);
        grid.add(new Label("Date de votre experience:"), 0, 3);
        grid.add(dateexperienceField, 1, 3);




        dialog.getDialogPane().setContent(grid);

        // Convert the result to an Oeuvre when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                avis.setCommentaire(commentaireField.getText());
                avis.setNote(Integer.parseInt(noteField.getText()));
                avis.setDateExperience(Date.valueOf(dateexperienceField.getText()));

                return avis;
            }
            return null;
        });


        // Show dialog and get result
        Optional<Avis> result = dialog.showAndWait();

        result.ifPresent(updatedAvis -> {
            // Update the database
            serviceAvis.modifier(updatedAvis);

            // Refresh table view
            TableView.getItems().clear();
            TableView.getItems().addAll(serviceAvis.getAvisByUserId(4));
        });
    }

    private void handleTableViewDoubleClick() {
        Avis selectedRec = TableView.getSelectionModel().getSelectedItem();
       if (selectedRec != null) {
            openEditDialog(selectedRec);
       }
    }


}
