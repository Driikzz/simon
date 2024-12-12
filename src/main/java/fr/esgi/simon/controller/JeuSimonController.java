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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class JeuSimonController {

    private final PadService padService = new PadServiceImpl();
    private final SequenceService sequenceService = new SequenceServiceImpl(padService);
    private final List<Pad> sequenceUtilisateur = new ArrayList<>();
    private boolean utilisateurPeutJouer = false;

    private static List<String> joueurs = new ArrayList<>(); // Liste des joueurs
    private final List<Integer> scores = new ArrayList<>(); // Scores des joueurs
    private int indexJoueurActuel = 0; // Index du joueur en cours
    private static boolean modeSolo = true; // Mode par défaut : solo

    @FXML
    private Button padRed, padGreen, padBlue, padYellow;

    @FXML
    private Label labelNomJoueur, labelScoreJoueur; // Affichage du joueur et de son score

    @FXML
    private ListView<String> listScores; // Affichage des scores de tous les joueurs

    /**
     * Définit le mode solo.
     *
     * @param solo true pour solo, false pour multi-joueurs
     */
    public static void setModeSolo(boolean solo) {
        modeSolo = solo;
        if (solo) {
            joueurs.clear();
            joueurs.add("Joueur 1");
        }
    }

    /**
     * Définit les joueurs pour le mode multi-joueurs.
     *
     * @param joueursListe Liste des noms des joueurs
     */
    public static void setJoueurs(List<String> joueursListe) {
        modeSolo = false;
        joueurs = joueursListe;
    }

    /**
     * Initialise la partie.
     */
    @FXML
    public void initialize() {
        // Initialise les scores pour chaque joueur
        for (int i = 0; i < joueurs.size(); i++) {
            scores.add(0);
        }
        mettreAJourScores();
        afficherNomJoueur();
        handleStartGame();
    }

    /**
     * Démarre la partie.
     */
    @FXML
    private void handleStartGame() {
        sequenceService.initialiserSequence();
        sequenceUtilisateur.clear();
        utilisateurPeutJouer = false;
        jouerTour();
    }

    /**
     * Gestion du clic sur un bouton (Pad).
     *
     * @param event L'événement du clic
     */
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
            animerBouton(source);
            verifierSequence();
        }
    }

    /**
     * Lance le tour actuel.
     */
    private void jouerTour() {
        utilisateurPeutJouer = false;
        sequenceUtilisateur.clear();
        sequenceService.allongerSequence();
        afficherSequence(sequenceService.obtenirSequenceActuelle());
    }

    /**
     * Affiche la séquence actuelle.
     *
     * @param sequence La séquence à afficher
     */
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

    /**
     * Met en évidence un bouton (Pad).
     *
     * @param pad Le Pad à mettre en évidence
     */
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

    /**
     * Réinitialise les styles des boutons.
     */
    private void clearHighlights() {
        padRed.setStyle("-fx-background-color: red;");
        padGreen.setStyle("-fx-background-color: green;");
        padBlue.setStyle("-fx-background-color: blue;");
        padYellow.setStyle("-fx-background-color: yellow;");
    }

    /**
     * Vérifie la séquence utilisateur.
     */
    private void verifierSequence() {
        int tailleActuelle = sequenceUtilisateur.size();
        List<Pad> sequenceAttendue = sequenceService.obtenirSequenceActuelle();

        for (int i = 0; i < tailleActuelle; i++) {
            if (!sequenceUtilisateur.get(i).equals(sequenceAttendue.get(i))) {
                afficherMessage("Incorrect ! " + (modeSolo ? "Fin du jeu." : "Passage au joueur suivant."), Alert.AlertType.ERROR);
                scores.set(indexJoueurActuel, scores.get(indexJoueurActuel) - 2);
                mettreAJourScores();
                if (!modeSolo) {
                    passerAuJoueurSuivant();
                } else {
                    reinitialiserJeu();
                }
                return;
            }
        }

        if (tailleActuelle == sequenceAttendue.size()) {
            afficherMessage("Correct ! Niveau suivant.", Alert.AlertType.INFORMATION);
            scores.set(indexJoueurActuel, scores.get(indexJoueurActuel) + 2);
            mettreAJourScores();
            if (!modeSolo) {
                passerAuJoueurSuivant();
            } else {
                jouerTour();
            }
        }
    }

    /**
     * Passe au joueur suivant.
     */
    private void passerAuJoueurSuivant() {
        indexJoueurActuel = (indexJoueurActuel + 1) % joueurs.size();
        afficherNomJoueur();
        jouerTour();
    }

    /**
     * Met à jour l'affichage des scores.
     */
    private void mettreAJourScores() {
        listScores.getItems().clear();
        for (int i = 0; i < joueurs.size(); i++) {
            listScores.getItems().add(joueurs.get(i) + ": " + scores.get(i) + " points");
        }
    }

    /**
     * Affiche le joueur en cours.
     */
    private void afficherNomJoueur() {
        labelNomJoueur.setText("À toi, " + joueurs.get(indexJoueurActuel));
        labelScoreJoueur.setText("Score : " + scores.get(indexJoueurActuel));
    }

    /**
     * Affiche un message d'alerte.
     *
     * @param message Le message à afficher
     * @param type    Le type de l'alerte
     */
    private void afficherMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Erreur" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Anime un bouton.
     *
     * @param button Le bouton à animer
     */
    private void animerBouton(Button button) {
        ScaleTransition agrandir = new ScaleTransition(Duration.millis(200), button);
        agrandir.setToX(1.2);
        agrandir.setToY(1.2);

        ScaleTransition reduire = new ScaleTransition(Duration.millis(200), button);
        reduire.setToX(1.0);
        reduire.setToY(1.0);

        agrandir.setOnFinished(event -> reduire.play());
        agrandir.play();
    }

    /**
     * Réinitialise le jeu.
     */
    private void reinitialiserJeu() {
        sequenceService.initialiserSequence();
        sequenceUtilisateur.clear();
        utilisateurPeutJouer = false;
    }
}
