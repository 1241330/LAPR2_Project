package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.AddStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationNameController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.ui.gui.message.ErrorMessageGUI;
import pt.ipp.isep.dei.ui.gui.message.SuccessMessageGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class responsible for handling the process of naming a new station.
 * Allows the user to accept a suggested name or input a custom name.
 */
public class AddStationNameGUI implements Initializable {

    /** Controller for station name logic. */
    private AddStationNameController controller;

    @FXML
    private Label nameStationLabel;

    @FXML
    private Label keepNameLabel;

    @FXML
    private Label suggestedNameLabel;

    @FXML
    private Label enterDesiredNameLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField desiredNameTextField;

    @FXML
    private Button yesNameButton;

    @FXML
    private Button noNameButton;

    @FXML
    private Button continueButton;

    /**
     * Initializes the controller for this GUI.
     *
     * @param url Not used.
     * @param resourceBundle Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new AddStationNameController();
    }

    /**
     * Sets the information required for station creation.
     *
     * @param simulation The simulation instance.
     * @param stationType The type of the station.
     * @param direction The direction of the station.
     * @param xPosition The X position.
     * @param yPosition The Y position.
     */
    public void setInformation(Simulation simulation, StationType stationType, String direction, String xPosition, String yPosition) {
        controller.setSimulation(simulation);
        controller.setStationType(stationType);
        controller.setDirection(direction);
        controller.setPosition(xPosition, yPosition);

        nameStationLabel.setText(controller.getAutoStationName());
    }

    /**
     * Handles the event when the user accepts the suggested station name.
     *
     * @param event The action event.
     */
    @FXML
    public void handleAcceptNameButtonOnAction(ActionEvent event) {
        createNewStation(event);
    }

    /**
     * Handles the event when the user rejects the suggested station name.
     * Shows the UI for entering a custom name.
     *
     * @param event The action event.
     */
    @FXML
    public void handleRejectNameButtonOnAction(ActionEvent event) {
        suggestedNameLabel.setVisible(false);
        nameStationLabel.setVisible(false);
        keepNameLabel.setVisible(false);
        yesNameButton.setVisible(false);
        noNameButton.setVisible(false);

        continueButton.setVisible(true);
        enterDesiredNameLabel.setVisible(true);
        desiredNameTextField.setVisible(true);
    }

    /**
     * Handles the event when the user clicks the continue button after entering a custom name.
     *
     * @param event The action event.
     */
    @FXML
    public void handleContinueButtonOnAction(ActionEvent event) {
        if (desiredNameTextField.getText().isEmpty()) {
            errorLabel.setText("You need to fill the field with the name that you want!!");
            return;
        }
        controller.setStationName(desiredNameTextField.getText());

        createNewStation(event);
    }

    /**
     * Creates a new station and shows a success or error message.
     * Navigates back to the StationRelated GUI.
     *
     * @param event The action event.
     */
    private void createNewStation(ActionEvent event) {
        boolean result = controller.createStation();

        if (result) {
            showSuccessMessage("Station Created Successfully.");
        } else {
            showErrorMessage("Station could not be Created.");
        }

        navigateToStationRelated(event);
    }

