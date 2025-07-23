package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.ManageRoute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.DeactivateRoutePOPController;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DeactivateRoutePOPController class.
 */
public class DesactivateRoutePOPControllerTest {
    private DeactivateRoutePOPController controller;
    private Route route;
    private Simulation simulation;
    private Train train;

    @BeforeEach
    void setUp() {
        controller = new DeactivateRoutePOPController();
        simulation = new Simulation("simulacao", null);
        Locomotive loco = new Locomotive("Locomotiva1", "img.png", 1000.0, 2.0, 120.0, 2000, 5000, FuelType.DIESEL, 10, 100);
        TimeDate acquisitionDate = new TimeDate(2024, 1, 1);
        train = new Train(loco, new ArrayList<Carriage>(), acquisitionDate);
        route = new Route(new ArrayList<>(), new ArrayList<>(), "rota", false);
        route.setAssignedTrain(train);
        route.setActiveFlag(true);
        train.setActiveFlag(true);
    }

    /**
     * Tests the getter and setter for route.
     */
    @Test
    void testGetAndSetRoute() {
        controller.setRoute(route);
        assertEquals(route, controller.getRoute());
    }

    /**
     * Tests the getter and setter for simulation.
     */
    @Test
    void testGetAndSetSimulation() {
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
    }

    /**
     * Tests deactivateRoute deactivates the route and train, and unassigns the train from the route.
     */
    @Test
    void testDeactivateRoute() {
        controller.setRoute(route);
        controller.deactivateRoute();
        assertFalse(route.getActiveFlag());
        assertFalse(train.getActiveFlag());
        assertNull(route.getAssignedTrain());
    }
}
