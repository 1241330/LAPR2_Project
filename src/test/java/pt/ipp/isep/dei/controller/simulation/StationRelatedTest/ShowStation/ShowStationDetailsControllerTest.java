package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.ShowStation;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation.ShowStationDetailsController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.dto.StationDTO;
import pt.ipp.isep.dei.mapper.StationMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowStationDetailsController.
 */
public class ShowStationDetailsControllerTest {
    private ShowStationDetailsController controller;
    private Simulation simulation;
    private Station station;

    @BeforeEach
    void setUp() {
        controller = new ShowStationDetailsController();
        simulation = new Simulation("TestSimulation", null);
        station = new Station("StationA");
        controller.setSimulation(simulation);
        controller.setSelectedStation(station);
    }

    /**
     * Tests getSimulation and setSimulation.
     */
    @Test
    void testGetAndSetSimulation() {
        ShowStationDetailsController c2 = new ShowStationDetailsController();
        c2.setSimulation(simulation);
        assertEquals(simulation, c2.getSimulation());
    }

    /**
     * Tests setSelectedStation and getStationDTO.
     */
    @Test
    void testSetSelectedStationAndGetStationDTO() {
        controller.setSelectedStation(station);
        StationDTO dto = controller.getStationDTO();
        assertNotNull(dto);
        assertEquals(station.getName(), dto.getName());
    }

    /**
     * Tests getStationBuildings returns the buildings from the DTO.
     */
    @Test
    void testGetStationBuildings() {
        // Por padrão, StationDTO.getBuildings() retorna lista vazia
        ObservableList<String> buildings = controller.getStationBuildings();
        assertNotNull(buildings);
        assertTrue(buildings.isEmpty());
    }

    /**
     * Tests getStationCargos returns the cargos from the DTO.
     */
    @Test
    void testGetStationCargos() {
        ObservableList<String> cargos = controller.getStationCargos();
        assertNotNull(cargos);
        assertTrue(cargos.isEmpty());
    }
}
