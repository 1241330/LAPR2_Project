package pt.ipp.isep.dei.controller.simulation.InSimulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.InSimulation.RunSimulationController;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.GenerationEvent;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RunSimulationControllerTest {
    private RunSimulationController controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private Station station;
    private RailwayLine railwayLine;

    @BeforeEach
    void setUp() {
        Size size = new Size(100, 100);
        map = new Map("TestMap", size);
        TimeDate begin = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        scenario = new Scenario(map, "TestScenario", 1000, begin, end);
        simulation = new Simulation("Sim1", scenario);
        Position pos = new Position(1, 1);
        station = new Station(StationType.STATION, pos, 1, "NORTH", scenario);
        List<Position> path = new ArrayList<>();
        path.add(pos);
        railwayLine = new RailwayLine(path, station, station, RailwayLineType.SINGLE_ELECTRIFIED, begin);
        simulation.getStations().add(station);
        simulation.getRailwayLines().add(railwayLine);
        controller = new RunSimulationController();
        controller.setSimulation(simulation);
    }

    /**
     * Tests the setSimulation and getSimulation methods.
     */
    @Test
    void testSetAndGetSimulation() {
        assertEquals(simulation, controller.getSimulation());
    }

    /**
     * Tests the setCurrentTime and getCurrentTime methods.
     */
    @Test
    void testGetAndSetCurrentTime() {
        controller.setCurrentTime(50);
        assertEquals(50, controller.getCurrentTime());
    }

    /**
     * Tests the getActualBudget method.
     */
    @Test
    void testGetActualBudget() {
        assertEquals(1000, controller.getActualBudget());
    }

    /**
     * Tests the getActualDate method.
     */
    @Test
    void testGetActualDate() {
        controller.setCurrentTime(10);
        TimeDate date = controller.getActualDate();
        assertNotNull(date);
    }

    /**
     * Tests the increaseSimulationSpeed, decreaseSimulationSpeed, and getSimulationSpeed methods.
     */
    @Test
    void testIncreaseAndDecreaseSimulationSpeed() {
        double initialSpeed = controller.getSimulationSpeed();
        controller.increaseSimulationSpeed();
        assertTrue(controller.getSimulationSpeed() > initialSpeed);
        controller.decreaseSimulationSpeed();
        assertEquals(initialSpeed, controller.getSimulationSpeed());
    }

    /**
     * Tests the increaseSimulationSpeed method at its maximum value.
     */
    @Test
    void testIncreaseSimulationSpeedAtMax() {
        for (int i = 0; i < 10; i++) controller.increaseSimulationSpeed();
        double maxSpeed = controller.getSimulationSpeed();
        controller.increaseSimulationSpeed();
        assertEquals(maxSpeed, controller.getSimulationSpeed());
    }

    /**
     * Tests the decreaseSimulationSpeed method at its minimum value.
     */
    @Test
    void testDecreaseSimulationSpeedAtMin() {
        for (int i = 0; i < 10; i++) controller.decreaseSimulationSpeed();
        double minSpeed = controller.getSimulationSpeed();
        controller.decreaseSimulationSpeed();
        assertEquals(minSpeed, controller.getSimulationSpeed());
    }

    /**
     * Tests the getLogs method when the log list is initially empty.
     */
    @Test
    void testGetLogsInitiallyEmpty() {
        assertTrue(controller.getLogs().isEmpty());
    }

    /**
     * Tests the getMapId method.
     */
    @Test
    void testGetMapId() {
        assertEquals(1, controller.getMapId());
    }

    /**
     * Tests the setActualScenario and getActualScenario methods.
     */
    @Test
    void testGetAndSetActualScenario() {
        Size size = new Size(50, 50);
        Map newMap = new Map("Map2", size);
        TimeDate begin = new TimeDate(2025, 2, 1);
        TimeDate end = new TimeDate(2025, 11, 30);
        Scenario newScenario = new Scenario(newMap, "NewScenario", 500, begin, end);
        controller.setActualScenario(newScenario);
        assertEquals(newScenario, controller.getActualScenario());
    }

    /**
     * Tests the setEvents and getEvents methods.
     */
    @Test
    void testGetAndSetEvents() {
        List<Event> events = new ArrayList<>();
        controller.setEvents(events);
        assertEquals(events, controller.getEvents());
    }

    /**
     * Tests the getMaxTime method.
     */
    @Test
    void testGetMaxTime() {
        assertEquals(100, controller.getMaxTime());
    }

    /**
     * Tests the initializeMapModifications method.
     */
    @Test
    void testInitializeMapModifications() {
        controller.initializeMapModifications();
        assertTrue(map.getStationList().contains(station));
        assertTrue(map.getRailwayLines().contains(railwayLine));
    }

    /**
     * Tests the refreshEvents method.
     */
    @Test
    void testRefreshEvents() {
        controller.refreshEvents();
        assertNotNull(controller.getEvents());
    }

    /**
     * Tests the checkEvents method with an empty event list.
     */
    @Test
    void testCheckEventsWithEmptyList() {
        controller.setEvents(new ArrayList<>());
        controller.setCurrentTime(10);
        controller.checkEvents();
        assertTrue(controller.getLogs().isEmpty());
    }

    /**
     * Tests the refreshEvents method to ensure the event list is updated.
     */
    @Test
    void testRefreshEventsUpdatesList() {
        controller.setEvents(new ArrayList<>());
        controller.refreshEvents();
        assertFalse(controller.getEvents().isEmpty());
    }

    /**
     * Tests the checkEvents method with a real GenerationEvent.
     */
    @Test
    void testCheckEventsWithRealGenerationEvent() {
        // Criar uma Industry real
        Industry industry = new Industry("TestIndustry", IndustryType.PRIMARY_SECTOR, new Position(2, 2));
        // Criar um ResourcesType e Resource reais
        ResourcesType resourceType = new ResourcesType("Coal", 100, 1, 10);
        Resource resource = new Resource(resourceType, 5);
        // Criar GenerationEvent para o tempo 10
        GenerationEvent event = new GenerationEvent("GenEvent", 1, 10, resource, industry);
        List<Event> events = new ArrayList<>();
        events.add(event);
        controller.setEvents(events);
        controller.setCurrentTime(10);
        controller.checkEvents();
        // O log deve conter "produzido" ou similar, pois o evento foi disparado
        boolean found = controller.getLogs().stream().anyMatch(s -> s.toLowerCase().contains("produz"));
        assertTrue(found);
    }
}
