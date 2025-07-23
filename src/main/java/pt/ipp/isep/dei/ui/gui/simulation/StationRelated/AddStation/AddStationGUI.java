package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.AddStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for adding a new station in the simulation.
 * Handles user interactions and navigation between screens.
 */
public class AddStationGUI implements Initializable {

    private AddStationController controller;

    @FXML
    private Label errorLabel;

    @FXML
    private Label depotRadiusLabel;

    @FXML
    private Label stationRadiusLabel;

    @FXML
    private Label terminalRadiusLabel;

    @FXML
    private Label depotPriceLabel;

    @FXML
    private Label stationPriceLabel;

    @FXML
    private Label terminalPriceLabel;

    /**
     * Initializes the controller class.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new AddStationController();
    }

    /**
     * Sets the simulation instance and initializes the GUI items.
     *
     * @param simulation The simulation instance.
     */
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the labels for station radius and price.
     */
    private void initializeGUIItems() {
        depotRadiusLabel.setText(controller.getStationTypeRadius(StationType.DEPOT) + " x " + controller.getStationTypeRadius(StationType.DEPOT));
        stationRadiusLabel.setText(controller.getStationTypeRadius(StationType.STATION) + " x " + controller.getStationTypeRadius(StationType.STATION));
        terminalRadiusLabel.setText(controller.getStationTypeRadius(StationType.TERMINAL) + " x " + controller.getStationTypeRadius(StationType.TERMINAL));

        depotPriceLabel.setText(controller.getStationTypePrice(StationType.DEPOT) + " \uD83D\uDCB0");
        stationPriceLabel.setText(controller.getStationTypePrice(StationType.STATION) + " \uD83D\uDCB0");
        terminalPriceLabel.setText(controller.getStationTypePrice(StationType.TERMINAL) + " \uD83D\uDCB0");
    }

    /**
     * Handles the selection of the Depot station type.
     * Navigates to the position selection screen if the user has enough balance.
     *
     * @param event The action event triggered by the button.
     */
    public void handleDepotSelectButtonOnAction(ActionEvent event) {
        controller.setStationType(StationType.DEPOT);
        goToAddStationPosition(event);
    }

    /**
     * Handles the selection of the Terminal station type.
     * Navigates to the position selection screen if the user has enough balance.
     *
     * @param event The action event triggered by the button.
     */
    public void handleTerminalSelectButtonOnAction(ActionEvent event) {
        controller.setStationType(StationType.TERMINAL);
        goToAddStationPosition(event);
    }

    /**
     * Navigates to the position selection screen for the selected station type.
     * Displays an error if the user does not have enough balance.
     *
     * @param event The action event triggered by the button.
     */
    private void goToAddStationPosition(ActionEvent event) {
        if (!controller.verifyIfYouHaveMoney()) {
            errorLabel.setText("Your Balance is not enough!!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStationPosition.fxml"));
            Parent root = loader.load();
            AddStationPositionGUI addStationPositionGUI = loader.getController();
            addStationPositionGUI.setInformation(controller.getSimulation(), controller.getStationType(), null);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Name");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the selection of the Station station type.
     * Navigates to the direction selection screen if the user has enough balance.
     *
     * @param event The action event triggered by the button.
     */
    public void handleStationSelectButtonOnAction(ActionEvent event) {
        controller.setStationType(StationType.STATION);
        goToAddStationDirection(event);
    }

    /**
     * Navigates to the direction selection screen for the selected station type.
     * Displays an error if the user does not have enough balance.
     *
     * @param event The action event triggered by the button.
     */
    private void goToAddStationDirection(ActionEvent event) {
        if (!controller.verifyIfYouHaveMoney()) {
            errorLabel.setText("Your Balance is not enough!!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStationDirection.fxml"));
            Parent root = loader.load();

            AddStationDirectionGUI addStationDirectionGUI = loader.getController();
            addStationDirectionGUI.setSimulation(controller.getSimulation(), controller.getStationType());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Direction");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action for the Back button.
     * Navigates to the previous screen (Station Related).
     *
     * @param event The action event triggered by the button.
     */
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/StationRelated.fxml"));
            Parent root = loader.load();
            StationRelatedGUI stationRelatedGUI = loader.getController();
            stationRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Station Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action for the Exit button.
     * Navigates to the pause menu screen of the simulation.
     *
     * @param event The action event triggered by the button.
     */
    public void handleExitButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/PauseMenu.fxml"));
            Parent root = loader.load();

            PauseMenuGUI pauseMenuGUI = loader.getController();
            pauseMenuGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Pause Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the controller.
     *
     * @return The AddStationController instance.
     */
    public AddStationController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The AddStationController instance.
     */
    public void setController(AddStationController controller) {
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
     * @param errorLabel The error label.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the depot radius label.
     *
     * @return The depot radius label.
     */
    public Label getDepotRadiusLabel() {
        return depotRadiusLabel;
    }

    /**
     * Sets the depot radius label.
     *
     * @param depotRadiusLabel The depot radius label.
     */
    public void setDepotRadiusLabel(Label depotRadiusLabel) {
        this.depotRadiusLabel = depotRadiusLabel;
    }

    /**
     * Gets the station radius label.
     *
     * @return The station radius label.
     */
    public Label getStationRadiusLabel() {
        return stationRadiusLabel;
    }

    /**
     * Sets the station radius label.
     *
     * @param stationRadiusLabel The station radius label.
     */
    public void setStationRadiusLabel(Label stationRadiusLabel) {
        this.stationRadiusLabel = stationRadiusLabel;
    }

    /**
     * Gets the terminal radius label.
     *
     * @return The terminal radius label.
     */
    public Label getTerminalRadiusLabel() {
        return terminalRadiusLabel;
    }

    /**
     * Sets the terminal radius label.
     *
     * @param terminalRadiusLabel The terminal radius label.
     */
    public void setTerminalRadiusLabel(Label terminalRadiusLabel) {
        this.terminalRadiusLabel = terminalRadiusLabel;
    }

    /**
     * Gets the depot price label.
     *
     * @return The depot price label.
     */
    public Label getDepotPriceLabel() {
        return depotPriceLabel;
    }

    /**
     * Sets the depot price label.
     *
     * @param depotPriceLabel The depot price label.
     */
    public void setDepotPriceLabel(Label depotPriceLabel) {
        this.depotPriceLabel = depotPriceLabel;
    }

    /**
     * Gets the station price label.
     *
     * @return The station price label.
     */
    public Label getStationPriceLabel() {
        return stationPriceLabel;
    }

    /**
     * Sets the station price label.
     *
     * @param stationPriceLabel The station price label.
     */
    public void setStationPriceLabel(Label stationPriceLabel) {
        this.stationPriceLabel = stationPriceLabel;
    }

    /**
     * Gets the terminal price label.
     *
     * @return The terminal price label.
     */
    public Label getTerminalPriceLabel() {
        return terminalPriceLabel;
    }

    /**
     * Sets the terminal price label.
     *
     * @param terminalPriceLabel The terminal price label.
     */
    public void setTerminalPriceLabel(Label terminalPriceLabel) {
        this.terminalPriceLabel = terminalPriceLabel;
    }
}