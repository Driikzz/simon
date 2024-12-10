package fr.esgi.simon.service.impl;

import fr.esgi.simon.business.Joueur;
import fr.esgi.simon.service.JoueurService;

import java.util.ArrayList;
import java.util.List;

public class JoueurServiceImpl implements JoueurService {
    private final List<Joueur> joueurs = new ArrayList<>();
    private Long compteur = 1L;

    @Override
    public Joueur creerJoueur(String pseudo) {
        Joueur joueur = new Joueur();
        joueur.setId(compteur++);
        joueur.setPseudo(pseudo);
        joueurs.add(joueur);
        return joueur;
    }

    @Override
    public Joueur obtenirJoueur(Long id) {
        return joueurs.stream()
                .filter(joueur -> joueur.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Joueur> listerTousLesJoueurs() {
        return joueurs;
    }

    @Override
    public void supprimerJoueur(Long id) {
        joueurs.removeIf(joueur -> joueur.getId().equals(id));
    }
}
