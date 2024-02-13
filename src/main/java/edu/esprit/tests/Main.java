package edu.esprit.tests;
import edu.esprit.entities.Exposition;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceExposition;
import edu.esprit.services.ServicePersonne;
import edu.esprit.services.ServiceReservation;
import edu.esprit.utils.DataSource;

import java.security.Provider;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Main {
    
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        dataSource.getCnx();

        ServicePersonne personne=new ServicePersonne();
        ServiceExposition exposition=new ServiceExposition();
        ServiceReservation reservation=new ServiceReservation();

        Exposition e=new Exposition();
        User p = new User();

        int num_exp=9;
        int num_user=5;


        ////////RESERVATION//////

        //Exposition exp_add_reser=exposition.getOneById(num_exp);
        //User user_add_rese=personne.getOneById(num_user);


//        Reservation r = new Reservation(Timestamp.valueOf(LocalDateTime.now()),5,true,exp_add_reser,user_add_rese);
//        reservation.ajouter(r);
       // Reservation r1 = new Reservation(8,Timestamp.valueOf(LocalDateTime.now()),20,true,exp_add_reser,user_add_rese);
       // reservation.modifier(r1);

       // System.out.println(reservation.getAll());
       // reservation.supprimer(30);
        //System.out.println(reservation.triparDateAncienne());


        ///////////////:EXPOSITION/::::::::::::::::::::::::::::


////////********ajouterrrrr exposition********////////
//        Exposition exp=new Exposition("ancienne vague",Timestamp.valueOf(LocalDateTime.of(2022, 5, 1, 12, 0)),Timestamp.valueOf(LocalDateTime.of(2022, 5, 2, 12, 0)),"cest .............","nostalgie","/images/art/comique");
//        exposition.ajouter(exp);
//        Exposition exp1=new Exposition(4,"ancienne vague",Timestamp.valueOf(LocalDateTime.of(2022, 5, 1, 12, 0)),Timestamp.valueOf(LocalDateTime.of(2022, 5, 2, 12, 0)),"cest .............","nostalgie","/images/art/comique");
//        exposition.modifier(exp1);
        //exposition.supprimer(10);
       // System.out.println(reservation.getAll());

       // System.out.println(exposition.chercherParThemeOuNom("","limoncello"));





       // System.out.println(personne.getAll());
        //System.out.println(personne.getOneById(5));
    }
    }
