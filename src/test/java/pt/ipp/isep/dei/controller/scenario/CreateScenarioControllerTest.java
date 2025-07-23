package pt.ipp.isep.dei.controller.scenario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.repository.HouseBlockResourceRepository;
import pt.ipp.isep.dei.repository.PrimaryResourceRepository;
import pt.ipp.isep.dei.repository.TransformingResourceRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateScenarioController class.
 * <p>
 * This class tests all public methods and scenarios of the CreateScenarioController, including:
 * <ul>
 *     <li>Scenario creation and removal</li>
 *     <li>Setting and clearing available locomotives and resources</li>
 *     <li>Industry and resource configuration methods</li>
 *     <li>Getters and setters for all fields</li>
 *     <li>Validation of null arguments and empty lists</li>
 *     <li>Checking for existing scenario names in a map</li>
 * </ul>
 * The tests ensure that the controller behaves as expected in both normal and edge cases.
 */
class CreateScenarioControllerTest {
    private CreateScenarioController controller;
    private Map map;
    private TimeDate start;
    private TimeDate end;
    private HouseBlockResourceRepository houseBlockResourceRepository;
    private PrimaryResourceRepository primaryResourceRepository;
    private TransformingResourceRepository transformingResourceRepository;
    private List<HouseBlockResource> houseBlockResourceList;
    private List<PrimaryResource> primaryResourceList;
    private List<TransformingResource> transformingResourceList;
    private List<ResourcesType> allResourceListIndustry;
    private List<ResourcesType> allResourcesList;
    private List<FuelType> fuelTypeList;
    private List<ResourcesType> resourceListImport;
    private List<ResourcesType> resourceListExport;
    private List<ResourcesType> resourceListTransforming;
    private List<FuelType> availableFuelTypeList;
    private List<HouseBlockResource> availableHouseBlockResourceList;

    /**
     * Sets up the test environment before each test.
     * Initializes the controller, map, resources, and lists.
     */
    @BeforeEach
    void setUp() {
        controller = new CreateScenarioController();
        map = new Map("TestMap", new pt.ipp.isep.dei.domain._Others_.Size(10, 10));
        start = new TimeDate(2024, 1, 1);
        end = new TimeDate(2024, 12, 31);
        controller.setActualMap(map);
        controller.setName("TestScenario");
        controller.setInitialDate(start);
        controller.setEndDate(end);
        controller.setInitialMoney(1000);
        houseBlockResourceRepository = controller.getHouseBlockResourceRepository();
        primaryResourceRepository = controller.getPrimaryResourceRepository();
        transformingResourceRepository = controller.getTransformingResourceRepository();
        houseBlockResourceList = new ArrayList<>(houseBlockResourceRepository.getCopyOfHouseBlockResources());
        primaryResourceList = new ArrayList<>(primaryResourceRepository.getCopyPrimaryResources());
        transformingResourceList = new ArrayList<>(transformingResourceRepository.getCopyTransformingResources());
        allResourceListIndustry = controller.getAllResourceListIndustry();
        allResourcesList = controller.getAllResourcesList();
        fuelTypeList = new ArrayList<>(List.of(FuelType.values()));
        resourceListImport = controller.getResourceListImport();
        resourceListExport = controller.getResourceListExport();
        resourceListTransforming = controller.getResourceListTransforming();
        availableFuelTypeList = controller.getAvailableFuelTypeList();
        availableHouseBlockResourceList = controller.getAvailableHouseBlockResourceList();
        if (!houseBlockResourceList.isEmpty()) houseBlockResourceList.get(0);
        if (!primaryResourceList.isEmpty()) primaryResourceList.get(0);
        if (!transformingResourceList.isEmpty()) transformingResourceList.get(0);
        if (!allResourceListIndustry.isEmpty()) allResourceListIndustry.get(0);
        if (!fuelTypeList.isEmpty()) availableFuelTypeList.add(fuelTypeList.get(0));
    }

