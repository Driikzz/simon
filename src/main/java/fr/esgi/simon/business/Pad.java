package fr.esgi.simon.business;

import lombok.Data;

@Data
public class Pad {
    private Long id;
    private String couleur;
    private Son son;

    public Pad(Long id, String couleur, Son son) {
        this.id = id;
        this.couleur = couleur;
        this.son = son;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pad pad = (Pad) o;

        return couleur != null ? couleur.equals(pad.couleur) : pad.couleur == null;
    }

    @Override
    public int hashCode() {
        return couleur != null ? couleur.hashCode() : 0;
    }
}
