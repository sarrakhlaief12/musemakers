package edu.esprit.entities;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Exposition {
    private int id;
    private String nom;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private String description;
    private String theme;
    private String image;
    public Exposition(){}

    public Exposition(int id, String nom, Timestamp dateDebut, Timestamp dateFin, String description, String theme, String image) {
        this.id = id;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description =

                description;
        this.theme = theme;
        this.image = image;
    }
    public Exposition( String nom, Timestamp dateDebut, Timestamp dateFin, String description, String theme, String image) {

        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.theme = theme;
        this.image = image;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {

            this.nom = nom;

    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
            this.dateDebut = dateDebut;

    }
    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
            this.dateFin = dateFin;

    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exposition that)) return false;
        return getId() == that.getId();
    }

    @Override
    public String toString() {
        return "Exposition{" +
                "nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", description='" + description + '\'' +
                ", theme='" + theme + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

