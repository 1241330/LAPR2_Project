package pt.ipp.isep.dei.ui.gui.simulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.SimulationRelated.CreateSimulationController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation.InitializeSimulationGUI;
import pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation.TypesSimulationSelectionGUI;

import java.io.IOException;

/**
 * This class provides the GUI for creating a new simulation within a selected scenario.
 * It allows the user to input a simulation name, validates the input, and handles navigation between screens.
 * The class interacts with the CreateSimulationController to manage the simulation creation logic.
 *
 * Main functionalities include:
 * <ul>
 *   <li>Initializing the controller and GUI components.</li>
 *   <li>Setting the selected scenario for which the simulation will be created.</li>
 *   <li>Handling the back button to return to the simulation type selection screen.</li>
 *   <li>Validating and saving the simulation name, ensuring it is unique and valid.</li>
 *   <li>Transitioning to the simulation initialization screen after successful creation.</li>
 * </ul>
 */
public class CreateSimulationGUI {

    private CreateSimulationController controller;

    @FXML
    private TextField simulationName;

    @FXML
    private Label errorLabel;

    /**
     * Initializes the CreateSimulationController and prepares the GUI components.
     * This method is automatically called after the FXML fields are injected.
     */
    @FXML
    private void initialize() {
        controller = new CreateSimulationController();
    }

    /**
     * Sets the selected scenario for which the simulation will be created.
     *
     * @param scenario the scenario to associate with the new simulation
     */
    public void setSelectedScenario(Scenario scenario) {
        controller.setActualScenario(scenario);
    }

    /**
     * Handles the action of the back button, returning to the simulation type selection screen.
     * Loads the TypesSimulationSelectionGUI and sets the current scenario.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/TypesSimulationSelection.fxml"));
            Parent root = loader.load();
            TypesSimulationSelectionGUI typesSimulationSelectionGUI = loader.getController();
            typesSimulationSelectionGUI.setScenario(controller.getActualScenario());
            Stage chooseScenarioStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            chooseScenarioStage.setTitle("MABEC - Types of Simulation Selection");
            chooseScenarioStage.setScene(new Scene(root));
            chooseScenarioStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of saving the simulation name. Validates the name and creates the simulation if valid.
     * Shows error messages if the name is invalid or already exists.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleSaveNameButton(ActionEvent event) {
        simulationName.setText(simulationName.getText().trim());
        controller.setSimulationName(simulationName.getText());
        if (controller.nameSimulationIsEmpty()) {
            errorLabel.setText("Simulation name cannot be empty!");
            return;
        }
        if (!controller.isValidName()) {
            errorLabel.setText("Simulation name can only contain letters, numbers, and underscores!");
            return;
        }
        if (controller.alreadyExistsNameSimulationInScenario()) {
            errorLabel.setText("Simulation with this name already exists in the scenario!");
            return;
        }
        controller.createSimulation();
        runSimulationGUI(event);
    }

    /**
     * Loads and displays the GUI for initializing the newly created simulation.
     *
     * @param event the action event triggered by the user
     */
    private void runSimulationGUI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/InitializeSimulation.fxml"));
            Parent root = loader.load();
            InitializeSimulationGUI initializeSimulationGUI = loader.getController();
            initializeSimulationGUI.setInformation(controller.getSimulation(),true);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Run Simulation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

