package pt.ipp.isep.dei.controller.scenario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.nio.file.Path;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DeleteScenarioController} class.
 */
class DeleteScenarioControllerTest {
    private DeleteScenarioController controller;
    private Map map;
    private Scenario scenario;
    private Size size;
    private TimeDate start;
    private TimeDate end;

    @TempDir
    Path tempDir;

    /**
     * Sets up the test environment with a sample map and scenario before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new DeleteScenarioController();
        size = new Size(10, 10);
        map = new Map("MapaTeste", size);
        start = new TimeDate(2024, 1, 1);
        end = new TimeDate(2024, 12, 31);
        scenario = new Scenario(map, "CenarioTeste", 0, start, end);
    }

    /**
     * Verifies that setting a null map throws an exception.
     */
    @Test
    void testSetActualMapNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> controller.setActualMap(null));
    }

    /**
     * Tests setting and retrieving the actual map.
     */
    @Test
    void testSetAndGetActualMap() {
        controller.setActualMap(map);
        assertEquals(map, controller.getActualMap());
    }

    /**
     * Tests setting and retrieving the actual scenario.
     */
    @Test
    void testSetAndGetActualScenario() {
        controller.setActualScenario(scenario);
        assertEquals("CenarioTeste", controller.getNameScenario());
    }

    /**
     * Tests setActualScenario with null argument throws exception.
     */
    @Test
    void testSetActualScenarioNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> controller.setActualScenario(null));
    }

    /**
     * Tests getActualMap and getActualScenario when not set (should return null).
     */
    @Test
    void testGetActualMapAndScenarioNull() {
        assertNull(controller.getActualMap());
        // Não existe getActualScenario no controller, então não testar
    }

    /**
     * Verifies that thereAreScenarios returns false when empty, and true when a scenario is added.
     */
    @Test
    void testThereAreScenariosFalseAndTrue() {
        controller.setActualMap(map);
        assertFalse(controller.thereAreScenarios());
        map.addScenario(scenario);
        assertTrue(controller.thereAreScenarios());
    }

    /**
     * Tests the behaviour of getNameMap and getNameScenario when none are set.
     */
    @Test
    void testGetNameMapAndScenarioNulls() {
        assertNull(controller.getNameMap());
        assertNull(controller.getNameScenario());
    }

    /**
     * Tests getNameScenario returns null if scenario is not set.
     */
    @Test
    void testGetNameScenarioNull() {
        assertNull(controller.getNameScenario());
    }

    /**
     * Tests getNameMap returns null if map is not set.
     */
    @Test
    void testGetNameMapNull() {
        assertNull(controller.getNameMap());
    }

    /**
     * Tests getNameMap and getNameScenario when both are properly set.
     */
    @Test
    void testGetNameMapAndScenarioFilled() {
        controller.setActualMap(map);
        controller.setActualScenario(scenario);
        assertEquals("MapaTeste", controller.getNameMap());
        assertEquals("CenarioTeste", controller.getNameScenario());
    }

    /**
     * Ensures that attempting to delete a scenario when not set throws an exception.
     */
    @Test
    void testDeleteScenarioNullsThrows() {
        assertThrows(IllegalArgumentException.class, () -> controller.deleteScenario());
    }

    /**
     * Tests deleteScenario when scenario name or map name is null (should throw exception).
     */
    @Test
    void testDeleteScenarioWithNullNamesThrows() {
        controller.setActualMap(map);
        Scenario s = new Scenario(map, null, 0, start, end);
        controller.setActualScenario(s);
        map.addScenario(s);
        assertThrows(IllegalArgumentException.class, () -> controller.deleteScenario());
    }

    /**
     * Tests deleteScenario when scenario is not present in the map (should not throw).
     */
    @Test
    void testDeleteScenarioNotInMap() {
        controller.setActualMap(map);
        controller.setActualScenario(scenario);
        // Not added to map
        assertDoesNotThrow(() -> controller.deleteScenario());
    }

    /**
     * Tests successful deletion of scenario and simulation files.
     */
    @Test
    void testDeleteScenarioSuccess() throws IOException {
        File scenarioDir = tempDir.resolve("Scenario").toFile();
        File simulationDir = tempDir.resolve("Simulation").toFile();
        scenarioDir.mkdir();
        simulationDir.mkdir();

        String scenarioFileName = "CenarioTeste-MapaTeste.ser";
        File scenarioFile = new File(scenarioDir, scenarioFileName);
        assertTrue(scenarioFile.createNewFile());

        String simulationFileName = "Test_Simulation-CenarioTeste.ser";
        File simulationFile = new File(simulationDir, simulationFileName);
        assertTrue(simulationFile.createNewFile());

        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SCENARIO", scenarioDir.getAbsolutePath() + "/");
        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SIMULATION", simulationDir.getAbsolutePath() + "/");

        controller.setActualMap(map);
        controller.setActualScenario(scenario);
        map.addScenario(scenario);

        controller.deleteScenario();

        assertFalse(scenarioFile.exists());
        assertFalse(simulationFile.exists());
    }

    /**
     * Tests deletion when the scenario file cannot be removed.
     */
    @Test
    void testDeleteScenarioFileFails() throws IOException {
        File scenarioDir = tempDir.resolve("Scenario").toFile();
        scenarioDir.mkdir();

        String scenarioFileName = "CenarioTeste-MapaTeste.ser";
        File scenarioFile = new File(scenarioDir, scenarioFileName);
        assertTrue(scenarioFile.createNewFile());

        scenarioFile.setWritable(false); // Prevent deletion

        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SCENARIO", scenarioDir.getAbsolutePath() + "/");
        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SIMULATION", tempDir.resolve("Simulation").toFile().getAbsolutePath() + "/");

        controller.setActualMap(map);
        controller.setActualScenario(scenario);
        map.addScenario(scenario);

        Exception ex = assertThrows(IllegalStateException.class, () -> controller.deleteScenario());
        assertTrue(ex.getMessage().contains("Failed to delete scenario file"));

        scenarioFile.setWritable(true); // Cleanup
    }

    /**
     * Tests deletion when the simulation file cannot be removed.
     */
    @Test
    void testDeleteSimulationFileFails() throws IOException {
        File scenarioDir = tempDir.resolve("Scenario").toFile();
        File simulationDir = tempDir.resolve("Simulation").toFile();
        scenarioDir.mkdir();
        simulationDir.mkdir();

        String scenarioFileName = "CenarioTeste-MapaTeste.ser";
        File scenarioFile = new File(scenarioDir, scenarioFileName);
        assertTrue(scenarioFile.createNewFile());

        String simulationFileName = "Test_Simulation-CenarioTeste.ser";
        File simulationFile = new File(simulationDir, simulationFileName);
        assertTrue(simulationFile.createNewFile());

        simulationFile.setWritable(false); // Prevent deletion

        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SCENARIO", scenarioDir.getAbsolutePath() + "/");
        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SIMULATION", simulationDir.getAbsolutePath() + "/");

        controller.setActualMap(map);
        controller.setActualScenario(scenario);
        map.addScenario(scenario);

        Exception ex = assertThrows(IllegalStateException.class, () -> controller.deleteScenario());
        assertTrue(ex.getMessage().contains("Failed to delete simulation file"));

        simulationFile.setWritable(true); // Cleanup
    }

    /**
     * Tests deletion when the simulation directory path is invalid or contains no files.
     */
    @Test
    void testDeleteSimulationFilesNullArray() {
        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SCENARIO", "invalid/Scenario/");
        setStaticField(DeleteScenarioController.class, "PATH_DIRECTORY_SIMULATION", "invalid/Simulation/");
        controller.setActualMap(map);
        controller.setActualScenario(scenario);
        map.addScenario(scenario);

        // Should not throw an exception even if directory is invalid
        controller.deleteScenario();
    }

    /**
     * Utility method to change final static fields via reflection.
     *
     * @param clazz     The class containing the static field
     * @param fieldName The name of the static field to change
     * @param value     The new value to assign
     */
    private static void setStaticField(Class<?> clazz, String fieldName, String value) {
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            java.lang.reflect.Field modifiersField = java.lang.reflect.Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
            field.set(null, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
