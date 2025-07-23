package pt.ipp.isep.dei.ui.gui.authorization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.authorization.CreateAccountController;
import pt.ipp.isep.dei.ui.console.menu.EditorUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the Create Account screen in the MABEC application.
 * <p>
 * Handles user input for account creation, including email/password validation,
 * role selection (Editor or Player), and redirection upon success.
 * </p>
 */
public class CreateAccountGUI implements Initializable {

    private CreateAccountController controller;

    @FXML
    private PasswordField loginPasswordTextField;

    @FXML
    private TextField loginEmailTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button editorButton;

    @FXML
    private Button playerButton;

    /**
     * Initializes the UI components and sets default styles.
     *
     * @param url            location used to resolve relative paths
     * @param resourceBundle resources for localization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new CreateAccountController();
        errorLabel.setText("");
        activatePlayerButton();
        activateEditorButton();
    }

    /**
     * Applies active style to the player button.
     */
    private void activatePlayerButton() {
        playerButton.setStyle("-fx-background-color: #b39a6f");
    }

    /**
     * Applies inactive style to the player button.
     */
    private void deactivatePlayerButton() {
        playerButton.setStyle("-fx-background-color: #d6c1a1");
    }

    /**
     * Applies active style to the editor button.
     */
    private void activateEditorButton() {
        editorButton.setStyle("-fx-background-color: #b39a6f");
    }

    /**
     * Applies inactive style to the editor button.
     */
    private void deactivateEditorButton() {
        editorButton.setStyle("-fx-background-color: #d6c1a1");
    }

    /**
     * Handles the selection of the editor role.
     * Updates styles and internal controller state.
     */
    @FXML
    public void handleSelectEditorButton() {
        controller.setEditorSelected();
        deactivateEditorButton();
        activatePlayerButton();
    }

    /**
     * Handles the selection of the player role.
     * Updates styles and internal controller state.
     */
    @FXML
    public void handleSelectPlayerButton() {
        controller.setPlayerSelected();
        deactivatePlayerButton();
        activateEditorButton();
    }

    /**
     * Handles the "Back" button click, returning to the login screen.
     *
     * @param event the triggered event
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/authorization/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the creation of an account when the login button is clicked.
     *
     * @param event the triggered event
     */
    @FXML
    public void handleLoginButton(ActionEvent event) {
        createAccount(event);
    }

    /**
     * Handles pressing Enter in the email field. Triggers validation and account creation.
     *
     * @param event the triggered event
     */
    @FXML
    public void handleEmailTextFieldEnter(ActionEvent event) {
        if (loginEmailTextField.getText().isEmpty() || loginPasswordTextField.getText().isEmpty()) {
            errorLabel.setText("Please fill in both fields.");
        } else {
            createAccount(event);
        }
    }

    /**
     * Handles pressing Enter in the password field. Triggers validation and account creation.
     *
     * @param event the triggered event
     */
    @FXML
    public void handlePasswordTextFieldEnter(ActionEvent event) {
        if (loginEmailTextField.getText().isEmpty() || loginPasswordTextField.getText().isEmpty()) {
            errorLabel.setText("Please fill in both fields.");
        } else {
            createAccount(event);
        }
    }

    /**
     * Performs account creation with validation. Displays feedback for specific validation failures.
     * Redirects to login or role-specific UI if successful.
     *
     * @param event the triggered event
     */
    private void createAccount(ActionEvent event) {
        if (controller.getUserSelected() == 0) {
            errorLabel.setText("Please select a user type (Editor or Player).");
            return;
        }

        controller.setEmail(loginEmailTextField.getText());
        controller.setPassword(loginPasswordTextField.getText());
        int validationResult = controller.validateEmailAndPassword();

        switch (validationResult) {
            case 0:
                controller.createAccount();
                redirectToLogin(event);
                break;
            case 1:
                errorLabel.setText("Invalid email format, valid format is: aaaaaa@bbbb.ccc");
                break;
            case 2:
                errorLabel.setText("Password must be at least 7 characters long.");
                break;
            case 3:
                errorLabel.setText("Password contains invalid characters.");
                break;
            case 4:
                errorLabel.setText("Password must contain at least 3 uppercase letters.");
                break;
            case 5:
                errorLabel.setText("Password must contain at least 2 digits.");
                break;
            case 6:
                errorLabel.setText("Email and password cannot be empty.");
                break;
            case 7:
                errorLabel.setText("Email already exists, please choose another one.");
                break;
            default:
                errorLabel.setText("An unknown error occurred.");
        }
    }

    /**
     * Redirects the user to the appropriate UI (Editor or Player) after account creation.
     *
     * @param event the triggered event
     */
    public void redirectToLogin(ActionEvent event) {
        if (controller.getUserSelected() == 1) {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            EditorUI editorUI = new EditorUI();
            editorUI.run();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("MABEC - Player Mode");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the controller instance.
     *
     * @return the CreateAccountController instance
     */
    public CreateAccountController getController() {
        return controller;
    }

    /**
     * Sets the controller instance.
     *
     * @param controller the CreateAccountController to set
     */
    public void setController(CreateAccountController controller) {
        this.controller = controller;
    }

    /**
     * Gets the login password text field.
     *
     * @return the PasswordField for login password
     */
    public PasswordField getLoginPasswordTextField() {
        return loginPasswordTextField;
    }

    /**
     * Sets the login password text field.
     *
     * @param loginPasswordTextField the PasswordField to set
     */
    public void setLoginPasswordTextField(PasswordField loginPasswordTextField) {
        this.loginPasswordTextField = loginPasswordTextField;
    }

    /**
     * Gets the login email text field.
     *
     * @return the TextField for login email
     */
    public TextField getLoginEmailTextField() {
        return loginEmailTextField;
    }

    /**
     * Sets the login email text field.
     *
     * @param loginEmailTextField the TextField to set
     */
    public void setLoginEmailTextField(TextField loginEmailTextField) {
        this.loginEmailTextField = loginEmailTextField;
    }

    /**
     * Gets the error label.
     *
     * @return the Label for error messages
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the error label.
     *
     * @param errorLabel the Label to set
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the editor button.
     *
     * @return the Button for editor selection
     */
    public Button getEditorButton() {
        return editorButton;
    }

    /**
     * Sets the editor button.
     *
     * @param editorButton the Button to set
     */
    public void setEditorButton(Button editorButton) {
        this.editorButton = editorButton;
    }

    /**
     * Gets the player button.
     *
     * @return the Button for player selection
     */
    public Button getPlayerButton() {
        return playerButton;
    }

    /**
     * Sets the player button.
     *
     * @param playerButton the Button to set
     */
    public void setPlayerButton(Button playerButton) {
        this.playerButton = playerButton;
    }
}