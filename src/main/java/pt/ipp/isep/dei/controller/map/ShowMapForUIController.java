package pt.ipp.isep.dei.controller.map;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

/**
 * Controller responsible for displaying map information.
 * Provides methods to select, retrieve, and list maps from the repository.
 */
public class ShowMapForUIController {

    /**
     * The repository that stores map entities.
     */
    private MapRepository mapRepository;

    /**
     * The currently selected map.
     */
    private Map actualMap;

    /**
     * Constructs a ShowMapForUIController and initializes the map repository.
     */
    public ShowMapForUIController() {
        initializeMapRepository();
    }

    /**
     * Initializes the map repository if it is not already set.
     */
    private void initializeMapRepository() {
        if (mapRepository == null) {
            Repositories repositories = Repositories.getInstance();
            mapRepository = repositories.getMapRepository();
        }
    }

    /**
     * Gets the currently selected map.
     *
     * @return the current map, or null if not set
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the currently selected map.
     *
     * @param actualMap the map to set as current
     * @throws IllegalArgumentException if the map is null
     */
    public void setActualMap(Map actualMap) throws IllegalArgumentException {
        this.actualMap = actualMap;
    }

    /**
     * Gets the ID of the current map.
     *
     * @return the map ID, or 0 if not set
     */
    public int getIdMap() {
        if (actualMap != null) {
            return actualMap.getId();
        }
        return 0;
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
     * Lists all maps in the repository.
     *
     * @return a list of all maps
     */
    public List<Map> listMaps() {
        return mapRepository.getAllMaps();
    }

    /**
     * Returns a string representation of the current map.
     *
     * @return the map's string representation, or a message if no map is selected
     */
    @Override
    public String toString() {
        if (actualMap == null) {
            return "No map selected";
        }
        return actualMap.toString();
    }
}