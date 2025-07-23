package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelatedTest.BuyCarriage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.BuyCarriage.CarriagePurchaseConfirmationController;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CarriagePurchaseConfirmationController.
 */
public class CarriagePurchaseConfirmationControllerTest {
    private CarriagePurchaseConfirmationController controller;
    private Simulation simulation;
    private Carriage carriage;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new CarriagePurchaseConfirmationController();
        simulation = new Simulation("TestSim", null);
        carriage = new Carriage("C1", "img", 2020, 50, 100);
        simulation.setActualMoney(100);
        controller.setSimulation(simulation);
        controller.setCarriage(carriage);
    }

    /**
     * Tests getter and setter for Simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        CarriagePurchaseConfirmationController c = new CarriagePurchaseConfirmationController();
        assertNull(c.getSimulation());
        c.setSimulation(simulation);
        assertEquals(simulation, c.getSimulation());
    }

    /**
     * Tests getter and setter for Carriage.
     */
    @Test
    void testSetAndGetCarriage() {
        CarriagePurchaseConfirmationController c = new CarriagePurchaseConfirmationController();
        assertNull(c.getCarriage());
        c.setCarriage(carriage);
        assertEquals(carriage, c.getCarriage());
    }

    /**
     * Tests buyCarriage updates money, adds to bought list, and sets inUse to false.
     */
    @Test
    void testBuyCarriage() {
        controller.buyCarriage();
        assertEquals(50, simulation.getActualMoney());
        List<Carriage> bought = simulation.getBoughtCarriages();
        assertEquals(1, bought.size());
        Carriage boughtCarriage = bought.get(0);
        assertEquals(carriage.getName(), boughtCarriage.getName());
        assertFalse(carriage.getInUse());
    }
}
