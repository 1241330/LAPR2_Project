package pt.ipp.isep.dei.ui.gui.message;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Error Message GUI screen.
 * Handles the display and interaction with error messages in the GUI.
 */
public class ErrorMessageGUI implements Initializable {

    /**
     * Label to display the error message to the user.
     */
    @FXML
    Label errorLabel;

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Handles the action of the OK button. Hides the error message window when the button is clicked.
     */
    @FXML
    public void handleOkkButton() {
        errorLabel.getScene().getWindow().hide();
    }

    /**
     * Sets the error message to be displayed in the error label.
     *
     * @param message The error message to display.
     */
    public void setErrorMessage(String message) {
        errorLabel.setText(message);
    }
}
