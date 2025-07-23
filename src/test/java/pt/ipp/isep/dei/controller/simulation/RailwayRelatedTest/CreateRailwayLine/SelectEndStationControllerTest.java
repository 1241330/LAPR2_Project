package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.CreateRailwayLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.SelectEndStationController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SelectEndStationController.
 */
public class SelectEndStationControllerTest {
    private SelectEndStationController controller;
    private Simulation simulation;
    private Station stationA;
    private Station stationB;
    private Station stationC;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new SelectEndStationController();
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
        controller.setBeginningStation(stationA);
    }

    /**
     * Tests getAvailableStations returns all except beginning station.
     */
    @Test
    void testGetAvailableStations_Normal() {
        List<Station> expected = new ArrayList<>();
        expected.add(stationB);
        expected.add(stationC);
        List<Station> available = controller.getAvailableStations();
        assertTrue(available.contains(stationB));
        assertTrue(available.contains(stationC));
        assertFalse(available.contains(stationA));
        assertEquals(2, available.size());
    }

    /**
     * Tests getAvailableStations when only beginning station exists.
     */
    @Test
    void testGetAvailableStations_OnlyBeginning() {
        Simulation sim = new Simulation("Test", null);
        Size size = new Size(10, 10);
        Map map = new Map("mapa", size);
        TimeDate begin = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2025, 1, 1);
        Scenario scenario = new Scenario(map, "sc", 1000, begin, end);
        Position pos = new Position(0, 0);
        Station s = new Station(StationType.STATION, pos, 0, "N", scenario);
        sim.getStations().add(s);
        SelectEndStationController c = new SelectEndStationController();
        c.setSimulation(sim);
        c.setBeginningStation(s);
        assertTrue(c.getAvailableStations().isEmpty());
    }

    /**
     * Tests getAvailableStations when stations list is empty.
     */
    @Test
    void testGetAvailableStations_EmptyList() {
        Simulation sim = new Simulation("Test", null);
        SelectEndStationController c = new SelectEndStationController();
        c.setSimulation(sim);
        c.setBeginningStation(null);
        assertTrue(c.getAvailableStations().isEmpty());
    }

    /**
     * Tests checkExistingLineBetweenStations returns true if line exists (A-B).
     */
    @Test
    void testCheckExistingLineBetweenStations_Exists_AB() {
        RailwayLine line = new RailwayLine(stationA, stationB, RailwayLineType.SINGLE_ELECTRIFIED);
        simulation.getMap().getRailwayLines().add(line);
        assertTrue(controller.checkExistingLineBetweenStations(stationB));
    }

    /**
     * Tests checkExistingLineBetweenStations returns true if line exists (B-A).
     */
    @Test
    void testCheckExistingLineBetweenStations_Exists_BA() {
        RailwayLine line = new RailwayLine(stationB, stationA, RailwayLineType.SINGLE_ELECTRIFIED);
        simulation.getMap().getRailwayLines().add(line);
        assertTrue(controller.checkExistingLineBetweenStations(stationB));
    }

    /**
     * Tests checkExistingLineBetweenStations returns false if no line exists.
     */
    @Test
    void testCheckExistingLineBetweenStations_NotExists() {
        assertFalse(controller.checkExistingLineBetweenStations(stationB));
    }

    /**
     * Tests checkExistingLineBetweenStations with empty railway lines list.
     */
    @Test
    void testCheckExistingLineBetweenStations_EmptyList() {
        simulation.getMap().getRailwayLines().clear();
        assertFalse(controller.checkExistingLineBetweenStations(stationB));
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        SelectEndStationController c = new SelectEndStationController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getter and setter for BeginningStation.
     */
    @Test
    void testSetAndGetBeginningStation() {
        SelectEndStationController c = new SelectEndStationController();
        assertNull(c.getBeginningStation());
        c.setBeginningStation(stationB);
        assertEquals(stationB, c.getBeginningStation());
    }
}
