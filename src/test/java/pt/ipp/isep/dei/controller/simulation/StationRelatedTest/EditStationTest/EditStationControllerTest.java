package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.EditStationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EditStationController.
 */
public class EditStationControllerTest {
    private EditStationController controller;
    private Simulation simulation;
    private Station stationTerminal;
    private Station stationDepot;
    private Station stationStation;

    /**
     * Minimal implementation of Station for testing.
     */
    static class StationTest extends Station {
        private final String typeString;
        public StationTest(StationType type) {
            super(type, null, 1, "NORTH", null);
            this.typeString = type.toString();
        }
        @Override
        public String getStationType() {
            return typeString;
        }
    }

    @BeforeEach
    void setUp() {
        controller = new EditStationController();
        simulation = new Simulation(null, null);
        stationTerminal = new StationTest(StationType.TERMINAL);
        stationDepot = new StationTest(StationType.DEPOT);
        stationStation = new StationTest(StationType.STATION);
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
        controller.setStation(stationDepot);
        assertEquals(stationDepot, controller.getStation());
    }

    /**
     * Tests verifyIfStationIsMaxLevel returns true for TERMINAL type.
     */
    @Test
    void testVerifyIfStationIsMaxLevel_Terminal() {
        controller.setStation(stationTerminal);
        assertTrue(controller.verifyIfStationIsMaxLevel());
    }

    /**
     * Tests verifyIfStationIsMaxLevel returns false for DEPOT type.
     */
    @Test
    void testVerifyIfStationIsMaxLevel_Depot() {
        controller.setStation(stationDepot);
        assertFalse(controller.verifyIfStationIsMaxLevel());
    }

    /**
     * Tests verifyIfStationIsMaxLevel returns false for STATION type.
     */
    @Test
    void testVerifyIfStationIsMaxLevel_Station() {
        controller.setStation(stationStation);
        assertFalse(controller.verifyIfStationIsMaxLevel());
    }
}
