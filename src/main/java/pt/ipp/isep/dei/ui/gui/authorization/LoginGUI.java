package pt.ipp.isep.dei.ui.gui.authorization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.ui.console.menu.EditorUI;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * JavaFX controller class for the login screen of the MABEC application.
 * <p>
 * Handles user authentication, navigation to account creation and role-specific UIs.
 * </p>
 */
public class LoginGUI implements Initializable {

    private AuthenticationController controller;
    private ActionEvent actualEvent;

    @FXML
    private PasswordField loginPasswordTextField;

    @FXML
    private TextField loginEmailTextField;

    @FXML
    private Label loginFailed = new Label("");

    @FXML
    private Label loginLockerText;

    @FXML
    private Button registerButton;

    /**
     * Initializes the controller class.
     *
     * @param url            location used to resolve relative paths for the root object
     * @param resourceBundle resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new AuthenticationController();
    }

    /**
     * Handles the event when the login button is clicked.
     *
     * @param event the event object
     */
    @FXML
    public void handleLoginButton(ActionEvent event) {
        doLogin(event);
    }

    /**
     * Handles pressing Enter in the email text field.
     * Validates input and triggers login.
     *
     * @param event the event object
     */
    @FXML
    public void handleEmailTextFieldEnter(ActionEvent event) {
        if (loginEmailTextField.getText().isEmpty() || loginPasswordTextField.getText().isEmpty()) {
            loginFailed.setText("Please fill in both fields.");
        } else {
            doLogin(event);
        }
    }

    /**
     * Handles pressing Enter in the password text field.
     * Validates input and triggers login.
     *
     * @param event the event object
     */
    @FXML
    public void handlePasswordTextFieldEnter(ActionEvent event) {
        if (loginEmailTextField.getText().isEmpty() || loginPasswordTextField.getText().isEmpty()) {
            loginFailed.setText("Please fill in both fields.");
        } else {
            doLogin(event);
        }
    }

    /**
     * Handles the event when the register button is clicked.
     * Navigates to the account creation screen.
     *
     * @param event the event object
     */
    @FXML
    private void handleRegisterButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/authorization/CreateAccount.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Create Account");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to authenticate the user using the entered credentials.
     * On success, redirects to the appropriate role UI.
     *
     * @param event the event that triggered the login
     */
    private void doLogin(ActionEvent event) {
        actualEvent = event;
        boolean success = controller.doLogin(loginEmailTextField.getText(), loginPasswordTextField.getText());
        if (success) {
            loginLockerText.setTextFill(javafx.scene.paint.Color.GREEN);
            List<UserRoleDTO> roles = controller.getUserRoles();
            UserRoleDTO role = controller.selectsRole(roles);
            List<MenuItem> rolesUI = getMenuItemForRoles();
            this.redirectToRoleUI(rolesUI, role);
        } else {
            loginFailed.setText("Login failed. Please try again.");
            loginLockerText.setTextFill(javafx.scene.paint.Color.RED);
            loginEmailTextField.clear();
            loginPasswordTextField.clear();
        }
    }

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the main menu.
     *
     * @param event the event object
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu/MainMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Main Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies style changes to the register button when hovered or focused.
     */
    @FXML
    public void handleShowSelection() {
        registerButton.setStyle("-fx-text-fill: #0000FF; -fx-background-color: transparent; -fx-border-color: transparent");
    }

    /**
     * Resets the register button style when not hovered or focused.
     */
    @FXML
    public void handleDontShowSelection() {
        registerButton.setStyle("-fx-text-fill: #8C7853; -fx-background-color: transparent; -fx-border-color: transparent");
    }

    /**
     * Creates and returns the menu items corresponding to each user role.
     *
     * @return list of menu items associated with each role
     */
    public List<MenuItem> getMenuItemForRoles() {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_EDITOR, this::redirectEditorMode));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_PLAYER, this::redirectPlayerMode));
        return rolesUI;
    }

    /**
     * Redirects the authenticated user to the editor mode UI.
     */
    private void redirectEditorMode() {
        ((Stage) ((Node) actualEvent.getSource()).getScene().getWindow()).close();
        EditorUI editorUI = new EditorUI();
        editorUI.run();
    }

    /**
     * Redirects the authenticated user to the player mode UI.
     */
    private void redirectPlayerMode() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actualEvent.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Player Menu");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirects the user to the corresponding UI based on their selected role.
     *
     * @param rolesUI the list of available menu items for roles
     * @param role    the selected user role
     */
    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role) {
        boolean found = false;
        Iterator<MenuItem> it = rolesUI.iterator();
        while (it.hasNext() && !found) {
            MenuItem item = it.next();
            found = item.hasDescription(role.getDescription());
            if (found) {
                item.run();
            }
        }
        if (!found) {
            System.out.println("There is no UI for users with role '" + role.getDescription() + "'");
        }
    }

    /**
     * Gets the login password text field.
     *
     * @return the login password text field
     */
    public PasswordField getLoginPasswordTextField() {
        return loginPasswordTextField;
    }

    /**
     * Sets the login password text field.
     *
     * @param loginPasswordTextField the login password text field to set
     */
    public void setLoginPasswordTextField(PasswordField loginPasswordTextField) {
        this.loginPasswordTextField = loginPasswordTextField;
    }

    /**
     * Gets the login email text field.
     *
     * @return the login email text field
     */
    public TextField getLoginEmailTextField() {
        return loginEmailTextField;
    }

    /**
     * Sets the login email text field.
     *
     * @param loginEmailTextField the login email text field to set
     */
    public void setLoginEmailTextField(TextField loginEmailTextField) {
        this.loginEmailTextField = loginEmailTextField;
    }

    /**
     * Gets the login failed label.
     *
     * @return the login failed label
     */
    public Label getLoginFailed() {
        return loginFailed;
    }

    /**
     * Sets the login failed label.
     *
     * @param loginFailed the login failed label to set
     */
    public void setLoginFailed(Label loginFailed) {
        this.loginFailed = loginFailed;
    }

    /**
     * Gets the login locker text label.
     *
     * @return the login locker text label
     */
    public Label getLoginLockerText() {
        return loginLockerText;
    }

    /**
     * Sets the login locker text label.
     *
     * @param loginLockerText the login locker text label to set
     */
    public void setLoginLockerText(Label loginLockerText) {
        this.loginLockerText = loginLockerText;
    }

    /**
     * Gets the register button.
     *
     * @return the register button
     */
    public Button getRegisterButton() {
        return registerButton;
    }

    /**
     * Sets the register button.
     *
     * @param registerButton the register button to set
     */
    public void setRegisterButton(Button registerButton) {
        this.registerButton = registerButton;
    }

    /**
     * Gets the authentication controller.
     *
     * @return the authentication controller
     */
    public AuthenticationController getController() {
        return controller;
    }

    /**
     * Sets the authentication controller.
     *
     * @param controller the authentication controller to set
     */
    public void setController(AuthenticationController controller) {
        this.controller = controller;
    }

    /**
     * Gets the actual event.
     *
     * @return the actual event
     */
    public ActionEvent getActualEvent() {
        return actualEvent;
    }

    /**
     * Sets the actual event.
     *
     * @param actualEvent the actual event to set
     */
    public void setActualEvent(ActionEvent actualEvent) {
        this.actualEvent = actualEvent;
    }
}