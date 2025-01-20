package fr.esgi.simon.service.impl;

import fr.esgi.simon.business.Pad;
import fr.esgi.simon.service.PadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class SequenceServiceImplTest {

    private PadService padService;
    private SequenceServiceImpl sequenceServiceImpl;

    @BeforeEach
    void setUp() {
        padService = new PadServiceImpl();
        sequenceServiceImpl = new SequenceServiceImpl(padService);
    }

    @Test
    void testInitialiserSequence() {
        sequenceServiceImpl.initialiserSequence();
        Assertions.assertTrue(sequenceServiceImpl.obtenirSequenceActuelle().isEmpty(),
                "La séquence doit être vide après initialisation.");
    }

    @Test
    void testObtenirSequenceActuelle() {
        sequenceServiceImpl.initialiserSequence();
        Assertions.assertNotNull(sequenceServiceImpl.obtenirSequenceActuelle(),
                "La séquence ne doit pas être null après initialisation.");
        Assertions.assertTrue(sequenceServiceImpl.obtenirSequenceActuelle().isEmpty(),
                "La séquence doit être vide après initialisation.");

        sequenceServiceImpl.allongerSequence();
        List<Pad> sequence = sequenceServiceImpl.obtenirSequenceActuelle();
        Assertions.assertEquals(1, sequence.size(),
                "La séquence doit contenir un élément après un appel à allongerSequence.");
    }

    @Test
    void testAllongerSequence() {
        sequenceServiceImpl.initialiserSequence(); // Réinitialisation de la séquence

        sequenceServiceImpl.allongerSequence();
        List<Pad> sequence = sequenceServiceImpl.obtenirSequenceActuelle();
        Assertions.assertEquals(1, sequence.size(),
                "La séquence doit contenir 1 élément après le premier appel à allongerSequence.");
        Assertions.assertNotNull(sequence.get(0),
                "Le premier élément de la séquence ne doit pas être null.");

        sequenceServiceImpl.allongerSequence();
        sequence = sequenceServiceImpl.obtenirSequenceActuelle();
        Assertions.assertEquals(2, sequence.size(),
                "La séquence doit contenir 2 éléments après le deuxième appel à allongerSequence.");
        Assertions.assertNotEquals(sequence.get(0), sequence.get(1),
                "Les deux éléments de la séquence doivent être différents (aléatoires).");
    }

}
