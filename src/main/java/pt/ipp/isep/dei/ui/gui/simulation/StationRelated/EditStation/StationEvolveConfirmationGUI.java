package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.StationEvolveConfirmationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for confirming the evolution of a station.
 * <p>
 * Handles user confirmation for evolving a station and manages the corresponding UI actions.
 */
public class StationEvolveConfirmationGUI {

    @FXML
    private Label errorLabel;

    @FXML
    private Stage stage;

    private StationEvolveConfirmationController controller;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new StationEvolveConfirmationController();
    }

    /**
     * Sets the simulation, station, and stage for this confirmation dialog.
     *
     * @param simulation The current {@link Simulation} instance.
     * @param station The {@link Station} to be evolved.
     * @param stage The current {@link Stage} of the dialog.
     */
    public void setData(Simulation simulation, Station station, Stage stage) {
        controller.setSimulation(simulation);
        controller.setStation(station);
        this.stage = stage;
    }

    /**
     * Handles the action when the "Yes" button is clicked.
     * Should trigger the evolution of the station (implementation pending).
     *
     * @param event The action event triggered by clicking the "Yes" button.
     */
    @FXML
    public void handleYesButton(ActionEvent event) {
        // TODO: Implement the logic to evolve the station and close the dialog if successful.
    }

    /**
     * Handles the action when the "No" button is clicked.
     * Closes the confirmation dialog.
     *
     * @param event The action event triggered by clicking the "No" button.
     */
    @FXML
    public void handleNoButton(ActionEvent event) {
        errorLabel.getScene().getWindow().hide();
    }

    /**
     * Sets the purchase message to be displayed in the error label.
     *
     * @param message The message to display.
     */
    public void setPurchaseMessage(String message) {
        errorLabel.setText(message);
    }

    /**
     * Gets the error label.
     *
     * @return The error {@link Label}.
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the error label.
     *
     * @param errorLabel The error {@link Label} to set.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the current stage.
     *
     * @return The current {@link Stage}.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the current stage.
     *
     * @param stage The {@link Stage} to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the controller.
     *
     * @return The {@link StationEvolveConfirmationController} instance.
     */
    public StationEvolveConfirmationController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The {@link StationEvolveConfirmationController} to set.
     */
    public void setController(StationEvolveConfirmationController controller) {
        this.controller = controller;
    }
}