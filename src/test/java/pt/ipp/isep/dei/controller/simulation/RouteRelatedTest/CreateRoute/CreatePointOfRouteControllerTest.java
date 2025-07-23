package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.CreateRoute;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.CreatePointOfRouteController;
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
 * Unit tests for the CreatePointOfRouteController class.
 */
public class CreatePointOfRouteControllerTest {
    private CreatePointOfRouteController controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private Station depot;
    private Station station;
    private Station terminal;
    private RailwayLine line1;
    private RailwayLine line2;
    private ResourcesType resourceType;

    @BeforeEach
    void setUp() {
        controller = new CreatePointOfRouteController();
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
     * Tests the getter and setter for the simulation property.
     */
    @Test
    void testGetAndSetSimulation() {
        assertEquals(simulation, controller.getSimulation());
        Simulation sim2 = new Simulation("other", scenario);
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests the getter and setter for the departure station property.
     */
    @Test
    void testGetAndSetDepartureStation() {
        controller.setDepartureStation(depot);
        assertEquals(depot, controller.getDepartureStation());
    }

    /**
     * Tests the getter and setter for the pointOfRouteList property.
     */
    @Test
    void testGetAndSetPointOfRouteList() {
        List<PointOfRoute> list = new ArrayList<>();
        PointOfRoute p = new PointOfRoute(new ArrayList<>(), depot, TypeOfCargoMode.FULL);
        list.add(p);
        controller.setPointOfRouteList(list);
        assertEquals(list, controller.getPointOfRouteList());
    }

    /**
     * Tests the getter and setter for the railwayTypeAvailableFlag property.
     */
    @Test
    void testGetAndSetRailwayTypeAvailableFlag() {
        controller.setRailwayTypeAvailableFlag(true);
        assertTrue(controller.getIsRailwayTypeAvailableFlag());
        assertTrue(controller.isRailwayTypeAvailableFlag());
        controller.setRailwayTypeAvailableFlag(false);
        assertFalse(controller.getIsRailwayTypeAvailableFlag());
        assertFalse(controller.isRailwayTypeAvailableFlag());
    }

    /**
     * Tests the getter and setter for the stationPath property.
     */
    @Test
    void testGetAndSetStationPath() {
        List<Station> path = new ArrayList<>();
        path.add(depot);
        controller.setStationPath(path);
        assertEquals(path, controller.getStationPath());
    }

    /**
     * Tests the addPointOfRoute method.
     */
    @Test
    void testAddPointOfRoute() {
        PointOfRoute p = new PointOfRoute(new ArrayList<>(), depot, TypeOfCargoMode.FULL);
        controller.addPointOfRoute(p);
        assertTrue(controller.getPointOfRouteList().contains(p));
    }

    /**
     * Tests the setter and getter for the departure point.
     */
    @Test
    void testSetAndGetDeparturePoint() {
        PointOfRoute p = new PointOfRoute(new ArrayList<>(), depot, TypeOfCargoMode.FULL);
        controller.setDeparturePoint(p);
        assertEquals(p, controller.getDeparturePoint());
    }

    /**
     * Tests the getCargoModeTypes method.
     */
    @Test
    void testGetCargoModeTypes() {
        ObservableList<TypeOfCargoMode> types = controller.getCargoModeTypes();
        assertTrue(types.contains(TypeOfCargoMode.FULL));
        assertTrue(types.contains(TypeOfCargoMode.HALF));
        assertTrue(types.contains(TypeOfCargoMode.AVAILABLE));
    }

    /**
     * Tests the getAvailableStations method with and without a station to remove.
     */
    @Test
    void testGetAvailableStations() {
        controller.setRailwayTypeAvailableFlag(false);
        ObservableList<Station> available = controller.getAvailableStations(null);
        assertTrue(available.contains(depot));
        assertTrue(available.contains(station));
        assertTrue(available.contains(terminal));
        ObservableList<Station> available2 = controller.getAvailableStations(depot);
        assertFalse(available2.contains(depot));
    }

    /**
     * Tests the getResourceType method.
     */
    @Test
    void testGetResourceType() {
        ObservableList<ResourcesType> types = controller.getResourceType();
        assertTrue(types.contains(resourceType));
    }

    /**
     * Tests the findDjikstraPath method.
     */
    @Test
    void testFindDjikstraPath() {
        PointOfRoute p = new PointOfRoute(new ArrayList<>(), depot, TypeOfCargoMode.FULL);
        controller.setDeparturePoint(p);
        List<Station> path = controller.findDjikstraPath(terminal);
        assertNotNull(path);
    }

    /**
     * Tests the findUsedRailwaysInPath method for valid, null, and empty paths.
     */
    @Test
    void testFindUsedRailwaysInPath() {
        List<Station> path = new ArrayList<>();
        path.add(depot);
        path.add(station);
        List<RailwayLine> used = controller.findUsedRailwaysInPath(path);
        assertTrue(used.contains(line1));
        assertNull(controller.findUsedRailwaysInPath(null));
        assertNull(controller.findUsedRailwaysInPath(new ArrayList<>()));
    }

    /**
     * Tests the findAvailableRailwayLines method for all and only electric lines.
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

    /**
     * Tests the getRailwayLineSize method for different types of lines.
     */
    @Test
    void testGetRailwayLineSize() {
        assertEquals(2, controller.getRailwayLineSize(line1));
        assertEquals(4, controller.getRailwayLineSize(line2));
    }

    /**
     * Tests the getRailwayLineColor method for different types of lines.
     */
    @Test
    void testGetRailwayLineColor() {
        assertEquals("#d1882e", controller.getRailwayLineColor(line1));
        assertEquals("#635544", controller.getRailwayLineColor(line2));
    }

    /**
     * Tests the findScaledPositions method.
     */
    @Test
    void testFindScaledPositions() {
        double[] pos = controller.findScaledPositions(depot, 100, 100);
        assertEquals(2, pos.length);
    }

    /**
     * Tests the getStationColor method for all station types.
     */
    @Test
    void testGetStationColor() {
        assertEquals("#de5959", controller.getStationColor(depot));
        assertEquals("#c23232", controller.getStationColor(station));
        assertEquals("#a60518", controller.getStationColor(terminal));
    }

    /**
     * Tests the getStationSize method for all station types.
     */
    @Test
    void testGetStationSize() {
        assertEquals(12, controller.getStationSize(depot));
        assertEquals(15, controller.getStationSize(station));
        assertEquals(18, controller.getStationSize(terminal));
    }

    /**
     * Tests the getLabelDistance method for different station sizes.
     */
    @Test
    void testGetLabelDistance() {
        assertEquals(21, controller.getLabelDistance(12));
        assertEquals(24, controller.getLabelDistance(15));
        assertEquals(27, controller.getLabelDistance(18));
    }

    /**
     * Tests the addPathPositions method.
     */
    @Test
    void testAddPathPositions() {
        List<Station> path = new ArrayList<>();
        path.add(station);
        path.add(terminal);
        controller.addPathPositions(path);
        assertTrue(controller.getStationPath().contains(terminal));
    }

    /**
     * Tests the setter and getter for the Djikstra path.
     */
    @Test
    void testSetAndGetDjikstraPath() {
        List<Station> path = new ArrayList<>();
        path.add(depot);
        controller.setDjikstraPath(path);
        assertEquals(path, controller.getDjikstraPath());
    }

    /**
     * Tests the getRailwayLineBetween method for direct and non-direct connections.
     */
    @Test
    void testGetRailwayLineBetween() {
        assertEquals(line1, controller.getRailwayLineBetween(depot, station));
        assertEquals(line1, controller.getRailwayLineBetween(station, depot));
        assertNull(controller.getRailwayLineBetween(depot, terminal));
    }
}
