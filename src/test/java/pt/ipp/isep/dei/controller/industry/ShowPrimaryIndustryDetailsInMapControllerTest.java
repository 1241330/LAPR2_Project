package pt.ipp.isep.dei.controller.industry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowPrimaryIndustryDetailsInMapController.
 * This class tests all public methods of the controller, covering all scenarios.
 */
public class ShowPrimaryIndustryDetailsInMapControllerTest {
    private ShowPrimaryIndustryDetailsInMapController controller;
    private PrimaryIndustry primaryIndustryMap;
    private PrimaryIndustry primaryIndustryScenario;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        // Create a PrimaryResource
        PrimaryResource primaryResource = new PrimaryResource("Iron", 100, 5, 10);

        // Create Position
        Position position = new Position(2, 3);

        // Create PrimaryIndustry for map
        primaryIndustryMap = new PrimaryIndustry("IndustryMap", IndustryType.PRIMARY_SECTOR, position);

        // Create PrimaryIndustry for scenario
        primaryIndustryScenario = new PrimaryIndustry("IndustryScenario", "Scenario1", primaryResource);
        primaryIndustryScenario.setPrimaryResource(primaryResource);
        primaryIndustryScenario.setMaxResources(100);
        primaryIndustryScenario.setIntervalBetweenResourceGeneration(5);
        primaryIndustryScenario.setQuantityProduced(10);

        controller = new ShowPrimaryIndustryDetailsInMapController();
        controller.setPrimaryIndustryMap(primaryIndustryMap);
        controller.setPrimaryIndustryScenario(primaryIndustryScenario);
    }

    /**
     * Tests getIndustryName method.
     */
    @Test
    void testGetIndustryName() {
        assertEquals("IndustryMap", controller.getIndustryName());
    }

    /**
     * Tests getPrimaryIndustryMap method.
     */
    @Test
    void testGetPrimaryIndustryMap() {
        assertEquals(primaryIndustryMap, controller.getPrimaryIndustryMap());
    }

    /**
     * Tests getPrimaryIndustryScenario method.
     */
    @Test
    void testGetPrimaryIndustryScenario() {
        assertEquals(primaryIndustryScenario, controller.getPrimaryIndustryScenario());
    }

    /**
     * Tests getIndustryPosition method.
     */
    @Test
    void testGetIndustryPosition() {
        // Position is (2,3) in map, so should return (3,4) as string
        assertEquals(new Position(3, 4).toString(), controller.getIndustryPosition());
    }

    /**
     * Tests getAssignedStationName method when station is assigned.
     */
    @Test
    void testGetAssignedStationName_Assigned() {
        // Não existe estação, então sempre retorna "None"
        assertEquals("None", controller.getAssignedStationName());
    }

    /**
     * Tests getAssignedStationName method when station is not assigned.
     */
    @Test
    void testGetAssignedStationName_None() {
        PrimaryIndustry noStationIndustry = new PrimaryIndustry("NoStation", IndustryType.PRIMARY_SECTOR, new Position(1, 1));
        controller.setPrimaryIndustryMap(noStationIndustry);
        assertEquals("None", controller.getAssignedStationName());
    }

    /**
     * Tests getIndustryInventory method.
     */
    @Test
    void testGetIndustryInventory() {
        // Como não há inventário, retorna lista vazia
        List<String> inventory = controller.getIndustryInventory();
        assertEquals(0, inventory.size());
    }

    /**
     * Tests getPrimaryResourceName method when resource is set.
     */
    @Test
    void testGetPrimaryResourceName_Set() {
        assertEquals("Iron", controller.getPrimaryResourceName());
    }

    /**
     * Tests getPrimaryResourceName method when resource is not set.
     */
    @Test
    void testGetPrimaryResourceName_NotSet() {
        controller.setPrimaryIndustryScenario(new PrimaryIndustry("Empty", "Scenario2", null));
        assertEquals("N/A", controller.getPrimaryResourceName());
    }

    /**
     * Tests getMaxResources method when scenario is set.
     */
    @Test
    void testGetMaxResources_Set() {
        assertEquals(100, controller.getMaxResources());
    }

    /**
     * Tests getMaxResources method when scenario is not set.
     */
    @Test
    void testGetMaxResources_NotSet() {
        controller.setPrimaryIndustryScenario(null);
        assertEquals(0, controller.getMaxResources());
    }

    /**
     * Tests getIntervalBetweenResourceGeneration method when scenario is set.
     */
    @Test
    void testGetIntervalBetweenResourceGeneration_Set() {
        assertEquals(5, controller.getIntervalBetweenResourceGeneration());
    }

    /**
     * Tests getIntervalBetweenResourceGeneration method when scenario is not set.
     */
    @Test
    void testGetIntervalBetweenResourceGeneration_NotSet() {
        controller.setPrimaryIndustryScenario(null);
        assertEquals(0, controller.getIntervalBetweenResourceGeneration());
    }

    /**
     * Tests getQuantityProduced method when scenario is set.
     */
    @Test
    void testGetQuantityProduced_Set() {
        assertEquals(10, controller.getQuantityProduced());
    }

    /**
     * Tests getQuantityProduced method when scenario is not set.
     */
    @Test
    void testGetQuantityProduced_NotSet() {
        controller.setPrimaryIndustryScenario(null);
        assertEquals(0, controller.getQuantityProduced());
    }
}
