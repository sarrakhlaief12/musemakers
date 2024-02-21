package edu.esprit.controllers;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Oeuvre;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServicePersonne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class AjouterAvis {

    @FXML
    private TextArea comment_id;

    @FXML
    private DatePicker dateex_id;

    @FXML
    private ChoiceBox<Integer> note_id;

    @FXML
    private Label details_id;

    @FXML
    private Button button_envoyer;

    @FXML
    private ImageView image_id;
    @FXML
    private Button historique_id;

    private Oeuvre oeuvre;

    ServicePersonne servicePersonne = new ServicePersonne();
    ServiceAvis serviceAvis = new ServiceAvis();
    Avis a=new Avis();
    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
        // Set the image in the ImageView
        //details_id.setText("Exposition: " + oeuvre.getNom() );
    }
    @FXML
    public void initialize() {
        // Ajoutez des éléments à la ChoiceBox dans la méthode initialize
        note_id.getItems().addAll(1,2,3,4,5);

    }


    public void setImage(Image image) {
        image_id.setImage(image);
    }
    @FXML
    private void submitAvis(ActionEvent event) {
        try {
            // Get the note entered by the user
            int note = note_id.getValue();
            // Get the comment entered by the user
            String commentaire = comment_id.getText();


            // Get the user with ID 4  (You can modify this part based on your requirements)
            User client = servicePersonne.getOneById(4);

            // Récupérer la date sélectionnée dans le DatePicker
            LocalDate localDate = dateex_id.getValue();
            Date date = Date.valueOf(localDate); // Conversion LocalDate en Date
            // Create an avis
            Avis avis = new Avis(commentaire, date, note, oeuvre, client);

            // Add the avis to the database
            serviceAvis.ajouter(avis);

            comment_id.clear();
            dateex_id.setValue(null);
            note_id.setValue(null);

            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Avis soumis");
            alert.setHeaderText(null);
            alert.setContentText("Votre avis a été enregistré. Merci pour votre feedback!");
            alert.showAndWait();

            // Close the dialog

            //Stage stage = (Stage) comment_id.getScene().getWindow();
          //  stage.close();
        } catch (NumberFormatException e) {
            // Handle the case where the user enters a non-numeric value for note
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une note valide.");
            alert.showAndWait();
        }
    }
    private String formatDateTime(java.util.Date date) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateTimeFormat.format(date);
    }

    @FXML
    void Afficherhistoriqueavis(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/HistoriqueAvis.fxml"));
        Parent root=loader.load();
//        Scene scene = new Scene(root);
//
//        // Create a new stage (window)
//        Stage stage = new Stage();
//        stage.setTitle("Exhibition List"); // Set a title for the new window
//        stage.setScene(scene);

        // Show the new stage
//        stage.show();
        comment_id.getScene().setRoot(root);
    }

}
