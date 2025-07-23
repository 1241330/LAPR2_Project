package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.CreateRoute;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.ChooseRailwayTypeForRouteController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.RouteRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents the GUI screen that allows the user to choose the type of railway
 * (electric or all types) to be used for route creation in a simulation.
 * It connects to the corresponding controller to manage simulation state and navigation.
 *
 * FXML file: /fxml/RouteRelated/CreateRoute/ChooseRailwayTypeForRoute.fxml
 */
public class ChooseRailwayTypeForRouteGUI implements Initializable {

    /** Controller for handling logic related to choosing railway type for route creation. */
    private ChooseRailwayTypeForRouteController controller;

    /**
     * Initializes the GUI controller.
     *
     * @param url            Not used.
     * @param resourceBundle Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseRailwayTypeForRouteController();
    }

    /**
     * Sets the simulation object to be used by this GUI and its controller.
     *
     * @param simulation The simulation to be set.
     */
    public void setData(Simulation simulation){
        controller.setSimulation(simulation);
    }

    /**
     * Handles the event when the user selects to include all types of railways.
     * Loads the ChooseDepartureStation GUI with all railway types available.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void handleAllRailwayTypesButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/CreateRoute/ChooseDepartureStation.fxml"));
            Parent root = loader.load();

            ChooseDepartureStationGUI chooseDepartureStationGUI = loader.getController();
            chooseDepartureStationGUI.setData(controller.getSimulation(), false);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Route Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the user selects to use only electric railways.
     * Loads the ChooseDepartureStation GUI with only electric railways available.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void handleElectricRailwaysButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/CreateRoute/ChooseDepartureStation.fxml"));
            Parent root = loader.load();

            ChooseDepartureStationGUI chooseDepartureStationGUI = loader.getController();
            chooseDepartureStationGUI.setData(controller.getSimulation(), true);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Route Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the user clicks the Back button.
     * Navigates back to the RouteRelated GUI.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void backButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/RouteRelated.fxml"));
            Parent root = loader.load();

            RouteRelatedGUI routeRelatedGUI = loader.getController();
            routeRelatedGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Route Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the user clicks the Exit button.
     * Navigates to the pause menu screen.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void handleExitButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/PauseMenu.fxml"));
            Parent root = loader.load();

            PauseMenuGUI pauseMenuGUI = loader.getController();
            pauseMenuGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Pause Menu");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

