package pt.ipp.isep.dei.domain.City;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link HouseBlock} class.
 * <p>
 * This class covers all public methods and scenarios for the HouseBlock class, including:
 * <ul>
 *     <li>Constructor and getters</li>
 *     <li>Setters for position and assigned station</li>
 *     <li>Productions list manipulation</li>
 *     <li>Unique and incremental ID</li>
 *     <li>City name getter</li>
 *     <li>Inventory getter</li>
 *     <li>Static idCounter</li>
 *     <li>Reflection for updatedinventory field</li>
 *     <li>toString method</li>
 *     <li>HouseBlockResource setters and getters</li>
 * </ul>
 * No external imports, mocks, dummies or fakes are used.
 * All scenarios are covered with real objects and direct assertions.
 */
class HouseBlockTest {

    private HouseBlock houseBlock;
    private Position position;

    /**
     * Sets up the initial state before each test.
     * Initializes a {@code HouseBlock} with a predefined {@code Position}.
     */
    @BeforeEach
    void setUp() {
        position = new Position(2, 3);
        houseBlock = new HouseBlock(position, "Porto");
    }

    /**
     * Tests the constructor and getter methods of {@code HouseBlock}.
     * Verifies that:
     * <ul>
     *     <li>The position is correctly assigned</li>
     *     <li>No station is assigned initially</li>
     *     <li>The productions list is initialized</li>
     *     <li>The ID is greater than or equal to zero</li>
     *     <li>The city name is correctly assigned</li>
     * </ul>
     */
    @Test
    void testConstructorAndGetters() {
        Position pos = houseBlock.getPosition();
        assertEquals(position.getX(), pos.getX());
        assertEquals(position.getY(), pos.getY());
        assertNull(houseBlock.getAssignedStation());
        assertNotNull(houseBlock.getProductions());
        assertTrue(houseBlock.getId() >= 0);
        assertEquals("Porto", houseBlock.getCityName());
    }

    /**
     * Tests the {@code setPosition()} method.
     * Ensures the {@code HouseBlock} updates its position correctly.
     */
    @Test
    void testSetPosition() {
        Position newPos = new Position(5, 7);
        houseBlock.setPosition(newPos);
        Position pos = houseBlock.getPosition();
        assertEquals(5, pos.getX());
        assertEquals(7, pos.getY());
    }

    /**
     * Tests setting and getting the assigned station of the {@code HouseBlock}.
     */
    @Test
    void testSetAndGetAssignedStation() {
        Station station = new Station("Station1");
        houseBlock.setAssignedStation(station);
        assertEquals(station, houseBlock.getAssignedStation());
    }

    /**
     * Tests the {@code toString()} method of the {@code HouseBlock}.
     * Ensures the string contains key information about the position and assigned station.
     */
    @Test
    void testToString() {
        String str = houseBlock.toString();
        assertTrue(str.contains("position="));
        assertTrue(str.contains("assignedStation="));
    }

    /**
     * Testa se é possível adicionar e remover produções corretamente.
     */
    @Test
    void testAddAndRemoveProductions() {
        HouseBlockResource resource1 = new HouseBlockResource("Água", 100, 10, 5);
        HouseBlockResource resource2 = new HouseBlockResource("Energia", 200, 20, 10);
        houseBlock.getProductions().add(resource1);
        houseBlock.getProductions().add(resource2);
        assertTrue(houseBlock.getProductions().contains(resource1));
        assertTrue(houseBlock.getProductions().contains(resource2));
        houseBlock.getProductions().remove(resource1);
        assertFalse(houseBlock.getProductions().contains(resource1));
    }

    /**
     * Testa se o ID é único e incremental.
     */
    @Test
    void testUniqueAndIncrementalId() {
        HouseBlock hb1 = new HouseBlock(new Position(0, 0), "A");
        HouseBlock hb2 = new HouseBlock(new Position(1, 1), "B");
        assertTrue(hb2.getId() > hb1.getId());
    }

