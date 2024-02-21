package edu.esprit.controllers;
import edu.esprit.entities.Avis;
import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceOeuvre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Date;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
               // handleTableViewDoubleClick();
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
    //private void handleTableViewDoubleClick() {
      //  Avis selectedRec = TableView.getSelectionModel().getSelectedItem();
       // if (selectedRec != null) {
            //openEditDialog(selectedRec);
       // }
    //}


}
