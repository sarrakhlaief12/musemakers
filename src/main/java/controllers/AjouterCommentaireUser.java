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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        c.setReclamation(r);
        c.setContenuCom(contenuCommentaireTF.getText());
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


        CvContenu.setCellValueFactory(new PropertyValueFactory<>("ContenuCom"));
        CvDate.setCellValueFactory(new PropertyValueFactory<>("DateCom"));

        TableViewCommentaire.setItems(FXCollections.observableArrayList(CommentaireList));
    }
/*
 @FXML
 void modifier(ActionEvent event) throws IOException, SQLException {
     // Récupérer le commentaire sélectionné dans la table
     Commentaire selectedCommentaire = TableViewCommentaire.getSelectionModel().getSelectedItem();

     if (selectedCommentaire != null) {
         // Mettre à jour le contenu du commentaire avec le texte du TextField
         selectedCommentaire.setContenuCom(contenuCommentaireTF.getText());

         // Mettre à jour la date du commentaire avec la date actuelle
         selectedCommentaire.setDateCom(new Date(System.currentTimeMillis()));

         // Demander une confirmation à l'utilisateur
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Confirmation de modification");
         alert.setHeaderText("Modifier le commentaire");
         alert.setContentText("Êtes-vous sûr de vouloir modifier le commentaire sélectionné ?");

         Optional<ButtonType> result = alert.showAndWait();
         // Si l'utilisateur confirme la modification, procéder
         if (result.isPresent() && result.get() == ButtonType.OK) {
             // Mettre à jour le commentaire dans la base de données
             cs.modifier(selectedCommentaire);

             // Rafraîchir l'affichage des commentaires dans la TableView
             ShowCommentaire();
         }
     } else {
         // Afficher un message si aucun commentaire n'est sélectionné
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Aucun commentaire sélectionné");
         alert.setHeaderText("Aucun commentaire sélectionné");
         alert.setContentText("Veuillez sélectionner un commentaire à modifier.");
         alert.showAndWait();
     }
 }*/
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
        // Associez la réclamation au commentaire
        c.setReclamation(r);
        // Mettez à jour le contenu du commentaire avec le texte du TextField
        c.setContenuCom(contenuCommentaireTF.getText());
        c.setDateCom(new Date(System.currentTimeMillis()));
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
      }

