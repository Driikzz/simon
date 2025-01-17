package fr.esgi.simon.service.impl;

import fr.esgi.simon.business.Joueur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;

class JoueurServiceImplTest {

    @Mock
    private List<Joueur> joueurs;

    @InjectMocks
    private JoueurServiceImpl joueurServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        joueurs = new ArrayList<>(); // Initialisation de la liste simulée
        joueurServiceImpl = new JoueurServiceImpl();
    }

    @Test
    void testCreerJoueur() {
        Joueur joueur = joueurServiceImpl.creerJoueur("TestPlayer");
        Assertions.assertNotNull(joueur);
        Assertions.assertEquals("TestPlayer", joueur.getPseudo());
    }

    @Test
    void testObtenirJoueur() {
        Joueur joueur = joueurServiceImpl.creerJoueur("TestPlayer");
        Joueur result = joueurServiceImpl.obtenirJoueur(joueur.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(joueur.getId(), result.getId());
    }

    @Test
    void testListerTousLesJoueurs() {
        joueurServiceImpl.creerJoueur("Player1");
        joueurServiceImpl.creerJoueur("Player2");

        List<Joueur> result = joueurServiceImpl.listerTousLesJoueurs();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testSupprimerJoueur() {
        Joueur joueur = joueurServiceImpl.creerJoueur("PlayerToDelete");
        joueurServiceImpl.supprimerJoueur(joueur.getId());

        Joueur result = joueurServiceImpl.obtenirJoueur(joueur.getId());
        Assertions.assertNull(result);
    }
}
