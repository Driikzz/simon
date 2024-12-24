package fr.esgi.simon.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SonTest {

    @Mock
    private Instrument instrumentMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // Optionnel : remettre compteur à zéro avant chaque test pour contrôler l'auto-incrément
        try {
            var compteurField = Son.class.getDeclaredField("compteur");
            compteurField.setAccessible(true);
            compteurField.set(null, 0L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Si vous ne souhaitez pas faire de réflexion, commentez ceci
            System.err.println("Impossible de remettre compteur à zéro : " + e.getMessage());
        }
    }

    @Test
    void testConstructorAndAutoIncrementId() {
        Son son1 = new Son("nom1", "path1");
        Son son2 = new Son("nom2", "path2");

        // Vérifie que chaque nouveau Son a un id incrémenté
        assertNotNull(son1);
        assertNotNull(son2);
        assertEquals(1L, son1.getId(),
                "Le premier Son devrait avoir l'ID 1.");
        assertEquals(2L, son2.getId(),
                "Le deuxième Son devrait avoir l'ID 2.");
    }

    @Test
    void testGettersAndSetters() {
        Son son = new Son("mySong", "myPath");
        son.setInstrument(instrumentMock);

        // Vérifie que le constructeur a bien initialisé nom, path et id
        assertEquals("mySong", son.getNom());
        assertEquals("myPath", son.getPath());
        assertEquals(1L, son.getId(),
                "Après remise à zéro du compteur, l'ID devrait être 1.");

        // Vérifie que l’instrument est correctement injecté
        assertSame(instrumentMock, son.getInstrument(),
                "Instrument devrait être le mock injecté.");
    }

    @Test
    void testInstrumentSetter() {
        Son son = new Son("songName", "songPath");
        assertNull(son.getInstrument(),
                "L'instrument devrait être null par défaut.");

        son.setInstrument(instrumentMock);
        assertNotNull(son.getInstrument());
        assertSame(instrumentMock, son.getInstrument());
    }

    @Test
    void testToString() {
        Son son = new Son("coolSong", "coolPath");
        String result = son.toString();

        assertNotNull(result, "toString() ne devrait pas être null.");
        assertTrue(result.contains("coolSong"),
                "Le toString() devrait inclure le nom.");
        assertTrue(result.contains("coolPath"),
                "Le toString() devrait inclure le path.");
    }

    @Test
    void testEqualsAndHashCode_lombokDefault() {
        // Lombok @Data génère equals() et hashCode() sur TOUTES les propriétés (id, nom, path, instrument).
        // Comme l'id est incrémenté automatiquement, deux Son créés à la suite n'auront pas le même id.
        Son son1 = new Son("sameName", "samePath");
        Son son2 = new Son("sameName", "samePath");

        // ID différents => equals() devrait être false
        assertNotEquals(son1, son2,
                "Avec des IDs différents, les deux Son ne devraient pas être égaux (impl. par Lombok).");

        // Si vous voulez ignorer l'ID dans equals(), il faudrait surcharger equals() manuellement.
    }

    @Test
    void testCompteurStaticFieldDirectly() {
        // Montre comment vérifier la valeur de 'compteur' SANS remise à zéro (ou après).
        // create un premier Son => id = 1
        Son son1 = new Son("test1", "path1");
        assertEquals(1L, son1.getId());

        // create un deuxième Son => id = 2
        Son son2 = new Son("test2", "path2");
        assertEquals(2L, son2.getId());

        // Lire compteur via réflexion pour confirmer qu'il vaut 2
        try {
            var compteurField = Son.class.getDeclaredField("compteur");
            compteurField.setAccessible(true);
            long currentCompteur = (long) compteurField.get(null);

            assertEquals(2L, currentCompteur,
                    "Le compteur devrait valoir 2 après avoir créé deux instances de Son.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Impossible d'accéder au champ 'compteur' via réflexion : " + e.getMessage());
        }
    }
}
