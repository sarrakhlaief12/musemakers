package edu.esprit.services;

import edu.esprit.entities.Oeuvre;
import edu.esprit.entities.Avis;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import java.util.Set;

import java.util.HashSet;

import java.sql.*;

public class ServiceAvis implements IService<Avis>{
    Connection cnx= DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Avis p) {
        String req = "INSERT INTO avis (commentaire, date_experience, note, id_oeuvre, id_user) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getCommentaire());
            ps.setDate(2, p.getDateExperience());
            ps.setInt(3, p.getNote());
            ps.setInt(4, p.getOeuvre().getId()); // Assuming Oeuvre has a getIdOeuvre method
            ps.setInt(5, p.getClient().getId_user()); // Assuming User has a getIdUser method
            ps.executeUpdate();
            System.out.println("Avis ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Avis p) {
        String req = "UPDATE avis SET commentaire=?, date_experience=?, note=?, id_oeuvre=?, id_user=? WHERE id_avis=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getCommentaire());
            ps.setDate(2, p.getDateExperience());
            ps.setInt(3, p.getNote());
            ps.setInt(4, p.getOeuvre().getId());
            ps.setInt(5, p.getClient().getId_user());
            ps.setInt(6, p.getIdAvis());
            ps.executeUpdate();
            System.out.println("Avis modifié avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM avis WHERE id_avis=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Avis supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Avis getOneById(int id) {

        String req = "SELECT * FROM avis WHERE id_avis=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String commentaire = res.getString("commentaire");
                Date dateExperience = res.getDate("date_experience");
                int note = res.getInt("note");
                int idOeuvre = res.getInt("id_oeuvre");
                int idUser = res.getInt("id_user");

                ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
                Oeuvre oeuvre = serviceOeuvre.getOneById(idOeuvre);
                ServicePersonne servicePersonne = new ServicePersonne();
                User user = servicePersonne.getOneById(idUser);

                Avis avis = new Avis(id, commentaire, dateExperience, note,oeuvre,user );
                System.out.println("avis retrouvée !");
                System.out.println(avis.toString()); // Utilisation de la méthode toString
                return avis;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       return null;
    }

    @Override
    public Set<Avis> getAll() {
        Set<Avis> avisSet = new HashSet<>();
        String req = "SELECT * FROM avis";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                String commentaire = res.getString("commentaire");
                Date dateExperience = res.getDate("date_experience");
                int note = res.getInt("note");
                int idOeuvre = res.getInt("id_oeuvre");
                int idUser = res.getInt("id_user");

                ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
                Oeuvre oeuvre = serviceOeuvre.getOneById(idOeuvre);
                ServicePersonne servicePersonne = new ServicePersonne();
                User user = servicePersonne.getOneById(idUser);

                Avis avis = new Avis(commentaire,dateExperience, note,oeuvre,user);
                avisSet.add(avis);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return avisSet;
    }

}
