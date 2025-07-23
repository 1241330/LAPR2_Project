package pt.ipp.isep.dei.domain._Others_;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Position} class.
 */
class PositionTest {

    /**
     * Tests that a Position object is correctly constructed with valid x and y coordinates.
     */
    @Test
    void testConstructorValidCoordinates() {
        Position pos = new Position(3, 5);
        assertEquals(3, pos.getX());
        assertEquals(5, pos.getY());
    }

    /**
     * Tests that creating a Position with a negative x coordinate throws an exception.
     */
    @Test
    void testConstructorNegativeXThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(-1, 2));
    }

    /**
     * Tests that creating a Position with a negative y coordinate throws an exception.
     */
    @Test
    void testConstructorNegativeYThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(1, -2));
    }

    /**
     * Tests that setX correctly updates the x coordinate.
     */
    @Test
    void testSetX() {
        Position pos = new Position(1, 2);
        pos.setX(10);
        assertEquals(10, pos.getX());
    }

    /**
     * Tests that setY correctly updates the y coordinate.
     */
    @Test
    void testSetY() {
        Position pos = new Position(1, 2);
        pos.setY(20);
        assertEquals(20, pos.getY());
    }

    /**
     * Tests that equalsPosition returns true for equal positions.
     */
    @Test
    void testEqualsPositionTrue() {
        Position pos1 = new Position(4, 7);
        Position pos2 = new Position(4, 7);
        assertTrue(pos1.equalsPosition(pos2));
    }

    /**
     * Tests that equalsPosition returns false for different positions.
     */
    @Test
    void testEqualsPositionFalse() {
        Position pos1 = new Position(4, 7);
        Position pos2 = new Position(5, 7);
        assertFalse(pos1.equalsPosition(pos2));
    }

    /**
     * Tests that the string representation of the Position object is formatted correctly.
     */
    @Test
    void testToStringFormat() {
        Position pos = new Position(2, 3);
        assertEquals("(x = 2, y = 3)", pos.toString());
    }
}
