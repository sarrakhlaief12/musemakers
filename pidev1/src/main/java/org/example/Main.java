package org.example;

import entities.Cour;
import entities.User;
import service.ServiceCour;
import utils.DataSource;

import java.sql.Date;

public class Main {
    public static void main(String[] args)
    {
        DataSource ds = DataSource.getInstance();
        System.out.println(ds);
        Date currentDate = new Date(System.currentTimeMillis());
        ServiceCour sc=new ServiceCour();
        /*
                        //AJOUT
        User u = new User();
        u.setId_user(1);
        Cour c1=new Cour("rania", "Descri rania", currentDate, currentDate,u);
        sc.ajouter(c1);
        */

        /*

                //SUPPRIMER
        int idrsupprimer=2;
        sc.supprimer(idrsupprimer);
        */

/*
        //AFFICHER PAR ID
        System.out.println(sc.getOneById(1));
        System.out.println(sc.getOneById(4));


        */
        //MODIFIER
        Cour coursAModifier = sc.getOneById(1); // Supposons que l'ID du cours à modifier est 1
        coursAModifier.setTitre_cours("mathematique ");
        coursAModifier.setDescription_cours("difficile");
        sc.modifier(coursAModifier);


        //System.out.println(sc.getAll());


    }
}