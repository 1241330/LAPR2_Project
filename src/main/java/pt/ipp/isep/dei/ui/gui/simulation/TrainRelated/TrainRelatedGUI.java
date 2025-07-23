package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated;

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
import pt.ipp.isep.dei.controller.simulation.TrainRelated.TrainRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.CarriageRelatedGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.ConstructTrainRelatedGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.LocomotiveRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for managing train-related actions in the simulation.
 * Handles navigation and updates for train operations.
 */
public class TrainRelatedGUI implements Initializable {

    /** Controller for train-related operations. */
    private TrainRelatedController controller;

    /** Label displaying the current date. */
    @FXML
    private Label actualDateLabel;

    /** Label displaying the current budget. */
    @FXML
    private Label actuaBudgetLabel;

    /**
     * Initializes the controller and sets up the GUI.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new TrainRelatedController();
    }

    /**
     * Sets the simulation and updates the labels with current date and budget.
     *
     * @param simulation The simulation to set.
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
     * Handles the action of the "Locomotive" button.
     * Navigates to the locomotive-related screen.
     *
     * @param event The action event.
     */
    @FXML
    public void handleLocomotiveButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/LocomotiveRelated/LocomotiveRelated.fxml"));
            Parent root = loader.load();
            LocomotiveRelatedGUI locomotiveRelatedGUI = loader.getController();
            locomotiveRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Locomotive Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the "Carriage" button.
     * Navigates to the carriage-related screen.
     *
     * @param event The action event.
     */
    @FXML
    public void handleCarriageButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/CarriageRelated/CarriageRelated.fxml"));
            Parent root = loader.load();
            CarriageRelatedGUI carriageRelatedGUI = loader.getController();
            carriageRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Carriage Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the "Construct Train" button.
     * Navigates to the construct train-related screen.
     *
     * @param event The action event.
     */
    @FXML
    public void handleConstructTrainButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/ConstructTrainRelated/ConstructTrainsRelated.fxml"));
            Parent root = loader.load();
            ConstructTrainRelatedGUI constructTrainRelatedGUI = loader.getController();
            constructTrainRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Construct Train Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the "Return" button.
     * Navigates back to the pause menu screen.
     *
     * @param event The action event.
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
     * Gets the train-related controller.
     *
     * @return The TrainRelatedController.
     */
    public TrainRelatedController getController() {
        return controller;
    }

    /**
     * Sets the train-related controller.
     *
     * @param controller The TrainRelatedController to set.
     */
    public void setController(TrainRelatedController controller) {
        this.controller = controller;
    }

    /**
     * Gets the label displaying the current date.
     *
     * @return The actual date label.
     */
    public Label getActualDateLabel() {
        return actualDateLabel;
    }

    /**
     * Sets the label displaying the current date.
     *
     * @param actualDateLabel The actual date label to set.
     */
    public void setActualDateLabel(Label actualDateLabel) {
        this.actualDateLabel = actualDateLabel;
    }

    /**
     * Gets the label displaying the current budget.
     *
     * @return The actual budget label.
     */
    public Label getActuaBudgetLabel() {
        return actuaBudgetLabel;
    }

    /**
     * Sets the label displaying the current budget.
     *
     * @param actuaBudgetLabel The actual budget label to set.
     */
    public void setActuaBudgetLabel(Label actuaBudgetLabel) {
        this.actuaBudgetLabel = actuaBudgetLabel;
    }
}