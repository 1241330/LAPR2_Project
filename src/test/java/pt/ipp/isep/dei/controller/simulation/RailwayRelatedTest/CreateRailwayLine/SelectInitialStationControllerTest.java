package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.CreateRailwayLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.SelectInitialStationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SelectInitialStationController.
 */
public class SelectInitialStationControllerTest {
    private SelectInitialStationController controller;
    private Simulation simulation;
    private Station stationA;
    private Station stationB;
    private Station stationC;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new SelectInitialStationController();
        simulation = new Simulation("TestSim", null);
        Size size = new Size(10, 10);
        Map map = new Map("mapa", size);
        TimeDate begin = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2025, 1, 1);
        Scenario scenario = new Scenario(map, "sc", 1000, begin, end);
        Position posA = new Position(0, 0);
        Position posB = new Position(1, 0);
        Position posC = new Position(2, 0);
        stationA = new Station(StationType.STATION, posA, 0, "N", scenario);
        stationB = new Station(StationType.STATION, posB, 0, "S", scenario);
        stationC = new Station(StationType.STATION, posC, 0, "E", scenario);
        simulation.getStations().clear();
        simulation.getStations().add(stationA);
        simulation.getStations().add(stationB);
        simulation.getStations().add(stationC);
        controller.setSimulation(simulation);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        SelectInitialStationController c = new SelectInitialStationController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getAvailableStations returns all stations in the simulation.
     */
    @Test
    void testGetAvailableStations_Normal() {
        List<Station> available = controller.getAvailableStations();
        assertTrue(available.contains(stationA));
        assertTrue(available.contains(stationB));
        assertTrue(available.contains(stationC));
        assertEquals(3, available.size());
    }

    /**
     * Tests getAvailableStations when stations list is empty.
     */
    @Test
    void testGetAvailableStations_EmptyList() {
        Simulation sim = new Simulation("Test", null);
        SelectInitialStationController c = new SelectInitialStationController();
        c.setSimulation(sim);
        assertTrue(c.getAvailableStations().isEmpty());
    }
}
