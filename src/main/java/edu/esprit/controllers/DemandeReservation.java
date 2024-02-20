package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceExposition;
import edu.esprit.services.ServicePersonne;
import edu.esprit.services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

public class DemandeReservation {
    @FXML
    private TableColumn<Exposition, Timestamp> dateDebutColumn;

    @FXML
    private TableColumn<Exposition, Timestamp> dateFinColumn;

    @FXML
    private TableColumn<Reservation, Timestamp> dateReservationColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<Reservation, Integer> etatrdv;

    @FXML
    private TableColumn<Exposition, String> nomExpositionColumn;

    @FXML
    private TableView<Reservation> reservationTableView;

    @FXML
    private TableColumn<Exposition, String> themeExpositionColumn;

    @FXML
    private TableColumn<Reservation, Integer> ticketsNumberColumn;

    @FXML
    private TableColumn action;


    private ServiceReservation serviceReservation = new ServiceReservation();
    private ServiceExposition serviceExposition = new ServiceExposition();
    private ServicePersonne servicePersonne = new ServicePersonne();

    Exposition exposition = new Exposition();
    User user = new User();
    @FXML
    private void initialize() {
        // Assuming you have a method to retrieve "En cours" reservations in your service class
        Set<Reservation> enCoursReservations = serviceReservation.getEnCoursReservations();

        // Populate the TableView with "En cours" reservations
        reservationTableView.getItems().addAll(enCoursReservations);

        // Set up the columns (you might need to adjust the properties based on your Reservation class)
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        nomExpositionColumn.setCellValueFactory(new PropertyValueFactory<>("expositionNom"));
        themeExpositionColumn.setCellValueFactory(new PropertyValueFactory<>("expositionTheme"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("ExpositionDateD"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("ExpositionDateF"));
        ticketsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketsNumber"));
        dateReservationColumn.setCellValueFactory(new PropertyValueFactory<>("dateReser"));
//        etatrdv.setCellValueFactory(new PropertyValueFactory<>("accessByAdmin"));
        action.setCellFactory(column -> new TableCell<Reservation, Void>() {
            private final Button checkButton = new Button("Check");
            private final Button xButton = new Button("X");

            {
                checkButton.setOnAction(event -> {
                    Reservation reservation = getTableRow().getItem();
                    if (reservation != null && showConfirmationDialog("accepter cette reservation")) {
                        serviceReservation.acceptReservation(reservation.getIdReservation());
                        refreshTable();
                    }
                });

                xButton.setOnAction(event -> {
                    Reservation reservation = getTableRow().getItem();
                    if (reservation != null && showConfirmationDialog("Annuler cette Reservation")) {
                        serviceReservation.refuserReservation(reservation.getIdReservation());
                        refreshTable();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, checkButton, xButton));
                }
            }
        });
    }

    // Method to show a confirmation dialog
    private boolean showConfirmationDialog(String action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmer: " + action);
        alert.setContentText("vous voulez" + action.toLowerCase() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // Method to refresh the table
    private void refreshTable() {
        reservationTableView.getItems().clear();
        Set<Reservation> enCoursReservations = serviceReservation.getEnCoursReservations();
        reservationTableView.getItems().addAll(enCoursReservations);
    }






    }


