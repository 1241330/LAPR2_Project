package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.BuildingsPurchaseConfirmationController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.BuildingType;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the popup that confirms the purchase of a building for a station.
 * Handles user interaction and updates the UI accordingly.
 */
public class BuildingsPurchaseConfirmationPopupGUI implements Initializable {

    @FXML
    private Label titleLabel, text1Label, text2Label;

    @FXML
    private Label errorLabel;

    private BuildingsPurchaseConfirmationController controller;
    private Stage previousStage;

    /**
     * Initializes the controller class.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new BuildingsPurchaseConfirmationController();
    }

    /**
     * Sets the data required for the popup.
     *
     * @param map          The map object.
     * @param scenario     The scenario object.
     * @param station      The station where the building will be purchased.
     * @param simulation   The simulation object.
     * @param previousStage The previous stage to return to after closing the popup.
     * @param buildingType The type of building to purchase.
     */
    public void setData(Map map, Scenario scenario, Station station, Simulation simulation, Stage previousStage, BuildingType buildingType) {
        controller.setSimulation(simulation);
        controller.setScenario(scenario);
        controller.setMap(map);
        controller.setStation(station);
        controller.setBuildingType(buildingType);
        this.previousStage = previousStage;
    }

    /**
     * Initializes the popup labels with the provided messages.
     *
     * @param title The title of the popup.
     * @param msg1  The first message to display.
     * @param msg2  The second message to display.
     */
    public void initializeGUIItems(String title, String msg1, String msg2) {
        titleLabel.setText(title);
        text1Label.setText(msg1);
        text2Label.setText(msg2);
    }

    /**
     * Handles the action when the "Yes" button is pressed.
     * Adds the building to the station and returns to the previous screen.
     *
     * @param event The action event triggered by the button.
     */
    @FXML
    public void handleYesButton(ActionEvent event) {
        controller.addBuildingToStation();

        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/EditStationBuildings.fxml"));
            Parent root = loader.load();
            EditStationBuildingsGUI editStationBuildingsGUI = loader.getController();
            editStationBuildingsGUI.setData(controller.getSimulation(), controller.getStation());
            previousStage.setTitle("MABEC - Simulation: Edit Building Station");
            previousStage.setScene(new Scene(root));
            previousStage.centerOnScreen();
            previousStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "No" button is pressed.
     * Closes the popup window.
     *
     * @param event The action event triggered by the button.
     */
    @FXML
    public void handleNoButton(ActionEvent event) {
        closePopup(event);
    }

    /**
     * Closes the popup window.
     *
     * @param event The action event triggered by the button.
     */
    private void closePopup(ActionEvent event) {
        Stage popupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popupStage.close();
    }

    /**
     * Sets the error message to be displayed in the popup.
     *
     * @param message The error message.
     */
    public void setPurchaseMessage(String message) {
        errorLabel.setText(message);
    }

    /**
     * Gets the controller for building purchase confirmation.
     *
     * @return The controller.
     */
    public BuildingsPurchaseConfirmationController getController() {
        return controller;
    }

    /**
     * Sets the controller for building purchase confirmation.
     *
     * @param controller The controller to set.
     */
    public void setController(BuildingsPurchaseConfirmationController controller) {
        this.controller = controller;
    }

    /**
     * Gets the previous stage.
     *
     * @return The previous stage.
     */
    public Stage getPreviousStage() {
        return previousStage;
    }

    /**
     * Sets the previous stage.
     *
     * @param previousStage The previous stage to set.
     */
    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    /**
     * Gets the title label.
     *
     * @return The title label.
     */
    public Label getTitleLabel() {
        return titleLabel;
    }

    /**
     * Sets the title label.
     *
     * @param titleLabel The title label to set.
     */
    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    /**
     * Gets the first text label.
     *
     * @return The first text label.
     */
    public Label getText1Label() {
        return text1Label;
    }

    /**
     * Sets the first text label.
     *
     * @param text1Label The first text label to set.
     */
    public void setText1Label(Label text1Label) {
        this.text1Label = text1Label;
    }

    /**
     * Gets the second text label.
     *
     * @return The second text label.
     */
    public Label getText2Label() {
        return text2Label;
    }

    /**
     * Sets the second text label.
     *
     * @param text2Label The second text label to set.
     */
    public void setText2Label(Label text2Label) {
        this.text2Label = text2Label;
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