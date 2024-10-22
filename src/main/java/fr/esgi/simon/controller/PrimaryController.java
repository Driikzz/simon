package fr.esgi.simon.controller;

import java.io.IOException;

import fr.esgi.simon.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
