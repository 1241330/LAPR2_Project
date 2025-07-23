package pt.ipp.isep.dei.domain.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PrimaryResource} class.
 * These tests verify the correct behavior of getters, setters, and inheritance
 * for primary resource entities in the simulation.
 */
class PrimaryResourceTest {

    private PrimaryResource primaryResource;

    private static final String NAME = "Iron";
    private static final int MAX_RESOURCES = 1000;
    private static final int INTERVAL = 10;
    private static final int QUANTITY = 50;

    /**
     * Initializes a default {@link PrimaryResource} instance before each test.
     */
    @BeforeEach
    void setUp() {
        primaryResource = new PrimaryResource(NAME, MAX_RESOURCES, INTERVAL, QUANTITY);
    }

    /**
     * Tests the constructor and verifies all field values are correctly initialized.
     */
    @Test
    void testConstructor() {
        assertNotNull(primaryResource);
        assertEquals(NAME, primaryResource.getName());
        assertEquals(MAX_RESOURCES, primaryResource.getMaxResources());
        assertEquals(INTERVAL, primaryResource.getIntervalBetweenResourceGeneration());
        assertEquals(QUANTITY, primaryResource.getQuantityProduced());
    }

    /**
     * Verifies the setter and getter for the {@code name} field.
     */
    @Test
    void testSetAndGetName() {
        String newName = "Coal";
        primaryResource.setName(newName);
        assertEquals(newName, primaryResource.getName());
    }

    /**
     * Verifies the setter and getter for the {@code maxResources} field.
     */
    @Test
    void testSetAndGetMaxResources() {
        int newMax = 2000;
        primaryResource.setMaxResources(newMax);
        assertEquals(newMax, primaryResource.getMaxResources());
    }

    /**
     * Verifies the setter and getter for the {@code intervalBetweenResourceGeneration} field.
     */
    @Test
    void testSetAndGetInterval() {
        int newInterval = 20;
        primaryResource.setIntervalBetweenResourceGeneration(newInterval);
        assertEquals(newInterval, primaryResource.getIntervalBetweenResourceGeneration());
    }

    /**
     * Verifies the setter and getter for the {@code quantityProduced} field.
     */
    @Test
    void testSetAndGetQuantityProduced() {
        int newQuantity = 100;
        primaryResource.setQuantityProduced(newQuantity);
        assertEquals(newQuantity, primaryResource.getQuantityProduced());
    }

    /**
     * Ensures the {@code toString()} method returns the expected formatted string.
     */
    @Test
    void testToString() {
        String expected = NAME + " (Max resources storage: " + MAX_RESOURCES
                + ", Interval between resource generation: " + INTERVAL
                + ", Quantity produced per generation: " + QUANTITY + ")";
        assertEquals(expected, primaryResource.toString());
    }

    /**
     * Confirms that {@link PrimaryResource} inherits from {@link ResourcesType}.
     */
    @Test
    void testInheritanceFromResourcesType() {
        assertTrue(primaryResource instanceof ResourcesType);
    }
}
