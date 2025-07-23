package pt.ipp.isep.dei.controller.simulation.SimulationRelatedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.SimulationRelated.ChooseSimulationController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ChooseSimulationController class.
 */
public class ChooseSimulationControllerTest {

    private ChooseSimulationController controller;
    private Scenario scenario;
    private Simulation simulation1;
    private Simulation simulation2;
    private Size size;
    private Map map;
    private TimeDate date;

    @BeforeEach
    void setUp() {
        controller = new ChooseSimulationController();
        this.size = new Size(10, 10);
        this.map = new Map("TestMap", this.size);
        this.date = new TimeDate(2024, 1, 1);
        this.scenario = new Scenario(this.map, "TestScenario", 1000, this.date, this.date);
        this.simulation1 = new Simulation("Sim1", this.scenario);
        this.simulation2 = new Simulation("Sim2", this.scenario);
    }

    /**
     * Test setActualScenario and getActualScenario methods.
     */
    @Test
    void testSetAndGetActualScenario() {
        scenario.addSimulation(simulation1);
        controller.setActualScenario(scenario);
        assertEquals(scenario, controller.getActualScenario());
    }

    /**
     * Test setListOfNamesSimulations and getListOfNamesSimulations methods.
     */
    @Test
    void testSetAndGetListOfNamesSimulations() {
        scenario.addSimulation(simulation1);
        scenario.addSimulation(simulation2);
        controller.setActualScenario(scenario);
        List<String> expected = new ArrayList<>();
        expected.add("Sim1");
        expected.add("Sim2");
        assertEquals(expected, controller.getListOfNamesSimulations());
    }

    /**
     * Test setSelectedSimulation and getSelectedSimulation methods.
     */
    @Test
    void testSetAndGetSelectedSimulation() {
        scenario.addSimulation(simulation1);
        scenario.addSimulation(simulation2);
        controller.setActualScenario(scenario);
        controller.setSelectedSimulation(1);
        assertEquals(simulation2, controller.getSelectedSimulation());
    }

    /**
     * Test listOfSimulationsIsEmpty returns true when no simulations exist.
     */
    @Test
    void testListOfSimulationsIsEmptyTrue() {
        controller.setActualScenario(scenario);
        assertTrue(controller.listOfSimulationsIsEmpty());
    }

    /**
     * Test listOfSimulationsIsEmpty returns false when simulations exist.
     */
    @Test
    void testListOfSimulationsIsEmptyFalse() {
        scenario.addSimulation(simulation1);
        controller.setActualScenario(scenario);
        assertFalse(controller.listOfSimulationsIsEmpty());
    }

    /**
     * Test setSelectedSimulation with invalid index throws exception.
     */
    @Test
    void testSetSelectedSimulationInvalidIndex() {
        scenario.addSimulation(simulation1);
        controller.setActualScenario(scenario);
        assertThrows(IndexOutOfBoundsException.class, () -> controller.setSelectedSimulation(5));
    }
}
