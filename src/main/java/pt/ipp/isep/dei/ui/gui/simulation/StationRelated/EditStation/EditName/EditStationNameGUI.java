package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation.EditName;

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
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationNameController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.message.ErrorMessageGUI;
import pt.ipp.isep.dei.ui.gui.message.SuccessMessageGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.EditStation.EditStationGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.StationRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for editing the name of a station in the simulation.
 * Handles user interactions for changing a station's name, including validation,
 * success and error messages, and navigation between screens.
 */
public class EditStationNameGUI implements Initializable {

    private EditStationNameController controller;

    @FXML
    private Label stationNameLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField NewNameTextField;

    /**
     * Initializes the controller for editing the station name.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new EditStationNameController();
    }

    /**
     * Sets the simulation and station data for this GUI.
     * Updates the label to show the current station name.
     *
     * @param simulation The simulation instance.
     * @param station The station to edit.
     */
    public void setData(Simulation simulation, Station station) {
        controller.setSimulation(simulation);
        controller.setStation(station);
        stationNameLabel.setText(controller.getStationName());
    }

    /**
     * Handles the action of the back button, returning to the edit station screen.
     *
     * @param event The action event triggered by the user.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/EditStation/EditStation.fxml"));
            Parent root = loader.load();

            EditStationGUI editStationGUI = loader.getController();
            editStationGUI.setData(controller.getSimulation(), controller.getStation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose what to Edit");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the save button for the new station name.
     * Validates the input and proceeds to edit the name if valid.
     *
     * @param event The action event triggered by the user.
     */
    public void handleSaveNewNameButtonOnAction(ActionEvent event) {
        if (NewNameTextField.getText().isEmpty()) {
            errorLabel.setText("You need to fill the field with the name that you want!!");
            return;
        }
        controller.setNewName(NewNameTextField.getText());
        editNewName(event);
    }

    /**
     * Edits the station name using the controller.
     * Shows a success or error message depending on the result,
     * and then returns to the main station related screen.
     *
     * @param event The action event triggered by the user.
     */
    private void editNewName(ActionEvent event) {
        boolean result = controller.editName();

        if (result) {
            showSuccessMessage("New name changed Successfully.");
        } else {
            showErrorMessage("Error changing the name.");
        }

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
     * Shows a success message in a popup window for 5 seconds.
     *
     * @param message The success message to display.
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
     * Shows an error message in a popup window for 5 seconds.
     *
     * @param message The error message to display.
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
     * Gets the controller for editing the station name.
     *
     * @return The EditStationNameController instance.
     */
    public EditStationNameController getController() {
        return controller;
    }

    /**
     * Sets the controller for editing the station name.
     *
     * @param controller The EditStationNameController to set.
     */
    public void setController(EditStationNameController controller) {
        this.controller = controller;
    }

    /**
     * Gets the label displaying the station name.
     *
     * @return The station name label.
     */
    public Label getStationNameLabel() {
        return stationNameLabel;
    }

    /**
     * Sets the label displaying the station name.
     *
     * @param stationNameLabel The station name label to set.
     */
    public void setStationNameLabel(Label stationNameLabel) {
        this.stationNameLabel = stationNameLabel;
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
     * Gets the text field for the new station name.
     *
     * @return The new name text field.
     */
    public TextField getNewNameTextField() {
        return NewNameTextField;
    }

    /**
     * Sets the text field for the new station name.
     *
     * @param newNameTextField The new name text field to set.
     */
    public void setNewNameTextField(TextField newNameTextField) {
        this.NewNameTextField = newNameTextField;
    }
}