package pt.ipp.isep.dei.domain.Station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Building} class.
 * These tests validate the basic functionality of buildings including
 * construction, property access, and string representation.
 */
class BuildingTest {

    private Building building;
    private BuildingType type;
    private static final String BUILDING_NAME = "Telegraph";
    private static final int CONSTRUCTION_COST = 30;

    /**
     * Sets up a building instance before each test.
     */
    @BeforeEach
    void setUp() {
        type = BuildingType.TELEGRAPH;
        building = new Building(type);
    }

    /**
     * Tests the building constructor and verifies the building type is set correctly.
     */
    @Test
    void testConstructor() {
        assertNotNull(building);
        assertEquals(type, building.getType());
    }

    /**
     * Tests the getName method returns the correct building name.
     */
    @Test
    void testGetName() {
        assertEquals(BUILDING_NAME, building.getName());
    }

    /**
     * Tests the getConstructionCost method returns the correct cost.
     */
    @Test
    void testGetConstructionCost() {
        assertEquals(CONSTRUCTION_COST, building.getConstructionCost());
    }

    /**
     * Tests the toString method returns the correct string representation.
     */
    @Test
    void testToString() {
        String expected = BUILDING_NAME + " (Cost: " + CONSTRUCTION_COST + ")";
        assertEquals(expected, building.toString());
    }
}