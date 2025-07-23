package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.ManageRoute;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.DeactivateRoutePOPController;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.RouteRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for the popup window that handles route deactivation confirmation.
 * <p>
 * This popup allows the user to confirm or cancel the deactivation of a selected route
 * within a simulation.
 */
public class DeactivateRoutePOPGUI implements Initializable {

    private DeactivateRoutePOPController controller;

    @FXML
    private Stage stage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label text1Label;

    @FXML
    private Label text2Label;

    /**
     * Initializes the controller when the popup GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new DeactivateRoutePOPController();
    }

    /**
     * Sets the simulation, route to deactivate, and the main window stage for this popup.
     *
     * @param simulation The current simulation instance.
     * @param route The route selected for deactivation.
     * @param window The main stage window of the application.
     */
    public void setData(Simulation simulation, Route route, Stage window){
        controller.setRoute(route);
        controller.setSimulation(simulation);

        this.stage = window;
    }

    /**
     * Initializes the popup labels with the provided text.
     *
     * @param title The title text for the popup.
     * @param txt1 The first line of descriptive text.
     * @param txt2 The second line of descriptive text.
     */
    public void initializeGUIItems(String title, String txt1, String txt2) {
        titleLabel.setText(title);
        text1Label.setText(txt1);
        text2Label.setText(txt2);
    }

    /**
     * Handles the confirmation button ("Yes") action to deactivate the selected route.
     * <p>
     * After deactivating the route, the popup is closed and the main route-related GUI is refreshed.
     *
     * @param event The action event triggered by the button click.
     */
    public void handleYesButtonOnAction(ActionEvent event){
        controller.deactivateRoute();

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
     * Handles the cancellation button ("No") action.
     * <p>
     * Simply closes the popup without making any changes.
     *
     * @param event The action event triggered by the button click.
     */
    public void handleNoButtonOnAction(ActionEvent event){
        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
    }
}
