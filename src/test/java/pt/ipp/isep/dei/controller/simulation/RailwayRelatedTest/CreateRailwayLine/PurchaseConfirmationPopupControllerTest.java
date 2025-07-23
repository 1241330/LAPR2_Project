package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.CreateRailwayLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.PurchaseConfirmationPopupController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Scenario.Scenario;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PurchaseConfirmationPopupController.
 */
public class PurchaseConfirmationPopupControllerTest {
    private PurchaseConfirmationPopupController controller;

    /**
     * Creates a valid Scenario for the tests.
     * @return valid instance of Scenario
     */
    private Scenario createScenario() {
        Map map = new Map("testMap", new Size(10, 10));
        TimeDate start = new TimeDate(2020, 1, 1);
        TimeDate end = new TimeDate(2025, 1, 1);
        return new Scenario(map, "test", 1000, start, end);
    }

    /**
     * Creates a valid Simulation for testing.
     * @param money initial money
     * @return a valid Simulation instance
     */
    private Simulation createSimulation(int money) {
        Simulation sim = new Simulation("sim", createScenario());
        sim.setActualMoney(money);
        return sim;
    }

    /**
     * Setup before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new PurchaseConfirmationPopupController();
    }

    /**
     * Test getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        Simulation simulation = createSimulation(100);
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
    }

    /**
     * Test getter and setter for path.
     */
    @Test
    void testSetAndGetPath() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 1);
        List<Position> path = Arrays.asList(pos1, pos2);
        controller.setPath(path);
        assertEquals(path, controller.getPath());
    }

    /**
     * Test getter and setter for departure station.
     */
    @Test
    void testSetAndGetDepartureStation() {
        Station station = new Station("Departure");
        controller.setDepartureStation(station);
        assertEquals(station, controller.getDepartureStation());
    }

    /**
     * Test getter and setter for arrival station.
     */
    @Test
    void testSetAndGetArrivalStation() {
        Station station = new Station("Arrival");
        controller.setArrivalStation(station);
        assertEquals(station, controller.getArrivalStation());
    }

    /**
     * Test getter and setter for railway line type.
     */
    @Test
    void testSetAndGetType() {
        RailwayLineType type = RailwayLineType.SINGLE_ELECTRIFIED;
        controller.setType(type);
        assertEquals(type, controller.getType());
    }

    /**
     * Test getter and setter for cost.
     */
    @Test
    void testSetAndGetCost() {
        controller.setCost(100);
        assertEquals(100, controller.getCost());
    }

    /**
     * Test addRailwayLine with normal values.
     */
    @Test
    void testAddRailwayLineNormal() {
        Simulation simulation = createSimulation(200);
        controller.setSimulation(simulation);
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 1);
        List<Position> path = Arrays.asList(pos1, pos2);
        controller.setPath(path);
        Station departure = new Station("Departure");
        Station arrival = new Station("Arrival");
        controller.setDepartureStation(departure);
        controller.setArrivalStation(arrival);
        RailwayLineType type = RailwayLineType.SINGLE_ELECTRIFIED;
        controller.setType(type);
        controller.setCost(50);
        controller.addRailwayLine();
        assertEquals(150, simulation.getActualMoney());
        assertEquals(1, simulation.getRailwayLines().size());
    }

    /**
     * Test addRailwayLine with zero cost.
     */
    @Test
    void testAddRailwayLineWithZeroCost() {
        Simulation simulation = createSimulation(100);
        controller.setSimulation(simulation);
        controller.setPath(Arrays.asList(new Position(0, 0)));
        controller.setDepartureStation(new Station("Departure"));
        controller.setArrivalStation(new Station("Arrival"));
        controller.setType(RailwayLineType.SINGLE_ELECTRIFIED);
        controller.setCost(0);
        controller.addRailwayLine();
        assertEquals(100, simulation.getActualMoney());
        assertEquals(1, simulation.getRailwayLines().size());
    }

    /**
     * Test addRailwayLine with null values for all fields except simulation and cost.
     */
    @Test
    void testAddRailwayLineWithNulls() {
        Simulation simulation = createSimulation(100);
        controller.setSimulation(simulation);
        controller.setPath(null);
        controller.setDepartureStation(null);
        controller.setArrivalStation(null);
        controller.setType(null);
        controller.setCost(10);
        controller.addRailwayLine();
        assertEquals(90, simulation.getActualMoney());
        assertEquals(1, simulation.getRailwayLines().size());
    }
}
