package fr.esgi.simon.business;

import lombok.Data;

/**
 * Représente un joueur dans le jeu Simon.
 * Chaque joueur a un identifiant unique, un pseudo et un score global.
 */
@Data
public class Joueur {

    /**
     * Identifiant unique du joueur.
     */
    private Long id;

    /**
     * Pseudo du joueur.
     */
    private String pseudo;

    /**
     * Score global du joueur.
     */
    private static int score;

    /**
     * Compteur global pour générer des identifiants uniques.
     */
    private static Long compteur = 0L;
}
