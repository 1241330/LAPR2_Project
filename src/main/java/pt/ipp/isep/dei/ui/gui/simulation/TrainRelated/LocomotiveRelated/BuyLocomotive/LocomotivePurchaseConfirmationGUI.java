package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive.LocomotivePurchaseConfirmationController;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.LocomotiveRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for the locomotive purchase confirmation window.
 * Handles user interactions for confirming or cancelling the purchase of a locomotive.
 */
public class LocomotivePurchaseConfirmationGUI implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private Stage stage;

    private LocomotivePurchaseConfirmationController controller;

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new LocomotivePurchaseConfirmationController();
    }

    /**
     * Sets the data required for the purchase confirmation.
     *
     * @param simulation The current simulation.
     * @param locomotive The locomotive to be purchased.
     * @param stage The stage for this window.
     */
    public void setData(Simulation simulation, Locomotive locomotive, Stage stage) {
        controller.setSimulation(simulation);
        controller.setLocomotive(locomotive);
        this.stage = stage;
    }

    /**
     * Handles the action when the "Yes" button is pressed.
     * Confirms the purchase, closes the confirmation window, and opens the locomotive related window.
     *
     * @param event The action event.
     */
    @FXML
    public void handleYesButton(ActionEvent event) {
        controller.buyLocomotive();
        errorLabel.getScene().getWindow().hide();
        try {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/LocomotiveRelated/LocomotiveRelated.fxml"));
            Parent root = loader.load();
            LocomotiveRelatedGUI locomotiveRelatedGUI = loader.getController();
            locomotiveRelatedGUI.setSimulation(controller.getSimulation());
            stage.setTitle("MABEC - Simulation: Locomotive Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "No" button is pressed.
     * Closes the confirmation window.
     *
     * @param event The action event.
     */
    @FXML
    public void handleNoButton(ActionEvent event) {
        errorLabel.getScene().getWindow().hide();
    }

    /**
     * Sets the purchase confirmation message.
     *
     * @param message The message to display.
     */
    public void setPurchaseMessage(String message) {
        errorLabel.setText(message);
    }

    /**
     * Gets the error label.
     *
     * @return The error label.
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the error label.
     *
     * @param errorLabel The error label to set.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the stage.
     *
     * @return The stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the controller.
     *
     * @return The LocomotivePurchaseConfirmationController.
     */
    public LocomotivePurchaseConfirmationController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The LocomotivePurchaseConfirmationController to set.
     */
    public void setController(LocomotivePurchaseConfirmationController controller) {
        this.controller = controller;
    }
}