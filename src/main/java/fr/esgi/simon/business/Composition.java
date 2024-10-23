package fr.esgi.simon.business;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class Composition {
    private Long id;
    private List<Son> son;
    private Joueur joueur;
    private static Long compteur = 0L;
}
