package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.CreateRoute;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.RouteCreationConfirmationPopController;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.RouteRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI class for the route creation confirmation popup in the simulation.
 * Handles the confirmation dialog for creating a route, including user actions for confirmation or cancellation.
 * Integrates with the controller to manage simulation data and update the main route-related GUI after confirmation.
 */
public class RouteCreationConfirmationPopGUI implements Initializable {

    private RouteCreationConfirmationPopController controller;

    @FXML
    private Stage stage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label text1Label;

    @FXML
    private Label text2Label;

    /**
     * Initializes the controller for the confirmation popup.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new RouteCreationConfirmationPopController();
    }

    /**
     * Sets the data required for the confirmation popup, including route points, station path, simulation, and window.
     * @param pointOfRouteList The list of points of the route.
     * @param stationPath The list of stations in the route path.
     * @param simulation The simulation instance.
     * @param isRailwayTypeAvailableFlag Flag indicating if the railway type is available.
     * @param window The stage (window) for the popup.
     */
    public void setData(List<PointOfRoute> pointOfRouteList, List<Station> stationPath, Simulation simulation, boolean isRailwayTypeAvailableFlag, Stage window) {
        controller.setPointOfRouteList(pointOfRouteList);
        controller.setStationPath(stationPath);
        controller.setSimulation(simulation);
        controller.setRailwayTypeAvailableFlag(isRailwayTypeAvailableFlag);

        this.stage = window;
    }

    /**
     * Initializes the GUI items of the popup with the provided title and text.
     * @param title The title to display.
     * @param txt1 The first line of text to display.
     * @param txt2 The second line of text to display.
     */
    public void initializeGUIItems(String title, String txt1, String txt2) {
        titleLabel.setText(title);
        text1Label.setText(txt1);
        text2Label.setText(txt2);

    }

    /**
     * Handles the action when the user confirms the creation of the route.
     * Creates the route, closes the popup, and updates the main route-related GUI.
     * @param event The action event triggered by the user.
     */
    public void handleYesButtonOnAction(ActionEvent event){
        controller.createRoute();

        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/RouteRelated.fxml"));
            Parent root = loader.load();

            RouteRelatedGUI routeRelatedGUI = loader.getController();
            routeRelatedGUI.setData(controller.getSimulation());

            stage.setTitle("MABEC - Simulation: Route Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the user cancels the creation of the route.
     * Closes the confirmation popup.
     * @param event The action event triggered by the user.
     */
    public void handleNoButtonOnAction(ActionEvent event){
        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
    }
}
