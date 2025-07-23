package pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

/**
 * Controller responsible for managing the editing of a station in a simulation.
 * Provides methods to set and retrieve the simulation and station, and to verify if the station is at its maximum level.
 */
public class EditStationController {

    /**
     * The simulation in which the station exists.
     */
    private Simulation simulation;

    /**
     * The station being edited.
     */
    private Station station;

    /**
     * Default constructor.
     */
    public EditStationController() {
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
     * Gets the station being edited.
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station to be edited.
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Verifies if the station is at its maximum level (TERMINAL).
     * @return true if the station is TERMINAL, false otherwise
     */
    public boolean verifyIfStationIsMaxLevel() {
        return station.getStationType().equals(StationType.TERMINAL.toString());
    }
}
