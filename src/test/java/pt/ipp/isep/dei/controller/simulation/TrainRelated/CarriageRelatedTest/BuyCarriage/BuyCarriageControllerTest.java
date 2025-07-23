package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelatedTest.BuyCarriage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.BuyCarriage.BuyCarriageController;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit tests for BuyCarriageController.
 */
public class BuyCarriageControllerTest {
    private BuyCarriageController controller;
    private Simulation simulation;
    private Carriage carriageCheap;
    private Carriage carriageExpensive;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new BuyCarriageController();
        Size size = new Size(1, 1);
        Map map = new Map("mapa", size);
        TimeDate begin = new TimeDate(2020, 1, 1);
        TimeDate end = new TimeDate(2021, 1, 1);
        int dinheiro = 1000;
        Scenario scenario = new Scenario(map, "cenário", dinheiro, begin, end);
        simulation = new Simulation("TestSim", scenario);
        carriageCheap = new Carriage("Cheap", "img", 2020, 10, 100);
        carriageExpensive = new Carriage("Expensive", "img", 2020, 1000, 100);
        simulation.getAvailableDateCarriages().clear();
        simulation.getAvailableDateCarriages().add(carriageCheap);
        simulation.getAvailableDateCarriages().add(carriageExpensive);
        controller.setSimulation(simulation);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        BuyCarriageController c = new BuyCarriageController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getCarriagesList returns all available carriages.
     */
    @Test
    void testGetCarriagesList() {
        List<Carriage> list = controller.getCarriagesList();
        assertTrue(list.contains(carriageCheap));
        assertTrue(list.contains(carriageExpensive));
        assertEquals(2, list.size());
    }

    /**
     * Tests getCarriagesList returns empty when no carriages are available.
     */
    @Test
    void testGetCarriagesList_Empty() {
        simulation.getAvailableDateCarriages().clear();
        List<Carriage> list = controller.getCarriagesList();
        assertTrue(list.isEmpty());
    }

    /**
     * Tests notEnoughMoney returns false when there is enough money.
     */
    @Test
    void testNotEnoughMoney_Enough() {
        simulation.setActualMoney(100);
        assertFalse(controller.notEnoughMoney(carriageCheap));
    }

    /**
     * Tests notEnoughMoney returns true when there is not enough money.
     */
    @Test
    void testNotEnoughMoney_NotEnough() {
        simulation.setActualMoney(5);
        assertTrue(controller.notEnoughMoney(carriageCheap));
    }
}
