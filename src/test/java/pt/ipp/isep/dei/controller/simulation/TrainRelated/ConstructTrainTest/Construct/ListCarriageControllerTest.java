package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrainTest.Construct;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct.ListCarriageController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain._Others_.Size;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ListCarriageController class.
 */
class ListCarriageControllerTest {
    /**
     * Creates a valid Scenario for testing purposes.
     *
     * @return a valid Scenario instance
     */
    private Scenario createScenario() {
        // Criação mínima de Map, TimeDate e Size para o construtor de Scenario
        Size size = new Size(100, 100); // valores fictícios
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
     * Creates a Carriage with the specified in-use status.
     *
     * @param inUse whether the carriage is in use
     * @return a Carriage instance
     */
    private Carriage createCarriage(boolean inUse) {
        Carriage c = new Carriage("C1", "img.png", 2000, 1000, 50);
        c.setInUse(inUse);
        return c;
    }

    /**
     * Creates a valid Locomotive for testing purposes.
     *
     * @return a Locomotive instance
     */
    private Locomotive createLocomotive() {
        return new Locomotive("L1", "img.png", 1000.0, 2.0, 120.0, 2000, 5000, FuelType.DIESEL, 10, 1000);
    }

    /**
     * Tests setting and getting the Simulation in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        ListCarriageController controller = new ListCarriageController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests setting and getting the Locomotive in the controller.
     */
    @Test
    void testSetAndGetLocomotive() {
        ListCarriageController controller = new ListCarriageController();
        Locomotive loco = createLocomotive();
        controller.setLocomotive(loco);
        assertEquals(loco, controller.getLocomotive());
    }

    /**
     * Tests retrieving acquired carriages when the list is empty.
     */
    @Test
    void testGetAcquiredCarriagesList_EmptyList() {
        ListCarriageController controller = new ListCarriageController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        ObservableList<Carriage> result = controller.getAcquiredCarriagesList();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests retrieving acquired carriages when all are in use.
     */
    @Test
    void testGetAcquiredCarriagesList_AllInUse() {
        ListCarriageController controller = new ListCarriageController();
        Simulation sim = createSimulation();
        for (int i = 0; i < 3; i++) {
            sim.getBoughtCarriages().add(createCarriage(true));
        }
        controller.setSimulation(sim);
        ObservableList<Carriage> result = controller.getAcquiredCarriagesList();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests retrieving acquired carriages when all are available.
     */
    @Test
    void testGetAcquiredCarriagesList_AllAvailable() {
        ListCarriageController controller = new ListCarriageController();
        Simulation sim = createSimulation();
        for (int i = 0; i < 3; i++) {
            sim.getBoughtCarriages().add(createCarriage(false));
        }
        controller.setSimulation(sim);
        ObservableList<Carriage> result = controller.getAcquiredCarriagesList();
        assertEquals(3, result.size());
        for (Carriage c : result) {
            assertFalse(c.getInUse());
        }
    }

    /**
     * Tests retrieving acquired carriages when the list is mixed (some in use, some available).
     */
    @Test
    void testGetAcquiredCarriagesList_Mixed() {
        ListCarriageController controller = new ListCarriageController();
        Simulation sim = createSimulation();
        Carriage c1 = createCarriage(false);
        Carriage c2 = createCarriage(true);
        Carriage c3 = createCarriage(false);
        sim.getBoughtCarriages().add(c1);
        sim.getBoughtCarriages().add(c2);
        sim.getBoughtCarriages().add(c3);
        controller.setSimulation(sim);
        ObservableList<Carriage> result = controller.getAcquiredCarriagesList();
        assertEquals(2, result.size());
        assertTrue(result.contains(c1));
        assertTrue(result.contains(c3));
        assertFalse(result.contains(c2));
    }
}
