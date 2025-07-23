package pt.ipp.isep.dei.controller.map;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.io.File;
import java.util.List;

/**
 * Controller responsible for deleting maps from the repository.
 * Provides methods to select, retrieve, and delete maps, as well as to remove associated scenario and simulation files.
 */
public class DeleteMapController {

    private MapRepository mapRepository;
    private Map actualMap;

    private static final String PATH_DIRECTORY_MAP = "data/Map/";
    private static final String PATH_DIRECTORY_SCENARIO = "data/Scenario/";
    private static final String PATH_DIRECTORY_SIMULATION = "data/Simulation/";
    private static final String EXTENSION = ".ser";

    /**
     * Constructs the controller and initializes the map repository.
     */
    public DeleteMapController() {
        initializeMapRepository();
    }

    /**
     * Initializes the map repository from the global repositories if not already set.
     */
    private void initializeMapRepository() {
        if (mapRepository == null) {
            Repositories repositories = Repositories.getInstance();
            mapRepository = repositories.getMapRepository();
        }
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
     * @return the map name, or null if not set
     */
    public String getNameMap() {
        return actualMap != null ? actualMap.getName() : null;
    }

    /**
     * Deletes the currently selected map from the repository and removes associated files.
     *
     * @throws IllegalStateException if no map is selected for deletion or if file deletion fails
     */
    public void deleteMap() throws IllegalStateException {
        if (actualMap == null) {
            throw new IllegalStateException("No map selected for deletion");
        }
        int idMap = actualMap.getId();
        if (mapRepository.existsMapWithID(idMap)) {
            mapRepository.removeMap(idMap);
            deleteSimulationFiles();
            deleteScenarioFiles();
            deleteMapFile();
        }
    }

    /**
     * Deletes the map file associated with the currently selected map.
     *
     * @throws IllegalStateException if the file cannot be deleted
     */
    private void deleteMapFile() {
        File file = new File(PATH_DIRECTORY_MAP + actualMap.getName() + EXTENSION);
        if (!file.delete()) {
            throw new IllegalStateException("Failed to delete map file: " + file.getAbsolutePath());
        }
    }

    /**
     * Deletes scenario files associated with the currently selected map.
     *
     * @throws IllegalStateException if any scenario file cannot be deleted
     */
    private void deleteScenarioFiles() {
        File directoryScenario = new File(PATH_DIRECTORY_SCENARIO);
        File[] scenarioFiles = directoryScenario.listFiles();
        if (scenarioFiles != null) {
            for (File scenarioFile : scenarioFiles) {
                if (scenarioFile.getName().contains(actualMap.getName())) {
                    if (!scenarioFile.delete()) {
                        throw new IllegalStateException("Failed to delete scenario file: " + scenarioFile.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * Deletes simulation files associated with the scenarios of the currently selected map.
     *
     * @throws IllegalStateException if any simulation file cannot be deleted
     */
    private void deleteSimulationFiles() {
        File directoryScenario = new File(PATH_DIRECTORY_SCENARIO);
        File[] scenarioFiles = directoryScenario.listFiles();
        if (scenarioFiles != null) {
            for (File scenarioFile : scenarioFiles) {
                File directorySimulation = new File(PATH_DIRECTORY_SIMULATION);
                File[] simulationFiles = directorySimulation.listFiles();
                if (simulationFiles != null) {
                    for (File simulationFile : simulationFiles) {
                        if (simulationFile.getName().contains(scenarioFile.getName())) {
                            if (!simulationFile.delete()) {
                                throw new IllegalStateException("Failed to delete simulation file: " + simulationFile.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if there are any active maps in the repository.
     *
     * @return true if there are active maps, false otherwise
     */
    public boolean thereAreActiveMaps() {
        return (mapRepository.nActiveMaps() != 0);
    }

    /**
     * Gets the list of all maps in the repository.
     *
     * @return the list of maps
     */
    public List<Map> listMaps() {
        return mapRepository.getAllMaps();
    }

    /**
     * Gets the map repository.
     *
     * @return the map repository
     */
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    /**
     * Sets the map repository.
     *
     * @param mapRepository the map repository to set
     */
    public void setMapRepository(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }
}