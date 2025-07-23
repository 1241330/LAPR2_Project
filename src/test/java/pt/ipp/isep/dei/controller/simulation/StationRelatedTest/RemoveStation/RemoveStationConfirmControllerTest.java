package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.RemoveStation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.RemoveStation.RemoveStationConfirmController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RemoveStationConfirmController.
 */
public class RemoveStationConfirmControllerTest {
    private RemoveStationConfirmController controller;
    private Simulation simulation;
    private Station station;
    private RailwayLine railwayLine1;
    private RailwayLine railwayLine2;

    @BeforeEach
    void setUp() {
        controller = new RemoveStationConfirmController();
        Size size = new Size(1, 1);
        Map map = new Map("mapa", size);
        // Registar o mapa no repositório
        MapRepository mapRepository = Repositories.getInstance().getMapRepository();
        mapRepository.addMap(map);
        int mapId = map.getId();
        TimeDate begin = new TimeDate(2020, 1, 1);
        TimeDate end = new TimeDate(2021, 1, 1);
        int dinheiro = 1000;
        Scenario scenario = new Scenario(map, "cenário", dinheiro, begin, end);
        simulation = new Simulation("TestSimulation", scenario);
        station = new Station(StationType.DEPOT, new Position(0, 0), mapId, "NORTH", scenario);
        simulation.getStations().add(station);
        List<Position> path = new ArrayList<>();
        path.add(new Position(0, 0));
        RailwayLineType type = RailwayLineType.SINGLE_ELECTRIFIED;
        TimeDate constructionDate = new TimeDate(2024, 1, 1);
        railwayLine1 = new RailwayLine(path, station, station, type, constructionDate);
        railwayLine2 = new RailwayLine(path, null, null, type, constructionDate);
        simulation.getRailwayLines().add(railwayLine1);
        simulation.getRailwayLines().add(railwayLine2);
        controller.setSimulation(simulation);
        controller.setStation(station);
    }

    /**
     * Tests getSimulation and setSimulation.
     */
    @Test
    void testGetAndSetSimulation() {
        RemoveStationConfirmController c2 = new RemoveStationConfirmController();
        c2.setSimulation(simulation);
        assertEquals(simulation, c2.getSimulation());
    }

    /**
     * Tests getSelectedStation and setStation.
     */
    @Test
    void testGetAndSetSelectedStation() {
        RemoveStationConfirmController c2 = new RemoveStationConfirmController();
        c2.setStation(station);
        assertEquals(station, c2.getSelectedStation());
    }

    /**
     * Tests getStationName returns the correct name.
     */
    @Test
    void testGetStationName() {
        assertEquals(station.getName(), controller.getStationName());
    }

    /**
     * Tests removeSelectedStation removes the station and its railway lines.
     */
    @Test
    void testRemoveSelectedStation_RemovesStationAndRailwayLines() {
        controller.removeSelectedStation();
        assertFalse(simulation.getRailwayLines().contains(railwayLine1));
        assertTrue(simulation.getRailwayLines().contains(railwayLine2));
        assertFalse(simulation.getStations().contains(station));
    }
}
