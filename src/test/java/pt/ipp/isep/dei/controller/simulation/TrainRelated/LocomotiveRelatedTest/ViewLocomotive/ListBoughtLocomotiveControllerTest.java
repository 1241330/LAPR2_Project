package pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelatedTest.ViewLocomotive;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.ViewLocomotive.ListBoughtLocomotiveController;
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
 * Unit tests for the ListBoughtLocomotiveController class.
 */
public class ListBoughtLocomotiveControllerTest {
    private Scenario createScenario() {
        Size size = new Size(100, 100);
        Map map = new Map("mapaTeste", size);
        TimeDate inicio = new TimeDate(2020, 1, 1);
        TimeDate fim = new TimeDate(2030, 12, 31);
        return new Scenario(map, "cenarioTeste", 10000, inicio, fim);
    }

    private Simulation createSimulation() {
        return new Simulation("SimTest", createScenario());
    }

    private Locomotive createLocomotive(String name, int price) {
        return new Locomotive(name, "img.png", 1000.0, 2.0, 120.0, 2000, price, FuelType.DIESEL, 10, 100);
    }

    /**
     * Tests setting and getting the Simulation in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        ListBoughtLocomotiveController controller = new ListBoughtLocomotiveController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests retrieving the bought locomotives list when the list is empty.
     */
    @Test
    void testGetLocomotivesList_Empty() {
        ListBoughtLocomotiveController controller = new ListBoughtLocomotiveController();
        Simulation sim = createSimulation();
        controller.setSimulation(sim);
        ObservableList<Locomotive> result = controller.getLocomotivesList();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests retrieving the bought locomotives list when there are locomotives present.
     */
    @Test
    void testGetLocomotivesList_WithLocomotives() {
        ListBoughtLocomotiveController controller = new ListBoughtLocomotiveController();
        Simulation sim = createSimulation();
        Locomotive l1 = createLocomotive("L1", 5000);
        Locomotive l2 = createLocomotive("L2", 7000);
        sim.getAcquiredLocomotives().add(l1);
        sim.getAcquiredLocomotives().add(l2);
        controller.setSimulation(sim);
        ObservableList<Locomotive> result = controller.getLocomotivesList();
        assertEquals(2, result.size());
        assertTrue(result.contains(l1));
        assertTrue(result.contains(l2));
    }
}
