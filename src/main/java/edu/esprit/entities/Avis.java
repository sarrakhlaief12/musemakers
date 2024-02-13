package edu.esprit.entities;

import java.sql.Date;
public class Avis {

    private int idAvis;
    private String commentaire;
    private Date dateExperience;
    private int note;
    // Foreign key
    private Oeuvre oeuvre;
    private User client;

    public Avis(){
    }

    public Avis(int idAvis,String commentaire, Date dateExperience, int note, Oeuvre oeuvre, User client) {
        this.idAvis = idAvis;
        this.commentaire = commentaire;
        this.dateExperience = dateExperience;
        this.note = note;
        this.oeuvre = oeuvre;
        this.client = client;
    }

    public Avis(String commentaire, Date dateExperience, int note, Oeuvre oeuvre, User client) {
        this.commentaire = commentaire;
        this.dateExperience = dateExperience;
        this.note = note;
        this.oeuvre = oeuvre;
        this.client = client;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateExperience() {
        return dateExperience;
    }

    public void setDateExperience(Date dateExperience) {
        this.dateExperience = dateExperience;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avis that)) return false;
        return getIdAvis() == that.getIdAvis();
    }

    @Override
    public String toString() {
        return "Avis{" +
                "commentaire='" + commentaire + '\'' +
                ", dateExperience=" + dateExperience +
                ", note=" + note +
                ", oeuvre=" + oeuvre +
                ", User=" + client +
                '}';
    }


}
