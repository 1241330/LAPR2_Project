package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.RouteRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.CreateRoute.ChooseRailwayTypeForRouteGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.ManageRoute.ChooseRouteGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for route-related operations within a simulation.
 * <p>
 * This class manages the interactions from the route-related menu, allowing the user to:
 * <ul>
 *     <li>Create a new route</li>
 *     <li>Manage existing routes</li>
 *     <li>Return to the pause menu</li>
 * </ul>
 * It uses JavaFX to load and switch between different FXML scenes related to route management.
 */
public class RouteRelatedGUI implements Initializable {

    private RouteRelatedController controller;

    /**
     * Initializes the controller for route-related operations when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new RouteRelatedController();
    }

    /**
     * Sets the current simulation instance for use in this GUI.
     *
     * @param simulation The current {@link Simulation} instance.
     */
    public void setData(Simulation simulation){
        controller.setSimulation(simulation);
    }

    /**
     * Handles the event triggered when the "Create Route" button is clicked.
     * <p>
     * Loads the FXML for choosing the railway type for the new route and transfers the simulation data to the new scene.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void handleCreateRouteButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/CreateRoute/ChooseRailwayTypeForRoute.fxml"));
            Parent root = loader.load();

            ChooseRailwayTypeForRouteGUI chooseRailwayTypeForRouteGUI = loader.getController();
            chooseRailwayTypeForRouteGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Railway Type available");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event triggered when the "Manage Route" button is clicked.
     * <p>
     * Loads the FXML for managing existing routes and transfers the simulation data to the new scene.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void handleMenageRouteButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/MenageRoute/ChooseRoute.fxml"));
            Parent root = loader.load();

            ChooseRouteGUI chooseRouteGUI = loader.getController();
            chooseRouteGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Menage Routes");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event triggered when the "Return" button is clicked.
     * <p>
     * Loads the pause menu FXML and transfers the current simulation to the pause menu GUI.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    public void handleReturnButtonOnAction(ActionEvent event){
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
}
