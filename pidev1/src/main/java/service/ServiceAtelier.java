package service;

import entities.Atelier;
import entities.Cour;
import entities.User;
import utils.DataSource;

import java.sql.*;
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


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateDebutStr = sdf.format(p.getDateDebut_atelier());
            String dateFinStr = sdf.format(p.getDateFin_atelier());

            String requete = "UPDATE atelier SET id_cours=?, dateDebut_atelier=?, dateFin_atelier=?, lien_atelier=? WHERE id_atelier=?";
            try {
                PreparedStatement pst = conn.prepareStatement(requete);
                pst.setInt(1, p.getId_atelier());
                pst.setInt(2, p.getCour().getId_cours());
                pst.setString(3, dateDebutStr);
                pst.setString(4, dateFinStr);
                pst.setString(5, p.getLien());


                pst.executeUpdate();
                System.out.println("Atelier modifié avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification de l'atelier : " + e.getMessage());
            }
        }

    @Override
    public void supprimer(int id) {
        try {
            String requete = "DELETE  FROM atelier WHERE id_cours=?";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("atelier supprimé!");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("atelier non supprimé!");
        }

    }

    @Override
    public Atelier getOneById(int id) {
        Atelier atelier = null;
        String query = "SELECT * FROM atelier WHERE id_atelier = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Récupérer les données de l'atelier à partir du ResultSet
                int id_cours = rs.getInt("id_cours");
                Date dateDebut = rs.getDate("dateDebut_atelier");
                Date dateFin = rs.getDate("dateFin_atelier");
                String lien = rs.getString("lien_atelier");
                // Créer une nouvelle instance de Atelier
                atelier = new Atelier(id, new Cour(id_cours), dateDebut, dateFin, lien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atelier;
    }



    @Override
    public Set<Atelier> getAll() {
        return null;
    }
}












