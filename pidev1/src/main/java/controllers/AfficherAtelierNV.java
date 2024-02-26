package controllers;

import entities.Atelier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.IService;
import service.ServiceAtelier;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class AfficherAtelierNV {

    @FXML
    private TableView<Atelier> tableAtelier;


    @FXML
    private TableColumn<Atelier, String> colDescription;

    @FXML
    private TableColumn<Atelier, LocalDate> colDateDebut;

    @FXML
    private TableColumn<Atelier, LocalDate> colDateFin;

    @FXML
    private TableColumn<Atelier, Void> colDelete;

    @FXML
    private TextField txtLien;

    @FXML
    private DatePicker datePickerDebut;

    @FXML
    private DatePicker datePickerFin;

    @FXML
    private Button btnSave;



    @FXML
    private Label lblTitreError, lblDescriptionError, lblDateDebutError, lblDateFinError;
    @FXML
    private Button ajoutid ;

    private Atelier atelier;

    private final IService<Atelier> serviceAtelier = new ServiceAtelier();

    @FXML
    void initialize() throws SQLException {
        // Récupérer la liste des ateliers depuis le service
        Set<Atelier> ateliers = serviceAtelier.getAll();
        ObservableList<Atelier> atelierList = FXCollections.observableArrayList(ateliers);

        // Initialiser les colonnes de la table avec les valeurs appropriées

        colDescription.setCellValueFactory(new PropertyValueFactory<>("lien"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut_atelier"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin_atelier"));

        // Ajouter les ateliers à la table
        tableAtelier.setItems(atelierList);

        // Gérer le clic sur une ligne de la table
        tableAtelier.setOnMouseClicked(event -> {
            if (!tableAtelier.getSelectionModel().isEmpty()) {
                Atelier atelierSelectionne = tableAtelier.getSelectionModel().getSelectedItem();
                // Remplir les champs avec les informations de l'atelier sélectionné
               // txtLien.setText(atelierSelectionne.getLien());
                datePickerDebut.setValue(atelierSelectionne.getDateDebut_atelier());
                datePickerFin.setValue(atelierSelectionne.getDateFin_atelier());
                atelier = atelierSelectionne;
            }
        });

        // Ajouter la fonctionnalité de suppression à la colonne Supprimer
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    deleteButton.setOnAction(event -> {
                        Atelier atelier = getTableView().getItems().get(getIndex());
                        // Supprimer l'atelier
                        serviceAtelier.supprimer(atelier.getId_atelier());
                        tableAtelier.getItems().remove(atelier);
                    });
                    setGraphic(deleteButton);
                }
            }
        });
    }


    @FXML
    void saveChanges() {
        // Vérifier si un atelier est sélectionné
        if (atelier != null) {
            // Mettre à jour l'objet atelier avec les nouvelles valeurs
            atelier.setLien(txtLien.getText());
            atelier.setDateDebut_atelier(datePickerDebut.getValue());
            atelier.setDateFin_atelier(datePickerFin.getValue());

            // Mettre à jour l'objet atelier dans la base de données
            serviceAtelier.modifier(atelier);

            // Rafraîchir le tableau
            tableAtelier.refresh();
        } else {
            // Afficher un message d'erreur si aucun atelier n'est sélectionné
            afficherAlerteErreur("Erreur", "Aucun atelier sélectionné.");
        }
    }

    // Méthode pour afficher une alerte d'erreur
    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void ajoutid(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterAtelier.fxml")));
        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ajouter Atelier");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

}
