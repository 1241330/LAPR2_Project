package pt.ipp.isep.dei.domain.City;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link City} class.
 */
class CityTest {

    private City city;
    private List<HouseBlock> houseBlocks;
    private List<Position> houseBlocksPositions;

    /**
     * Sets up the test environment by creating a City instance with predefined house blocks and positions.
     */
    @BeforeEach
    void setUp() {
        houseBlocks = new ArrayList<>();
        houseBlocks.add(new HouseBlock(new Position(1, 1), "Porto"));
        houseBlocks.add(new HouseBlock(new Position(2, 2), "Porto"));
        houseBlocksPositions = new ArrayList<>();
        houseBlocksPositions.add(new Position(1, 1));
        houseBlocksPositions.add(new Position(2, 2));
        city = new City("Porto", new Position(5, 5), houseBlocks, houseBlocksPositions);
    }

    /**
     * Tests the constructor and getter methods of the City class.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("Porto", city.getName());
        assertEquals(new Position(5, 5), city.getPosition());
        assertEquals(houseBlocks, city.getHouseBlocks());
        assertEquals(houseBlocksPositions, city.getHouseBlocksPositions());
        assertEquals("City", city.getType());
        assertTrue(city.toString().contains("Porto"));
        assertTrue(city.toString().contains("(5, 5)"));
    }

    /**
     * Tests the setName method to ensure it updates the city's name correctly.
     */
    @Test
    void testSetName() {
        city.setName("Lisboa");
        assertEquals("Lisboa", city.getName());
    }

    /**
     * Tests the setPosition method to ensure it updates the city's position correctly.
     */
    @Test
    void testSetPosition() {
        Position newPos = new Position(10, 10);
        city.setPosition(newPos);
        assertEquals(newPos, city.getPosition());
    }

    /**
     * Tests the setHouseBlocks method and verifies that positions are updated accordingly.
     */
    @Test
    void testSetHouseBlocks() {
        List<HouseBlock> newBlocks = new ArrayList<>();
        newBlocks.add(new HouseBlock(new Position(3, 3), "Lisboa"));
        city.setHouseBlocks(newBlocks);
        assertEquals(newBlocks, city.getHouseBlocks());
        // Deve atualizar as posições também
        assertEquals(1, city.getHouseBlocksPositions().size());
        assertEquals(new Position(3, 3), city.getHouseBlocksPositions().get(0));
    }

    /**
     * Tests the setHouseBlocksPositions method to ensure it updates the positions correctly.
     */
    @Test
    void testSetHouseBlocksPositions() {
        List<Position> newPositions = new ArrayList<>();
        newPositions.add(new Position(7, 7));
        city.setHouseBlocksPositions(newPositions);
        assertEquals(newPositions, city.getHouseBlocksPositions());
    }

    /**
     * Tests the updateHouseBlocksPositions method to ensure it clears and updates positions correctly.
     */
    @Test
    void testUpdateHouseBlocksPositions() {
        List<HouseBlock> newBlocks = new ArrayList<>();
        newBlocks.add(new HouseBlock(new Position(8, 8), "Lisboa"));
        newBlocks.add(new HouseBlock(new Position(9, 9), "Lisboa"));
        city.setHouseBlocks(newBlocks);
        // Limpa e atualiza
        city.updateHouseBlocksPositions();
        assertEquals(2, city.getHouseBlocksPositions().size());
        assertEquals(new Position(8, 8), city.getHouseBlocksPositions().get(0));
        assertEquals(new Position(9, 9), city.getHouseBlocksPositions().get(1));
    }

    /**
     * Testa se setHouseBlocksPositions adiciona corretamente as posições dos houseBlocks,
     * e se não duplica posições ao ser chamado múltiplas vezes.
     */
    @Test
    void testSetHouseBlocksPositionsNoDuplicates() {
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(10, 10), "TestCity"));
        blocks.add(new HouseBlock(new Position(20, 20), "TestCity"));
        City testCity = new City("TestCity", new Position(0, 0), blocks, new ArrayList<>());
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(10, 10));
        posList.add(new Position(20, 20));
        testCity.setHouseBlocksPositions(posList);
        int initialSize = testCity.getHouseBlocksPositions().size();
        testCity.setHouseBlocksPositions(posList); // Chama novamente
        // O tamanho deve ser igual ao tamanho da lista passada
        assertEquals(posList.size(), testCity.getHouseBlocksPositions().size());
    }

    /**
     * Testa se o ID da cidade é único e incremental.
     */
    @Test
    void testCityIdIsUniqueAndIncremental() {
        int id1 = new City("A", new Position(1, 1), new ArrayList<>(), new ArrayList<>()).getId();
        int id2 = new City("B", new Position(2, 2), new ArrayList<>(), new ArrayList<>()).getId();
        assertTrue(id2 > id1);
    }

    /**
     * Tests the getType method to ensure it returns "City".
     */
    @Test
    void testGetType() {
        assertEquals("City", city.getType());
    }

    /**
     * Tests the toString method to ensure it returns the correct formatted string.
     */
    @Test
    void testToString() {
        String expected = "Porto " + new Position(5, 5);
        assertEquals(expected, city.toString());
    }

    /**
     * Tests the counter of created cities to ensure it increments with each new instance.
     */
    @Test
    void testCounterCreatedCities() {
        int before = City.getCounterCreatedCities();
        new City("Braga", new Position(0, 0), new ArrayList<>(), new ArrayList<>());
        assertEquals(before + 1, City.getCounterCreatedCities());
    }

    /**
     * Tests the getCounterCreatedCities method to ensure it returns the correct count.
     */
    @Test
    void testGetCounterCreatedCities() {
        int before = City.getCounterCreatedCities();
        new City("Braga", new Position(0, 0), new ArrayList<>(), new ArrayList<>());
        assertEquals(before + 1, City.getCounterCreatedCities());
    }
}
