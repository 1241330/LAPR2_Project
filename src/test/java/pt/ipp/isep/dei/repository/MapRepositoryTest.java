package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MapRepository} class.
 * Covers all scenarios: add, remove, get, existence, name checks, and null/edge cases.
 */
class MapRepositoryTest {
    private MapRepository repository;
    private Map map1;
    private Map map2;

    @BeforeEach
    void setUp() throws Exception {
        repository = new MapRepository();
        repository.getAllMaps().clear();
        map1 = new Map("MapA", new Size(10, 10));
        map2 = new Map("MapB", new Size(10, 10));
    }

    /**
     * Test adding and retrieving maps by ID, including not found.
     */
    @Test
    void testAddAndGetMapById() {
        repository.addMap(map1);
        repository.addMap(map2);
        assertEquals(map1, repository.getMapById(map1.getId()));
        assertEquals(map2, repository.getMapById(map2.getId()));
        assertNull(repository.getMapById(999));
    }

    /**
     * Test adding a null map throws IllegalArgumentException.
     */
    @Test
    void testAddMapThrowsExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> repository.addMap(null));
    }

    /**
     * Test map count increases when maps are added.
     */
    @Test
    void testGetMapCount() {
        assertEquals(0, repository.getMapCount());
        repository.addMap(map1);
        assertEquals(1, repository.getMapCount());
        repository.addMap(map2);
        assertEquals(2, repository.getMapCount());
    }

    /**
     * Test retrieving all maps returns the correct list.
     */
    @Test
    void testGetAllMaps() {
        repository.addMap(map1);
        repository.addMap(map2);
        List<Map> maps = repository.getAllMaps();
        assertTrue(maps.contains(map1));
        assertTrue(maps.contains(map2));
        assertEquals(2, maps.size());
    }

    /**
     * Test removing a map by its ID, including non-existent ID.
     */
    @Test
    void testRemoveMap() {
        repository.addMap(map1);
        repository.addMap(map2);
        repository.removeMap(map1.getId());
        assertNull(repository.getMapById(map1.getId()));
        assertEquals(1, repository.getMapCount());
        repository.removeMap(999); // Remove non-existent
        assertEquals(1, repository.getMapCount());
    }

    /**
     * Test checking if a map exists by ID.
     */
    @Test
    void testExistsMapWithID() {
        repository.addMap(map1);
        assertTrue(repository.existsMapWithID(map1.getId()));
        assertFalse(repository.existsMapWithID(999));
    }

    /**
     * Test checking if a map exists by name (case-sensitive, null, empty).
     */
    @Test
    void testExistsMapWithName() {
        repository.addMap(map1);
        assertTrue(repository.existsMapWithName("MapA"));
        assertFalse(repository.existsMapWithName("mapa"));
        assertFalse(repository.existsMapWithName(""));
        assertFalse(repository.existsMapWithName(null));
    }

    /**
     * Test retrieving the map ID by its name (case-insensitive, null, empty).
     */
    @Test
    void testGetIdByName() {
        repository.addMap(map1);
        assertEquals(map1.getId(), repository.getIdByName("MapA"));
        assertEquals(map1.getId(), repository.getIdByName("mapa".toUpperCase()));
        assertEquals(-1, repository.getIdByName("Nonexistent"));
        assertThrows(IllegalArgumentException.class, () -> repository.getIdByName(""));
        assertThrows(IllegalArgumentException.class, () -> repository.getIdByName(null));
    }

    /**
     * Test finding a map by its name.
     */
    @Test
    void testFindMapByName() {
        repository.addMap(map2);
        assertEquals(map2, repository.findMapByName("MapB"));
        assertNull(repository.findMapByName("Other"));
    }

    /**
     * Test the number of active maps in the repository.
     */
    @Test
    void testNActiveMaps() {
        assertEquals(0, repository.nActiveMaps());
        repository.addMap(map1);
        assertEquals(1, repository.nActiveMaps());
    }

    /**
     * Test that getAllMaps returns a mutable list (modifications affect the repository).
     */
    @Test
    void testGetAllMapsIsMutable() {
        repository.addMap(map1);
        List<Map> maps = repository.getAllMaps();
        maps.clear();
        assertEquals(0, repository.getMapCount());
    }
}
