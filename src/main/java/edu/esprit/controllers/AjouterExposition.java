package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.services.ServiceExposition;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class AjouterExposition {
    private final ServiceExposition exp = new ServiceExposition();

    private File selectedFile;
    private String imageUrl;
    @FXML
    public Button buttonaddExpoID;
    @FXML
    public DatePicker datefinId;
    @FXML
    public DatePicker dateDebutId;
    @FXML
    public TextField pathimageid;
    @FXML
    public TextField themeID;
    @FXML
    public TextArea descriptionId;
    @FXML
    public TextField nomExpoId;

    @FXML
    public Button browseButton;
    public void addExpo(ActionEvent event) throws IOException, SQLException {
//
        LocalDate startDate = dateDebutId.getValue();
        LocalDate endDate = datefinId.getValue();

        Timestamp sqlStartDate = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp sqlEndDate = Timestamp.valueOf(endDate.atStartOfDay());

        exp.ajouter(new Exposition(
                nomExpoId.getText(),
                sqlStartDate,
                sqlEndDate,
                descriptionId.getText(),
                themeID.getText(),
                pathimageid.getText()));
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Validation");
//            alert.setContentText("Expo added successfully");
//            alert.showAndWait();
//
//
    }
    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherExposition.fxml"));
        Parent root=loader.load();
//        Scene scene = new Scene(root);
//
//        // Create a new stage (window)
//        Stage stage = new Stage();
//        stage.setTitle("Exhibition List"); // Set a title for the new window
//        stage.setScene(scene);

        // Show the new stage
//        stage.show();
        nomExpoId.getScene().setRoot(root);
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
            pathimageid.setText(selectedFile.getPath());

            // Chargez l'image depuis le fichier sélectionné
            try {
                // Utilisez la classe Paths pour obtenir un chemin de fichier correct
                String imagePath = Paths.get(selectedFile.getPath()).toUri().toString();
                Image image = new Image(imagePath);

                // Affichez l'image dans l'ImageView si nécessaire
                // imageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
                // Gérer l'exception, par exemple, afficher une image par défaut
            }
        }
    }

}



