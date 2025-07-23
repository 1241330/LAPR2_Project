package pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.InitializeSimulationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.RunSimulationGUI;

import java.io.IOException;

/**
 * GUI controller for initializing a simulation.
 * Handles the display of selected map and scenario, and navigation to run or return from simulation setup.
 */
public class InitializeSimulationGUI {

    /**
     * Controller responsible for business logic related to simulation initialization.
     */
    private InitializeSimulationController controller;

    /**
     * Label for displaying the selected map name.
     */
    @FXML
    private Label nameMap;

    /**
     * Label for displaying the selected scenario name.
     */
    @FXML
    private Label nameScenario;

    /**
     * Initializes the controller for simulation initialization.
     */
    @FXML
    public void initialize() {
        controller = new InitializeSimulationController();
    }

    /**
     * Sets the simulation and updates the map and scenario labels.
     * @param simulation The simulation to be initialized.
     */
    public void setInformation(Simulation simulation, boolean newSimulation) {
        controller.setSimulation(simulation);
        nameMap.setText(controller.getNameMap());
        nameScenario.setText(controller.getNameScenario());
        controller.setNewSimulation(newSimulation);
    }

    /**
     * Handles the action of the Run Simulation button. Loads and starts the simulation.
     * @param event The ActionEvent triggered by clicking the Run Simulation button.
     */
    @FXML
    public void handleRunSimulationButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/RunSimulation.fxml"));
            Parent root = loader.load();
            RunSimulationGUI runSimulationGUI = loader.getController();
            runSimulationGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("MABEC - Running Simulation");
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Return button. Deletes the current simulation and returns to the simulation type selection.
     * @param event The ActionEvent triggered by clicking the Return button.
     */
    @FXML
    public void returnButtonOnAction(ActionEvent event) {
        try {
            controller.deleteSimulation();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/TypesSimulationSelection.fxml"));
            Parent root = loader.load();
            TypesSimulationSelectionGUI typesSimulationSelectionGUI = loader.getController();
            typesSimulationSelectionGUI.setScenario(controller.getActualScenario());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Types of Simulation Selection");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

