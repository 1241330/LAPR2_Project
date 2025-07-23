package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.CreateRoute;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.ChooseDepartureStationController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TypeOfCargoMode;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ChooseDepartureStationController} class.
 * <p>
 * Verifies correct handling of simulation setup, station and railway line filtering, cargo modes, and route point creation.
 */
public class ChooseDepartureStationControllerTest {
    private ChooseDepartureStationController controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private Station depot;
    private Station station;
    private Station terminal;
    private RailwayLine line1;
    private RailwayLine line2;
    private ResourcesType resourceType;

    /**
     * Sets up a simulation environment with stations, railway lines, and resources before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new ChooseDepartureStationController();
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
        resourceType = new ResourcesType("Water", 100, 1, 10);
        scenario.getTypeResourceList().add(resourceType);
        controller.setSimulation(simulation);
    }

    /**
     * Tests the getter and setter for the simulation.
     */
    @Test
    void testGetAndSetSimulation() {
        assertEquals(simulation, controller.getSimulation());
        Simulation sim2 = new Simulation("other", scenario);
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests setting and retrieving the railway type filter flag.
     */
    @Test
    void testGetAndSetRailwayTypeAvailableFlag() {
        controller.setRailwayTypeAvailableFlag(true);
        assertTrue(controller.getRailwayTypeAvailableFlag());
        assertTrue(controller.isRailwayTypeAvailableFlag());
        controller.setRailwayTypeAvailableFlag(false);
        assertFalse(controller.getRailwayTypeAvailableFlag());
        assertFalse(controller.isRailwayTypeAvailableFlag());
    }

    /**
     * Tests setting and getting the departure point.
     */
    @Test
    void testGetAndSetDeparturePoint() {
        PointOfRoute point = new PointOfRoute(new ArrayList<>(), depot, TypeOfCargoMode.FULL);
        controller.setDeparturePoint(point);
        assertEquals(point, controller.getDeparturePoint());
    }

    /**
     * Tests available stations when all railway lines are considered.
     */
    @Test
    void testGetAvailableStations_AllConnected() {
        controller.setRailwayTypeAvailableFlag(false);
        ObservableList<Station> available = controller.getAvailableStations();
        assertTrue(available.contains(depot));
        assertTrue(available.contains(station));
        assertTrue(available.contains(terminal));
    }

    /**
     * Tests available stations when only electric railway lines are allowed.
     */
    @Test
    void testGetAvailableStations_OnlyElectric() {
        controller.setRailwayTypeAvailableFlag(true);
        ObservableList<Station> available = controller.getAvailableStations();
        assertTrue(available.contains(depot));
        assertTrue(available.contains(station));
        assertFalse(available.contains(terminal));
    }

    /**
     * Tests retrieving all available railway lines.
     */
    @Test
    void testFindAvailableRailwayLines_All() {
        controller.setRailwayTypeAvailableFlag(false);
        List<RailwayLine> lines = controller.findAvailableRailwayLines();
        assertTrue(lines.contains(line1));
        assertTrue(lines.contains(line2));
    }

    /**
     * Tests retrieving only electric railway lines.
     */
    @Test
    void testFindAvailableRailwayLines_OnlyElectric() {
        controller.setRailwayTypeAvailableFlag(true);
        List<RailwayLine> lines = controller.findAvailableRailwayLines();
        assertTrue(lines.contains(line1));
        assertFalse(lines.contains(line2));
    }

    /**
     * Tests retrieval of available cargo mode types.
     */
    @Test
    void testGetCargoModeTypes() {
        ObservableList<TypeOfCargoMode> types = controller.getCargoModeTypes();
        assertTrue(types.contains(TypeOfCargoMode.FULL));
        assertTrue(types.contains(TypeOfCargoMode.HALF));
        assertTrue(types.contains(TypeOfCargoMode.AVAILABLE));
    }

    /**
     * Tests retrieval of resource types in the scenario.
     */
    @Test
    void testGetResourceType() {
        ObservableList<ResourcesType> types = controller.getResourceType();
        assertTrue(types.contains(resourceType));
    }

    /**
     * Tests creation of a PointOfRoute object and its correctness.
     */
    @Test
    void testCreatePointOfRoute() {
        List<ResourcesType> resources = new ArrayList<>();
        resources.add(resourceType);
        controller.createPointOfRoute(depot, TypeOfCargoMode.FULL, resources);
        PointOfRoute point = controller.getDeparturePoint();
        assertEquals(depot, point.getStation());
        assertEquals(TypeOfCargoMode.FULL, point.getCargoMode());
        assertEquals(resources, point.getCargoToPick());
    }

    /**
     * Tests getting the size (visual weight) of railway lines.
     */
    @Test
    void testGetRailwayLineSize() {
        assertEquals(2, controller.getRailwayLineSize(line1));
        assertEquals(4, controller.getRailwayLineSize(line2));
    }

    /**
     * Tests getting the color (as string) of railway lines based on their type.
     */
    @Test
    void testGetRailwayLineColor() {
        assertEquals("#d1882e", controller.getRailwayLineColor(line1));
        assertEquals("#635544", controller.getRailwayLineColor(line2));
    }

    /**
     * Tests scaled coordinates calculation of a station on a given map scale.
     */
    @Test
    void testFindScaledPositions() {
        double[] pos = controller.findScaledPositions(depot, 100, 100);
        assertEquals(2, pos.length);
    }

    /**
     * Tests color codes assigned to stations based on their type.
     */
    @Test
    void testGetStationColor() {
        assertEquals("#de5959", controller.getStationColor(depot));
        assertEquals("#c23232", controller.getStationColor(station));
        assertEquals("#a60518", controller.getStationColor(terminal));
    }

    /**
     * Tests the graphical size assigned to stations based on their type.
     */
    @Test
    void testGetStationSize() {
        assertEquals(12, controller.getStationSize(depot));
        assertEquals(15, controller.getStationSize(station));
        assertEquals(18, controller.getStationSize(terminal));
    }

    /**
     * Tests calculation of label distance from station center based on station size.
     */
    @Test
    void testGetLabelDistance() {
        assertEquals(21, controller.getLabelDistance(12));
        assertEquals(24, controller.getLabelDistance(15));
        assertEquals(27, controller.getLabelDistance(18));
    }
}
