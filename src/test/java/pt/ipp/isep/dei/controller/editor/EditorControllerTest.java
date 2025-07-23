package pt.ipp.isep.dei.controller.editor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link EditorController}.
 */
class EditorControllerTest {

    private EditorController controller;

    /**
     * Sets up the test environment by clearing the map repository
     * and initializing a new EditorController before each test.
     */
    @BeforeEach
    void setUp() {
        Repositories.getInstance().getMapRepository().getAllMaps().clear();
        controller = new EditorController();
    }

    /**
     * Tests that setting a null map throws an IllegalArgumentException.
     */
    @Test
    void setActualMap_Null_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.setActualMap(null));
    }

    /**
     * Tests that setting a valid map assigns it correctly.
     */
    @Test
    void setActualMap_ValidMap_ShouldSet() {
        Map map = new Map("Map1", new Size(2, 2));
        controller.setActualMap(map);
        assertEquals(map, controller.getActualMap());
    }

    /**
     * Tests that setting the same map multiple times does not cause errors.
     */
    @Test
    void setActualMap_RepeatedSet_ShouldNotThrow() {
        Map map = new Map("MapRepeated", new Size(2, 2));
        controller.setActualMap(map);
        assertDoesNotThrow(() -> controller.setActualMap(map));
        assertEquals(map, controller.getActualMap());
    }

    /**
     * Tests that getActualMap returns null when no map is selected.
     */
    @Test
    void getActualMap_NoMapSelected_ShouldReturnNull() {
        assertNull(controller.getActualMap());
    }

    /**
     * Tests that getIdMap returns 0 when no map is selected.
     */
    @Test
    void getIdMap_NoMapSelected_ShouldReturnZero() {
        assertEquals(0, controller.getIdMap());
    }

    /**
     * Tests that getIdMap returns the correct ID when a map is selected.
     */
    @Test
    void getIdMap_MapSelected_ShouldReturnId() {
        Map map = new Map("Map2", new Size(3, 3));
        controller.setActualMap(map);
        assertEquals(map.getId(), controller.getIdMap());
    }

    /**
     * Tests that thereAreActiveMaps returns false when the repository is empty.
     */
    @Test
    void thereAreActiveMaps_EmptyRepository_ShouldReturnFalse() {
        assertFalse(controller.thereAreActiveMaps());
    }

    /**
     * Tests that thereAreActiveMaps returns true when there are maps in the repository.
     */
    @Test
    void thereAreActiveMaps_WithMaps_ShouldReturnTrue() {
        Map map = new Map("Map3", new Size(4, 4));
        Repositories.getInstance().getMapRepository().addMap(map);
        assertTrue(controller.thereAreActiveMaps());
    }

    /**
     * Tests that listMaps returns all maps from the repository.
     */
    @Test
    void listMaps_ShouldReturnAllMaps() {
        Map map1 = new Map("A", new Size(1, 1));
        Map map2 = new Map("B", new Size(2, 2));
        Repositories.getInstance().getMapRepository().addMap(map1);
        Repositories.getInstance().getMapRepository().addMap(map2);
        List<Map> maps = controller.listMaps();
        assertEquals(2, maps.size());
        assertTrue(maps.contains(map1));
        assertTrue(maps.contains(map2));
    }

    /**
     * Tests that listMaps returns an empty list when the repository is empty.
     */
    @Test
    void listMaps_EmptyRepository_ShouldReturnEmptyList() {
        assertTrue(controller.listMaps().isEmpty());
    }

    /**
     * Tests that toString returns a message when no map is selected.
     */
    @Test
    void toString_NoMapSelected_ShouldReturnMessage() {
        assertEquals("No map selected", controller.toString());
    }

    /**
     * Tests that toString returns the selected map's string representation.
     */
    @Test
    void toString_MapSelected_ShouldReturnMapToString() {
        Map map = new Map("Map4", new Size(1, 1));
        controller.setActualMap(map);
        assertEquals(map.toString(), controller.toString());
    }

    /**
     * Tests that after clearing the repository, the controller still holds the selected map.
     */
    @Test
    void getActualMap_AfterRepositoryCleared_ShouldStillReturnMap() {
        Map map = new Map("MapPersist", new Size(2, 2));
        controller.setActualMap(map);
        Repositories.getInstance().getMapRepository().getAllMaps().clear();
        assertEquals(map, controller.getActualMap());
    }

    /**
     * Tests that after clearing the repository, listMaps returns an empty list.
     */
    @Test
    void listMaps_AfterRepositoryCleared_ShouldReturnEmptyList() {
        Map map = new Map("MapPersist", new Size(2, 2));
        Repositories.getInstance().getMapRepository().addMap(map);
        assertFalse(controller.listMaps().isEmpty());
        Repositories.getInstance().getMapRepository().getAllMaps().clear();
        assertTrue(controller.listMaps().isEmpty());
    }
}
