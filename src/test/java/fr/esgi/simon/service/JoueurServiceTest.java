package fr.esgi.simon.service;

import fr.esgi.simon.business.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JoueurServiceTest {

    private JoueurService joueurService;

    @BeforeEach
    void setUp() {
        // Création d'un mock pour JoueurService
        joueurService = mock(JoueurService.class);
    }

    @Test
    void testCreerJoueur() {
        String pseudo = "TestPlayer";
        Joueur joueurMock = new Joueur();
        joueurMock.setPseudo(pseudo);

        when(joueurService.creerJoueur(pseudo)).thenReturn(joueurMock);

        Joueur joueur = joueurService.creerJoueur(pseudo);

        assertNotNull(joueur);
        assertEquals(pseudo, joueur.getPseudo());
        verify(joueurService, times(1)).creerJoueur(pseudo);
    }

    @Test
    void testObtenirJoueur() {
        Long id = 1L;
        Joueur joueurMock = new Joueur();
        joueurMock.setId(id);

        when(joueurService.obtenirJoueur(id)).thenReturn(joueurMock);

        Joueur joueur = joueurService.obtenirJoueur(id);

        assertNotNull(joueur);
        assertEquals(id, joueur.getId());
        verify(joueurService, times(1)).obtenirJoueur(id);
    }

    @Test
    void testListerTousLesJoueurs() {
        List<Joueur> joueursMock = Arrays.asList(new Joueur(), new Joueur());

        when(joueurService.listerTousLesJoueurs()).thenReturn(joueursMock);

        List<Joueur> joueurs = joueurService.listerTousLesJoueurs();

        assertNotNull(joueurs);
        assertEquals(2, joueurs.size());
        verify(joueurService, times(1)).listerTousLesJoueurs();
    }

    @Test
    void testSupprimerJoueur() {
        Long id = 1L;

        doNothing().when(joueurService).supprimerJoueur(id);

        joueurService.supprimerJoueur(id);

        verify(joueurService, times(1)).supprimerJoueur(id);
    }
}
