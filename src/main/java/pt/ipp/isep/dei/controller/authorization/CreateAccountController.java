package pt.ipp.isep.dei.controller.authorization;

import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for handling user account creation.
 * Manages user role selection, email and password validation, and account creation logic.
 */
public class CreateAccountController {

    /** Repository for authentication operations. */
    private AuthenticationRepository authenticationRepository;

    /** User role selection: 1 for editor, 2 for player. */
    private int userSelected = 0;

    /** User's email address. */
    private String email;

    /** User's password. */
    private String password;

    /**
     * Constructs a CreateAccountController and initializes the authentication repository.
     */
    public CreateAccountController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    /**
     * Sets the user role selection to player.
     */
    public void setPlayerSelected() {
        userSelected = 2;
    }

    /**
     * Sets the user role selection to editor.
     */
    public void setEditorSelected() {
        userSelected = 1;
    }

    /**
     * Gets the selected user role.
     *
     * @return the selected user role (1 for editor, 2 for player)
     */
    public int getUserSelected() {
        return userSelected;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's email address.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's password.
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Creates a new user account with the selected role, email, and password.
     * Throws an exception if email or password are empty.
     */
    public void createAccount() {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password cannot be empty.");
        }
        if (userSelected == 1) {
            authenticationRepository.addUserWithRole("Editor", email, password, AuthenticationController.ROLE_EDITOR);
        } else {
            authenticationRepository.addUserWithRole("Player", email, password, AuthenticationController.ROLE_PLAYER);
        }
    }

    /**
     * Validates the user's email and password according to specific rules.
     *
     * @return 0 if valid, error code otherwise:
     *         1 - Invalid email format
     *         2 - Password too short
     *         3 - Invalid character in password
     *         4 - Password must contain at least 3 uppercase letters
     *         5 - Password must contain at least 2 digits
     *         6 - Email or password is empty
     *         7 - Email already exists
     * @throws IllegalArgumentException if email or password are null
     */
    public int validateEmailAndPassword() {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password cannot be null.");
        }

        if (email.isEmpty() || password.isEmpty()) {
            return 6; // Email or password is empty
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return 1; // Invalid email format
        }

        if (authenticationRepository.existsUserWithEmail(email)) {
            return 7; // Email already exists
        }

        if (password.length() < 7) {
            return 2; // Password too short
        }

        int uppercaseCount = 0;
        int digitCount = 0;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isDigit(c)) {
                digitCount++;
            } else if (!Character.isLetterOrDigit(c)) {
                return 3; // Invalid character in password
            }
        }

        if (uppercaseCount < 3) {
            return 4; // Password must contain at least 3 uppercase letters
        }

        if (digitCount < 2) {
            return 5; // Password must contain at least 2 digits
        }

        return 0; // Valid email and password
    }

    /**
     * Gets the authentication repository.
     *
     * @return the authentication repository
     */
    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }

    /**
     * Sets the authentication repository.
     *
     * @param authenticationRepository the authentication repository to set
     */
    public void setAuthenticationRepository(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }
}