package pt.ipp.isep.dei.ui.gui.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.ui.console._MDISC_.VerifyRoutesUI;
import pt.ipp.isep.dei.ui.gui.message.ErrorMessageGUI;
import pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation.ChooseMapGUI;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Player Menu GUI screen.
 * Handles the initialization and user interactions for the Player Menu.
 */
public class PlayerMenuGUI implements Initializable {

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Handles the action of the Simulation button. Loads the Choose Map screen if maps are available, otherwise shows an error message.
     *
     * @param event The ActionEvent triggered by clicking the Simulation button.
     */
    @FXML
    public void handleSimulationButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/ChooseMap.fxml"));
            Parent root = loader.load();
            ChooseMapGUI chooseMapGUI = loader.getController();
            if (chooseMapGUI.confirmationContinueAction()) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("MABEC - Simulation: Choose Map");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/message/ErrorMessage.fxml"));
                Parent root2 = loader2.load();
                ErrorMessageGUI errorMessageGUI = loader2.getController();
                errorMessageGUI.setErrorMessage("There aren't maps available to select.\nPlease create a map first.");
                Stage stage2 = new Stage();
                stage2.setTitle("MABEC - Error Message");
                stage2.setScene(new Scene(root2));
                stage2.show();
                javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5));
                delay.setOnFinished(event1 -> stage2.close());
                delay.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Verify Routes button. Closes the current window and runs the VerifyRoutesUI in console mode.
     *
     * @param event The ActionEvent triggered by clicking the Verify Routes button.
     */
    @FXML
    public void handleVerifyRoutesButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        new VerifyRoutesUI().run();
    }

    /**
     * Handles the action of the Logout button. Loads the Main Menu screen and sets it as the current scene.
     *
     * @param event The ActionEvent triggered by clicking the Logout button.
     */
    @FXML
    public void handleLogoutButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu/MainMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Main Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

