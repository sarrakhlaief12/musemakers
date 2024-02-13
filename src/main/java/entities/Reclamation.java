package entities;

import java.util.Date;
import java.util.Objects;

public class Reclamation {
    private int idRec ;
    private int idU;
    private String descriRec ;
    private Date DateRec ;
    private String CategorieRec ;
    private String StatutRec ;

    private int test = 0;
    public Reclamation() {

    }

    public Reclamation(int idRec, int idU, String descriRec, Date dateRec, String categorieRec, String statutRec) {
        this.idRec = idRec;
        this.idU = idU;
        this.descriRec = descriRec;
        DateRec = dateRec;
        CategorieRec = categorieRec;
        StatutRec = statutRec;
    }

    public Reclamation(int idU, String descriRec, Date dateRec, String categorieRec, String statutRec) {
        this.idU = idU;
        this.descriRec = descriRec;
        DateRec = dateRec;
        CategorieRec = categorieRec;
        StatutRec = statutRec;
    }

    public int getIdRec() {
        return idRec;
    }

    public int getIdU() {
        return idU;
    }

    public String getDescriRec() {
        return descriRec;
    }

    public Date getDateRec() {
        return DateRec;
    }

    public String getCategorieRec() {
        return CategorieRec;
    }

    public String getStatutRec() {
        return StatutRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public void setDescriRec(String descriRec) {
        this.descriRec = descriRec;
    }

    public void setDateRec(Date dateRec) {
        DateRec = dateRec;
    }

    public void setCategorieRec(String categorieRec) {
        CategorieRec = categorieRec;
    }

    public void setStatutRec(String statutRec) {
        StatutRec = statutRec;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idRec=" + idRec +
                ", idU=" + idU +
                ", descriRec=" + descriRec +
                ", DateRec=" + DateRec +
                ", CategorieRec=" + CategorieRec +
                ", StatutRec=" + StatutRec +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reclamation that)) return false;
        return getIdRec() == that.getIdRec() && Objects.equals(getStatutRec(), that.getStatutRec());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdRec(), getStatutRec());
    }
}
