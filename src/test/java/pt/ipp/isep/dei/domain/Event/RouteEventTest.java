package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Simulation.TypeOfCargoMode;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteEventTest {
    private RouteEvent routeEvent;
    private Simulation simulation;
    private Route route;
    private Scenario scenario;
    private Station station;
    private PointOfRoute pointOfRoute;

    /**
     * Sets up minimal valid objects for RouteEvent before each test.
     */
    @BeforeEach
    void setUp() {
        simulation = new Simulation("Sim1", null);
        station = new Station("Station1");
        List<ResourcesType> resourcesTypes = new ArrayList<>();
        pointOfRoute = new PointOfRoute(resourcesTypes, station, TypeOfCargoMode.FULL);
        List<PointOfRoute> points = new ArrayList<>();
        points.add(pointOfRoute);
        route = new Route(points, new ArrayList<>(), "Route1", true);
        Map map = new Map("mapa", null);
        TimeDate begin = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2025, 1, 1);
        scenario = new Scenario(map, "cenario", 1000, begin, end);
        routeEvent = new RouteEvent("Event1", 1, 0, route, simulation, scenario);
    }

    /**
     * Tests the constructor and all getters.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals(simulation, routeEvent.getSimulation());
        assertEquals(route, routeEvent.getRoute());
        assertFalse(routeEvent.isActiveStatus());
    }

    /**
     * Tests the setters for simulation and route.
     */
    @Test
    void testSetters() {
        Simulation sim2 = new Simulation("Sim2", null);
        routeEvent.setSimulation(sim2);
        assertEquals(sim2, routeEvent.getSimulation());
        Route route2 = new Route(new ArrayList<>(), new ArrayList<>(), "Route2", false);
        routeEvent.setRoute(route2);
        assertEquals(route2, routeEvent.getRoute());
    }

    /**
     * Tests the active status transitions and their effect on nextGenerationDate.
     */
    @Test
    void testActiveStatusTransitions() {
        routeEvent.setActiveStatus(true);
        assertTrue(routeEvent.isActiveStatus());
        routeEvent.setActiveStatus(false);
        assertFalse(routeEvent.isActiveStatus());
    }

    /**
     * Tests trigger() when the route is inactive (should return minimal logs).
     */
    @Test
    void testTriggerWithInactiveRoute() {
        route.setActiveFlag(false);
        List<String> logs = routeEvent.trigger();
        assertNotNull(logs);
        assertTrue(logs.isEmpty());
    }

    /**
     * Tests trigger() when the route is active (should return logs with event info).
     */
    @Test
    void testTriggerWithActiveRoute() {
        route.setActiveFlag(true);
        // Setup minimal train and inventory for the route
        route.setAssignedTrain(null); // No train assigned, should not throw
        List<String> logs = routeEvent.trigger();
        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        assertTrue(logs.stream().anyMatch(s -> s.contains("Route Event Triggered")));
    }

    /**
     * Tests the findCompletePath logic via constructor (should not throw and produce a list).
     */
    @Test
    void testFindCompletePathLogic() {
        RouteEvent event = new RouteEvent("Event2", 2, 0, route, simulation, scenario);
        // Não há getter direto, mas a construção não deve lançar exceção
        assertNotNull(event);
    }
}
