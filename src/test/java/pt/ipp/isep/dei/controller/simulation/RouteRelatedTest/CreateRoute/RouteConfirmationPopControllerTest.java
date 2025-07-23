package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.CreateRoute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.RouteCreationConfirmationPopController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RouteCreationConfirmationPopController class.
 */
public class RouteConfirmationPopControllerTest {
    private RouteCreationConfirmationPopController controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private Station depot;
    private Station station;
    private Station terminal;
    private RailwayLine line1;
    private RailwayLine line2;
    private List<PointOfRoute> pointOfRouteList;
    private List<Station> stationPath;

    @BeforeEach
    void setUp() {
        controller = new RouteCreationConfirmationPopController();
        map = new Map("mapa", new Size(50, 50));
        scenario = new Scenario(map, "cenario", 1000, null, null);
        simulation = new Simulation("simulacao", scenario);
        depot = new Station(StationType.DEPOT, new Position(1, 1), map.getId(), null, scenario);
        station = new Station(StationType.STATION, new Position(2, 2), map.getId(), "NORTH", scenario);
        terminal = new Station(StationType.TERMINAL, new Position(3, 3), map.getId(), null, scenario);
        simulation.getStations().add(depot);
        simulation.getStations().add(station);
        simulation.getStations().add(terminal);
        map.getStationList().add(depot);
        map.getStationList().add(station);
        map.getStationList().add(terminal);
        line1 = new RailwayLine(depot, station, RailwayLineType.SINGLE_ELECTRIFIED);
        line2 = new RailwayLine(station, terminal, RailwayLineType.DOUBLE_NON_ELECTRIFIED);
        simulation.getRailwayLines().add(line1);
        simulation.getRailwayLines().add(line2);
        map.getRailwayLines().add(line1);
        map.getRailwayLines().add(line2);
        pointOfRouteList = new ArrayList<>();
        pointOfRouteList.add(new PointOfRoute(new ArrayList<>(), depot, null));
        pointOfRouteList.add(new PointOfRoute(new ArrayList<>(), station, null));
        stationPath = new ArrayList<>();
        stationPath.add(depot);
        stationPath.add(station);
        stationPath.add(terminal);
        controller.setSimulation(simulation);
        controller.setPointOfRouteList(pointOfRouteList);
        controller.setStationPath(stationPath);
    }

    /**
     * Tests the getter and setter for pointOfRouteList.
     */
    @Test
    void testGetAndSetPointOfRouteList() {
        List<PointOfRoute> list = new ArrayList<>();
        controller.setPointOfRouteList(list);
        assertEquals(list, controller.getPointOfRouteList());
    }

    /**
     * Tests the getter and setter for stationPath.
     */
    @Test
    void testGetAndSetStationPath() {
        List<Station> path = new ArrayList<>();
        controller.setStationPath(path);
        assertEquals(path, controller.getStationPath());
    }

    /**
     * Tests the getter and setter for simulation.
     */
    @Test
    void testGetAndSetSimulation() {
        Simulation sim2 = new Simulation("other", scenario);
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests the getter and setter for railwayTypeAvailableFlag.
     */
    @Test
    void testGetAndSetRailwayTypeAvailableFlag() {
        controller.setRailwayTypeAvailableFlag(true);
        assertTrue(controller.isRailwayTypeAvailableFlag(true));
        controller.setRailwayTypeAvailableFlag(false);
        assertFalse(controller.isRailwayTypeAvailableFlag(false));
    }

    /**
     * Tests createRoute with valid data.
     */
    @Test
    void testCreateRoute() {
        int initialRoutes = simulation.getRoutes().size();
        controller.createRoute();
        assertEquals(initialRoutes + 1, simulation.getRoutes().size());
        Route created = simulation.getRoutes().getLast();
        assertEquals("route_1", created.getName());
        assertEquals(pointOfRouteList, created.getPointOfRouteList());
    }

    /**
     * Tests getRailwayLineBetween for direct, reverse, and not found cases.
     */
    @Test
    void testGetRailwayLineBetween() {
        assertEquals(line1, controller.getRailwayLineBetween(depot, station));
        assertEquals(line1, controller.getRailwayLineBetween(station, depot));
        assertNull(controller.getRailwayLineBetween(depot, terminal));
    }

    /**
     * Tests findAvailableRailwayLines for all and only electric lines.
     */
    @Test
    void testFindAvailableRailwayLines() {
        controller.setRailwayTypeAvailableFlag(false);
        List<RailwayLine> all = controller.findAvailableRailwayLines();
        assertTrue(all.contains(line1));
        assertTrue(all.contains(line2));
        controller.setRailwayTypeAvailableFlag(true);
        List<RailwayLine> onlyElectric = controller.findAvailableRailwayLines();
        assertTrue(onlyElectric.contains(line1));
        assertFalse(onlyElectric.contains(line2));
    }
}
