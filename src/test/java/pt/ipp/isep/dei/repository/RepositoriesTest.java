package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Repositories} singleton class.
 * This class covers all scenarios for singleton behavior and repository getters.
 */
class RepositoriesTest {

    /**
     * Tests that the singleton instance is consistent and only one instance exists.
     */
    @Test
    void testSingletonInstance() {
        Repositories instance1 = Repositories.getInstance();
        Repositories instance2 = Repositories.getInstance();
        assertSame(instance1, instance2, "Should always return the same instance (Singleton)");
    }

    /**
     * Tests that all repository getters return non-null instances.
     */
    @Test
    void testRepositoriesNotNull() {
        Repositories repos = Repositories.getInstance();
        assertNotNull(repos.getAuthenticationRepository(), "AuthenticationRepository should not be null");
        assertNotNull(repos.getMapRepository(), "MapRepository should not be null");
        assertNotNull(repos.getLocomotiveRepository(), "LocomotiveRepository should not be null");
        assertNotNull(repos.getCarriageRepository(), "CarriageRepository should not be null");
        assertNotNull(repos.getPrimaryResourceRepository(), "PrimaryResourceRepository should not be null");
        assertNotNull(repos.getTransformingTypeRepository(), "TransformingResourceRepository should not be null");
        assertNotNull(repos.getHouseBlockResourceRepository(), "HouseBlockResourceRepository should not be null");
    }

    /**
     * Tests that each repository getter always returns the same instance (singleton behavior for each repository).
     */
    @Test
    void testRepositoriesAreSingletons() {
        Repositories repos = Repositories.getInstance();
        assertSame(repos.getAuthenticationRepository(), repos.getAuthenticationRepository(), "AuthenticationRepository should be singleton");
        assertSame(repos.getMapRepository(), repos.getMapRepository(), "MapRepository should be singleton");
        assertSame(repos.getLocomotiveRepository(), repos.getLocomotiveRepository(), "LocomotiveRepository should be singleton");
        assertSame(repos.getCarriageRepository(), repos.getCarriageRepository(), "CarriageRepository should be singleton");
        assertSame(repos.getPrimaryResourceRepository(), repos.getPrimaryResourceRepository(), "PrimaryResourceRepository should be singleton");
        assertSame(repos.getTransformingTypeRepository(), repos.getTransformingTypeRepository(), "TransformingResourceRepository should be singleton");
        assertSame(repos.getHouseBlockResourceRepository(), repos.getHouseBlockResourceRepository(), "HouseBlockResourceRepository should be singleton");
    }

    /**
     * Tests that the singleton instance is not null.
     */
    @Test
    void testGetInstanceNotNull() {
        assertNotNull(Repositories.getInstance(), "Repositories singleton instance should not be null");
    }
}

