package controllers;

import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.ServiceCour;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AjouterCourNV1 {



        @FXML
        private DatePicker datePicker;
        @FXML
        private DatePicker datefin;

        @FXML
        private TextField titreField;

        @FXML
        private TextField descriptionField;



        @FXML
        private Text title;

        private final ServiceCour serviceCour = new ServiceCour();

        @FXML
        void Ajouterr (ActionEvent event) {
            try {
                LocalDate dateDebut = datePicker.getValue();
                LocalDate dateFin = datefin.getValue();
                if (dateDebut == null) {
                    throw new IllegalArgumentException("Veuillez sélectionner une date de début.");
                }

                if (!titreField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
                    String titre = titreField.getText();
                    String description = descriptionField.getText();

                    // Créer l'objet Cour
                    Cour cour = new Cour(titre, description, dateDebut, dateFin, null);

                    System.out.printf(cour.toString());
                    // Appeler la méthode pour ajouter le cours
                    serviceCour.ajouter(cour);

                    // Afficher une confirmation à l'utilisateur
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Cour ajouté avec succès");
                    alert.showAndWait();
                } else {
                    throw new IllegalArgumentException("Le titre ou la description est vide");
                }
            } catch (Exception e) {
                // Gérer les autres exceptions
                afficherAlerteErreur("Erreurrrr", e.getMessage());
            }
        }

        private void afficherAlerteErreur(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }




        public void afficheriid(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherCourNv.fxml")));
            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Configurer la nouvelle scène dans une nouvelle fenêtre
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Afficher les cours");

            // Afficher la nouvelle fenêtre
            stage.show();
        }

    }





