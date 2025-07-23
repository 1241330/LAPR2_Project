package pt.ipp.isep.dei.domain.Train;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Carriage} class.
 * This class validates the behavior of Carriage's constructor,
 * getter and setter methods, the in-use state, and the toString method.
 */
class CarriageTest {

    /**
     * Tests the constructor and all getter methods.
     */
    @Test
    void testConstructorAndGetters() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);

        assertEquals("C1", carriage.getName());
        assertEquals("/img/c1.png", carriage.getImagePath());
        assertEquals(2000, carriage.getStartYearOperation());
        assertEquals(50000, carriage.getAcquisitionCost());
        assertEquals(100, carriage.getMaxResourceCapacity());
        assertFalse(carriage.getInUse());
    }

    /**
     * Tests the setName method.
     */
    @Test
    void testSetName() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);
        carriage.setName("C2");
        assertEquals("C2", carriage.getName());
    }

    /**
     * Tests the setImagePath method.
     */
    @Test
    void testSetImagePath() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);
        carriage.setImagePath("/img/c2.png");
        assertEquals("/img/c2.png", carriage.getImagePath());
    }

    /**
     * Tests the setStartYearOperation method.
     */
    @Test
    void testSetStartYearOperation() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);
        carriage.setStartYearOperation(2010);
        assertEquals(2010, carriage.getStartYearOperation());
    }

    /**
     * Tests the setAcquisitionCost method.
     */
    @Test
    void testSetAcquisitionCost() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);
        carriage.setAcquisitionCost(60000);
        assertEquals(60000, carriage.getAcquisitionCost());
    }

    /**
     * Tests the setMaxResourceCapacity method.
     */
    @Test
    void testSetMaxResourceCapacity() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);
        carriage.setMaxResourceCapacity(200);
        assertEquals(200, carriage.getMaxResourceCapacity());
    }

    /**
     * Tests the getInUse and setInUse methods.
     */
    @Test
    void testGetAndSetInUse() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);
        assertFalse(carriage.getInUse());
        carriage.setInUse(true);
        assertTrue(carriage.getInUse());
        carriage.setInUse(false);
        assertFalse(carriage.getInUse());
    }

    /**
     * Tests the toString method.
     */
    @Test
    void testToString() {
        Carriage carriage = new Carriage("C1", "/img/c1.png", 2000, 50000, 100);
        String expected = "Carriage → name: C1 | Acquisition Price: 50000 €";
        assertEquals(expected, carriage.toString());
    }
}