    /**
     * Tests successful scenario creation and association with the map.
     */
    @Test
    void testCreateScenarioSuccess() {
        controller.createScenario();
        Scenario scenario = controller.getScenario();
        assertNotNull(scenario);
        assertEquals("TestScenario", scenario.getName());
        assertEquals(map, scenario.getMap());
        assertTrue(map.getScenarios().contains(scenario));
    }

    /**
     * Tests that creating a scenario with a null initial date throws an exception.
     */
    @Test
    void testCreateScenarioNullInitialDate() {
        controller.setInitialDate(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.createScenario());
        assertTrue(ex.getMessage().contains("Initial date cannot be null"));
    }

    /**
     * Tests that creating a scenario with a null end date throws an exception.
     */
    @Test
    void testCreateScenarioNullEndDate() {
        controller.setEndDate(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.createScenario());
        assertTrue(ex.getMessage().contains("End date cannot be null"));
    }

    /**
     * Tests removing a scenario from the map and controller.
     */
    @Test
    void testRemoveScenario() {
        controller.createScenario();
        Scenario scenario = controller.getScenario();
        assertTrue(map.getScenarios().contains(scenario));
        controller.removeScenario();
        assertFalse(map.getScenarios().contains(scenario));
        assertNull(controller.getScenario());
    }

    /**
     * Tests if there are active maps in the repository.
     */
    @Test
    void testThereAreActiveMaps() {
        assertTrue(controller.thereAreActiveMaps());
    }

    /**
     * Tests listing all maps from the repository.
     */
    @Test
    void testListMaps() {
        assertNotNull(controller.listMaps());
        assertTrue(controller.listMaps().contains(map));
    }

    /**
     * Tests getting the ID of the current map.
     */
    @Test
    void testGetIdMap() {
        assertEquals(map.getId(), controller.getIdMap());
        controller.setActualMap(null);
        assertEquals(0, controller.getIdMap());
    }

    /**
     * Tests checking if a scenario name already exists in the map.
     */
    @Test
    void testAlreadyExistsNameScenarioInMap() {
        controller.createScenario();
        assertTrue(controller.alreadyExistsNameScenarioInMap("TestScenario"));
        assertFalse(controller.alreadyExistsNameScenarioInMap("OtherScenario"));
    }

