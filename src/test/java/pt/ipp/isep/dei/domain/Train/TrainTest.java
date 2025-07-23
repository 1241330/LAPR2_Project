package pt.ipp.isep.dei.domain.Train;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.Resource;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Train} class.
 */
class TrainTest {

    private Train train;
    private Locomotive locomotive;
    private List<Carriage> carriages;
    private static final String LOCOMOTIVE_NAME = "Test Locomotive";
    private static final String LOCOMOTIVE_IMAGE = "locomotive.png";
    private static final String CARRIAGE_IMAGE = "carriage.png";
    private static final TimeDate ACQUISITION_DATE = new TimeDate(2024, 6, 15);

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        locomotive = new Locomotive(LOCOMOTIVE_NAME, LOCOMOTIVE_IMAGE, 2000, 1.5, 120, 1950,
                50000, FuelType.DIESEL, 5, 1000);
        carriages = new ArrayList<>();
        carriages.add(new Carriage("Carriage1", CARRIAGE_IMAGE, 1950, 10000, 10));
        carriages.add(new Carriage("Carriage2", CARRIAGE_IMAGE, 1950, 12000, 15));
        train = new Train(locomotive, carriages, ACQUISITION_DATE);
    }

    /**
     * Tests the train constructor with valid parameters.
     */
    @Test
    void testConstructor() {
        assertNotNull(train);
        assertEquals(locomotive, train.getLocomotive());
        assertEquals(carriages, train.getCarriages());
        assertEquals(LOCOMOTIVE_NAME + " Train", train.getName());
        assertEquals(25, train.getMaxInventorySpace());
    }

    /**
     * Tests setting a valid locomotive.
     */
    @Test
    void testSetValidLocomotive() {
        Locomotive newLocomotive = new Locomotive("New Locomotive", LOCOMOTIVE_IMAGE, 2500, 1.8,
                150, 1960, 60000,
                FuelType.DIESEL, 6, 1200);
        train.setLocomotive(newLocomotive);
        assertEquals(newLocomotive, train.getLocomotive());
    }

    /**
     * Tests setting an invalid (null) locomotive.
     */
    @Test
    void testSetInvalidLocomotive() {
        assertThrows(IllegalArgumentException.class, () -> train.setLocomotive(null));
    }

    /**
     * Tests setting valid carriages.
     */
    @Test
    void testSetValidCarriages() {
        List<Carriage> newCarriages = new ArrayList<>();
        newCarriages.add(new Carriage("NewCarriage", CARRIAGE_IMAGE, 1955, 15000, 20));
        train.setCarriages(newCarriages);
        assertEquals(newCarriages, train.getCarriages());
    }

    /**
     * Tests setting invalid (empty) carriages.
     */
    @Test
    void testSetInvalidCarriages() {
        List<Carriage> emptyCarriages = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> train.setCarriages(emptyCarriages));
    }

    /**
     * Tests getting inventory space when empty.
     */
    @Test
    void testGetInventorySpaceOcupiedWhenEmpty() {
        assertEquals(0, train.getInventorySpaceOcupied());
    }

    /**
     * Tests the maximum inventory space calculation.
     */
    @Test
    void testMaxInventorySpace() {
        assertEquals(25, train.getMaxInventorySpace());
    }

    /**
     * Tests the string representation of the train.
     */
    @Test
    void testToString() {
        String expected = "Train: " + LOCOMOTIVE_NAME + " Train" +
                "Locomotive: " + LOCOMOTIVE_NAME +
                "Carriages: " + carriages.get(0).toString() + carriages.get(1).toString();
        assertEquals(expected, train.toString());
    }

    /**
     * Tests the train default constructor.
     */
    @Test
    void testDefaultConstructor() {
        Train t = new Train();
        assertNotNull(t);
    }

    /**
     * Tests getting the inventory.
     */
    @Test
    void testGetInventory() {
        assertNotNull(train.getInventory());
    }

    /**
     * Tests getting and setting the active flag.
     */
    @Test
    void testActiveFlag() {
        assertFalse(train.getActiveFlag());
        train.setActiveFlag(true);
        assertTrue(train.getActiveFlag());
    }

    /**
     * Tests getting and setting the acquisition date.
     */
    @Test
    void testAcquisitionDate() {
        assertEquals(ACQUISITION_DATE, train.getAcquisitionDate());
        TimeDate date = new TimeDate(2025, 1, 1);
        train.setAcquisitionDate(date);
        assertEquals(date, train.getAcquisitionDate());
    }

    /**
     * Tests getting inventory space occupied with resources.
     */
    @Test
    void testGetInventorySpaceOcupiedWithResources() {
        ResourcesType type = new ResourcesType("Coal", 100, 1, 10);
        Resource resource = new Resource(type, 20);
        train.getInventory().addResource(resource);
        assertEquals(20, train.getInventorySpaceOcupied());
    }

    /**
     * Tests the string representation of the train with no carriages.
     */
    @Test
    void testToStringWithNoCarriages() {
        Train t = new Train(locomotive, new ArrayList<>(), ACQUISITION_DATE);
        String expected = "Train: " + locomotive.getName() + " Train" +
                "Locomotive: " + locomotive.getName() +
                "Carriages: ";
        assertEquals(expected, t.toString());
    }
}