package org.example;

import entities.Reclamation;
import sevice.ReclamationService;

import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Reclamation r1=new Reclamation(3,"ggg", new Date(),"probtechnique","non resolue");
        ReclamationService rs=new ReclamationService();
       // rs.ajouter(r1);




        // Créer une instance de Reclamation
         Reclamation reclamation = new Reclamation(35, 1, "bare", new java.sql.Date(System.currentTimeMillis()), "categorie", "statut");

        // Modifier la reclamation
        /*reclamation.setIdU( 3 );
       reclamation.setDescriRec(" rania ");
        reclamation.setDateRec(new Date());
        reclamation.setCategorieRec("nouvelle categorie");
        reclamation.setStatutRec("cou");

        // Créer une instance de votre classe qui contient la méthode modifier (par exemple, ReclamationService)


        // Appeler la méthode modifier
        rs.modifier(reclamation ); */
         /* int idrsupprimer=36;
        rs.supprimer(idrsupprimer); */

        System.out.println(rs.getOneById(35));
        //System.out.println(rs.getAll());
    }
}

