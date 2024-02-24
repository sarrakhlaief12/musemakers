package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Accueil {

    @FXML
    private Button homeButton;

    // Vous pouvez définir une méthode pour gérer les actions sur le bouton
    @FXML
    private void handleHomeButtonAction() {
        System.out.println("Le bouton Home a été cliqué !");
    }
}
