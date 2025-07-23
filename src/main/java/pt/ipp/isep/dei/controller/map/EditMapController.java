package pt.ipp.isep.dei.controller.map;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

/**
 * Controller responsible for editing map entities.
 * Provides methods to set and get map properties, as well as to list and check available maps.
 */
public class EditMapController {

    private MapRepository mapRepository;
    private Map actualMap;

    /**
     * Constructs an EditMapController and initializes the map repository.
     */
    public EditMapController() {
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
        if (actualMap == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        this.actualMap = actualMap;
    }

    /**
     * Sets a new name for the current map.
     *
     * @param newName the new name to set
     * @throws IllegalStateException if no map is selected
     * @throws IllegalArgumentException if the new name is null or empty
     */
    public void setNewName(String newName) throws IllegalStateException, IllegalArgumentException {
        if (actualMap == null) {
            throw new IllegalStateException("No map selected for editing");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Map name cannot be null or empty");
        }
        actualMap.setName(newName);
    }

    /**
     * Checks if a map name already exists in the repository.
     *
     * @param actualName the name to check
     * @return true if the name exists, false otherwise
     */
    public boolean alreadyExistNameMapInMapRepository(String actualName) {
        for (Map existingMap : mapRepository.getAllMaps()) {
            if (existingMap.getName().equals(actualName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the name of the current map.
     *
     * @return the map name, or null if not set
     */
    public String getName() {
        return actualMap != null ? actualMap.getName() : null;
    }

    /**
     * Sets a new size for the current map.
     *
     * @param newWidth  the new width
     * @param newHeight the new height
     * @throws IllegalStateException if no map is selected
     * @throws IllegalArgumentException if width or height are not positive
     */
    public void setNewSize(int newWidth, int newHeight) throws IllegalStateException, IllegalArgumentException {
        if (actualMap == null) {
            throw new IllegalStateException("No map selected for editing");
        }
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("Map dimensions must be positive values");
        }
        Size newSize = new Size(newWidth, newHeight);
        actualMap.setPixelSize(newSize);
    }

    /**
     * Gets the size of the current map.
     *
     * @return the map size, or null if not set
     */
    public Size getSize() {
        return actualMap != null ? actualMap.getPixelSize() : null;
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

    /**
     * Gets the map repository instance.
     *
     * @return the map repository
     */
    public MapRepository getMapRepositoryInstance() {
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