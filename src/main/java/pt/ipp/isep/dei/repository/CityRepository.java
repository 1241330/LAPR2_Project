package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing City entities.
 * Provides methods to add, remove, retrieve, and check cities.
 */
public class CityRepository implements Serializable {

    /**
     * List that stores all cities.
     */
    private List<City> cities = new ArrayList<>();

    /**
     * Constructs a CityRepository and initializes the city list.
     */
    public CityRepository() {
    }

    /**
     * Adds a city to the repository.
     *
     * @param city the city to be added
     * @return true if the city was added successfully, false if the city name already exists
     * @throws IllegalArgumentException if the city is null
     */
    public boolean addCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        if (cityNameExists(city.getName())) {
            return false;
        }
        cities.add(city);
        return true;
    }

    /**
     * Removes a city from the repository.
     *
     * @param city the city to be removed
     * @return true if the city was removed successfully, false if the city does not exist
     * @throws IllegalArgumentException if the city is null
     */
    public boolean removeCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        if (!cityExists(city)) {
            Utils.printMessage("< City doesn't exist >");
            return false;
        }
        cities.remove(city);
        return true;
    }

    /**
     * Gets a city by its ID.
     *
     * @param id the ID of the city
     * @return the city with the specified ID, or null if not found
     */
    public City getCityById(int id) {
        for (City city : cities) {
            if (city.getId() == id) {
                return city;
            }
        }
        return null;
    }

    /**
     * Gets the total number of cities in the repository.
     *
     * @return the number of cities
     */
    public int getCityCount() {
        return cities.size();
    }

    /**
     * Gets the list of all cities.
     *
     * @return the list of cities
     */
    public List<City> getAllCities() {
        return cities;
    }

    /**
     * Checks if a city exists in the repository.
     *
     * @param actualCity the city to check
     * @return true if the city exists, false otherwise
     */
    public boolean cityExists(City actualCity) {
        for (City city : cities) {
            if (city == actualCity) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a city name already exists in the repository.
     *
     * @param name the name of the city to check
     * @return true if the city name exists, false otherwise
     */
    public boolean cityNameExists(String name) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(name)) {
                Utils.printMessage("<City name already exist>");
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of cities.
     *
     * @return the list of cities
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * Sets the list of cities.
     *
     * @param cities the list of cities to set
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}