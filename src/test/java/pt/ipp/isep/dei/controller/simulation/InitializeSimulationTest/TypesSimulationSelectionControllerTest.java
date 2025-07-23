package pt.ipp.isep.dei.controller.simulation.InitializeSimulationTest;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.TypesSimulationSelectionController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import static org.junit.jupiter.api.Assertions.*;

public class TypesSimulationSelectionControllerTest {
    /**
     * Tests controller initialization with no scenario selected.
     */
    @Test
    void testInitializationWithoutScenario() {
        TypesSimulationSelectionController controller = new TypesSimulationSelectionController();
        assertNull(controller.getSelectedScenario());
        assertThrows(IllegalStateException.class, controller::getMapFromScenario);
    }

    /**
     * Tests setting and getting the selected scenario.
     */
    @Test
    void testSetAndGetSelectedScenario() {
        Map map = new Map("MapTest", new Size(10, 10));
        Scenario scenario = new Scenario(map, "ScenarioTest", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        TypesSimulationSelectionController controller = new TypesSimulationSelectionController();
        controller.setSelectedScenario(scenario);
        assertEquals(scenario, controller.getSelectedScenario());
    }

    /**
     * Tests getMapFromScenario returns the correct map when a scenario is selected.
     */
    @Test
    void testGetMapFromScenarioWithScenarioSelected() {
        Map map = new Map("MapTest", new Size(10, 10));
        Scenario scenario = new Scenario(map, "ScenarioTest", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        TypesSimulationSelectionController controller = new TypesSimulationSelectionController();
        controller.setSelectedScenario(scenario);
        assertEquals(map, controller.getMapFromScenario());
    }

    /**
     * Tests getMapFromScenario throws exception if scenario is set to null.
     */
    @Test
    void testGetMapFromScenarioWithNullScenario() {
        TypesSimulationSelectionController controller = new TypesSimulationSelectionController();
        controller.setSelectedScenario(null);
        assertNull(controller.getSelectedScenario());
        assertThrows(IllegalStateException.class, controller::getMapFromScenario);
    }

    /**
     * Tests changing the selected scenario after one is already set.
     */
    @Test
    void testChangeSelectedScenario() {
        Map map1 = new Map("Map1", new Size(10, 10));
        Map map2 = new Map("Map2", new Size(20, 20));
        Scenario scenario1 = new Scenario(map1, "Scenario1", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        Scenario scenario2 = new Scenario(map2, "Scenario2", 2000, new TimeDate(2025, 1, 1), new TimeDate(2025, 12, 31));
        TypesSimulationSelectionController controller = new TypesSimulationSelectionController();
        controller.setSelectedScenario(scenario1);
        assertEquals(scenario1, controller.getSelectedScenario());
        controller.setSelectedScenario(scenario2);
        assertEquals(scenario2, controller.getSelectedScenario());
        assertEquals(map2, controller.getMapFromScenario());
    }

    /**
     * Tests setting the same scenario multiple times.
     */
    @Test
    void testSetSameScenarioMultipleTimes() {
        Map map = new Map("MapTest", new Size(10, 10));
        Scenario scenario = new Scenario(map, "ScenarioTest", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        TypesSimulationSelectionController controller = new TypesSimulationSelectionController();
        controller.setSelectedScenario(scenario);
        controller.setSelectedScenario(scenario);
        assertEquals(scenario, controller.getSelectedScenario());
        assertEquals(map, controller.getMapFromScenario());
    }

    /**
     * Tests getMapFromScenario throws NullPointerException if scenario has null map.
     */
    @Test
    void testGetMapFromScenarioWithScenarioWithNullMap() {
        Scenario scenario = new Scenario(null, "ScenarioTest", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        TypesSimulationSelectionController controller = new TypesSimulationSelectionController();
        controller.setSelectedScenario(scenario);
        assertThrows(NullPointerException.class, controller::getMapFromScenario);
    }
}
