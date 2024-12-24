package fr.esgi.simon.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

    @Test
    void testGetSetId() {
        Joueur joueur = new Joueur();
        joueur.setId(1L);
        assertEquals(1L, joueur.getId(),
                "L'ID devrait être 1L après avoir utilisé setId(1L).");
    }

    @Test
    void testGetSetPseudo() {
        Joueur joueur = new Joueur();
        joueur.setPseudo("PlayerOne");
        assertEquals("PlayerOne", joueur.getPseudo(),
                "Le pseudo devrait être 'PlayerOne' après setPseudo().");
    }

    @Test
    void testEqualsAndHashCode() {
        Joueur joueur1 = new Joueur();
        joueur1.setId(1L);
        joueur1.setPseudo("PlayerOne");

        Joueur joueur2 = new Joueur();
        joueur2.setId(1L);
        joueur2.setPseudo("PlayerOne");

        assertEquals(joueur1, joueur2,
                "Deux joueurs avec les mêmes champs doivent être égaux.");
        assertEquals(joueur1.hashCode(), joueur2.hashCode(),
                "Deux joueurs égaux doivent avoir le même hashCode.");
    }

    @Test
    void testToString() {
        Joueur joueur = new Joueur();
        joueur.setId(10L);
        joueur.setPseudo("PlayerTen");

        String toStringResult = joueur.toString();
        assertNotNull(toStringResult, "toString() ne devrait pas retourner null.");
        assertTrue(toStringResult.contains("PlayerTen"),
                "Le toString() devrait contenir la valeur du pseudo.");
        assertTrue(toStringResult.contains("10"),
                "Le toString() devrait contenir la valeur de l'ID.");
    }

    @Test
    void testStaticFieldsScoreAndCompteur() {
        // Les champs "score" et "compteur" sont private static et
        // sans getters/setters publics. On les teste via la réflexion.
        try {
            // Récupère le champ "score"
            var scoreField = Joueur.class.getDeclaredField("score");
            scoreField.setAccessible(true);
            int initialScore = (int) scoreField.get(null); // champ static => get(null)

            assertEquals(0, initialScore,
                    "Par défaut, le score statique devrait être initialisé à 0.");

            // On modifie le score via la réflexion pour vérifier que ça fonctionne
            scoreField.set(null, 42);
            int updatedScore = (int) scoreField.get(null);
            assertEquals(42, updatedScore,
                    "Après mise à jour, le score statique devrait valoir 42.");

            // Récupère le champ "compteur"
            var compteurField = Joueur.class.getDeclaredField("compteur");
            compteurField.setAccessible(true);
            Long initialCompteur = (Long) compteurField.get(null);

            assertEquals(0L, initialCompteur,
                    "Par défaut, le compteur statique devrait être initialisé à 0L.");

            // Mise à jour du compteur
            compteurField.set(null, 999L);
            Long updatedCompteur = (Long) compteurField.get(null);
            assertEquals(999L, updatedCompteur,
                    "Après mise à jour, le compteur statique devrait valoir 999L.");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Impossible d'accéder aux champs statiques via réflexion : " + e.getMessage());
        }
    }
}
