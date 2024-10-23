package fr.esgi.simon.business;

import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Data
@Getter
public class Composition {
    private Long id;
    private Map<List<Pad>, Double> sonComposition;
    private Joueur joueur;
    private static Long compteur = 0L;
}
