package service;

import entities.Atelier;
import entities.Cour;
import entities.User;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class ServiceAtelier implements IService<Atelier>{

    Connection  conn= DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Atelier p) {
        String requete ="insert into atelier ( id_cours, dateDebut_atelier, dateFin_atelier, lien_atelier) VALUES ( ?, ?, ?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, p.getCour().getId_cours()); // Supposons que l'id_cours soit la clé étrangère
            pst.setDate(2, new java.sql.Date(p.getDateDebut_atelier().getTime()));
            pst.setDate(3, new java.sql.Date(p.getDateFin_atelier().getTime()));
            pst.setString(4, p.getLien());
            pst.executeUpdate();
            System.out.println("Atelier ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'atelier : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Atelier p) {





    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Atelier getOneById(int id) {
      /*  Atelier a = new Atelier();
        String req = "SELECT * FROM atelier WHERE id_atelier = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                a.setId_atelier(rst.getInt("id_cours"));

                a.setDateDebut_atelier(rst.getDate("dateDebut_atelier"));
                a.setDateFin_atelier(rst.getDate("DateFin_atelier"));
                a.setLien(rst.getString("lien"));
                Cour c1 = new Cour();

                c1.setId_cours(rst.getInt("id_cours"));

                a.setId_cours(c1);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;*/
       /* Atelier a = new Atelier();
        String req = "SELECT * FROM atelier WHERE id_atelier = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                a.setId_atelier(rst.getInt("id_atelier")); // Changed from "id_cours" to "id_atelier"

                a.setDateDebut_atelier(rst.getDate("dateDebut_atelier"));
                a.setDateFin_atelier(rst.getDate("DateFin_atelier"));
                a.setLien(rst.getString("lien_atelier"));
                Cour c1 = new Cour();

                c1.setId_cours(rst.getInt("id_cours")); // Assuming "id_cours" is a field in your "Cour" class

                a.setCour(c1); // Changed from "setId_cours(c1)" to "setCour(c1)"

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;

    }

    @Override
    public Set<Atelier> getAll() {
        return null;
    }
}




*/
