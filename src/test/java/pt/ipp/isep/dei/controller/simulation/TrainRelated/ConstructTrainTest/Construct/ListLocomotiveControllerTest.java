package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrainTest.Construct;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct.ListLocomotiveController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain._Others_.Size;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ListLocomotiveController class.
 */
public class ListLocomotiveControllerTest {
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
    private Simulation createSimulation() {
        return new Simulation("SimTest", createScenario());
    }

    /**
     * Creates a Locomotive with the specified in-use status.
     *
     * @param inUse whether the locomotive is in use
     * @return a Locomotive instance
     */
    private Locomotive createLocomotive(boolean inUse) {
        Locomotive l = new Locomotive("L1", "img.png", 1000.0, 2.0, 120.0, 2000, 5000, FuelType.DIESEL, 10, 1000);
        l.setInUse(inUse);
        return l;
    }

    /**
     * Tests setting and getting the Simulation in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        ListLocomotiveController controller = new ListLocomotiveController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests retrieving the list of locomotives when the list is empty.
     */
    @Test
    void testGetLocomotivesList_Empty() {
        ListLocomotiveController controller = new ListLocomotiveController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        ObservableList<Locomotive> result = controller.getLocomotivesList();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests retrieving the list of locomotives when all are in use.
     */
    @Test
    void testGetLocomotivesList_AllInUse() {
        ListLocomotiveController controller = new ListLocomotiveController();
        Simulation sim = createSimulation();
        for (int i = 0; i < 3; i++) {
            sim.getAcquiredLocomotives().add(createLocomotive(true));
        }
        controller.setSimulation(sim);
        ObservableList<Locomotive> result = controller.getLocomotivesList();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests retrieving the list of locomotives when all are available.
     */
    @Test
    void testGetLocomotivesList_AllAvailable() {
        ListLocomotiveController controller = new ListLocomotiveController();
        Simulation sim = createSimulation();
        for (int i = 0; i < 3; i++) {
            sim.getAcquiredLocomotives().add(createLocomotive(false));
        }
        controller.setSimulation(sim);
        ObservableList<Locomotive> result = controller.getLocomotivesList();
        assertEquals(3, result.size());
        for (Locomotive l : result) {
            assertFalse(l.getInUse());
        }
    }

    /**
     * Tests retrieving the list of locomotives when there is a mix of available and in-use locomotives.
     */
    @Test
    void testGetLocomotivesList_Mixed() {
        ListLocomotiveController controller = new ListLocomotiveController();
        Simulation sim = createSimulation();
        Locomotive l1 = createLocomotive(false);
        Locomotive l2 = createLocomotive(true);
        Locomotive l3 = createLocomotive(false);
        sim.getAcquiredLocomotives().add(l1);
        sim.getAcquiredLocomotives().add(l2);
        sim.getAcquiredLocomotives().add(l3);
        controller.setSimulation(sim);
        ObservableList<Locomotive> result = controller.getLocomotivesList();
        assertEquals(2, result.size());
        assertTrue(result.contains(l1));
        assertTrue(result.contains(l3));
        assertFalse(result.contains(l2));
    }
}
