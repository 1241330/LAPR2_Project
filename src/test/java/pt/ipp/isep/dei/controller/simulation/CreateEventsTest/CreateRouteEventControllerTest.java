package pt.ipp.isep.dei.controller.simulation.CreateEventsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.CreateEvents.CreateRouteEventController;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.RouteEvent;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CreateRouteEventController} class.
 * These tests ensure that route event creation and list management behave correctly under various conditions.
 */
public class CreateRouteEventControllerTest {
    private Simulation simulation;
    private Scenario scenario;
    private Route route1;
    private Route route2;
    private TimeDate timeDate;

    /**
     * Initializes a simulation and scenario with two routes before each test.
     */
    @BeforeEach
    void setUp() {
        // Setup a minimal scenario and simulation with two routes
        timeDate = new TimeDate(2024, 6, 15);
        scenario = new Scenario(null, "Scenario1", 1000, timeDate, timeDate);
        simulation = new Simulation("Sim1", scenario);
        List<pt.ipp.isep.dei.domain.Simulation.PointOfRoute> points = new ArrayList<>();
        List<pt.ipp.isep.dei.domain.RailwayLine.RailwayLine> path = new ArrayList<>();
        route1 = new Route(points, path, "RouteA", true);
        route2 = new Route(points, path, "RouteB", false);
        simulation.getRoutes().add(route1);
        simulation.getRoutes().add(route2);
    }

    /**
     * Tests that the controller is properly initialized with empty route event list.
     */
    @Test
    void testConstructorInitializesCorrectly() {
        CreateRouteEventController controller = new CreateRouteEventController(simulation, scenario);
        assertNotNull(controller);
        assertTrue(controller.getRouteEventList().isEmpty());
    }

    /**
     * Tests that calling setRouteEventList with null does not replace the existing list.
     */
    @Test
    void testSetRouteEventListWithNullDoesNotChangeList() {
        CreateRouteEventController controller = new CreateRouteEventController(simulation, scenario);
        List<Event> original = controller.getRouteEventList();
        controller.setRouteEventList(null);
        assertSame(original, controller.getRouteEventList());
    }

    /**
     * Tests that calling setRouteEventList with a non-null list replaces the current list.
     */
    @Test
    void testSetRouteEventListWithNonNullReplacesList() {
        CreateRouteEventController controller = new CreateRouteEventController(simulation, scenario);
        List<Event> newList = new ArrayList<>();
        controller.setRouteEventList(newList);
        assertSame(newList, controller.getRouteEventList());
    }

    /**
     * Tests that addRouteEventsToList adds a RouteEvent for each route in the simulation.
     */
    @Test
    void testAddRouteEventsToListAddsEventsForAllRoutes() {
        CreateRouteEventController controller = new CreateRouteEventController(simulation, scenario);
        controller.addRouteEventsToList();
        List<Event> events = controller.getRouteEventList();
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(e -> e instanceof RouteEvent && ((RouteEvent) e).getRoute().equals(route1)));
        assertTrue(events.stream().anyMatch(e -> e instanceof RouteEvent && ((RouteEvent) e).getRoute().equals(route2)));
    }

    /**
     * Tests that addRouteEventsToList does not duplicate events when called multiple times.
     */
    @Test
    void testAddRouteEventsToListDoesNotDuplicateEvents() {
        CreateRouteEventController controller = new CreateRouteEventController(simulation, scenario);
        controller.addRouteEventsToList();
        int initialSize = controller.getRouteEventList().size();
        controller.addRouteEventsToList(); // Should not add duplicates
        assertEquals(initialSize, controller.getRouteEventList().size());
    }

    /**
     * Tests that getRouteEventList returns the correct list and reflects changes after events are added.
     */
    @Test
    void testGetRouteEventListReturnsCurrentList() {
        CreateRouteEventController controller = new CreateRouteEventController(simulation, scenario);
        List<Event> list = controller.getRouteEventList();
        assertNotNull(list);
        assertTrue(list.isEmpty());
        controller.addRouteEventsToList();
        assertFalse(controller.getRouteEventList().isEmpty());
    }
}
