package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrainTest.View;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.View.ViewTrainController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain._Others_.Size;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ViewTrainController class.
 */
public class ViewTrainControllerTest {
    /**
     * Creates a valid Scenario for testing purposes.
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
     * Creates a valid Simulation for testing purposes.
     *
     * @return a valid Simulation instance
     */
    private Simulation createSimulation() {
        return new Simulation("SimTest", createScenario());
    }

    /**
     * Tests setting and getting the Simulation in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        ViewTrainController controller = new ViewTrainController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests retrieving the train list when the list is empty.
     */
    @Test
    void testGetTrainList_Empty() {
        ViewTrainController controller = new ViewTrainController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        ObservableList<Train> result = controller.getTrainList();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests retrieving the train list when there are trains present.
     */
    @Test
    void testGetTrainList_WithTrains() {
        ViewTrainController controller = new ViewTrainController();
        Simulation sim = createSimulation();
        // Create a dummy Train (Train is likely abstract, so use an anonymous class)
        Train t1 = new Train() {};
        Train t2 = new Train() {};
        sim.getTrainList().add(t1);
        sim.getTrainList().add(t2);
        controller.setSimulation(sim);
        ObservableList<Train> result = controller.getTrainList();
        assertEquals(2, result.size());
        assertTrue(result.contains(t1));
        assertTrue(result.contains(t2));
    }
}
