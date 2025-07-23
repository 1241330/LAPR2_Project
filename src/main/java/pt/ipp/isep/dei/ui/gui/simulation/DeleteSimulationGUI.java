package pt.ipp.isep.dei.ui.gui.simulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.SimulationRelated.DeleteSimulationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation.TypesSimulationSelectionGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class provides the GUI for deleting a simulation within a selected scenario.
 * It allows the user to confirm or cancel the deletion of a simulation and handles navigation back to the simulation menu.
 * The class interacts with the DeleteSimulationController to manage the simulation deletion logic.
 *
 * Main functionalities include:
 * <ul>
 *   <li>Initializing the controller and GUI components.</li>
 *   <li>Setting the simulation to be deleted and the parent stage for navigation.</li>
 *   <li>Handling the delete and keep actions for the simulation.</li>
 *   <li>Transitioning back to the simulation menu after an action.</li>
 * </ul>
 */
public class DeleteSimulationGUI implements Initializable {

    private DeleteSimulationController controller;

    private Stage stageMenu;

    /**
     * Initializes the DeleteSimulationController and prepares the GUI components.
     * This method is automatically called after the FXML fields are injected.
     *
     * @param url the location used to resolve relative paths for the root object, or null if unknown
     * @param resourceBundle the resources used to localize the root object, or null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new DeleteSimulationController();
    }

    /**
     * Sets the simulation to be deleted and the parent stage for navigation.
     *
     * @param simulation the simulation to be deleted
     * @param stageMenu the parent stage to return to after deletion or cancellation
     */
    public void setInformation(Simulation simulation, Stage stageMenu) {
        controller.setActualSimulation(simulation);
        this.stageMenu = stageMenu;
    }

    /**
     * Handles the action of deleting the simulation. Closes the current window and returns to the simulation menu.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleDeleteSimulationButton(ActionEvent event) {
        controller.deleteSimulation();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        runSimulationMenu();
    }

    /**
     * Handles the action of keeping the simulation (cancelling deletion). Closes the current window and returns to the simulation menu.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleKeepSimulationButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        runSimulationMenu();
    }

    /**
     * Loads and displays the simulation menu GUI after a delete or keep action.
     */
    private void runSimulationMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/TypesSimulationSelection.fxml"));
            Parent root = loader.load();
            TypesSimulationSelectionGUI typesSimulationSelectionGUI = loader.getController();
            typesSimulationSelectionGUI.setScenario(controller.getScenarioOfSimulation());
            stageMenu.setTitle("MABEC - Types of Simulation Selection");
            stageMenu.setScene(new Scene(root));
            stageMenu.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

