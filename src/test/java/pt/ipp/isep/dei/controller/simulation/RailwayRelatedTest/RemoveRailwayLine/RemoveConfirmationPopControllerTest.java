package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.RemoveRailwayLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.RemoveRailwayLine.RemoveConfirmationPopController;
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
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RemoveConfirmationPopController.
 */
public class RemoveConfirmationPopControllerTest {
    private RemoveConfirmationPopController controller;
    private Simulation simulation;
    private Station stationA;
    private Station stationB;
    private RailwayLine line;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new RemoveConfirmationPopController();
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
        line = new RailwayLine(stationA, stationB, RailwayLineType.SINGLE_ELECTRIFIED);
        simulation.getMap().getRailwayLines().add(line);
        controller.setSimulation(simulation);
        controller.setRailwayLine(line);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        RemoveConfirmationPopController c = new RemoveConfirmationPopController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getter and setter for RailwayLine.
     */
    @Test
    void testSetAndGetRailwayLine() {
        RemoveConfirmationPopController c = new RemoveConfirmationPopController();
        assertNull(c.getRailwayLine());
        c.setRailwayLine(line);
        assertEquals(line, c.getRailwayLine());
    }

    /**
     * Tests removeRailwayLine removes the line from the simulation.
     */
    @Test
    void testRemoveRailwayLine() {
        assertTrue(simulation.getMap().getRailwayLines().contains(line));
        controller.removeRailwayLine();
        assertFalse(simulation.getMap().getRailwayLines().contains(line));
    }
}
