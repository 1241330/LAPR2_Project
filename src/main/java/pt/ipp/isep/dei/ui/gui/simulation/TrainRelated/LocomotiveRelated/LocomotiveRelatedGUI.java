package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated;

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
import pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.LocomotiveRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive.BuyLocomotiveGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.ViewLocomotive.ListBoughtLocomotiveGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.TrainRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for managing locomotive-related actions in the simulation.
 * Handles navigation and updates for locomotive operations.
 */
public class LocomotiveRelatedGUI implements Initializable {

    /** Controller for locomotive-related operations. */
    private LocomotiveRelatedController controller;

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
        controller = new LocomotiveRelatedController();
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
     * Handles the action of the "Buy Locomotive" button.
     * Navigates to the buy locomotive screen.
     *
     * @param event The action event.
     */
    @FXML
    public void handleBuyLocomotiveButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/LocomotiveRelated/BuyLocomotive/BuyLocomotive2.fxml"));
            Parent root = loader.load();
            BuyLocomotiveGUI buyLocomotiveGUI = loader.getController();
            buyLocomotiveGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Buy Locomotive");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the "List Locomotives" button.
     * Navigates to the list of acquired locomotives screen.
     *
     * @param event The action event.
     */
    public void handleListLocomotivesButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/LocomotiveRelated/ViewAcquiredLocomotives/ListBoughtLocomotives.fxml"));
            Parent root = loader.load();
            ListBoughtLocomotiveGUI listBoughtLocomotiveGUI = loader.getController();
            listBoughtLocomotiveGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: List Acquired Locomotives");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the "Return" button.
     * Navigates back to the train-related main screen.
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
     * Gets the controller for locomotive-related operations.
     *
     * @return The LocomotiveRelatedController.
     */
    public LocomotiveRelatedController getController() {
        return controller;
    }

    /**
     * Sets the controller for locomotive-related operations.
     *
     * @param controller The LocomotiveRelatedController to set.
     */
    public void setController(LocomotiveRelatedController controller) {
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