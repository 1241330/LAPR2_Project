package pt.ipp.isep.dei.ui.gui.simulation;

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
import pt.ipp.isep.dei.controller.simulation.SimulationRelated.ChooseSimulationController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation.InitializeSimulationGUI;
import pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation.TypesSimulationSelectionGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for choosing a simulation to run or delete.
 * Handles user interactions for selecting, running, or deleting simulations.
 */
public class ChooseSimulationGUI implements Initializable {

    /** Controller for managing simulation selection logic. */
    private ChooseSimulationController controller;

    /** Flag indicating whether to run or delete the selected simulation. */
    private boolean runSimulation;

    /** ChoiceBox displaying available simulations. */
    @FXML
    private ChoiceBox<String> simulationsChoiceBox;

    /** Label for displaying error messages. */
    @FXML
    private Label errorLabel = new Label("");

    /**
     * Initializes the controller and sets up the confirmation action to run later on the JavaFX application thread.
     *
     * @param url the location used to resolve relative paths for the root object, or null if unknown
     * @param resourceBundle the resources used to localize the root object, or null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseSimulationController();
        javafx.application.Platform.runLater(this::confirmationContinueAction);
    }

    /**
     * Sets the scenario and whether to run or delete a simulation, and populates the choice box with available simulations.
     *
     * @param scenario the scenario to be used
     * @param runSimulation true if the simulation should be run, false if it should be deleted
     */
    public void setInformation(Scenario scenario, boolean runSimulation) {
        controller.setActualScenario(scenario);
        simulationsChoiceBox.getItems().addAll(controller.getListOfNamesSimulations());
        this.runSimulation = runSimulation;
    }

    /**
     * Checks if the list of simulations is not empty to confirm continuation.
     *
     * @return true if there are simulations available, false otherwise
     */
    public boolean confirmationContinueAction() {
        return !controller.listOfSimulationsIsEmpty();
    }

    /**
     * Handles the action of the back button, returning to the types of simulation selection screen.
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
     * Handles the action of selecting a simulation, either to run or delete it, depending on the current mode.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleSelectSimulationButton(ActionEvent event) {
        if (simulationsChoiceBox.getValue() == null) {
            errorLabel.setText("Select a Simulation to Continue!");
            return;
        }
        controller.setSelectedSimulation(simulationsChoiceBox.getSelectionModel().getSelectedIndex());
        if (runSimulation) {
            runSimulationGUI(event);
        } else {
            deleteSimulationGUI(event);
        }
    }

    /**
     * Loads and displays the GUI for running the selected simulation.
     *
     * @param event the action event triggered by the user
     */
    private void runSimulationGUI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/InitializeSimulation.fxml"));
            Parent root = loader.load();
            InitializeSimulationGUI initializeSimulationGUI = loader.getController();
            initializeSimulationGUI.setInformation(controller.getSelectedSimulation(), false);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Run Simulation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the GUI for deleting the selected simulation.
     *
     * @param event the action event triggered by the user
     */
    private void deleteSimulationGUI(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/DeleteSimulation.fxml"));
            Parent root = loader.load();
            DeleteSimulationGUI deleteSimulationGUI = loader.getController();
            deleteSimulationGUI.setInformation(controller.getSelectedSimulation(), (Stage) ((Node) event.getSource()).getScene().getWindow());
            Stage stage = new Stage();
            stage.setTitle("MABEC - Delete Simulation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the controller for managing simulation selection logic.
     *
     * @return the ChooseSimulationController instance
     */
    public ChooseSimulationController getController() {
        return controller;
    }

    /**
     * Sets the controller for managing simulation selection logic.
     *
     * @param controller the ChooseSimulationController to set
     */
    public void setController(ChooseSimulationController controller) {
        this.controller = controller;
    }

    /**
     * Gets the flag indicating whether to run or delete the selected simulation.
     *
     * @return true if running simulation, false if deleting
     */
    public boolean isRunSimulation() {
        return runSimulation;
    }

    /**
     * Sets the flag indicating whether to run or delete the selected simulation.
     *
     * @param runSimulation true to run, false to delete
     */
    public void setRunSimulation(boolean runSimulation) {
        this.runSimulation = runSimulation;
    }

    /**
     * Gets the ChoiceBox displaying available simulations.
     *
     * @return the simulationsChoiceBox
     */
    public ChoiceBox<String> getSimulationsChoiceBox() {
        return simulationsChoiceBox;
    }

    /**
     * Sets the ChoiceBox displaying available simulations.
     *
     * @param simulationsChoiceBox the ChoiceBox to set
     */
    public void setSimulationsChoiceBox(ChoiceBox<String> simulationsChoiceBox) {
        this.simulationsChoiceBox = simulationsChoiceBox;
    }

    /**
     * Gets the label for displaying error messages.
     *
     * @return the errorLabel
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the label for displaying error messages.
     *
     * @param errorLabel the Label to set
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }
}