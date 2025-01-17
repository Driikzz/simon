package fr.esgi.simon.service.impl;

import fr.esgi.simon.business.Pad;
import fr.esgi.simon.business.Son;
import fr.esgi.simon.service.PadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        Assertions.assertTrue(sequenceServiceImpl.obtenirSequenceActuelle().isEmpty());
    }

    @Test
    void testObtenirSequenceActuelle() {
        sequenceServiceImpl.initialiserSequence();
        Assertions.assertNotNull(sequenceServiceImpl.obtenirSequenceActuelle());
        Assertions.assertTrue(sequenceServiceImpl.obtenirSequenceActuelle().isEmpty());

        sequenceServiceImpl.allongerSequence();
        List<Pad> sequence = sequenceServiceImpl.obtenirSequenceActuelle();
        Assertions.assertEquals(1, sequence.size());
    }

    @Test
    void testAllongerSequence() {
        sequenceServiceImpl.initialiserSequence();
        sequenceServiceImpl.allongerSequence();

        List<Pad> sequence = sequenceServiceImpl.obtenirSequenceActuelle();
        Assertions.assertEquals(1, sequence.size());
        Assertions.assertNotNull(sequence.get(0));

        sequenceServiceImpl.allongerSequence();
        Assertions.assertEquals(2, sequence.size());
    }
}
