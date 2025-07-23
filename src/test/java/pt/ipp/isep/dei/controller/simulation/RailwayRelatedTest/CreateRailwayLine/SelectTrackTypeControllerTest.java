package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.CreateRailwayLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.SelectTrackTypeController;
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
 * Unit tests for SelectTrackTypeController.
 * Covers all scenarios for 100% code coverage.
 */
public class SelectTrackTypeControllerTest {
    private SelectTrackTypeController controller;
    private Simulation simulation;
    private Station stationA;
    private Station stationB;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new SelectTrackTypeController();
        simulation = new Simulation("TestSim", null);
        Size size = new Size(10, 10);
        Map map = new Map("mapa", size);
        TimeDate begin = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2025, 1, 1);
        Scenario scenario = new Scenario(map, "sc", 1000, begin, end);
        Position posA = new Position(0, 0);
        Position posB = new Position(1, 0);
        stationA = new Station(StationType.STATION, posA, 0, "N", scenario);
        stationB = new Station(StationType.STATION, posB, 0, "S", scenario);
        simulation.getStations().clear();
        simulation.getStations().add(stationA);
        simulation.getStations().add(stationB);
        controller.setSimulation(simulation);
        controller.setBeginningStation(stationA);
        controller.setArrivalStation(stationB);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        SelectTrackTypeController c = new SelectTrackTypeController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getter and setter for BeginningStation.
     */
    @Test
    void testSetAndGetBeginningStation() {
        SelectTrackTypeController c = new SelectTrackTypeController();
        assertNull(c.getBeginningStation());
        c.setBeginningStation(stationA);
        assertEquals(stationA, c.getBeginningStation());
    }

    /**
     * Tests getter and setter for ArrivalStation.
     */
    @Test
    void testSetAndGetArrivalStation() {
        SelectTrackTypeController c = new SelectTrackTypeController();
        assertNull(c.getArrivalStation());
        c.setArrivalStation(stationB);
        assertEquals(stationB, c.getArrivalStation());
    }

    /**
     * Tests getRailwayTypes returns all enum values.
     */
    @Test
    void testGetRailwayTypes() {
        List<RailwayLineType> types = controller.getRailwayTypes();
        assertTrue(types.contains(RailwayLineType.SINGLE_ELECTRIFIED));
        assertTrue(types.contains(RailwayLineType.DOUBLE_ELECTRIFIED));
        assertTrue(types.contains(RailwayLineType.SINGLE_NON_ELECTRIFIED));
        assertTrue(types.contains(RailwayLineType.DOUBLE_NON_ELECTRIFIED));
        assertEquals(4, types.size());
    }

    /**
     * Tests the price getters for each track type.
     */
    @Test
    void testGetPrices() {
        assertEquals(RailwayLineType.SINGLE_NON_ELECTRIFIED.getCost(), controller.getSNEPrice());
        assertEquals(RailwayLineType.SINGLE_ELECTRIFIED.getCost(), controller.getSEPrice());
        assertEquals(RailwayLineType.DOUBLE_NON_ELECTRIFIED.getCost(), controller.getDNEPrice());
        assertEquals(RailwayLineType.DOUBLE_ELECTRIFIED.getCost(), controller.getDEPrice());
    }

    /**
     * Tests returnRailwayCost for all combinations of track type and path size.
     */
    @Test
    void testReturnRailwayCost_AllCombinations() {
        List<Position> path = new ArrayList<>();
        path.add(new Position(0, 0));
        path.add(new Position(1, 0));
        // Double, Electrified
        int costDE = controller.returnRailwayCost(path, true, true);
        assertEquals(RailwayLineType.DOUBLE_ELECTRIFIED.getCost() * 2, costDE);
        // Double, Not Electrified
        int costDNE = controller.returnRailwayCost(path, true, false);
        assertEquals(RailwayLineType.DOUBLE_NON_ELECTRIFIED.getCost() * 2, costDNE);
        // Single, Electrified
        int costSE = controller.returnRailwayCost(path, false, true);
        assertEquals(RailwayLineType.SINGLE_ELECTRIFIED.getCost() * 2, costSE);
        // Single, Not Electrified
        int costSNE = controller.returnRailwayCost(path, false, false);
        assertEquals(RailwayLineType.SINGLE_NON_ELECTRIFIED.getCost() * 2, costSNE);
    }

    /**
     * Tests returnRailwayCost with empty path.
     */
    @Test
    void testReturnRailwayCost_EmptyPath() {
        List<Position> path = new ArrayList<>();
        int cost = controller.returnRailwayCost(path, false, false);
        assertEquals(0, cost);
    }

    /**
     * Tests getPath returns a non-null list (basic scenario).
     */
    @Test
    void testGetPath_Basic() {
        List<Position> path = controller.getPath(RailwayLineType.SINGLE_ELECTRIFIED);
        assertNotNull(path);
    }
}
