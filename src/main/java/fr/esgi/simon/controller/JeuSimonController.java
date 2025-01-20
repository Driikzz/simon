package fr.esgi.simon.controller;

import fr.esgi.simon.App;
import fr.esgi.simon.business.Pad;
import fr.esgi.simon.service.PadService;
import fr.esgi.simon.service.SequenceService;
import fr.esgi.simon.service.impl.PadServiceImpl;
import fr.esgi.simon.service.impl.SequenceServiceImpl;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class JeuSimonController {

    private final PadService padService = new PadServiceImpl();
    private final SequenceService sequenceService = new SequenceServiceImpl(padService);
    private final List<Pad> sequenceUtilisateur = new ArrayList<>();
    private boolean utilisateurPeutJouer = false;

    private static List<String> joueurs = new ArrayList<>();
    private final List<Integer> scores = new ArrayList<>();
    private int indexJoueurActuel = 0;
    private static boolean modeSolo = true;
    private int erreursConsecutives = 0;

    @FXML
    private Button padRed, padGreen, padBlue, padYellow;

    @FXML
    private Label labelNomJoueur, labelScoreJoueur;

    @FXML
    private ListView<String> listScores;

    public static void setModeSolo(boolean solo) {
        modeSolo = solo;
        if (solo) {
            joueurs.clear();
            joueurs.add("Joueur 1");
        }
    }

    public static void setJoueurs(List<String> joueursListe) {
        modeSolo = false;
        joueurs = joueursListe;
    }

    @FXML
    public void initialize() {
        if (joueurs == null || joueurs.isEmpty()) {
            joueurs = new ArrayList<>();
            joueurs.add("Joueur 1");
            modeSolo = true;

        }

        scores.clear();
        for (int i = 0; i < joueurs.size(); i++) {
            scores.add(0);
        }

        erreursConsecutives = 0;
        mettreAJourScores();
        afficherNomJoueur();
        handleStartGame();
    }

    @FXML
    private void handleStartGame() {
        sequenceService.initialiserSequence();
        sequenceUtilisateur.clear();
        utilisateurPeutJouer = false;
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
            // Lecture du son associé au pad
            jouerSon(padClique.getSon().getPath());

            sequenceUtilisateur.add(padClique);
            animerBouton(source);
            verifierSequence();
        }
    }

    private void jouerSon(String cheminSon) {
        try {
            // Vérifiez si le fichier est résolu
            var resource = getClass().getResource(cheminSon);
            if (resource == null) {
                throw new IllegalArgumentException("Fichier audio introuvable : " + cheminSon);
            }
            System.out.println("Chargement du fichier audio depuis : " + resource.toString());
            Media media = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du son : " + cheminSon + " - " + e.getMessage());
            System.out.println("Chemin requis : " + cheminSon);
            System.out.println("Chemin résolu : " + getClass().getResource(cheminSon));

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

        for (int i = 0; i < tailleActuelle; i++) {
            if (!sequenceUtilisateur.get(i).equals(sequenceAttendue.get(i))) {
                afficherMessage("Incorrect !", Alert.AlertType.ERROR);

                scores.set(indexJoueurActuel, scores.get(indexJoueurActuel) - 2);
                mettreAJourScores();

                // Si le joueur est éliminé
                if (scores.get(indexJoueurActuel) < 0) {
                    listScores.getItems().set(indexJoueurActuel, joueurs.get(indexJoueurActuel) + " (Éliminé)");
                    joueurs.set(indexJoueurActuel, null); // Marque le joueur comme éliminé
                }

                // Vérifie s'il reste au moins 2 joueurs actifs
                long joueursRestants = joueurs.stream().filter(joueur -> joueur != null).count();
                if (joueursRestants < 2) {
                    finDuJeu();
                    return;
                }

                if (modeSolo) {
                    jouerTour(); // Relance la séquence pour réessayer
                } else {
                    passerAuJoueurSuivant();
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



    private void passerAuJoueurSuivant() {
        do {
            indexJoueurActuel = (indexJoueurActuel + 1) % joueurs.size();
        } while (joueurs.get(indexJoueurActuel) == null); // Ignore les joueurs éliminés

        afficherNomJoueur();
        mettreAJourScores();
        jouerTour();
    }


    private void mettreAJourScores() {
        listScores.getItems().clear();
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i) != null) {
                listScores.getItems().add(joueurs.get(i) + ": " + scores.get(i) + " points");
            } else {
                listScores.getItems().add("Éliminé");
            }
        }

        // Met à jour l'affichage du score pour le joueur actuel
        if (joueurs.get(indexJoueurActuel) != null) {
            labelScoreJoueur.setText("Score : " + scores.get(indexJoueurActuel));
        }
    }


    private void afficherNomJoueur() {
        labelNomJoueur.setText("À toi, " + joueurs.get(indexJoueurActuel));
        labelScoreJoueur.setText("Score : " + scores.get(indexJoueurActuel));
    }

    private void afficherMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Erreur" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

    private void reinitialiserJeu() {
        sequenceService.initialiserSequence();
        sequenceUtilisateur.clear();
        utilisateurPeutJouer = false;
    }


    private void finDuJeu() {
        try {
            List<String> notesJouees = new ArrayList<>();
            for (Pad pad : sequenceService.obtenirSequenceActuelle()) {
                notesJouees.add(pad.getCouleur());
            }

            FXMLLoader loader = new FXMLLoader(App.class.getResource("FinJeu.fxml"));
            Parent root = loader.load();

            FinJeuController controller = loader.getController();
            if (modeSolo) {
                controller.setScore(scores.get(indexJoueurActuel));
            } else {
                controller.setScores(joueurs, scores);
            }
            controller.setNotesJouees(notesJouees);

            App.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
