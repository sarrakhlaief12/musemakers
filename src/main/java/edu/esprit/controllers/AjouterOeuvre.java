package edu.esprit.controllers;

import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceOeuvre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;

import java.io.IOException;

public class AjouterOeuvre {

    private final ServiceOeuvre PS = new ServiceOeuvre();

    private File selectedFile;

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
    private Button browseButton;

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
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/admin/AfficherOeuvre.fxml"));
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
    @FXML
    void browseImage(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            // Enregistrez le chemin du fichier dans le champ de texte pathimageid
            image_id.setText(selectedFile.getPath());

            // Chargez l'image depuis le fichier sélectionné
            try {
                // Utilisez la classe Paths pour obtenir un chemin de fichier correct
                String imagePath = Paths.get(selectedFile.getPath()).toUri().toString();
                Image image = new Image(imagePath);

                // Affichez l'image dans l'ImageView si nécessaire
                // imageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());

            }
        }
    }
}