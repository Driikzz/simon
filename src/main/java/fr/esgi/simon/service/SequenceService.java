package fr.esgi.simon.service;

import fr.esgi.simon.business.Pad;

import java.util.List;

public interface SequenceService {
    void initialiserSequence(); // Réinitialise la séquence au début du jeu
    List<Pad> obtenirSequenceActuelle(); // Retourne la séquence actuelle
    void allongerSequence(); // Ajoute un Pad aléatoire à la séquence
}
