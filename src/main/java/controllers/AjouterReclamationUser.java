package controllers;

import entities.Reclamation;
import entities.User;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ReclamationService;
import service.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AjouterReclamationUser {
    private final ReclamationService rs= new ReclamationService( );
    private final ServiceUser su= new ServiceUser();

    @FXML
    private TextField CategorieRecTF;

    @FXML
    private TableColumn<?, ?> CvCat;

    @FXML
    private TableColumn<?, ?> CvDate;

    @FXML
    private TableColumn<?, ?> CvDescri;

    @FXML
    private TableColumn<?, ?> CvStatut;

    @FXML
    private TableView<Reclamation> TableViewRec;

    @FXML
    private Button ajouter;

    @FXML
    private Label cate;

    @FXML
    private Label desc;

    @FXML
    private TextField descriRecTF;
    @FXML
    private TextField searchTF;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;
    @FXML
    private Button rec;

    List<Reclamation> RecList;

    public void initialize() throws IOException {
        ShowReclamation();
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Reclamation r=new Reclamation();
        User userAdd= su.getOneById(2);
        String descriRec = descriRecTF.getText();

        // Ajouter le contrôle de saisie ici
        if (descriRec.length() > 50) {
            System.out.println("Vous avez dépassé 50 caractères.");
            return;
        } else if (descriRec.isEmpty()) {
            System.out.println("La description est vide.");
            return;
        }

        r.setCategorieRec(CategorieRecTF.getText());
        r.setStatutRec("En cours");
        r.setDescriRec(descriRec);
        r.setUser(userAdd);
        r.setDateRec(new Date(System.currentTimeMillis()));

        try {
            rs.ajouter(r);
            ShowReclamation(); // Rafraîchir les données de la table
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        // Obtenez la réclamation sélectionnée dans la table
        Reclamation r = TableViewRec.getSelectionModel().getSelectedItem();
        if (r != null) {
            String descriRec = descriRecTF.getText();

            // Ajouter le contrôle de saisie ici
            if (descriRec.length() > 50) {
                System.out.println("Vous avez dépassé 50 caractères.");
                return;
            } else if (descriRec.isEmpty()) {
                System.out.println("La description est vide.");
                return;
            }

            // Mettez à jour les champs de la réclamation
            r.setCategorieRec(CategorieRecTF.getText());
            r.setDescriRec(descriRec);

            try {
                // Mettez à jour la réclamation dans la base de données
                rs.modifier(r);
                // Rafraîchir les données de la table
                ShowReclamation();
                TableViewRec.refresh(); // Ajoutez cette ligne
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void supprimer(ActionEvent event) throws IOException, SQLException {
        // Obtenez l'objet Reclamation sélectionné
        Reclamation selectedReclamation = TableViewRec.getSelectionModel().getSelectedItem();

        // Vérifiez si une réclamation est sélectionnée
        if (selectedReclamation != null) {
            // Supprimez cet objet de votre base de données
            try {
                rs.supprimer(selectedReclamation.getIdRec());
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression de la réclamation: " + e.getMessage());
                return;
            }



            // Rafraîchir les données de la table
            ShowReclamation();
        } else {
            System.out.println("Aucune réclamation sélectionnée.");
        }
    }

    @FXML
    void rec(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamations.fxml"));

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reclamations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    public void ShowReclamation() throws IOException {
        try {
            RecList = rs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        User userAdd = su.getOneById(2);
        List<Reclamation> filteredRecList = new ArrayList<>();

        for (Reclamation r : RecList) {
            if (r.getUser().equals(userAdd)) {
                filteredRecList.add(r);
            }
        }

        // Créer une FilteredList
        FilteredList<Reclamation> filteredData = new FilteredList<>(FXCollections.observableArrayList(filteredRecList), p -> true);

        // Envelopper la FilteredList dans une SortedList
        SortedList<Reclamation> sortedData = new SortedList<>(filteredData);

        // Lier le comparateur de la SortedList au comparateur de TableView
        sortedData.comparatorProperty().bind(TableViewRec.comparatorProperty());

        // Ajouter les données triées (et filtrées) à la TableView
        TableViewRec.setItems(sortedData);

        CvDescri.setCellValueFactory(new PropertyValueFactory<>("descriRec"));
        CvDate.setCellValueFactory(new PropertyValueFactory<>("DateRec"));
        CvCat.setCellValueFactory(new PropertyValueFactory<>("CategorieRec"));
        CvStatut.setCellValueFactory(new PropertyValueFactory<>("StatutRec"));

        // Ajouter un listener à searchTF pour qu'il réagisse aux changements de texte
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reclamation -> {
                // Si le texte de recherche est vide, afficher toutes les réclamations
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Comparer le texte de recherche avec la catégorie et la description de chaque réclamation
                String lowerCaseFilter = newValue.toLowerCase();
                if (reclamation.getCategorieRec().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Le filtre correspond à la catégorie
                } else if (reclamation.getDescriRec().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Le filtre correspond à la description
                }
                return false; // Aucune correspondance
            });
        });
    }

}