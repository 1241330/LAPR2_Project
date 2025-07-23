package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.ManageRoute;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.ChooseRouteController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ChooseRouteController class.
 */
public class ChooseRouteControllerTest {
    private ChooseRouteController controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private Station depot;
    private Station station;
    private Station terminal;
    private RailwayLine line1;
    private RailwayLine line2;
    private Route route1;
    private Route route2;

    @BeforeEach
    void setUp() {
        controller = new ChooseRouteController();
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
        List<pt.ipp.isep.dei.domain.Simulation.PointOfRoute> points = new ArrayList<>();
        List<RailwayLine> path = new ArrayList<>();
        route1 = new Route(points, path, "RotaA", true);
        route2 = new Route(points, path, "RotaB", false);
        simulation.getRoutes().add(route1);
        simulation.getRoutes().add(route2);
        controller.setSimulation(simulation);
    }

    /**
     * Tests the getter and setter for the simulation property.
     */
    @Test
    void testGetAndSetSimulation() {
        assertEquals(simulation, controller.getSimulation());
        Simulation sim2 = new Simulation("simulacaoTeste", scenario);
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests getRoutes returns all routes in the simulation.
     */
    @Test
    void testGetRoutes() {
        ObservableList<Route> routes = controller.getRoutes();
        assertTrue(routes.contains(route1));
        assertTrue(routes.contains(route2));
    }

    /**
     * Tests getAssignedTrain returns the route name.
     */
    @Test
    void testGetAssignedTrain() {
        assertEquals("RotaA", controller.getAssignedTrain(route1));
        assertEquals("RotaB", controller.getAssignedTrain(route2));
    }

    /**
     * Tests getRailwayLineSize for all types of railway lines.
     */
    @Test
    void testGetRailwayLineSize() {
        assertEquals(2, controller.getRailwayLineSize(line1));
        assertEquals(4, controller.getRailwayLineSize(line2));
    }

    /**
     * Tests getRailwayLineColor for all types of railway lines.
     */
    @Test
    void testGetRailwayLineColor() {
        assertEquals("#d1882e", controller.getRailwayLineColor(line1));
        assertEquals("#635544", controller.getRailwayLineColor(line2));
    }

    /**
     * Tests findScaledPositions returns a double array of length 2.
     */
    @Test
    void testFindScaledPositions() {
        double[] positions = controller.findScaledPositions(depot, 100, 100);
        assertEquals(2, positions.length);
    }

    /**
     * Tests getStationColor for all station types.
     */
    @Test
    void testGetStationColor() {
        assertEquals("#de5959", controller.getStationColor(depot));
        assertEquals("#c23232", controller.getStationColor(station));
        assertEquals("#a60518", controller.getStationColor(terminal));
    }

    /**
     * Tests getStationSize for all station types.
     */
    @Test
    void testGetStationSize() {
        assertEquals(12, controller.getStationSize(depot));
        assertEquals(15, controller.getStationSize(station));
        assertEquals(18, controller.getStationSize(terminal));
    }

    /**
     * Tests getLabelDistance for different station sizes.
     */
    @Test
    void testGetLabelDistance() {
        assertEquals(21, controller.getLabelDistance(12));
        assertEquals(24, controller.getLabelDistance(15));
        assertEquals(27, controller.getLabelDistance(18));
    }
}
