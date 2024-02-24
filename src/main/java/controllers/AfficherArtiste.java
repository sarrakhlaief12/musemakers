package controllers;

import entities.Artiste;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.ServiceUser;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AfficherArtiste {

    @FXML
    private TableView<Artiste> tableid;
    @FXML
    private TableColumn<Artiste, String> nomid;
    @FXML
    private TableColumn<Artiste, String> prenomid;
    @FXML
    private TableColumn<Artiste, String> mailid;
    @FXML
    private TableColumn<Artiste, String> passeid;
    @FXML
    private TableColumn<Artiste, String> telid;
    @FXML
    private TableColumn<Artiste, String> dateid;
    @FXML
    private TableColumn<Artiste, String> carteproid;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField passeField;
    @FXML
    private TextField telField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField carteproField;
    @FXML
    private Button browseid;
    @FXML
    private Button modifierid;
    @FXML
    private Button supprimerid;

    @FXML
    public void initialize() {
        // Obtenez la liste des artistes à partir de votre service
        Set<Artiste> artistes = getAllArtists();


        // Convertir le Set en ObservableList
        ObservableList<Artiste> observableList = FXCollections.observableArrayList(artistes);

        // Configurez les colonnes de la table pour afficher les propriétés correctes de l'objet Artiste
        nomid.setCellValueFactory(new PropertyValueFactory<>("nom_user"));
        prenomid.setCellValueFactory(new PropertyValueFactory<>("prenom_user"));
        mailid.setCellValueFactory(new PropertyValueFactory<>("email"));
        passeid.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        telid.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        dateid.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        carteproid.setCellValueFactory(new PropertyValueFactory<>("cartepro"));


        // Ajoutez les artistes à la table
        tableid.setItems(observableList);

        // Ajoutez un ChangeListener à la sélection du TableView
        tableid.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Artiste selectedArtiste = tableid.getSelectionModel().getSelectedItem();
                nomField.setText(selectedArtiste.getNom_user());
                prenomField.setText(selectedArtiste.getPrenom_user());
                mailField.setText(selectedArtiste.getEmail());
                passeField.setText(selectedArtiste.getMdp());
                telField.setText(String.valueOf(selectedArtiste.getNum_tel()));
                java.sql.Date sqlDate = (Date) selectedArtiste.getDate_de_naissance();
                java.time.LocalDate localDate = sqlDate.toLocalDate();
                dateField.setValue(localDate);
                carteproField.setText(selectedArtiste.getCartepro());
            }
        });

        //delete
        supprimerid.setOnAction(e -> handleDelete());
        modifierid.setOnAction(e -> handleUpdate());
    }

    private void handleUpdate() {
        // Obtenez l'artiste sélectionné dans le TableView
        Artiste selectedArtiste = tableid.getSelectionModel().getSelectedItem();

        if (selectedArtiste != null) {
            // Mettez à jour l'artiste avec les informations des champs de texte
            selectedArtiste.setNom_user(nomField.getText());
            selectedArtiste.setPrenom_user(prenomField.getText());
            selectedArtiste.setEmail(mailField.getText());
            selectedArtiste.setMdp(passeField.getText());
            selectedArtiste.setNum_tel(Integer.parseInt(telField.getText()));
            selectedArtiste.setDate_de_naissance(java.sql.Date.valueOf(dateField.getValue()));
            selectedArtiste.setCartepro(carteproField.getText());

            // Mettez à jour l'artiste dans la base de données à l'aide de votre service
            ServiceUser serviceArtiste = new ServiceUser();
            if (selectedArtiste.getId_user() != -1) {
                serviceArtiste.modifier(selectedArtiste);
            } else {
                System.out.println("L'artiste sélectionné n'a pas d'ID.");
            }

            // Mettez à jour le TableView
            tableid.refresh();
        } else {
            System.out.println("Aucun artiste n'est sélectionné.");
        }

    }

    private void handleDelete() {
        // Obtenez l'artiste sélectionné dans le TableView
        Artiste selectedArtiste = tableid.getSelectionModel().getSelectedItem();

        if (selectedArtiste != null) {
            // Supprimez l'artiste sélectionné à l'aide de votre service
            ServiceUser serviceArtiste = new ServiceUser();
            serviceArtiste.supprimer(selectedArtiste.getId_user());

            // Supprimez l'artiste de la TableView
            tableid.getItems().remove(selectedArtiste);
        } else {
            System.out.println("Aucun artiste n'est sélectionné.");
        }

    }

    // Votre méthode pour obtenir tous les artistes
    public Set<Artiste> getAllArtists() {
        Set<Artiste> artistes = new HashSet<>();
        String sql = "SELECT * FROM user WHERE role = 'Artiste'";

        try (Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/musemakers", "root", "");
             PreparedStatement pstmt = cnx.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Artiste artiste = new Artiste();
                artiste.setId_user(rs.getInt("id_user")); // Ajoutez cette ligne

                artiste.setNom_user(rs.getString("nom_user"));
                artiste.setPrenom_user(rs.getString("prenom_user"));
                artiste.setEmail(rs.getString("email"));
                artiste.setMdp(rs.getString("mdp"));
                artiste.setNum_tel(rs.getInt("num_tel"));
                artiste.setDate_de_naissance(rs.getDate("date_de_naissance"));
                artiste.setCartepro(rs.getString("cartepro"));

                artistes.add(artiste);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artistes;
    }



}



