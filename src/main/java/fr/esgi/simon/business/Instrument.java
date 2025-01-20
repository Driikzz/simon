package fr.esgi.simon.business;

import lombok.Data;

/**
 * Représente un instrument musical.
 * Chaque instrument a un nom et un chemin vers son fichier associé.
 */
@Data
public class Instrument {

    /**
     * Identifiant unique de l'instrument.
     */
    private Long id;

    /**
     * Nom de l'instrument.
     */
    private String nom;

    /**
     * Chemin du fichier audio associé à l'instrument.
     */
    private String path;

    /**
     * Compteur global pour générer des identifiants uniques.
     */
    private static Long compteur = 0L;
}
