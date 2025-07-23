package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.EditStationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.EditStationBuildingsController;
import pt.ipp.isep.dei.domain.Station.Building;
import pt.ipp.isep.dei.domain.Station.BuildingType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EditStationBuildingsController.
 */
public class EditStationBuildingsControllerTest {
    private EditStationBuildingsController controller;
    private SimulationFake simulation;
    private StationFake station;

    /**
     * Minimal implementation of Simulation for testing.
     */
    static class SimulationFake extends Simulation {
        private int money = 1000;
        private int currentTime = 0;
        public SimulationFake() { super(null, null); }
        @Override
        public int getActualMoney() { return money; }
        @Override
        public void setActualMoney(int money) { this.money = money; }
        @Override
        public int getCurrentTime() { return currentTime; }
        public void setCurrentTime(int time) { this.currentTime = time; }
    }

    /**
     * Minimal implementation of Station for testing.
     */
    static class StationFake extends Station {
        private final List<Building> buildings = new ArrayList<>();
        public StationFake() { super(pt.ipp.isep.dei.domain.Station.StationType.DEPOT, null, 1, "NORTH", null); }
        @Override
        public void addBuilding(Building building) { buildings.add(building); }
        @Override
        public List<Building> getBuildings() { return buildings; }
    }

    @BeforeEach
    void setUp() {
        controller = new EditStationBuildingsController();
        simulation = new SimulationFake();
        station = new StationFake();
        controller.setSimulation(simulation);
        controller.setStation(station);
    }

    /**
     * Tests setting and getting the simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        SimulationFake sim2 = new SimulationFake();
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests setting and getting the station.
     */
    @Test
    void testSetAndGetStation() {
        StationFake station2 = new StationFake();
        controller.setStation(station2);
        assertEquals(station2, controller.getStation());
    }

    /**
     * Tests getAvailableBuildingTypes returns all types when no buildings exist and time is 0.
     */
    @Test
    void testGetAvailableBuildingTypes_AllAvailable() {
        simulation.setCurrentTime(0);
        ObservableList<BuildingType> available = controller.getAvailableBuildingTypes();
        for (BuildingType type : available) {
            assertTrue(type.getOperationYearInDays() <= simulation.getCurrentTime());
        }
    }

    /**
     * Tests getAvailableBuildingTypes removes already existing buildings.
     */
    @Test
    void testGetAvailableBuildingTypes_RemovesExisting() {
        simulation.setCurrentTime(0);
        station.addBuilding(new Building(BuildingType.TELEGRAPH));
        ObservableList<BuildingType> available = controller.getAvailableBuildingTypes();
        for (BuildingType type : available) {
            assertNotEquals(BuildingType.TELEGRAPH, type);
        }
    }

    /**
     * Tests convertToObservableList returns an ObservableList with the same elements.
     */
    @Test
    void testConvertToObservableList() {
        List<BuildingType> list = new ArrayList<>();
        list.add(BuildingType.TELEGRAPH);
        list.add(BuildingType.TELEPHONE);
        ObservableList<BuildingType> obs = controller.convertToObservableList(list);
        assertEquals(list.size(), obs.size());
        assertTrue(obs.containsAll(list));
    }

    /**
     * Tests checkIfHasMoney returns true if enough money, false otherwise.
     */
    @Test
    void testCheckIfHasMoney() {
        simulation.setActualMoney(1000);
        assertTrue(controller.checkIfHasMoney(BuildingType.TELEGRAPH));
        simulation.setActualMoney(10);
        assertFalse(controller.checkIfHasMoney(BuildingType.TELEGRAPH));
    }

    /**
     * Tests checkIfHasMoney returns false if not a BuildingType.
     */
    @Test
    void testCheckIfHasMoney_NotBuildingType() {
        assertFalse(controller.checkIfHasMoney("not a building type"));
    }

    /**
     * Tests addBuildingToStation adds building and deducts money.
     */
    @Test
    void testAddBuildingToStation() {
        int initialMoney = simulation.getActualMoney();
        controller.addBuildingToStation(BuildingType.TELEGRAPH);
        assertEquals(initialMoney - BuildingType.TELEGRAPH.getConstructionCost(), simulation.getActualMoney());
        assertEquals(1, station.getBuildings().size());
        assertEquals(BuildingType.TELEGRAPH, station.getBuildings().get(0).getType());
    }
}
