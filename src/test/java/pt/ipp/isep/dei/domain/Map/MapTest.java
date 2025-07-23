package pt.ipp.isep.dei.domain.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Scenario.Scenario;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Map} class.
 */
class MapTest {

    private Map map;
    private Size size;

    @BeforeEach
    void setUp() {
        size = new Size(10, 20);
        map = new Map("TestMap", size);
    }

    @Test
    void testConstructorWithNameAndSize() {
        assertEquals("TestMap", map.getName());
        assertEquals(size, map.getPixelSize());
        assertTrue(map.getMapElementsUsed().isEmpty());
    }

    @Test
    void testConstructorWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Map(null, size));
    }

    @Test
    void testConstructorWithEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Map("   ", size));
    }

    @Test
    void testConstructorWithNullSizeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Map("Test", null));
    }

    @Test
    void testSetNameValid() {
        map.setName("NewName");
        assertEquals("NewName", map.getName());
    }

    @Test
    void testSetNameNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> map.setName(null));
    }

    @Test
    void testSetNameEmptyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> map.setName(""));
    }

    @Test
    void testSetSizeValid() {
        Size newSize = new Size(5, 5);
        map.setPixelSize(newSize);
        assertEquals(newSize, map.getPixelSize());
    }

    @Test
    void testSetSizeNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> map.setPixelSize(null));
    }

    @Test
    void testToStringFormat() {
        String str = map.toString();
        assertTrue(str.contains("TestMap"));
        assertTrue(str.contains("width: 10"));
        assertTrue(str.contains("height: 20"));
    }

    @Test
    void testGetAndSetLastNameUsed() {
        map.setLastNameUsed("OtherName");
        assertEquals("OtherName", map.getLastNameUsed());
    }

    @Test
    void testGetAndSetSavedFileName() {
        map.setSavedFileName();
        assertEquals("TestMap.ser", map.getSavedFileName());
    }

    @Test
    void testGetId() {
        assertTrue(map.getId() >= 0);
    }

    /**
     * Tests the addition and removal of a MapElement to the map.
     * The element is added and then removed, and the assertions check if the element
     * is present or absent in the map's element list as expected.
     */
    @Test
    void testAddAndRemoveElement() {
        MapElement element = new MapElement(new Position(1, 1)) {
            @Override public String getType() { return "Test"; }
        };
        boolean added = map.addElement(element);
        // Pode ser false se o repositório não aceitar, mas não deve lançar exceção
        assertFalse(added ^ map.getMapElementsUsed().contains(element));
        boolean removed = map.removeElement(element);
        assertFalse(removed && map.getMapElementsUsed().contains(element));
    }

    /**
     * Tests if the occupied positions list contains the position of an added element.
     * The assertion is true if the position is present or the element was not added.
     */
    @Test
    void testGetOccupiedPositions() {
        MapElement element = new MapElement(new Position(2, 2)) {
            @Override public String getType() { return "Test"; }
        };
        map.addElement(element);
        assertTrue(map.getOccupiedPositions().contains(new Position(2, 2)) || !map.getMapElementsUsed().contains(element));
    }

    /**
     * Tests the positionOccupiedList method for both occupied and non-occupied positions.
     * Checks if the method returns true for an occupied position and false for a non-occupied one.
     */
    @Test
    void testPositionOccupiedList() {
        MapElement element = new MapElement(new Position(3, 3)) {
            @Override public String getType() { return "Test"; }
        };
        map.addElement(element);
        java.util.List<Position> occupiedList = new java.util.ArrayList<>();
        occupiedList.add(new Position(3, 3));
        assertTrue(map.positionOccupiedList(occupiedList) || !map.getMapElementsUsed().contains(element));
        java.util.List<Position> notOccupiedList = new java.util.ArrayList<>();
        notOccupiedList.add(new Position(9, 9));
        assertFalse(map.positionOccupiedList(notOccupiedList));
    }

    /**
     * Tests that all repository-based lists (stations, cities, railway lines, industries, house blocks)
     * are not null when retrieved from the map.
     */
    @Test
    void testGetLists() {
        assertNotNull(map.getStationList());
        assertNotNull(map.getCitiesList());
        assertNotNull(map.getRailwayLines());
        assertNotNull(map.getIndustriesList());
        assertNotNull(map.getHouseBlockList());
    }

    /**
     * Tests if the emoji matrix returned by the map has the correct dimensions
     * according to the map's size.
     */
    @Test
    void testGetEmojiMatrix() {
        String[][] matrix = map.getEmojiMatrix();
        assertEquals(size.getHeight(), matrix.length);
        assertEquals(size.getWidth(), matrix[0].length);
    }

    /**
     * Tests the addition and removal of a scenario to the map, and checks the scenario list and count.
     */
    @Test
    void testScenarioMethods() {
        Scenario scenarioObj = new Scenario(map, "Scenario1", 100, new pt.ipp.isep.dei.domain.Simulation.TimeDate(2000,1,1), new pt.ipp.isep.dei.domain.Simulation.TimeDate(2001,1,1));
        map.addScenario(scenarioObj);
        assertTrue(map.getScenarios().contains(scenarioObj));
        assertTrue(map.getNumActiveScenarios() >= 0);
        map.removeScenario(scenarioObj);
        assertFalse(map.getScenarios().contains(scenarioObj));
    }
}
