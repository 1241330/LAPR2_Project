package pt.ipp.isep.dei.controller.scenario;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;

import java.io.File;

/**
 * Controller responsible for deleting scenarios from a map and removing their associated files.
 * Provides methods to select, retrieve, and delete scenarios.
 */
public class DeleteScenarioController {

    private Map actualMap;
    private Scenario actualScenario;
    private static final String PATH_DIRECTORY_SCENARIO = "data/Scenario/";
    private static final String PATH_DIRECTORY_SIMULATION = "data/Simulation/";
    private static final String EXTENSION = ".ser";

    /**
     * Default constructor for DeleteScenarioController.
     */
    public DeleteScenarioController() {
        // No initialization required
    }

    /**
     * Checks if there are any scenarios in the current map.
     *
     * @return true if the current map has scenarios, false otherwise
     */
    public boolean thereAreScenarios() {
        if (actualMap != null) {
            return !actualMap.getScenarios().isEmpty();
        }
        return false;
    }

    /**
     * Sets the currently selected scenario for deletion.
     *
     * @param actualScenario the scenario to set as current
     */
    public void setActualScenario(Scenario actualScenario) {
        this.actualScenario = actualScenario;
    }

    /**
     * Sets the currently selected map for deletion.
     *
     * @param actualMap the map to set as current
     * @throws IllegalArgumentException if the map is null
     */
    public void setActualMap(Map actualMap) throws IllegalArgumentException {
        if (actualMap == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        this.actualMap = actualMap;
    }

    /**
     * Gets the currently selected map.
     *
     * @return the actual map
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Gets the name of the currently selected map.
     *
     * @return the name of the map, or null if not set
     */
    public String getNameMap() {
        if (actualMap != null) {
            return actualMap.getName();
        }
        return null;
    }

    /**
     * Gets the name of the currently selected scenario.
     *
     * @return the name of the scenario, or null if not set
     */
    public String getNameScenario() {
        if (actualScenario != null) {
            return actualScenario.getName();
        }
        return null;
    }

    /**
     * Deletes the currently selected scenario from the map and removes its files.
     *
     * @throws IllegalArgumentException if the map or scenario is not set
     * @throws IllegalStateException if file deletion fails
     */
    public void deleteScenario() {
        if (actualMap == null || actualScenario == null) {
            throw new IllegalArgumentException("Map or scenario cannot be null");
        }
        actualMap.removeScenario(actualScenario);
        deleteSimulationFiles();
        deleteScenarioFile();
    }

    /**
     * Deletes the scenario file from the scenario directory.
     *
     * @throws IllegalStateException if the file cannot be deleted
     */
    private void deleteScenarioFile() {
        File file = new File(PATH_DIRECTORY_SCENARIO + getNameScenario() + "-" + getNameMap() + EXTENSION);
        if (!file.delete()) {
            throw new IllegalStateException("Failed to delete scenario file: " + file.getAbsolutePath());
        }
    }

    /**
     * Deletes all simulation files related to the current scenario from the simulation directory.
     *
     * @throws IllegalStateException if any file cannot be deleted
     */
    private void deleteSimulationFiles() {
        File directorySimulation = new File(PATH_DIRECTORY_SIMULATION);
        File[] simulationFiles = directorySimulation.listFiles();
        if (simulationFiles != null) {
            for (File simulationFile : simulationFiles) {
                if (simulationFile.getName().contains(actualScenario.getName())) {
                    if (!simulationFile.delete()) {
                        throw new IllegalStateException("Failed to delete simulation file: " + simulationFile.getAbsolutePath());
                    }
                }
            }
        }
    }
}