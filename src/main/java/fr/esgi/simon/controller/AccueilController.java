package fr.esgi.simon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import fr.esgi.simon.App;

import java.io.IOException;

public class AccueilController {

    @FXML
    public void handleModeSolo() {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible de charger le mode solo.");
        }
    }

    @FXML
    public void handleModeMultiJoueurs() {
        try {
            App.setRoot("ChoixJoueurs");
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible de charger le mode multi-joueurs.");
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
