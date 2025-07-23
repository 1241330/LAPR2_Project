package pt.ipp.isep.dei.controller.city;

import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

/**
 * Controller responsible for providing the list of cities
 * from a specific map identified by its ID.
 */
public class ListCityController {

    /**
     * Repository for accessing map data.
     */
    private MapRepository mapRepository;

    /**
     * The current map from which cities will be listed.
     */
    private Map actualMap;

    /**
     * Constructs a controller to list cities from the map with the given ID.
     *
     * @param mapId the identifier of the map from which to retrieve cities
     */
    public ListCityController(int mapId) {
        this.mapRepository = Repositories.getInstance().getMapRepository();
        this.actualMap = mapRepository.getMapById(mapId);
    }

    /**
     * Returns the list of cities from the current map.
     *
     * @return list of cities of the map
     */
    public List<City> getActualCities() {
        return actualMap.getCitiesList();
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