// ReservationController.java
package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePersonne;
import edu.esprit.services.ServiceReservation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class ReservationController {

    @FXML
    private TextField ticketsTextField;

    private Exposition exposition;
    @FXML
    private Label expositionDetailsLabel;
    ServicePersonne servicePersonne = new ServicePersonne();
    ServiceReservation serviceReservation = new ServiceReservation();
    Reservation r=new Reservation();


    public void setExposition(Exposition exposition) {
        this.exposition = exposition;
        // Set the exhibition details in the label when the exposition is set
        expositionDetailsLabel.setText("Exposition: " + exposition.getNom() + " - Date et Heure: " + formatDateTime(exposition.getDateDebut()));
    }

    @FXML
    private void reserveTickets() {
        try {
            // Get the number of tickets entered by the user
            int ticketsNumber = Integer.parseInt(ticketsTextField.getText());

            // Get the user with ID 6 (You can modify this part based on your requirements)
            User user = servicePersonne.getOneById(5);



            // Create a reservation
            Reservation reservation = new Reservation(Timestamp.valueOf(LocalDateTime.now()), ticketsNumber, r.getAccessByAdmin(), exposition, user);

            // Add the reservation to the database
            serviceReservation.ajouter(reservation);

            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réservation effectuée");
            alert.setHeaderText(null);
            alert.setContentText("Votre réservation a été enregistrée. Attendez la confirmation de l'administrateur.");
            alert.showAndWait();

            // Close the dialog
            Stage stage = (Stage) ticketsTextField.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Handle the case where the user enters a non-numeric value for tickets
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un nombre valide de tickets.");
            alert.showAndWait();
        }
    }
    private String formatDateTime(java.util.Date date) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateTimeFormat.format(date);
    }
}
