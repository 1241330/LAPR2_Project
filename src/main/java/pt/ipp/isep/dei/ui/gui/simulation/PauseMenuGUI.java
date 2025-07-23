package pt.ipp.isep.dei.ui.gui.simulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.InSimulation.PauseMenuController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.map.ShowMapGUI;
import pt.ipp.isep.dei.ui.gui.simulation.FinancialResult.FinancialResultsGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.RouteRelatedGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.TrainRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * GUI class for the simulation pause menu.
 * <p>
 * Allows the user to resume the simulation or access operations related
 * to stations, trains, railway lines, and routes while the simulation is paused.
 * </p>
 * Implements {@link Initializable} for FXML initialization.
 */
public class PauseMenuGUI implements Initializable {

    private PauseMenuController controller;
    private final Scanner scanner = new Scanner(System.in);

    @FXML
    private Label actualDateLabel;

    @FXML
    private Label actuaBudgetLabel;

    /**
     * Initializes the PauseMenuController and prepares the GUI components.
     *
     * @param url the location used to resolve relative paths for the root object, or null if unknown
     * @param resourceBundle the resources used to localize the root object, or null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new PauseMenuController();
    }

    /**
     * Sets the current simulation and updates the labels for date and budget.
     *
     * @param simulation the simulation to be managed in the pause menu
     */
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);

        actualDateLabel.setText(controller.getActualDate());
        actualDateLabel.setAlignment(Pos.CENTER);
        actualDateLabel.setTextAlignment(TextAlignment.CENTER);

        actuaBudgetLabel.setText("BUDGET: " + controller.getActualMoney() + " \uD83D\uDCB0");
        actuaBudgetLabel.setAlignment(Pos.CENTER);
        actuaBudgetLabel.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * Handles the action to continue the simulation.
     * Loads the RunSimulationGUI and resumes the simulation.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleContinueProgramButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/RunSimulation.fxml"));
            Parent root = loader.load();
            RunSimulationGUI runSimulationGUI = loader.getController();
            runSimulationGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("MABEC - Running Simulation");
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to access station-related operations.
     * Loads the StationRelatedGUI.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleStationRelatedButton(ActionEvent event) {
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
     * Handles the action to access train-related operations.
     * Loads the TrainRelatedGUI.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleTrainRelatedButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/TrainRelated.fxml"));
            Parent root = loader.load();
            TrainRelatedGUI trainRelatedGUI = loader.getController();
            trainRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Train Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to access railway line-related operations.
     * Loads the RailwayRelatedGUI.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleRailwayLineRelatedButton(ActionEvent event) {
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

    /**
     * Handles the action to access route-related operations.
     * Loads the RouteRelatedGUI.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleRouteRelatedButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/RouteRelated.fxml"));
            Parent root = loader.load();
            RouteRelatedGUI routeRelatedGUI = loader.getController();
            routeRelatedGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Route Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to view the financial history.
     * Loads the FinancialResultsGUI.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleViewFinancialHistoryButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/financialResults/ViewFinancialResults.fxml"));
            Parent root = loader.load();
            FinancialResultsGUI financialResultsGUI = loader.getController();
            financialResultsGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Financial history");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to show the map.
     * Loads the ShowMapGUI.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleShowMapButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map/ShowMap.fxml"));
            Parent root = loader.load();
            ShowMapGUI showMapGUI = loader.getController();
            showMapGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Show Map");
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setAlwaysOnTop(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to end the simulation.
     * Loads the SaveSimulationGUI.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void handleEndSimulation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/SaveSimulation.fxml"));
            Parent root = loader.load();
            SaveSimulationGUI saveSimulationGUI = loader.getController();
            saveSimulationGUI.setInformation(controller.getSimulation(), (Stage) ((Node) event.getSource()).getScene().getWindow());
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("MABEC - Save Simulation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the PauseMenuController instance.
     *
     * @return the PauseMenuController
     */
    public PauseMenuController getController() {
        return controller;
    }

    /**
     * Sets the PauseMenuController instance.
     *
     * @param controller the PauseMenuController to set
     */
    public void setController(PauseMenuController controller) {
        this.controller = controller;
    }

    /**
     * Gets the actual date label.
     *
     * @return the actualDateLabel
     */
    public Label getActualDateLabel() {
        return actualDateLabel;
    }

    /**
     * Sets the actual date label.
     *
     * @param actualDateLabel the Label to set
     */
    public void setActualDateLabel(Label actualDateLabel) {
        this.actualDateLabel = actualDateLabel;
    }

    /**
     * Gets the actual budget label.
     *
     * @return the actuaBudgetLabel
     */
    public Label getActuaBudgetLabel() {
        return actuaBudgetLabel;
    }

    /**
     * Sets the actual budget label.
     *
     * @param actuaBudgetLabel the Label to set
     */
    public void setActuaBudgetLabel(Label actuaBudgetLabel) {
        this.actuaBudgetLabel = actuaBudgetLabel;
    }
}