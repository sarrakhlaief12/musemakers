package org.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

    public class MainFx extends Application {
        public static void main(String[] args) {
        launch(args);
    }
        @Override
        public void start(Stage primaryStage) throws IOException {


           // FXMLLoader loader= new FXMLLoader(getClass().getResource("//AfficherCourNV.fxml"));
           // Parent root= loader.load(getClass().getResource("/AfficherCourNV.fxml"));
           // FXMLLoader loader= new FXMLLoader(getClass().getResource("//AfficherCourFront.fxml"));
           // Parent root= loader.load(getClass().getResource("/AfficherCourFront.fxml"));
            // FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherCourFrontNV.fxml"));
             //Parent root= loader.load(getClass().getResource("/AfficherCourFrontNV.fxml"));



            Parent root=FXMLLoader.load(getClass().getResource("/AjouterCourNV.fxml"));

           // Parent root=FXMLLoader.load(getClass().getResource("/AjouterAtelierNV.fxml"));



           // FXMLLoader loader= new FXMLLoader(getClass().getResource("//AjouterAtelier.fxml"));
            //Parent root=loader.load(getClass().getResource("/AjouterAtelier.fxml"));
          //  FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherAtelierNV.fxml"));
           // Parent root=loader.load(getClass().getResource("/AfficherAtelierNV.fxml"));
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("ajout");
            primaryStage.show();
        }


    }

