package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.ShowRailwayLine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.ShowRailwayList.ViewDetailsRailwayController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewDetailsRailwayGUI implements Initializable {

    private ViewDetailsRailwayController controller;

    @FXML
    private Label departureStationLabel;

    @FXML
    private Label arrivalStationLabel;

    @FXML
    private Label railwayTipeLabel;

    @FXML
    private Label railwaySizeLabel;

    /**
     * Initializes the controller for this GUI.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ViewDetailsRailwayController();
    }

    /**
     * Sets the simulation and railway line for this GUI and initializes the GUI items.
     * @param simulation The simulation context.
     * @param railwayLine The railway line to display details for.
     */
    public void setSimulation(Simulation simulation, RailwayLine railwayLine) {
        controller.setSimulation(simulation);
        controller.setRailwayLine(railwayLine);
        initializeGUIItems();
    }

    /**
     * Initializes the GUI labels with the details of the selected railway line.
     */
    private void initializeGUIItems() {
        departureStationLabel.setText(controller.getDepartureStation());
        arrivalStationLabel.setText(controller.getArrivalStation());
        railwayTipeLabel.setText(controller.getRailwayType());
        railwaySizeLabel.setText(controller.getRailwaySize());
    }

    /**
     * Handles the event when the back button is pressed.
     * Navigates back to the Choose Railway Line GUI.
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleBackButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/ListRailwayLines/ChooseRailwayLine.fxml"));
            Parent root = loader.load();
            ChooseRailwayLineGUI chooseRailwayLineGUI = loader.getController();
            chooseRailwayLineGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Railway Line");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the cancel button is pressed.
     * Navigates back to the Railway Related GUI.
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleCancelButtonOnAction(ActionEvent event){
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
