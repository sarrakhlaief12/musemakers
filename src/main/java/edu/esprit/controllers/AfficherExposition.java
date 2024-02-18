package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.services.ServiceExposition;
import edu.esprit.services.ServicePersonne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    void initialize() {

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

}
