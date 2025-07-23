package pt.ipp.isep.dei.controller.simulation.InitializeSimulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.ChooseScenarioController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChooseScenarioControllerTest {
    private Map map;
    private Scenario scenario1;
    private Scenario scenario2;

    @BeforeEach
    void setUp() {
        map = new Map("TestMap", new Size(10, 10));
        scenario1 = new Scenario(map, "Scenario1", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        scenario2 = new Scenario(map, "Scenario2", 2000, new TimeDate(2025, 1, 1), new TimeDate(2025, 12, 31));
    }

    /**
     * Tests controller initialization with no map set.
     */
    @Test
    void testInitializationWithoutMap() {
        ChooseScenarioController controller = new ChooseScenarioController();
        assertNull(controller.getActualMap());
        assertTrue(controller.getListOfNamesScenarios().isEmpty());
        assertTrue(controller.listOfScenariosIsEmpty());
    }

    /**
     * Tests setting a map with no scenarios.
     */
    @Test
    void testSetMapWithNoScenarios() {
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        assertEquals(map, controller.getActualMap());
        assertTrue(controller.getListOfNamesScenarios().isEmpty());
        assertTrue(controller.listOfScenariosIsEmpty());
    }

    /**
     * Tests setting a map with scenarios and updating the scenario names list.
     */
    @Test
    void testSetMapWithScenarios() {
        map.addScenario(scenario1);
        map.addScenario(scenario2);
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        List<String> names = controller.getListOfNamesScenarios();
        assertEquals(2, names.size());
        assertTrue(names.contains("Scenario1"));
        assertTrue(names.contains("Scenario2"));
        assertFalse(controller.listOfScenariosIsEmpty());
    }

    /**
     * Tests selecting a scenario by valid index and retrieving the selected scenario.
     */
    @Test
    void testSetSelectedScenarioAndGetSelectedScenario() {
        map.addScenario(scenario1);
        map.addScenario(scenario2);
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        controller.setSelectedScenario(1);
        assertEquals(scenario2, controller.getSelectedScenario());
    }

    /**
     * Tests selecting a scenario with an invalid index throws IndexOutOfBoundsException.
     */
    @Test
    void testSetSelectedScenarioWithInvalidIndex() {
        map.addScenario(scenario1);
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        assertThrows(IndexOutOfBoundsException.class, () -> controller.setSelectedScenario(5));
    }

    /**
     * Tests setListOfNamesScenarios updates the names list correctly after scenarios are added.
     */
    @Test
    void testSetListOfNamesScenariosUpdatesList() {
        map.addScenario(scenario1);
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        controller.setListOfNamesScenarios();
        assertEquals(List.of("Scenario1"), controller.getListOfNamesScenarios());
    }

    /**
     * Tests selecting a scenario when the scenario list is empty throws IndexOutOfBoundsException.
     */
    @Test
    void testSetSelectedScenarioWhenListIsEmpty() {
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map); // map sem cenários
        assertThrows(IndexOutOfBoundsException.class, () -> controller.setSelectedScenario(0));
    }

    /**
     * Tests that getSelectedScenario returns null before any selection.
     */
    @Test
    void testGetSelectedScenarioBeforeSelection() {
        map.addScenario(scenario1);
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        assertNull(controller.getSelectedScenario());
    }

    /**
     * Tests adding scenarios with duplicate names.
     */
    @Test
    void testAddScenariosWithDuplicateNames() {
        Scenario duplicateScenario = new Scenario(map, "Scenario1", 3000, new TimeDate(2026, 1, 1), new TimeDate(2026, 12, 31));
        map.addScenario(scenario1);
        map.addScenario(duplicateScenario);
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        List<String> names = controller.getListOfNamesScenarios();
        assertEquals(2, names.size());
        assertEquals("Scenario1", names.get(0));
        assertEquals("Scenario1", names.get(1));
    }

    /**
     * Tests changing the map after selecting a scenario.
     */
    @Test
    void testChangeMapAfterSelection() {
        map.addScenario(scenario1);
        ChooseScenarioController controller = new ChooseScenarioController();
        controller.setActualMap(map);
        controller.setSelectedScenario(0);
        assertEquals(scenario1, controller.getSelectedScenario());

        Map newMap = new Map("NewMap", new Size(5, 5));
        controller.setActualMap(newMap);
        assertNull(controller.getSelectedScenario());
        assertTrue(controller.getListOfNamesScenarios().isEmpty());
    }
}
