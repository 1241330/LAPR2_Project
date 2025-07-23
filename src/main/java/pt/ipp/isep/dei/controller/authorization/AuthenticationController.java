package pt.ipp.isep.dei.controller.authorization;

import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * Controller responsible for handling user authentication and authorization.
 * Provides methods for login, logout, retrieving user roles, and selecting a role.
 */
public class AuthenticationController {

    /** Constant representing the editor role. */
    public static final String ROLE_EDITOR = "EDITOR";
    /** Constant representing the player role. */
    public static final String ROLE_PLAYER = "PLAYER";

    /** Repository for authentication operations. */
    private AuthenticationRepository authenticationRepository;

    /**
     * Constructs an AuthenticationController and initializes the authentication repository.
     */
    public AuthenticationController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    /**
     * Attempts to log in a user with the provided email and password.
     *
     * @param email the user's email
     * @param pwd the user's password
     * @return true if login is successful, false otherwise
     */
    public boolean doLogin(String email, String pwd) {
        try {
            return authenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Retrieves the list of roles for the currently logged-in user.
     *
     * @return a list of UserRoleDTO if the user is logged in, null otherwise
     */
    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    /**
     * Logs out the currently logged-in user.
     */
    public void doLogout() {
        authenticationRepository.doLogout();
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

    /**
     * Allows the user to select a role from a list of roles.
     * If there is only one role, it is automatically selected.
     * Otherwise, the user is prompted to select a role.
     *
     * @param roles the list of roles to choose from
     * @return the selected UserRoleDTO
     */
    public UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.getFirst();
        } else {
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
        }
    }
}