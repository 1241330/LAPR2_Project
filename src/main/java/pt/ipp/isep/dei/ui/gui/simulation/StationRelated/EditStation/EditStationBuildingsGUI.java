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
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationBuildingsController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.BuildingType;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for editing station buildings in a simulation.
 * <p>
 * Handles the display and purchase of available building types for a station.
 */
public class EditStationBuildingsGUI implements Initializable {

    private EditStationBuildingsController controller;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<BuildingType> buildingTableView;

    @FXML
    private TableColumn<BuildingType, String> nameColumn;

    @FXML
    private TableColumn<BuildingType, String> costColumn;

    /**
     * Initializes the controller and sets up the business logic controller.
     *
     * @param url            The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new EditStationBuildingsController();
    }

    /**
     * Sets the simulation and station data for the GUI and updates the display.
     *
     * @param simulation The current simulation.
     * @param station    The station being edited.
     */
    public void setData(Simulation simulation, Station station) {
        controller.setSimulation(simulation);
        controller.setStation(station);
        updateGUI();
    }

    /**
     * Updates the GUI elements, including table columns and available building types.
     * Also disables the horizontal scroll bar for the table.
     */
    private void updateGUI() {
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        costColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getConstructionCost() + " \uD83D\uDCB0")
        );

        // Deactivate horizontal scroll bar
        Platform.runLater(() -> {
            buildingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : buildingTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                }
            }
        });

        buildingTableView.setItems(controller.getAvailableBuildingTypes());
    }

    /**
     * Handles the action triggered by the "Back" button.
     * Returns the user to the main Edit Station GUI.
     *
     * @param event The action event from the button click.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
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
     * Handles the action triggered by the "Buy" button.
     * Validates the selection and available funds, then opens a confirmation popup for the building purchase.
     *
     * @param event The action event from the button click.
     */
    public void handleBuyButtonOnAction(ActionEvent event) {
        if (buildingTableView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Select the building you want to add!!");
            return;
        }

        if (!controller.checkIfHasMoney(buildingTableView.getSelectionModel().getSelectedItem())) {
            errorLabel.setText("You Don't have enough money!!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/BuildingsPurchaseConfirmation.fxml"));
            Parent root = loader.load();

            BuildingsPurchaseConfirmationPopupGUI buildingsPurchaseConfirmationPopupGUI = loader.getController();
            buildingsPurchaseConfirmationPopupGUI.setData(
                    controller.getSimulation().getMap(),
                    controller.getSimulation().getScenario(),
                    controller.getStation(),
                    controller.getSimulation(),
                    (Stage) ((Node) event.getSource()).getScene().getWindow(),
                    buildingTableView.getSelectionModel().getSelectedItem()
            );
            buildingsPurchaseConfirmationPopupGUI.setPurchaseMessage("Do you want to buy this Building?");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Purchase Confirmation Building");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateGUI();
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
     * Gets the building table view.
     *
     * @return The building table view.
     */
    public TableView<BuildingType> getBuildingTableView() {
        return buildingTableView;
    }

    /**
     * Sets the building table view.
     *
     * @param buildingTableView The building table view to set.
     */
    public void setBuildingTableView(TableView<BuildingType> buildingTableView) {
        this.buildingTableView = buildingTableView;
    }

    /**
     * Gets the name column.
     *
     * @return The name column.
     */
    public TableColumn<BuildingType, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The name column to set.
     */
    public void setNameColumn(TableColumn<BuildingType, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the cost column.
     *
     * @return The cost column.
     */
    public TableColumn<BuildingType, String> getCostColumn() {
        return costColumn;
    }

    /**
     * Sets the cost column.
     *
     * @param costColumn The cost column to set.
     */
    public void setCostColumn(TableColumn<BuildingType, String> costColumn) {
        this.costColumn = costColumn;
    }

    /**
     * Gets the controller.
     *
     * @return The controller.
     */
    public EditStationBuildingsController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The controller to set.
     */
    public void setController(EditStationBuildingsController controller) {
        this.controller = controller;
    }
}