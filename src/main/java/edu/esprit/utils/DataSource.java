package edu.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataSource {
        private String url="jdbc:mysql://localhost:3306/musemakers";
        private String user ="root";
        private String passwd="";
        private Connection cnx;
        //on a utulise le modele singleton
        private static DataSource instance; //static pour arreter plusieurs instanciations en  main

        private DataSource(){
            try {
                cnx = DriverManager.getConnection(url, user, passwd);
                System.out.println("connected to BD successfully");
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public  static DataSource getInstance(){
            if(instance==null){
                instance=new DataSource();
            }
            return instance;
        }
        public Connection getCnx(){
            return cnx;
        }
    }

