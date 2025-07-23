package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.AddStation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationNameController;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AddStationNameController.
 */
public class AddStationNameControllerTest {
    private AddStationNameController controller;
    private SimulationFake simulation;
    private MapFake map;
    private ScenarioFake scenario;

    /**
     * Fake implementation of Simulation for testing.
     */
    static class SimulationFake extends Simulation {
        private int actualMoney = 1000;
        private Scenario scenario;
        private List<Station> addedStations = new ArrayList<>();
        public SimulationFake() { super(null, null); }
        public void setScenario(Scenario scenario) { this.scenario = scenario; }
        @Override
        public Scenario getScenario() { return scenario; }
        @Override
        public int getActualMoney() { return actualMoney; }
        @Override
        public void setActualMoney(int money) { this.actualMoney = money; }
        public boolean addStation(Station station) {
            addedStations.add(station);
            return true;
        }
        public List<Station> getAddedStations() { return addedStations; }
    }
    /**
     * Fake implementation of Scenario for testing.
     */
    static class ScenarioFake extends Scenario {
        private Map map;
        public ScenarioFake(Map map) { super(map, "Test", 1000, null, null); this.map = map; }
        @Override
        public Map getMap() { return map; }
    }
    /**
     * Fake implementation of Map for testing.
     */
    static class MapFake extends Map {
        private final List<City> cities = new ArrayList<>();
        private final List<Station> stations = new ArrayList<>();
        private final int id = 1;
        public MapFake() { super("TestMap", null); }
        public void addCity(City city) { cities.add(city); }
        public void addStation(Station station) { stations.add(station); }
        @Override
        public List<City> getCitiesList() { return cities; }
        @Override
        public List<Station> getStationList() { return stations; }
        @Override
        public int getId() { return id; }
    }
    /**
     * Fake implementation of City for testing.
     */
    static class CityFake extends City {
        private final String name;
        private final Position position;
        public CityFake(String name, Position position) { super(name, position, new ArrayList<>(), new ArrayList<>()); this.name = name; this.position = position; }
        @Override
        public String getName() { return name; }
        @Override
        public Position getPosition() { return position; }
    }
    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new AddStationNameController();
        map = new MapFake();
        scenario = new ScenarioFake(map);
        simulation = new SimulationFake();
        simulation.setScenario(scenario);
        controller.setSimulation(simulation);
    }
    /**
     * Tests setting and getting the direction.
     */
    @Test
    void testSetAndGetDirection() {
        controller.setDirection("NORTH");
        assertEquals("NORTH", controller.getDirection());
        controller.setDirection(null);
        assertNull(controller.getDirection());
    }
    /**
     * Tests setting and getting the simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
    }
    /**
     * Tests setting the position using string values.
     */
    @Test
    void testSetPosition() {
        controller.setPosition("5", "7");
        // No getter, but covered by getAutoStationName and createStation
    }
    /**
     * Tests setting and getting the station type.
     */
    @Test
    void testSetAndGetStationType() {
        controller.setStationType(StationType.DEPOT);
        assertEquals(StationType.DEPOT, controller.getStationType());
        controller.setStationType(null);
        assertNull(controller.getStationType());
    }
    /**
     * Tests setting the station name.
     */
    @Test
    void testSetStationName() {
        controller.setStationName("MyStation");
        // No getter, but covered by createStation
    }
    /**
     * Tests getAutoStationName with no existing stations and a city nearby.
     */
    @Test
    void testGetAutoStationName_NoExistingStations() {
        CityFake city = new CityFake("Porto", new Position(5, 7));
        map.addCity(city);
        controller.setPosition("5", "7");
        controller.setStationType(StationType.DEPOT);
        String name = controller.getAutoStationName();
        assertEquals("depot_Porto", name);
    }
    /**
     * Tests getAutoStationName with existing stations of the same type and city.
     */
    @Test
    void testGetAutoStationName_WithExistingStations() {
        CityFake city = new CityFake("Lisbon", new Position(10, 10));
        map.addCity(city);
        controller.setPosition("10", "10");
        controller.setStationType(StationType.STATION);
        // Add an existing station with the same base name
        Station existing = new Station(StationType.STATION, new Position(10, 10), map.getId(), "NORTH", scenario);
        existing.setName("station_Lisbon");
        map.addStation(existing);
        String name = controller.getAutoStationName();
        assertEquals("station_Lisbon_1", name);
    }
    /**
     * Tests getAutoStationName when no city is close (should use UnknownCity).
     */
    @Test
    void testGetAutoStationName_NoCityNearby() {
        controller.setPosition("1", "1");
        controller.setStationType(StationType.TERMINAL);
        String name = controller.getAutoStationName();
        assertTrue(name.startsWith("terminal_UnknownCity"));
    }
    /**
     * Tests createStation returns true and updates money when station is created successfully.
     */
    @Test
    void testCreateStation_Success() {
        controller.setPosition("5", "7");
        controller.setStationType(StationType.DEPOT);
        controller.setStationName("depot_Porto");
        controller.setDirection("NORTH");
        int initialMoney = simulation.getActualMoney();
        boolean result = controller.createStation();
        assertTrue(result);
        assertEquals(initialMoney - StationType.DEPOT.getConstructionCost(), simulation.getActualMoney());
        assertEquals("depot_Porto", simulation.getAddedStations().get(0).getName());
    }
    /**
     * Tests createStation returns false if simulation is null.
     */
    @Test
    void testCreateStation_SimulationNull() {
        controller.setSimulation(null);
        controller.setPosition("5", "7");
        controller.setStationType(StationType.DEPOT);
        controller.setStationName("depot_Porto");
        controller.setDirection("NORTH");
        assertFalse(controller.createStation());
    }
    /**
     * Tests createStation returns false if map is null.
     */
    @Test
    void testCreateStation_MapNull() {
        scenario = new ScenarioFake(null);
        simulation.setScenario(scenario);
        controller.setPosition("5", "7");
        controller.setStationType(StationType.DEPOT);
        controller.setStationName("depot_Porto");
        controller.setDirection("NORTH");
        assertFalse(controller.createStation());
    }
}