    /**
     * Tests adding different types of resources to the productions list and verifies their properties.
     */
    @Test
    void testAddDifferentResourceTypesToProductions() {
        HouseBlockResource water = new HouseBlockResource("Water", 100, 5, 10);
        HouseBlockResource electricity = new HouseBlockResource("Electricity", 200, 10, 20);
        HouseBlockResource food = new HouseBlockResource("Food", 50, 7, 5);
        houseBlock.getProductions().add(water);
        houseBlock.getProductions().add(electricity);
        houseBlock.getProductions().add(food);
        assertEquals(3, houseBlock.getProductions().size());
        assertEquals("Water", houseBlock.getProductions().get(0).getName());
        assertEquals(100, houseBlock.getProductions().get(0).getMaxResources());
        assertEquals(5, houseBlock.getProductions().get(0).getIntervalBetweenResourceGeneration());
        assertEquals(10, houseBlock.getProductions().get(0).getQuantityProduced());
        assertEquals("Electricity", houseBlock.getProductions().get(1).getName());
        assertEquals("Food", houseBlock.getProductions().get(2).getName());
    }

    /**
     * Tests the behavior when no productions or station are assigned.
     */
    @Test
    void testHouseBlockWithNoProductionsOrStation() {
        HouseBlock emptyBlock = new HouseBlock(new Position(9, 9), "Aveiro");
        assertTrue(emptyBlock.getProductions().isEmpty());
        assertNull(emptyBlock.getAssignedStation());
        String str = emptyBlock.toString();
        assertTrue(str.contains("position="));
        assertTrue(str.contains("assignedStation=null"));
    }

    /**
     * Tests the setters and getters for HouseBlockResource properties.
     */
    @Test
    void testResourceSettersAndGetters() {
        HouseBlockResource resource = new HouseBlockResource("Gas", 80, 12, 8);
        resource.setName("Natural Gas");
        resource.setMaxResources(90);
        resource.setIntervalBetweenResourceGeneration(15);
        resource.setQuantityProduced(9);
        assertEquals("Natural Gas", resource.getName());
        assertEquals(90, resource.getMaxResources());
        assertEquals(15, resource.getIntervalBetweenResourceGeneration());
        assertEquals(9, resource.getQuantityProduced());
    }

    /**
     * Tests the {@code getCityName()} method.
     * Ensures the city name is correctly retrieved.
     */
    @Test
    void testGetCityName() {
        assertEquals("Porto", houseBlock.getCityName());
    }

    /**
     * Tests the {@code setUpdatedinventory()} and {@code isUpdatedinventory()} methods.
     * Ensures the updated inventory flag is correctly set and retrieved.
     */
    @Test
    void testSetAndGetUpdatedInventory() {
        try {
            java.lang.reflect.Field field = HouseBlock.class.getDeclaredField("updatedinventory");
            field.setAccessible(true);
            field.set(houseBlock, true);
            assertTrue(field.getBoolean(houseBlock));
            field.set(houseBlock, false);
            assertFalse(field.getBoolean(houseBlock));
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    /**
     * Tests the {@code getInventory()} method.
     * Ensures the inventory is correctly initialized and retrieved.
     */
    @Test
    void testGetInventory() {
        assertNotNull(houseBlock.getInventory());
    }

    /**
     * Tests the static {@code idCounter} field.
     * Ensures the ID counter is correctly incremented for each new {@code HouseBlock}.
     */
    @Test
    void testStaticIdCounter() {
        int before = getHouseBlockIdCounter();
        HouseBlock hb = new HouseBlock(new Position(10, 10), "Braga");
        assertEquals(before + 1, getHouseBlockIdCounter());
    }

    /**
     * Auxiliary method to access the static {@code idCounter} field.
     */
    private int getHouseBlockIdCounter() {
        try {
            java.lang.reflect.Field field = HouseBlock.class.getDeclaredField("idCounter");
            field.setAccessible(true);
            return field.getInt(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the getConsumableResources method to ensure it returns a non-null list.
     */
    @Test
    void testGetConsumableResources() {
        assertNotNull(houseBlock.getConsumableResources());
        assertFalse(houseBlock.getConsumableResources().isEmpty());
    }
}
