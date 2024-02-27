package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {  public static void main(String[] args) {
    launch(args);
}
    @Override
    public void start(Stage primaryStage) throws IOException {
   //FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterReclamationUser.fxml"));
      // FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherReclamations.fxml"));
   //FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherRecBack.fxml"));
       //FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterComUser.fxml"));
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterComAdmin.fxml"));

        Parent root=loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ajout");
        primaryStage.show();
    }


}