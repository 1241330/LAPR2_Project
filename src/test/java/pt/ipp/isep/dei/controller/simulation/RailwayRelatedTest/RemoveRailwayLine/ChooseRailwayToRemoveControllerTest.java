package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.RemoveRailwayLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.RemoveRailwayLine.ChooseRailwayToRemoveController;
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
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ChooseRailwayToRemoveController.
 */
public class ChooseRailwayToRemoveControllerTest {
    private ChooseRailwayToRemoveController controller;
    private Simulation simulation;
    private Station stationA;
    private Station stationB;
    private RailwayLine line;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new ChooseRailwayToRemoveController();
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
        controller.setSimulation(simulation);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        ChooseRailwayToRemoveController c = new ChooseRailwayToRemoveController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getRailwayList returns all railway lines in the simulation.
     */
    @Test
    void testGetRailwayList_Normal() {
        simulation.getMap().getRailwayLines().add(line);
        List<RailwayLine> list = controller.getRailwayList();
        assertEquals(1, list.size());
        assertTrue(list.contains(line));
    }

    /**
     * Tests getRailwayList when there are no railway lines.
     */
    @Test
    void testGetRailwayList_Empty() {
        simulation.getMap().getRailwayLines().clear();
        List<RailwayLine> list = controller.getRailwayList();
        assertTrue(list.isEmpty());
    }
}
