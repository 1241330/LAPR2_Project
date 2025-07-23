package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.ShowStation;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation.ShowStationListController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.dto.StationDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowStationListController.
 */
public class ShowStationListControllerTest {
    private ShowStationListController controller;
    private Simulation simulation;
    private Scenario scenario;
    private Map map;
    private List<Station> stationList;

    @BeforeEach
    void setUp() {
        controller = new ShowStationListController();
        map = new Map("TestMap", null);
        scenario = new Scenario(map, "TestScenario", 1000, null, null);
        simulation = new Simulation("TestSimulation", scenario);
        stationList = map.getStationList();
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
        ShowStationListController c2 = new ShowStationListController();
        c2.setSimulation(simulation);
        assertEquals(simulation, c2.getSimulation());
    }

    /**
     * Tests getStationList returns all stations as DTOs from the map.
     */
    @Test
    void testGetStationList() {
        ObservableList<StationDTO> result = controller.getStationList();
        assertEquals(stationList.size(), result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getName().equals("A")));
        assertTrue(result.stream().anyMatch(dto -> dto.getName().equals("B")));
    }

    /**
     * Tests getStationByDTO returns the correct Station instance.
     */
    @Test
    void testGetStationByDTO() {
        ObservableList<StationDTO> dtos = controller.getStationList();
        for (StationDTO dto : dtos) {
            Station found = controller.getStationByDTO(dto);
            assertNotNull(found);
            assertEquals(dto.getName(), found.getName());
        }
    }
}
