package fr.esgi.simon.controller;

import fr.esgi.simon.business.Pad;
import fr.esgi.simon.service.PadService;
import fr.esgi.simon.service.SequenceService;
import fr.esgi.simon.service.impl.PadServiceImpl;
import fr.esgi.simon.service.impl.SequenceServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class PrimaryController {
    private final PadService padService = new PadServiceImpl();
    private final SequenceService sequenceService = new SequenceServiceImpl(padService);
    private List<Pad> sequenceUtilisateur = new ArrayList<>();
    private int niveau = 1;

    @FXML
    private Button padRed, padGreen, padBlue, padYellow;

    @FXML
    private void handleStartGame() {
        niveau = 1;
        sequenceService.initialiserSequence(); // Réinitialise la séquence au début
        sequenceUtilisateur.clear();
        jouerTour();
    }

    @FXML
    private void handlePadClick(javafx.event.ActionEvent event) {
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
            verifierSequence();
        }
    }

    private void jouerTour() {
        sequenceService.allongerSequence(); // Ajoute un nouvel élément à la séquence
        afficherSequence(sequenceService.obtenirSequenceActuelle());
    }

    private void afficherSequence(List<Pad> sequence) {
        // Simule une séquence en affichant les couleurs à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        StringBuilder message = new StringBuilder("Séquence : ");
        for (Pad pad : sequence) {
            message.append(pad.getCouleur()).append(" ");
        }
        alert.setContentText(message.toString());
        alert.showAndWait();
    }

    private void verifierSequence() {
        if (sequenceUtilisateur.size() == sequenceService.obtenirSequenceActuelle().size()) {
            if (sequenceUtilisateur.equals(sequenceService.obtenirSequenceActuelle())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Correct ! Niveau suivant.");
                alert.showAndWait();
                niveau++;
                sequenceUtilisateur.clear();
                jouerTour(); // Passe au niveau suivant
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect ! Fin du jeu.");
                alert.showAndWait();
            }
        }
    }
}
