package fr.esgi.simon.service.impl;

import fr.esgi.simon.business.Pad;
import fr.esgi.simon.business.Son;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PadServiceImplTest {

    private PadServiceImpl padServiceImpl;

    @BeforeEach
    void setUp() {
        padServiceImpl = new PadServiceImpl();
    }

    @Test
    void testInitialiserPads() {
        List<Pad> result = padServiceImpl.initialiserPads();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size());

        Assertions.assertEquals("Rouge", result.get(0).getCouleur());
        Assertions.assertEquals("Vert", result.get(1).getCouleur());
        Assertions.assertEquals("Bleu", result.get(2).getCouleur());
        Assertions.assertEquals("Jaune", result.get(3).getCouleur());
    }

    @Test
    void testObtenirPadParCouleur() {
        padServiceImpl.initialiserPads();

        Pad rougePad = padServiceImpl.obtenirPadParCouleur("Rouge");
        Assertions.assertNotNull(rougePad);
        Assertions.assertEquals("Rouge", rougePad.getCouleur());

        Pad vertPad = padServiceImpl.obtenirPadParCouleur("Vert");
        Assertions.assertNotNull(vertPad);
        Assertions.assertEquals("Vert", vertPad.getCouleur());

        Pad nonExistantPad = padServiceImpl.obtenirPadParCouleur("NonExistant");
        Assertions.assertNull(nonExistantPad);
    }
}
