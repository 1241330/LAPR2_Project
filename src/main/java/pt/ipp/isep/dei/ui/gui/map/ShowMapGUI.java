package pt.ipp.isep.dei.ui.gui.map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.map.ShowMapForGUIController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller responsible for displaying the simulation map in a GUI.
 * <p>
 * This class visualizes the map grid of the current simulation and provides
 * navigation to return to the pause menu.
 * </p>
 */
public class ShowMapGUI implements Initializable {

    /**
     * Controller that manages the logic for setting and displaying the simulation map.
     */
    private ShowMapForGUIController controller;

    /**
     * The grid layout where the map elements will be rendered.
     */
    @FXML
    private GridPane mapGrid;

    /**
     * Label displaying the actual position.
     */
    @FXML
    private Label actualPositionLabel;

    /**
     * Initializes the controller class. Called automatically after the FXML file has been loaded.
     *
     * @param url            Not used.
     * @param resourceBundle Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowMapForGUIController();
    }

    /**
     * Sets the simulation instance and renders its corresponding map on the grid pane.
     *
     * @param simulation The simulation whose map is to be displayed.
     */
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        controller.setMap(mapGrid, actualPositionLabel);
    }

    /**
     * Handles the action event triggered by clicking the "Back" button.
     * Loads the Pause Menu screen and passes the current simulation to it.
     *
     * @param event The action event from the button click.
     */
    @FXML
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/PauseMenu.fxml"));
            Parent root = loader.load();

            // Set simulation on the pause menu controller
            PauseMenuGUI pauseMenuGUI = loader.getController();
            pauseMenuGUI.setSimulation(controller.getSimulation());

            // Switch to the pause menu scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setFullScreen(false);
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("MABEC - Pause Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the map grid pane.
     *
     * @return the map grid pane.
     */
    public GridPane getMapGrid() {
        return mapGrid;
    }

    /**
     * Sets the map grid pane.
     *
     * @param mapGrid the map grid pane to set.
     */
    public void setMapGrid(GridPane mapGrid) {
        this.mapGrid = mapGrid;
    }

    /**
     * Gets the actual position label.
     *
     * @return the actual position label.
     */
    public Label getActualPositionLabel() {
        return actualPositionLabel;
    }

    /**
     * Sets the actual position label.
     *
     * @param actualPositionLabel the actual position label to set.
     */
    public void setActualPositionLabel(Label actualPositionLabel) {
        this.actualPositionLabel = actualPositionLabel;
    }

    /**
     * Gets the controller responsible for the map logic.
     *
     * @return the ShowMapForGUIController instance.
     */
    public ShowMapForGUIController getController() {
        return controller;
    }

    /**
     * Sets the controller responsible for the map logic.
     *
     * @param controller the ShowMapForGUIController to set.
     */
    public void setController(ShowMapForGUIController controller) {
        this.controller = controller;
    }
}