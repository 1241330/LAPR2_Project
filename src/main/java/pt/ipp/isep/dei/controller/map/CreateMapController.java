package pt.ipp.isep.dei.controller.map;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for creating maps and managing the map repository.
 */
public class CreateMapController {

    /** The map repository used to store and retrieve maps. */
    private MapRepository mapRepository;

    /** The map instance created or managed by this controller. */
    private Map map;

    /** The name of the map to be created. */
    private String nameMap;

    /** The width of the map to be created. */
    private int widthMap;

    /** The height of the map to be created. */
    private int heightMap;

    /** Default prefix for map names when creating maps with default names. */
    private static final String OMISSION_NAME_MAP = "Map_";

    /**
     * Constructs the controller and initializes the map repository.
     */
    public CreateMapController() {
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
     * Creates a new map with the specified name, width, and height.
     *
     * @throws IllegalArgumentException if the map name is null or empty, or if the dimensions are not positive values
     */
    public void createMap() throws IllegalArgumentException {
        if (nameMap == null || nameMap.trim().isEmpty()) {
            throw new IllegalArgumentException("Map name cannot be null or empty");
        }
        if (widthMap <= 0 || heightMap <= 0) {
            throw new IllegalArgumentException("Map dimensions must be positive values");
        }
        Size size = new Size(widthMap, heightMap);
        map = new Map(nameMap, size);
        mapRepository.addMap(map);
    }

    /**
     * Creates a new map with a default name and the specified width and height.
     * The name will be unique within the repository.
     *
     * @throws IllegalArgumentException if the dimensions are not positive values
     */
    public void createMapDefault() throws IllegalArgumentException {
        if (widthMap <= 0 || heightMap <= 0) {
            throw new IllegalArgumentException("Map dimensions must be positive values");
        }
        Size size = new Size(widthMap, heightMap);
        String name;
        int counter = 1;
        do {
            name = OMISSION_NAME_MAP + counter;
            counter++;
        } while (alreadyExistNameMapInMapRepository(name));
        map = new Map(name, size);
        mapRepository.addMap(map);
    }

    /**
     * Checks if a map with the given name already exists in the map repository.
     *
     * @param actualName the name to check
     * @return true if a map with the given name exists, false otherwise
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
     * Returns the name of the map to be created.
     *
     * @return the map name
     */
    public String getNameMap() {
        return nameMap;
    }

    /**
     * Sets the name of the map to be created.
     *
     * @param nameMap the map name to set
     */
    public void setNameMap(String nameMap) {
        this.nameMap = nameMap;
    }

    /**
     * Returns the width of the map to be created.
     *
     * @return the map width
     */
    public int getWidthMap() {
        return widthMap;
    }

    /**
     * Sets the width of the map to be created.
     *
     * @param widthMap the map width to set
     */
    public void setWidthMap(int widthMap) {
        this.widthMap = widthMap;
    }

    /**
     * Returns the height of the map to be created.
     *
     * @return the map height
     */
    public int getHeightMap() {
        return heightMap;
    }

    /**
     * Sets the height of the map to be created.
     *
     * @param heightMap the map height to set
     */
    public void setHeightMap(int heightMap) {
        this.heightMap = heightMap;
    }

    /**
     * Returns the map created by this controller.
     *
     * @return the created map, or null if no map was created
     */
    public Map getMapCreated() {
        return map;
    }

    /**
     * Returns the map repository used by this controller.
     *
     * @return the map repository
     */
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    /**
     * Sets the map repository for this controller.
     *
     * @param mapRepository the map repository to set
     */
    public void setMapRepository(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    /**
     * Returns the current map instance.
     *
     * @return the current map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Sets the current map instance.
     *
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }
}