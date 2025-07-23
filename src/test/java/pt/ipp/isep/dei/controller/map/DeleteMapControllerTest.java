package pt.ipp.isep.dei.controller.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DeleteMapController} class.
 * Covers all possible scenarios related to map deletion, using country names as map names.
 */
class DeleteMapControllerTest {

    private DeleteMapController controller;

    /**
     * Clears the map repository and initializes the controller before each test.
     */
    @BeforeEach
    void setUp() {
        Repositories.getInstance().getMapRepository().getAllMaps().clear();
        controller = new DeleteMapController();
    }

    /**
     * Tests that an exception is thrown when setting a null map.
     */
    @Test
    void setActualMap_Null_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.setActualMap(null));
    }

    /**
     * Tests setting a valid map and retrieving its name.
     */
    @Test
    void setActualMap_ValidMap_ShouldSet() {
        Map map = new Map("Brazil", new Size(5, 5));
        controller.setActualMap(map);
        assertEquals("Brazil", controller.getNameMap());
        assertEquals(map, controller.getActualMap());
    }

    /**
     * Tests that an exception is thrown when attempting to delete a map without setting one first.
     */
    @Test
    void deleteMap_NoMapSet_ShouldThrowException() {
        assertThrows(IllegalStateException.class, () -> controller.deleteMap());
    }

    /**
     * Tests deleting an existing map from the repository.
     */
    @Test
    void deleteMap_ValidMap_ShouldRemoveFromRepository() {
        Map map = new Map("France", new Size(4, 4));
        Repositories.getInstance().getMapRepository().addMap(map);
        controller.setActualMap(map);
        assertDoesNotThrow(() -> controller.deleteMap());
        assertFalse(Repositories.getInstance().getMapRepository().existsMapWithID(map.getId()));
    }

    /**
     * Tests that no exception is thrown when attempting to delete a map not present in the repository.
     */
    @Test
    void deleteMap_MapNotInRepository_ShouldNotThrow() {
        Map map = new Map("Germany", new Size(3, 3));
        controller.setActualMap(map);
        assertDoesNotThrow(() -> controller.deleteMap());
    }

    /**
     * Tests that the controller returns false when there are no active maps in the repository.
     */
    @Test
    void thereAreActiveMaps_WhenRepositoryIsEmpty_ShouldReturnFalse() {
        assertFalse(controller.thereAreActiveMaps());
    }

    /**
     * Tests that the controller returns true when there is at least one active map in the repository.
     */
    @Test
    void thereAreActiveMaps_WhenRepositoryHasMaps_ShouldReturnTrue() {
        Map map = new Map("Italy", new Size(3, 3));
        Repositories.getInstance().getMapRepository().addMap(map);
        assertTrue(controller.thereAreActiveMaps());
    }

    /**
     * Tests that the controller returns all maps from the repository.
     */
    @Test
    void listMaps_ShouldReturnAllMaps() {
        Map map1 = new Map("Spain", new Size(2, 2));
        Map map2 = new Map("Portugal", new Size(3, 3));
        Repositories.getInstance().getMapRepository().addMap(map1);
        Repositories.getInstance().getMapRepository().addMap(map2);
        List<Map> maps = controller.listMaps();
        assertEquals(2, maps.size());
        assertTrue(maps.contains(map1));
        assertTrue(maps.contains(map2));
    }

    /**
     * Tests that the map repository can be retrieved and set correctly.
     */
    @Test
    void getSetMapRepository_ShouldWork() {
        assertNotNull(controller.getMapRepository());
        controller.setMapRepository(Repositories.getInstance().getMapRepository());
        assertNotNull(controller.getMapRepository());
    }
}