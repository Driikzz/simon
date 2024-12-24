package fr.esgi.simon.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccueilControllerTest {

    private AccueilController controller;

    @BeforeAll
    public static void initJavaFX() {
        // Initialisation de JavaFX
        new Thread(() -> javafx.application.Platform.startup(() -> {})).start();
    }

    @BeforeEach
    void setUp() {
        controller = new AccueilController();
    }

    @Test
    void testHandleModeSolo_noException() {
        assertDoesNotThrow(() -> controller.handleModeSolo(),
                "handleModeSolo() ne doit pas lancer d'exception.");
    }

    @Test
    void testHandleModeMultiJoueurs_noException() {
        assertDoesNotThrow(() -> controller.handleModeMultiJoueurs(),
                "handleModeMultiJoueurs() ne doit pas lancer d'exception.");
    }
}
