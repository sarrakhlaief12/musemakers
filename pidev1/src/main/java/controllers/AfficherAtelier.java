package controllers;

import entities.Atelier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.IService;
import service.ServiceAtelier;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

public class AfficherAtelier {

    @FXML
    private TableView<Atelier> tableAtelier;

    @FXML
    private TableColumn<Atelier, String> colTitre;

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
    private Button btnAdd;

    @FXML
    private Label lblTitreError, lblDescriptionError, lblDateDebutError, lblDateFinError;

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
              //  txtLien.setText(atelierSelectionne.getLien());
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
}
