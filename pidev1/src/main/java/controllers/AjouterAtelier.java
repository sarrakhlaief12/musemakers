package controllers;

import entities.Atelier;
import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import service.ServiceAtelier;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
public class AjouterAtelier {




        @FXML
        private DatePicker datePickerDebut;

        @FXML
        private DatePicker datePickerFin;

        @FXML
        private TextField lienField;

        private final ServiceAtelier serviceAtelier = new ServiceAtelier();

        @FXML
        void Ajouter(ActionEvent event) {
            try {
                LocalDate dateDebut = datePickerDebut.getValue();
                LocalDate dateFin = datePickerFin.getValue();

                if (dateDebut == null || dateFin == null) {
                    throw new IllegalArgumentException("Veuillez sélectionner une date de début et une date de fin.");
                }

                if (!lienField.getText().isEmpty()) {
                    String lien = lienField.getText();

                    // Vous devez obtenir le cour sélectionné depuis votre interface utilisateur
                    Cour cour = null; // Remplacez null par le cour sélectionné

                    // Créer l'objet Atelier
                    Atelier atelier = new Atelier(cour, dateDebut, dateFin, lien);

                    // Appeler la méthode pour ajouter l'atelier
                    serviceAtelier.ajouter(atelier);

                    // Afficher une confirmation à l'utilisateur
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Atelier ajouté avec succès");
                    alert.showAndWait();
                } else {
                    throw new IllegalArgumentException("Le lien est vide");
                }
            } catch (Exception e) {
                // Gérer les autres exceptions
                afficherAlerteErreur("Erreur", e.getMessage());
            }
        }

        private void afficherAlerteErreur(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }


