package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AfficherCourFont {


        @FXML
        private void afficherInterface() {
            try {
                // Charger le fichier FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCourFront.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Créer une nouvelle fenêtre
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Titre de votre application");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




