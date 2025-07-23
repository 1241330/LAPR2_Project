package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.UpgradeRailway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway.UpgradeConfirmationPopController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link UpgradeConfirmationPopController} class.
 */
public class UpgradeConfirmationPopControllerTest {

    private UpgradeConfirmationPopController controller;
    private Simulation simulation;
    private RailwayLine railwayLine;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new UpgradeConfirmationPopController();
        Size size = new Size(10, 10);
        Map map = new Map("mapTest", size);
        TimeDate start = new TimeDate(2020, 1, 1);
        TimeDate end = new TimeDate(2030, 12, 31);
        Scenario scenario = new Scenario(map, "scenarioTest", 1000, start, end);
        simulation = new Simulation("SimTest", scenario);
        railwayLine = new RailwayLine(new Station("A"), new Station("B"), RailwayLineType.SINGLE_ELECTRIFIED);
    }

    /**
     * Tests the default constructor.
     */
    @Test
    void testDefaultConstructor() {
        UpgradeConfirmationPopController ctrl = new UpgradeConfirmationPopController();
        assertNotNull(ctrl);
    }

    /**
     * Tests the getSimulation and setSimulation methods.
     */
    @Test
    void testGetAndSetSimulation() {
        assertNull(controller.getSimulation());
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
    }

    /**
     * Tests the getRailwayLine and setRailwayLine methods.
     */
    @Test
    void testGetAndSetRailwayLine() {
        assertNull(controller.getRailwayLine());
        controller.setRailwayLine(railwayLine);
        assertEquals(railwayLine, controller.getRailwayLine());
    }

    /**
     * Tests the getNewRailwayLineType and setNewRailwayLineType methods.
     */
    @Test
    void testGetAndSetNewRailwayLineType() {
        assertNull(controller.getNewRailwayLineType());
        controller.setNewRailwayLineType(RailwayLineType.DOUBLE_ELECTRIFIED);
        assertEquals(RailwayLineType.DOUBLE_ELECTRIFIED, controller.getNewRailwayLineType());
    }

    /**
     * Tests the getUpgradeCost and setUpgradeCost methods.
     */
    @Test
    void testGetAndSetUpgradeCost() {
        assertEquals(0, controller.getUpgradeCost());
        controller.setUpgradeCost(500);
        assertEquals(500, controller.getUpgradeCost());
    }

    /**
     * Tests the modifyRailwayLineType method, ensuring the railway line type is updated and money is deducted.
     */
    @Test
    void testModifyRailwayLineType() {
        controller.setSimulation(simulation);
        controller.setRailwayLine(railwayLine);
        controller.setNewRailwayLineType(RailwayLineType.DOUBLE_ELECTRIFIED);
        controller.setUpgradeCost(200);

        int initialMoney = simulation.getActualMoney();
        controller.modifyRailwayLineType();

        assertEquals(RailwayLineType.DOUBLE_ELECTRIFIED, railwayLine.getRailwayType());
        assertEquals(initialMoney - 200, simulation.getActualMoney());
    }
}