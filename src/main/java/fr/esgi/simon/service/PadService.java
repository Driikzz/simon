package fr.esgi.simon.service;

import fr.esgi.simon.business.Pad;
import java.util.List;

public interface PadService {
    List<Pad> initialiserPads();
    Pad obtenirPadParCouleur(String couleur);
}
