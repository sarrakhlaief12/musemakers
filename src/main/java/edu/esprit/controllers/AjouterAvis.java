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


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;

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

    @FXML
    private Button gallerie_id;

    @FXML
    private Text commentaireerreur;

    @FXML
    private Text dateerreur;

    @FXML
    private Text noteerreur;

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
            Integer note = note_id.getValue();

            String erreurnote = (note == null) ? "Veuillez sélectionner une note." : "";

            // Get the comment entered by the user
            String commentaire = comment_id.getText();
            String  erreurCommentaire = (commentaire.isEmpty() || commentaire.length() > 30 || !commentaire.matches("[a-zA-Z0-9,\\-]+")) ? "Le commentaire ne peut pas être vide, ne doit pas dépasser 30 caractères et doit contenir uniquement des lettres, des chiffres, des virgules et des tirets." : "";


            // Get the user with ID 4  (You can modify this part based on your requirements)
            User client = servicePersonne.getOneById(3);

            // Récupérer la date sélectionnée dans le DatePicker
            LocalDate localDate = dateex_id.getValue();
            String erreurDate = (localDate == null) ? "Veuillez sélectionner une date." : "";
            Date date = Date.valueOf(localDate); // Conversion LocalDate en Date

            commentaireerreur.setText(erreurCommentaire);
            commentaireerreur.setFill(Color.RED);

            dateerreur.setText(erreurDate);
            dateerreur.setFill(Color.RED);

            noteerreur.setText(erreurnote);
            noteerreur.setFill(Color.RED);

            if (erreurCommentaire.isEmpty() && erreurDate.isEmpty() && erreurnote.isEmpty()) {

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
            }
            // Close the dialog

            //Stage stage = (Stage) comment_id.getScene().getWindow();
          //  stage.close();
        } catch (NumberFormatException e) {
            // Handle the case where the user enters a non-numeric value for note
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un avis valide.");
            alert.showAndWait();
        }
    }
    private String formatDateTime(java.util.Date date) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateTimeFormat.format(date);
    }

    @FXML
    void Afficherhistoriqueavis(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/client/HistoriqueAvis.fxml"));
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
    @FXML
    void Affichergallerie(javafx.event.ActionEvent event) throws IOException {
       FXMLLoader loader= new FXMLLoader(getClass().getResource("/client/AfficherOeuvreClient.fxml"));
       Parent root=loader.load();

        comment_id.getScene().setRoot(root);
        /*try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOeuvreClient.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source (button) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        //} catch (IOException e) {
            //e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }*/
    }
}
