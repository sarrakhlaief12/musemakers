package entities;

import java.util.Date;

public class Cour {
    private int id_cours;
    private String titre_cours;
    private String description_cours;
        private Date dateDebut_cours;
    private Date dateFin_cours;
    private int id_user;

    public Cour() {
    }

    public Cour(int id_cours, String titre_cours, String description_cours, Date dateDebut_cours, Date dateFin_cours, int id_user) {
        this.id_cours = id_cours;
        this.titre_cours = titre_cours;
        this.description_cours = description_cours;
        this.dateDebut_cours = dateDebut_cours;
        this.dateFin_cours = dateFin_cours;
        this.id_user = id_user;
    }

    public Cour(String titre_cours, String description_cours, Date dateDebut_cours, Date dateFin_cours, int id_user) {
        this.titre_cours = titre_cours;
        this.description_cours = description_cours;
        this.dateDebut_cours = dateDebut_cours;
        this.dateFin_cours = dateFin_cours;
        this.id_user = id_user;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public String getTitre_cours() {
        return titre_cours;
    }

    public void setTitre_cours(String titre_cours) {
        this.titre_cours = titre_cours;
    }

    public String getDescription_cours() {
        return description_cours;
    }

    public void setDescription_cours(String description_cours) {
        this.description_cours = description_cours;
    }

    public Date getDateDebut_cours() {
        return dateDebut_cours;
    }

    public void setDateDebut_cours(Date dateDebut_cours) {
        this.dateDebut_cours = dateDebut_cours;
    }

    public Date getDateFin_cours() {
        return dateFin_cours;
    }

    public void setDateFin_cours(Date dateFin_cours) {
        this.dateFin_cours = dateFin_cours;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Cour{" +
                "id_cours=" + id_cours +
                ", titre_cours='" + titre_cours + '\'' +
                ", description_cours='" + description_cours + '\'' +
                ", dateDebut_cours=" + dateDebut_cours +
                ", dateFin_cours=" + dateFin_cours +
                ", id_user=" + id_user +
                '}';
    }
}
