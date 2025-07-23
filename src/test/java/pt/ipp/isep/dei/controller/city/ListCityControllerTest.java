package pt.ipp.isep.dei.controller.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ListCityController}.
 */
class ListCityControllerTest {

    private int mapId;
    private MapRepository mapRepository;
    private Map map;
    private ListCityController controller;

    /**
     * Sets up the test environment before each test.
     * Creates a map and initialises the controller.
     */
    @BeforeEach
    void setUp() {
        mapRepository = Repositories.getInstance().getMapRepository();
        mapRepository.getAllMaps().clear();

        Size size = new Size(10, 10);
        map = new Map("PortugalMap", size);
        mapRepository.addMap(map);
        mapId = map.getId();

        controller = new ListCityController(mapId);
    }

    /**
     * Tests listing cities when the map has no cities.
     * Ensures the returned list is not null and is empty.
     */
    @Test
    void testGetActualCitiesWhenEmpty() {
        List<City> cities = controller.getActualCities();
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    /**
     * Tests listing cities when the map has one city.
     * Ensures the correct city is returned.
     */
    @Test
    void testGetActualCitiesWithOneCity() {
        City lisboa = new City("Lisboa", new Position(2, 2), new ArrayList<>(), new ArrayList<>());
        map.addElement(lisboa);
        List<City> cities = controller.getActualCities();
        assertEquals(1, cities.size());
        assertEquals("Lisboa", cities.get(0).getName());
    }

    /**
     * Tests listing cities when the map has multiple cities.
     * Ensures all cities are returned and their names are correct.
     */
    @Test
    void testGetActualCitiesWithMultipleCities() {
        City porto = new City("Porto", new Position(1, 1), new ArrayList<>(), new ArrayList<>());
        City braga = new City("Braga", new Position(3, 3), new ArrayList<>(), new ArrayList<>());
        City coimbra = new City("Coimbra", new Position(4, 4), new ArrayList<>(), new ArrayList<>());
        map.addElement(porto);
        map.addElement(braga);
        map.addElement(coimbra);
        List<City> cities = controller.getActualCities();
        assertEquals(3, cities.size());
        assertTrue(cities.stream().anyMatch(c -> c.getName().equals("Porto")));
        assertTrue(cities.stream().anyMatch(c -> c.getName().equals("Braga")));
        assertTrue(cities.stream().anyMatch(c -> c.getName().equals("Coimbra")));
    }

    /**
     * Tests that the returned list is a live view and reflects changes after adding a city.
     * Ensures the list updates after a city is added.
     */
    @Test
    void testGetActualCitiesReflectsAdditions() {
        List<City> citiesBefore = controller.getActualCities();
        assertTrue(citiesBefore.isEmpty());
        City aveiro = new City("Aveiro", new Position(5, 5), new ArrayList<>(), new ArrayList<>());
        map.addElement(aveiro);
        List<City> citiesAfter = controller.getActualCities();
        assertEquals(1, citiesAfter.size());
        assertEquals("Aveiro", citiesAfter.get(0).getName());
    }

    /**
     * Tests that the returned list is a live view and reflects changes after removing a city.
     * Ensures the list updates after a city is removed.
     */
    @Test
    void testGetActualCitiesReflectsRemovals() {
        City faro = new City("Faro", new Position(6, 6), new ArrayList<>(), new ArrayList<>());
        map.addElement(faro);
        assertEquals(1, controller.getActualCities().size());
        map.removeElement(faro);
        assertTrue(controller.getActualCities().isEmpty());
    }

    /**
     * Tests listing cities after adding a null city (should not throw and should not add).
     */
    @Test
    void testGetActualCitiesWithNullCity() {
        map.addElement(null);
        List<City> cities = controller.getActualCities();
        assertTrue(cities.isEmpty());
    }

    /**
     * Tests listing cities after adding duplicate cities (same name and position).
     * Ensures both are present in the list.
     */
    @Test
    void testGetActualCitiesWithDuplicateCities() {
        City porto1 = new City("Porto", new Position(1, 1), new ArrayList<>(), new ArrayList<>());
        City porto2 = new City("Porto", new Position(1, 1), new ArrayList<>(), new ArrayList<>());
        map.addElement(porto1);
        map.addElement(porto2);
        List<City> cities = controller.getActualCities();
        assertEquals(2, cities.size());
        assertEquals("Porto", cities.get(0).getName());
        assertEquals("Porto", cities.get(1).getName());
    }
}

