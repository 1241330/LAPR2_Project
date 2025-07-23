package pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelatedTest.BuyLocomotive;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive.LocomotivePurchaseConfirmationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain._Others_.Size;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LocomotivePurchaseConfirmationController class.
 */
public class LocomotivePurchaseConfirmationControllerTest {
    private Scenario createScenario() {
        Size size = new Size(100, 100);
        Map map = new Map("mapaTeste", size);
        TimeDate inicio = new TimeDate(2020, 1, 1);
        TimeDate fim = new TimeDate(2030, 12, 31);
        return new Scenario(map, "cenarioTeste", 10000, inicio, fim);
    }

    private Simulation createSimulation(int money) {
        Simulation sim = new Simulation("SimTest", createScenario());
        try {
            java.lang.reflect.Field f = Simulation.class.getDeclaredField("actualMoney");
            f.setAccessible(true);
            f.setInt(sim, money);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sim;
    }

    private Locomotive createLocomotive(String name, int price) {
        return new Locomotive(name, "img.png", 1000.0, 2.0, 120.0, 2000, price, FuelType.DIESEL, 10, 100);
    }

    /**
     * Tests setting and getting the Simulation and Locomotive in the controller.
     */
    @Test
    void testSetAndGetSimulationAndLocomotive() {
        LocomotivePurchaseConfirmationController controller = new LocomotivePurchaseConfirmationController();
        Simulation sim = createSimulation(10000);
        Locomotive loco = createLocomotive("L1", 5000);
        controller.setSimulation(sim);
        controller.setLocomotive(loco);
        assertEquals(sim, controller.getSimulation());
        assertEquals(loco, controller.getLocomotive());
    }

    /**
     * Tests buying a locomotive updates simulation money and adds the locomotive to bought list.
     */
    @Test
    void testBuyLocomotive() {
        LocomotivePurchaseConfirmationController controller = new LocomotivePurchaseConfirmationController();
        Simulation sim = createSimulation(10000);
        Locomotive loco = createLocomotive("L1", 5000);
        controller.setSimulation(sim);
        controller.setLocomotive(loco);
        controller.buyLocomotive();
        assertEquals(5000, sim.getActualMoney());
        assertTrue(sim.getBoughtLocomotives().stream().anyMatch(l -> l.getName().equals("L1") && l.getAcquisitionPrice() == 5000));
    }
}
