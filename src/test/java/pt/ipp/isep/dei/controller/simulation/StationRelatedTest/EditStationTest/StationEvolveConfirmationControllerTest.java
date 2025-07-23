package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.EditStationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.StationEvolveConfirmationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for StationEvolveConfirmationController.
 */
public class StationEvolveConfirmationControllerTest {
    private StationEvolveConfirmationController controller;
    private Simulation simulation;
    private Station station;

    /**
     * Minimal implementation of Station for testing.
     */
    static class StationFake extends Station {
        public StationFake() { super("TestStation"); }
    }

    /**
     * Minimal implementation of Simulation for testing.
     */
    static class SimulationFake extends Simulation {
        public SimulationFake() { super(null, null); }
    }

    @BeforeEach
    void setUp() {
        controller = new StationEvolveConfirmationController();
        simulation = new SimulationFake();
        station = new StationFake();
    }

    /**
     * Tests setting and getting the simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
    }

    /**
     * Tests setting and getting the station.
     */
    @Test
    void testSetAndGetStation() {
        controller.setStation(station);
        assertEquals(station, controller.getStation());
    }
}
