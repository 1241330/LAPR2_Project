package pt.ipp.isep.dei.controller.simulation.SimulationRelatedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.SimulationRelated.DeleteSimulationController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DeleteSimulationController class.
 * This class tests all public methods and possible scenarios.
 */
public class DeleteSimulationControllerTest {

    private DeleteSimulationController controller;
    private Scenario scenario;
    private Simulation simulation;
    private Size size;
    private Map map;
    private TimeDate date;
    private File fileToDelete;

    @BeforeEach
    void setUp() {
        controller = new DeleteSimulationController();
        this.size = new Size(10, 10);
        this.map = new Map("TestMap", this.size);
        this.date = new TimeDate(2024, 1, 1);
        this.scenario = new Scenario(this.map, "TestScenario", 1000, this.date, this.date);
        this.simulation = new Simulation("SimToDelete", this.scenario);
        this.scenario.addSimulation(this.simulation);
        // Cria o ficheiro simulado para deletar
        String path = "data/Simulation/SimToDelete-TestScenario.ser";
        this.fileToDelete = new File(path);
        try {
            this.fileToDelete.getParentFile().mkdirs();
            this.fileToDelete.createNewFile();
        } catch (Exception ignored) {}
    }

    /**
     * Test setActualSimulation and getScenarioOfSimulation methods.
     */
    @Test
    void testSetAndGetScenarioOfSimulation() {
        controller.setActualSimulation(simulation);
        assertEquals(scenario, controller.getScenarioOfSimulation());
    }

    /**
     * Test deleteSimulation removes simulation from scenario and deletes file.
     */
    @Test
    void testDeleteSimulationSuccess() {
        controller.setActualSimulation(simulation);
        assertTrue(scenario.getSimulations().contains(simulation));
        assertTrue(fileToDelete.exists());
        controller.deleteSimulation();
        assertFalse(scenario.getSimulations().contains(simulation));
        assertFalse(fileToDelete.exists());
    }

    /**
     * Test deleteSimulation throws exception if no simulation is set.
     */
    @Test
    void testDeleteSimulationNoSimulationSet() {
        assertThrows(IllegalStateException.class, () -> controller.deleteSimulation());
    }

    /**
     * Test deleteSimulation throws exception if file cannot be deleted.
     */
    @Test
    void testDeleteSimulationFileNotDeleted() {
        controller.setActualSimulation(simulation);
        // Simula um ficheiro que não pode ser apagado (diretório em vez de ficheiro)
        File fakeFile = new File("data/Simulation/SimToDelete-TestScenario.ser");
        fakeFile.delete();
        fakeFile.mkdir();
        try {
            assertThrows(IllegalStateException.class, () -> controller.deleteSimulation());
        } finally {
            fakeFile.delete();
        }
    }
}
