package fr.esgi.simon.service;

import fr.esgi.simon.business.Joueur;

import java.util.List;

public interface JoueurService {
    Joueur creerJoueur(String pseudo);
    Joueur obtenirJoueur(Long id);
    List<Joueur> listerTousLesJoueurs();
    void supprimerJoueur(Long id);
}
