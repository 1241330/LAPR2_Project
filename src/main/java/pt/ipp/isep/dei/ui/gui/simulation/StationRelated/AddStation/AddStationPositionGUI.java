package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.AddStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationPositionController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class responsible for handling the position input when adding a new station.
 * Manages user interactions and navigation between screens.
 */
public class AddStationPositionGUI implements Initializable {

    private AddStationPositionController controller;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField xPosition;

    @FXML
    private TextField yPosition;

    /**
     * Initializes the controller for this GUI.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new AddStationPositionController();
    }

    /**
     * Sets the simulation, station type, and direction information for the controller.
     *
     * @param simulation  The current simulation.
     * @param stationType The type of the station to be added.
     * @param direction   The direction of the station.
     */
    public void setInformation(Simulation simulation, StationType stationType, String direction) {
        controller.setSimulation(simulation);
        controller.setStationType(stationType);
        controller.setDirection(direction);
    }

    /**
     * Handles the action of the back button, returning to the previous screen.
     *
     * @param event The action event triggered by the user.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStation.fxml"));
            Parent root = loader.load();
            AddStationGUI addStationGUI = loader.getController();
            addStationGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose station type");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the continue button, validating input and proceeding to the next screen.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    public void handleContinueButtonOnAction(ActionEvent event) {
        if (xPosition.getText() == null || yPosition.getText() == null) {
            errorLabel.setText("You must fill both fields!");
        } else if (!"no problem".equals(controller.verifyValues(xPosition.getText(), yPosition.getText()))) {
            errorLabel.setText(controller.verifyValues(xPosition.getText(), yPosition.getText()));
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/AddStation/AddStationName.fxml"));
                Parent root = loader.load();
                AddStationNameGUI addStationNameGUI = loader.getController();
                addStationNameGUI.setInformation(
                        controller.getSimulation(),
                        controller.getStationType(),
                        controller.getDirection(),
                        xPosition.getText(),
                        yPosition.getText()
                );
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("MABEC - Simulation: Station Related");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the action of the cancel button, returning to the station related main screen.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    public void handleCancelButtonOnAction(ActionEvent event) {
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
     * Gets the X position text field.
     *
     * @return The X position text field.
     */
    public TextField getXPosition() {
        return xPosition;
    }

    /**
     * Sets the X position text field.
     *
     * @param xPosition The X position text field to set.
     */
    public void setXPosition(TextField xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Gets the Y position text field.
     *
     * @return The Y position text field.
     */
    public TextField getYPosition() {
        return yPosition;
    }

    /**
     * Sets the Y position text field.
     *
     * @param yPosition The Y position text field to set.
     */
    public void setYPosition(TextField yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Gets the controller.
     *
     * @return The AddStationPositionController.
     */
    public AddStationPositionController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The AddStationPositionController to set.
     */
    public void setController(AddStationPositionController controller) {
        this.controller = controller;
    }
}