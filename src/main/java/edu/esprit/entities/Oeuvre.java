package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;
public class Oeuvre {
    private int id;
    private String nom;

    private String categorie;
    private float prix;

    private Date dateCreation;

    private String description;

    private String image;

    public Oeuvre(){}

    public Oeuvre(int id, String nom,String categorie, float prix, Date dateCreation, String description, String image) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.prix = prix;
        this.dateCreation = dateCreation;
        this.description= description;
        this.image = image;
    }
    public Oeuvre( String nom, String categorie, float prix, Date dateCreation, String description, String image) {

        this.nom = nom;
        this.categorie = categorie;
        this.prix = prix;
        this.dateCreation = dateCreation;
        this.description= description;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }


    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof Oeuvre that)) return false;
        return getId() == that.getId();
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
                "nom='" + nom + '\'' +
                ", categorie=" + categorie +
                ", prix=" + prix +
                ", dateCreation=" + dateCreation +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}






