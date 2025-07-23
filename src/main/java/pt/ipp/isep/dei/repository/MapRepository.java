package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Map.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Map entities.
 * Provides methods to add, retrieve, remove, and check the existence of maps.
 */
public class MapRepository implements Serializable {

    /**
     * Static list that stores all maps.
     */
    private static List<Map> maps = new ArrayList<>();

    /**
     * Constructs an empty MapRepository.
     */
    public MapRepository() {
        // Default constructor
    }

    /**
     * Adds a map to the repository.
     *
     * @param map The map to add.
     * @throws IllegalArgumentException if the map is null.
     */
    public void addMap(Map map) throws IllegalArgumentException {
        if (map == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        maps.add(map);
    }

    /**
     * Retrieves a map by its unique identifier.
     *
     * @param id The ID of the map.
     * @return The map with the specified ID, or null if not found.
     */
    public Map getMapById(int id) {
        for (Map map : maps) {
            if (map.getId() == id) {
                return map;
            }
        }
        return null;
    }

    /**
     * Retrieves the total number of maps in the repository.
     *
     * @return The number of maps.
     */
    public int getMapCount() {
        return maps.size();
    }

    /**
     * Retrieves a list of all maps in the repository.
     *
     * @return A list of maps.
     */
    public List<Map> getAllMaps() {
        return maps;
    }

    /**
     * Removes a map from the repository by its ID.
     *
     * @param id The ID of the map to remove.
     */
    public void removeMap(int id) {
        Map mapToRemove = getMapById(id);
        if (mapToRemove != null) {
            maps.remove(mapToRemove);
        }
    }

    /**
     * Checks if a map with a specific ID exists in the repository.
     *
     * @param id The ID to check.
     * @return True if a map with the ID exists, false otherwise.
     */
    public boolean existsMapWithID(int id) {
        for (Map map : maps) {
            if (map.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a map with a specific name exists in the repository.
     *
     * @param name The name to check.
     * @return True if a map with the name exists, false otherwise.
     */
    public boolean existsMapWithName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        for (Map map : maps) {
            if (map.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the ID of a map by its name.
     *
     * @param name The name of the map.
     * @return The ID of the map, or -1 if not found.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    public int getIdByName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Map name cannot be null or empty");
        }
        String searchName = name.trim().toLowerCase();
        for (Map map : maps) {
            if (map.getName().trim().toLowerCase().equals(searchName)) {
                return map.getId();
            }
        }
        return -1;
    }

    /**
     * Finds a map by its name.
     *
     * @param name The name of the map.
     * @return The map with the specified name, or null if not found.
     */
    public Map findMapByName(String name) {
        int id = getIdByName(name);
        return id == -1 ? null : getMapById(id);
    }

    /**
     * Retrieves the number of active maps in the repository.
     *
     * @return The number of active maps.
     */
    public int nActiveMaps() {
        return maps.size();
    }

    /**
     * Gets the static list of maps.
     *
     * @return The list of maps.
     */
    public static List<Map> getMaps() {
        return maps;
    }

    /**
     * Sets the static list of maps.
     *
     * @param maps The list of maps to set.
     */
    public static void setMaps(List<Map> maps) {
        MapRepository.maps = maps;
    }
}