package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain;

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
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.CarriageRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.Construct.ListLocomotiveGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.View.ViewTrainGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.TrainRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Construct Train Related GUI.
 * Handles the logic for displaying and navigating between train construction related screens.
 */
public class ConstructTrainRelatedGUI implements Initializable {

    /** Controller for carriage-related operations. */
    private CarriageRelatedController controller;

    /** Label displaying the current date. */
    @FXML
    private Label actualDateLabel;

    /** Label displaying the current budget. */
    @FXML
    private Label actuaBudgetLabel;

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new CarriageRelatedController();
    }

    /**
     * Sets the simulation and updates the date and budget labels.
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
     * Handles the event when the user clicks the "Construct Train" button.
     * Navigates to the list of available locomotives.
     *
     * @param event The action event.
     */
    @FXML
    public void handleConstructTrainButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/ConstructTrainRelated/ConstructTrain/ListAvailableLocomotives.fxml"));
            Parent root = loader.load();
            ListLocomotiveGUI listLocomotiveGUI = loader.getController();
            listLocomotiveGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: List Available Locomotives");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the user clicks the "View Train" button.
     * Navigates to the view trains screen.
     *
     * @param event The action event.
     */
    public void handleViewTrainButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/ConstructTrainRelated/ViewTrain/ViewTrain.fxml"));
            Parent root = loader.load();
            ViewTrainGUI viewTrainGUI = loader.getController();
            viewTrainGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: View Trains");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the user clicks the "Return" button.
     * Navigates back to the main train related screen.
     *
     * @param event The action event.
     */
    @FXML
    public void handleReturnButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/TrainRelated.fxml"));
            Parent root = loader.load();
            TrainRelatedGUI trainRelatedGUI = loader.getController();
            trainRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Train Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the carriage related controller.
     *
     * @return The carriage related controller.
     */
    public CarriageRelatedController getController() {
        return controller;
    }

    /**
     * Sets the carriage related controller.
     *
     * @param controller The carriage related controller to set.
     */
    public void setController(CarriageRelatedController controller) {
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