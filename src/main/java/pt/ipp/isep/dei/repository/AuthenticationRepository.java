package pt.ipp.isep.dei.repository;

import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;

import java.io.Serializable;

/**
 * Repository responsible for handling authentication operations.
 * Provides methods for user login, logout, user and role management,
 * and access to the current user session.
 */
public class AuthenticationRepository implements Serializable {

    /**
     * Facade for authentication operations.
     */
    private AuthFacade authenticationFacade;

    /**
     * Constructs an AuthenticationRepository and initializes the AuthFacade.
     */
    public AuthenticationRepository() {
        this.authenticationFacade = new AuthFacade();
    }

    /**
     * Attempts to log in a user with the provided email and password.
     *
     * @param email the user's email
     * @param pwd   the user's password
     * @return true if login is successful, false otherwise
     */
    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
    }

    /**
     * Checks if a user exists with the given email.
     *
     * @param email the user's email
     * @return true if a user exists with the given email, false otherwise
     */
    public boolean existsUserWithEmail(String email) {
        return authenticationFacade.existsUser(email);
    }

    /**
     * Logs out the current user.
     */
    public void doLogout() {
        authenticationFacade.doLogout();
    }

    /**
     * Gets the current user session.
     *
     * @return the current UserSession
     */
    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    /**
     * Adds a new user role.
     *
     * @param id          the role identifier
     * @param description the role description
     * @return true if the role was added successfully, false otherwise
     */
    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    /**
     * Adds a new user with a specific role.
     *
     * @param name   the user's name
     * @param email  the user's email
     * @param pwd    the user's password
     * @param roleId the role identifier
     * @return true if the user was added successfully, false otherwise
     */
    public boolean addUserWithRole(String name, String email, String pwd, String roleId) {
        return authenticationFacade.addUserWithRole(name, email, pwd, roleId);
    }

    /**
     * Gets the authentication facade.
     *
     * @return the AuthFacade instance
     */
    public AuthFacade getAuthenticationFacade() {
        return authenticationFacade;
    }

    /**
     * Sets the authentication facade.
     *
     * @param authenticationFacade the AuthFacade instance to set
     */
    public void setAuthenticationFacade(AuthFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }
}