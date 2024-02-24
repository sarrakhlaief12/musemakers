package controllers;

import entities.Admin;
import entities.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class LoginAdmin {

    @FXML
    private TextField mailid;

    @FXML
    private PasswordField passeid;
    @FXML
    private CheckBox showid;

    @FXML
    private Button inscrireid;

    @FXML
    private Label errorLabel;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/musemakers";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Mettez votre mot de passe de base de données ici





    public void initialize() {

        showid.setOnAction(e -> handleShowPassword());
        inscrireid.setOnAction(this::handleInscription);
    }
    @FXML
    private void handleShowPassword() {
        if (showid.isSelected()) {
            passeid.setPromptText(passeid.getText());
            passeid.setText(null);
            passeid.setStyle("-fx-prompt-text-fill: black;"); // Change la couleur du texte d'invite en noir
        } else {
            passeid.setText(passeid.getPromptText());
            passeid.setPromptText(null);
            passeid.setStyle(""); // Réinitialise le style
        }
    }
    public void seConnecter() {

            String email = mailid.getText();
            String motDePasse = passeid.getText();

            Platform.runLater(() -> {
                errorLabel.setText("");
            });

            if (email.isEmpty() || motDePasse.isEmpty()) {
                Platform.runLater(() -> {
                    errorLabel.setText("L'email et le mot de passe ne peuvent pas être vides");
                });
                return;
            }

            String role = findUserByEmailAndPassword(email, motDePasse);
            if (role != null) {
                // Affichez le rôle de l'utilisateur après la connexion
                System.out.println("Login effectué en tant que " + role);

                try {
                    // Définir le chemin du fichier FXML en fonction du rôle
                    String fxmlFile = "";
                    switch (role) {
                        case "Admin":
                            fxmlFile = "/Accueil.fxml";
                            break;
                        case "Artiste":
                            fxmlFile = "/InscriptionArtiste.fxml";
                            break;
                        case "Client":
                            fxmlFile = "/InscriptionArtiste.fxml";
                            break;
                    }

                    // Charger le fichier FXML
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource(fxmlFile));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Créer une nouvelle scène
                    Scene scene = new Scene(root);

                    // Obtenir le stage actuel
                    Stage currentStage = (Stage) mailid.getScene().getWindow();

                    // Afficher la nouvelle scène
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (RuntimeException e) {
                    e.printStackTrace();


                }
            } else {
                // Les informations d'identification ne sont pas valides, affichez un message d'erreur
                Platform.runLater(() -> {
                    errorLabel.setText("Email ou mot de passe invalide");
                });
            }

   }

    private String findUserByEmailAndPassword(String email, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user WHERE email = ? AND mdp = ?")) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void handleInscription(ActionEvent e) {
        // Votre code d'inscription ici...

        // Charger l'interface d'accueil
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Accueil.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    /*@FXML
  private void handleButtonAction(ActionEvent event) throws IOException {
      // Charger le deuxième fichier FXML
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginClient.fxml"));

      // Créer une nouvelle scène
      Parent root = loader.load();
      Scene scene = new Scene(root);

      // Obtenir le contrôleur pour le deuxième fichier FXML
      LoginClient secondController = loader.getController();

      // Fermer la fenêtre actuelle
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      currentStage.close();

      // Afficher la nouvelle scène
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.show();
  }*/
}
