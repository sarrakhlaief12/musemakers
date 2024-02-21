package edu.esprit.controllers;
import edu.esprit.entities.Avis;
import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceOeuvre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Date;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public void initialize() {
        // Get the user's reviews
        ServiceAvis serviceAvis = new ServiceAvis();
        ObservableList<Avis> avisList = FXCollections.observableArrayList(serviceAvis.getAvisByUserId(3)); // Replace 4 with the actual user ID

        // Set up the columns in the table
        Note_id.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("note"));
        commentaire_id.setCellValueFactory(new PropertyValueFactory<Avis, String>("commentaire"));
        dateexp_id.setCellValueFactory(new PropertyValueFactory<Avis, Date>("dateExperience"));
        nomoeuvre_id.setCellValueFactory(new PropertyValueFactory<Avis, String>("oeuvre"));

        // Load the data into the table
        TableView.setItems(avisList);
    }


}
