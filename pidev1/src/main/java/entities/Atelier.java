package entities;

import java.util.Date;
import java.util.Objects;

public class Atelier {
    private int id_atelier ;
    private  Cour cour;

    private Date dateDebut_atelier;
    private Date dateFin_atelier ;

    private String lien_atelier;

    public Atelier () {
    }

    public Atelier(int id_atelier, Cour cour, Date dateDebut_atelier, Date dateFin_atelier, String lien) {
        this.id_atelier = id_atelier;
        //this.cour = cour;
        this.dateDebut_atelier = dateDebut_atelier;
        this.dateFin_atelier = dateFin_atelier;
        this.lien_atelier = lien;
    }

    public Atelier(Cour cour, Date dateDebut_atelier, Date dateFin_atelier, String lien) {
        //this.cour = cour;
        this.dateDebut_atelier = dateDebut_atelier;
        this.dateFin_atelier = dateFin_atelier;
        this.lien_atelier = lien;
    }

    @Override
    public String toString() {
        return "Atelier{" +
                "id_atelier=" + id_atelier +
                ", cour=" + cour +
                ", dateDebut_atelier=" + dateDebut_atelier +
                ", dateFin_atelier=" + dateFin_atelier +
                ", lien_atelier='" + lien_atelier + '\'' +
                '}';
    }

    public int getId_atelier() {
        return id_atelier;
    }

    public void setId_atelier(int id_atelier) {
        this.id_atelier = id_atelier;
    }
    /*public int getId_cours() {
        return cour.getId_cours();
    }

    public void setId_cours(Cour C) {
        this.cour = C;
    }*/

    public Cour getCour() {
        return cour;
    }

    public void setCour(Cour cour) {
        this.cour = cour;
    }

    public Date getDateDebut_atelier() {
        return dateDebut_atelier;
    }

    public void setDateDebut_atelier(Date dateDebut_atelier) {
        this.dateDebut_atelier = dateDebut_atelier;
    }

    public Date getDateFin_atelier() {
        return dateFin_atelier;
    }

    public void setDateFin_atelier(Date dateFin_atelier) {
        this.dateFin_atelier = dateFin_atelier;
    }

    public String getLien() {
        return lien_atelier;
    }

    public void setLien(String lien) {
        this.lien_atelier = lien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atelier atelier = (Atelier) o;
        return id_atelier == atelier.id_atelier && Objects.equals(cour, atelier.cour) && Objects.equals(dateDebut_atelier, atelier.dateDebut_atelier) && Objects.equals(dateFin_atelier, atelier.dateFin_atelier) && Objects.equals(lien_atelier, atelier.lien_atelier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_atelier, cour, dateDebut_atelier, dateFin_atelier, lien_atelier);
    }
}
