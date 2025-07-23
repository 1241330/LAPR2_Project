package pt.ipp.isep.dei.controller.simulation.SimulationRelatedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.SimulationRelated.CreateSimulationController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateSimulationController class.
 */
public class CreateSimulationControllerTest {

    private CreateSimulationController controller;
    private Scenario scenario;
    private Simulation simulation;
    private Size size;
    private Map map;
    private TimeDate date;

    @BeforeEach
    void setUp() {
        controller = new CreateSimulationController();
        this.size = new Size(10, 10);
        this.map = new Map("TestMap", this.size);
        this.date = new TimeDate(2024, 1, 1);
        this.scenario = new Scenario(this.map, "TestScenario", 1000, this.date, this.date);
    }

    /**
     * Test setActualScenario and getActualScenario methods.
     */
    @Test
    void testSetAndGetActualScenario() {
        controller.setActualScenario(scenario);
        assertEquals(scenario, controller.getActualScenario());
    }

    /**
     * Test setSimulationName and getSimulationName methods.
     */
    @Test
    void testSetAndGetSimulationName() {
        controller.setSimulationName("SimTest");
        assertEquals("SimTest", controller.getSimulationName());
    }

    /**
     * Test nameSimulationIsEmpty returns true for null and empty names.
     */
    @Test
    void testNameSimulationIsEmpty() {
        controller.setSimulationName(null);
        assertTrue(controller.nameSimulationIsEmpty());
        controller.setSimulationName("");
        assertTrue(controller.nameSimulationIsEmpty());
        controller.setSimulationName("   ");
        assertTrue(controller.nameSimulationIsEmpty());
        controller.setSimulationName("Sim");
        assertFalse(controller.nameSimulationIsEmpty());
    }

    /**
     * Test isValidName returns true for valid names and false for invalid ones.
     */
    @Test
    void testIsValidName() {
        controller.setSimulationName("Sim1");
        assertTrue(controller.isValidName());
        controller.setSimulationName("Sim_2");
        assertTrue(controller.isValidName());
        controller.setSimulationName("Sim Test");
        assertFalse(controller.isValidName());
        controller.setSimulationName("Sim-3");
        assertFalse(controller.isValidName());
    }

    /**
     * Test createSimulation creates a simulation and adds it to the scenario.
     */
    @Test
    void testCreateSimulation() {
        controller.setActualScenario(scenario);
        controller.setSimulationName("SimTest");
        controller.createSimulation();
        Simulation sim = controller.getSimulation();
        assertNotNull(sim);
        assertEquals("SimTest", sim.getName());
        assertTrue(scenario.getSimulations().contains(sim));
    }

    /**
     * Test createSimulation throws exception if scenario is not set.
     */
    @Test
    void testCreateSimulationNoScenario() {
        controller.setSimulationName("SimTest");
        assertThrows(IllegalArgumentException.class, controller::createSimulation);
    }

    /**
     * Test alreadyExistsNameSimulationInScenario returns true if name exists, false otherwise.
     */
    @Test
    void testAlreadyExistsNameSimulationInScenario() {
        controller.setActualScenario(scenario);
        controller.setSimulationName("SimTest");
        controller.createSimulation();
        controller.setSimulationName("SimTest");
        assertTrue(controller.alreadyExistsNameSimulationInScenario());
        controller.setSimulationName("OtherName");
        assertFalse(controller.alreadyExistsNameSimulationInScenario());
    }
}
