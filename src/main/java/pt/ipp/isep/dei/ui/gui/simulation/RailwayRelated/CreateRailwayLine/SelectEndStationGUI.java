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
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.SelectEndStationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for selecting the end station when creating a new railway line in the simulation.
 * Handles the display of available stations, selection validation, and navigation between screens.
 */
public class SelectEndStationGUI implements Initializable {

    /**
     * Controller responsible for business logic related to selecting the end station.
     */
    private SelectEndStationController controller;

    /**
     * TableView displaying available arrival stations.
     */
    @FXML
    private TableView<Station> arrivalStationTableView;
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
     * Initializes the controller for selecting the end station.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new SelectEndStationController();
    }

    /**
     * Sets the simulation and selected initial station, then updates the GUI with available stations.
     * @param simulation The current simulation context.
     * @param selectedStation The initial station selected by the user.
     */
    public void setData(Simulation simulation, Station selectedStation){
        controller.setSimulation(simulation);
        controller.setBeginningStation(selectedStation);
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
        arrivalStationTableView.setItems(controller.getAvailableStations());
    }

    /**
     * Handles the action of the Select button. Validates the selection and navigates to the track type selection screen.
     * @param event The ActionEvent triggered by clicking the Select button.
     */
    public void handleSelectButton(ActionEvent event){
        if (arrivalStationTableView.getSelectionModel().getSelectedItem() == null){
            errorLabel.setText("Select a station first!!");
            return;
        }

        if(controller.checkExistingLineBetweenStations(arrivalStationTableView.getSelectionModel().getSelectedItem())){
            errorLabel.setText("These stations are already connected!!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/CreateRailwayLine/SelectTrackType.fxml"));
            Parent root = loader.load();

            SelectTrackTypeGUI selectTrackTypeGUI = loader.getController();
            selectTrackTypeGUI.setData(controller.getSimulation(), controller.getBeginningStation(),arrivalStationTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Track Type");
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
     * Handles the action of the Back button. Returns to the initial station selection screen.
     * @param event The ActionEvent triggered by clicking the Back button.
     */
    public void handleBackButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/CreateRailwayLine/SelectInitialStation.fxml"));
            Parent root = loader.load();

            SelectInitialStationGUI selectInitialStationGUI = loader.getController();
            selectInitialStationGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Pause Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

