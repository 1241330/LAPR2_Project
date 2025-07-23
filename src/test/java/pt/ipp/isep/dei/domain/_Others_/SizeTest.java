package pt.ipp.isep.dei.domain._Others_;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Size} class.
 */
class SizeTest {

    /**
     * Tests the constructor and getter methods to ensure the dimensions are set correctly.
     */
    @Test
    void testConstructorAndGetters() {
        Size size = new Size(10, 20);
        assertEquals(10, size.getWidth());
        assertEquals(20, size.getHeight());
    }

    /**
     * Tests that the constructor throws exceptions for invalid width or height values.
     */
    @Test
    void testConstructorThrowsExceptionForInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new Size(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Size(5, 0));
        assertThrows(IllegalArgumentException.class, () -> new Size(-1, 5));
        assertThrows(IllegalArgumentException.class, () -> new Size(5, -1));
    }

    /**
     * Tests setting a valid width value.
     */
    @Test
    void testSetWidthValid() {
        Size size = new Size(2, 3);
        size.setWidth(7);
        assertEquals(7, size.getWidth());
    }

    /**
     * Tests that setting an invalid width throws an exception.
     */
    @Test
    void testSetWidthThrowsException() {
        Size size = new Size(2, 3);
        assertThrows(IllegalArgumentException.class, () -> size.setWidth(0));
        assertThrows(IllegalArgumentException.class, () -> size.setWidth(-5));
    }

    /**
     * Tests setting a valid height value.
     */
    @Test
    void testSetHeightValid() {
        Size size = new Size(2, 3);
        size.setHeight(8);
        assertEquals(8, size.getHeight());
    }

    /**
     * Tests that setting an invalid height throws an exception.
     */
    @Test
    void testSetHeightThrowsException() {
        Size size = new Size(2, 3);
        assertThrows(IllegalArgumentException.class, () -> size.setHeight(0));
        assertThrows(IllegalArgumentException.class, () -> size.setHeight(-2));
    }

    /**
     * Tests the string representation of the Size object.
     */
    @Test
    void testToString() {
        Size size = new Size(4, 9);
        assertEquals("(width: 4, height: 9)", size.toString());
    }
}
