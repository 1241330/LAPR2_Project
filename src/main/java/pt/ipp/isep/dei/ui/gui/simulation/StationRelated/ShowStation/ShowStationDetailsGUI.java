package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.ShowStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation.ShowStationDetailsController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.dto.StationDTO;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class responsible for displaying the details of a selected station.
 * Handles the initialization, data population, and navigation actions.
 */
public class ShowStationDetailsGUI implements Initializable {

    private ShowStationDetailsController controller;

    @FXML
    private Label stationNameLabel;

    @FXML
    private Label stationTypeLabel;

    @FXML
    private Label stationPositionLabel;

    @FXML
    private Label stationDirectionLabel;

    @FXML
    private ListView<String> stationBuildingsListView;

    @FXML
    private ListView<String> stationCargoesListView;

    /**
     * Initializes the controller for this GUI.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowStationDetailsController();
    }

    /**
     * Sets the simulation and selected station, then displays their details.
     *
     * @param simulation The current simulation.
     * @param selectedStation The selected station to display.
     */
    public void setData(Simulation simulation, Station selectedStation) {
        controller.setSimulation(simulation);
        controller.setSelectedStation(selectedStation);
        showItems();
    }

    /**
     * Populates the GUI components with the station details.
     */
    private void showItems() {
        StationDTO dto = controller.getStationDTO();

        stationNameLabel.setText(dto.getName());
        stationTypeLabel.setText(dto.getType());
        stationPositionLabel.setText(dto.getPosition());
        stationDirectionLabel.setText(dto.getDirection());

        stationBuildingsListView.setItems(controller.getStationBuildings());
        stationCargoesListView.setItems(controller.getStationCargos());
    }

    /**
     * Handles the action of the back button, returning to the station list screen.
     *
     * @param event The action event triggered by the user.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/ShowStation/ShowStationList.fxml"));
            Parent root = loader.load();

            ShowStationListGUI showStationListGUI = loader.getController();
            showStationListGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Show Stations");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the cancel button, returning to the main station related screen.
     *
     * @param event The action event triggered by the user.
     */
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
     * Gets the controller.
     *
     * @return The ShowStationDetailsController.
     */
    public ShowStationDetailsController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The ShowStationDetailsController to set.
     */
    public void setController(ShowStationDetailsController controller) {
        this.controller = controller;
    }

    /**
     * Gets the station name label.
     *
     * @return The station name label.
     */
    public Label getStationNameLabel() {
        return stationNameLabel;
    }

    /**
     * Sets the station name label.
     *
     * @param stationNameLabel The station name label to set.
     */
    public void setStationNameLabel(Label stationNameLabel) {
        this.stationNameLabel = stationNameLabel;
    }

    /**
     * Gets the station type label.
     *
     * @return The station type label.
     */
    public Label getStationTypeLabel() {
        return stationTypeLabel;
    }

    /**
     * Sets the station type label.
     *
     * @param stationTypeLabel The station type label to set.
     */
    public void setStationTypeLabel(Label stationTypeLabel) {
        this.stationTypeLabel = stationTypeLabel;
    }

    /**
     * Gets the station position label.
     *
     * @return The station position label.
     */
    public Label getStationPositionLabel() {
        return stationPositionLabel;
    }

    /**
     * Sets the station position label.
     *
     * @param stationPositionLabel The station position label to set.
     */
    public void setStationPositionLabel(Label stationPositionLabel) {
        this.stationPositionLabel = stationPositionLabel;
    }

    /**
     * Gets the station direction label.
     *
     * @return The station direction label.
     */
    public Label getStationDirectionLabel() {
        return stationDirectionLabel;
    }

    /**
     * Sets the station direction label.
     *
     * @param stationDirectionLabel The station direction label to set.
     */
    public void setStationDirectionLabel(Label stationDirectionLabel) {
        this.stationDirectionLabel = stationDirectionLabel;
    }

    /**
     * Gets the station buildings list view.
     *
     * @return The station buildings list view.
     */
    public ListView<String> getStationBuildingsListView() {
        return stationBuildingsListView;
    }

    /**
     * Sets the station buildings list view.
     *
     * @param stationBuildingsListView The station buildings list view to set.
     */
    public void setStationBuildingsListView(ListView<String> stationBuildingsListView) {
        this.stationBuildingsListView = stationBuildingsListView;
    }

    /**
     * Gets the station cargoes list view.
     *
     * @return The station cargoes list view.
     */
    public ListView<String> getStationCargoesListView() {
        return stationCargoesListView;
    }

    /**
     * Sets the station cargoes list view.
     *
     * @param stationCargoesListView The station cargoes list view to set.
     */
    public void setStationCargoesListView(ListView<String> stationCargoesListView) {
        this.stationCargoesListView = stationCargoesListView;
    }
}