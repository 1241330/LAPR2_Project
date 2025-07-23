package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SimulationRepository} class.
 * This class covers all scenarios for adding, removing, retrieving and checking simulations.
 */
class SimulationRepositoryTest {

    private SimulationRepository repository;
    private Simulation simulation1;
    private Simulation simulation2;
    private Scenario scenario;
    private Map map;
    private Size size;
    private TimeDate timeDate;

    /**
     * Sets up a new repository and sample Simulations before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new SimulationRepository();
        size = new Size(1, 1);
        map = new Map("TestMap", size);
        timeDate = new TimeDate(2024, 1, 1); // Example valid date (year, month, day)
        scenario = new Scenario(map, "TestScenario", 100, timeDate, null);
        simulation1 = new Simulation("Sim1", scenario);
        simulation2 = new Simulation("Sim2", scenario);
    }

    /**
     * Tests adding a Simulation successfully.
     */
    @Test
    void testAddSimulationSuccess() {
        assertTrue(repository.addSimulation(simulation1), "Should add simulation successfully");
        assertTrue(repository.simulationExists(simulation1), "Repository should contain the added simulation");
    }

    /**
     * Tests that adding a null Simulation throws IllegalArgumentException.
     */
    @Test
    void testAddSimulationNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.addSimulation(null), "Adding null simulation should throw IllegalArgumentException");
    }

    /**
     * Tests that adding a Simulation with a duplicate name returns false.
     */
    @Test
    void testAddSimulationDuplicateName() {
        repository.addSimulation(simulation1);
        Simulation duplicate = new Simulation("Sim1", scenario);
        assertFalse(repository.addSimulation(duplicate), "Should not add simulation with duplicate name");
    }

    /**
     * Tests removing an existing Simulation.
     */
    @Test
    void testRemoveSimulationSuccess() {
        repository.addSimulation(simulation1);
        assertTrue(repository.removeSimulation(simulation1), "Should remove simulation successfully");
        assertFalse(repository.simulationExists(simulation1), "Repository should not contain the removed simulation");
    }

    /**
     * Tests removing a Simulation that does not exist returns false.
     */
    @Test
    void testRemoveSimulationNotExists() {
        assertFalse(repository.removeSimulation(simulation1), "Should return false when removing non-existent simulation");
    }

    /**
     * Tests removing a null Simulation throws IllegalArgumentException.
     */
    @Test
    void testRemoveSimulationNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeSimulation(null), "Removing null simulation should throw IllegalArgumentException");
    }

    /**
     * Tests retrieving a simulation by name (case-insensitive).
     */
    @Test
    void testGetSimulationByName() {
        repository.addSimulation(simulation1);
        assertEquals(simulation1, repository.getSimulationByName("Sim1"));
        assertEquals(simulation1, repository.getSimulationByName("sim1"));
        assertNull(repository.getSimulationByName("NonExistent"));
    }

    /**
     * Tests getSimulationCount returns correct number.
     */
    @Test
    void testGetSimulationCount() {
        assertEquals(0, repository.getSimulationCount());
        repository.addSimulation(simulation1);
        assertEquals(1, repository.getSimulationCount());
        repository.addSimulation(simulation2);
        assertEquals(2, repository.getSimulationCount());
    }

    /**
     * Tests getAllSimulations returns the correct list.
     */
    @Test
    void testGetAllSimulations() {
        assertTrue(repository.getAllSimulations().isEmpty());
        repository.addSimulation(simulation1);
        repository.addSimulation(simulation2);
        List<Simulation> all = repository.getAllSimulations();
        assertTrue(all.contains(simulation1));
        assertTrue(all.contains(simulation2));
    }

    /**
     * Tests simulationExists method.
     */
    @Test
    void testSimulationExists() {
        assertFalse(repository.simulationExists(simulation1));
        repository.addSimulation(simulation1);
        assertTrue(repository.simulationExists(simulation1));
        // Different object, same name
        Simulation simCopy = new Simulation("Sim1", scenario);
        assertTrue(repository.simulationExists(simCopy));
    }

    /**
     * Tests simulationNameExists method (case-insensitive).
     */
    @Test
    void testSimulationNameExists() {
        assertFalse(repository.simulationNameExists("Sim1"));
        repository.addSimulation(simulation1);
        assertTrue(repository.simulationNameExists("Sim1"));
        assertTrue(repository.simulationNameExists("sim1"));
        assertFalse(repository.simulationNameExists("Other"));
    }

    /**
     * Tests getSimulations and setSimulations methods.
     */
    @Test
    void testGetAndSetSimulations() {
        List<Simulation> sims = new ArrayList<>();
        sims.add(simulation1);
        repository.setSimulations(sims);
        assertEquals(sims, repository.getSimulations());
    }

    /**
     * Tests getLastSimulation returns the last added simulation or null if empty.
     */
    @Test
    void testGetLastSimulation() {
        assertNull(repository.getLastSimulation());
        repository.addSimulation(simulation1);
        assertEquals(simulation1, repository.getLastSimulation());
        repository.addSimulation(simulation2);
        assertEquals(simulation2, repository.getLastSimulation());
    }

    /**
     * Tests clear method removes all simulations.
     */
    @Test
    void testClear() {
        repository.addSimulation(simulation1);
        repository.addSimulation(simulation2);
        repository.clear();
        assertEquals(0, repository.getSimulationCount());
        assertTrue(repository.getAllSimulations().isEmpty());
    }
}
