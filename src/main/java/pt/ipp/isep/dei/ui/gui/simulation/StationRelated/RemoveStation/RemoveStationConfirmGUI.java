package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.RemoveStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.RemoveStation.RemoveStationConfirmController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for confirming the removal of a station.
 * <p>
 * Handles user actions for confirming or cancelling the removal of a station,
 * and manages the corresponding UI transitions.
 */
public class RemoveStationConfirmGUI implements Initializable {

    private RemoveStationConfirmController controller;

    @FXML
    private Label stationNameLabel;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new RemoveStationConfirmController();
    }

    /**
     * Sets the simulation and selected station for this confirmation dialog.
     * Updates the label with the station name.
     *
     * @param simulation The current {@link Simulation} instance.
     * @param selectedStation The {@link Station} to be removed.
     */
    public void setData(Simulation simulation, Station selectedStation) {
        controller.setSimulation(simulation);
        controller.setStation(selectedStation);
        stationNameLabel.setText(controller.getStationName());
    }

    /**
     * Handles the action when the "Back" button is clicked.
     * Returns to the station selection list.
     *
     * @param event The action event triggered by clicking the "Back" button.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/RemoveStation/ShowStationList2.fxml"));
            Parent root = loader.load();
            ShowStationListGUI2 showStationListGUI2 = loader.getController();
            showStationListGUI2.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "No" button is clicked.
     * Returns to the main station related menu.
     *
     * @param event The action event triggered by clicking the "No" button.
     */
    public void handleNoButtonOnAction(ActionEvent event) {
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
     * Handles the action when the "Yes" button is clicked.
     * Removes the selected station and returns to the main station related menu.
     *
     * @param event The action event triggered by clicking the "Yes" button.
     */
    public void handleYesButtonOnAction(ActionEvent event) {
        controller.removeSelectedStation();
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
     * Gets the controller.
     *
     * @return The {@link RemoveStationConfirmController} instance.
     */
    public RemoveStationConfirmController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The {@link RemoveStationConfirmController} to set.
     */
    public void setController(RemoveStationConfirmController controller) {
        this.controller = controller;
    }

    /**
     * Gets the station name label.
     *
     * @return The {@link Label} displaying the station name.
     */
    public Label getStationNameLabel() {
        return stationNameLabel;
    }

    /**
     * Sets the station name label.
     *
     * @param stationNameLabel The {@link Label} to set for the station name.
     */
    public void setStationNameLabel(Label stationNameLabel) {
        this.stationNameLabel = stationNameLabel;
    }
}