package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.ShowRailwayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.ShowRailwayList.ViewDetailsRailwayController;
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
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ViewDetailsRailwayController.
 */
public class ViewDetailsRailwayControllerTest {
    private ViewDetailsRailwayController controller;
    private Simulation simulation;
    private Station stationA;
    private Station stationB;
    private RailwayLine line;
    private List<Position> path;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new ViewDetailsRailwayController();
        Size size = new Size(10, 10);
        Map map = new Map("mapa", size);
        // Registar o mapa no repositório para evitar actualMap null
        MapRepository mapRepository = Repositories.getInstance().getMapRepository();
        mapRepository.addMap(map);
        int mapId = map.getId();
        TimeDate begin = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2025, 1, 1);
        Scenario scenario = new Scenario(map, "sc", 1000, begin, end);
        simulation = new Simulation("TestSim", scenario);
        Position posA = new Position(0, 0);
        Position posB = new Position(1, 0);
        stationA = new Station(StationType.STATION, posA, mapId, "N", scenario);
        stationB = new Station(StationType.STATION, posB, mapId, "S", scenario);
        simulation.getStations().clear();
        simulation.getStations().add(stationA);
        simulation.getStations().add(stationB);
        path = new ArrayList<>();
        path.add(posA);
        path.add(posB);
        line = new RailwayLine(path, stationA, stationB, RailwayLineType.SINGLE_ELECTRIFIED, begin);
        controller.setSimulation(simulation);
        controller.setRailwayLine(line);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        ViewDetailsRailwayController c = new ViewDetailsRailwayController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getter and setter for RailwayLine.
     */
    @Test
    void testSetAndGetRailwayLine() {
        ViewDetailsRailwayController c = new ViewDetailsRailwayController();
        assertNull(c.getRailwayLine());
        c.setRailwayLine(line);
        assertEquals(line, c.getRailwayLine());
    }

    /**
     * Tests getDepartureStation returns the correct station name.
     */
    @Test
    void testGetDepartureStation() {
        assertEquals(stationA.getName(), controller.getDepartureStation());
    }

    /**
     * Tests getArrivalStation returns the correct station name.
     */
    @Test
    void testGetArrivalStation() {
        assertEquals(stationB.getName(), controller.getArrivalStation());
    }

    /**
     * Tests getRailwayType returns the correct type name.
     */
    @Test
    void testGetRailwayType() {
        assertEquals(RailwayLineType.SINGLE_ELECTRIFIED.name(), controller.getRailwayType());
    }

    /**
     * Tests getRailwaySize returns the correct size as string.
     */
    @Test
    void testGetRailwaySize() {
        assertEquals(String.valueOf(path.size()), controller.getRailwaySize());
    }
}
