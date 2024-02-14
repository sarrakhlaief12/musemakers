package sevice;

import entities.Reclamation;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ReclamationService implements IService<Reclamation>{
    private Connection cnx ;
    private Statement ste ;
    PreparedStatement ps;
    private PreparedStatement pst ;

    public ReclamationService() {
        cnx= DataSource.getInstance().getCnx();
    }

    public void ajouter(Reclamation r){
        String requete = " insert into reclamation (idU,descriRec,DateRec,CategorieRec,StatutRec) values (?,?,?,?,?)" ;
        try {
            pst=cnx.prepareStatement(requete);
            pst.setInt(1,r.getUser().getId_user());  // Utilisez 'r.getUser().getId_user()' au lieu de 'r.getIdU()'
            pst.setString(2,r.getDescriRec());
            pst.setDate(3, new java.sql.Date(r.getDateRec().getTime()));
            pst.setString(4,r.getCategorieRec());
            pst.setString(5,r.getStatutRec());
            pst.executeUpdate();
            System.out.println("Reclamation ajoutée!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Reclamation r){
        try {
            PreparedStatement ps = cnx.prepareStatement("UPDATE reclamation SET idU = ?, descriRec = ?, DateRec = ?, CategorieRec = ?, StatutRec = ? WHERE idRec = ?");
            ps.setInt(1, r.getUser().getId_user());  // Utilisez 'r.getUser().getId_user()' au lieu de 'r.getIdU()'
            ps.setString(2, r.getDescriRec());
            ps.setDate(3, new java.sql.Date(r.getDateRec().getTime()));
            ps.setString(4, r.getCategorieRec());
            ps.setString(5, r.getStatutRec());
            ps.setInt(6, r.getIdRec());
            ps.executeUpdate();
            System.out.println("Reclamation modifiée!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int idRec ) {
        try {
            String requete = "DELETE  FROM Reclamation WHERE idRec=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, idRec);
            pst.executeUpdate();
            System.out.println("Reclamation supprimée!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Reclamation non supprimée!");
        }
    }

    @Override
    public Reclamation getOneById(int id) {
        Reclamation r = new Reclamation();
        String req = "SELECT * FROM Reclamation WHERE idRec = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                User user = new User();
                user.setId_user(rst.getInt("IdU"));  // Utilisez 'user.setId_user(rst.getInt("IdU"))' au lieu de 'r.setIdRec(rst.getInt("IdU"))'
                r.setUser(user);  // Ajoutez cette ligne
                r.setIdRec(rst.getInt("idRec"));
                r.setDescriRec(rst.getString("DescriRec"));
                r.setDateRec(rst.getDate("dateRec"));
                r.setCategorieRec(rst.getString("categorieRec"));
                r.setStatutRec(rst.getString("statutRec"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }
    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();
        String req = "SELECT * FROM Reclamation";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                Reclamation r = new Reclamation();
                User user = new User();
                user.setId_user(rst.getInt("IdU"));  // Utilisez 'user.setId_user(rst.getInt("IdU"))' au lieu de 'r.setIdRec(rst.getInt("IdU"))'
                r.setUser(user);  // Ajoutez cette ligne
                r.setIdRec(rst.getInt("idRec"));
                r.setDescriRec(rst.getString("DescriRec"));
                r.setDateRec(rst.getDate("dateRec"));
                r.setCategorieRec(rst.getString("categorieRec"));
                r.setStatutRec(rst.getString("statutRec"));
                reclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }

}
