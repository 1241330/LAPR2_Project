package pt.ipp.isep.dei.ui.gui.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.ui.console.menu.EditorUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Represents the main menu UI of the application.
 * Displays the main options and handles user selection.
 */
public class MainMenuGUI implements Initializable {

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Handles the action of the Login button. Loads the Login screen and sets it as the current scene.
     *
     * @param event The ActionEvent triggered by clicking the Login button.
     */
    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/authorization/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Know Development Team button. Loads the Dev Team screen and sets it as the current scene.
     *
     * @param event The ActionEvent triggered by clicking the Know Development Team button.
     */
    @FXML
    public void handleKnowDevelopmentTeam(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu/DevTeam.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Know the Development Team");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Editor Mode button. Closes the current window and runs the Editor UI in console mode.
     *
     * @param event The ActionEvent triggered by clicking the Editor Mode button.
     */
    @FXML
    public void handleEditorMode(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        EditorUI editorUI = new EditorUI();
        editorUI.run();
    }

    /**
     * Handles the action of the Player Mode button. Loads the Player Mode screen and sets it as the current scene.
     *
     * @param event The ActionEvent triggered by clicking the Player Mode button.
     */
    @FXML
    public void handlePlayerMode(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Player Mode");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Exit button. Closes the application window and terminates the program.
     *
     * @param event The ActionEvent triggered by clicking the Exit button.
     */
    @FXML
    public void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}


