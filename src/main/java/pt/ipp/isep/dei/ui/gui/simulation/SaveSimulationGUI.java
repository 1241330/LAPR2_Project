package pt.ipp.isep.dei.ui.gui.simulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.SimulationRelated.SaveSimulationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.message.SuccessMessageGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class responsible for handling the save simulation dialog.
 */
public class SaveSimulationGUI implements Initializable {

    private SaveSimulationController controller;
    private Stage simulationStage;

    /**
     * Initializes the controller for saving simulations.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if unknown
     * @param resourceBundle the resources used to localize the root object, or null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new SaveSimulationController();
    }

    /**
     * Sets the simulation and the stage to be used after saving or discarding the simulation.
     *
     * @param simulation      the current simulation
     * @param simulationStage the stage to be updated after the operation
     */
    public void setInformation(Simulation simulation, Stage simulationStage) {
        controller.setActualSimulation(simulation);
        this.simulationStage = simulationStage;
    }

    /**
     * Handles the action when the user chooses to save the simulation.
     * Closes the current window, saves the simulation, ends the simulation, and shows a success message.
     *
     * @param event the action event triggered by the button
     */
    @FXML
    public void handleSaveSimulationButton(ActionEvent event) {
        closeCurrentWindow(event);
        controller.saveSimulation();
        endSimulation(event);
        showSuccessMessage();
    }

    /**
     * Handles the action when the user chooses not to save the simulation.
     * Closes the current window, discards the simulation, and ends the simulation.
     *
     * @param event the action event triggered by the button
     */
    @FXML
    public void handleDontSaveSimulationButton(ActionEvent event) {
        closeCurrentWindow(event);
        controller.dontSaveSimulation();
        endSimulation(event);
    }

    /**
     * Handles the action when the user cancels the operation.
     * Closes the current window.
     *
     * @param event the action event triggered by the button
     */
    @FXML
    public void handleCancelButton(ActionEvent event) {
        closeCurrentWindow(event);
    }

    /**
     * Ends the simulation and returns to the Player Mode menu.
     *
     * @param event the action event that triggered the end of the simulation
     */
    private void endSimulation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
            Parent root = loader.load();
            simulationStage.setTitle("MABEC - Player Mode");
            simulationStage.setScene(new Scene(root));
            simulationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the current window from which the event was triggered.
     *
     * @param event the action event
     */
    private void closeCurrentWindow(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Shows a success message after saving the simulation.
     */
    private void showSuccessMessage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/SuccessMessage.fxml"));
            Parent root = loader.load();
            SuccessMessageGUI successMessageGUI = loader.getController();
            successMessageGUI.setSuccessMessage("Simulation saved successfully!");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation Saved");
            stage.setScene(new Scene(root));
            stage.show();
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5));
            delay.setOnFinished(event1 -> stage.close());
            delay.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the SaveSimulationController.
     *
     * @return the controller
     */
    public SaveSimulationController getController() {
        return controller;
    }

    /**
     * Sets the SaveSimulationController.
     *
     * @param controller the controller to set
     */
    public void setController(SaveSimulationController controller) {
        this.controller = controller;
    }

    /**
     * Gets the simulation stage.
     *
     * @return the simulationStage
     */
    public Stage getSimulationStage() {
        return simulationStage;
    }

    /**
     * Sets the simulation stage.
     *
     * @param simulationStage the stage to set
     */
    public void setSimulationStage(Stage simulationStage) {
        this.simulationStage = simulationStage;
    }
}