package fr.esgi.simon.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PadTest {

    @Mock
    private Son sonMock;

    private Pad pad;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // On instancie le Pad avec une couleur non-nulle et le Son mocké
        pad = new Pad(1L, "Rouge", sonMock);
    }

    @Test
    void testConstructor() {
        assertNotNull(pad, "Le pad ne doit pas être null après construction.");
        assertEquals(1L, pad.getId(), "L'id devrait être 1L.");
        assertEquals("Rouge", pad.getCouleur(), "La couleur devrait être 'Rouge'.");
        assertSame(sonMock, pad.getSon(), "Le Son devrait être le mock injecté.");
    }

    @Test
    void testEquals_sameObject() {
        // Comparaison avec lui-même
        assertTrue(pad.equals(pad), "Un pad devrait être égal à lui-même.");
    }

    @Test
    void testEquals_null() {
        // Comparaison avec null
        assertFalse(pad.equals(null), "Un pad ne devrait pas être égal à null.");
    }

    @Test
    void testEquals_differentClass() {
        // Comparaison avec un objet d’une autre classe
        assertFalse(pad.equals("Une chaîne"),
                "Un pad ne devrait pas être égal à un objet d'une autre classe.");
    }

    @Test
    void testEquals_sameColor() {
        // Deux pads avec la même couleur => equals() doit renvoyer true
        Pad otherPad = new Pad(2L, "Rouge", mock(Son.class));
        assertTrue(pad.equals(otherPad),
                "Deux pads de même couleur doivent être considérés comme égaux.");
    }

    @Test
    void testEquals_differentColor() {
        // Deux pads avec une couleur différente => equals() doit renvoyer false
        Pad otherPad = new Pad(2L, "Bleu", mock(Son.class));
        assertFalse(pad.equals(otherPad),
                "Deux pads de couleur différente ne doivent pas être égaux.");
    }

    @Test
    void testHashCode_sameColor() {
        Pad otherPad = new Pad(999L, "Rouge", mock(Son.class));

        // Même couleur => hashCode identique selon l’implémentation
        assertEquals(pad.hashCode(), otherPad.hashCode(),
                "Pads avec la même couleur devraient avoir le même hashCode.");
    }

    @Test
    void testHashCode_differentColor() {
        Pad otherPad = new Pad(999L, "Vert", mock(Son.class));

        // Couleur différente => hashCode différent
        assertNotEquals(pad.hashCode(), otherPad.hashCode(),
                "Pads avec une couleur différente devraient avoir un hashCode différent.");
    }

    @Test
    void testSettersAndGetters() {
        // Test basique pour s'assurer que les setters sont fonctionnels (générés par Lombok)
        pad.setId(10L);
        pad.setCouleur("Jaune");
        Son newSon = mock(Son.class);
        pad.setSon(newSon);

        assertEquals(10L, pad.getId());
        assertEquals("Jaune", pad.getCouleur());
        assertSame(newSon, pad.getSon());
    }

    @Test
    void testToString() {
        String result = pad.toString();
        assertNotNull(result, "toString() ne doit pas retourner null.");
        assertTrue(result.contains("Rouge"),
                "Le toString() devrait contenir la couleur 'Rouge'.");
    }
}
