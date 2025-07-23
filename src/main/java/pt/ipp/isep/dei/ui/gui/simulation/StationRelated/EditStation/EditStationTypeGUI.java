package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationTypeController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for editing the station type in the simulation.
 * Handles the logic for evolving a station to a terminal or another station type,
 * as well as updating the direction and managing the UI state.
 */
public class EditStationTypeGUI implements Initializable {

    private EditStationTypeController controller;

    @FXML
    private Button stationEvolveButton;

    @FXML
    private Button terminalEvolveButton;

    @FXML
    private Button northButton;

    @FXML
    private Button southButton;

    @FXML
    private Button eastButton;

    @FXML
    private Button westButton;

    @FXML
    private Label terminalCostLabel;

    @FXML
    private Label stationCostLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Label evolveToLabel;

    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new EditStationTypeController();
    }

    /**
     * Sets the simulation and station data for this GUI.
     * Initializes the controller and updates the UI items.
     *
     * @param simulation The current simulation instance.
     * @param station The station to be edited.
     */
    public void setData(Simulation simulation, Station station) {
        controller.setSimulation(simulation);
        controller.setStation(station);
        controller.setStationType(station.getStationType());
        setActiveItems();
    }

    /**
     * Updates the UI elements based on the current station type and costs.
     */
    private void setActiveItems() {
        terminalCostLabel.setText(String.valueOf(controller.getTerminalEvolveCost()) + "\uD83D\uDCB0");

        if (controller.stationIsOfTypeStation()) {
            stationCostLabel.setDisable(true);
            stationEvolveButton.setVisible(false);
            return;
        }

        stationCostLabel.setText(String.valueOf(controller.getStationEvolveCost()) + "\uD83D\uDCB0");
    }

    /**
     * Handles the action for evolving the station to a terminal.
     * Checks if there is enough money and updates the station type.
     *
     * @param event The action event triggered by clicking the terminal evolve button.
     */
    public void handleTerminalEvolveButtonOnAction(ActionEvent event) {
        if (!controller.checkIfHasMoney(controller.getTerminalEvolveCost())) {
            errorLabel.setText("You Don't have enough money!!");
            return;
        }

        controller.setStationTypeToTerminal();
        returnToLastMenu(event);
    }

    /**
     * Handles the action for evolving the station to a station type.
     * Checks if there is enough money and updates the UI for direction selection.
     *
     * @param event The action event triggered by clicking the station evolve button.
     */
    public void handleStationEvolveButtonOnAction(ActionEvent event) {
        if (!controller.checkIfHasMoney(controller.getStationEvolveCost())) {
            errorLabel.setText("You Don't have enough money!!");
            return;
        }

        controller.setStationTypeToStation();

        stationEvolveButton.setVisible(false);
        terminalEvolveButton.setVisible(false);
        stationCostLabel.setVisible(false);
        terminalCostLabel.setVisible(false);
        costLabel.setVisible(false);
        evolveToLabel.setVisible(false);

        northButton.setVisible(true);
        southButton.setVisible(true);
        eastButton.setVisible(true);
        westButton.setVisible(true);
    }

    /**
     * Handles the action for selecting the North direction.
     * Sets the station direction and returns to the previous menu.
     *
     * @param event The action event triggered by clicking the north button.
     */
    @FXML
    public void handleNorthButtonOnAction(ActionEvent event) {
        controller.stationDirection("North");
        returnToLastMenu(event);
    }

    /**
     * Handles the action for selecting the South direction.
     * Sets the station direction and returns to the previous menu.
     *
     * @param event The action event triggered by clicking the south button.
     */
    @FXML
    public void handleSouthButtonOnAction(ActionEvent event) {
        controller.stationDirection("South");
        returnToLastMenu(event);
    }

    /**
     * Handles the action for selecting the East direction.
     * Sets the station direction and returns to the previous menu.
     *
     * @param event The action event triggered by clicking the east button.
     */
    @FXML
    public void handleEastButtonOnAction(ActionEvent event) {
        controller.stationDirection("East");
        returnToLastMenu(event);
    }

    /**
     * Handles the action for selecting the West direction.
     * Sets the station direction and returns to the previous menu.
     *
     * @param event The action event triggered by clicking the west button.
     */
    @FXML
    public void handleWestButtonOnAction(ActionEvent event) {
        controller.stationDirection("West");
        returnToLastMenu(event);
    }

    /**
     * Handles the action for the back button.
     * Resets the station type and returns to the previous menu.
     *
     * @param event The action event triggered by clicking the back button.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        controller.setStationTypeDefault();
        returnToLastMenu(event);
    }

    /**
     * Returns to the previous menu (EditStationGUI).
     *
     * @param event The action event that triggered the navigation.
     */
    private void returnToLastMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/EditStation.fxml"));
            Parent root = loader.load();

            EditStationGUI editStationGUI = loader.getController();
            editStationGUI.setData(controller.getSimulation(), controller.getStation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose what to Edit");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the controller.
     *
     * @return The EditStationTypeController instance.
     */
    public EditStationTypeController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The EditStationTypeController to set.
     */
    public void setController(EditStationTypeController controller) {
        this.controller = controller;
    }

    /**
     * Gets the error label.
     *
     * @return The error label.
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the error label.
     *
     * @param errorLabel The error label to set.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }
}