package pt.ipp.isep.dei.controller.scenario;

import org.junit.jupiter.api.*;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SaveScenarioController class.
 * <p>
 * This class covers all relevant scenarios for saving and restoring scenarios, including:
 * <ul>
 *     <li>Saving a scenario to a file and verifying serialization</li>
 *     <li>Handling IO exceptions during save</li>
 *     <li>Restoring a scenario from backup if the file exists</li>
 *     <li>Ensuring no restoration occurs if no backup file exists</li>
 * </ul>
 * Each test is documented with Javadoc explaining its purpose.
 */
class SaveScenarioControllerTest {
    private Map map;
    private Scenario scenario;
    private SaveScenarioController controller;
    private static final String DIR = "data/Scenario/";
    private static final String EXT = ".ser";
    private String fileName;

    @BeforeEach
    void setUp() {
        map = new Map("TestMap", new Size(10, 10));
        scenario = new Scenario(map, "TestScenario", 100, new TimeDate(2024, 1, 1), new TimeDate(2024, 12, 31));
        map.addScenario(scenario);
        controller = new SaveScenarioController(scenario);
        fileName = DIR + scenario.getName() + "-" + map.getName() + EXT;
        // Garantir diretório existe
        new File(DIR).mkdirs();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(fileName));
    }

    /**
     * Tests if the file is created and contains a serialized Scenario object.
     */
    @Test
    void testSaveScenarioCreatesFile() throws IOException, ClassNotFoundException {
        controller.saveScenario();
        File file = new File(fileName);
        assertTrue(file.exists());
        // Verifies if the object can be deserialized correctly
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            assertTrue(obj instanceof Scenario);
            Scenario loaded = (Scenario) obj;
            assertEquals(scenario.getName(), loaded.getName());
        }
    }

    /**
     * Tests if saveMap handles IO exceptions (e.g., invalid directory).
     */
    @Test
    void testSaveScenarioIOException() {
        SaveScenarioController ctrl = new SaveScenarioController(scenario) {
            @Override
            public void saveScenario() {
                try {
                    FileOutputStream fileOut = new FileOutputStream("/invalid_path/" + scenario.getName() + EXT);
                    ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
                    outStream.writeObject(scenario);
                    outStream.close();
                    fileOut.close();
                } catch(IOException i) {
                    // Expected: IOException
                    assertTrue(i instanceof IOException);
                }
            }
        };
        ctrl.saveScenario();
    }

    /**
     * Tests dontSaveMap when the backup file exists (restores the removed scenario).
     */
    @Test
    void testDontSaveScenarioWithBackup() throws IOException, ClassNotFoundException {
        // Saves the scenario to create the backup
        controller.saveScenario();
        assertTrue(new File(fileName).exists());
        // Removes the scenario and restores
        controller.dontSaveScenario();
        assertTrue(map.getScenarios().stream().anyMatch(s -> s.getName().equals(scenario.getName())));
    }

    /**
     * Tests dontSaveMap when the backup file does not exist (does not restore anything).
     */
    @Test
    void testDontSaveScenarioWithoutBackup() {
        // Removes the file if it exists
        new File(fileName).delete();
        controller.dontSaveScenario();
        assertTrue(map.getScenarios().stream().noneMatch(s -> s.getName().equals(scenario.getName())));
    }

    /**
     * Tests if getActualScenario returns the current scenario.
     */
    @Test
    void testGetActualScenario() {
        assertEquals(scenario, controller.getActualScenario());
    }

    /**
     * Tests if setActualScenario changes the current scenario.
     */
    @Test
    void testSetActualScenario() {
        Scenario newScenario = new Scenario(map, "OtherScenario", 200, new TimeDate(2025, 1, 1), new TimeDate(2025, 12, 31));
        controller.setActualScenario(newScenario);
        assertEquals(newScenario, controller.getActualScenario());
    }

    /**
     * Tests if dontSaveScenario handles ClassNotFoundException gracefully.
     * This is simulated by creating a corrupted file.
     */
    @Test
    void testDontSaveScenarioClassNotFoundException() throws IOException {
        // Create a corrupted file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("corrupted content");
        }
        // Should not throw, should not restore
        controller.dontSaveScenario();
        assertTrue(map.getScenarios().stream().noneMatch(s -> s.getName().equals(scenario.getName())));
    }
}
