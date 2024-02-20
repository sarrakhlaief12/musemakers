package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.entities.Reservation;
import edu.esprit.services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.util.Set;

public class HistoriqueReservation {
    @FXML
    private TableColumn<Exposition, Timestamp> dateDebutColumn;

    @FXML
    private TableColumn<Exposition, Timestamp> dateFinColumn;

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
    private TableColumn<Reservation, Void> action;  // Change TableColumn type to include Void

    private ServiceReservation serviceReservation = new ServiceReservation();
    private ObservableList<Reservation> userReservations = FXCollections.observableArrayList();
    private String getStatusText(Integer accessByAdmin) {
        if (accessByAdmin == null) {
            return null;
        }

        switch (accessByAdmin) {
            case 0:
                return "En Cours";
            case 1:
                return "Accepté";
            case 2:
                return "Refusé";
            case 3:
                return "Annulé";
            default:
                return "Unknown";
        }
    }

    //3amla haja bsh netfakrha ; 0en cours, 1:accepté,2 :refusé par admin, 3 anuuler
    @FXML
    private void initialize() {
        // Assuming you have a method to retrieve reservations for a specific user
        userReservations.addAll(serviceReservation.getReservationsByUser(6));

        // Populate the TableView with user reservations
        reservationTableView.setItems(userReservations);

        // Set up the columns (you might need to adjust the properties based on your Reservation class)
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("expositionDateD"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("expositionDateF"));
        ticketsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketsNumber"));
        nomExpositionColumn.setCellValueFactory(new PropertyValueFactory<>("expositionNom"));
        themeExpositionColumn.setCellValueFactory(new PropertyValueFactory<>("expositionTheme"));
        etatrdv.setCellValueFactory(new PropertyValueFactory<>("accessByAdmin"));

        etatrdv.setCellFactory(column -> new TableCell<Reservation, Integer>() {
            @Override
            protected void updateItem(Integer accessByAdmin, boolean empty) {
                super.updateItem(accessByAdmin, empty);
                if (empty || accessByAdmin == null) {
                    setText("");
                } else {
                    String statusText = getStatusText(accessByAdmin);
                    setText(statusText != null ? statusText : "Unknown");
                }
            }
        });
        // Add the button column
        action.setCellFactory(column -> new TableCell<Reservation, Void>() {
            private final Button xButton = new Button("X");

            {
                xButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    handleXButtonAction(reservation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Reservation reservation = getTableView().getItems().get(getIndex());

                    // Check if accessByAdmin is 2 or 3
                    if (reservation.getAccessByAdmin() == 2 || reservation.getAccessByAdmin() == 3) {
                        xButton.setVisible(false); // Hide the button
                    } else {
                        xButton.setVisible(true); // Show the button
                    }
                    setGraphic(xButton);
                }
            }



        });
    }

    private void handleXButtonAction(Reservation reservation) {
        // Implement the logic to handle the X button action
        // For example, update the reservation status in the database
        serviceReservation.annulerReservation(reservation.getIdReservation());

        // Update the data in the ObservableList directly
        reservation.setAccessByAdmin(3); // Assuming 3 is the status for cancellation
        reservationTableView.refresh();
    }
}