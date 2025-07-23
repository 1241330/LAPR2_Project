package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CityRepository} class.
 * Covers all scenarios: add, remove, get, existence, name checks, and null/edge cases.
 */
class CityRepositoryTest {
    private CityRepository repository;
    private City city1;
    private City city2;
    private Position pos1;
    private Position pos2;
    private List<HouseBlock> blocks1;
    private List<HouseBlock> blocks2;
    private List<Position> positions1;
    private List<Position> positions2;

    @BeforeEach
    void setUp() {
        repository = new CityRepository();
        pos1 = new Position(10, 10);
        pos2 = new Position(20, 20);
        blocks1 = new ArrayList<>();
        blocks2 = new ArrayList<>();
        positions1 = new ArrayList<>();
        positions2 = new ArrayList<>();
        city1 = new City("Porto", pos1, blocks1, positions1);
        city2 = new City("Lisboa", pos2, blocks2, positions2);
    }

    /**
     * Test adding a city successfully.
     */
    @Test
    void testAddCitySuccess() {
        assertTrue(repository.addCity(city1));
        assertEquals(1, repository.getCityCount());
        assertTrue(repository.getAllCities().contains(city1));
    }

    /**
     * Test adding a city with duplicate name (case-insensitive).
     */
    @Test
    void testAddCityDuplicateName() {
        repository.addCity(city1);
        City cityDuplicate = new City("PORTO", pos2, blocks2, positions2);
        assertFalse(repository.addCity(cityDuplicate));
        assertEquals(1, repository.getCityCount());
    }

    /**
     * Test adding a null city throws exception.
     */
    @Test
    void testAddCityNull() {
        assertThrows(IllegalArgumentException.class, () -> repository.addCity(null));
    }

    /**
     * Test removing a city successfully.
     */
    @Test
    void testRemoveCitySuccess() {
        repository.addCity(city1);
        assertTrue(repository.removeCity(city1));
        assertEquals(0, repository.getCityCount());
    }

    /**
     * Test removing a city that does not exist returns false.
     */
    @Test
    void testRemoveCityNotExists() {
        assertFalse(repository.removeCity(city1));
    }

    /**
     * Test removing a null city throws exception.
     */
    @Test
    void testRemoveCityNull() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeCity(null));
    }

    /**
     * Test getCityById returns correct city or null.
     */
    @Test
    void testGetCityById() {
        repository.addCity(city1);
        repository.addCity(city2);
        assertEquals(city1, repository.getCityById(city1.getId()));
        assertEquals(city2, repository.getCityById(city2.getId()));
        assertNull(repository.getCityById(999));
    }

    /**
     * Test getCityCount returns correct number.
     */
    @Test
    void testGetCityCount() {
        assertEquals(0, repository.getCityCount());
        repository.addCity(city1);
        assertEquals(1, repository.getCityCount());
        repository.addCity(city2);
        assertEquals(2, repository.getCityCount());
    }

    /**
     * Test getAllCities returns the correct list.
     */
    @Test
    void testGetAllCities() {
        repository.addCity(city1);
        repository.addCity(city2);
        List<City> all = repository.getAllCities();
        assertTrue(all.contains(city1));
        assertTrue(all.contains(city2));
        assertEquals(2, all.size());
    }

    /**
     * Test cityExists returns true for existing city and false otherwise.
     */
    @Test
    void testCityExists() {
        repository.addCity(city1);
        assertTrue(repository.cityExists(city1));
        assertFalse(repository.cityExists(city2));
        assertFalse(repository.cityExists(null));
    }

    /**
     * Test cityNameExists returns true for existing name (case-insensitive) and false otherwise.
     */
    @Test
    void testCityNameExists() {
        repository.addCity(city1);
        assertTrue(repository.cityNameExists("porto"));
        assertTrue(repository.cityNameExists("PORTO"));
        assertFalse(repository.cityNameExists("Lisboa"));
        assertFalse(repository.cityNameExists(null));
    }

    /**
     * Test getCities returns the same as getAllCities.
     */
    @Test
    void testGetCities() {
        repository.addCity(city1);
        repository.addCity(city2);
        assertEquals(repository.getAllCities(), repository.getCities());
    }

    /**
     * Test setCities replaces the list of cities.
     */
    @Test
    void testSetCities() {
        List<City> newList = new ArrayList<>();
        newList.add(city2);
        repository.setCities(newList);
        assertEquals(1, repository.getCityCount());
        assertTrue(repository.getAllCities().contains(city2));
    }

    /**
     * Test setCities with null list.
     */
    @Test
    void testSetCitiesNull() {
        assertThrows(NullPointerException.class, () -> repository.setCities(null));
    }
}
