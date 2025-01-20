package fr.esgi.simon.service.impl;

import fr.esgi.simon.business.Pad;
import fr.esgi.simon.business.Son;
import fr.esgi.simon.service.PadService;

import java.util.ArrayList;
import java.util.List;

public class PadServiceImpl implements PadService {
    private final List<Pad> pads = new ArrayList<>();

    @Override
    public List<Pad> initialiserPads() {
        pads.add(new Pad(1L, "Rouge", new Son("RougeSon", "/sounds/Guitar/red_guitar_tone.wav")));
        pads.add(new Pad(2L, "Vert", new Son("VertSon", "/sounds/Guitar/green_guitar_tone.wav")));
        pads.add(new Pad(3L, "Bleu", new Son("BleuSon", "/sounds/Guitar/blue_guitar_tone.wav")));
        pads.add(new Pad(4L, "Jaune", new Son("JauneSon", "/sounds/Guitar/yellow_guitar_tone.wav")));
        return pads;
    }

    @Override
    public Pad obtenirPadParCouleur(String couleur) {
        return pads.stream()
                .filter(pad -> pad.getCouleur().equalsIgnoreCase(couleur))
                .findFirst()
                .orElse(null);
    }
}
