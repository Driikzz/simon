package fr.esgi.simon.controller;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccueilControllerTest {

    private AccueilController controller;

    @BeforeAll
    public static void initJavaFX() {
        // Initialisation de JavaFX pour les tests
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @BeforeEach
    void setUp() {
        controller = new AccueilController();
    }

    @Test
    void testHandleModeSolo_noException() {
        // Exécution du test sur le thread JavaFX
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> {
                // Simulation d'un comportement de App.setRoot
                AppTestMock.setRoot = fxml -> System.out.println("Simulated: " + fxml);
                controller.handleModeSolo();
            }, "handleModeSolo() ne doit pas lancer d'exception.");
        });
    }

    @Test
    void testHandleModeMultiJoueurs_noException() {
        // Exécution du test sur le thread JavaFX
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> {
                // Simulation d'un comportement de App.setRoot
                AppTestMock.setRoot = fxml -> System.out.println("Simulated: " + fxml);
                controller.handleModeMultiJoueurs();
            }, "handleModeMultiJoueurs() ne doit pas lancer d'exception.");
        });
    }

    // Classe statique auxiliaire pour simuler le comportement de App.setRoot
    private static class AppTestMock {
        static java.util.function.Consumer<String> setRoot;

        static void setRoot(String fxml) {
            if (setRoot != null) {
                setRoot.accept(fxml);
            }
        }
    }
}
