package edu.esprit.controllers;

import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceOeuvre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
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
    private Button button_afficher;



    @FXML
    public void initialize() {
        // Ajoutez des éléments à la ChoiceBox dans la méthode initialize
        categorie_id.getItems().addAll("Peinture", "Sculpture", "scene");

    }

    @FXML
    public void add(ActionEvent event){

// Récupérer la valeur sélectionnée dans la ChoiceBox
            String categorie = categorie_id.getValue();

            // Récupérer la date sélectionnée dans le DatePicker
            LocalDate localDate = date_id.getValue();
            Date date = Date.valueOf(localDate); // Conversion LocalDate en Date

            // Convertir le prix en float
            float prix = Float.parseFloat(prix_id.getText());

        System.out.println("Ajout de l'oeuvre : ");
            PS.ajouter(new Oeuvre(Nom_id.getText(), categorie, prix, date, description_id.getText(), image_id.getText()));



    }
    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherOeuvre.fxml"));
        Parent root=loader.load();
//        Scene scene = new Scene(root);
//
//        // Create a new stage (window)
//        Stage stage = new Stage();
//        stage.setTitle("Exhibition List"); // Set a title for the new window
//        stage.setScene(scene);

        // Show the new stage
//        stage.show();
        Nom_id.getScene().setRoot(root);
    }
}