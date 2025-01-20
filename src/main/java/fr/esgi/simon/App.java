package fr.esgi.simon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale de l'application Simon.
 * Cette classe gère le démarrage de l'application, le chargement des fichiers FXML,
 * et la gestion de la scène principale.
 */
public class App extends Application {

    /**
     * La scène principale de l'application.
     */
    private static Scene scene;

    /**
     * Point d'entrée JavaFX qui démarre l'application.
     *
     * @param stage La fenêtre principale fournie par JavaFX.
     * @throws IOException Si le fichier FXML d'accueil ne peut pas être chargé.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Accueil"), 640, 600);
        stage.setTitle("Jeu Simon");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Définit la racine de la scène principale avec un fichier FXML donné.
     *
     * @param fxml Le nom du fichier FXML (sans extension) à charger.
     * @throws IOException Si le fichier FXML ne peut pas être chargé.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Définit la racine de la scène principale avec un objet Parent donné.
     *
     * @param root L'objet Parent représentant la nouvelle racine de la scène.
     */
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    /**
     * Charge un fichier FXML et retourne son contenu sous forme de Parent.
     *
     * @param fxml Le nom du fichier FXML (sans extension) à charger.
     * @return L'objet Parent représentant le contenu chargé.
     * @throws IOException Si le fichier FXML ne peut pas être chargé.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Point d'entrée principal de l'application.
     *
     * @param args Les arguments passés via la ligne de commande.
     */
    public static void main(String[] args) {
        launch();
    }
}
