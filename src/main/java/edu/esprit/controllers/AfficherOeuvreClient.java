package edu.esprit.controllers;


import edu.esprit.entities.Oeuvre;
import edu.esprit.entities.Avis;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
public class AfficherOeuvreClient {
    private final ServiceAvis serviceAvis = new ServiceAvis();
    private final ServicePersonne servicePersonne = new ServicePersonne();
    private final ServiceOeuvre oe = new ServiceOeuvre();
    private Set<Oeuvre> listeo = oe.getAll();



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
        exhibitionVBox.getChildren().clear();

        for (Oeuvre o: listeo) {
            // Créer un HBox pour chaque exposition (to arrange components horizontally)
            HBox exhibitionBox = new HBox(10);
            exhibitionBox.setAlignment(javafx.geometry.Pos.CENTER);

            // ImageView pour l'image de l'exposition
            ImageView imageView = new ImageView();
            try {
                Image image = new Image(new File(o.getImage()).toURI().toString());
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

            // Bouton pour donner un avis a propos l oeuvre
            Button avisButton = new Button("ajouter avis");
            avisButton.setId("buttonavis");
            avisButton.setOnAction(event ->showAvisDialog(o));
            //{
                // Get the source of the event, which is the button
                //Node sourceNode = (Node) event.getSource();

                // Get the current stage using the button's scene
                //Stage currentStage = (Stage) sourceNode.getScene().getWindow();

                // Call the showAvisDialog method with the Oeuvre and currentStage
                //showAvisDialog(o, currentStage);
            //});

            // Bouton pour consulter les avis des autres clients
            Button avisButton1 = new Button("voir Avis");
            avisButton1.setId("buttonavis1");
            avisButton1.setOnAction(event ->  showAvisDialog1(o));

            // Ajouter les composants au VBox des détails
            detailsVBox.getChildren().addAll(nomLabel, categorieLabel, prixLabel,dateCreationLabel, descriptionLabel, avisButton,avisButton1);

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

    private void showAvisDialog(Oeuvre o) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/AjouterAvis.fxml"));

            // Load the FXML after setting the controller
            Parent root = loader.load();

            // Set the artwork to the controller
            AjouterAvis controller = loader.getController();
            controller.setOeuvre(o);

            // Load the image and set it to the controller
            Image image = new Image(new File(o.getImage()).toURI().toString());
            controller.setImage(image);

            // Create a new stage (window) ;
           // Stage stage = new Stage();
           //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.setTitle("donner votre avis");
            //stage.setScene(new Scene(root));
            // Close the current stage (AfficherClientOeuvre)
            //currentStage.close();

           //stage.showAndWait();
            exhibitionVBox.getScene().setRoot(root);


           // displayExhibitions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAvisDialog1(Oeuvre o) {

        // Créez une nouvelle fenêtre (Stage) pour afficher les avis
        Stage stage = new Stage();
        stage.setTitle("Avis sur " + o.getNom());

        stage.setMinWidth(800);

        // Créez une VBox pour afficher les avis
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Espacer les cartes de 10 pixels

        // Récupérez les avis de la base de données
        List<Avis> avisList = serviceAvis.getAvisByOeuvre(o);

        for (Avis avis : avisList) {
            // Créez une VBox pour chaque avis
            VBox avisBox = new VBox();

            // Créez des Labels pour l'utilisateur, le commentaire et la note
            Label userLabel = new Label("Utilisateur: " + avis.getClient());
            Label commentLabel = new Label("Commentaire: " + avis.getCommentaire());
            Label noteLabel = new Label("Note: " + avis.getNote());

            // Ajoutez les Labels à la VBox
            avisBox.getChildren().addAll(userLabel, commentLabel, noteLabel);

            // Ajoutez la VBox à la VBox principale
            vbox.getChildren().add(avisBox);
        }

        // Créez une Scene avec la VBox et ajoutez-la à la Stage
        Scene scene = new Scene(vbox);
        stage.setScene(scene);

        // Affichez la Stage
        stage.show();
    }


}


