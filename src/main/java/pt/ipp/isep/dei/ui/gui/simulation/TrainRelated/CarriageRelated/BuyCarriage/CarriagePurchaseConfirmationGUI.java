package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.BuyCarriage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.BuyCarriage.CarriagePurchaseConfirmationController;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.CarriageRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for confirming the purchase of a carriage.
 * Handles user confirmation, error display, and navigation after the purchase.
 */
public class CarriagePurchaseConfirmationGUI implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private Stage stage;

    private CarriagePurchaseConfirmationController controller;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new CarriagePurchaseConfirmationController();
    }

    /**
     * Sets the simulation, carriage, and stage for this GUI.
     *
     * @param simulation The current simulation instance.
     * @param carriage The carriage to be purchased.
     * @param stage The current stage.
     */
    public void setData(Simulation simulation, Carriage carriage, Stage stage) {
        controller.setSimulation(simulation);
        controller.setCarriage(carriage);
        this.stage = stage;
    }

    /**
     * Handles the action when the user confirms the purchase.
     * Buys the carriage, closes the confirmation window, and navigates to the CarriageRelatedGUI.
     *
     * @param event The action event triggered by clicking the "Yes" button.
     */
    @FXML
    public void handleYesButton(ActionEvent event) {
        controller.buyCarriage();
        errorLabel.getScene().getWindow().hide();
        try {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/CarriageRelated/BuyCarriage/BuyCarriage2.fxml"));
            Parent root = loader.load();
            BuyCarriageGUI buyCarriageGUI = loader.getController();
            buyCarriageGUI.setSimulation(controller.getSimulation());
            stage.setTitle("MABEC - Simulation: Buy Carriage");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the user cancels the purchase.
     * Closes the confirmation window.
     *
     * @param event The action event triggered by clicking the "No" button.
     */
    @FXML
    public void handleNoButton(ActionEvent event) {
        errorLabel.getScene().getWindow().hide();
    }

    /**
     * Sets the purchase confirmation message in the error label.
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
     * Gets the current stage.
     *
     * @return The stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the current stage.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the controller.
     *
     * @return The CarriagePurchaseConfirmationController instance.
     */
    public CarriagePurchaseConfirmationController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The CarriagePurchaseConfirmationController to set.
     */
    public void setController(CarriagePurchaseConfirmationController controller) {
        this.controller = controller;
    }
}