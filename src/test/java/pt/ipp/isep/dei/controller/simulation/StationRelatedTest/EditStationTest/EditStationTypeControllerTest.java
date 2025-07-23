package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.EditStationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationTypeController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EditStationTypeController.
 */
public class EditStationTypeControllerTest {
    private EditStationTypeController controller;
    private SimulationFake simulation;
    private StationFake station;

    /**
     * Minimal implementation of Station for testing.
     */
    static class StationFake extends Station {
        private String type = StationType.DEPOT.toString();
        private int constructionCost = StationType.DEPOT.getConstructionCost();
        private String direction;
        public StationFake() { super(StationType.DEPOT, null, 1, "NORTH", null); }
        @Override
        public String getStationType() { return type; }
        @Override
        public void setStationType(String type) { this.type = type; }
        @Override
        public int getConstructionCost() { return constructionCost; }
        public void setConstructionCost(int cost) { this.constructionCost = cost; }
        @Override
        public void setDirection(String direction) { this.direction = direction; }
        @Override
        public String getDirection() { return direction; }
    }

    /**
     * Minimal implementation of Simulation for testing.
     */
    static class SimulationFake extends Simulation {
        private int money = 1000;
        public SimulationFake() { super(null, null); }
        @Override
        public int getActualMoney() { return money; }
        @Override
        public void setActualMoney(int money) { this.money = money; }
    }

    @BeforeEach
    void setUp() {
        controller = new EditStationTypeController();
        simulation = new SimulationFake();
        station = new StationFake();
        controller.setSimulation(simulation);
        controller.setStation(station);
    }

    /**
     * Tests get/set simulation.
     */
    @Test
    void testGetAndSetSimulation() {
        SimulationFake sim2 = new SimulationFake();
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests get/set station.
     */
    @Test
    void testGetAndSetStation() {
        StationFake s2 = new StationFake();
        controller.setStation(s2);
        assertEquals(s2, controller.getStation());
    }

    /**
     * Tests get/set stationType.
     */
    @Test
    void testGetAndSetStationType() {
        controller.setStationType("TERMINAL");
        assertEquals("TERMINAL", controller.getStationType());
    }

    /**
     * Tests stationIsOfTypeStation returns true if type is STATION.
     */
    @Test
    void testStationIsOfTypeStation_True() {
        station.setStationType(StationType.STATION.toString());
        assertTrue(controller.stationIsOfTypeStation());
    }

    /**
     * Tests stationIsOfTypeStation returns false if type is not STATION.
     */
    @Test
    void testStationIsOfTypeStation_False() {
        station.setStationType(StationType.DEPOT.toString());
        assertFalse(controller.stationIsOfTypeStation());
    }

    /**
     * Tests getTerminalEvolveCost calculation.
     */
    @Test
    void testGetTerminalEvolveCost() {
        station.setConstructionCost(50);
        int expected = StationType.TERMINAL.getConstructionCost() - 50;
        assertEquals(expected, controller.getTerminalEvolveCost());
    }

    /**
     * Tests getStationEvolveCost calculation.
     */
    @Test
    void testGetStationEvolveCost() {
        station.setConstructionCost(50);
        int expected = StationType.STATION.getConstructionCost() - 50;
        assertEquals(expected, controller.getStationEvolveCost());
    }

    /**
     * Tests checkIfHasMoney returns true if enough money.
     */
    @Test
    void testCheckIfHasMoney_Enough() {
        assertTrue(controller.checkIfHasMoney(500));
    }

    /**
     * Tests checkIfHasMoney returns false if not enough money.
     */
    @Test
    void testCheckIfHasMoney_NotEnough() {
        assertFalse(controller.checkIfHasMoney(2000));
    }

    /**
     * Tests setStationTypeToTerminal sets type to TERMINAL.
     */
    @Test
    void testSetStationTypeToTerminal() {
        controller.setStationTypeToTerminal();
        assertEquals("TERMINAL", station.getStationType());
    }

    /**
     * Tests setStationTypeToStation sets type to STATION.
     */
    @Test
    void testSetStationTypeToStation() {
        controller.setStationTypeToStation();
        assertEquals("STATION", station.getStationType());
    }

    /**
     * Tests stationDirection sets the direction.
     */
    @Test
    void testStationDirection() {
        controller.stationDirection("EAST");
        assertEquals("EAST", station.getDirection());
    }

    /**
     * Tests setStationTypeDefault sets the type to the current stationType field.
     */
    @Test
    void testSetStationTypeDefault() {
        controller.setStationType("TERMINAL");
        controller.setStationTypeDefault();
        assertEquals("TERMINAL", station.getStationType());
    }
}
