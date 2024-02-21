package controllers;

import entities.Commentaire;
import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CommentaireService;
import service.ReclamationService;
import service.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javafx.collections.FXCollections.*;

public class AjouterComAdmin {
    private final CommentaireService cs = new CommentaireService();
    private final ServiceUser su = new ServiceUser();
    private final ReclamationService rs= new ReclamationService( );

    @FXML
    private TextField comTF;

    @FXML
    private TableColumn<?, ?> CvCom;

    @FXML
    private TableColumn<?, ?> CvNomA;

    @FXML
    private TableView<Commentaire> TableViewComA;

    @FXML
    private Button ajouter;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;
    public void initialize() throws IOException {
        ShowCommentaire();


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
        List<Reclamation> filteredCommentaireList = new ArrayList<>();


        CvCom.setCellValueFactory(new PropertyValueFactory<>("ContenuCom"));
        CvNomA.setCellValueFactory(new PropertyValueFactory<>("user"));

        TableViewComA.setItems(observableArrayList(CommentaireList).sorted());

    }
    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Commentaire c = new Commentaire();
        Reclamation r = null ; // Remplacez 1 par l'ID de la réclamation appropriée
        try {
            r = rs.getOneById(113);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        c.setReclamation(r);
        c.setContenuCom(comTF.getText());
        c.setDateCom(new Date(System.currentTimeMillis()));

        try {
            cs.ajouter(c);
            ShowCommentaire(); // Rafraîchir les données de la table
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        // Obtenez le commentaire sélectionné dans la table
        Commentaire c = TableViewComA.getSelectionModel().getSelectedItem();
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
            c.setContenuCom(comTF.getText());
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






    @FXML
    void supprimer(ActionEvent event) throws IOException, SQLException {
        // Obtenez le commentaire sélectionné dans la table
        Commentaire c = TableViewComA.getSelectionModel().getSelectedItem();
        if (c != null) {
            // Supprimez le commentaire de la base de données
            cs.supprimer(c.getIdCom());
            // Rafraîchir les données de la table
            ShowCommentaire();
        }
    }





}