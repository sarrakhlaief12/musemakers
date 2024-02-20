package edu.esprit.controllers;


import edu.esprit.entities.Oeuvre;
import edu.esprit.services.ServiceOeuvre;
import edu.esprit.services.ServicePersonne;
import edu.esprit.services.ServiceAvis;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Set;
public class AfficherOeuvreClient {
    private final ServiceAvis serviceReservation = new ServiceAvis();
    private final ServicePersonne servicePersonne = new ServicePersonne();
    private final ServiceOeuvre oe = new ServiceOeuvre();
    private Set<Oeuvre> listeo = oe.getAll();

    private final String cheminImages = "/art/";

    @FXML
    private VBox exhibitionVBox;

    public AfficherOeuvreClient() throws SQLException {
    }

    // Méthode appelée lors de l'initialisation de la vue
    @FXML
    public void initialize() {
        displayExhibitions();
    }

    // Méthode pour afficher toutes les expositions
    private void displayExhibitions() {
        for (Oeuvre o: listeo) {
            // Créer un HBox pour chaque exposition (to arrange components horizontally)
            HBox exhibitionBox = new HBox(10);
            exhibitionBox.setAlignment(javafx.geometry.Pos.CENTER);

            // ImageView pour l'image de l'exposition
            ImageView imageView = new ImageView();
            try {
                String cheminImage = cheminImages + o.getImage(); // Concaténation du chemin d'accès
                Image image = new Image(getClass().getResourceAsStream(cheminImage));
                imageView.setImage(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // Gérer l'exception, par exemple, définir une image par défaut
            }

            // VBox pour les détails de l'oeuvre
            VBox detailsVBox = new VBox(5);
            detailsVBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            // Labels pour les détails de l'oeuvre
            Label nomLabel = new Label("Nom: " + o.getNom());
            Label categorieLabel = new Label("Catégorie: " + o.getCategorie());
            Label prixLabel = new Label("Prix: " + o.getPrix());
            Label dateCreationLabel = new Label("Date de création: " + o.getDateCreation());
            Label descriptionLabel = new Label("Description: " + o.getDescription());

            // Bouton pour donner l avis a propos l oeuvre
            Button avisButton = new Button("ajouter avis");
            avisButton.setId("buttonavis");
            //avisButton.setOnAction(event -> showReservationDialog(expo));

            // Ajouter les composants au VBox des détails
            detailsVBox.getChildren().addAll(nomLabel, categorieLabel, prixLabel,dateCreationLabel, descriptionLabel, avisButton);

            // Ajouter les composants à l'HBox principale
            exhibitionBox.getChildren().addAll(imageView, detailsVBox);

            // Ajouter HBox à la VBox principale
            exhibitionVBox.getChildren().add(exhibitionBox);
        }
    }

    // Méthode pour formater la date
    private String formatDateTime(java.util.Date date) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateTimeFormat.format(date);
    }
}
