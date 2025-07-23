package pt.ipp.isep.dei.controller.simulation.CreateEventsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.CreateEvents.CreateExportEventController;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.ExportEvent;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain._Others_.Size;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateExportEventController class.
 */
public class CreateExportEventControllerTest {
    private CreateExportEventController controller;
    private Scenario scenario;
    private Map map;
    private List<ResourcesType> resources;

    @BeforeEach
    void setUp() {
        resources = new ArrayList<>();
        ResourcesType resource1 = new ResourcesType("Coal", 100, 1, 10);
        ResourcesType resource2 = new ResourcesType("Iron", 100, 1, 10);
        resources.add(resource1);
        resources.add(resource2);
        map = new Map("map1", new Size(10, 10));
        scenario = new Scenario(map, "scenario1", 1000, null, null);
        scenario.getTypeResourceList().addAll(resources);
        controller = new CreateExportEventController(scenario, 1);
    }

    /**
     * Tests constructor and all getters for correct initialization.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals(scenario, controller.getScenario());
        assertEquals(map.getStationList(), controller.getStationList());
        assertEquals(resources, controller.getResourcesListConfiguration());
        assertNotNull(controller.getExportEventList());
    }

    /**
     * Tests all setters for the CreateExportEventController class.
     */
    @Test
    void testSetters() {
        List<Station> newStations = new ArrayList<>();
        controller.setStationList(newStations);
        assertEquals(newStations, controller.getStationList());
        List<ResourcesType> newResources = new ArrayList<>();
        controller.setResourcesListConfiguration(newResources);
        assertEquals(newResources, controller.getResourcesListConfiguration());
        Scenario newScenario = new Scenario(map, "scenario2", 2000, null, null);
        controller.setScenario(newScenario);
        assertEquals(newScenario, controller.getScenario());
        List<Event> newEvents = new ArrayList<>();
        controller.setExportEventList(newEvents);
        assertEquals(newEvents, controller.getExportEventList());
        controller.setExportEventList(null); // Should not change
        assertEquals(newEvents, controller.getExportEventList());
    }

    /**
     * Tests addEventsToList with no stations (should not create any events).
     */
    @Test
    void testAddEventsToListWithNoStations() {
        controller.setStationList(new ArrayList<>());
        controller.addEventsToList();
        assertTrue(controller.getExportEventList().isEmpty());
    }

    /**
     * Tests addEventsToList with a station with no associations (should not create any events).
     */
    @Test
    void testAddEventsToListWithStationNoAssociations() {
        Station station = new Station(null, null, 0, null, scenario) {
            @Override
            public List<StationAssociations> getAllAssociations() {
                return new ArrayList<>();
            }
        };
        List<Station> stationList = new ArrayList<>();
        stationList.add(station);
        controller.setStationList(stationList);
        controller.addEventsToList();
        assertTrue(controller.getExportEventList().isEmpty());
    }

    /**
     * Tests addEventsToList with a MixedIndustry association (should create export events).
     */
    @Test
    void testAddEventsToListWithMixedIndustry() {
        MixedIndustry mixedIndustry = new MixedIndustry("Ind1", null) {
            @Override
            public List<ResourcesType> getExportedResources() {
                return resources;
            }
            @Override
            public MixedIndustry getClonedMixedIndustry(Scenario s) {
                return this;
            }
            @Override
            public String getName() {
                return "Ind1";
            }
        };
        Station station = new Station(null, null, 0, null, scenario) {
            @Override
            public List<StationAssociations> getAllAssociations() {
                List<StationAssociations> list = new ArrayList<>();
                list.add(mixedIndustry);
                return list;
            }
            @Override
            public String getName() {
                return "Station1";
            }
        };
        List<Station> stationList = new ArrayList<>();
        stationList.add(station);
        controller.setStationList(stationList);
        controller.addEventsToList();
        List<Event> events = controller.getExportEventList();
        assertEquals(2, events.size());
        assertInstanceOf(ExportEvent.class, events.get(0));
        assertInstanceOf(ExportEvent.class, events.get(1));
        assertTrue(events.get(0).getName().contains("Export of Coal"));
        assertTrue(events.get(1).getName().contains("Export of Iron"));
    }

    /**
     * Tests addEventsToList with a HouseBlock association (should create export events).
     */
    @Test
    void testAddEventsToListWithHouseBlock() {
        // HouseBlock minimal implementation
        pt.ipp.isep.dei.domain.City.HouseBlock houseBlock = new pt.ipp.isep.dei.domain.City.HouseBlock(null, null) {
            @Override
            public List<ResourcesType> getConsumableResources() {
                return resources;
            }
            @Override
            public String getCityName() {
                return "City1";
            }
            @Override
            public pt.ipp.isep.dei.domain._Others_.Position getPosition() {
                return new pt.ipp.isep.dei.domain._Others_.Position(1, 1);
            }
        };
        Station station = new Station(null, null, 0, null, scenario) {
            @Override
            public List<StationAssociations> getAllAssociations() {
                List<StationAssociations> list = new ArrayList<>();
                list.add(houseBlock);
                return list;
            }
            @Override
            public String getName() {
                return "Station2";
            }
        };
        List<Station> stationList = new ArrayList<>();
        stationList.add(station);
        controller.setStationList(stationList);
        controller.addEventsToList();
        List<Event> events = controller.getExportEventList();
        assertEquals(2, events.size());
        assertInstanceOf(ExportEvent.class, events.get(0));
        assertInstanceOf(ExportEvent.class, events.get(1));
        assertTrue(events.get(0).getName().contains("Export of Coal"));
        assertTrue(events.get(1).getName().contains("Export of Iron"));
    }

    /**
     * Tests that addEventsToList does not duplicate events if called multiple times.
     */
    @Test
    void testAddEventsToListDoesNotDuplicateEvents() {
        testAddEventsToListWithMixedIndustry();
        int initialSize = controller.getExportEventList().size();
        controller.addEventsToList();
        assertEquals(initialSize, controller.getExportEventList().size());
    }

    /**
     * Tests setters with null values (should not throw and should behave as expected).
     */
    @Test
    void testSettersWithNulls() {
        controller.setStationList(null);
        assertNull(controller.getStationList());
        controller.setResourcesListConfiguration(null);
        assertNull(controller.getResourcesListConfiguration());
        controller.setScenario(null);
        assertNull(controller.getScenario());
    }
}
