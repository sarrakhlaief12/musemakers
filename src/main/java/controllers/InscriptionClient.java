package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import entities.Client;
import service.ServiceUser;

import java.time.LocalDate;

public class InscriptionClient {

    @FXML
    private TextField nomid;

    @FXML
    private TextField prenomid;

    @FXML
    private TextField mailid;

    @FXML
    private TextField passeid;

    @FXML
    private TextField telid;

    @FXML
    private DatePicker dateid;

    @FXML
    private Button validerid;

    public InscriptionClient() {
    }

    @FXML
    public void initialize() {
        if (validerid != null) {
            validerid.setOnAction(e -> handleSubmit());
        } else {
            System.out.println("validerid is null");
        }
    }

    private void handleSubmit() {
        String nom = nomid.getText();
        String prenom = prenomid.getText();
        String mail = mailid.getText();
        String passe = passeid.getText();
        String tel = telid.getText();
        LocalDate date = dateid.getValue();

        // Créez un nouvel utilisateur en fonction des informations du formulaire
        // Ici, je suppose que vous créez un Client, mais vous pouvez créer un Admin ou un Artiste si nécessaire
        Client newUser = new Client();
        newUser.setNom_user(nom);
        newUser.setPrenom_user(prenom);
        newUser.setEmail(mail);
        newUser.setMdp(passe);
        newUser.setNum_tel(Integer.parseInt(tel)); // Assurez-vous que le numéro de téléphone est un nombre entier
        newUser.setDate_de_naissance(java.sql.Date.valueOf(date));

        // Créez une instance de votre service
        ServiceUser serviceUser = new ServiceUser();

        // Ajoutez le nouvel utilisateur à l'aide de votre service
        serviceUser.ajouter(newUser);
    }
}
