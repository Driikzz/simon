package fr.esgi.simon.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class InstrumentTest {

    @BeforeEach
    void setUp() {
        // Optionnel : remettre compteur à zéro avant chaque test via réflexion
        try {
            Field compteurField = Instrument.class.getDeclaredField("compteur");
            compteurField.setAccessible(true);
            compteurField.set(null, 0L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Impossible de réinitialiser le compteur: " + e.getMessage());
        }
    }

    @Test
    void testGettersAndSetters() {
        Instrument instrument = new Instrument();
        instrument.setId(1L);
        instrument.setNom("Guitare");
        instrument.setPath("/sons/guitare.wav");

        assertEquals(1L, instrument.getId(),
                "L'id devrait valoir 1 après le setter.");
        assertEquals("Guitare", instrument.getNom(),
                "Le nom devrait valoir 'Guitare'.");
        assertEquals("/sons/guitare.wav", instrument.getPath(),
                "Le path devrait valoir '/sons/guitare.wav'.");
    }

    @Test
    void testToString() {
        Instrument instrument = new Instrument();
        instrument.setNom("Piano");
        instrument.setPath("/sons/piano.wav");

        String result = instrument.toString();
        assertNotNull(result, "toString() ne doit pas être null.");
        assertTrue(result.contains("Piano"),
                "Le toString() doit inclure le nom de l'instrument.");
        assertTrue(result.contains("piano.wav"),
                "Le toString() doit inclure le path de l'instrument.");
    }

    @Test
    void testEqualsAndHashCode() {
        Instrument instrument1 = new Instrument();
        instrument1.setId(10L);
        instrument1.setNom("Violon");
        instrument1.setPath("/sons/violon.wav");

        Instrument instrument2 = new Instrument();
        instrument2.setId(10L);
        instrument2.setNom("Violon");
        instrument2.setPath("/sons/violon.wav");

        // Égalité
        assertEquals(instrument1, instrument2,
                "Deux instruments ayant les mêmes valeurs de champs devraient être égaux (Lombok).");
        assertEquals(instrument1.hashCode(), instrument2.hashCode(),
                "Deux instruments égaux devraient avoir le même hashCode.");
    }

    @Test
    void testCompteurStaticField() {
        // Par défaut, 'compteur' vaut 0L (après remise à zéro dans @BeforeEach).
        Instrument instrument = new Instrument();
        // On n’incrémente pas 'compteur' dans cette classe, donc on se contente de vérifier sa valeur
        try {
            Field compteurField = Instrument.class.getDeclaredField("compteur");
            compteurField.setAccessible(true);
            long currentValue = (long) compteurField.get(null);  // champ static => get(null)
            assertEquals(0L, currentValue,
                    "Le compteur devrait rester à 0 tant que rien ne l'incrémente.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Impossible d'accéder au champ 'compteur' via réflexion : " + e.getMessage());
        }
    }
}
