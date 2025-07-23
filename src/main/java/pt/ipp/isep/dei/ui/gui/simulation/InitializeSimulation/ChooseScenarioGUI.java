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
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.ChooseScenarioController;
import pt.ipp.isep.dei.domain.Map.Map;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for selecting a scenario before starting a simulation.
 * Handles scenario selection, navigation, and error display for the scenario selection process.
 */
public class ChooseScenarioGUI implements Initializable {

    /**
     * Controller responsible for business logic related to scenario selection.
     */
    private ChooseScenarioController controller;

    /**
     * ChoiceBox for displaying available scenario names to select.
     */
    @FXML
    private ChoiceBox<String> scenarioChoiceBox;
    /**
     * Label for displaying error messages to the user.
     */
    @FXML
    private Label errorLabel = new Label("");

    /**
     * Initializes the controller and triggers the check for available scenarios.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseScenarioController();
        javafx.application.Platform.runLater(this::confirmationContinueAction);
    }

    /**
     * Sets the selected map and populates the scenario choice box with available scenarios.
     *
     * @param map The selected map.
     */
    public void setSelectedMap(Map map) {
        controller.setActualMap(map);
        scenarioChoiceBox.getItems().addAll(controller.getListOfNamesScenarios());
    }

    /**
     * Checks if there are scenarios available to continue to the next step.
     *
     * @return true if there are scenarios available, false otherwise
     */
    public boolean confirmationContinueAction() {
        return !controller.listOfScenariosIsEmpty();
    }

    /**
     * Handles the action of the Select Scenario button. Validates selection and proceeds to simulation type selection.
     *
     * @param event The ActionEvent triggered by clicking the Select Scenario button.
     */
    @FXML
    public void handleSelectScenarioButton(ActionEvent event) {
        if (scenarioChoiceBox.getValue() == null) {
            errorLabel.setText("Select a Map to Continue!");
            return;
        }
        controller.setSelectedScenario(scenarioChoiceBox.getSelectionModel().getSelectedIndex());
        runTypesSimulationSelectionGUI(event);
    }

    /**
     * Loads the Types Simulation Selection GUI after a scenario is selected.
     *
     * @param event The ActionEvent that triggered the transition.
     */
    private void runTypesSimulationSelectionGUI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/TypesSimulationSelection.fxml"));
            Parent root = loader.load();
            TypesSimulationSelectionGUI typesSimulationSelectionGUI = loader.getController();
            typesSimulationSelectionGUI.setScenario(controller.getSelectedScenario());
            Stage chooseScenarioStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            chooseScenarioStage.setTitle("MABEC - Types of Simulation Selection");
            chooseScenarioStage.setScene(new Scene(root));
            chooseScenarioStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Exit button, returning to the Player Mode menu.
     *
     * @param event The ActionEvent triggered by clicking the Exit button.
     */
    @FXML
    public void handleExitButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Map");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Back button, returning to the Player Mode menu.
     *
     * @param event The ActionEvent triggered by clicking the Back button.
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Map");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
