package fr.esgi.simon.business;

import lombok.Data;

@Data
public class Instrument {
    private Long id;
    private String nom;
    private String path;
    private static Long compteur = 0L;
}
