package pt.ipp.isep.dei.ui.gui.message;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Success Message GUI screen.
 * Handles the display and interaction with success messages in the GUI.
 */
public class SuccessMessageGUI implements Initializable {

    /**
     * Label to display the success message to the user.
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
     * Handles the action of the OK button. Hides the success message window when the button is clicked.
     */
    @FXML
    public void handleOkkButton() {
        errorLabel.getScene().getWindow().hide();
    }

    /**
     * Sets the success message to be displayed in the label.
     *
     * @param message The success message to display.
     */
    public void setSuccessMessage(String message) {
        errorLabel.setText(message);
    }
}
