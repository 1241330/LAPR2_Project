package pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Station.Building;
import pt.ipp.isep.dei.domain.Station.BuildingType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controller responsible for managing the buildings of a station in a simulation.
 * Provides methods to retrieve available building types, check if the user has enough money,
 * and add buildings to a station. Also allows setting and getting the simulation and station.
 */
public class EditStationBuildingsController {

    /**
     * The simulation in which the station exists.
     */
    private Simulation simulation;

    /**
     * The station whose buildings are being managed.
     */
    private Station station;

    /**
     * Default constructor.
     */
    public EditStationBuildingsController(){
        // Default constructor
    }

    /**
     * Gets the current simulation.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation in which the station exists.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the station whose buildings are being managed.
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station whose buildings are being managed.
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Retrieves the list of available building types that can be added to the station.
     * Excludes types already present, replaced, or hidden by more advanced versions.
     * @return an observable list of available building types
     */
    public ObservableList<BuildingType> getAvailableBuildingTypes() {
        List<BuildingType> availableBuildingType = new ArrayList<>();
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.getOperationYearInDays() <= simulation.getCurrentTime()) {
                availableBuildingType.add(buildingType);
            }
        }

        List<Building> existingBuildings = station.getBuildings();

        // Remove already existing building types
        Iterator<BuildingType> iterator = availableBuildingType.iterator();
        while (iterator.hasNext()) {
            BuildingType buildingType = iterator.next();
            for (Building building : existingBuildings) {
                if (building.getType().getBuildingID() == buildingType.getBuildingID()) {
                    iterator.remove();
                    break;
                }
            }
        }

        // Remove building types that are replaced by existing buildings
        for (Building building : existingBuildings) {
            if (building.getType().getBuildingReplacesByID() != -1) {
                for (BuildingType type : new ArrayList<>(availableBuildingType)) {
                    if (type.getBuildingID() == building.getType().getBuildingReplacesByID()) {
                        availableBuildingType.remove(type);
                    }
                }
            }
        }

        // Remove building types that should be hidden due to more advanced versions
        List<Integer> idsToRemove = new ArrayList<>();
        for (BuildingType type : availableBuildingType) {
            int idToRemove = type.getDeleteFromAvailableID();
            if (idToRemove != -1) {
                for (BuildingType check : availableBuildingType) {
                    if (check.getBuildingID() == idToRemove) {
                        idsToRemove.add(idToRemove);
                        break;
                    }
                }
            }
        }

        availableBuildingType.removeIf(type -> idsToRemove.contains(type.getBuildingID()));
        return FXCollections.observableArrayList(availableBuildingType);
    }

    /**
     * Converts a list of BuildingType to an ObservableList.
     * @param list the list to convert
     * @return an observable list of building types
     */
    public ObservableList convertToObservableList(List<BuildingType> list){
        return FXCollections.observableArrayList(list);
    }

    /**
     * Checks if the simulation has enough money to purchase the selected building type.
     * @param selectedBuilding the building type to check
     * @return true if there is enough money, false otherwise
     */
    public boolean checkIfHasMoney(Object selectedBuilding) {
        if (!(selectedBuilding instanceof BuildingType)) return false;

        BuildingType buildingType = (BuildingType) selectedBuilding;
        return buildingType.getConstructionCost() <= simulation.getActualMoney();
    }

    /**
     * Adds a building of the selected type to the station and deducts its construction cost from the simulation's money.
     * @param selectedBuilding the building type to add
     */
    public void addBuildingToStation(Object selectedBuilding) {
        BuildingType buildingType = (BuildingType) selectedBuilding;

        station.addBuilding(new Building(buildingType));
        simulation.setActualMoney(simulation.getActualMoney() - buildingType.getConstructionCost());
    }
}

