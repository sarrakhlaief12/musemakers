package org.example;

import entities.Reclamation;
import entities.User;
import sevice.ReclamationService;

import java.util.Date;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ReclamationService service = new ReclamationService();

        try {
            // Créer un nouvel utilisateur et une nouvelle réclamation
            User user = new User(3, "Nom", "Prenom", "email@example.com", "motdepasse", 1234567890, new Date(), "cartepro", "role");
            Reclamation r = new Reclamation(user, "description", new Date(), "categorie", "statut");

            // Tester la méthode 'ajouter'
            service.ajouter(r);
            System.out.println("Reclamation ajoutée: " + r);

            // Tester la méthode 'modifier'
            r.setDescriRec("nouvelle description");
            service.modifier(r);
            System.out.println("Reclamation modifiée: " + r);

            // Tester la méthode 'getAll'
            Set<Reclamation> all = service.getAll();
            System.out.println("Toutes les réclamations: " + all);

            // Tester la méthode 'getOneById'
            Reclamation r2 = service.getOneById(r.getIdRec());
            System.out.println("Reclamation obtenue par ID: " + r2);

            // Tester la méthode 'supprimer'
            service.supprimer(r.getIdRec());
            System.out.println("Reclamation supprimée: " + r);
        } catch (NullPointerException e) {
            System.out.println("Une exception NullPointerException a été levée.");
            e.printStackTrace();
        }
    }
}

