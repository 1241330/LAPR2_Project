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
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation.EditName.EditStationNameGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for editing a station in the simulation.
 * Handles navigation and actions related to editing station properties.
 */
public class EditStationGUI implements Initializable {

    private EditStationController controller;

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
        controller = new EditStationController();
    }

    /**
     * Sets the simulation and selected station for editing.
     *
     * @param simulation The current simulation instance.
     * @param selectedStation The station to be edited.
     */
    public void setData(Simulation simulation, Station selectedStation) {
        controller.setSimulation(simulation);
        controller.setStation(selectedStation);
    }

    /**
     * Handles the action for the exit button.
     * Navigates back to the StationRelated screen.
     *
     * @param event The action event triggered by clicking the exit button.
     */
    public void handleExitButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/StationRelated.fxml"));
            Parent root = loader.load();

            StationRelatedGUI stationRelatedGUI = loader.getController();
            stationRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: StationRelated Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action for editing the station name.
     * Navigates to the EditStationName screen.
     *
     * @param event The action event triggered by clicking the edit name button.
     */
    public void handleEditNameOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/EditStationName.fxml"));
            Parent root = loader.load();

            EditStationNameGUI editStationNameGUI = loader.getController();
            editStationNameGUI.setData(controller.getSimulation(), controller.getStation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Change StationRelated Name");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action for adding a building to the station.
     * Navigates to the EditStationBuildings screen.
     *
     * @param event The action event triggered by clicking the add building button.
     */
    public void handleAddBuildingOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/EditStationBuildings.fxml"));
            Parent root = loader.load();

            EditStationBuildingsGUI editStationBuildingsGUI = loader.getController();
            editStationBuildingsGUI.setData(controller.getSimulation(), controller.getStation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Add Buildings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action for evolving the station type.
     * Navigates to the EditStationType screen if the station is not at max level.
     * Shows an error message if the station is already at max level.
     *
     * @param event The action event triggered by clicking the evolve station type button.
     */
    public void handleEvolveStationTypeButtonOnAction(ActionEvent event) {
        if (controller.verifyIfStationIsMaxLevel()) {
            errorLabel.setText("StationRelated Is already at max Level");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/EditStationType.fxml"));
            Parent root = loader.load();

            EditStationTypeGUI editStationTypeGUI = loader.getController();
            editStationTypeGUI.setData(controller.getSimulation(), controller.getStation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Change StationType");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action for the back button.
     * Navigates to the ShowStationList3 screen.
     *
     * @param event The action event triggered by clicking the back button.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/ShowStationList3.fxml"));
            Parent root = loader.load();

            ShowStationListGUI3 showStationListGUI3 = loader.getController();
            showStationListGUI3.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: StationRelated Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Gets the controller.
     *
     * @return The EditStationController instance.
     */
    public EditStationController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The EditStationController to set.
     */
    public void setController(EditStationController controller) {
        this.controller = controller;
    }
}