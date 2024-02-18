package controllers;

import entities.Reclamation;
import entities.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ReclamationService;
import service.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjouterReclamationController {
    private final ReclamationService rs= new ReclamationService( );
    private final service.ServiceUser su= new ServiceUser();

    @FXML
    private TextField CategorieRecTF;

    @FXML
    private TextField StatutRecTF;

    @FXML
    private TextField descriRecTF;

    @FXML
    private Button idA;
    @FXML
    private TableColumn<?, ?> CvCat;

    @FXML
    private TableColumn<?, ?> CvClient;

    @FXML
    private TableColumn<?, ?> CvDate;

    @FXML
    private TableColumn<?, ?> CvDescri;

    @FXML
    private TableColumn<?, ?> CvStatut;

    @FXML
    private TableView<?> TableViewRec;


public void initialize() throws IOException {
    ShowReclamation();
}


    @FXML
    void ajouter(ActionEvent event) throws  IOException {
        Reclamation r=new Reclamation();
        User userAdd= su.getOneById(2);
        r.setCategorieRec(CategorieRecTF.getText());
        r.setStatutRec("En cours");
        r.setDescriRec(descriRecTF.getText());
        r.setUser(userAdd);
        r.setDateRec(new Date(System.currentTimeMillis()));


        try {
            rs.ajouter(r);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    List<Reclamation> RecList;
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
        CvClient.setCellValueFactory(new PropertyValueFactory<>("user"));

        CvDescri.setCellValueFactory(new PropertyValueFactory<>("descriRec"));
        CvDate.setCellValueFactory(new PropertyValueFactory<>("DateRec"));
        CvCat.setCellValueFactory(new PropertyValueFactory<>("CategorieRec"));
        CvStatut.setCellValueFactory(new PropertyValueFactory<>("StatutRec"));

        if (TableViewRec != null && TableViewRec instanceof TableView) {
            ((TableView<Reclamation>) TableViewRec).setItems(FXCollections.observableArrayList(filteredRecList));
        }


    }


    }



