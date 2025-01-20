package fr.esgi.simon.controller;

import fr.esgi.simon.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class FinJeuController {

    @FXML
    private ListView<String> listNotes;

    @FXML
    private ListView<String> listScores;

    @FXML
    private Label labelScore;

    private List<String> notesJouees;

    public void setScore(int score) {
        labelScore.setText("Score final : " + score);
    }

    public void setScores(List<String> joueurs, List<Integer> scores) {
        if (listScores == null) {
            throw new IllegalStateException("Le composant ListView listScores n'a pas été initialisé. Vérifiez FinJeu.fxml.");
        }

        listScores.getItems().clear();
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i) != null) {
                listScores.getItems().add(joueurs.get(i) + ": " + scores.get(i) + " points");
            } else {
                listScores.getItems().add("Éliminé");
            }
        }
    }

    public void setNotesJouees(List<String> notesJouees) {
        this.notesJouees = notesJouees;
        if (listNotes != null) {
            listNotes.getItems().setAll(notesJouees);
        }
    }

    @FXML
    private void handleReplay() {
        try {
            App.setRoot("Accueil");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePlayMelody() {
        new Thread(() -> {
            try {
                for (String note : notesJouees) {
                    System.out.println("Joue la note : " + note);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
