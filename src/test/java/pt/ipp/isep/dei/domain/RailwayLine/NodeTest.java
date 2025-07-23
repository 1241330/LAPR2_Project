package pt.ipp.isep.dei.domain.RailwayLine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Node class.
 * Tests the creation and functionality of Node objects used in pathfinding.
 */
class NodeTest {

    /**
     * Tests if a Node can be created with all parameters and verifies
     * if all getters return the correct values.
     */
    @Test
    void testNodeCreationAndGetters() {
        Node parentNode = new Node(1, 1, 10, 5, null);
        Node node = new Node(2, 3, 15, 8, parentNode);

        assertEquals(2, node.getX());
        assertEquals(3, node.getY());
        assertEquals(15, node.getCost());
        assertEquals(8, node.getEstimate());
        assertEquals(parentNode, node.getParent());
    }

    /**
     * Tests if a Node can be created with null parent and verifies
     * the behavior of getParent method.
     */
    @Test
    void testNodeWithNullParent() {
        Node node = new Node(0, 0, 5, 3, null);

        assertNull(node.getParent());
    }

    /**
     * Tests if a Node correctly handles zero values for coordinates,
     * cost and estimate.
     */
    @Test
    void testNodeWithZeroValues() {
        Node node = new Node(0, 0, 0, 0, null);

        assertEquals(0, node.getX());
        assertEquals(0, node.getY());
        assertEquals(0, node.getCost());
        assertEquals(0, node.getEstimate());
    }

    /**
     * Tests if a Node correctly handles negative values for coordinates,
     * cost and estimate.
     */
    @Test
    void testNodeWithNegativeValues() {
        Node node = new Node(-1, -2, -3, -4, null);

        assertEquals(-1, node.getX());
        assertEquals(-2, node.getY());
        assertEquals(-3, node.getCost());
        assertEquals(-4, node.getEstimate());
    }
}