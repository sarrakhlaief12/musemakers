package edu.esprit.services;

import edu.esprit.entities.Oeuvre;

import edu.esprit.utils.DataSource;

import java.sql.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ServiceOeuvre implements IService<Oeuvre>  {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Oeuvre o) {
        String req = "INSERT INTO `oeuvre` (nom_oeuvre, categorie_oeuvre, prix_oeuvre, datecreation, description, image) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getCategorie());
            ps.setFloat(3, o.getPrix());
            ps.setDate(4, o.getDateCreation());
            ps.setString(5, o.getDescription());
            ps.setString(6, o.getImage());
            ps.executeUpdate();
            System.out.println("Oeuvre added succesfully !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Oeuvre o) {
        String req = "UPDATE oeuvre SET nom_oeuvre=?, categorie_oeuvre=?, prix_oeuvre=?, datecreation=?, description=?, image=? WHERE id_oeuvre=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getCategorie());
            ps.setFloat(3, o.getPrix());
            ps.setDate(4, o.getDateCreation());
            ps.setString(5, o.getDescription());
            ps.setString(6, o.getImage());
            ps.setInt(7, o.getId());
            ps.executeUpdate();
            System.out.println("Oeuvre modifiée avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM oeuvre WHERE id_oeuvre=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Oeuvre supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Oeuvre getOneById(int id) {
        String req = "SELECT * FROM oeuvre WHERE id_oeuvre=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id); //  the value for the placeholder
            ResultSet res = ps.executeQuery(); // Execution of prepared statement

            if (res.next()) {
                String nom = res.getString("nom_oeuvre");
                String categorie = res.getString("categorie_oeuvre");
                float prix = res.getFloat("prix_oeuvre");
                Date dateCreation = res.getDate("datecreation");
                String description = res.getString("description");
                String image = res.getString("image");
                Oeuvre oeuvre = new Oeuvre(id, nom, categorie, prix, dateCreation, description, image);
                System.out.println("Oeuvre retrouvée !");
                System.out.println(oeuvre.toString()); // Utilisation de la méthode toString
                return oeuvre;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Set<Oeuvre> getAll() {
        Set<Oeuvre> oeuvres = new HashSet<>();
        String req = "SELECT * FROM oeuvre";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt("id_oeuvre");
                String nom = res.getString("nom_oeuvre");
                String categorie = res.getString("categorie_oeuvre");
                float prix = res.getFloat("prix_oeuvre");
                Date dateCreation = res.getDate("datecreation");
                String description = res.getString("description");
                String image = res.getString("image");
                Oeuvre oeuvre = new Oeuvre(id, nom, categorie, prix, dateCreation, description, image);
                oeuvres.add(oeuvre);
                System.out.println(oeuvre.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return oeuvres;
    }
}
