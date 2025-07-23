package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.CreateRailwayLine;

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
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.SelectInitialStationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for selecting the initial station when creating a new railway line in the simulation.
 * Handles the display of available stations, selection validation, and navigation between screens.
 */
public class SelectInitialStationGUI implements Initializable {

    /**
     * Controller responsible for business logic related to selecting the initial station.
     */
    private SelectInitialStationController controller;

    /**
     * TableView displaying available departure stations.
     */
    @FXML
    private TableView<Station> departureStationTableView;

    /**
     * TableColumn displaying the name of each station.
     */
    @FXML
    private TableColumn<Station, String> nameColumn;

    /**
     * TableColumn displaying the position of each station.
     */
    @FXML
    private TableColumn<Station, String> positionColumn;

    /**
     * Label for displaying error messages to the user.
     */
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller for selecting the initial station.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new SelectInitialStationController();
    }

    /**
     * Sets the simulation and updates the GUI with available stations.
     * @param simulation The current simulation context.
     */
    public void setData (Simulation simulation){
        controller.setSimulation(simulation);
        updateGUI();
    }

    /**
     * Updates the TableView with available stations and their positions.
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
        departureStationTableView.setItems(controller.getAvailableStations());
    }

    /**
     * Handles the action of the Select button. Validates the selection and navigates to the end station selection screen.
     * @param event The ActionEvent triggered by clicking the Select button.
     */
    public void handleSelectButton(ActionEvent event){
        if (departureStationTableView.getSelectionModel().getSelectedItem() == null){
            errorLabel.setText("Select a station first!!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/CreateRailwayLine/SelectEndStation.fxml"));
            Parent root = loader.load();

            SelectEndStationGUI selectEndStationGUI = loader.getController();
            selectEndStationGUI.setData(controller.getSimulation(), (Station) departureStationTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Select End Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Handles the action of the Exit button. Returns to the pause menu.
     * @param event The ActionEvent triggered by clicking the Exit button.
     */
    public void handleExitButton(ActionEvent event){
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
     * Handles the action of the Back button. Returns to the railway related menu.
     * @param event The ActionEvent triggered by clicking the Back button.
     */
    public void handleBackButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/RailwayLineRelated.fxml"));
            Parent root = loader.load();

            RailwayRelatedGUI railwayRelatedGUI = loader.getController();
            railwayRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Railway Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
