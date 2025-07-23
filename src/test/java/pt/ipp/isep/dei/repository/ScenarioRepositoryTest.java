package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ScenarioRepository} class.
 * Covers all scenarios: add, remove, get, existence, name checks, and null/edge cases.
 */
class ScenarioRepositoryTest {
    private ScenarioRepository repository;
    private Scenario scenario1;
    private Scenario scenario2;
    private Scenario scenario3;
    private Map map;

    @BeforeEach
    void setUp() {
        repository = new ScenarioRepository();
        map = new Map("TestMap", new Size(10, 10));
        scenario1 = new Scenario(map, "ScenarioA", 0, null, null);
        scenario2 = new Scenario(map, "ScenarioB", 0, null, null);
        scenario3 = new Scenario(map, "ScenarioA", 0, null, null); // Same name as scenario1
    }

    /**
     * Test adding a scenario successfully.
     */
    @Test
    void testAddScenarioSuccess() {
        assertTrue(repository.addScenario(scenario1));
        assertEquals(1, repository.getScenarioCount());
        assertTrue(repository.getAllScenarios().contains(scenario1));
    }

    /**
     * Test adding a scenario with duplicate name (case-insensitive).
     */
    @Test
    void testAddScenarioDuplicateName() {
        repository.addScenario(scenario1);
        assertFalse(repository.addScenario(scenario3));
        assertEquals(1, repository.getScenarioCount());
    }

    /**
     * Test adding a null scenario throws exception.
     */
    @Test
    void testAddScenarioNull() {
        assertThrows(IllegalArgumentException.class, () -> repository.addScenario(null));
    }

    /**
     * Test removing a scenario successfully.
     */
    @Test
    void testRemoveScenarioSuccess() {
        repository.addScenario(scenario1);
        assertTrue(repository.removeScenario(scenario1));
        assertEquals(0, repository.getScenarioCount());
    }

    /**
     * Test removing a scenario that does not exist returns false.
     */
    @Test
    void testRemoveScenarioNotExists() {
        assertFalse(repository.removeScenario(scenario1));
    }

    /**
     * Test removing a null scenario throws exception.
     */
    @Test
    void testRemoveScenarioNull() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeScenario(null));
    }

    /**
     * Test getScenarioByName returns correct scenario or null.
     */
    @Test
    void testGetScenarioByName() {
        repository.addScenario(scenario1);
        repository.addScenario(scenario2);
        assertEquals(scenario1, repository.getScenarioByName("ScenarioA"));
        assertEquals(scenario2, repository.getScenarioByName("ScenarioB"));
        assertEquals(scenario1, repository.getScenarioByName("SCENARIOA"));
        assertNull(repository.getScenarioByName("Nonexistent"));
    }

    /**
     * Test getScenarioCount returns correct number.
     */
    @Test
    void testGetScenarioCount() {
        assertEquals(0, repository.getScenarioCount());
        repository.addScenario(scenario1);
        assertEquals(1, repository.getScenarioCount());
        repository.addScenario(scenario2);
        assertEquals(2, repository.getScenarioCount());
    }

    /**
     * Test getAllScenarios returns the correct list.
     */
    @Test
    void testGetAllScenarios() {
        repository.addScenario(scenario1);
        repository.addScenario(scenario2);
        List<Scenario> all = repository.getAllScenarios();
        assertTrue(all.contains(scenario1));
        assertTrue(all.contains(scenario2));
        assertEquals(2, all.size());
    }

    /**
     * Test scenarioExists returns true for existing scenario and false otherwise.
     */
    @Test
    void testScenarioExists() {
        repository.addScenario(scenario1);
        assertTrue(repository.scenarioExists(scenario1));
        assertFalse(repository.scenarioExists(scenario2));
        assertFalse(repository.scenarioExists(null));
    }

    /**
     * Test scenarioNameExists returns true for existing name (case-insensitive) and false otherwise.
     */
    @Test
    void testScenarioNameExists() {
        repository.addScenario(scenario1);
        assertTrue(repository.scenarioNameExists("ScenarioA"));
        assertTrue(repository.scenarioNameExists("SCENARIOA"));
        assertFalse(repository.scenarioNameExists("ScenarioB"));
        assertFalse(repository.scenarioNameExists(null));
    }

    /**
     * Test getScenarios returns the same as getAllScenarios.
     */
    @Test
    void testGetScenarios() {
        repository.addScenario(scenario1);
        repository.addScenario(scenario2);
        assertEquals(repository.getAllScenarios(), repository.getScenarios());
    }

    /**
     * Test setScenarios replaces the list of scenarios.
     */
    @Test
    void testSetScenarios() {
        List<Scenario> newList = new ArrayList<>();
        newList.add(scenario2);
        repository.setScenarios(newList);
        assertEquals(1, repository.getScenarioCount());
        assertTrue(repository.getAllScenarios().contains(scenario2));
    }

    /**
     * Test setScenarios with null list.
     */
    @Test
    void testSetScenariosNull() {
        assertThrows(NullPointerException.class, () -> repository.setScenarios(null));
    }
}
