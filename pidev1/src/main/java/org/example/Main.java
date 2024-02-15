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





                     //AJOUTatelier
        ServiceAtelier sc=new ServiceAtelier();
        // Création d'une instance de Cour avec un ID
        Cour c = new Cour();
        c.setId_cours(1);

        // Création d'une instance d'Atelier avec les informations nécessaires
        Atelier a1 = new Atelier( c, currentDate, currentDate, "omar123");



        // Appel de la méthode ajouter pour ajouter l'atelier à la base de données
        sc.ajouter(a1);}}



                        //AJOUTcour
       /* ServiceCour sc=new ServiceCour();
        User u = new User();
        u.setId_user(1);
        Cour c1=new Cour("kj", "mkk", currentDate, currentDate,u);
        sc.ajouter(c1);}}

        /*

                //SUPPRIMER
      /*  ServiceCour sc=new ServiceCour();
        int idrsupprimer=7;
        sc.supprimer(idrsupprimer);}}*/

/*
        //AFFICHER PAR ID cour
        ServiceCour sc = new ServiceCour();
        System.out.println(sc.getOneById(1));
        ;


        /*
        //MODIFIERCours
        Cour coursAModifier = sc.getOneById(1); // Supposons que l'ID du cours à modifier est 1
        coursAModifier.setTitre_cours("mathematique ");
        coursAModifier.setDescription_cours("difficile");
        sc.modifier(coursAModifier);

*/   //   ServiceCour sc = new ServiceCour();
        // System.out.println(sc.getAll());


       // ServiceAtelier serviceAtelier = new ServiceAtelier();

        // Création d'une instance de Cour pour associer à l'atelier
       /* Cour cour = new Cour();
        cour.setId_cours(1); // Supposons que l'ID du cours soit 1


        // Modification de l'atelier
        Atelier atelierAModifier = serviceAtelier.getOneById(1); // Supposons que l'ID de l'atelier à modifier soit 1
        atelierAModifier.setDateDebut_atelier(new java.util.Date()); // Nouvelle date de début de l'atelier
        atelierAModifier.setLien("heyyyy"); // Nouveau lien de l'atelier
        serviceAtelier.modifier(atelierAModifier);

/*
        //SUPPRIMERATELIER
        ServiceAtelier se=new ServiceAtelier();
        int idrsupprimer=1;
        se.supprimer(idrsupprimer);

        //ServiceAtelier se = new ServiceAtelier();
        //System.out.println(se.getAll());
//AFFICHER PAR ID atelier
       // ServiceAtelier se = new ServiceAtelier();
        //System.out.println(se.getOneById(5));
        ;

    }}*/