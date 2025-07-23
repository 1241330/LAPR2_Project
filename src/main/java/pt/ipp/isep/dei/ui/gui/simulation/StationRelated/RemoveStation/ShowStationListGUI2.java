package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.RemoveStation;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation.ShowStationListController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.dto.StationDTO;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for displaying the list of stations to remove.
 * Handles user interactions for selecting and removing stations.
 */
public class ShowStationListGUI2 implements Initializable {

    private ShowStationListController controller;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<StationDTO> stationTableView;

    @FXML
    private TableColumn<StationDTO, String> nameColumn;

    @FXML
    private TableColumn<StationDTO, String> positionColumn;

    /**
     * Initializes the controller class.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowStationListController();
    }

    /**
     * Sets the simulation and updates the GUI with the station list.
     *
     * @param simulation The simulation to set.
     */
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        updateGUI();
    }

    /**
     * Updates the table view with the list of stations.
     */
    private void updateGUI() {
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        positionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPosition())
        );
        stationTableView.setItems(controller.getStationList());
    }

    /**
     * Handles the action when the cancel button is clicked.
     * Navigates back to the StationRelated GUI.
     *
     * @param event The action event.
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
     * Handles the action when the select button is clicked.
     * Navigates to the confirmation screen for removing the selected station.
     *
     * @param event The action event.
     */
    public void handleSelectButtonOnAction(ActionEvent event) {
        StationDTO selectedItem = stationTableView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            errorLabel.setText("You need to select a Station first!!");
            return;
        }

        Station station = controller.getStationByDTO(selectedItem);
        if (station == null) {
            errorLabel.setText("Station could not be found!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/RemoveStation/RemoveStationConfirm.fxml"));
            Parent root = loader.load();

            RemoveStationConfirmGUI removeStationConfirmGUI = loader.getController();
            removeStationConfirmGUI.setData(controller.getSimulation(), station);
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
     * @return The ShowStationListController.
     */
    public ShowStationListController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The ShowStationListController to set.
     */
    public void setController(ShowStationListController controller) {
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

    /**
     * Gets the station table view.
     *
     * @return The station table view.
     */
    public TableView<StationDTO> getStationTableView() {
        return stationTableView;
    }

    /**
     * Sets the station table view.
     *
     * @param stationTableView The station table view to set.
     */
    public void setStationTableView(TableView<StationDTO> stationTableView) {
        this.stationTableView = stationTableView;
    }

    /**
     * Gets the name column.
     *
     * @return The name column.
     */
    public TableColumn<StationDTO, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The name column to set.
     */
    public void setNameColumn(TableColumn<StationDTO, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the position column.
     *
     * @return The position column.
     */
    public TableColumn<StationDTO, String> getPositionColumn() {
        return positionColumn;
    }

    /**
     * Sets the position column.
     *
     * @param positionColumn The position column to set.
     */
    public void setPositionColumn(TableColumn<StationDTO, String> positionColumn) {
        this.positionColumn = positionColumn;
    }
}