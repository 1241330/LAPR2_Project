package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.ManageRoute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.ConfirmRouteActivationPopController;
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
 * Unit tests for the ConfirmRouteActivationPopController class.
 */
public class ConfirmRouteActivationPopControllerTest {
    private ConfirmRouteActivationPopController controller;
    private Route route;
    private Simulation simulation;
    private Train train;

    @BeforeEach
    void setUp() {
        controller = new ConfirmRouteActivationPopController();
        simulation = new Simulation("simulacao", null);
        Locomotive loco = new Locomotive("Locomotiva1", "img.png", 1000.0, 2.0, 120.0, 2000, 5000, FuelType.DIESEL, 10, 100);
        TimeDate acquisitionDate = new TimeDate(2024, 1, 1);
        train = new Train(loco, new ArrayList<Carriage>(), acquisitionDate);
        route = new Route(new ArrayList<>(), new ArrayList<>(), "rota", false);
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
     * Tests the getter and setter for selectedTrain.
     */
    @Test
    void testGetAndSetSelectedTrain() {
        controller.setTrain(train);
        assertEquals(train, controller.getSelectedTrain());
    }

    /**
     * Tests activateRoute activates the route and train, and assigns the train to the route.
     */
    @Test
    void testActivateRoute() {
        controller.setRoute(route);
        controller.setTrain(train);
        assertFalse(route.getActiveFlag());
        assertFalse(train.getActiveFlag());
        assertNull(route.getAssignedTrain());
        controller.activateRoute();
        assertTrue(route.getActiveFlag());
        assertTrue(train.getActiveFlag());
        assertEquals(train, route.getAssignedTrain());
    }
}
