package pt.ipp.isep.dei.controller.city.houseBlock;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowHouseBlockDetailsInMapController.
 * This class tests all public methods of the controller, ensuring correct behavior for each scenario.
 */
public class ShowHouseBlockDetailsInMapControllerTest {
    /**
     * Tests setHouseBlock and getCityName methods.
     * Verifies that the city name is correctly returned after setting the HouseBlock.
     */
    @Test
    public void testSetHouseBlockAndGetCityName() {
        HouseBlock houseBlock = new HouseBlock(new Position(2, 3), "Porto");
        ShowHouseBlockDetailsInMapController controller = new ShowHouseBlockDetailsInMapController();
        controller.setHouseBlock(houseBlock);
        assertEquals("Porto", controller.getCityName());
    }

    /**
     * Tests setScenario and getHouseBlockProductions methods.
     * Verifies that the productions list is correctly returned from the scenario.
     */
    @Test
    public void testSetScenarioAndGetHouseBlockProductions() {
        // Dummy values for Scenario constructor
        pt.ipp.isep.dei.domain.Map.Map map = null;
        String name = "TestScenario";
        int initialMoney = 1000;
        pt.ipp.isep.dei.domain.Simulation.TimeDate beginningDate = null;
        pt.ipp.isep.dei.domain.Simulation.TimeDate endDate = null;
        Scenario scenario = new Scenario(map, name, initialMoney, beginningDate, endDate);
        HouseBlockResource resource1 = new HouseBlockResource("Water", 100, 10, 5);
        HouseBlockResource resource2 = new HouseBlockResource("Food", 50, 5, 2);
        scenario.getHouseBlockResourceList().add(resource1);
        scenario.getHouseBlockResourceList().add(resource2);
        ShowHouseBlockDetailsInMapController controller = new ShowHouseBlockDetailsInMapController();
        controller.setScenario(scenario);
        controller.setHouseBlock(new HouseBlock(new Position(0, 0), "Porto"));
        List<String> productions = controller.getHouseBlockProductions();
        assertTrue(productions.contains(resource1.toString()));
        assertTrue(productions.contains(resource2.toString()));
    }

    /**
     * Tests getPosition method.
     * Verifies that the position is incremented by 1 in both axes and returned as a string.
     */
    @Test
    public void testGetPosition() {
        Position pos = new Position(4, 7);
        HouseBlock houseBlock = new HouseBlock(pos, "Porto");
        ShowHouseBlockDetailsInMapController controller = new ShowHouseBlockDetailsInMapController();
        controller.setHouseBlock(houseBlock);
        String expected = new Position(5, 8).toString();
        assertEquals(expected, controller.getPosition());
    }

    /**
     * Tests getAssignedStationName method when a station is assigned.
     * Verifies that the assigned station name is returned.
     */
    @Test
    public void testGetAssignedStationNameWithStation() {
        HouseBlock houseBlock = new HouseBlock(new Position(1, 1), "Porto");
        // Dummy values for Station constructor
        StationType type = StationType.STATION;
        Position stationPos = new Position(0, 0);
        int idMap = 1;
        String direction = "N";
        pt.ipp.isep.dei.domain.Map.Map map = null;
        String name = "TestScenario";
        int initialMoney = 1000;
        pt.ipp.isep.dei.domain.Simulation.TimeDate beginningDate = null;
        pt.ipp.isep.dei.domain.Simulation.TimeDate endDate = null;
        Scenario scenario = new Scenario(map, name, initialMoney, beginningDate, endDate);
        Station station = new Station(type, stationPos, idMap, direction, scenario);
        houseBlock.setAssignedStation(station);
        ShowHouseBlockDetailsInMapController controller = new ShowHouseBlockDetailsInMapController();
        controller.setHouseBlock(houseBlock);
        assertEquals(station.getName(), controller.getAssignedStationName());
    }

    /**
     * Tests getAssignedStationName method when no station is assigned.
     * Verifies that "None" is returned.
     */
    @Test
    public void testGetAssignedStationNameWithoutStation() {
        HouseBlock houseBlock = new HouseBlock(new Position(1, 1), "Porto");
        ShowHouseBlockDetailsInMapController controller = new ShowHouseBlockDetailsInMapController();
        controller.setHouseBlock(houseBlock);
        assertEquals("None", controller.getAssignedStationName());
    }

    /**
     * Tests getHouseBlockInventory method.
     * Verifies that the inventory list is correctly returned as strings.
     */
    @Test
    public void testGetHouseBlockInventory() {
        HouseBlock houseBlock = new HouseBlock(new Position(1, 1), "Porto");
        // Criar ResourcesType para os recursos
        ResourcesType waterType = new ResourcesType("Water", 100, 10, 5);
        ResourcesType foodType = new ResourcesType("Food", 50, 5, 2);
        Resource res1 = new Resource(waterType, 10);
        Resource res2 = new Resource(foodType, 5);
        houseBlock.getInventory().addResource(res1);
        houseBlock.getInventory().addResource(res2);
        ShowHouseBlockDetailsInMapController controller = new ShowHouseBlockDetailsInMapController();
        controller.setHouseBlock(houseBlock);
        List<String> inventoryList = controller.getHouseBlockInventory();
        assertTrue(inventoryList.contains(res1.toString()));
        assertTrue(inventoryList.contains(res2.toString()));
    }
}
