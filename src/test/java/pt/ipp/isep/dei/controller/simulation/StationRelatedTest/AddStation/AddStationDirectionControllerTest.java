package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.AddStation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationDirectionController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AddStationDirectionController.
 */
public class AddStationDirectionControllerTest {
    /**
     * The controller under test.
     */
    private AddStationDirectionController controller;
    /**
     * A simple Simulation implementation for testing purposes.
     */
    static class SimulationFake extends Simulation {
        public SimulationFake() { super(null, null); }
    }
    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new AddStationDirectionController();
    }
    /**
     * Tests setting and getting the simulation in the controller.
     */
    @Test
    void testGetAndSetSimulation() {
        SimulationFake sim = new SimulationFake();
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }
    /**
     * Tests setting and getting the direction in the controller.
     */
    @Test
    void testGetAndSetDirection() {
        controller.setDirection("NORTH");
        assertEquals("NORTH", controller.getDirection());
        controller.setDirection("SOUTH");
        assertEquals("SOUTH", controller.getDirection());
        controller.setDirection("");
        assertEquals("", controller.getDirection());
        controller.setDirection(null);
        assertNull(controller.getDirection());
    }
    /**
     * Tests setting and getting the station type in the controller.
     */
    @Test
    void testGetAndSetStationType() {
        controller.setStationType(StationType.DEPOT);
        assertEquals(StationType.DEPOT, controller.getStationType());
        controller.setStationType(StationType.STATION);
        assertEquals(StationType.STATION, controller.getStationType());
        controller.setStationType(StationType.TERMINAL);
        assertEquals(StationType.TERMINAL, controller.getStationType());
        controller.setStationType(null);
        assertNull(controller.getStationType());
    }
}
