package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.EditStationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation.BuildingsPurchaseConfirmationController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Building;
import pt.ipp.isep.dei.domain.Station.BuildingType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BuildingsPurchaseConfirmationController.
 */
public class BuildingsPurchaseConfirmationControllerTest {
    private BuildingsPurchaseConfirmationController controller;
    private SimulationFake simulation;
    private StationFake station;
    private Map map;
    private Scenario scenario;
    private BuildingType buildingType;

    /**
     * Minimal implementation of Simulation for testing.
     */
    static class SimulationFake extends Simulation {
        private int money = 1000;
        public SimulationFake() { super(null, null); }
        @Override
        public int getActualMoney() { return money; }
        @Override
        public void setActualMoney(int money) { this.money = money; }
    }

    /**
     * Minimal implementation of Station for testing.
     */
    static class StationFake extends Station {
        private final List<Building> buildings = new ArrayList<>();
        public StationFake() {
            super(StationType.DEPOT, new pt.ipp.isep.dei.domain._Others_.Position(0,0), 1, "NORTH", null);
        }
        @Override
        public void addBuilding(Building building) { buildings.add(building); }
        public List<Building> getBuildings() { return buildings; }
    }

    @BeforeEach
    void setUp() {
        controller = new BuildingsPurchaseConfirmationController();
        simulation = new SimulationFake();
        station = new StationFake();
        buildingType = pt.ipp.isep.dei.domain.Station.BuildingType.TELEGRAPH;
        controller.setSimulation(simulation);
        controller.setStation(station);
        controller.setMap(null);
        controller.setScenario(null);
        controller.setBuildingType(buildingType);
    }

    /**
     * Tests setting and getting the map.
     */
    @Test
    void testSetAndGetMap() {
        Map map2 = null;
        controller.setMap(map2);
        assertEquals(map2, controller.getMap());
    }

    /**
     * Tests setting and getting the scenario.
     */
    @Test
    void testSetAndGetScenario() {
        Scenario scenario2 = null;
        controller.setScenario(scenario2);
        assertEquals(scenario2, controller.getScenario());
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
     * Tests setting and getting the building type.
     */
    @Test
    void testSetAndGetBuildingType() {
        BuildingType type2 = pt.ipp.isep.dei.domain.Station.BuildingType.TELEPHONE;
        controller.setBuildingType(type2);
        assertEquals(type2, controller.getBuildingType());
    }

    /**
     * Tests addBuildingToStation updates station and simulation money.
     */
    @Test
    void testAddBuildingToStation() {
        int initialMoney = simulation.getActualMoney();
        controller.addBuildingToStation();
        assertEquals(initialMoney - buildingType.getConstructionCost(), simulation.getActualMoney());
        assertEquals(1, station.getBuildings().size());
        assertEquals(buildingType, station.getBuildings().get(0).getType());
    }
}
