package pt.ipp.isep.dei.controller.city;

import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.List;

/**
 * Controller responsible for managing the deletion of a city from a given map.
 * Provides methods to check for existing cities, retrieve them,
 * and remove a specific city from the map.
 */
public class DeleteCityController {

    // === Instance Variables ===

    /**
     * Repository for accessing map data.
     */
    private MapRepository mapRepository;

    /**
     * The current map where the city will be deleted.
     */
    private Map actualMap;

    // === Constructor ===

    /**
     * Constructs the controller for a specific map identified by its ID.
     *
     * @param idMap the ID of the map where the city should be removed
     */
    public DeleteCityController(int idMap) {
        initializeMapRepository();
        this.actualMap = mapRepository.getMapById(idMap);
    }

    // === Repository Initialization ===

    /**
     * Initializes the map repository from the global repositories if not already set.
     */
    private void initializeMapRepository() {
        if (this.mapRepository == null) {
            Repositories repositories = Repositories.getInstance();
            this.mapRepository = repositories.getMapRepository();
        }
    }

    // === City Checks and Retrieval ===

    /**
     * Checks whether the current map has any active cities.
     *
     * @return true if the city list is not empty, false otherwise
     */
    public boolean thereAreActiveCities() {
        return !actualMap.getCitiesList().isEmpty();
    }

    /**
     * Retrieves the list of all cities currently present in the map.
     *
     * @return a list of City objects
     */
    public List<City> getActualCities() {
        return actualMap.getCitiesList();
    }

    // === City Deletion ===

    /**
     * Attempts to remove the specified city from the map.
     * Displays a success or failure message, and prints the updated map.
     *
     * @param city the City object to be removed
     */
    public void deleteCityToMap(City city) {
        if (actualMap.removeElement(city)) {
            Utils.printMessage("< City " + city.getName() + " removed from map >");
        } else {
            Utils.printMessage("< City " + city.getName() + " didn't removed from map >");
        }
        // Display the current state of the map after deletion attempt
        Utils.printMap(actualMap);
    }

    // === Getters and Setters ===

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
     * Gets the current map.
     *
     * @return the actual map
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the current map.
     *
     * @param actualMap the map to set
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
    }
}