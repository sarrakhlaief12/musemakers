package edu.esprit.controllers;

import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceOeuvre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.time.LocalDate;

import java.io.IOException;
import java.sql.SQLException;
public class AjouterOeuvre {

    private final ServiceOeuvre PS = new ServiceOeuvre();

    @FXML
    private TextField Nom_id;

    @FXML
    private ChoiceBox<String> categorie_id;

    @FXML
    private DatePicker date_id;

    @FXML
    private TextField description_id;

    @FXML
    private TextField image_id;

    @FXML
    private TextField prix_id;

    @FXML
    private Button button_ajouter;

    @FXML
    void Ajouter(ActionEvent event) {

        try {
            // Récupérer la valeur sélectionnée dans la ChoiceBox
            String categorie = categorie_id.getValue();

            // Récupérer la date sélectionnée dans le DatePicker
            LocalDate localDate = date_id.getValue();
            Date date = Date.valueOf(localDate); // Conversion LocalDate en Date

            // Convertir le prix en float
            float prix = Float.parseFloat(prix_id.getText());

            PS.ajouter(new Oeuvre(Nom_id.getText(), categorie,prix, date, description_id.getText(), image_id.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Oeuvre ajoute avec succes");
            alert.showAndWait();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
            Parent root=loader.load();
            Nom_id.getScene().setRoot(root);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
