package pt.ipp.isep.dei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main JavaFX application class for the MABEC Main Menu.
 * <p>
 * This class is responsible for launching the JavaFX application and displaying the main menu window.
 * It also suppresses JavaFX FXML warnings for a cleaner log output.
 * </p>
 */
public class MainMenuApp extends Application implements Runnable {

    /**
     * Entry point for running the application as a Runnable.
     * Suppresses JavaFX FXML warnings and launches the JavaFX application.
     */
    @Override
    public void run() {
        // Suppress JavaFX FXML warnings
        Logger logger = Logger.getLogger("javafx.fxml");
        logger.setLevel(Level.SEVERE);
        for (Handler handler : Logger.getLogger("").getHandlers()) {
            handler.setLevel(Level.SEVERE);
        }
        launch();
    }

    /**
     * JavaFX start method. Sets up the main menu scene and window properties.
     *
     * @param stage The primary stage for this application.
     * @throws Exception if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("MABEC - Main Menu");
        stage.show();
    }
}
