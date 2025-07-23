package pt.ipp.isep.dei.controller.industry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Inventory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowTransformingIndustryDetailsInMapController.
 * This class tests all public methods of the controller, covering all scenarios.
 */
public class ShowTransformingIndustryDetailsInMapControllerTest {
    private ShowTransformingIndustryDetailsInMapController controller;
    private TransformingIndustry transformingIndustryMap;
    private TransformingIndustry transformingIndustryScenario;
    private TransformingResource transformingResource;
    private List<ResourcesType> primaryResources;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        // Create needed resources
        ResourcesType neededResource1 = new ResourcesType("Coal", 50, 2, 5);
        ResourcesType neededResource2 = new ResourcesType("Iron", 100, 3, 10);
        primaryResources = Arrays.asList(neededResource1, neededResource2);

        // Create TransformingResource
        transformingResource = new TransformingResource("Steel", 200, 4, 20, primaryResources);

        // Create Position
        Position position = new Position(5, 7);

        // Create TransformingIndustry for map
        transformingIndustryMap = new TransformingIndustry("IndustryMap", IndustryType.TRANSFORMING, position) {
            // Removido @Override pois não existe na superclasse
            public Station getAssignedStation() { return null; }
            @Override
            public Inventory getInventory() { return null; }
        };

        // Create TransformingIndustry for scenario
        transformingIndustryScenario = new TransformingIndustry("IndustryScenario", "Scenario1", transformingResource);

        controller = new ShowTransformingIndustryDetailsInMapController();
        controller.setTransformingIndustryMap(transformingIndustryMap);
        controller.setTransformingIndustryScenario(transformingIndustryScenario);
    }

    /**
     * Tests getIndustryName method.
     */
    @Test
    void testGetIndustryName() {
        assertEquals("IndustryMap", controller.getIndustryName());
    }

    /**
     * Tests getIndustryPosition method.
     */
    @Test
    void testGetIndustryPosition() {
        // Position is (5,7) in map, so should return (6,8) as string
        assertEquals(new Position(6, 8).toString(), controller.getIndustryPosition());
    }

    /**
     * Tests getAssignedStationName method when station is not assigned.
     */
    @Test
    void testGetAssignedStationName_None() {
        assertEquals("None", controller.getAssignedStationName());
    }

    /**
     * Tests getTransformingResourceName method when resource is set.
     */
    @Test
    void testGetTransformingResourceName_Set() {
        assertEquals("Steel", controller.getTransformingResourceName());
    }

    /**
     * Tests getTransformingResourceName method when resource is not set.
     */
    @Test
    void testGetTransformingResourceName_NotSet() {
        controller.setTransformingIndustryScenario(new TransformingIndustry("Empty", "Scenario2", null));
        assertEquals("N/A", controller.getTransformingResourceName());
    }

    /**
     * Tests getPrimaryResourcesNames method when resources are set.
     */
    @Test
    void testGetPrimaryResourcesNames_Set() {
        assertEquals("Coal, Iron", controller.getPrimaryResourcesNames());
    }

    /**
     * Tests getPrimaryResourcesNames method when resources are not set.
     */
    @Test
    void testGetPrimaryResourcesNames_NotSet() {
        controller.setTransformingIndustryScenario(new TransformingIndustry("Empty", "Scenario2", null));
        assertEquals("N/A", controller.getPrimaryResourcesNames());
    }

    /**
     * Tests getIndustryInventory method when inventory is null.
     */
    @Test
    void testGetIndustryInventory_Null() {
        List<String> inventory = controller.getIndustryInventory();
        assertEquals(0, inventory.size());
    }

    /**
     * Tests getIndustryInventory method when inventory has resources.
     */
    @Test
    void testGetIndustryInventory_WithResources() {
        // Create needed ResourcesType
        ResourcesType copperType = new ResourcesType("Copper", 10, 1, 1);
        ResourcesType goldType = new ResourcesType("Gold", 20, 1, 1);
        List<Resource> resources = Arrays.asList(new Resource(copperType, 1), new Resource(goldType, 2));
        Inventory fakeInventory = new Inventory() {
            @Override
            public List<Resource> getAllResources() { return resources; }
        };
        TransformingIndustry industryWithInventory = new TransformingIndustry("IndustryMap", IndustryType.TRANSFORMING, new Position(1, 1)) {
            // Removido @Override pois não existe na superclasse
            public Station getAssignedStation() { return null; }
            @Override
            public Inventory getInventory() { return fakeInventory; }
        };
        ShowTransformingIndustryDetailsInMapController ctrl = new ShowTransformingIndustryDetailsInMapController();
        ctrl.setTransformingIndustryMap(industryWithInventory);
        List<String> inventory = ctrl.getIndustryInventory();
        assertEquals(2, inventory.size());
        assertTrue(inventory.get(0).contains("Copper"));
        assertTrue(inventory.get(1).contains("Gold"));
    }
}
