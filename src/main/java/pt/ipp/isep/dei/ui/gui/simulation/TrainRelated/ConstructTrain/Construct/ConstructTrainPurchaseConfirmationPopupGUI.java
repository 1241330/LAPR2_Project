package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.Construct;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct.ConstructTrainPurchaseConfirmationPopupController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.ConstructTrainRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI controller for the train purchase confirmation popup.
 * <p>
 * Handles user actions for confirming or cancelling the train purchase,
 * manages the popup window, and transitions back to the main train construction menu.
 */
public class ConstructTrainPurchaseConfirmationPopupGUI implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private Label text1Label;

    @FXML
    private Label text2Label;

    private ConstructTrainPurchaseConfirmationPopupController controller;
    private Stage previousStage;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ConstructTrainPurchaseConfirmationPopupController();
    }

    /**
     * Sets the simulation, locomotive, carriages, and previous stage for this popup.
     *
     * @param simulation    The current {@link Simulation} instance.
     * @param locomotive    The {@link Locomotive} to be purchased.
     * @param carriages     The list of {@link Carriage} to be attached.
     * @param previousStage The previous {@link Stage} to return to after confirmation.
     */
    public void setData(Simulation simulation, Locomotive locomotive, List<Carriage> carriages, Stage previousStage) {
        controller.setSimulation(simulation);
        controller.setLocomotive(locomotive);
        controller.setCarriages(carriages);
        this.previousStage = previousStage;
    }

    /**
     * Initializes the popup labels with the given title and messages.
     *
     * @param title The title to display.
     * @param msg1  The first message to display.
     * @param msg2  The second message to display.
     */
    public void initializeGUIItems(String title, String msg1, String msg2) {
        titleLabel.setText(title);
        text1Label.setText(msg1);
        text2Label.setText(msg2);
    }

    /**
     * Handles the action when the "Yes" button is clicked.
     * Confirms the train creation, closes the popup, and returns to the main train construction menu.
     *
     * @param event The action event triggered by clicking the "Yes" button.
     */
    @FXML
    public void handleYesButtonOnAction(ActionEvent event) {
        controller.confirmTrainCreation();

        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/ConstructTrainRelated/ConstructTrainsRelated.fxml"));
            Parent root = loader.load();
            ConstructTrainRelatedGUI constructTrainRelatedGUI = loader.getController();
            constructTrainRelatedGUI.setSimulation(controller.getSimulation());
            previousStage.setTitle("MABEC - Simulation: Construct Train Related");
            previousStage.setScene(new Scene(root));
            previousStage.centerOnScreen();
            previousStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "No" button is clicked.
     * Closes the popup window without confirming the purchase.
     *
     * @param event The action event triggered by clicking the "No" button.
     */
    @FXML
    public void handleNoButtonOnAction(ActionEvent event) {
        closePopup(event);
    }

    /**
     * Closes the popup window.
     *
     * @param event The action event that triggered the close.
     */
    private void closePopup(ActionEvent event) {
        Stage popupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popupStage.close();
    }

    /**
     * Gets the controller for the popup.
     *
     * @return The {@link ConstructTrainPurchaseConfirmationPopupController} instance.
     */
    public ConstructTrainPurchaseConfirmationPopupController getController() {
        return controller;
    }

    /**
     * Sets the controller for the popup.
     *
     * @param controller The {@link ConstructTrainPurchaseConfirmationPopupController} to set.
     */
    public void setController(ConstructTrainPurchaseConfirmationPopupController controller) {
        this.controller = controller;
    }

    /**
     * Gets the previous stage.
     *
     * @return The previous {@link Stage}.
     */
    public Stage getPreviousStage() {
        return previousStage;
    }

    /**
     * Sets the previous stage.
     *
     * @param previousStage The previous {@link Stage} to set.
     */
    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    /**
     * Gets the title label.
     *
     * @return The {@link Label} for the title.
     */
    public Label getTitleLabel() {
        return titleLabel;
    }

    /**
     * Sets the title label.
     *
     * @param titleLabel The {@link Label} to set for the title.
     */
    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    /**
     * Gets the first text label.
     *
     * @return The {@link Label} for the first message.
     */
    public Label getText1Label() {
        return text1Label;
    }

    /**
     * Sets the first text label.
     *
     * @param text1Label The {@link Label} to set for the first message.
     */
    public void setText1Label(Label text1Label) {
        this.text1Label = text1Label;
    }

    /**
     * Gets the second text label.
     *
     * @return The {@link Label} for the second message.
     */
    public Label getText2Label() {
        return text2Label;
    }

    /**
     * Sets the second text label.
     *
     * @param text2Label The {@link Label} to set for the second message.
     */
    public void setText2Label(Label text2Label) {
        this.text2Label = text2Label;
    }
}