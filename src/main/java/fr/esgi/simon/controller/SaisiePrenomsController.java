package fr.esgi.simon.controller;

import fr.esgi.simon.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaisiePrenomsController {

    private static int nombreJoueurs; // Nombre de joueurs transmis depuis la fenêtre précédente
    private final List<String> joueurs = new ArrayList<>(); // Liste des noms des joueurs

    @FXML
    private VBox joueursContainer;

    public static void setNombreJoueurs(int nombre) {
        nombreJoueurs = nombre;
    }

    @FXML
    public void initialize() {
        // Génère dynamiquement les noms des joueurs et les affiche
        for (int i = 1; i <= nombreJoueurs; i++) {
            String joueur = "Joueur " + i;
            joueurs.add(joueur);
            Label label = new Label(joueur);
            label.setStyle("-fx-font-size: 16px;");
            joueursContainer.getChildren().add(label);
        }
    }

    @FXML
    private void handleDemarrer() {
        try {
            JeuSimonController.setJoueurs(joueurs);
            App.setRoot("Primary");
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible de démarrer la partie.");
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
