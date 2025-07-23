package pt.ipp.isep.dei.controller.simulation.InitializeSimulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.InitializeSimulationController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import static org.junit.jupiter.api.Assertions.*;

public class InitializeSimulationControllerTest {
    private Map map;
    private Scenario scenario;
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        map = new Map("MapTest", new Size(10, 10));
        scenario = new Scenario(map, "ScenarioTest", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        simulation = new Simulation("SimTest", scenario);
    }

    /**
     * Tests controller initialization without setting a simulation.
     */
    @Test
    void testInitializationWithoutSimulation() {
        InitializeSimulationController controller = new InitializeSimulationController();
        assertNull(controller.getSimulation());
        assertNull(controller.getActualScenario());
        // Métodos que dependem de simulação devem lançar NullPointerException
        assertThrows(NullPointerException.class, controller::getNameMap);
        assertThrows(NullPointerException.class, controller::getNameScenario);
    }

    /**
     * Tests setting a simulation and all getters.
     */
    @Test
    void testSetSimulationAndGetters() {
        InitializeSimulationController controller = new InitializeSimulationController();
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
        assertEquals(scenario, controller.getActualScenario());
        assertEquals("MapTest", controller.getNameMap());
        assertEquals("ScenarioTest", controller.getNameScenario());
    }

    /**
     * Tests deleteSimulation removes the simulation from the scenario.
     */
    @Test
    void testDeleteSimulation() {
        InitializeSimulationController controller = new InitializeSimulationController();
        controller.setSimulation(simulation);
        // Adiciona a simulação ao cenário
        scenario.getSimulations().add(simulation);
        assertTrue(scenario.getSimulations().contains(simulation));
        controller.deleteSimulation();
        assertFalse(scenario.getSimulations().contains(simulation));
    }

    /**
     * Tests deleteSimulation when no simulation is set (should throw NullPointerException).
     */
    @Test
    void testDeleteSimulationWithoutSimulation() {
        InitializeSimulationController controller = new InitializeSimulationController();
        assertThrows(NullPointerException.class, controller::deleteSimulation);
    }

    /**
     * Tests setSimulation with null and subsequent method calls.
     */
    @Test
    void testSetSimulationWithNull() {
        InitializeSimulationController controller = new InitializeSimulationController();
        controller.setSimulation(null);
        assertNull(controller.getSimulation());
        assertNull(controller.getActualScenario());
        assertThrows(NullPointerException.class, controller::getNameMap);
        assertThrows(NullPointerException.class, controller::getNameScenario);
    }

    /**
     * Tests getNameMap and getNameScenario when scenario or map is null (should throw NullPointerException).
     */
    @Test
    void testGetNameMapScenarioWithNullScenarioOrMap() {
        InitializeSimulationController controller = new InitializeSimulationController();
        // Simulação com cenário null
        Simulation sim = new Simulation("SimNull", null);
        controller.setSimulation(sim);
        assertThrows(NullPointerException.class, controller::getNameMap);
        assertThrows(NullPointerException.class, controller::getNameScenario);
    }

    /**
     * Tests deleteSimulation when simulation is not in scenario (should not throw, just do nothing).
     */
    @Test
    void testDeleteSimulationNotInScenario() {
        InitializeSimulationController controller = new InitializeSimulationController();
        controller.setSimulation(simulation);
        // Não adiciona a simulação ao cenário
        assertFalse(scenario.getSimulations().contains(simulation));
        // Não deve lançar exceção
        assertDoesNotThrow(controller::deleteSimulation);
        assertFalse(scenario.getSimulations().contains(simulation));
    }
}
