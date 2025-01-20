module fr.esgi.simon {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires javafx.media;

    opens fr.esgi.simon.controller to javafx.fxml;
    exports fr.esgi.simon;

}
