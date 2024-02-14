package sevice;

import entities.Commentaire;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CommentaireService implements IService<Commentaire>{
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;

    public CommentaireService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouter(Commentaire c) {
        String requete = "INSERT INTO commentaire (idCom, idRec, DateCom, ContenuCom) VALUES (?, ?, ?, ?)";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getIdCom());
            pst.setInt(2, c.getReclamation().getIdRec());
            pst.setDate(3, new java.sql.Date(c.getDateCom().getTime()));
            pst.setString(4, c.getContenuCom());
            pst.executeUpdate();
            System.out.println("Commentaire ajouté !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Commentaire c) {
        String requete = "UPDATE commentaire SET idRec = ?, DateCom = ?, ContenuCom = ? WHERE idCom = ?";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getReclamation().getIdRec());
            pst.setDate(2, new java.sql.Date(c.getDateCom().getTime()));
            pst.setString(3, c.getContenuCom());
            pst.setInt(4, c.getIdCom());
            pst.executeUpdate();
            System.out.println("Commentaire modifié !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimer(int id) {
        String requete = "DELETE FROM commentaire WHERE idCom = ?";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Commentaire supprimé !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Commentaire getOneById(int id) {
        Commentaire c = null;
        String requete = "SELECT * FROM commentaire WHERE idCom = ?";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                c = new Commentaire();
                c.setIdCom(rst.getInt("idCom"));
                // Vous devez récupérer l'objet Reclamation associé à partir de la base de données
                c.setDateCom(rst.getDate("DateCom"));
                c.setContenuCom(rst.getString("ContenuCom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    @Override
    public Set<Commentaire> getAll() {
        Set<Commentaire> commentaires = new HashSet<>();
        String requete = "SELECT * FROM commentaire";
        try {
            ste = cnx.createStatement();
            ResultSet rst = ste.executeQuery(requete);
            while (rst.next()) {
                Commentaire c = new Commentaire();
                c.setIdCom(rst.getInt("idCom"));
                // Vous devez récupérer l'objet Reclamation associé à partir de la base de données
                c.setDateCom(rst.getDate("DateCom"));
                c.setContenuCom(rst.getString("ContenuCom"));
                commentaires.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commentaires;
    }
}
