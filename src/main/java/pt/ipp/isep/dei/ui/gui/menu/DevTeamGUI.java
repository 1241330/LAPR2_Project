package pt.ipp.isep.dei.ui.gui.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller responsible for displaying the development team screen.
 * <p>
 * Provides functionality to return to the main menu.
 * </p>
 */
public class DevTeamGUI implements Initializable {

    /**
     * Initializes the controller class. Called automatically after the FXML file has been loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // No initialization needed for now.
    }

    /**
     * Handles the action of the "Back" button, returning the user to the Main Menu screen.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu/MainMenu.fxml"));
            Parent root = fxmlLoader.load();

            // Retrieve the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set new scene and update window title
            stage.setTitle("MABEC - Main Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Consider replacing with proper logging
        }
    }
}