package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.RemoveStation;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.RemoveStation.ShowStationListController2;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowStationListController2.
 */
public class ShowStationListController2Test {
    private ShowStationListController2 controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private List<Station> stationList;

    @BeforeEach
    void setUp() {
        controller = new ShowStationListController2();
        // Instanciar objetos reais
        map = new Map("TestMap", null);
        scenario = new Scenario(map, "TestScenario", 1000, null, null);
        simulation = new Simulation("TestSimulation", scenario);
        stationList = map.getStationList();
        // Adicionar estações reais
        Station s1 = new Station("A");
        Station s2 = new Station("B");
        stationList.add(s1);
        stationList.add(s2);
        controller.setSimulation(simulation);
    }

    /**
     * Tests setSimulation and getSimulation.
     */
    @Test
    void testSetAndGetSimulation() {
        ShowStationListController2 c2 = new ShowStationListController2();
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
