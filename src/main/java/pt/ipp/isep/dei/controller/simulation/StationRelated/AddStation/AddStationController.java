package pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the addition of stations to a simulation.
 * Handles station type selection and verifies if there are enough funds to build a station.
 */
public class AddStationController {

    /**
     * The simulation currently being managed.
     */
    private Simulation simulation;

    /**
     * The selected station type.
     */
    private StationType stationType;

    /**
     * Default constructor.
     */
    public AddStationController() {
    }

    /**
     * Returns the current simulation associated with this controller.
     *
     * @return the current Simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation to be used by this controller.
     *
     * @param simulation the Simulation instance to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Returns a list of all station type names as strings.
     *
     * @return a list of station type names
     */
    public List<String> getStationTypes() {
        StationType[] stationTypes = StationType.values();
        List<String> stationTypeNames = new ArrayList<>();
        for (StationType stationType : stationTypes) {
            stationTypeNames.add(stationType.toString());
        }
        return stationTypeNames;
    }

    /**
     * Sets the station type to be used by this controller.
     *
     * @param stationType the StationType to set
     */
    public void setStationType(StationType stationType) {
        this.stationType = stationType;
    }

    /**
     * Returns the current station type set in this controller.
     *
     * @return the current StationType
     */
    public StationType getStationType() {
        return stationType;
    }

    /**
     * Checks if there is enough money in the simulation to build the selected station type.
     *
     * @return true if there is enough money, false otherwise
     */
    public boolean verifyIfYouHaveMoney() {
        return stationType.getConstructionCost() <= simulation.getActualMoney();
    }

    /**
     * Returns the influential radius of the given station type.
     *
     * @param stationType the StationType to check
     * @return the influential radius of the station type
     */
    public int getStationTypeRadius(StationType stationType) {
        return stationType.getInfluentialRadius();
    }

    /**
     * Returns the construction cost of the given station type.
     *
     * @param stationType the StationType to check
     * @return the construction cost of the station type
     */
    public int getStationTypePrice(StationType stationType) {
        return stationType.getConstructionCost();
    }
}