package pt.ipp.isep.dei.controller.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AddCityController} class.
 * This class tests various scenarios and validations of the public methods in AddCityController.
 */
class AddCityControllerTest {

    /**
     * Controller instance under test.
     */
    private AddCityController controller;

    /**
     * Map ID used in the tests.
     */
    private int mapId;

    /**
     * Initial setup before each test.
     * Creates a new map and initializes the controller with a clean state.
     */
    @BeforeEach
    void setUp() {
        MapRepository mapRepository = Repositories.getInstance().getMapRepository();
        mapRepository.getAllMaps().clear();
        Size size = new Size(10, 10);
        Map map = new Map("PortugalMap", size);
        mapRepository.addMap(map);
        mapId = map.getId();
        controller = new AddCityController(mapId);
    }

    /**
     * Tests if getActualMap returns the correct map instance.
     */
    @Test
    void testGetActualMap() {
        assertNotNull(controller.getActualMap());
        assertEquals(mapId, controller.getActualMap().getId());
    }

    /**
     * Tests the setCity and getCity methods for proper functionality.
     */
    @Test
    void testSetAndGetCity() {
        City city = new City("Lisbon", new Position(1, 1), new ArrayList<>(), new ArrayList<>());
        controller.setCity(city);
        assertEquals(city, controller.getCity());
    }

    /**
     * Tests the getCity and setCity methods with a test city.
     */
    @Test
    void testGetAndSetCity() {
        City city = new City("TestCity", new Position(1, 1), new ArrayList<>(), new ArrayList<>());
        controller.setCity(city);
        assertEquals(city, controller.getCity());
    }

    /**
     * Tests the setCityName and getCityName methods.
     */
    @Test
    void testSetAndGetCityName() {
        controller.setCityName("TestLisbon");
        assertEquals("TestLisbon", controller.getCityName());
    }

    /**
     * Tests the getCityName and setCityName methods with a different city name.
     */
    @Test
    void testGetAndSetCityName() {
        controller.setCityName("Porto");
        assertEquals("Porto", controller.getCityName());
    }

    /**
     * Tests the setPositionCity and getPositionCity methods.
     */
    @Test
    void testSetAndGetPositionCity() {
        Position pos = new Position(2, 3);
        controller.setPositionCity(pos);
        assertEquals(pos, controller.getPositionCity());
    }

    /**
     * Tests the getPositionCity and setPositionCity methods with the same position.
     */
    @Test
    void testGetAndSetPositionCity() {
        Position pos = new Position(2, 3);
        controller.setPositionCity(pos);
        assertEquals(pos, controller.getPositionCity());
    }

