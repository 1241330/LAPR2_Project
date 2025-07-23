package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.AddStation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationPositionController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Station.StationType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AddStationPositionController.
 */
public class AddStationPositionControllerTest {
    private AddStationPositionController controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private Size size;

    /**
     * Minimal implementation of Size for testing.
     */
    static class SizeTest extends Size {
        private final int width;
        private final int height;
        public SizeTest(int width, int height) { super(width, height); this.width = width; this.height = height; }
        @Override public int getWidth() { return width; }
        @Override public int getHeight() { return height; }
    }

    /**
     * Minimal implementation of Map for testing.
     */
    static class MapTest extends Map {
        private final Size size;
        public MapTest(Size size) { super("TestMap", size); this.size = size; }
        @Override public Size getPixelSize() { return size; }
    }

    /**
     * Minimal implementation of Scenario for testing.
     */
    static class ScenarioTest extends Scenario {
        private final Map map;
        public ScenarioTest(Map map) { super(map, "Test", 1000, null, null); this.map = map; }
        @Override public Map getMap() { return map; }
    }

    /**
     * Minimal implementation of Simulation for testing.
     */
    static class SimulationTest extends Simulation {
        private Scenario scenario;
        public SimulationTest(Scenario scenario) { super(null, null); this.scenario = scenario; }
        @Override public Scenario getScenario() { return scenario; }
        public void setScenario(Scenario scenario) { this.scenario = scenario; }
    }

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        size = new SizeTest(20, 30);
        map = new MapTest(size);
        scenario = new ScenarioTest(map);
        simulation = new SimulationTest(scenario);
        controller = new AddStationPositionController();
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
     * Tests verifyValues with valid values inside the map bounds.
     */
    @Test
    void testVerifyValues_Valid() {
        String result = controller.verifyValues("10", "20");
        assertEquals("no problem", result);
    }

    /**
     * Tests verifyValues with negative values.
     */
    @Test
    void testVerifyValues_Negative() {
        String result = controller.verifyValues("-1", "5");
        assertEquals("Every value must be Positive!!", result);
        result = controller.verifyValues("5", "-1");
        assertEquals("Every value must be Positive!!", result);
    }

    /**
     * Tests verifyValues with values out of map bounds.
     */
    @Test
    void testVerifyValues_OutOfBounds() {
        String result = controller.verifyValues("21", "10");
        assertEquals("Values out of Bound for the Map!! x: 20 y: 30", result);
        result = controller.verifyValues("10", "31");
        assertEquals("Values out of Bound for the Map!! x: 20 y: 30", result);
    }

    /**
     * Tests verifyValues with non-numeric input.
     */
    @Test
    void testVerifyValues_NonNumeric() {
        String result = controller.verifyValues("abc", "10");
        assertEquals("Insert only Numbers!!", result);
        result = controller.verifyValues("10", "xyz");
        assertEquals("Insert only Numbers!!", result);
    }
}
