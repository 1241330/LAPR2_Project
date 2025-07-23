package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.ShowStation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation.ShowStationDetailsInMapController;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ShowStationDetailsInMapController.
 */
public class ShowStationDetailsInMapControllerTest {
    private ShowStationDetailsInMapController controller;
    private Station station;

    @BeforeEach
    void setUp() {
        controller = new ShowStationDetailsInMapController();
        station = new Station(StationType.STATION, new Position(2, 3), 1, "NORTH", null);
        station.setName("StationA");
        controller.setStation(station);
    }

    /**
     * Tests getStation and setStation.
     */
    @Test
    void testGetAndSetStation() {
        ShowStationDetailsInMapController c2 = new ShowStationDetailsInMapController();
        c2.setStation(station);
        assertEquals(station, c2.getStation());
    }

    /**
     * Tests getStationName returns the correct name.
     */
    @Test
    void testGetStationName() {
        assertEquals("StationA", controller.getStationName());
    }

    /**
     * Tests getStationType returns the correct formatted type for STATION.
     */
    @Test
    void testGetStationType_Station() {
        assertEquals("Station (NORTH)", controller.getStationType());
    }

    /**
     * Tests getStationType returns the correct formatted type for DEPOT.
     */
    @Test
    void testGetStationType_Depot() {
        Station depot = new Station(StationType.DEPOT, new Position(1, 1), 1, null, null);
        depot.setName("DepotA");
        controller.setStation(depot);
        assertEquals("Depot", controller.getStationType());
    }

    /**
     * Tests getStationType returns the correct formatted type for TERMINAL.
     */
    @Test
    void testGetStationType_Terminal() {
        Station terminal = new Station(StationType.TERMINAL, new Position(1, 1), 1, null, null);
        terminal.setName("TerminalA");
        controller.setStation(terminal);
        assertEquals("Terminal", controller.getStationType());
    }

    /**
     * Tests getStationType returns error for unknown type.
     */
    @Test
    void testGetStationType_Unknown() {
        Station unknown = new Station(StationType.DEPOT, new Position(1, 1), 1, null, null) {
            @Override
            public String getStationType() { return "UNKNOWN"; }
        };
        controller.setStation(unknown);
        assertEquals("Error: Unknown Station Type", controller.getStationType());
    }

    /**
     * Tests getStationPosition returns the correct formatted position.
     */
    @Test
    void testGetStationPosition() {
        assertEquals("(3, 4)", controller.getStationPosition());
    }

    /**
     * Tests getAssociations returns an empty list by default.
     */
    @Test
    void testGetAssociations_Empty() {
        List<String> associations = controller.getAssociations();
        assertNotNull(associations);
        assertTrue(associations.isEmpty());
    }

    /**
     * Tests getResourcesRequested returns an empty list by default.
     */
    @Test
    void testGetResourcesRequested_Empty() {
        List<String> resources = controller.getResourcesRequested();
        assertNotNull(resources);
        assertTrue(resources.isEmpty());
    }
}