    /**
     * Tests the setHouseBlockList and getHouseBlockList methods.
     */
    @Test
    void testSetAndGetHouseBlockList() {
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(1, 1), "A"));
        controller.setHouseBlockList(blocks);
        assertEquals(blocks, controller.getHouseBlockList());
    }

    /**
     * Tests the setNumberHouseBlocks and getNumberHouseBlocks methods.
     */
    @Test
    void testSetAndGetNumberHouseBlocks() {
        controller.setNumberHouseBlocks(7);
        assertEquals(7, controller.getNumberHouseBlocks());
    }

    /**
     * Tests the getNumberHouseBlocks and setNumberHouseBlocks methods with a different value.
     */
    @Test
    void testGetAndSetNumberHouseBlocks() {
        controller.setNumberHouseBlocks(5);
        assertEquals(5, controller.getNumberHouseBlocks());
    }

    /**
     * Tests the getPositionX and getPositionY methods.
     */
    @Test
    void testGetPositionXandY() {
        Position pos = new Position(4, 8);
        controller.setPositionCity(pos);
        assertEquals(4, controller.getPositionX());
        assertEquals(8, controller.getPositionY());
    }

    /**
     * Tests the getPositionX and getPositionY methods with different coordinates.
     */
    @Test
    void testGetPositionXandY_2() {
        Position pos = new Position(7, 8);
        controller.setPositionCity(pos);
        assertEquals(7, controller.getPositionX());
        assertEquals(8, controller.getPositionY());
    }

    /**
     * Tests the getWidthMap and getHeightMap methods.
     */
    @Test
    void testGetWidthAndHeightMap() {
        assertEquals(10, controller.getWidthMap());
        assertEquals(10, controller.getHeightMap());
    }

    /**
     * Tests the getWidthMap and getHeightMap methods again for consistency.
     */
    @Test
    void testGetWidthAndHeightMap_2() {
        assertEquals(10, controller.getWidthMap());
        assertEquals(10, controller.getHeightMap());
    }

    /**
     * Tests the getOccupiedPositionsMap method with an empty map.
     */
    @Test
    void testGetOccupiedPositionsMap() {
        assertNotNull(controller.getOccupiedPositionsMap());
        assertTrue(controller.getOccupiedPositionsMap().isEmpty());
    }

    /**
     * Tests the getOccupiedPositionsMap method again for consistency.
     */
    @Test
    void testGetOccupiedPositionsMap_2() {
        assertNotNull(controller.getOccupiedPositionsMap());
        assertTrue(controller.getOccupiedPositionsMap().isEmpty());
    }

    /**
     * Tests the clearHouseBlockList method.
     */
    @Test
    void testClearHouseBlockList() {
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(1, 1), "A"));
        controller.setHouseBlockList(blocks);
        controller.clearHouseBlockList();
        assertTrue(controller.getHouseBlockList().isEmpty());
    }

    /**
     * Tests the clearHouseBlockList method with a different house block.
     */
    @Test
    void testClearHouseBlockList_2() {
        List<HouseBlock> list = new ArrayList<>();
        list.add(new HouseBlock(new Position(1, 1), "A"));
        controller.setHouseBlockList(list);
        controller.clearHouseBlockList();
        assertTrue(controller.getHouseBlockList().isEmpty());
    }

    /**
     * Tests the getNumberFreePositions method with both empty and occupied maps.
     */
    @Test
    void testGetNumberFreePositions() {
        assertEquals(100, controller.getNumberFreePositions());
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(0, 0), "Porto"));
        City portoCity = new City("Porto", new Position(0, 0), blocks, new ArrayList<>());
        controller.getActualMap().addElement(portoCity);
        assertEquals(99, controller.getNumberFreePositions());
    }

    /**
     * Tests the getNumberFreePositions method with an empty map.
     */
    @Test
    void testGetNumberFreePositions_2() {
        assertEquals(100, controller.getNumberFreePositions());
    }

    /**
     * Tests the setAutomaticHouseBlocks method with valid parameters.
     */
    @Test
    void testSetAutomaticHouseBlocks() {
        controller.setCityName("Viana do Castelo");
        controller.setPositionCity(new Position(5, 5));
        controller.setNumberHouseBlocks(5);
        controller.setAutomaticHouseBlocks();
        assertEquals(5, controller.getHouseBlockList().size());
    }

    /**
     * Tests the setAutomaticHouseBlocks method with a different city name.
     */
    @Test
    void testSetAutomaticHouseBlocks_2() {
        controller.setCityName("AutoCity");
        controller.setPositionCity(new Position(5, 5));
        controller.setNumberHouseBlocks(5);
        controller.setAutomaticHouseBlocks();
        assertEquals(5, controller.getHouseBlockList().size());
    }

    /**
     * Tests the setAutomaticHouseBlocks method with occupied positions.
     */
    @Test
    void testSetAutomaticHouseBlocksWithOccupiedPositions() {
        controller.setCityName("Viana do Castelo");
        controller.setPositionCity(new Position(5, 5));
        controller.setNumberHouseBlocks(3);
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(5, 5), "Viana do Castelo"));
        City portoCity = new City("Porto", new Position(5, 5), blocks, new ArrayList<>());
        controller.getActualMap().addElement(portoCity);
        controller.setAutomaticHouseBlocks();
        assertEquals(3, controller.getHouseBlockList().size());
        for (HouseBlock block : controller.getHouseBlockList()) {
            assertNotEquals(new Position(5, 5), block.getPosition());
        }
    }

    /**
     * Tests the setAutomaticHouseBlocks method with manually occupied positions.
     */
    @Test
    void testSetAutomaticHouseBlocksWithOccupiedPositions_2() {
        controller.setCityName("AutoCity");
        controller.setPositionCity(new Position(5, 5));
        controller.setNumberHouseBlocks(2);
        List<HouseBlock> list = new ArrayList<>();
        list.add(new HouseBlock(new Position(5, 5), "AutoCity"));
        controller.setHouseBlockList(list);
        controller.setAutomaticHouseBlocks();
        assertTrue(controller.getHouseBlockList().size() >= 2);
    }

    /**
     * Tests the addCityToMap method with valid data.
     */
    @Test
    void testAddCityToMap() {
        controller.setCityName("Braga");
        controller.setPositionCity(new Position(2, 2));
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(2, 3), "Braga"));
        controller.setHouseBlockList(blocks);
        boolean added = controller.addCityToMap();
        assertTrue(added);
        City city = controller.getCity();
        assertNotNull(city);
        assertEquals("Braga", city.getName());
    }

    /**
     * Tests the addCityToMap method again with the same data for consistency.
     */
    @Test
    void testAddCityToMap_2() {
        controller.setCityName("Braga");
        controller.setPositionCity(new Position(2, 2));
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(2, 3), "Braga"));
        controller.setHouseBlockList(blocks);
        boolean added = controller.addCityToMap();
        assertTrue(added);
        City city = controller.getCity();
        assertNotNull(city);
        assertEquals("Braga", city.getName());
    }

    /**
     * Tests the addCityToMap method with empty/null fields.
     */
    @Test
    void testAddCityToMapWithEmptyFields() {
        controller.setCityName("");
        controller.setPositionCity(null);
        controller.setHouseBlockList(null);
        boolean added = controller.addCityToMap();
        assertTrue(added);
        assertNotNull(controller.getCity());
    }

    /**
     * Tests the addCityToMap method with null values.
     */
    @Test
    void testAddCityToMapWithNulls() {
        controller.setCityName("");
        controller.setPositionCity(null);
        controller.setHouseBlockList(null);
        boolean result = controller.addCityToMap();
        assertTrue(result);
        assertNotNull(controller.getCity());
    }

    /**
     * Tests the addCityToMap method with an empty house block list.
     */
    @Test
    void testAddCityToMapWithEmptyHouseBlockList() {
        controller.setCityName("TestCity");
        controller.setPositionCity(new Position(2, 2));
        controller.setHouseBlockList(new ArrayList<>());
        boolean result = controller.addCityToMap();
        assertTrue(result);
        assertNotNull(controller.getCity());
    }

    /**
     * Tests the cityFarFromTheCentre method with both near and far positions.
     */
    @Test
    void testCityFarFromTheCentre() {
        controller.setPositionCity(new Position(5, 5));
        Position far = new Position(30, 30);
        assertTrue(controller.cityFarFromTheCentre(far));
        Position near = new Position(6, 6);
        assertFalse(controller.cityFarFromTheCentre(near));
    }

    /**
     * Tests the cityFarFromTheCentre method with a position far from center.
     */
    @Test
    void testCityFarFromTheCentreTrue() {
        controller.setPositionCity(new Position(0, 0));
        assertTrue(controller.cityFarFromTheCentre(new Position(50, 50)));
    }

    /**
     * Tests the cityFarFromTheCentre method with a position near center.
     */
    @Test
    void testCityFarFromTheCentreFalse() {
        controller.setPositionCity(new Position(0, 0));
        assertFalse(controller.cityFarFromTheCentre(new Position(1, 1)));
    }
}