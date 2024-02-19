package edu.esprit.services;

import edu.esprit.entities.Exposition;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ServiceExposition implements IService<Exposition>  {
    Connection cnx= DataSource.getInstance().getCnx();
    private boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }
    private boolean validateExpositionDates(Exposition exp) {
        LocalDate today = LocalDate.now();
        if (exp.getDateDebut() != null && exp.getDateDebut().before(Timestamp.valueOf(today.atStartOfDay()))) {
            System.out.println("Erreur : La date de début ne peut pas être inférieure à la date d'aujourd'hui.");
            return false;
        }
        if (exp.getDateDebut() != null && exp.getDateFin() != null && exp.getDateDebut().after(exp.getDateFin())) {
            System.out.println("Erreur : La date de fin doit être après la date de début.");
            return false;
        }
        return true;
    }


    @Override
    public void ajouter(Exposition exp)throws SQLException {
        if (validateExpositionDates(exp)) {

            if (!isValidString(exp.getNom()) || !isValidString(exp.getTheme()) || !isValidString(exp.getImage())) {
                System.out.println("Erreur : Le nom, le thème et l'image de l'exposition doivent être valides.");
                return;  // exit the method if the condition is not met
            }

            String req = "INSERT INTO `exposition` (nom, Date_debut, Date_fin, Description, Theme, image) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, exp.getNom());
                ps.setTimestamp(2, exp.getDateDebut());
                ps.setTimestamp(3, exp.getDateFin());
                ps.setString(4, exp.getDescription());
                ps.setString(5, exp.getTheme());
                ps.setString(6, exp.getImage());
                ps.executeUpdate();
                System.out.println("exposition added successfully!");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }}
    @Override
    public void modifier(Exposition exp)throws SQLException {
        if (validateExpositionDates(exp)) {


            if (!isValidString(exp.getNom()) || !isValidString(exp.getTheme()) || !isValidString(exp.getImage())) {
                System.out.println("Erreur : Le nom, le thème et l'image de l'exposition doivent être valides.");
                return;
            }
            String req = "UPDATE exposition SET nom=?, Date_debut=?, Date_fin=?, Description=?, Theme=?, image=? WHERE id_exposition=?";
            try{
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, exp.getNom());
                ps.setTimestamp(2, exp.getDateDebut());
                ps.setTimestamp(3, exp.getDateFin());
                ps.setString(4, exp.getDescription());
                ps.setString(5, exp.getTheme());
                ps.setString(6, exp.getImage());
                ps.setInt(7, exp.getId());
                int line_tomodify = ps.executeUpdate();
                if(line_tomodify>0){
                    System.out.println("exposition modifié  !");}
                else {
                    System.out.println("exposition with ID " + exp.getId() + " does not exist!");
                }

            }catch (SQLException e) {
                System.out.println(e.getMessage());

            }

        }}

    @Override
    public void supprimer(int id)throws SQLException {
        String req = "DELETE FROM exposition WHERE id_exposition=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(req);
            ps.setInt(1,id);
            int line_todelete=ps.executeUpdate();
            if (line_todelete>0){
                System.out.println("Exposition deleted successfully!");
            }
            else {
                System.out.println("Exposition with ID " + id + " does not exist!");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    @Override
    public Exposition getOneById(int id)throws SQLException {
        String req = "SELECT * FROM exposition WHERE id_exposition=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id); //  the value for the placeholder
            ResultSet res = ps.executeQuery(); // Execution ta3 prepared statement

            if (res.next()) {
                String nom = res.getString("nom");
                Timestamp dateDebut = res.getTimestamp("Date_debut");
                Timestamp dateFin = res.getTimestamp("Date_fin");
                String description = res.getString("Description");
                String theme = res.getString("Theme");
                String image = res.getString("image");
                System.out.println("exposition mijouda  !");
                return new Exposition(id, nom, dateDebut, dateFin, description, theme, image);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    @Override
    public Set<Exposition> getAll()throws SQLException{
        Set<Exposition> expositions=new HashSet<>();
        String req="SELECT * FROM exposition";
        try{
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while(res.next()){
                int id = res.getInt(1);
                String nom=res.getString(2);
                Timestamp date_debut=res.getTimestamp(3);
                Timestamp date_fin=res.getTimestamp(4);
                String description=res.getString(5);
                String theme=res.getString(6);
                String image=res.getString(7);
                Exposition exp = new Exposition(id, nom, date_debut, date_fin, description,theme, image);
                expositions.add(exp);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expositions;
    }
    public Set<Exposition> chercherParThemeOuNom(String theme, String nom)throws SQLException {
        Set<Exposition> result = new HashSet<>();
        String req = "SELECT * FROM exposition WHERE Theme LIKE ? AND nom LIKE ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, "%" + theme + "%");
            ps.setString(2, "%" + nom + "%");
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                int id = res.getInt(1);
                String expositionNom = res.getString(2);
                Timestamp dateDebut = res.getTimestamp(3);
                Timestamp dateFin = res.getTimestamp(4);

                String description = res.getString(5);
                String expositionTheme = res.getString(6);
                String image = res.getString(7);

                Exposition exp = new Exposition(id, expositionNom, dateDebut, dateFin, description, expositionTheme, image);
                result.add(exp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }






}