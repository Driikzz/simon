package fr.esgi.simon.business;


import lombok.Data;

@Data
public class Joueur {
    private Long id;
    private String pseudo;
    private static int score;
    private static Long compteur = 0L;
}
