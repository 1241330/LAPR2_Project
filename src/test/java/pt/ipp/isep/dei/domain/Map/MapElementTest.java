package pt.ipp.isep.dei.domain.Map;

import org.junit.jupiter.api.Test;

import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MapElement} abstract class using a concrete test subclass.
 */
class MapElementTest {

    /**
     * Concrete subclass of MapElement for testing purposes.
     */
    static class TestMapElement extends MapElement {
        public TestMapElement(Position position) {
            super(position);
        }

        public TestMapElement(Position position, List<Position> occupiedPositions) {
            super(position, occupiedPositions);
        }

        /**
         * Returns a hardcoded type name for test validation.
         * @return the type of map element.
         */
        @Override
        public String getType() {
            return "TestType";
        }
    }

    /**
     * Tests the constructor with initial position and occupied positions,
     * and verifies the getter methods return the expected values.
     */
    @Test
    void testConstructorAndGetters() {
        Position pos = new Position(1, 2);
        List<Position> occupied = Arrays.asList(new Position(1, 2), new Position(2, 3));
        TestMapElement element = new TestMapElement(pos, occupied);

        assertEquals(pos, element.getPosition());
        assertEquals(occupied, element.getOccupiedPositions());
    }

    /**
     * Tests that the position of a MapElement can be updated correctly.
     */
    @Test
    void testSetPosition() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(3, 4);
        TestMapElement element = new TestMapElement(pos1);
        element.setPosition(pos2);

        assertEquals(pos2, element.getPosition());
    }

    /**
     * Tests that the occupied positions list of a MapElement can be updated correctly.
     */
    @Test
    void testSetOccupiedPositions() {
        Position pos = new Position(1, 2);
        List<Position> occupied1 = new ArrayList<>();
        List<Position> occupied2 = Arrays.asList(new Position(5, 6));
        TestMapElement element = new TestMapElement(pos, occupied1);

        element.setOccupiedPositions(occupied2);
        assertEquals(occupied2, element.getOccupiedPositions());
    }

    /**
     * Tests that the overridden {@code getType} method returns the expected value.
     */
    @Test
    void testGetType() {
        TestMapElement element = new TestMapElement(new Position(0, 0));
        assertEquals("TestType", element.getType());
    }

    /**
     * Tests that modifying the occupied positions list externally affects the MapElement if the list is not defensively copied.
     */
    @Test
    void testOccupiedPositionsListMutability() {
        Position pos = new Position(1, 2);
        List<Position> occupied = new ArrayList<>();
        occupied.add(new Position(1, 2));
        TestMapElement element = new TestMapElement(pos, occupied);
        occupied.add(new Position(9, 9));
        assertEquals(2, element.getOccupiedPositions().size());
    }

    /**
     * Tests the behavior when passing an empty list of occupied positions.
     */
    @Test
    void testEmptyOccupiedPositions() {
        Position pos = new Position(1, 2);
        List<Position> emptyList = new ArrayList<>();
        TestMapElement element = new TestMapElement(pos, emptyList);
        assertTrue(element.getOccupiedPositions().isEmpty());
    }

    /**
     * Tests the behavior when setOccupiedPositions is called with null (should throw or set to null).
     */
    @Test
    void testSetOccupiedPositionsNull() {
        Position pos = new Position(1, 2);
        TestMapElement element = new TestMapElement(pos);
        element.setOccupiedPositions(null);
        assertNull(element.getOccupiedPositions());
    }
}
