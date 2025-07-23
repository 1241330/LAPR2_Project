package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Event.Event;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link EventRepository} class.
 * This class covers all scenarios for adding, removing, checking and retrieving events.
 */
class EventRepositoryTest {

    private EventRepository repository;
    private Event event1;
    private Event event2;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new EventRepository();
        event1 = new Event("Event1", 1, 0);
        event2 = new Event("Event2", 2, 0);
    }

    /**
     * Tests adding an event successfully.
     */
    @Test
    void testAddEvent_Success() {
        assertTrue(repository.addEvent(event1), "Should add event successfully");
        assertEquals(1, repository.getEventCount(), "Repository should contain one event");
    }

    /**
     * Tests adding an event with a duplicate name.
     */
    @Test
    void testAddEvent_DuplicateName() {
        repository.addEvent(event1);
        Event duplicate = new Event("Event1", 5, 10);
        assertFalse(repository.addEvent(duplicate), "Should not add event with duplicate name");
        assertEquals(1, repository.getEventCount(), "Repository should still contain one event");
    }

    /**
     * Tests adding a null event (should throw IllegalArgumentException).
     */
    @Test
    void testAddEvent_Null() {
        assertThrows(IllegalArgumentException.class, () -> repository.addEvent(null), "Adding null event should throw IllegalArgumentException");
    }

    /**
     * Tests removing an event successfully.
     */
    @Test
    void testRemoveEvent_Success() {
        repository.addEvent(event1);
        assertTrue(repository.removeEvent(event1), "Should remove event successfully");
        assertEquals(0, repository.getEventCount(), "Repository should be empty after removal");
    }

    /**
     * Tests removing an event that does not exist.
     */
    @Test
    void testRemoveEvent_NotExists() {
        assertFalse(repository.removeEvent(event1), "Should not remove non-existent event");
    }

    /**
     * Tests removing a null event (should throw IllegalArgumentException).
     */
    @Test
    void testRemoveEvent_Null() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeEvent(null), "Removing null event should throw IllegalArgumentException");
    }

    /**
     * Tests retrieving all events after adding multiple events.
     */
    @Test
    void testGetAllEvents() {
        repository.addEvent(event1);
        repository.addEvent(event2);
        List<Event> events = repository.getAllEvents();
        assertEquals(2, events.size(), "Repository should contain two events");
        assertTrue(events.contains(event1), "Repository should contain event1");
        assertTrue(events.contains(event2), "Repository should contain event2");
    }

    /**
     * Tests retrieving all events when repository is empty.
     */
    @Test
    void testGetAllEvents_Empty() {
        List<Event> events = repository.getAllEvents();
        assertNotNull(events, "Event list should not be null");
        assertTrue(events.isEmpty(), "Event list should be empty");
    }

    /**
     * Tests checking if an event exists in the repository.
     */
    @Test
    void testEventExists() {
        repository.addEvent(event1);
        assertTrue(repository.eventExists(event1), "Should return true for existing event");
        assertFalse(repository.eventExists(event2), "Should return false for non-existing event");
    }

    /**
     * Tests checking if an event name exists in the repository.
     */
    @Test
    void testEventNameExists() {
        repository.addEvent(event1);
        assertTrue(repository.eventNameExists("Event1"), "Should return true for existing event name");
        assertFalse(repository.eventNameExists("OtherEvent"), "Should return false for non-existing event name");
    }

    /**
     * Tests setting and getting the list of events.
     */
    @Test
    void testSetAndGetEvents() {
        List<Event> newList = new ArrayList<>();
        newList.add(event1);
        repository.setEvents(newList);
        assertEquals(newList, repository.getEvents(), "Repository should return the set list of events");
    }

    /**
     * Tests setting the event list to null (should allow or throw exception based on implementation).
     */
    @Test
    void testSetEvents_Null() {
        repository.setEvents(null);
        assertNull(repository.getEvents(), "Repository should allow setting events to null or handle accordingly");
    }
}
