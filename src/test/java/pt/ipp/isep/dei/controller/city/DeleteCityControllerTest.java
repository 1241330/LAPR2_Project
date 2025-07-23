package pt.ipp.isep.dei.controller.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DeleteCityController} class.*/
class DeleteCityControllerTest {

    private int mapId;
    private MapRepository mapRepository;
    private Map map;
    private City lisboa;
    private DeleteCityController controller;

    /**
     * Sets up the test environment before each test.
     * Creates a map with a city and initialises the controller.
     */
    @BeforeEach
    void setUp() {
        mapRepository = Repositories.getInstance().getMapRepository();
        mapRepository.getAllMaps().clear();

        Size size = new Size(10, 10);
        map = new Map("PortugalMap", size);
        mapRepository.addMap(map);
        mapId = map.getId();

        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(1, 1), "Lisboa"));
        lisboa = new City("Lisboa", new Position(2, 2), blocks, List.of(new Position(1, 1)));
        map.addElement(lisboa);

        controller = new DeleteCityController(mapId);
    }

    /**
     * Tests if the controller correctly detects active cities on the map.
     * Verifies both the presence and absence of cities.
     */
    @Test
    void testThereAreActiveCities() {
        assertTrue(controller.thereAreActiveCities());
        map.removeElement(lisboa);
        assertFalse(controller.thereAreActiveCities());
    }

    /**
     * Tests retrieval of the current list of cities from the map.
     * Ensures the correct list is returned.
     */
    @Test
    void testGetActualCities() {
        List<City> cities = controller.getActualCities();
        assertEquals(1, cities.size());
        assertEquals("Lisboa", cities.get(0).getName());
    }

    /**
     * Tests the successful deletion of an existing city from the map.
     * Ensures the city is removed and the map is updated accordingly.
     */
    @Test
    void testDeleteCitySuccessful() {
        controller.deleteCityToMap(lisboa);
        assertFalse(controller.thereAreActiveCities());
        assertTrue(controller.getActualCities().isEmpty());
    }

    /**
     * Tests attempting to delete a city that does not exist in the map.
     * Ensures the map remains unchanged.
     */
    @Test
    void testDeleteCityNonExistent() {
        City porto = new City("Porto", new Position(5, 5), new ArrayList<>(), new ArrayList<>());
        controller.deleteCityToMap(porto);
        assertTrue(controller.thereAreActiveCities());
        assertEquals(1, controller.getActualCities().size());
    }

    /**
     * Tests deleting a city when the map has no cities.
     * Ensures no errors occur and the map remains empty.
     */
    @Test
    void testDeleteCityWhenNoCities() {
        map.removeElement(lisboa);
        City coimbra = new City("Coimbra", new Position(3, 3), new ArrayList<>(), new ArrayList<>());
        controller.deleteCityToMap(coimbra);
        assertFalse(controller.thereAreActiveCities());
        assertTrue(controller.getActualCities().isEmpty());
    }

    /**
     * Tests the controller with a map that has multiple cities.
     * Ensures only the specified city is deleted.
     */
    @Test
    void testDeleteOneOfMultipleCities() {
        City braga = new City("Braga", new Position(4, 4), new ArrayList<>(), new ArrayList<>());
        map.addElement(braga);
        assertEquals(2, controller.getActualCities().size());
        controller.deleteCityToMap(braga);
        List<City> cities = controller.getActualCities();
        assertEquals(1, cities.size());
        assertEquals("Lisboa", cities.get(0).getName());
    }

    /**
     * Tests deleting all cities one by one.
     * Ensures the map is empty after all deletions.
     */
    @Test
    void testDeleteAllCities() {
        City aveiro = new City("Aveiro", new Position(6, 6), new ArrayList<>(), new ArrayList<>());
        City faro = new City("Faro", new Position(7, 7), new ArrayList<>(), new ArrayList<>());
        map.addElement(aveiro);
        map.addElement(faro);
        assertEquals(3, controller.getActualCities().size());
        controller.deleteCityToMap(lisboa);
        controller.deleteCityToMap(aveiro);
        controller.deleteCityToMap(faro);
        assertFalse(controller.thereAreActiveCities());
        assertTrue(controller.getActualCities().isEmpty());
    }

    /**
     * Tests deleting a city with a null reference.
     * Ensures no exceptions are thrown and the map remains unchanged.
     */
    @Test
    void testDeleteNullCity() {
        controller.deleteCityToMap(null);
        assertTrue(controller.thereAreActiveCities());
        assertEquals(1, controller.getActualCities().size());
    }

    /**
     * Tests deleting the same city twice.
     * Ensures the second deletion does not affect the map.
     */
    @Test
    void testDeleteSameCityTwice() {
        controller.deleteCityToMap(lisboa);
        controller.deleteCityToMap(lisboa);
        assertFalse(controller.thereAreActiveCities());
        assertTrue(controller.getActualCities().isEmpty());
    }
}
