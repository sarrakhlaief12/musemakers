package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import edu.esprit.services.ServiceExposition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class AfficherExpositionClient {

    private final ServiceExposition exp = new ServiceExposition();
    private Set<Exposition> listexpo = exp.getAll();

    @FXML
    private VBox exhibitionVBox;

    public AfficherExpositionClient() throws SQLException {
    }

    // Méthode appelée lors de l'initialisation de la vue
    @FXML
    public void initialize() {
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
            Label dateLabel = new Label("Date: " + formatDate(expo.getDateDebut()) + " - " + formatDate(expo.getDateFin()));
            Label themeLabel = new Label("Thème: " + expo.getTheme());
            Label descriptionLabel = new Label("Description: " + expo.getDescription());

            // Bouton pour afficher les détails (vous pouvez ajouter une gestion d'événements ici)
            Button detailsButton = new Button("Détails");
            detailsButton.setId("btndetailseance");

            // Ajouter les composants au VBox des détails
            detailsVBox.getChildren().addAll(nameLabel, dateLabel, themeLabel, descriptionLabel, detailsButton);

            // Ajouter les composants à l'HBox principale
            exhibitionBox.getChildren().addAll(imageView, detailsVBox);

            // Ajouter HBox à la VBox principale
            exhibitionVBox.getChildren().add(exhibitionBox);
        }
    }

    // Méthode pour formater la date
    private String formatDate(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
