package edu.esprit.controllers;

import edu.esprit.entities.Exposition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;

public class DetailExposition {


        @FXML
        private Label nameLabel;

        @FXML
        private Label dateLabel;

        @FXML
        private Label themeLabel;

        @FXML
        private Label descriptionLabel;



        private String formatDate(java.util.Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }

    public void setExpositionDetails(Exposition exposition) {
        nameLabel.setText("Nom: " + exposition.getNom());
        dateLabel.setText("Date: " + formatDate(exposition.getDateDebut()) + " - " + formatDate(exposition.getDateFin()));
        themeLabel.setText("Thème: " + exposition.getTheme());
        descriptionLabel.setText("Description: " + exposition.getDescription());
    }
    }


