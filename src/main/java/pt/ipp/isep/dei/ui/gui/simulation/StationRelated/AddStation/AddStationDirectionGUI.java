package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.AddStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationDirectionController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class responsible for handling the direction selection when adding a new station.
 * Allows the user to choose a direction (North, South, East, West) and navigate to the next step,
 * or cancel/back to previous screens.
 */
public class AddStationDirectionGUI implements Initializable {

    private AddStationDirectionController controller;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url not used
     * @param resourceBundle not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new AddStationDirectionController();
    }

    /**
     * Sets the simulation and station type for the controller.
     *
     * @param simulation the current simulation instance
     * @param stationType the type of station to add
     */
    public void setSimulation(Simulation simulation, StationType stationType) {
        controller.setSimulation(simulation);
        controller.setStationType(stationType);
    }

    /**
     * Handles the action when the "North" button is pressed.
     * Proceeds to the next scenario with the "North" direction.
     *
     * @param event the action event
     */
    @FXML
    public void handleNorthButtonOnAction(ActionEvent event) {
        goToNextScenario(event, "North");
    }

    /**
     * Handles the action when the "South" button is pressed.
     * Proceeds to the next scenario with the "South" direction.
     *
     * @param event the action event
     */
    @FXML
    public void handleSouthButtonOnAction(ActionEvent event) {
        goToNextScenario(event, "South");
    }

    /**
     * Handles the action when the "East" button is pressed.
     * Proceeds to the next scenario with the "East" direction.
     *
     * @param event the action event
     */
    @FXML
    public void handleEastButtonOnAction(ActionEvent event) {
        goToNextScenario(event, "East");
    }

    /**
     * Handles the action when the "West" button is pressed.
     * Proceeds to the next scenario with the "West" direction.
     *
     * @param event the action event
     */
    @FXML
    public void handleWestButtonOnAction(ActionEvent event) {
        goToNextScenario(event, "West");
    }

    /**
     * Navigates to the next scenario, passing the selected direction.
     *
     * @param event the action event
     * @param direction the selected direction
     */
    private void goToNextScenario(ActionEvent event, String direction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStationPosition.fxml"));
            Parent root = loader.load();

            AddStationPositionGUI addStationPositionGUI = loader.getController();
            addStationPositionGUI.setInformation(controller.getSimulation(), controller.getStationType(), direction);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Name");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Cancel" button is pressed.
     * Returns to the station related menu.
     *
     * @param event the action event
     */
    @FXML
    public void handleCancelButtonOnAction(ActionEvent event) {
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
     * Handles the action when the "Back" button is pressed.
     * Returns to the previous add station screen.
     *
     * @param event the action event
     */
    @FXML
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStation.fxml"));
            Parent root = loader.load();

            AddStationGUI addStationGUI = loader.getController();
            addStationGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Add Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the controller.
     *
     * @return the AddStationDirectionController instance
     */
    public AddStationDirectionController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller the AddStationDirectionController instance
     */
    public void setController(AddStationDirectionController controller) {
        this.controller = controller;
    }
}