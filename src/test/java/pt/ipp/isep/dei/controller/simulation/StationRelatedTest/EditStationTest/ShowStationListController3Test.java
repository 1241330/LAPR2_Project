package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.EditStationTest;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.ShowStationListController3;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowStationListController3.
 */
public class ShowStationListController3Test {
    private ShowStationListController3 controller;
    private SimulationFake simulation;
    private ScenarioFake scenario;
    private MapFake map;
    private List<Station> stationList;

    /**
     * Minimal implementation of Station for testing.
     */
    static class StationFake extends Station {
        public StationFake(String name) { super(name); }
    }

    /**
     * Minimal implementation of Map for testing.
     */
    static class MapFake extends Map {
        private final List<Station> stations;
        public MapFake(List<Station> stations) { super("TestMap", new pt.ipp.isep.dei.domain._Others_.Size(1,1)); this.stations = stations; }
        @Override
        public List<Station> getStationList() { return stations; }
    }

    /**
     * Minimal implementation of Scenario for testing.
     */
    static class ScenarioFake extends Scenario {
        private final Map map;
        public ScenarioFake(Map map) {
            super(map, "Test", 1000, new pt.ipp.isep.dei.domain.Simulation.TimeDate(2020, 1, 1), new pt.ipp.isep.dei.domain.Simulation.TimeDate(2021, 1, 1));
            this.map = map;
        }
        @Override
        public Map getMap() { return map; }
    }

    /**
     * Minimal implementation of Simulation for testing.
     */
    static class SimulationFake extends Simulation {
        private Scenario scenario;
        public SimulationFake(Scenario scenario) {
            super("Fake", scenario);
            this.scenario = scenario;
            // Garante que o campo scenario da superclasse não fica null
            super.setScenario(scenario);
        }
        @Override
        public Scenario getScenario() { return scenario; }
        public void setScenario(Scenario scenario) {
            this.scenario = scenario;
            super.setScenario(scenario);
        }
    }

    @BeforeEach
    void setUp() {
        stationList = new ArrayList<>();
        stationList.add(new StationFake("A"));
        stationList.add(new StationFake("B"));
        map = new MapFake(stationList);
        scenario = new ScenarioFake(map);
        simulation = new SimulationFake(scenario);
        controller = new ShowStationListController3();
        controller.setSimulation(simulation);
    }

    /**
     * Tests setting and getting the simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        ShowStationListController3 c2 = new ShowStationListController3();
        c2.setSimulation(simulation);
        assertEquals(simulation, c2.getSimulation());
    }

    /**
     * Tests getStationList returns all stations from the map.
     */
    @Test
    void testGetStationList() {
        ObservableList<Station> result = controller.getStationList();
        assertEquals(stationList.size(), result.size());
        assertTrue(result.containsAll(stationList));
    }
}
