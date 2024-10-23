package fr.esgi.simon.business;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Joueur {
    private Long id;
    private String pseudo;
    private static int score;
    private static Long compteur = 0L;
}
