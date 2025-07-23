package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelatedTest.ViewCarriages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.ViewCarriages.ListBoughtCarriageController;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ListBoughtCarriageController.
 */
public class ListBoughtCarriageControllerTest {
    private ListBoughtCarriageController controller;
    private Simulation simulation;
    private Carriage carriage1;
    private Carriage carriage2;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new ListBoughtCarriageController();
        simulation = new Simulation("TestSim", null);
        carriage1 = new Carriage("C1", "img", 2020, 10, 100);
        carriage2 = new Carriage("C2", "img", 2020, 20, 200);
        simulation.getBoughtCarriages().clear();
        simulation.getBoughtCarriages().add(carriage1);
        simulation.getBoughtCarriages().add(carriage2);
        controller.setSimulation(simulation);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        ListBoughtCarriageController c = new ListBoughtCarriageController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getCarriagesList returns all bought carriages.
     */
    @Test
    void testGetCarriagesList() {
        List<Carriage> list = controller.getCarriagesList();
        assertTrue(list.contains(carriage1));
        assertTrue(list.contains(carriage2));
        assertEquals(2, list.size());
    }

    /**
     * Tests getCarriagesList returns empty when no carriages are bought.
     */
    @Test
    void testGetCarriagesList_Empty() {
        simulation.getBoughtCarriages().clear();
        List<Carriage> list = controller.getCarriagesList();
        assertTrue(list.isEmpty());
    }
}
