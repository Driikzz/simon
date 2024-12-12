package fr.esgi.simon.service.impl;

import fr.esgi.simon.business.Pad;
import fr.esgi.simon.service.PadService;
import fr.esgi.simon.service.SequenceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceServiceImpl implements SequenceService {
    private final PadService padService;
    private final Random random = new Random();
    private final List<Pad> sequence = new ArrayList<>();

    public SequenceServiceImpl(PadService padService) {
        this.padService = padService;
    }

    @Override
    public void initialiserSequence() {
        sequence.clear();
    }

    @Override
    public List<Pad> obtenirSequenceActuelle() {
        return new ArrayList<>(sequence);
    }

    @Override
    public void allongerSequence() {
        List<Pad> padsDisponibles = padService.initialiserPads();
        Pad padAleatoire = padsDisponibles.get(random.nextInt(padsDisponibles.size()));
        sequence.add(padAleatoire);
    }
}
