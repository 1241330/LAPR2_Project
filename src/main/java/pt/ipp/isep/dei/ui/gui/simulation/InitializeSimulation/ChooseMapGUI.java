package pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.ChooseMapController;
import pt.ipp.isep.dei.ui.gui.message.ErrorMessageGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for selecting a map before starting a simulation.
 * Handles map selection, navigation, and error display for the map selection process.
 */
public class ChooseMapGUI implements Initializable {

    /**
     * Controller responsible for business logic related to map selection.
     */
    private ChooseMapController controller;

    /**
     * ChoiceBox for displaying available map names to select.
     */
    @FXML
    private ChoiceBox<String> mapChoiceBox;
    /**
     * Label for displaying error messages to the user.
     */
    @FXML
    private Label errorLabel = new Label("");

    /**
     * Initializes the controller and populates the map choice box with available maps.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseMapController();
        mapChoiceBox.getItems().addAll(controller.getListOfNamesMaps());
    }

    /**
     * Checks if there are maps available to continue to the next step.
     * @return true if there are maps available, false otherwise
     */
    public boolean confirmationContinueAction() {
        return !controller.listOfMapsIsEmpty();
    }

    /**
     * Handles the action of the Back button, returning to the Player Mode menu.
     * @param event The ActionEvent triggered by clicking the Back button.
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
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
     * Handles the action of the Select Map button. Validates selection and proceeds to scenario selection.
     * @param event The ActionEvent triggered by clicking the Select Map button.
     */
    @FXML
    public void handleSelectMapButton(ActionEvent event){
        if (mapChoiceBox.getValue() == null){
            errorLabel.setText("Select a Map to Continue!");
            return;
        }
        controller.setSelectedMap(mapChoiceBox.getSelectionModel().getSelectedIndex());
        runChooseScenarioGUI(event);
    }

    /**
     * Loads the Choose Scenario GUI after a map is selected, or shows an error if no scenarios are available.
     * @param event The ActionEvent that triggered the transition.
     */
    private void runChooseScenarioGUI(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/ChooseScenario.fxml"));
            Parent root = loader.load();
            ChooseScenarioGUI chooseScenarioGUI = loader.getController();
            chooseScenarioGUI.setSelectedMap(controller.getSelectedMap());
            if (chooseScenarioGUI.confirmationContinueAction()) {
                stage.setTitle("MABEC - Simulation: Choose Scenario");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
                Parent root3 = loader3.load();
                stage.setTitle("MABEC - Player Mode");
                stage.setScene(new Scene(root3));
                stage.show();

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/message/ErrorMessage.fxml"));
                Parent root2 = loader2.load();
                ErrorMessageGUI errorMessageGUI = loader2.getController();
                errorMessageGUI.setErrorMessage("There aren't scenarios available to select.\nPlease create a scenario first.");
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
}
