package pt.ipp.isep.dei.controller.editor;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

/**
 * Controller responsible for editing maps.
 * Provides methods to manage the current map, list all maps,
 * and check for active maps in the repository.
 */
public class EditorController {

    /**
     * Repository for accessing map data.
     */
    private MapRepository mapRepository;

    /**
     * The currently selected map.
     */
    private Map actualMap;

    /**
     * Constructs an EditorController and initializes the map repository.
     */
    public EditorController() {
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
     * Checks if there are any active maps in the repository.
     *
     * @return {@code true} if there is at least one active map, {@code false} otherwise
     */
    public boolean thereAreActiveMaps() {
        return (mapRepository.nActiveMaps() != 0);
    }

    /**
     * Gets the currently selected map.
     *
     * @return the selected map, or {@code null} if none is selected
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the currently selected map.
     *
     * @param actualMap the map to set as current
     * @throws IllegalArgumentException if the passed map is {@code null}
     */
    public void setActualMap(Map actualMap) throws IllegalArgumentException {
        if (actualMap == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        this.actualMap = actualMap;
    }

    /**
     * Gets the ID of the currently selected map.
     *
     * @return the ID of the current map, or 0 if no map is selected
     */
    public int getIdMap() {
        if (actualMap != null) {
            return actualMap.getId();
        }
        return 0;
    }

    /**
     * Lists all maps available in the repository.
     *
     * @return a list of all maps
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

    /**
     * Returns a string representation of the controller.
     *
     * @return description of the current map or "No map selected" if none is selected
     */
    @Override
    public String toString() {
        if (actualMap == null) {
            return "No map selected";
        }
        return actualMap.toString();
    }
}