package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Event} class.
 * These tests validate the correct behavior of the {@code Event} class, including
 * construction, property accessors (getters/setters), and date calculations.
 */
class EventTest {

    private Event event;
    private static final String NAME = "Test Event";
    private static final int INTERVAL = 10;
    private static final int ACTUAL_DATE = 2024;

    /**
     * Initializes a default {@link Event} instance before each test.
     */
    @BeforeEach
    void setUp() {
        event = new Event(NAME, INTERVAL, ACTUAL_DATE);
    }

    /**
     * Tests the constructor to ensure the fields are initialized correctly.
     */
    @Test
    void testConstructor() {
        assertNotNull(event);
        assertEquals(NAME, event.getName());
        assertEquals(INTERVAL, event.getInterval());
        assertEquals(ACTUAL_DATE + INTERVAL, event.getNextGenerationDate());
    }

    /**
     * Verifies the setter and getter for the {@code interval} field.
     */
    @Test
    void testSetAndGetInterval() {
        int newInterval = 20;
        event.setInterval(newInterval);
        assertEquals(newInterval, event.getInterval());
    }

    /**
     * Verifies the setter and getter for the {@code name} field.
     */
    @Test
    void testSetAndGetName() {
        String newName = "New Test Event";
        event.setName(newName);
        assertEquals(newName, event.getName());
    }

    /**
     * Verifies the setter and getter for the {@code nextGenerationDate} field.
     */
    @Test
    void testSetAndGetNextGenerationDate() {
        int newDate = 2035;
        event.setNextGenerationDate(newDate);
        assertEquals(newDate, event.getNextGenerationDate());
    }

    /**
     * Confirms that the {@code nextGenerationDate} is calculated correctly from constructor input.
     */
    @Test
    void testNextGenerationDateCalculation() {
        assertEquals(ACTUAL_DATE + INTERVAL, event.getNextGenerationDate());
    }
}
