package pt.ipp.isep.dei.controller.scenario;

import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

/**
 * Controller responsible for displaying scenarios and their details.
 * Provides methods to access scenario and map information, as well as their resources and industries.
 */
public class ShowScenariosController {

    private MapRepository mapRepository;
    private Map actualMap;
    private Scenario actualScenario;

    /**
     * Constructs the controller and initializes the repositories.
     */
    public ShowScenariosController() {
        initializeRepositories();
    }

    /**
     * Initializes the map repository from the global repositories if not already set.
     */
    private void initializeRepositories() {
        if (mapRepository == null) {
            Repositories repositories = Repositories.getInstance();
            mapRepository = repositories.getMapRepository();
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

    /**
     * Gets the currently selected map.
     *
     * @return the actual map
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the currently selected map.
     *
     * @param actualMap the map to set as current
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
    }

    /**
     * Checks if there are any scenarios in the current map.
     *
     * @return true if there are scenarios, false otherwise
     */
    public boolean thereAreScenarios() {
        if (actualMap != null) {
            return !actualMap.getScenarios().isEmpty();
        }
        return false;
    }

    /**
     * Gets the currently selected scenario.
     *
     * @return the actual scenario
     */
    public Scenario getActualScenario() {
        return actualScenario;
    }

    /**
     * Sets the currently selected scenario.
     *
     * @param actualScenario the scenario to set as current
     */
    public void setActualScenario(Scenario actualScenario) {
        this.actualScenario = actualScenario;
    }

    /**
     * Gets the name of the current scenario.
     *
     * @return the scenario name, or null if not set
     */
    public String getNameScenario() {
        if (actualScenario != null) {
            return actualScenario.getName();
        }
        return null;
    }

    /**
     * Gets the name of the current map.
     *
     * @return the map name, or null if not set
     */
    public String getNameMap() {
        if (actualMap != null) {
            return actualMap.getName();
        }
        return null;
    }

    /**
     * Gets the initial money of the current scenario.
     *
     * @return the initial money, or 0 if scenario is not set
     */
    public int getInitialMoney() {
        if (actualScenario != null) {
            return actualScenario.getInitialMoney();
        }
        return 0;
    }

    /**
     * Gets the beginning date of the current scenario as a string.
     *
     * @return the beginning date, or null if scenario is not set
     */
    public String getBeginningDate() {
        if (actualScenario != null) {
            return actualScenario.getBeginningDate().toString();
        }
        return null;
    }

    /**
     * Gets the end date of the current scenario as a string.
     *
     * @return the end date, or null if scenario is not set
     */
    public String getEndDate() {
        if (actualScenario != null) {
            return actualScenario.getEndDate().toString();
        }
        return null;
    }

    /**
     * Gets the list of resource types in the current scenario.
     *
     * @return the list of resource types, or null if scenario is not set
     */
    public List<ResourcesType> getResourcesList() {
        if (actualScenario != null) {
            return actualScenario.getTypeResourceList();
        }
        return null;
    }

    /**
     * Gets the list of house block resources in the current scenario.
     *
     * @return the list of house block resources, or null if scenario is not set
     */
    public List<HouseBlockResource> getHouseBlocksResourceList() {
        if (actualScenario != null) {
            return actualScenario.getHouseBlockResourceList();
        }
        return null;
    }

    /**
     * Gets the list of industries in the current scenario.
     *
     * @return the list of industries, or null if scenario is not set
     */
    public List<Industry> getIndustriesList() {
        if (actualScenario != null) {
            return actualScenario.getIndustriesListInScenario();
        }
        return null;
    }
}