    /**
     * Tests that creating a scenario with a duplicate name throws an exception.
     */
    @Test
    void testCreateScenarioWithDuplicateNameThrows() {
        controller.createScenario();
        controller.setScenario(null); // Limpa referência para forçar novo cenário
        controller.setName("TestScenario");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.createScenario());
        assertTrue(ex.getMessage().toLowerCase().contains("already exists"));
    }

    /**
     * Tests that creating a scenario with a null map throws an exception.
     */
    @Test
    void testCreateScenarioNullMap() {
        controller.setActualMap(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.createScenario());
        assertTrue(ex.getMessage().toLowerCase().contains("map"));
    }

    /**
     * Tests that creating a scenario with a null name throws an exception.
     */
    @Test
    void testCreateScenarioNullName() {
        controller.setName(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.createScenario());
        assertTrue(ex.getMessage().toLowerCase().contains("name"));
    }

    /**
     * Tests that creating a scenario with an empty name throws an exception.
     */
    @Test
    void testCreateScenarioEmptyName() {
        controller.setName("");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> controller.createScenario());
        assertTrue(ex.getMessage().toLowerCase().contains("name"));
    }

    /**
     * Tests setting and clearing the available locomotive list in the scenario.
     */
    @Test
    void testSetLocomotiveListAvailableAndClear() {
        controller.createScenario();
        controller.setAvailableFuelTypeList(fuelTypeList);
        controller.setLocomotiveListAvailable();
        assertNotNull(controller.getScenario().getAvailableLocomotiveList());
        controller.clearLocomotiveListAvailable();
        assertTrue(controller.getScenario().getAvailableLocomotiveList().isEmpty());
    }

    /**
     * Tests setting the resources list in the scenario.
     */
    @Test
    void testSetResourcesListScenario() {
        controller.createScenario();
        controller.setResourcesListScenario();
        assertNotNull(controller.getScenario().getTypeResourceList());
    }

    /**
     * Tests setting the available house block resource list in the scenario.
     */
    @Test
    void testSetHouseBlockResourceListScenario() {
        controller.createScenario();
        controller.setAvailableHouseBlockResourceList(houseBlockResourceList);
        controller.setHouseBlockResourceListScenario();
        assertNotNull(controller.getScenario().getHouseBlockResourceList());
    }

    /**
     * Tests setting the default house block resource list in the scenario.
     */
    @Test
    void testSetHouseBlockResourceListScenarioDefault() {
        controller.createScenario();
        controller.setHouseBlockResourceListScenarioDefault();
        assertNotNull(controller.getScenario().getHouseBlockResourceList());
    }

    /**
     * Tests setting and clearing the available house block resource list in the controller.
     */
    @Test
    void testSetAndClearAvailableHouseBlockResourceList() {
        controller.setAvailableHouseBlockResourceList(houseBlockResourceList);
        assertEquals(houseBlockResourceList, controller.getAvailableHouseBlockResourceList());
        controller.clearAvailableHouseBlockResourceList();
        assertTrue(controller.getAvailableHouseBlockResourceList().isEmpty());
    }

    /**
     * Tests if the import resource list is empty.
     */
    @Test
    void testResourceListImportIsEmpty() {
        resourceListImport.clear();
        assertTrue(controller.resourceListImportIsEmpty());
    }

    /**
     * Tests if the export resource list is empty.
     */
    @Test
    void testResourceListExportIsEmpty() {
        resourceListExport.clear();
        assertTrue(controller.resourceListExportIsEmpty());
    }

    /**
     * Tests if the transforming resource list is empty.
     */
    @Test
    void testResourceListTransformingIsEmpty() {
        resourceListTransforming.clear();
        assertTrue(controller.resourceListTransformingIsEmpty());
    }

    /**
     * Tests all setters and getters with null values to ensure proper handling.
     */
    @Test
    void testSettersAndGettersNulls() {
        controller.setActualMap(null);
        controller.setName(null);
        controller.setInitialDate(null);
        controller.setEndDate(null);
        controller.setInitialMoney(0);
        controller.setScenario(null);
        controller.setFuelTypeList(null);
        controller.setResourceListImport(null);
        controller.setResourceListExport(null);
        controller.setResourceListTransforming(null);
        controller.setAvailableFuelTypeList(null);
        controller.setAvailableHouseBlockResourceList(null);
        controller.setPrimaryResourceRepository(null);
        controller.setTransformingResourceRepository(null);
        controller.setHouseBlockResourceRepository(null);
        controller.setPrimaryResourceList(null);
        controller.setTransformingResourceList(null);
        controller.setAllResourceListIndustry(null);
        controller.setAllResourcesList(null);
        controller.setHouseBlockResourceList(null);
        assertNull(controller.getActualMap());
        assertNull(controller.getName());
        assertNull(controller.getInitialDate());
        assertNull(controller.getEndDate());
        assertEquals(0, controller.getInitialMoney());
        assertNull(controller.getScenario());
        assertNull(controller.getFuelTypeList());
        assertNull(controller.getResourceListImport());
        assertNull(controller.getResourceListExport());
        assertNull(controller.getResourceListTransforming());
        assertNull(controller.getAvailableFuelTypeList());
        assertNull(controller.getAvailableHouseBlockResourceList());
        assertNull(controller.getPrimaryResourceRepository());
        assertNull(controller.getTransformingResourceRepository());
        assertNull(controller.getHouseBlockResourceRepository());
        assertNull(controller.getPrimaryResourceList());
        assertNull(controller.getTransformingResourceList());
        assertNull(controller.getAllResourceListIndustry());
        assertNull(controller.getAllResourcesList());
        assertNull(controller.getHouseBlockResourceList());
    }
}
