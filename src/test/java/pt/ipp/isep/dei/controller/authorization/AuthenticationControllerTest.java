package pt.ipp.isep.dei.controller.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.Bootstrap;
import pt.ipp.isep.dei.repository.AuthenticationRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerTest {

    private AuthenticationController controller;
    private AuthenticationRepository authRepo;

    /**
     * Sets up the test environment by logging out any user,
     * initializing the Bootstrap users, and instantiating the controller.
     */
    @BeforeEach
    void setUp() {
        authRepo = Repositories.getInstance().getAuthenticationRepository();
        authRepo.doLogout();

        new Bootstrap().addUsers();

        controller = new AuthenticationController();
    }

    /**
     * Tests that a user with valid credentials is successfully logged in.
     */
    @Test
    void doLogin_WithValidCredentials_ShouldReturnTrue() {
        boolean result = controller.doLogin("editor@this.app", "edt");
        assertTrue(result);
        assertTrue(authRepo.getCurrentUserSession().isLoggedIn());
    }

    /**
     * Tests that login fails with invalid credentials.
     */
    @Test
    void doLogin_WithInvalidCredentials_ShouldReturnFalse() {
        boolean result = controller.doLogin("invalid@this.app", "wrong");
        assertFalse(result);
        assertFalse(authRepo.getCurrentUserSession().isLoggedIn());
    }

    /**
     * Tests that the correct user roles are returned when a user is logged in.
     */
    @Test
    void getUserRoles_WhenLoggedIn_ShouldReturnRoles() {
        controller.doLogin("editor@this.app", "edt");
        List<UserRoleDTO> roles = controller.getUserRoles();
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
        assertEquals(AuthenticationController.ROLE_EDITOR, roles.get(0).getId());
    }

    /**
     * Tests that null is returned when no user is logged in.
     */
    @Test
    void getUserRoles_WhenNotLoggedIn_ShouldReturnNull() {
        List<UserRoleDTO> roles = controller.getUserRoles();
        assertNull(roles);
    }

    /**
     * Tests that logging out successfully ends the user session.
     */
    @Test
    void doLogout_ShouldLogoutUser() {
        controller.doLogin("editor@this.app", "edt");
        assertTrue(authRepo.getCurrentUserSession().isLoggedIn());
        controller.doLogout();
        assertFalse(authRepo.getCurrentUserSession().isLoggedIn());
    }
}
