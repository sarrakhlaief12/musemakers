package controllers;

import entities.Commentaire;
import entities.Reclamation;
import entities.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CommentaireService;
import service.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class AjouterCommentaireUser {
    private final CommentaireService cs = new CommentaireService();
    private final ReclamationService rs = new ReclamationService();

    @FXML
    private TextField contenuCommentaireTF;

    @FXML
    private TableColumn<?, ?> CvContenu;

    @FXML
    private TableColumn<?, ?> CvDate;

    @FXML
    private TableView<Commentaire> TableViewCommentaire;

    @FXML
    private Button ajout;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;
    private static final List<String> BAD_WORDS = Arrays.asList("Sick", "Bad", "Dump");
    public void initialize() throws IOException {
        TableViewCommentaire.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifie si c'est un simple clic
                Commentaire selectedCommentaire = (Commentaire) TableViewCommentaire.getSelectionModel().getSelectedItem();
                if (selectedCommentaire != null) {
                    // Afficher les informations de la séance sélectionnée dans le formulaire
                    displayCommentaireInfo(selectedCommentaire);
                }
            }
        });
        ShowCommentaire();
    }


    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Commentaire c = new Commentaire();
        Reclamation r = null ; // Remplacez 1 par l'ID de la réclamation appropriée
        try {
            r = rs.getOneById(35);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String contenuCom = contenuCommentaireTF.getText();

        // Ajouter le contrôle de saisie ici
        if (contenuCom.length() > 50) {
            System.out.println("Vous avez dépassé 50 caractères.");
            return;
        } else if (contenuCom.isEmpty()) {
            System.out.println("Le contenu du commentaire est vide.");
            return;
        }

        c.setReclamation(r);
        c.setContenuCom(contenuCom);
        c.setDateCom(new Date(System.currentTimeMillis()));

        try {
            cs.ajouter(c);
            ShowCommentaire(); // Rafraîchir les données de la table
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    List<Commentaire> CommentaireList;
    @FXML
    public void ShowCommentaire() throws IOException {
        try {
            CommentaireList = cs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Reclamation reclamationAdd = rs.getOneById(35);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Reclamation> filteredCommentaireList = new ArrayList<>();

        // Censor bad words before displaying them
        CommentaireList.forEach(commentaire -> {
            String censoredContenuCom = censorBadWords(commentaire.getContenuCom());
            commentaire.setContenuCom(censoredContenuCom);
        });

        CvContenu.setCellValueFactory(new PropertyValueFactory<>("ContenuCom"));
        CvDate.setCellValueFactory(new PropertyValueFactory<>("DateCom"));

        TableViewCommentaire.setItems(FXCollections.observableArrayList(CommentaireList));
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        // Obtenez le commentaire sélectionné dans la table
        Commentaire c = TableViewCommentaire.getSelectionModel().getSelectedItem();
        if (c != null) {
            // Obtenez une instance de la réclamation que vous souhaitez associer
            Reclamation r = null;
            try {
                r = rs.getOneById(35); // Remplacez 35 par l'ID de la réclamation appropriée
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String contenuCom = contenuCommentaireTF.getText();

            // Ajouter le contrôle de saisie ici
            if (contenuCom.length() > 50) {
                System.out.println("Vous avez dépassé 50 caractères.");
                return;
            } else if (contenuCom.isEmpty()) {
                System.out.println("Le contenu du commentaire est vide.");
                return;
            }

            // Associez la réclamation au commentaire
            c.setReclamation(r);
            // Mettez à jour le contenu du commentaire avec le texte du TextField
            c.setContenuCom(contenuCom);
            c.setDateCom(new Date(System.currentTimeMillis()));

            // Check if the message has been modified
            String censoredMessage = censorBadWords(contenuCom);
            if (!contenuCom.equals(censoredMessage)) {
                System.out.println("Votre commentaire contient des mots interdits.");
                return;
            } else {
                System.out.println("Votre commentaire a été modifié avec succès.");
            }

            try {
                // Appelez la méthode modifier pour mettre à jour le commentaire dans la base de données
                cs.modifier(c);
                // Rafraîchir les données de la table
                ShowCommentaire();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la modification du commentaire", e);
            }
        }
    }


    private void displayCommentaireInfo(Commentaire c) {

        contenuCommentaireTF.setText(c.getContenuCom());
    }
    @FXML
    void supprimer(ActionEvent event) throws IOException {
        // Obtenez le commentaire sélectionné dans la TableView
        Commentaire c = TableViewCommentaire.getSelectionModel().getSelectedItem();

        if (c != null) {
            try {
                cs.supprimer(c.getIdCom());
                ShowCommentaire(); // Rafraîchir les données de la table
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private String censorBadWords(String text) {
        for (String badWord : BAD_WORDS) {
            // Replace bad words with **
            text = text.replaceAll("(?i)" + badWord, "*****");
        }
        return text;
    }
}