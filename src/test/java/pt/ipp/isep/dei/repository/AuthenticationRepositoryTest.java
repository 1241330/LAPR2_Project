package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.UserSession;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRepositoryTest {

    private AuthenticationRepository authRepo;

    /**
     * Initializes the authentication repository and sets up user roles and users before each test.
     */
    @BeforeEach
    void setUp() {
        authRepo = new AuthenticationRepository();
        authRepo.doLogout();
        authRepo.addUserRole("EDITOR", "Editor");
        authRepo.addUserRole("PLAYER", "Player");
        authRepo.addUserWithRole("Editor", "editor@this.app", "edt", "EDITOR");
        authRepo.addUserWithRole("Player", "player@this.app", "ply", "PLAYER");
    }

    /**
     * Tests logging in with valid credentials.
     */
    @Test
    void testDoLoginWithValidCredentials() {
        boolean result = authRepo.doLogin("editor@this.app", "edt");
        assertTrue(result, "Login should succeed with valid credentials");
        assertTrue(authRepo.getCurrentUserSession().isLoggedIn(), "User session should be logged in");
    }

    /**
     * Tests logging in with invalid credentials.
     */
    @Test
    void testDoLoginWithInvalidCredentials() {
        boolean result = authRepo.doLogin("invalid@this.app", "wrong");
        assertFalse(result, "Login should fail with invalid credentials");
        assertFalse(authRepo.getCurrentUserSession().isLoggedIn(), "User session should not be logged in");
    }

    /**
     * Tests logging out after a successful login.
     */
    @Test
    void testDoLogout() {
        authRepo.doLogin("editor@this.app", "edt");
        assertTrue(authRepo.getCurrentUserSession().isLoggedIn(), "User should be logged in before logout");
        authRepo.doLogout();
        assertFalse(authRepo.getCurrentUserSession().isLoggedIn(), "User should be logged out after logout");
    }

    /**
     * Tests adding a new user role.
     */
    @Test
    void testAddUserRole() {
        boolean added = authRepo.addUserRole("TEST_ROLE", "Test Role");
        assertTrue(added, "Should add a new user role successfully");
    }

    /**
     * Tests adding a new user with a specific role and logging in with that user.
     */
    @Test
    void testAddUserWithRole() {
        boolean added = authRepo.addUserWithRole("Test User", "test@this.app", "test", "EDITOR");
        assertTrue(added, "Should add a new user with role successfully");
        assertTrue(authRepo.doLogin("test@this.app", "test"), "Should login with the new user");
    }

    /**
     * Tests retrieving the current user session.
     */
    @Test
    void testGetCurrentUserSession() {
        UserSession session = authRepo.getCurrentUserSession();
        assertNotNull(session, "User session should not be null");
    }

    /**
     * Tests login without any roles or users created.
     */
    @Test
    void testDoLoginWithoutUsers() {
        AuthenticationRepository repo = new AuthenticationRepository();
        boolean result = repo.doLogin("nouser@this.app", "nopass");
        assertFalse(result, "Login should fail when no users are registered");
    }

    /**
     * Tests logout without a previous login.
     */
    @Test
    void testDoLogoutWithoutLogin() {
        authRepo.doLogout();
        assertFalse(authRepo.getCurrentUserSession().isLoggedIn(), "Session should be logged out even without previous login");
    }

    /**
     * Tests adding a duplicate role.
     */
    @Test
    void testAddDuplicateUserRole() {
        boolean first = authRepo.addUserRole("DUPLICATE", "Duplicate");
        boolean second = authRepo.addUserRole("DUPLICATE", "Duplicate");
        assertTrue(first, "First addition should succeed");
        assertFalse(second, "Second addition should fail due to duplication");
    }

    /**
     * Tests adding a duplicate user.
     */
    @Test
    void testAddDuplicateUserWithRole() {
        boolean first = authRepo.addUserWithRole("Editor", "editor@this.app", "edt", "EDITOR");
        boolean second = authRepo.addUserWithRole("Editor", "editor@this.app", "edt", "EDITOR");
        assertFalse(second, "Should not allow adding a user with an existing email");
    }

    /**
     * Tests adding a user with a non-existent role.
     */
    @Test
    void testAddUserWithNonExistentRole() {
        boolean added = authRepo.addUserWithRole("Fake", "fake@this.app", "fake", "FAKE_ROLE");
        assertFalse(added, "Should not allow adding a user with a non-existent role");
    }

    /**
     * Tests getting the AuthFacade instance.
     */
    @Test
    void testGetAuthenticationFacade() {
        assertNotNull(authRepo.getAuthenticationFacade(), "AuthFacade should not be null");
    }
}
