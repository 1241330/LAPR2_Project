package pt.ipp.isep.dei.controller.industry;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain._Others_.Inventory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowMixedIndustryDetailsInMapController.
 * This class tests all public methods of the controller, ensuring correct behavior for each scenario.
 */
public class ShowMixedIndustryDetailsInMapControllerTest {
    /**
     * Tests setMixedIndustryMap and getIndustryName methods.
     * Verifies that the industry name is correctly returned after setting the MixedIndustry.
     */
    @Test
    public void testSetMixedIndustryMapAndGetIndustryName() {
        MixedIndustry industry = new MixedIndustry("Ind1", IndustryType.MIXED, new Position(2, 3));
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryMap(industry);
        assertEquals("Ind1", controller.getIndustryName());
    }

    /**
     * Tests getIndustryPosition method.
     * Verifies that the position is incremented by 1 in both axes and returned as a string.
     */
    @Test
    public void testGetIndustryPosition() {
        MixedIndustry industry = new MixedIndustry("Ind2", IndustryType.MIXED, new Position(4, 7));
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryMap(industry);
        String expected = new Position(5, 8).toString();
        assertEquals(expected, controller.getIndustryPosition());
    }

    /**
     * Tests getAssignedStationName when a station is assigned.
     * Verifies that the assigned station name is returned.
     */
    @Test
    public void testGetAssignedStationNameWithStation() {
        // Criar Station válido
        Scenario scenario = new Scenario(null, "TestScenario", 1000, null, null);
        Station station = new Station(StationType.STATION, new Position(0, 0), 1, "N", scenario);
        station.setName("Central Station");
        MixedIndustry industry = new MixedIndustry("Ind3", IndustryType.MIXED, new Position(1, 1));
        industry.setAssignedStation(station);
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryMap(industry);
        assertEquals("Central Station", controller.getAssignedStationName());
    }

    /**
     * Tests getAssignedStationName when no station is assigned.
     * Verifies that "None" is returned.
     */
    @Test
    public void testGetAssignedStationNameWithoutStation() {
        MixedIndustry industry = new MixedIndustry("Ind4", IndustryType.MIXED, new Position(1, 1));
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryMap(industry);
        assertEquals("None", controller.getAssignedStationName());
    }

    /**
     * Tests setMixedIndustryScenario and getExportedResourcesNames methods.
     * Verifies that the exported resources names are correctly returned from the scenario.
     */
    @Test
    public void testSetMixedIndustryScenarioAndGetExportedResourcesNames() {
        MixedIndustry industryScenario = new MixedIndustry("Ind5", IndustryType.MIXED, new Position(0, 0));
        ResourcesType water = new ResourcesType("Water", 100, 10, 5);
        ResourcesType food = new ResourcesType("Food", 50, 5, 2);
        industryScenario.addExportedResource(water);
        industryScenario.addExportedResource(food);
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryScenario(industryScenario);
        List<String> names = controller.getExportedResourcesNames();
        assertTrue(names.contains("Water"));
        assertTrue(names.contains("Food"));
    }

    /**
     * Tests getImportedResourcesNames method.
     * Verifies that the imported resources names are correctly returned from the scenario.
     */
    @Test
    public void testGetImportedResourcesNames() {
        MixedIndustry industryScenario = new MixedIndustry("Ind6", IndustryType.MIXED, new Position(0, 0));
        ResourcesType iron = new ResourcesType("Iron", 100, 10, 5);
        ResourcesType coal = new ResourcesType("Coal", 50, 5, 2);
        industryScenario.addImportedResource(iron);
        industryScenario.addImportedResource(coal);
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryScenario(industryScenario);
        List<String> names = controller.getImportedResourcesNames();
        assertTrue(names.contains("Iron"));
        assertTrue(names.contains("Coal"));
    }

    /**
     * Tests getTransformedResourcesNames method.
     * Verifies that the transformed resources names are correctly returned from the scenario.
     */
    @Test
    public void testGetTransformedResourcesNames() {
        MixedIndustry industryScenario = new MixedIndustry("Ind7", IndustryType.MIXED, new Position(0, 0));
        // Criar TransformingResource
        TransformingResource steel = new TransformingResource("Steel", 100, 10, 5, java.util.Collections.emptyList());
        industryScenario.addTransformingResource(steel);
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryScenario(industryScenario);
        List<String> names = controller.getTransformedResourcesNames();
        assertTrue(names.contains("Steel"));
    }

    /**
     * Tests getIndustryInventory method.
     * Verifies that the inventory list is correctly returned as strings.
     */
    @Test
    public void testGetIndustryInventory() {
        Inventory inventory = new Inventory();
        ResourcesType waterType = new ResourcesType("Water", 100, 10, 5);
        ResourcesType foodType = new ResourcesType("Food", 50, 5, 2);
        Resource res1 = new Resource(waterType, 10);
        Resource res2 = new Resource(foodType, 5);
        inventory.addResource(res1);
        inventory.addResource(res2);
        MixedIndustry industry = new MixedIndustry("Ind8", IndustryType.MIXED, new Position(1, 1));
        industry.getInventory().addResource(res1);
        industry.getInventory().addResource(res2);
        ShowMixedIndustryDetailsInMapController controller = new ShowMixedIndustryDetailsInMapController();
        controller.setMixedIndustryMap(industry);
        List<String> inventoryList = controller.getIndustryInventory();
        assertTrue(inventoryList.contains(res1.toString()));
        assertTrue(inventoryList.contains(res2.toString()));
    }
}
