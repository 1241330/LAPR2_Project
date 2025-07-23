package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.EditStationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationNameController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EditStationNameController.
 */
public class EditStationNameControllerTest {
    private EditStationNameController controller;
    private StationFake station;
    private SimulationFake simulation;

    /**
     * Minimal implementation of Station for testing.
     */
    static class StationFake extends Station {
        private String name;
        public StationFake(String name) {
            super(null, null, 1, null, null);
            this.name = name;
        }
        @Override
        public String getName() { return name; }
        @Override
        public void setName(String name) { this.name = name; }
    }

    /**
     * Minimal implementation of Simulation for testing.
     */
    static class SimulationFake extends Simulation {
        private final List<Station> stations = new ArrayList<>();
        public SimulationFake() { super(null, null); }
        @Override
        public List<Station> getStations() { return stations; }
    }

    @BeforeEach
    void setUp() {
        controller = new EditStationNameController();
        station = new StationFake("OldName");
        simulation = new SimulationFake();
        controller.setStation(station);
        controller.setSimulation(simulation);
    }

    /**
     * Tests getting and setting the new name.
     */
    @Test
    void testGetAndSetNewName() {
        controller.setNewName("NewName");
        assertEquals("NewName", controller.getNewName());
    }

    /**
     * Tests getting and setting the station.
     */
    @Test
    void testGetAndSetStation() {
        StationFake s2 = new StationFake("Another");
        controller.setStation(s2);
        assertEquals(s2, controller.getStation());
    }

    /**
     * Tests getting and setting the simulation.
     */
    @Test
    void testGetAndSetSimulation() {
        SimulationFake sim2 = new SimulationFake();
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests getStationName returns the current station name.
     */
    @Test
    void testGetStationName() {
        assertEquals("OldName", controller.getStationName());
    }

    /**
     * Tests changeStationName updates the station name.
     */
    @Test
    void testChangeStationName() {
        controller.changeStationName("Changed");
        assertEquals("Changed", station.getName());
    }

    /**
     * Tests editName returns false if the new name is the same as the current name.
     */
    @Test
    void testEditName_SameName() {
        controller.setNewName("OldName");
        assertFalse(controller.editName());
    }

    /**
     * Tests editName returns false if the new name already exists in another station.
     */
    @Test
    void testEditName_NameExists() {
        StationFake other = new StationFake("NewName");
        simulation.getStations().add(other);
        controller.setNewName("NewName");
        assertFalse(controller.editName());
    }

    /**
     * Tests editName returns true and changes the name if the new name is valid and unique.
     */
    @Test
    void testEditName_Success() {
        controller.setNewName("UniqueName");
        assertTrue(controller.editName());
        assertEquals("UniqueName", station.getName());
    }
}
