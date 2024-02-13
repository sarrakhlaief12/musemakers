package org.example;

import entities.Atelier;
import entities.Cour;
import entities.User;
import service.ServiceAtelier;
import service.ServiceCour;
import utils.DataSource;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        DataSource ds = DataSource.getInstance();
        System.out.println(ds);
        Date currentDate = new Date(System.currentTimeMillis());




/*
                        //AJOUTatelier
        ServiceAtelier sc=new ServiceAtelier();
        // Création d'une instance de Cour avec un ID
        Cour c = new Cour();
        c.setId_cours(4);

        // Création d'une instance d'Atelier avec les informations nécessaires
        Atelier a1 = new Atelier( c, currentDate, currentDate, "lien_atelier");

        // Appel de la méthode ajouter pour ajouter l'atelier à la base de données
        sc.ajouter(a1);

 */
/*
                        //AJOUTcour
        ServiceCour sc=new ServiceCour();
        User u = new User();
        u.setId_user(1);
        Cour c1=new Cour("b", "Descri ", currentDate, currentDate,u);
        sc.ajouter(c1);

        /*

                //SUPPRIMER
        int idrsupprimer=2;
        sc.supprimer(idrsupprimer);
        */

/*
        //AFFICHER PAR ID
        ServiceCour sc = new ServiceCour();
        System.out.println(sc.getOneById(1));
        ;

    }

        /*
        //MODIFIER
        Cour coursAModifier = sc.getOneById(1); // Supposons que l'ID du cours à modifier est 1
        coursAModifier.setTitre_cours("mathematique ");
        coursAModifier.setDescription_cours("difficile");
        sc.modifier(coursAModifier);

*/
        //System.out.println(sc.getAll());




    }
}