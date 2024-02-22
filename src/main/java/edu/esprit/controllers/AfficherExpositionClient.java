package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.services.ServiceExposition;
import edu.esprit.services.ServicePersonne;
import edu.esprit.services.ServiceReservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class AfficherExpositionClient {
    @FXML
    private TextField nameSearchID;

    @FXML
    private TextField themeSearchID;


    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final ServicePersonne servicePersonne = new ServicePersonne();
    private final ServiceExposition exp = new ServiceExposition();

    private Set<Exposition> listexpo = exp.getAll();

    @FXML
    private VBox exhibitionVBox;

    public AfficherExpositionClient() throws SQLException {
    }

    // Méthode appelée lors de l'initialisation de la vue
    @FXML
    public void initialize() {
        // Add a ChangeListener to the nameSearchID TextField
        nameSearchID.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());

        // Add a ChangeListener to the themeSearchID TextField
        themeSearchID.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());

        // Initial display of all exhibitions
        displayExhibitions();
    }


    // Méthode pour afficher toutes les expositions
    private void displayExhibitions() {

        for (Exposition expo : listexpo) {
            // Créer un HBox pour chaque exposition (to arrange components horizontally)
            HBox exhibitionBox = new HBox(10);
            exhibitionBox.setAlignment(javafx.geometry.Pos.CENTER);

            // ImageView pour l'image de l'exposition
            ImageView imageView = new ImageView();
            try {
                Image image = new Image(new File(expo.getImage()).toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // Gérer l'exception, par exemple, définir une image par défaut
            }

            // VBox pour les détails de l'exposition
            VBox detailsVBox = new VBox(5);
            detailsVBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            // Labels pour les détails de l'exposition
            Label nameLabel = new Label("Nom: " + expo.getNom());
//            Label dateLabel = new Label("Date: " + formatDate(expo.getDateDebut()) + " - " + formatDate(expo.getDateFin()));
            Label dateTimeLabel = new Label("Date et Heure: " + formatDateTime(expo.getDateDebut()) + " - " + formatDateTime(expo.getDateFin()));

            Label themeLabel = new Label("Thème: " + expo.getTheme());
            Label descriptionLabel = new Label("Description: " + expo.getDescription());


            // Bouton pour réserver l'exposition
            Button reserveButton = new Button("Réserver");
            reserveButton.setId("btnreserverexposition");
            reserveButton.setOnAction(event -> showReservationDialog(expo));

            // Ajouter les composants au VBox des détails
            detailsVBox.getChildren().addAll(nameLabel, dateTimeLabel, themeLabel, descriptionLabel, reserveButton);

            // Ajouter les composants à l'HBox principale
            exhibitionBox.getChildren().addAll(imageView, detailsVBox);

            // Ajouter HBox à la VBox principale
            exhibitionVBox.getChildren().add(exhibitionBox);
        }
        if (listexpo.isEmpty()) {
            // Aucune exposition trouvée, afficher un message
            Label noResultLabel = new Label("Aucune exposition trouvée.");
            exhibitionVBox.getChildren().add(noResultLabel);
        } else {
            // Afficher les expositions normalement
            for (Exposition expo : listexpo) {
                // ... (Votre code existant pour afficher chaque exposition)
            }
        }
    }

    // Méthode pour formater la date
    private String formatDateTime(java.util.Date date) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateTimeFormat.format(date);
    }
    private void showReservationDialog(Exposition expo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Reservation.fxml"));

            // Load the FXML after setting the controller
            Parent root = loader.load();

            // Set the exposition to the controller
            ReservationController controller = loader.getController();
            controller.setExposition(expo);

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Formulaire de Réservation");

            Label titleLabel = new Label("Réserver des Billets");
            titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Réserver l'exposition");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // You can refresh the exhibition display here if needed
            // displayExhibitions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void histoClientNav(javafx.event.ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/histoReservationClient.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source (button) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    @FXML
    void reserverNav(javafx.event.ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/afficherExpoClient.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source (button) and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    @FXML
    private void handleSearch() {
        String theme = themeSearchID.getText();
        String name = nameSearchID.getText();

        try {
            Set<Exposition> searchResult = exp.chercherParThemeOuNom(theme, name);
            listexpo = searchResult; // Update the listexpo

            // Clear the previous search results from exhibitionVBox
            exhibitionVBox.getChildren().clear();

            // Display the search results in exhibitionVBox
            displayExhibitions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}

