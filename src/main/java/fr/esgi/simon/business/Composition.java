package fr.esgi.simon.business;

import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Représente une composition musicale.
 * Chaque composition est associée à un joueur et contient une collection de sons.
 */
@Data
@Getter
public class Composition {

    /**
     * Identifiant unique de la composition.
     */
    private Long id;

    /**
     * Map associant une liste de pads à un poids (double), représentant les sons de la composition.
     */
    private Map<List<Pad>, Double> sonComposition;

    /**
     * Joueur à qui appartient cette composition.
     */
    private Joueur joueur;

    /**
     * Compteur global pour générer des identifiants uniques.
     */
    private static Long compteur = 0L;
}
