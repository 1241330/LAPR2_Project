package pt.ipp.isep.dei.controller.simulation.TrainRelated;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.TrainRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TrainRelatedController class.
 */
public class TrainRelatedControllerTest {
    private Scenario createScenario() {
        Size size = new Size(100, 100);
        Map map = new Map("mapaTeste", size);
        TimeDate inicio = new TimeDate(2020, 1, 1);
        TimeDate fim = new TimeDate(2030, 12, 31);
        return new Scenario(map, "cenarioTeste", 10000, inicio, fim);
    }

    private Simulation createSimulation(int currentTime, int actualMoney) {
        Simulation sim = new Simulation("SimTest", createScenario());
        try {
            java.lang.reflect.Field f1 = Simulation.class.getDeclaredField("currentTime");
            f1.setAccessible(true);
            f1.setInt(sim, currentTime);
            java.lang.reflect.Field f2 = Simulation.class.getDeclaredField("actualMoney");
            f2.setAccessible(true);
            f2.setInt(sim, actualMoney);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sim;
    }

    /**
     * Tests setting and getting the Simulation in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        TrainRelatedController controller = new TrainRelatedController();
        Simulation sim = createSimulation(100, 5000);
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests getting the actual date string from the controller.
     */
    @Test
    void testGetActualDate() {
        TrainRelatedController controller = new TrainRelatedController();
        Simulation sim = createSimulation(12345, 5000);
        controller.setSimulation(sim);
        String date = controller.getActualDate();
        assertNotNull(date);
        assertFalse(date.isEmpty());
    }

    /**
     * Tests getting the actual money string from the controller.
     */
    @Test
    void testGetActualMoney() {
        TrainRelatedController controller = new TrainRelatedController();
        Simulation sim = createSimulation(100, 9876);
        controller.setSimulation(sim);
        String money = controller.getActualMoney();
        assertEquals("9876", money);
    }
}
