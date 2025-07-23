package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link HistoricalEvent} class.
 *
 * This test class validates the constructor, inheritance, and getter/setter
 * methods of {@code HistoricalEvent}, including the logic to compute the next generation date.
 */
class HistoricalEventTest {

    private HistoricalEvent historicalEvent;

    // Constants used for test setup
    private static final String NAME = "World War I";
    private static final int INTERVAL = 12;
    private static final int ACTUAL_DATE = 1914;

    /**
     * Creates a new {@link HistoricalEvent} instance before each test using preset constants.
     */
    @BeforeEach
    void setUp() {
        historicalEvent = new HistoricalEvent(NAME, INTERVAL, ACTUAL_DATE);
    }

    /**
     * Tests whether the constructor correctly initializes all fields and calculates the next generation date.
     */
    @Test
    void testConstructor() {
        assertNotNull(historicalEvent);
        assertEquals(NAME, historicalEvent.getName());
        assertEquals(INTERVAL, historicalEvent.getInterval());
        assertEquals(ACTUAL_DATE + INTERVAL, historicalEvent.getNextGenerationDate());
    }

    /**
     * Tests the {@link HistoricalEvent#setName(String)} and {@link HistoricalEvent#getName()} methods.
     */
    @Test
    void testSetAndGetName() {
        String newName = "World War II";
        historicalEvent.setName(newName);
        assertEquals(newName, historicalEvent.getName());
    }

    /**
     * Tests the {@link HistoricalEvent#setInterval(int)} and {@link HistoricalEvent#getInterval()} methods.
     */
    @Test
    void testSetAndGetInterval() {
        int newInterval = 24;
        historicalEvent.setInterval(newInterval);
        assertEquals(newInterval, historicalEvent.getInterval());
    }

    /**
     * Ensures that {@code HistoricalEvent} extends the {@link Event} class.
     */
    @Test
    void testInheritanceFromEvent() {
        assertTrue(historicalEvent instanceof Event);
    }

    /**
     * Tests the calculation of the next generation date based on interval and actual date.
     */
    @Test
    void testNextGenerationDateCalculation() {
        assertEquals(ACTUAL_DATE + INTERVAL, historicalEvent.getNextGenerationDate());
    }
}
