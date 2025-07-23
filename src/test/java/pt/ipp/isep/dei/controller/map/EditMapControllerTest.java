package pt.ipp.isep.dei.controller.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link EditMapController} class.
 * Covers all possible scenarios for editing maps, using country names as map names.
 */
class EditMapControllerTest {

    private EditMapController controller;

    /**
     * Clears the map repository and initializes the controller before each test.
     */
    @BeforeEach
    void setUp() {
        Repositories.getInstance().getMapRepository().getAllMaps().clear();
        controller = new EditMapController();
    }

    /**
     * Tests that setting a null map throws an IllegalArgumentException.
     */
    @Test
    void setActualMap_Null_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> controller.setActualMap(null));
    }

    /**
     * Tests that a valid map can be set as the current map.
     */
    @Test
    void setActualMap_ValidMap_ShouldSet() {
        Map map = new Map("Brazil", new Size(5, 5));
        controller.setActualMap(map);
        assertEquals(map, controller.getActualMap());
    }

    /**
     * Tests that setting a new name updates the map's name.
     */
    @Test
    void setNewName_Valid_ShouldChangeName() {
        Map map = new Map("France", new Size(3, 3));
        controller.setActualMap(map);
        controller.setNewName("Germany");
        assertEquals("Germany", controller.getName());
    }

    /**
     * Tests that setting a null or blank name throws an IllegalArgumentException.
     */
    @Test
    void setNewName_NullOrEmpty_ShouldThrowException() {
        Map map = new Map("Italy", new Size(3, 3));
        controller.setActualMap(map);
        assertThrows(IllegalArgumentException.class, () -> controller.setNewName(null));
        assertThrows(IllegalArgumentException.class, () -> controller.setNewName("   "));
    }

    /**
     * Tests that trying to set a name without selecting a map throws an IllegalStateException.
     */
    @Test
    void setNewName_NoMapSelected_ShouldThrowException() {
        assertThrows(IllegalStateException.class, () -> controller.setNewName("Spain"));
    }

    /**
     * Tests that setting new valid dimensions updates the map size.
     */
    @Test
    void setNewSize_Valid_ShouldChangeSize() {
        Map map = new Map("Portugal", new Size(2, 2));
        controller.setActualMap(map);
        controller.setNewSize(4, 6);
        assertEquals(4, controller.getSize().getWidth());
        assertEquals(6, controller.getSize().getHeight());
    }

    /**
     * Tests that invalid size dimensions (zero or negative) throw an IllegalArgumentException.
     */
    @Test
    void setNewSize_InvalidDimensions_ShouldThrowException() {
        Map map = new Map("Argentina", new Size(2, 2));
        controller.setActualMap(map);
        assertThrows(IllegalArgumentException.class, () -> controller.setNewSize(0, 5));
        assertThrows(IllegalArgumentException.class, () -> controller.setNewSize(5, -1));
    }

    /**
     * Tests that setting size without selecting a map throws an IllegalStateException.
     */
    @Test
    void setNewSize_NoMapSelected_ShouldThrowException() {
        assertThrows(IllegalStateException.class, () -> controller.setNewSize(3, 3));
    }

    /**
     * Tests that the correct map ID is returned or zero if no map is selected.
     */
    @Test
    void getIdMap_ShouldReturnMapIdOrZero() {
        assertEquals(0, controller.getIdMap());
        Map map = new Map("Japan", new Size(1, 1));
        controller.setActualMap(map);
        assertEquals(map.getId(), controller.getIdMap());
    }

    /**
     * Tests whether the controller correctly detects if there are active maps in the repository.
     */
    @Test
    void thereAreActiveMaps_ShouldReflectRepositoryState() {
        assertFalse(controller.thereAreActiveMaps());
        Map map = new Map("Canada", new Size(1, 1));
        Repositories.getInstance().getMapRepository().addMap(map);
        assertTrue(controller.thereAreActiveMaps());
    }

    /**
     * Tests that all maps in the repository are listed correctly.
     */
    @Test
    void listMaps_ShouldReturnAllMaps() {
        Map map1 = new Map("Australia", new Size(1, 1));
        Map map2 = new Map("India", new Size(2, 2));
        Repositories.getInstance().getMapRepository().addMap(map1);
        Repositories.getInstance().getMapRepository().addMap(map2);
        List<Map> maps = controller.listMaps();
        assertEquals(2, maps.size());
        assertTrue(maps.contains(map1));
        assertTrue(maps.contains(map2));
    }

    /**
     * Tests the toString method when no map is selected.
     */
    @Test
    void toString_NoMapSelected_ShouldReturnMessage() {
        assertEquals("No map selected", controller.toString());
    }

    /**
     * Tests the toString method when a map is selected.
     */
    @Test
    void toString_MapSelected_ShouldReturnMapToString() {
        Map map = new Map("Mexico", new Size(1, 1));
        controller.setActualMap(map);
        assertEquals(map.toString(), controller.toString());
    }

    /**
     * Tests the alreadyExistNameMapInMapRepository method.
     */
    @Test
    void alreadyExistNameMapInMapRepository_ShouldReturnCorrectly() {
        Map map = new Map("Egypt", new Size(2, 2));
        Repositories.getInstance().getMapRepository().addMap(map);
        assertTrue(controller.alreadyExistNameMapInMapRepository("Egypt"));
        assertFalse(controller.alreadyExistNameMapInMapRepository("Norway"));
    }

    /**
     * Tests the get/set methods for the map repository.
     */
    @Test
    void getSetMapRepository_ShouldWork() {
        assertNotNull(controller.getMapRepositoryInstance());
        controller.setMapRepository(Repositories.getInstance().getMapRepository());
        assertNotNull(controller.getMapRepositoryInstance());
    }
}