package org.example;

import entities.Cour;
import service.ServiceCour;
import utils.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        System.out.println("Hello world!");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        /*DataSource ds = DataSource.getInstance();
        System.out.println(ds);*/
        Date dateDebut = sdf.parse("11/02/2024");
        Date dateFin = sdf.parse("11/02/2024");
        /*java.sql.Date sqlDateDebut = new java.sql.Date(dateDebut.getTime());
        java.sql.Date sqlDateFin = new java.sql.Date(dateFin.getTime());*/
       /* Cour c1=new Cour("rania", "Descri rania", dateDebut, dateFin,1);
        ServiceCour sc=new ServiceCour();
        sc.ajouter(c1);*/
        ServiceCour sc=new ServiceCour();
/*
        int idrsupprimer=3;
        sc.supprimer(idrsupprimer);*/
        /*
        System.out.println(sc.getOneById(1));
        System.out.println(sc.getOneById(3));
    */
        System.out.println(sc.getAll());


    }
}