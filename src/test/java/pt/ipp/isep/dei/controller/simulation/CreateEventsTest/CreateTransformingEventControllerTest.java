package pt.ipp.isep.dei.controller.simulation.CreateEventsTest;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.CreateEvents.CreateTransformingEventController;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.TranformingEvent;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateTransformingEventController class.
 */
class CreateTransformingEventControllerTest {
    /**
     * Tests the constructor, getters, and setters of the controller.
     */
    @Test
    void testConstructorAndGettersAndSetters() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        List<ResourcesType> resourcesTypeList = new ArrayList<>();
        scenario.setTypeResourceList(resourcesTypeList);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 5);
        assertEquals(scenario, controller.getScenario());
        assertEquals(map, controller.getActualMap());
        assertEquals(map.getStationList(), controller.getStationList());
        assertEquals(resourcesTypeList, controller.getResourcesListConfiguration());
        assertEquals(5, controller.getActualTime());
        assertNotNull(controller.getTransformingEventList());
        // setters
        List<Event> eventList = new ArrayList<>();
        controller.setTransformingEventList(eventList);
        assertEquals(eventList, controller.getTransformingEventList());
        controller.setTransformingEventList(null); // não deve alterar
        assertEquals(eventList, controller.getTransformingEventList());
        List<Station> newStationList = new ArrayList<>();
        controller.setStationList(newStationList);
        assertEquals(newStationList, controller.getStationList());
        List<ResourcesType> newResourcesList = new ArrayList<>();
        controller.setResourcesListConfiguration(newResourcesList);
        assertEquals(newResourcesList, controller.getResourcesListConfiguration());
        Scenario newScenario = new Scenario(map, "novo", 100, start, end);
        controller.setScenario(newScenario);
        assertEquals(newScenario, controller.getScenario());
        Map newMap = new Map("mapa2", new Size(5, 5));
        controller.setActualMap(newMap);
        assertEquals(newMap, controller.getActualMap());
        controller.setActualTime(99);
        assertEquals(99, controller.getActualTime());
    }

    /**
     * Tests that no events are created when there are no stations or industries.
     */
    @Test
    void testAddEventsToList_Empty() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        scenario.setTypeResourceList(new ArrayList<>());
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 10);
        controller.addEventsToList();
        assertTrue(controller.getTransformingEventList().isEmpty());
    }

    /**
     * Tests event creation for a single TransformingIndustry associated with a station.
     */
    @Test
    void testAddEventsToList_TransformingIndustry() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        scenario.setTypeResourceList(new ArrayList<>());
        // Criar recurso transformador
        TransformingResource transformingResource = new TransformingResource("ResourceA", 100, 1, 10, new ArrayList<>());
        // Criar indústria transformadora
        TransformingIndustry transformingIndustry = new TransformingIndustry("IndustryA", scenario.getName(), transformingResource);
        transformingIndustry.setPosition(new Position(2, 2));
        // Adicionar indústria ao cenário
        scenario.getIndustriesList().add(transformingIndustry);
        // Criar estação na posição da indústria
        Station station = new Station(StationType.STATION, new Position(2, 2), map.getId(), "NORTH", scenario);
        map.getStationList().add(station);
        // Atualizar associações
        station.assignGenerationPosts(scenario);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 10);
        controller.addEventsToList();
        List<Event> events = controller.getTransformingEventList();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof TranformingEvent);
        assertTrue(events.get(0).getName().contains("ResourceA"));
    }

    /**
     * Tests event creation for a single MixedIndustry associated with a station.
     */
    @Test
    void testAddEventsToList_MixedIndustry() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        scenario.setTypeResourceList(new ArrayList<>());
        // Criar recurso transformador
        TransformingResource transformingResource = new TransformingResource("ResourceB", 100, 1, 10, new ArrayList<>());
        // Criar indústria mista
        MixedIndustry mixedIndustry = new MixedIndustry("IndustryB", scenario.getName());
        mixedIndustry.setPosition(new Position(3, 3));
        mixedIndustry.getTransformedResources().add(transformingResource);
        // Adicionar indústria ao cenário
        scenario.getIndustriesList().add(mixedIndustry);
        // Criar estação na posição da indústria
        Station station = new Station(StationType.STATION, new Position(3, 3), map.getId(), "NORTH", scenario);
        map.getStationList().add(station);
        // Atualizar associações
        station.assignGenerationPosts(scenario);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 20);
        controller.addEventsToList();
        List<Event> events = controller.getTransformingEventList();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof TranformingEvent);
        assertTrue(events.get(0).getName().contains("ResourceB"));
    }

    /**
     * Tests that duplicate events are not created for the same scenario.
     */
    @Test
    void testAddEventsToList_DuplicateEvents() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        scenario.setTypeResourceList(new ArrayList<>());
        // Criar recurso transformador
        TransformingResource transformingResource = new TransformingResource("ResourceA", 100, 1, 10, new ArrayList<>());
        // Criar indústria transformadora
        TransformingIndustry transformingIndustry = new TransformingIndustry("IndustryA", scenario.getName(), transformingResource);
        transformingIndustry.setPosition(new Position(2, 2));
        // Adicionar indústria ao cenário
        scenario.getIndustriesList().add(transformingIndustry);
        // Criar estação na posição da indústria
        Station station = new Station(StationType.STATION, new Position(2, 2), map.getId(), "NORTH", scenario);
        map.getStationList().add(station);
        // Atualizar associações
        station.assignGenerationPosts(scenario);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 10);
        controller.addEventsToList();
        controller.addEventsToList(); // não deve duplicar
        List<Event> events = controller.getTransformingEventList();
        assertEquals(1, events.size());
    }

    /**
     * Tests that the controller handles a null station list without errors.
     */
    @Test
    void testAddEventsToList_NullStationList() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 10);
        controller.setStationList(null);
        assertDoesNotThrow(controller::addEventsToList);
        assertTrue(controller.getTransformingEventList().isEmpty());
    }

    /**
     * Tests that the controller handles a null resources list without errors.
     */
    @Test
    void testAddEventsToList_NullResourcesList() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 10);
        controller.setResourcesListConfiguration(null);
        assertDoesNotThrow(controller::addEventsToList);
    }

    /**
     * Tests event creation for multiple stations and multiple industries.
     */
    @Test
    void testAddEventsToList_MultipleStationsAndIndustries() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        scenario.setTypeResourceList(new ArrayList<>());
        // Indústria 1
        TransformingResource tr1 = new TransformingResource("ResourceA", 100, 1, 10, new ArrayList<>());
        TransformingIndustry ti1 = new TransformingIndustry("IndustryA", scenario.getName(), tr1);
        ti1.setPosition(new Position(2, 2));
        scenario.getIndustriesList().add(ti1);
        Station s1 = new Station(StationType.STATION, new Position(2, 2), map.getId(), "NORTH", scenario);
        map.getStationList().add(s1);
        s1.assignGenerationPosts(scenario);
        // Indústria 2
        TransformingResource tr2 = new TransformingResource("ResourceB", 100, 1, 10, new ArrayList<>());
        TransformingIndustry ti2 = new TransformingIndustry("IndustryB", scenario.getName(), tr2);
        ti2.setPosition(new Position(3, 3));
        scenario.getIndustriesList().add(ti2);
        Station s2 = new Station(StationType.STATION, new Position(3, 3), map.getId(), "NORTH", scenario);
        map.getStationList().add(s2);
        s2.assignGenerationPosts(scenario);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 10);
        controller.addEventsToList();
        List<Event> events = controller.getTransformingEventList();
        assertEquals(2, events.size());
    }

    /**
     * Tests event creation for a MixedIndustry with multiple transformed resources.
     */
    @Test
    void testAddEventsToList_MixedIndustryMultipleResources() {
        Map map = new Map("mapa1", new Size(10, 10));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario scenario = new Scenario(map, "cenário1", 1000, start, end);
        scenario.setTypeResourceList(new ArrayList<>());
        TransformingResource tr1 = new TransformingResource("ResourceA", 100, 1, 10, new ArrayList<>());
        TransformingResource tr2 = new TransformingResource("ResourceB", 100, 1, 10, new ArrayList<>());
        MixedIndustry mi = new MixedIndustry("IndustryB", scenario.getName());
        mi.setPosition(new Position(4, 4));
        mi.getTransformedResources().add(tr1);
        mi.getTransformedResources().add(tr2);
        scenario.getIndustriesList().add(mi);
        Station station = new Station(StationType.STATION, new Position(4, 4), map.getId(), "NORTH", scenario);
        map.getStationList().add(station);
        station.assignGenerationPosts(scenario);
        CreateTransformingEventController controller = new CreateTransformingEventController(scenario, 20);
        controller.addEventsToList();
        List<Event> events = controller.getTransformingEventList();
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(e -> e.getName().contains("ResourceA")));
        assertTrue(events.stream().anyMatch(e -> e.getName().contains("ResourceB")));
    }
}
