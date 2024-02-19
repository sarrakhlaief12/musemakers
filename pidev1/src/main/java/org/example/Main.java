package org.example;

import entities.Atelier;
import entities.Cour;
import service.ServiceAtelier;
import service.ServiceCour;
import utils.DataSource;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        DataSource ds = DataSource.getInstance();
        System.out.println(ds);
        Date currentDate = new Date(System.currentTimeMillis());


        //AJOUTcour
       /* ServiceCour sc=new ServiceCour();
        User u = new User();
        u.setId_user(1);
        Cour c1=new Cour("kj", "mkk", currentDate, currentDate,u);
        sc.ajouter(c1);}}

        /*

                //SUPPRIMERCOUR
       ServiceCour sc=new ServiceCour();
        int idrsupprimer=6;
        sc.supprimer(idrsupprimer);





/*
                            //MODIFIERCours
        ServiceCour sc = new ServiceCour();
        Cour coursAModifier = sc.getOneById(1); // Supposons que l'ID du cours à modifier est 1
        coursAModifier.setTitre_cours(" donnees ");
        coursAModifier.setDescription_cours("diff");
        sc.modifier(coursAModifier);}}

        /*
                               /* AFFICHER COUR
  ServiceCour sc = new ServiceCour();
        System.out.println(sc.getAll());}}


                            //AFFICHER PAR ID cour
        ServiceCour sc = new ServiceCour();
        System.out.println(sc.getOneById(6));
        ;}}

                    //ARELIERS

                //AJOUTatelier
       /* ServiceAtelier sc=new ServiceAtelier();
        // Création d'une instance de Cour avec un ID
        Cour c = new Cour();
        c.setId_cours(6);

        // Création d'une instance d'Atelier avec les informations nécessaires
       Atelier a1 = new Atelier( c, currentDate, currentDate, "moul");

        // Appel de la méthode ajouter pour ajouter l'atelier à la base de données
        sc.ajouter(a1);}}

/*

                        //SUPPRIMERATELIER
ServiceAtelier se=new ServiceAtelier();
int idrsupprimer=26;
        se.supprimer(idrsupprimer);}}
/*
        // AFFICHERALL
ServiceAtelier se = new ServiceAtelier();
      System.out.println(se.getAll());}}
/*
        //AFFICHER PAR ID atelier
         ServiceAtelier se = new ServiceAtelier();
         System.out.println(se.getOneById(29));}}
/*
        //MODIFIERATELIERS
       ServiceAtelier serviceAtelier = new ServiceAtelier();

        // Création d'une instance de Cour pour associer à l'atelier
        Cour cour = new Cour();
        cour.setId_cours(1); // Supposons que l'ID du cours soit 1
        // Modification de l'atelier
        Atelier atelierAModifier = serviceAtelier.getOneById(26); // Supposons que l'ID de l'atelier à modifier soit 1
        atelierAModifier.setDateDebut_atelier(new java.util.Date()); // Nouvelle date de début de l'atelier
        atelierAModifier.setLien("heyyyy"); // Nouveau lien de l'atelier
        serviceAtelier.modifier(atelierAModifier);
*/


/*


        // Créez une instance de votre classe 'ServiceAtelier'
        ServiceAtelier  ServiceAtelier = new ServiceAtelier();
        ServiceCour ServiceCour = new ServiceCour();
        // Créez une instance de 'Cour' à modifier
        Cour Cour = new Cour();
        Cour.setId_cours(1); // Remplacez 1 par l'ID du cours que vous voulez utiliser

        // Créez une instance de 'Atelier' à modifier
        Atelier atelier = new Atelier(new Cour(), currentDate, currentDate, "lien");// Remplacez 1 par l'ID du cours que vous voulez utiliser
        atelier.setLien("monjiii");
        ServiceAtelier.modifier(atelier);
        // Appelez la méthode 'modifier'
      }
      /*
        ServiceAtelier  ServiceAtelier = new ServiceAtelier();
            Atelier atelier = new Atelier();
            atelier.setId_atelier(26);
            atelier.setCour(new Cour()); // Remplacez par une instance réelle de Cour
            atelier.setDateDebut_atelier(currentDate);
            atelier.setDateFin_atelier(currentDate);
            atelier.setLien("lien");

            // Afficher l'atelier avant la modification
            System.out.println("Avant la modification : " + atelier);

            // Créer une nouvelle instance de Atelier pour la modification
            Atelier nouvelAtelier = new Atelier();
            nouvelAtelier.setId_atelier(26);
            nouvelAtelier.setCour(new Cour()); // Remplacez par une nouvelle instance réelle de Cour
            nouvelAtelier.setDateDebut_atelier(currentDate);
            nouvelAtelier.setDateFin_atelier(currentDate);
            nouvelAtelier.setLien("nouveau lien");

            // Modifier l'atelier
            ServiceAtelier.modifier(nouvelAtelier);

            // Afficher l'atelier après la modification
            System.out.println("Après la modification : " + atelier);
        }

    }*/


    }}