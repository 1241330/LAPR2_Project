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
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.ConfirmRouteActivationPopController;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.RouteRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for the popup window that confirms route activation.
 * <p>
 * This popup asks the user to confirm the activation of a specific route for a given train
 * within the current simulation context.
 */
public class ConfirmRouteActivationPopGUI implements Initializable {

    private ConfirmRouteActivationPopController controller;

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
        controller = new ConfirmRouteActivationPopController();
    }

    /**
     * Sets the simulation, route, train, and the main stage for this popup.
     *
     * @param simulation The current simulation instance.
     * @param route The route to be activated.
     * @param train The train for which the route will be activated.
     * @param stage The main stage window of the application.
     */
    public void setData(Simulation simulation, Route route, Train train, Stage stage){
        controller.setSimulation(simulation);
        controller.setRoute(route);
        controller.setTrain(train);

        this.stage = stage;
    }

    /**
     * Initializes the popup labels with the given title and descriptive texts.
     *
     * @param title The title text for the popup.
     * @param txt1 The first descriptive line.
     * @param txt2 The second descriptive line.
     */
    public void initializeGUIItems(String title, String txt1, String txt2) {
        titleLabel.setText(title);
        text1Label.setText(txt1);
        text2Label.setText(txt2);
    }

    /**
     * Handles the "Yes" button action to activate the selected route for the train.
     * <p>
     * After activation, closes the popup and refreshes the main route-related GUI.
     *
     * @param event The action event triggered by clicking the "Yes" button.
     */
    public void handleYesButtonOnAction(ActionEvent event){
        controller.activateRoute();

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
     * Handles the "No" button action to cancel route activation.
     * <p>
     * Simply closes the popup without making any changes.
     *
     * @param event The action event triggered by clicking the "No" button.
     */
    public void handleNoButtonOnAction(ActionEvent event){
        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
    }
}