    /**
     * Shows a success message dialog.
     *
     * @param message The success message.
     */
    private void showSuccessMessage(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/SuccessMessage.fxml"));
            Parent root = loader.load();
            SuccessMessageGUI successMessageGUI = loader.getController();
            successMessageGUI.setSuccessMessage(message);
            Stage stage = new Stage();
            stage.setTitle("MABEC - Success Message");
            stage.setScene(new Scene(root));
            stage.show();
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5));
            delay.setOnFinished(event1 -> stage.close());
            delay.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an error message dialog.
     *
     * @param message The error message.
     */
    private void showErrorMessage(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/ErrorMessage.fxml"));
            Parent root = loader.load();
            ErrorMessageGUI errorMessageGUI = loader.getController();
            errorMessageGUI.setErrorMessage(message);
            Stage stage = new Stage();
            stage.setTitle("MABEC - Error Message");
            stage.setScene(new Scene(root));
            stage.show();
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5));
            delay.setOnFinished(event1 -> stage.close());
            delay.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the StationRelated GUI.
     *
     * @param event The action event.
     */
    private void navigateToStationRelated(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/StationRelated.fxml"));
            Parent root = loader.load();

            StationRelatedGUI stationRelatedGUI = loader.getController();
            stationRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Station Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the user clicks the cancel button.
     * Navigates to the StationRelated GUI.
     *
     * @param event The action event.
     */
    public void handleCancelButtonOnAction(ActionEvent event) {
        navigateToStationRelated(event);
    }

    /**
     * Handles the event when the user clicks the back button.
     * Navigates to the AddStationPosition GUI.
     *
     * @param event The action event.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStationPosition.fxml"));
            Parent root = loader.load();

            AddStationPositionGUI addStationPositionGUI = loader.getController();
            addStationPositionGUI.setInformation(controller.getSimulation(), controller.getStationType(), controller.getDirection());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose station type");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters

    /**
     * Gets the name station label.
     *
     * @return The name station label.
     */
    public Label getNameStationLabel() {
        return nameStationLabel;
    }

    /**
     * Sets the name station label.
     *
     * @param nameStationLabel The name station label.
     */
    public void setNameStationLabel(Label nameStationLabel) {
        this.nameStationLabel = nameStationLabel;
    }

    /**
     * Gets the keep name label.
     *
     * @return The keep name label.
     */
    public Label getKeepNameLabel() {
        return keepNameLabel;
    }

    /**
     * Sets the keep name label.
     *
     * @param keepNameLabel The keep name label.
     */
    public void setKeepNameLabel(Label keepNameLabel) {
        this.keepNameLabel = keepNameLabel;
    }

    /**
     * Gets the suggested name label.
     *
     * @return The suggested name label.
     */
    public Label getSuggestedNameLabel() {
        return suggestedNameLabel;
    }

    /**
     * Sets the suggested name label.
     *
     * @param suggestedNameLabel The suggested name label.
     */
    public void setSuggestedNameLabel(Label suggestedNameLabel) {
        this.suggestedNameLabel = suggestedNameLabel;
    }

    /**
     * Gets the enter desired name label.
     *
     * @return The enter desired name label.
     */
    public Label getEnterDesiredNameLabel() {
        return enterDesiredNameLabel;
    }

    /**
     * Sets the enter desired name label.
     *
     * @param enterDesiredNameLabel The enter desired name label.
     */
    public void setEnterDesiredNameLabel(Label enterDesiredNameLabel) {
        this.enterDesiredNameLabel = enterDesiredNameLabel;
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
     * @param errorLabel The error label.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the desired name text field.
     *
     * @return The desired name text field.
     */
    public TextField getDesiredNameTextField() {
        return desiredNameTextField;
    }

    /**
     * Sets the desired name text field.
     *
     * @param desiredNameTextField The desired name text field.
     */
    public void setDesiredNameTextField(TextField desiredNameTextField) {
        this.desiredNameTextField = desiredNameTextField;
    }

    /**
     * Gets the yes name button.
     *
     * @return The yes name button.
     */
    public Button getYesNameButton() {
        return yesNameButton;
    }

    /**
     * Sets the yes name button.
     *
     * @param yesNameButton The yes name button.
     */
    public void setYesNameButton(Button yesNameButton) {
        this.yesNameButton = yesNameButton;
    }

    /**
     * Gets the no name button.
     *
     * @return The no name button.
     */
    public Button getNoNameButton() {
        return noNameButton;
    }

    /**
     * Sets the no name button.
     *
     * @param noNameButton The no name button.
     */
    public void setNoNameButton(Button noNameButton) {
        this.noNameButton = noNameButton;
    }

    /**
     * Gets the continue button.
     *
     * @return The continue button.
     */
    public Button getContinueButton() {
        return continueButton;
    }

    /**
     * Sets the continue button.
     *
     * @param continueButton The continue button.
     */
    public void setContinueButton(Button continueButton) {
        this.continueButton = continueButton;
    }

    /**
     * Gets the controller.
     *
     * @return The AddStationNameController.
     */
    public AddStationNameController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The AddStationNameController.
     */
    public void setController(AddStationNameController controller) {
        this.controller = controller;
    }
}