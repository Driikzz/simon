package fr.esgi.simon.controller;

import fr.esgi.simon.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class ChoixJoueursController {

    @FXML
    private ComboBox<Integer> comboNombreJoueurs;

    @FXML
    public void initialize() {
        comboNombreJoueurs.getItems().addAll(2, 3, 4);
        comboNombreJoueurs.setValue(2);
    }

    @FXML
    private void handleValider() {
        Integer nombreJoueurs = comboNombreJoueurs.getValue();
        if (nombreJoueurs == null) {
            afficherErreur("Veuillez sélectionner un nombre de joueurs.");
            return;
        }

        try {
            SaisiePrenomsController.setNombreJoueurs(nombreJoueurs);
            App.setRoot("SaisiePrenoms");
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible de charger la fenêtre de saisie des prénoms.");
        }
    }

    @FXML
    private void handleRetour() {
        try {
            App.setRoot("Accueil");
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible de revenir à l'accueil.");
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
