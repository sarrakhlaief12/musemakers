package controllers;

import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import service.ServiceCour;

import java.time.LocalDate;


public class AjouterCour {

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
    void Ajouter(ActionEvent event) {
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


}
