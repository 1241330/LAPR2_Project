package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.CreateRoute;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.ChooseRailwayTypeForRouteController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ChooseRailwayTypeForRouteController class.
 */
public class ChooseRailwayTypeForRouteControllerTest {

    /**
     * Test the default constructor and initial state.
     */
    @Test
    void testConstructorInitialState() {
        ChooseRailwayTypeForRouteController controller = new ChooseRailwayTypeForRouteController();
        assertNull(controller.getSimulation(), "Simulation should be null after construction");
    }

    /**
     * Test setSimulation and getSimulation methods.
     */
    @Test
    void testSetAndGetSimulation() {
        ChooseRailwayTypeForRouteController controller = new ChooseRailwayTypeForRouteController();
        Scenario scenario = new Scenario(null, "cenario", 1000, null, null);
        Simulation simulation = new Simulation("simulacao", scenario);
        assertNull(controller.getSimulation());
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
        // Test setting simulation to null
        controller.setSimulation(null);
        assertNull(controller.getSimulation());
    }
}
