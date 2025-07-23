package pt.ipp.isep.dei.controller.simulation.CreateEventsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.CreateEvents.CreateGenerationEventController;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.GenerationEvent;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain._Others_.Position;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateGenerationEventController class.
 */
public class CreateGenerationEventControllerTest {
    private CreateGenerationEventController controller;
    private Scenario scenario;
    private Map map;
    private List<Station> stations;
    private List<ResourcesType> resources;
    private List<HouseBlockResource> houseBlockResources;

    @BeforeEach
    void setUp() {
        resources = new ArrayList<>();
        houseBlockResources = new ArrayList<>();
        ResourcesType resource1 = new ResourcesType("Coal", 100, 1, 10);
        ResourcesType resource2 = new ResourcesType("Iron", 100, 1, 10);
        resources.add(resource1);
        resources.add(resource2);
        houseBlockResources.add(new HouseBlockResource("Coal", 100, 1, 10));
        houseBlockResources.add(new HouseBlockResource("Iron", 100, 1, 10));
        map = new Map("map1", new Size(10, 10));
        stations = new ArrayList<>();
        map.getStationList().addAll(stations);
        scenario = new Scenario(map, "scenario1", 1000, null, null);
        scenario.getTypeResourceList().addAll(resources);
        scenario.getHouseBlockResourceList().addAll(houseBlockResources);
        controller = new CreateGenerationEventController(scenario, 1);
    }

    /**
     * Tests constructor and all getters for correct initialization.
     */
    @Test
    void testConstructorAndGetters() {
        assertNotNull(controller);
        assertNotNull(controller.getGenerationEventList());
    }

    /**
     * Tests all setters for the CreateGenerationEventController class.
     */
    @Test
    void testSetters() {
        List<Event> newEvents = new ArrayList<>();
        controller.setGenerationEventList(newEvents);
        assertEquals(newEvents, controller.getGenerationEventList());
        controller.setGenerationEventList(null); // Should not change
        assertEquals(newEvents, controller.getGenerationEventList());
    }

    /**
     * Tests addEventsToList with no stations (should not create any events).
     */
    @Test
    void testAddEventsToListWithNoStations() {
        controller = new CreateGenerationEventController(scenario, 1);
        controller.addEventsToList();
        assertTrue(controller.getGenerationEventList().isEmpty());
    }

    /**
     * Tests addEventsToList with a HouseBlock association (should create generation events).
     */
    @Test
    void testAddEventsToListWithHouseBlock() {
        HouseBlock houseBlock = new HouseBlock(null, null) {
            @Override
            public int getId() { return 1; }
        };
        Station station = new Station(null, new Position(1,1), 0, null, scenario) {
            @Override
            public List<StationAssociations> getAllAssociations() {
                List<StationAssociations> list = new ArrayList<>();
                list.add(houseBlock);
                return list;
            }
            @Override
            public String getName() { return "Station1"; }
        };
        map.getStationList().add(station);
        controller.addEventsToList();
        List<Event> events = controller.getGenerationEventList();
        assertFalse(events.isEmpty());
        assertInstanceOf(GenerationEvent.class, events.get(0));
    }

    /**
     * Tests addEventsToList with a PrimaryIndustry association (should create generation events).
     */
    @Test
    void testAddEventsToListWithPrimaryIndustry() {
        IndustryType industryType = IndustryType.PRIMARY_SECTOR; // Valor válido do enum
        Position position = new Position(2,2);
        PrimaryResource primaryResource = new PrimaryResource("Coal", 100, 1, 10);
        PrimaryIndustry primaryIndustry = new PrimaryIndustry("PI1", industryType, position) {
            @Override
            public PrimaryIndustry getClonedPrimaryIndustry(Scenario s) { return this; }
            @Override
            public String getName() { return "PI1"; }
            @Override
            public PrimaryResource getPrimaryResource() { return primaryResource; }
            @Override
            public int getIntervalBetweenResourceGeneration() { return 1; }
        };
        Station station = new Station(null, position, 0, null, scenario) {
            @Override
            public List<StationAssociations> getAllAssociations() {
                List<StationAssociations> list = new ArrayList<>();
                list.add(primaryIndustry);
                return list;
            }
            @Override
            public String getName() { return "Station2"; }
        };
        map.getStationList().add(station);
        controller.addEventsToList();
        List<Event> events = controller.getGenerationEventList();
        assertFalse(events.isEmpty());
        assertInstanceOf(GenerationEvent.class, events.get(0));
    }

    /**
     * Tests addEventsToList with a MixedIndustry association (should create generation events).
     */
    @Test
    void testAddEventsToListWithMixedIndustry() {
        MixedIndustry mixedIndustry = new MixedIndustry("MI1", null) {
            @Override
            public List<ResourcesType> getImportedResources() { return resources; }
            @Override
            public MixedIndustry getClonedMixedIndustry(Scenario s) { return this; }
            @Override
            public String getName() { return "MI1"; }
        };
        Station station = new Station(null, new Position(3,3), 0, null, scenario) {
            @Override
            public List<StationAssociations> getAllAssociations() {
                List<StationAssociations> list = new ArrayList<>();
                list.add(mixedIndustry);
                return list;
            }
            @Override
            public String getName() { return "Station3"; }
        };
        map.getStationList().add(station);
        controller.addEventsToList();
        List<Event> events = controller.getGenerationEventList();
        assertFalse(events.isEmpty());
        assertInstanceOf(GenerationEvent.class, events.get(0));
    }

    /**
     * Tests that addEventsToList does not duplicate events if called multiple times.
     */
    @Test
    void testAddEventsToListDoesNotDuplicateEvents() {
        testAddEventsToListWithHouseBlock();
        int initialSize = controller.getGenerationEventList().size();
        controller.addEventsToList();
        assertEquals(initialSize, controller.getGenerationEventList().size());
    }
}
