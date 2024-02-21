package controllers;

import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import service.ServiceCour;

import java.sql.SQLException;
import java.time.LocalDate;
import entities.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.IService;
import service.ServiceCour;
import java.sql.SQLException;
import java.util.Set;

public class AjouterCour {

    @FXML
    private DatePicker datePicker;
    @FXML
    private DatePicker datefin;

    @FXML
    private TextField titreField;

    @FXML
    private TextField descriptionField;



    @FXML
    private Text title;

    private final ServiceCour serviceCour = new ServiceCour();

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            LocalDate dateDebut = datePicker.getValue();
            LocalDate dateFin = datefin.getValue();
            if (dateDebut == null) {
                throw new IllegalArgumentException("Veuillez sélectionner une date de début.");
            }

            if (!titreField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
                String titre = titreField.getText();
                String description = descriptionField.getText();

                // Créer l'objet Cour
                Cour cour = new Cour(titre, description, dateDebut, dateFin, null);

                System.out.printf(cour.toString());
                // Appeler la méthode pour ajouter le cours
                serviceCour.ajouter(cour);

                // Afficher une confirmation à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Cour ajouté avec succès");
                alert.showAndWait();
            } else {
                throw new IllegalArgumentException("Le titre ou la description est vide");
            }
        } catch (Exception e) {
            // Gérer les autres exceptions
            afficherAlerteErreur("Erreurrrr", e.getMessage());
        }
    }

    private void afficherAlerteErreur(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public class AfficherParking {

        @FXML
        private TableView<Cour> tableCour;

        @FXML
        private TableColumn<Cour, String> colTitre;

        @FXML
        private TableColumn<Cour, String> colDescription;

        @FXML
        private TableColumn<Cour, LocalDate> colDateDebut;

        @FXML
        private TableColumn<Cour, LocalDate> colDateFin;

        @FXML
        private TableColumn<Cour, Void> colDelete;

        @FXML
        private TableColumn<Cour, Void> colEdit;

        @FXML
        private TextField txtTitre;

        @FXML
        private TextField txtDescription;

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

        private Cour cour;

        private final IService<Cour> serviceCour = new ServiceCour();

        @FXML
        void initialize() throws SQLException  {
            Set<Cour> cours = serviceCour.getAll();
            ObservableList<Cour> courList = FXCollections.observableArrayList(cours);

            colTitre.setCellValueFactory(new PropertyValueFactory<>("titre_cours"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description_cours"));
            colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut_cours"));
            colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin_cours"));

            // Ajout de la colonne de suppression
            colDelete.setCellFactory(param -> new TableCell<>() {
                private final Button deleteButton = new Button("Supprimer");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        deleteButton.setOnAction(event -> {
                            Cour cour = getTableView().getItems().get(getIndex());
                            // Supprimer l'élément
                            serviceCour.supprimer(cour.getId_cours());
                            tableCour.getItems().remove(cour);
                        });

                        setGraphic(deleteButton);
                    }
                }
            });

            // Ajout de la colonne de modification
            colEdit.setCellFactory(param -> new TableCell<>() {
                private final Button editButton = new Button("Modifier");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        editButton.setOnAction(event -> {
                            cour = getTableView().getItems().get(getIndex());
                            // Charger les détails du cour dans les champs de texte
                            txtTitre.setText(cour.getTitre_cours());
                            txtDescription.setText(cour.getDescription_cours());
                            datePickerDebut.setValue(cour.getDateDebut_cours());
                            datePickerFin.setValue(cour.getDateFin_cours());
                        });

                        setGraphic(editButton);
                    }
                }
            });

            tableCour.setItems(courList);
        }

        @FXML
        void saveChanges() {
            // Mettre à jour l'objet cour avec les nouvelles valeurs
            cour.setTitre_cours(txtTitre.getText());
            cour.setDescription_cours(txtDescription.getText());
            cour.setDateDebut_cours(datePickerDebut.getValue());
            cour.setDateFin_cours(datePickerFin.getValue());

            // Mettre à jour l'objet cour dans la base de données
            serviceCour.modifier(cour);

            // Rafraîchir le tableau
            tableCour.refresh();
        }

        @FXML
        void ajouterCour() {
            // Créer un nouvel objet Cour avec les valeurs des champs de texte
            Cour newCour = new Cour();
            newCour.setTitre_cours(txtTitre.getText());
            newCour.setDescription_cours(txtDescription.getText());
            newCour.setDateDebut_cours(datePickerDebut.getValue());
            newCour.setDateFin_cours(datePickerFin.getValue());

            // Ajouter le nouvel objet Cour à la base de données
            serviceCour.ajouter(newCour);

            // Ajouter le nouvel objet Cour au tableau
            tableCour.getItems().add(newCour);

            // Rafraîchir le tableau
            tableCour.refresh();
        }
    }
}
