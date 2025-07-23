package pt.ipp.isep.dei.ui.gui.simulation.StationRelated;

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
import pt.ipp.isep.dei.controller.simulation.StationRelated.StationRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.AddStation.AddStationGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation.ShowStationListGUI3;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.RemoveStation.ShowStationListGUI2;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.ShowStation.ShowStationListGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for the Station Related menu in the simulation.
 * <p>
 * Handles navigation between station-related actions such as adding, removing, editing, and showing stations.
 */
public class StationRelatedGUI implements Initializable {

    private StationRelatedController controller;

    @FXML
    private Label actualDateLabel;

    @FXML
    private Label actuaBudgetLabel;

    /**
     * Initializes the controller and sets up the business logic controller.
     *
     * @param url            The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new StationRelatedController();
    }

    /**
     * Sets the simulation data for the GUI and updates the display.
     *
     * @param simulation The current simulation.
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
     * Handles the action triggered by the "Add Station" button.
     * Navigates to the Add Station GUI.
     *
     * @param event The action event from the button click.
     */
    @FXML
    public void handleAddStationButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStation.fxml"));
            Parent root = loader.load();
            AddStationGUI addStationGUI = loader.getController();
            addStationGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Station Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the "Show Station List" button.
     * Navigates to the Show Station List GUI.
     *
     * @param event The action event from the button click.
     */
    @FXML
    public void handleShowStationListButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/ShowStation/ShowStationList.fxml"));
            Parent root = loader.load();
            ShowStationListGUI showStationListGUI = loader.getController();
            showStationListGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Show Stations");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the "Remove Station" button.
     * Navigates to the Remove Station GUI.
     *
     * @param event The action event from the button click.
     */
    @FXML
    public void handleRemoveStationButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/RemoveStation/ShowStationList2.fxml"));
            Parent root = loader.load();
            ShowStationListGUI2 showStationListGUI2 = loader.getController();
            showStationListGUI2.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Show Stations");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the "Edit Station" button.
     * Navigates to the Edit Station GUI.
     *
     * @param event The action event from the button click.
     */
    @FXML
    public void handleEditStationButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/ShowStationList3.fxml"));
            Parent root = loader.load();
            ShowStationListGUI3 showStationListGUI3 = loader.getController();
            showStationListGUI3.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Edit Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the "Return" button.
     * Navigates back to the Pause Menu GUI.
     *
     * @param event The action event from the button click.
     */
    @FXML
    public void handleReturnButton(ActionEvent event) {
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
     * Gets the controller.
     *
     * @return The StationRelatedController instance.
     */
    public StationRelatedController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The StationRelatedController to set.
     */
    public void setController(StationRelatedController controller) {
        this.controller = controller;
    }

    /**
     * Gets the actual date label.
     *
     * @return The actual date label.
     */
    public Label getActualDateLabel() {
        return actualDateLabel;
    }

    /**
     * Sets the actual date label.
     *
     * @param actualDateLabel The actual date label to set.
     */
    public void setActualDateLabel(Label actualDateLabel) {
        this.actualDateLabel = actualDateLabel;
    }

    /**
     * Gets the actual budget label.
     *
     * @return The actual budget label.
     */
    public Label getActuaBudgetLabel() {
        return actuaBudgetLabel;
    }

    /**
     * Sets the actual budget label.
     *
     * @param actuaBudgetLabel The actual budget label to set.
     */
    public void setActuaBudgetLabel(Label actuaBudgetLabel) {
        this.actuaBudgetLabel = actuaBudgetLabel;
    }
}