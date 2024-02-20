package edu.esprit.controllers;

import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceOeuvre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.text.html.ImageView;
import java.sql.Date;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private Button button_modifier;

    private Oeuvre selectedOeuvre;
    @FXML
    private final ServiceOeuvre PS=new ServiceOeuvre();



    @FXML
    void initialize() throws SQLException {


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

}

