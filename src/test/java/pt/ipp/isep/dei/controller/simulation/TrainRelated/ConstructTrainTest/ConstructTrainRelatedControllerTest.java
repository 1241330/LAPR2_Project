package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrainTest;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.ConstructTrainRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ConstructTrainRelatedController class.
 */
public class ConstructTrainRelatedControllerTest {
    /**
     * Creates a minimal valid Scenario for testing purposes.
     *
     * @return a valid Scenario instance
     */
    private Scenario createScenario() {
        Size size = new Size(100, 100);
        Map map = new Map("mapaTeste", size);
        TimeDate inicio = new TimeDate(2020, 1, 1);
        TimeDate fim = new TimeDate(2030, 12, 31);
        return new Scenario(map, "cenarioTeste", 10000, inicio, fim);
    }

    /**
     * Creates a minimal valid Simulation for testing purposes.
     *
     * @return a valid Simulation instance
     */
    private Simulation createSimulation(int currentTime, int actualMoney) {
        Simulation sim = new Simulation("SimTest", createScenario());
        // Reflection or setters may be needed if there are no public setters for currentTime/actualMoney
        // For this test, we assume setters or direct field access is possible
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
        ConstructTrainRelatedController controller = new ConstructTrainRelatedController();
        Simulation sim = createSimulation(100, 5000);
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests getting the actual date string from the controller.
     */
    @Test
    void testGetActualDate() {
        ConstructTrainRelatedController controller = new ConstructTrainRelatedController();
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
        ConstructTrainRelatedController controller = new ConstructTrainRelatedController();
        Simulation sim = createSimulation(100, 9876);
        controller.setSimulation(sim);
        String money = controller.getActualMoney();
        assertEquals("9876", money);
    }
}
