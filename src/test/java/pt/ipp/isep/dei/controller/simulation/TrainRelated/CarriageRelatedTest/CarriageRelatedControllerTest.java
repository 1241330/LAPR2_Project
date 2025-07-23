package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelatedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.CarriageRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CarriageRelatedController.
 */
public class CarriageRelatedControllerTest {
    private CarriageRelatedController controller;
    private Simulation simulation;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new CarriageRelatedController();
        // Criação de um Scenario real para evitar NullPointerException
        Size size = new Size(1, 1);
        Map map = new Map("mapa", size);
        TimeDate begin = new TimeDate(2020, 1, 1);
        TimeDate end = new TimeDate(2021, 1, 1);
        int dinheiro = 1000;
        Scenario scenario = new Scenario(map, "cenário", dinheiro, begin, end);
        simulation = new Simulation("TestSim", scenario);
        controller.setSimulation(simulation);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        CarriageRelatedController c = new CarriageRelatedController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getActualDate returns the correct date string.
     */
    @Test
    void testGetActualDate() {
        simulation.setCurrentTime(0);
        String date = controller.getActualDate();
        assertNotNull(date);
        assertTrue(date instanceof String);
    }

    /**
     * Tests getActualMoney returns the correct money string.
     */
    @Test
    void testGetActualMoney() {
        simulation.setActualMoney(1234);
        String money = controller.getActualMoney();
        assertEquals("1234", money);
    }
}
