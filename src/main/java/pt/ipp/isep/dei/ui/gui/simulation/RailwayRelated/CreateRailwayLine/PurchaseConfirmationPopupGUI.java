package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.CreateRailwayLine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.PurchaseConfirmationPopupController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI controller for the purchase confirmation popup when creating a railway line.
 * Handles the confirmation, cancellation, and navigation logic for purchasing a new railway line in the simulation.
 */
public class PurchaseConfirmationPopupGUI implements Initializable {

    /**
     * Controller responsible for business logic related to the purchase confirmation popup.
     */
    private PurchaseConfirmationPopupController controller;

    /**
     * The stage (window) for this popup.
     */
    @FXML
    private Stage stage;

    /**
     * Label for the popup title.
     */
    @FXML
    private Label titleLabel;

    /**
     * Label for the first line of popup text.
     */
    @FXML
    private Label text1Label;

    /**
     * Label for the second line of popup text.
     */
    @FXML
    private Label text2Label;

    /**
     * Initializes the controller for the purchase confirmation popup.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new PurchaseConfirmationPopupController();
    }

    /**
     * Sets the data for the popup, including simulation, stations, path, railway line type, cost, and stage.
     * @param simulation The simulation context.
     * @param beginningStation The departure station.
     * @param arrivalStation The arrival station.
     * @param path The path for the railway line.
     * @param railwayLineType The type of railway line.
     * @param cost The cost of the railway line.
     * @param stage The stage (window) for this popup.
     */
    public void setData(Simulation simulation, Station beginningStation, Station arrivalStation, List<Position> path, RailwayLineType railwayLineType, int cost, Stage stage) {
        controller.setSimulation(simulation);
        controller.setDepartureStation(beginningStation);
        controller.setArrivalStation(arrivalStation);
        controller.setPath(path);
        controller.setType(railwayLineType);
        controller.setCost(cost);

        this.stage = stage;
    }

    /**
     * Initializes the GUI items (labels) with the provided title and text.
     * @param title The title for the popup.
     * @param txt1 The first line of text.
     * @param txt2 The second line of text.
     */
    public void initializeGUIItems(String title, String txt1, String txt2) {
        titleLabel.setText(title);
        text1Label.setText(txt1);
        text2Label.setText(txt2);

    }

    /**
     * Handles the action of the Yes button. Adds the railway line and navigates back to the Railway Related GUI.
     * @param event The ActionEvent triggered by clicking the Yes button.
     */
    public void handleYesButtonOnAction(ActionEvent event){
        controller.addRailwayLine();
        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/RailwayLineRelated.fxml"));
            Parent root = loader.load();

            RailwayRelatedGUI railwayRelatedGUI = loader.getController();
            railwayRelatedGUI.setSimulation(controller.getSimulation());

            stage.setTitle("MABEC - Simulation: Railway Line Related");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the No button. Closes the popup without making changes.
     * @param event The ActionEvent triggered by clicking the No button.
     */
    public void handleNoButtonOnAction(ActionEvent event){
        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
    }
}
