package fr.esgi.simon.controller;

import fr.esgi.simon.business.Pad;
import fr.esgi.simon.service.PadService;
import fr.esgi.simon.service.SequenceService;
import fr.esgi.simon.service.impl.PadServiceImpl;
import fr.esgi.simon.service.impl.SequenceServiceImpl;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class JeuSimonController {
    private final PadService padService = new PadServiceImpl();
    private final SequenceService sequenceService = new SequenceServiceImpl(padService);
    private final List<Pad> sequenceUtilisateur = new ArrayList<>();
    private boolean utilisateurPeutJouer = false;

    @FXML
    private Button padRed, padGreen, padBlue, padYellow;

    @FXML
    private void handleStartGame() {
        sequenceService.initialiserSequence();
        sequenceUtilisateur.clear();
        utilisateurPeutJouer = false;
        jouerTour();
    }

    @FXML
    private void handlePadClick(javafx.event.ActionEvent event) {
        if (!utilisateurPeutJouer) return;

        Button source = (Button) event.getSource();
        Pad padClique = null;

        if (source == padRed) {
            padClique = padService.obtenirPadParCouleur("Rouge");
        } else if (source == padGreen) {
            padClique = padService.obtenirPadParCouleur("Vert");
        } else if (source == padBlue) {
            padClique = padService.obtenirPadParCouleur("Bleu");
        } else if (source == padYellow) {
            padClique = padService.obtenirPadParCouleur("Jaune");
        }

        if (padClique != null) {
            sequenceUtilisateur.add(padClique);
            animerBouton(source); // Animation du bouton
            verifierSequence();
        }
    }

    private void jouerTour() {
        utilisateurPeutJouer = false;
        sequenceUtilisateur.clear();
        sequenceService.allongerSequence();
        afficherSequence(sequenceService.obtenirSequenceActuelle());
    }

    private void afficherSequence(List<Pad> sequence) {
        new Thread(() -> {
            try {
                for (Pad pad : sequence) {
                    highlightButton(pad);
                    Thread.sleep(500);
                    clearHighlights();
                    Thread.sleep(200);
                }
                utilisateurPeutJouer = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void highlightButton(Pad pad) {
        if (pad.getCouleur().equalsIgnoreCase("Rouge")) {
            padRed.setStyle("-fx-background-color: lightcoral;");
        } else if (pad.getCouleur().equalsIgnoreCase("Vert")) {
            padGreen.setStyle("-fx-background-color: lightgreen;");
        } else if (pad.getCouleur().equalsIgnoreCase("Bleu")) {
            padBlue.setStyle("-fx-background-color: lightblue;");
        } else if (pad.getCouleur().equalsIgnoreCase("Jaune")) {
            padYellow.setStyle("-fx-background-color: lightyellow;");
        }
    }

    private void clearHighlights() {
        padRed.setStyle("-fx-background-color: red;");
        padGreen.setStyle("-fx-background-color: green;");
        padBlue.setStyle("-fx-background-color: blue;");
        padYellow.setStyle("-fx-background-color: yellow;");
    }

    private void verifierSequence() {
        int tailleActuelle = sequenceUtilisateur.size();
        List<Pad> sequenceAttendue = sequenceService.obtenirSequenceActuelle();

        // Vérifie chaque élément de la séquence utilisateur contre la séquence attendue
        for (int i = 0; i < tailleActuelle; i++) {
            if (!sequenceUtilisateur.get(i).equals(sequenceAttendue.get(i))) {
                afficherMessage("Incorrect ! Fin du jeu.", Alert.AlertType.ERROR);
                reinitialiserJeu();
                return;
            }
        }

        // Si la séquence utilisateur correspond complètement à la séquence attendue
        if (tailleActuelle == sequenceAttendue.size()) {
            afficherMessage("Correct ! Niveau suivant.", Alert.AlertType.INFORMATION);
            jouerTour();
        }
    }


    private void reinitialiserJeu() {
        sequenceService.initialiserSequence();
        sequenceUtilisateur.clear();
        utilisateurPeutJouer = false;
    }

    private void afficherMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    private void animerBouton(Button button) {
        ScaleTransition agrandir = new ScaleTransition(Duration.millis(200), button);
        agrandir.setToX(1.2); // Agrandir horizontalement de 20 %
        agrandir.setToY(1.2); // Agrandir verticalement de 20 %

        ScaleTransition reduire = new ScaleTransition(Duration.millis(200), button);
        reduire.setToX(1.0); // Retour à la taille normale
        reduire.setToY(1.0);

        agrandir.setOnFinished(event -> reduire.play()); // Réduit le bouton après l'agrandissement
        agrandir.play(); // Démarre l'animation
    }
}
