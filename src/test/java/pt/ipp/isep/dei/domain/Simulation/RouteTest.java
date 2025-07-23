package pt.ipp.isep.dei.domain.Simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Route} class.
 */
class RouteTest {

    private Locomotive locomotive;
    private List<Carriage> carriages;
    private Train train;
    private List<Station> stations;
    private Route route;
    private List<PointOfRoute> pointOfRouteList;
    private List<RailwayLine> path;
    private String routeName;
    private boolean electricFlag;

    /**
     * Initializes test data before each test method.
     * Creates a sample locomotive, train, and route with two stations.
     */
    @BeforeEach
    void setUp() {
        // Dummy stations
        Station stationA = new Station("StationA");
        Station stationB = new Station("StationB");
        stations = new ArrayList<>();
        stations.add(stationA);
        stations.add(stationB);
        // Dummy resources and cargo mode
        List<ResourcesType> cargo = new ArrayList<>();
        cargo.add(new ResourcesType("Coal", 100, 1, 10));
        // PointOfRoute list
        pointOfRouteList = new ArrayList<>();
        pointOfRouteList.add(new PointOfRoute(cargo, stationA, TypeOfCargoMode.FULL));
        pointOfRouteList.add(new PointOfRoute(cargo, stationB, TypeOfCargoMode.HALF));
        // Dummy path
        path = new ArrayList<>();
        path.add(new RailwayLine(stationA, stationB, RailwayLineType.DOUBLE_NON_ELECTRIFIED));
        // Route name and electric flag
        routeName = "TestRoute";
        electricFlag = true;
        // Locomotive, carriages, train
        locomotive = new Locomotive("Locomotive1", "img.png", 2000, 1.5, 120, 1950, 50000, FuelType.DIESEL, 5, 1000);
        carriages = new ArrayList<>();
        carriages.add(new Carriage("Carriage1", "img.png", 1950, 10000, 10));
        carriages.add(new Carriage("Carriage2", "img.png", 1950, 12000, 15));
        train = new Train(locomotive, carriages, new TimeDate(2024, 6, 15));
        // Route
        route = new Route(pointOfRouteList, path, routeName, electricFlag);
        route.setAssignedTrain(train);
    }

    /**
     * Tests the constructor and getter methods of the {@link Route} class.
     * Verifies that all initial values are correctly assigned.
     */
    @Test
    void testConstructorAndGetters() {
        // Test assigned train
        assertEquals(train, route.getAssignedTrain());
        // Test departure station
        assertEquals(stations.get(0), route.getDepartureStation());
        // Test point of route list
        assertEquals(pointOfRouteList, route.getPointOfRouteList());
        // Test path
        assertEquals(path, route.getPath());
        // Test name
        assertEquals(routeName, route.getName());
        // Test electric flag
        assertTrue(route.getElectricFlag());
        // Test initial flags
        assertFalse(route.getActiveFlag());
        assertEquals(0, route.getPointPathPosition());
        assertFalse(route.getOldStatus());
    }

    /**
     * Tests the setter methods of the {@link Route} class.
     * Verifies that the route properties can be updated correctly.
     */
    @Test
    void testSetters() {
        // Test setAssignedTrain
        Locomotive newLocomotive = new Locomotive("Locomotive2", "img.png", 2500, 1.8, 150, 1960, 60000, FuelType.DIESEL, 6, 1200);
        List<Carriage> newCarriages = new ArrayList<>();
        newCarriages.add(new Carriage("Carriage3", "img.png", 1960, 15000, 20));
        Train newTrain = new Train(newLocomotive, newCarriages, new TimeDate(2025, 1, 1));
        route.setAssignedTrain(newTrain);
        assertEquals(newTrain, route.getAssignedTrain());
        // Test setName
        route.setName("CustomRoute");
        assertEquals("CustomRoute", route.getName());
        // Test setElectricFlag
        route.setElectricFlag(false);
        assertFalse(route.getElectricFlag());
        // Test setActiveFlag
        route.setActiveFlag(true);
        assertTrue(route.getActiveFlag());
        // Test setPointPathPosition
        route.setPointPathPosition(1);
        assertEquals(1, route.getPointPathPosition());
        // Test setOldStatus
        route.setOldStatus(true);
        assertTrue(route.getOldStatus());
    }

    /**
     * Tests the {@code toString()} method of the {@link Route} class.
     * Ensures the returned string contains the correct format and values.
     */
    @Test
    void testToString() {
        String expected = "Route: " + route.getName() + " | " +
                "Train: " + train.getName() + " | " +
                "Station: StationA -> StationB | Interval: 3 months.";
        assertEquals(expected, route.toString());
    }

    @Test
    void testNextPointPathPosition() {
        route.setPointPathPosition(0);
        route.nextPointPathPosition();
        assertEquals(1, route.getPointPathPosition());
        // Should wrap around
        route.nextPointPathPosition();
        assertEquals(0, route.getPointPathPosition());
    }

    @Test
    void testGetActiveFlagOnString() {
        route.setActiveFlag(true);
        assertEquals("Active", route.getActiveFlagOnString());
        route.setActiveFlag(false);
        assertEquals("Deactivated", route.getActiveFlagOnString());
    }
}
