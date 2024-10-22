package fr.esgi.simon.controller;

import java.io.IOException;

import fr.esgi.simon.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}