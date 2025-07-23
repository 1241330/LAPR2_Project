package pt.ipp.isep.dei.controller.scenario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.repository.MapRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ShowScenariosController} class.
 *
 * This class tests all public methods of ShowScenariosController, including edge cases such as null values and empty lists.
 */
class ShowScenariosControllerTest {

    private Map testMap;
    private Scenario testScenario;
    private ShowScenariosController controller;

    /**
     * Initializes a test map, scenario, and a fake map repository before each test.
     */
    @BeforeEach
    void setUp() {
        testMap = new Map("Test Map", new Size(10, 10));
        testScenario = new Scenario(testMap, "Test Scenario", 1000, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        testMap.addScenario(testScenario);

        controller = new ShowScenariosController();

        // Mocking MapRepository with in-memory list
        controller.setMapRepository(new MapRepository() {
            private final List<Map> maps = new ArrayList<>();

            @Override
            public int nActiveMaps() {
                return !maps.isEmpty() ? 1 : 0;
            }

            public void saveMap(Map map) {
                maps.add(map);
            }

            @Override
            public List<Map> getAllMaps() {
                return maps;
            }

            @Override
            public Map getMapById(int id) {
                return maps.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
            }
        });

        // Add test map to mock repository
        ((MapRepository) controller.getMapRepository()).getAllMaps().add(testMap);

        controller.setActualMap(testMap);
        controller.setActualScenario(testScenario);
    }

    /**
     * Tests if the scenario name is correctly retrieved.
     */
    @Test
    void testGetNameScenario() {
        assertEquals("Test Scenario", controller.getNameScenario());
    }

    /**
     * Tests if the map name is correctly retrieved.
     */
    @Test
    void testGetNameMap() {
        assertEquals("Test Map", controller.getNameMap());
    }

    /**
     * Tests if the initial money value is correctly retrieved.
     */
    @Test
    void testGetInitialMoney() {
        assertEquals(1000, controller.getInitialMoney());
    }

    /**
     * Tests if the beginning date is correctly retrieved.
     */
    @Test
    void testGetBeginningDate() {
        assertEquals(new TimeDate(2024, 1, 1).toString(), controller.getBeginningDate());
    }

    /**
     * Tests if the end date is correctly retrieved.
     */
    @Test
    void testGetEndDate() {
        assertEquals(new TimeDate(2024, 12, 31).toString(), controller.getEndDate());
    }

    /**
     * Tests if the controller correctly detects that scenarios exist.
     */
    @Test
    void testThereAreScenarios() {
        assertTrue(controller.thereAreScenarios());
    }

    /**
     * Tests if the controller correctly detects that active maps exist.
     */
    @Test
    void testThereAreActiveMaps() {
        assertTrue(controller.thereAreActiveMaps());
    }

    /**
     * Tests if the resources list is not null.
     */
    @Test
    void testResourcesListIsNotNull() {
        assertNotNull(controller.getResourcesList());
    }

    /**
     * Tests if the house blocks list is not null.
     */
    @Test
    void testHouseBlocksListIsNotNull() {
        assertNotNull(controller.getHouseBlocksResourceList());
    }

    /**
     * Tests if the industries list is not null.
     */
    @Test
    void testIndustriesListIsNotNull() {
        assertNotNull(controller.getIndustriesList());
    }

    /**
     * Tests if the controller returns null when the scenario is set to null.
     */
    @Test
    void testGetNameScenarioWhenScenarioIsNull() {
        controller.setActualScenario(null);
        assertNull(controller.getNameScenario());
    }

    /**
     * Tests if the controller correctly detects the absence of scenarios in the map.
     */
    @Test
    void testThereAreScenariosWhenNoScenariosExist() {
        testMap.getScenarios().clear();
        assertFalse(controller.thereAreScenarios(), "Should return false when no scenarios exist in the map.");
    }

    /**
     * Tests if the getMapRepository method returns the repository correctly.
     */
    @Test
    void testGetMapRepository() {
        assertNotNull(controller.getMapRepository());
    }

    /**
     * Tests if the setMapRepository method changes the repository correctly.
     */
    @Test
    void testSetMapRepository() {
        MapRepository repo = new MapRepository() {
            public int nActiveMaps() { return 0; }
            public java.util.List<Map> getAllMaps() { return new ArrayList<>(); }
            public Map getMapById(int id) { return null; }
        };
        controller.setMapRepository(repo);
        assertEquals(repo, controller.getMapRepository());
    }

    /**
     * Tests if the getActualMap method returns the current map correctly.
     */
    @Test
    void testGetActualMap() {
        assertEquals(testMap, controller.getActualMap());
    }

    /**
     * Tests if the setActualMap method changes the current map correctly.
     */
    @Test
    void testSetActualMap() {
        Map newMap = new Map("Novo Mapa", new Size(5, 5));
        controller.setActualMap(newMap);
        assertEquals(newMap, controller.getActualMap());
    }

    /**
     * Tests if the getActualScenario method returns the current scenario correctly.
     */
    @Test
    void testGetActualScenario() {
        assertEquals(testScenario, controller.getActualScenario());
    }

    /**
     * Tests if the setActualScenario method changes the current scenario correctly.
     */
    @Test
    void testSetActualScenario() {
        Scenario newScenario = new Scenario(testMap, "Novo Cenário", 500, new TimeDate(2025, 1, 1), new TimeDate(2025, 12, 31));
        controller.setActualScenario(newScenario);
        assertEquals(newScenario, controller.getActualScenario());
    }

    /**
     * Tests if thereAreScenarios returns false when the map is null.
     */
    @Test
    void testThereAreScenariosWhenMapIsNull() {
        controller.setActualMap(null);
        assertFalse(controller.thereAreScenarios());
    }

    /**
     * Tests if getNameMap returns null when the map is null.
     */
    @Test
    void testGetNameMapWhenMapIsNull() {
        controller.setActualMap(null);
        assertNull(controller.getNameMap());
    }

    /**
     * Tests if getInitialMoney returns 0 when the scenario is null.
     */
    @Test
    void testGetInitialMoneyWhenScenarioIsNull() {
        controller.setActualScenario(null);
        assertEquals(0, controller.getInitialMoney());
    }

    /**
     * Tests if getBeginningDate returns null when the scenario is null.
     */
    @Test
    void testGetBeginningDateWhenScenarioIsNull() {
        controller.setActualScenario(null);
        assertNull(controller.getBeginningDate());
    }

    /**
     * Tests if getEndDate returns null when the scenario is null.
     */
    @Test
    void testGetEndDateWhenScenarioIsNull() {
        controller.setActualScenario(null);
        assertNull(controller.getEndDate());
    }

    /**
     * Tests if getResourcesList returns null when the scenario is null.
     */
    @Test
    void testGetResourcesListWhenScenarioIsNull() {
        controller.setActualScenario(null);
        assertNull(controller.getResourcesList());
    }

    /**
     * Tests if getHouseBlocksResourceList returns null when the scenario is null.
     */
    @Test
    void testGetHouseBlocksResourceListWhenScenarioIsNull() {
        controller.setActualScenario(null);
        assertNull(controller.getHouseBlocksResourceList());
    }

    /**
     * Tests if getIndustriesList returns null when the scenario is null.
     */
    @Test
    void testGetIndustriesListWhenScenarioIsNull() {
        controller.setActualScenario(null);
        assertNull(controller.getIndustriesList());
    }

    /**
     * Tests if getResourcesList returns an empty list when there are no resources.
     */
    @Test
    void testGetResourcesListEmpty() {
        Scenario emptyScenario = new Scenario(testMap, "Empty", 0, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        controller.setActualScenario(emptyScenario);
        assertTrue(controller.getResourcesList().isEmpty());
    }

    /**
     * Tests if getHouseBlocksResourceList returns an empty list when there are no house blocks.
     */
    @Test
    void testGetHouseBlocksResourceListEmpty() {
        Scenario emptyScenario = new Scenario(testMap, "Empty", 0, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        controller.setActualScenario(emptyScenario);
        assertTrue(controller.getHouseBlocksResourceList().isEmpty());
    }

    /**
     * Tests if getIndustriesList returns an empty list when there are no industries.
     */
    @Test
    void testGetIndustriesListEmpty() {
        Scenario emptyScenario = new Scenario(testMap, "Empty", 0, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        controller.setActualScenario(emptyScenario);
        assertTrue(controller.getIndustriesList().isEmpty());
    }
}
