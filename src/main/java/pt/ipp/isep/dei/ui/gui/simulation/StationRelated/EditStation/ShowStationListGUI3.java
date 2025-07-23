package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.ShowStationListController3;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for displaying and selecting stations in a table view.
 * <p>
 * Allows the user to view a list of stations, select one for editing, or cancel and return to the previous menu.
 * Integrates with JavaFX and communicates with the corresponding controller.
 */
public class ShowStationListGUI3 implements Initializable {

    private ShowStationListController3 controller;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<Station> stationTableView;

    @FXML
    private TableColumn<Station, String> nameColumn;

    @FXML
    private TableColumn<Station, String> positionColumn;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowStationListController3();
    }

    /**
     * Sets the current simulation instance and updates the GUI accordingly.
     *
     * @param simulation The current {@link Simulation} instance.
     */
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        updateGUI();
    }

    /**
     * Updates the table view with the list of stations and configures the columns.
     * Also disables the horizontal scroll bar for better UI experience.
     */
    private void updateGUI() {
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        positionColumn.setCellValueFactory(cellData -> {
            int x = cellData.getValue().getPosition().getX() + 1;
            int y = cellData.getValue().getPosition().getY() + 1;
            return new SimpleStringProperty("(x = " + x + ", y = " + y + ")");
        });

        // Deactivate horizontal scroll bar
        Platform.runLater(() -> {
            stationTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : stationTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                }
            }
        });

        stationTableView.setItems(controller.getStationList());
    }

    /**
     * Handles the action for the cancel button.
     * Returns to the previous menu (StationRelatedGUI).
     *
     * @param event The action event triggered by clicking the cancel button.
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
     * Handles the action for the select button.
     * Loads the edit station GUI for the selected station.
     * If no station is selected, displays an error message.
     *
     * @param event The action event triggered by clicking the select button.
     */
    public void handleSelectButtonOnAction(ActionEvent event) {
        Object selectedItem = stationTableView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            errorLabel.setText("You need to select a Station first!!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/EditStation.fxml"));
            Parent root = loader.load();

            EditStationGUI editStationGUI = loader.getController();
            editStationGUI.setData(controller.getSimulation(), (Station) selectedItem);
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
     * @return The {@link ShowStationListController3} instance.
     */
    public ShowStationListController3 getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The {@link ShowStationListController3} to set.
     */
    public void setController(ShowStationListController3 controller) {
        this.controller = controller;
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
     * Gets the station table view.
     *
     * @return The {@link TableView} of stations.
     */
    public TableView<Station> getStationTableView() {
        return stationTableView;
    }

    /**
     * Sets the station table view.
     *
     * @param stationTableView The {@link TableView} to set.
     */
    public void setStationTableView(TableView<Station> stationTableView) {
        this.stationTableView = stationTableView;
    }

    /**
     * Gets the name column.
     *
     * @return The {@link TableColumn} for station names.
     */
    public TableColumn<Station, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The {@link TableColumn} to set for station names.
     */
    public void setNameColumn(TableColumn<Station, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the position column.
     *
     * @return The {@link TableColumn} for station positions.
     */
    public TableColumn<Station, String> getPositionColumn() {
        return positionColumn;
    }

    /**
     * Sets the position column.
     *
     * @param positionColumn The {@link TableColumn} to set for station positions.
     */
    public void setPositionColumn(TableColumn<Station, String> positionColumn) {
        this.positionColumn = positionColumn;
    }
}