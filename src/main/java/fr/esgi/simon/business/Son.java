package fr.esgi.simon.business;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Son {
    private Long id;
    private String nom;
    private String path;
    private Instrument instrument;
    private static Long compteur = 0L;

    public Son(String nom, String path) {
        this.id = ++compteur;
        this.nom = nom;
        this.path = path;
    }
}
