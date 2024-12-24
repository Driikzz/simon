package fr.esgi.simon.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccueilControllerTest {

    private AccueilController controller;

    @BeforeEach
    void setUp() {
        // Pas de dépendances, on instancie juste le contrôleur
        controller = new AccueilController();
    }

    @Test
    void testHandleModeSolo_noException() {
        // On vérifie simplement qu'aucune exception n'est propagée
        assertDoesNotThrow(() -> controller.handleModeSolo(),
                "handleModeSolo() ne doit pas lancer d'exception.");
    }

    @Test
    void testHandleModeMultiJoueurs_noException() {
        // Même logique
        assertDoesNotThrow(() -> controller.handleModeMultiJoueurs(),
                "handleModeMultiJoueurs() ne doit pas lancer d'exception.");
    }
}
