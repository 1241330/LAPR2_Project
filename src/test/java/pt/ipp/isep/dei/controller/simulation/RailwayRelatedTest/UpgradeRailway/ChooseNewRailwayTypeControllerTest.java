package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.UpgradeRailway;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway.ChooseNewRailwayTypeController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ChooseNewRailwayTypeController} class.
 */
public class ChooseNewRailwayTypeControllerTest {

    /**
     * Creates a new {@link Simulation} instance with the specified amount of money.
     *
     * @param money the initial money for the scenario
     * @return a new Simulation instance
     */
    private Simulation createSimulation(int money) {
        Size size = new Size(100, 100);
        Map map = new Map("mapaTeste", size);
        TimeDate inicio = new TimeDate(2020, 1, 1);
        TimeDate fim = new TimeDate(2030, 12, 31);
        Scenario scenario = new Scenario(map, "cenarioTeste", money, inicio, fim);
        return new Simulation("SimTest", scenario);
    }

    /**
     * Creates a new {@link RailwayLine} with the given type, number of positions, and station names.
     *
     * @param type      the type of the railway line
     * @param positions the number of positions in the railway line
     * @param s1        the name of the first station
     * @param s2        the name of the second station
     * @return a new RailwayLine instance
     */
    private RailwayLine createRailwayLine(RailwayLineType type, int positions, String s1, String s2) {
        Station station1 = new Station(s1);
        Station station2 = new Station(s2);
        RailwayLine line = new RailwayLine(station1, station2, type);
        Position pos = new Position(0, 0);
        for (int i = 0; i < positions; i++) line.getPositionsRailwayLine().add(pos);
        return line;
    }

    /**
     * Tests setting and getting the Simulation and RailwayLine in the controller.
     */
    @Test
    void testSetAndGetSimulationAndRailwayLine() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        Simulation sim = createSimulation(10000);
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 10, "A", "B");
        controller.setSimulation(sim);
        controller.setRailwayLine(line);
        assertEquals(sim, controller.getSimulation());
        assertEquals(line, controller.getRailwayLine());
    }

    /**
     * Tests getting and setting the Simulation in the controller.
     */
    @Test
    void testGetSimulationAndSetSimulation() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        Simulation sim = createSimulation(5000);
        assertNull(controller.getSimulation());
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests getting and setting the RailwayLine in the controller.
     */
    @Test
    void testGetRailwayLineAndSetRailwayLine() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 3, "A", "B");
        assertNull(controller.getRailwayLine());
        controller.setRailwayLine(line);
        assertEquals(line, controller.getRailwayLine());
    }

    /**
     * Tests getting prices for different railway types.
     */
    @Test
    void testGetPrices() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        assertEquals(String.valueOf(RailwayLineType.SINGLE_ELECTRIFIED.getCost()), controller.getSEPrice());
        assertEquals(String.valueOf(RailwayLineType.DOUBLE_NON_ELECTRIFIED.getCost()), controller.getDNEPrice());
        assertEquals(String.valueOf(RailwayLineType.DOUBLE_ELECTRIFIED.getCost()), controller.getDEPrice());
    }

    /**
     * Tests getting the price for single electrified railway type.
     */
    @Test
    void testGetSEPrice() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        assertEquals(String.valueOf(RailwayLineType.SINGLE_ELECTRIFIED.getCost()), controller.getSEPrice());
    }

    /**
     * Tests getting the price for double non-electrified railway type.
     */
    @Test
    void testGetDNEPrice() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        assertEquals(String.valueOf(RailwayLineType.DOUBLE_NON_ELECTRIFIED.getCost()), controller.getDNEPrice());
    }

    /**
     * Tests getting the price for double electrified railway type.
     */
    @Test
    void testGetDEPrice() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        assertEquals(String.valueOf(RailwayLineType.DOUBLE_ELECTRIFIED.getCost()), controller.getDEPrice());
    }

    /**
     * Tests getting the stations string for a railway line with stations "Alpha" and "Beta".
     */
    @Test
    void testGetStationsAlphaBeta() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 5, "Alpha", "Beta");
        assertEquals("Alpha ⇆ Beta", controller.getStations(line));
    }

    /**
     * Tests getting the stations string for a railway line with stations "A" and "B".
     */
    @Test
    void testGetStationsAB() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 2, "A", "B");
        assertEquals("A ⇆ B", controller.getStations(line));
    }

    /**
     * Tests calculating the upgrade cost for a railway line with 4 positions.
     */
    @Test
    void testGetUpgradeCost4Positions() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 4, "A", "B");
        controller.setRailwayLine(line);
        int expected = 4 * (RailwayLineType.DOUBLE_ELECTRIFIED.getCost() - RailwayLineType.SINGLE_ELECTRIFIED.getCost());
        assertEquals(expected, controller.getUpgradeCost(RailwayLineType.DOUBLE_ELECTRIFIED));
    }

    /**
     * Tests calculating the upgrade cost for a railway line with 5 positions.
     */
    @Test
    void testGetUpgradeCost5Positions() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 5, "A", "B");
        controller.setRailwayLine(line);
        int expected = 5 * (RailwayLineType.DOUBLE_ELECTRIFIED.getCost() - RailwayLineType.SINGLE_ELECTRIFIED.getCost());
        assertEquals(expected, controller.getUpgradeCost(RailwayLineType.DOUBLE_ELECTRIFIED));
    }

    /**
     * Tests verifying if there is enough money for the upgrade.
     */
    @Test
    void testVerifyIfHasMoney() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        Simulation sim = createSimulation(1000);
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 2, "A", "B");
        controller.setSimulation(sim);
        controller.setRailwayLine(line);
        int upgradeCost = controller.getUpgradeCost(RailwayLineType.DOUBLE_ELECTRIFIED);
        if (upgradeCost <= 1000) {
            assertTrue(controller.verifyIfHasMoney(RailwayLineType.DOUBLE_ELECTRIFIED));
        } else {
            assertFalse(controller.verifyIfHasMoney(RailwayLineType.DOUBLE_ELECTRIFIED));
        }
    }

    /**
     * Tests verifying if there is enough money for the upgrade in both true and false scenarios.
     */
    @Test
    void testVerifyIfHasMoneyTrueAndFalse() {
        ChooseNewRailwayTypeController controller = new ChooseNewRailwayTypeController();
        Simulation sim = createSimulation(10000);
        RailwayLine line = createRailwayLine(RailwayLineType.SINGLE_ELECTRIFIED, 2, "A", "B");
        controller.setSimulation(sim);
        controller.setRailwayLine(line);
        // Should have enough money
        assertTrue(controller.verifyIfHasMoney(RailwayLineType.DOUBLE_ELECTRIFIED));
        // Now simulate low money
        Simulation sim2 = createSimulation(1);
        controller.setSimulation(sim2);
        assertFalse(controller.verifyIfHasMoney(RailwayLineType.DOUBLE_ELECTRIFIED));
    }
}