package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import entities.Artiste;
import javafx.stage.FileChooser;
import service.ServiceUser;

import java.io.File;
import java.time.LocalDate;
import java.sql.Date;

public class InscriptionArtiste {

    @FXML
    private TextField nomid;
    @FXML
    private TextField prenomid;
    @FXML
    private TextField mailid;
    @FXML
    private PasswordField passeid;
    @FXML
    private TextField telid;
    @FXML
    private TextField carteproid;
    @FXML
    private DatePicker dateid;
    @FXML
    private Button browseid;
    @FXML
    private Button inscrireid;
    @FXML
    private CheckBox showPassword;
    @FXML
    private PasswordField confirmerPasseid;
    private FileChooser fileChooser;
    private File file;

    public void initialize() {
        fileChooser = new FileChooser();
        browseid.setOnAction(e -> handleBrowse());
        inscrireid.setOnAction(e -> handleInscription());
        showPassword.setOnAction(e -> handleShowPassword());
    }

    @FXML
    private void handleBrowse() {
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            carteproid.setText(file.toURI().toString());
        }
    }

    @FXML
    private void handleInscription() {
        String nom = nomid.getText();
        String prenom = prenomid.getText();
        String email = mailid.getText();
        String password = passeid.getText();
        String confirmerPassword = confirmerPasseid.getText();
        int tel = Integer.parseInt(telid.getText());
        LocalDate date = dateid.getValue();
        String cartepro = carteproid.getText();

        if (!password.equals(confirmerPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'inscription");
            alert.setHeaderText(null);
            alert.setContentText("Les mots de passe ne correspondent pas !");
            alert.showAndWait();
            return;
        }

        Artiste artiste = new Artiste(nom, prenom, email, password, tel, Date.valueOf(date), cartepro);
        ServiceUser serviceUser = new ServiceUser();
        serviceUser.ajouter(artiste);
    }

    @FXML
    private void handleShowPassword() {
        if (showPassword.isSelected()) {
            passeid.setPromptText(passeid.getText());
            passeid.setText(null);
            passeid.setDisable(true);
        } else {
            passeid.setText(passeid.getPromptText());
            passeid.setPromptText(null);
            passeid.setDisable(false);
        }
    }
}


