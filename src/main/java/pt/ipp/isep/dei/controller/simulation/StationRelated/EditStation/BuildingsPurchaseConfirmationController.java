package pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Building;
import pt.ipp.isep.dei.domain.Station.BuildingType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Train.Locomotive;

import java.util.Iterator;
import java.util.List;

/**
 * Controller responsible for confirming the purchase of buildings for a station in a simulation.
 * Provides methods to set and retrieve the simulation, scenario, map, station, and building type.
 * Also allows adding a building to a station and updating the simulation's money accordingly.
 */
public class BuildingsPurchaseConfirmationController {

    /**
     * The simulation in which the building purchase is being made.
     */
    private Simulation simulation;

    /**
     * The scenario associated with the simulation.
     */
    private Scenario scenario;

    /**
     * The station where the building will be added.
     */
    private Station station;

    /**
     * The map associated with the scenario.
     */
    private Map map;

    /**
     * The type of building to be purchased and added to the station.
     */
    private BuildingType buildingType;

    /**
     * Default constructor.
     */
    public BuildingsPurchaseConfirmationController() {
    }

    /**
     * Gets the map associated with the scenario.
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Sets the map associated with the scenario.
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Gets the scenario associated with the simulation.
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets the scenario associated with the simulation.
     * @param scenario the scenario to set
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Sets the simulation in which the building purchase is being made.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the simulation in which the building purchase is being made.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the station where the building will be added.
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Gets the station where the building will be added.
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Gets the type of building to be purchased and added to the station.
     * @return the building type
     */
    public BuildingType getBuildingType() {
        return buildingType;
    }

    /**
     * Sets the type of building to be purchased and added to the station.
     * @param buildingType the building type to set
     */
    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    /**
     * Adds a building of the specified type to the station and deducts its construction cost from the simulation's money.
     */
    public void addBuildingToStation() {
        int replacesId = buildingType.getBuildingReplacesByID();

        if (replacesId != -1) {
            List<Building> buildings = station.getBuildings();
            Iterator<Building> iterator = buildings.iterator();

            while (iterator.hasNext()) {
                Building building = iterator.next();
                if (replacesId == building.getType().getBuildingID()) {
                    iterator.remove();
                    station.removeBuilding(building);
                }
            }
        }

        station.addBuilding(new Building(buildingType));
        simulation.setActualMoney(simulation.getActualMoney() - buildingType.getConstructionCost());
    }

}

