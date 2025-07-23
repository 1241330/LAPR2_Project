package pt.ipp.isep.dei.domain.Scenario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Scenario} class.
 */
class ScenarioTest {

    private Scenario scenario;
    private Map map;
    private TimeDate beginDate;
    private TimeDate endDate;
    private static final String SCENARIO_NAME = "Test Scenario";
    private static final int INITIAL_MONEY = 1000;

    /**
     * Sets up a default scenario instance before each test.
     */
    @BeforeEach
    void setUp() {
        map = new Map("Test Map", new Size(10, 10));
        beginDate = new TimeDate(1800, 1, 1);
        endDate = new TimeDate(1900, 12, 31);
        scenario = new Scenario(map, SCENARIO_NAME, INITIAL_MONEY, beginDate, endDate);
    }

    /**
     * Tests the constructor to ensure all fields are initialized correctly.
     */
    @Test
    void testConstructor() {
        assertNotNull(scenario);
        assertEquals(SCENARIO_NAME, scenario.getName());
        assertEquals(INITIAL_MONEY, scenario.getInitialMoney());
        assertEquals(beginDate, scenario.getBeginningDate());
        assertEquals(endDate, scenario.getEndDate());
        assertEquals(map, scenario.getMap());
    }

    /**
     * Tests the string representation of the scenario.
     */
    @Test
    void testToString() {
        assertEquals(SCENARIO_NAME, scenario.toString());
    }

    /**
     * Tests setting and getting the map.
     */
    @Test
    void testSetAndGetMap() {
        Map newMap = new Map("Another Map", new Size(5, 5));
        scenario.setMap(newMap);
        assertEquals(newMap, scenario.getMap());
    }

    /**
     * Tests industries list and industries in scenario.
     */
    @Test
    void testIndustriesListAndScenario() {
        scenario.getIndustriesListInScenario().clear();
        assertTrue(scenario.getIndustriesListInScenario().isEmpty());
    }

    /**
     * Tests resource type list setters and getters.
     */
    @Test
    void testTypeResourceList() {
        List<ResourcesType> resources = new ArrayList<>();
        resources.add(new HouseBlockResource("Water", 100, 5, 10));
        scenario.setTypeResourceList(resources);
        assertEquals(resources, scenario.getTypeResourceList());
    }

    /**
     * Tests house block resource list setters and getters.
     */
    @Test
    void testHouseBlockResourceList() {
        List<HouseBlockResource> houseResources = new ArrayList<>();
        houseResources.add(new HouseBlockResource("Electricity", 200, 10, 20));
        scenario.setHouseBlockResourceList(houseResources);
        assertEquals(houseResources, scenario.getHouseBlockResourceList());
    }

    /**
     * Tests adding industries by map cloning methods.
     */
    @Test
    void testAddIndustriesByMap() {
        scenario.getIndustriesListInScenario().clear();
        IndustryType type = IndustryType.PRIMARY_SECTOR;
        Position pos = new Position(1, 1);
        Industry industry = new Industry("TestIndustry", type, pos);
        scenario.getIndustriesListInScenario().add(industry);
        assertTrue(scenario.getIndustriesListInScenario().contains(industry));
    }

    /**
     * Tests simulation repository methods.
     */
    @Test
    void testSimulationRepositoryMethods() {
        Simulation sim = new Simulation("Sim1", scenario);
        scenario.addSimulation(sim);
        assertEquals(1, scenario.getNumSimulations());
        assertEquals(sim, scenario.getLastSimulation());
        assertTrue(scenario.getSimulations().contains(sim));
        scenario.removeSimulation(sim);
        assertEquals(0, scenario.getNumSimulations());
    }

    /**
     * Tests removing a simulation not present.
     */
    @Test
    void testRemoveSimulationNotPresent() {
        Simulation sim = new Simulation("Sim4", scenario);
        scenario.removeSimulation(sim); // não deve lançar exceção
        assertFalse(scenario.getSimulations().contains(sim));
    }

    /**
     * Tests setting type resource list to null.
     */
    @Test
    void testSetTypeResourceListNull() {
        scenario.setTypeResourceList(null);
        assertNull(scenario.getTypeResourceList());
    }

    /**
     * Tests setting house block resource list to null.
     */
    @Test
    void testSetHouseBlockResourceListNull() {
        scenario.setHouseBlockResourceList(null);
        assertNull(scenario.getHouseBlockResourceList());
    }
}
