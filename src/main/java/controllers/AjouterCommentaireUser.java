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
        ShowCommentaire();
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Commentaire c = new Commentaire();
        Reclamation r = null; // Remplacez 1 par l'ID de la réclamation appropriée
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
       List<Reclamation> filteredRecList = new ArrayList<>();


        CvContenu.setCellValueFactory(new PropertyValueFactory<>("ContenuCom"));
        CvDate.setCellValueFactory(new PropertyValueFactory<>("DateCom"));

        TableViewCommentaire.setItems(FXCollections.observableArrayList(CommentaireList));
    }




  @FXML
  void modifier(ActionEvent event) throws IOException {
      // Obtenez le commentaire sélectionné dans la table
      Commentaire c = (Commentaire) TableViewCommentaire.getSelectionModel().getSelectedItem();

      if (c != null) {
          // Mettez à jour le contenu du commentaire avec le texte du TextField
          c.setContenuCom(contenuCommentaireTF.getText());

          // Mettez à jour la date du commentaire avec la date actuelle
          c.setDateCom(new Date(System.currentTimeMillis()));
          try {


              // Appelez la méthode modifier de CommentaireService
              cs.modifier(c);

              // Rafraîchir les données de la table
              ShowCommentaire();
              TableViewCommentaire.refresh();

          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
      }}


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

