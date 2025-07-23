package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.RouteRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RouteRelatedController class.
 */
public class RouteRelatedControllerTest {

    /**
     * Tests the default constructor and initial state.
     */
    @Test
    void testConstructorInitialState() {
        RouteRelatedController controller = new RouteRelatedController();
        assertNull(controller.getSimulation(), "Simulation should be null after construction");
    }

    /**
     * Tests setSimulation and getSimulation methods.
     */
    @Test
    void testSetAndGetSimulation() {
        RouteRelatedController controller = new RouteRelatedController();
        Simulation simulation = new Simulation("simulacao", null);
        assertNull(controller.getSimulation());
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
        // Test setting simulation to null
        controller.setSimulation(null);
        assertNull(controller.getSimulation());
    }
}
