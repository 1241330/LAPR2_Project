package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.RailwayRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RailwayRelatedController} class.
 */
public class RailwayRelatedControllerTest {

    private RailwayRelatedController controller;
    private Simulation simulation;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new RailwayRelatedController();
        Size size = new Size(10, 10);
        Map map = new Map("mapTest", size);
        TimeDate start = new TimeDate(2020, 1, 1);
        TimeDate end = new TimeDate(2030, 12, 31);
        Scenario scenario = new Scenario(map, "scenarioTest", 5000, start, end);
        simulation = new Simulation("SimTest", scenario);
    }

    /**
     * Tests the default constructor.
     */
    @Test
    void testDefaultConstructor() {
        RailwayRelatedController ctrl = new RailwayRelatedController();
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
     * Tests the getActualDate method.
     */
    @Test
    void testGetActualDate() {
        controller.setSimulation(simulation);
        String actualDate = controller.getActualDate();
        assertNotNull(actualDate);
        // The date string should contain the year 2020 (start date)
        assertTrue(actualDate.contains("2020"));
    }

    /**
     * Tests the getActualMoney method.
     */
    @Test
    void testGetActualMoney() {
        controller.setSimulation(simulation);
        String money = controller.getActualMoney();
        assertEquals(String.valueOf(simulation.getActualMoney()), money);
    }
}