package pt.ipp.isep.dei.controller.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ShowMapForUIController} class.
 */
class ShowMapForUIControllerTest {

    private ShowMapForUIController controller;

    /**
     * Sets up the test environment by clearing the map repository and initialising the controller before each test.
     */
    @BeforeEach
    void setUp() {
        Repositories.getInstance().getMapRepository().getAllMaps().clear();
        controller = new ShowMapForUIController();
    }

    /**
     * Tests that {@code getActualMap} returns {@code null} when no map has been set.
     */
    @Test
    void getActualMap_ShouldReturnNull_WhenNotSet() {
        assertNull(controller.getActualMap());
    }

    /**
     * Tests that {@code setActualMap} correctly assigns a map, and that {@code getActualMap} retrieves the same map.
     */
    @Test
    void setActualMap_AndGetActualMap_ShouldWork() {
        Map map = new Map("Portugal", new Size(5, 5));
        controller.setActualMap(map);
        assertEquals(map, controller.getActualMap());
    }

    /**
     * Tests that {@code getIdMap} returns 0 when no map has been set.
     */
    @Test
    void getIdMap_ShouldReturnZero_WhenNoMapSet() {
        assertEquals(0, controller.getIdMap());
    }

    /**
     * Tests that {@code getIdMap} returns the correct ID of the map when one is set.
     */
    @Test
    void getIdMap_ShouldReturnMapId_WhenMapSet() {
        Map map = new Map("Spain", new Size(4, 4));
        controller.setActualMap(map);
        assertEquals(map.getId(), controller.getIdMap());
    }

    /**
     * Tests that {@code thereAreActiveMaps} returns {@code false} when the map repository is empty.
     */
    @Test
    void thereAreActiveMaps_ShouldReturnFalse_WhenRepositoryIsEmpty() {
        assertFalse(controller.thereAreActiveMaps());
    }

    /**
     * Tests that {@code thereAreActiveMaps} returns {@code true} when there is at least one map in the repository.
     */
    @Test
    void thereAreActiveMaps_ShouldReturnTrue_WhenRepositoryHasMaps() {
        Map map = new Map("France", new Size(3, 3));
        Repositories.getInstance().getMapRepository().addMap(map);
        assertTrue(controller.thereAreActiveMaps());
    }

    /**
     * Tests that {@code listMaps} returns all maps currently stored in the repository.
     */
    @Test
    void listMaps_ShouldReturnAllMaps() {
        Map map1 = new Map("Germany", new Size(2, 2));
        Map map2 = new Map("Italy", new Size(3, 3));
        Repositories.getInstance().getMapRepository().addMap(map1);
        Repositories.getInstance().getMapRepository().addMap(map2);
        List<Map> maps = controller.listMaps();
        assertEquals(2, maps.size());
        assertTrue(maps.contains(map1));
        assertTrue(maps.contains(map2));
    }

    /**
     * Tests that {@code toString} returns "No map selected" when no map is currently set.
     */
    @Test
    void toString_ShouldReturnNoMapSelected_WhenNoMapSet() {
        assertEquals("No map selected", controller.toString());
    }

    /**
     * Tests that {@code toString} returns the {@code toString} representation of the current map when one is set.
     */
    @Test
    void toString_ShouldReturnMapToString_WhenMapSet() {
        Map map = new Map("Switzerland", new Size(2, 2));
        controller.setActualMap(map);
        assertEquals(map.toString(), controller.toString());
    }
}
