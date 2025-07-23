package pt.ipp.isep.dei.domain.Station;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link StationType} enum.
 * This class verifies the correctness of construction costs and influential radii
 * associated with each station type, as well as the integrity of enum operations.
 */
class StationTypeTest {

    /**
     * Tests that the values associated with the {@code DEPOT} type are correct.
     */
    @Test
    void testDepotValues() {
        assertEquals(50, StationType.DEPOT.getConstructionCost());
        assertEquals(3, StationType.DEPOT.getInfluentialRadius());
    }

    /**
     * Tests that the values associated with the {@code STATION} type are correct.
     */
    @Test
    void testStationValues() {
        assertEquals(100, StationType.STATION.getConstructionCost());
        assertEquals(4, StationType.STATION.getInfluentialRadius());
    }

    /**
     * Tests that the values associated with the {@code TERMINAL} type are correct.
     */
    @Test
    void testTerminalValues() {
        assertEquals(200, StationType.TERMINAL.getConstructionCost());
        assertEquals(5, StationType.TERMINAL.getInfluentialRadius());
    }

    /**
     * Tests that the {@code values()} method returns the expected enum constants in the correct order.
     */
    @Test
    void testEnumValues() {
        StationType[] expectedValues = {StationType.DEPOT, StationType.STATION, StationType.TERMINAL};
        assertArrayEquals(expectedValues, StationType.values());
    }

    /**
     * Tests that the {@code valueOf(String)} method returns the correct enum constant.
     */
    @Test
    void testValueOf() {
        assertEquals(StationType.DEPOT, StationType.valueOf("DEPOT"));
        assertEquals(StationType.STATION, StationType.valueOf("STATION"));
        assertEquals(StationType.TERMINAL, StationType.valueOf("TERMINAL"));
    }

    /**
     * Ensures that the construction cost of each station type is strictly positive.
     */
    @Test
    void testConstructionCostIsPositive() {
        for (StationType type : StationType.values()) {
            assertTrue(type.getConstructionCost() > 0,
                    "Construction cost should be positive for " + type.name());
        }
    }

    /**
     * Ensures that the influential radius of each station type is strictly positive.
     */
    @Test
    void testInfluentialRadiusIsPositive() {
        for (StationType type : StationType.values()) {
            assertTrue(type.getInfluentialRadius() > 0,
                    "Influential radius should be positive for " + type.name());
        }
    }

    /**
     * Verifies that construction costs increase in the order:
     * {@code DEPOT < STATION < TERMINAL}.
     */
    @Test
    void testCostsIncreaseWithStationType() {
        assertTrue(StationType.DEPOT.getConstructionCost() < StationType.STATION.getConstructionCost());
        assertTrue(StationType.STATION.getConstructionCost() < StationType.TERMINAL.getConstructionCost());
    }

    /**
     * Verifies that influential radii increase in the order:
     * {@code DEPOT < STATION < TERMINAL}.
     */
    @Test
    void testRadiiIncreaseWithStationType() {
        assertTrue(StationType.DEPOT.getInfluentialRadius() < StationType.STATION.getInfluentialRadius());
        assertTrue(StationType.STATION.getInfluentialRadius() < StationType.TERMINAL.getInfluentialRadius());
    }
}
