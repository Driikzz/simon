package fr.esgi.simon.business;

import lombok.Data;

/**
 * Représente un pad interactif dans le jeu Simon.
 * Chaque pad a un identifiant, une couleur et un son associé.
 */
@Data
public class Pad {

    /**
     * Identifiant unique du pad.
     */
    private Long id;

    /**
     * Couleur du pad.
     */
    private String couleur;

    /**
     * Son associé au pad.
     */
    private Son son;

    /**
     * Constructeur permettant d'initialiser un pad avec ses propriétés.
     *
     * @param id      Identifiant unique du pad.
     * @param couleur Couleur du pad.
     * @param son     Son associé au pad.
     */
    public Pad(Long id, String couleur, Son son) {
        this.id = id;
        this.couleur = couleur;
        this.son = son;
    }

    /**
     * Vérifie l'égalité entre deux pads sur la base de leur couleur.
     *
     * @param o Objet à comparer.
     * @return {@code true} si les deux pads ont la même couleur, sinon {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pad pad = (Pad) o;

        return couleur != null ? couleur.equals(pad.couleur) : pad.couleur == null;
    }

    /**
     * Génère le hashCode d'un pad basé sur sa couleur.
     *
     * @return Le hashCode calculé.
     */
    @Override
    public int hashCode() {
        return couleur != null ? couleur.hashCode() : 0;
    }
}
