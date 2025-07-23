package pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Train.Locomotive;

/**
 * Controller responsible for confirming the evolution of a station in a simulation.
 * Provides methods to set and retrieve the simulation and station involved in the evolution process.
 */
public class StationEvolveConfirmationController {

    /**
     * The simulation in which the station evolution is being confirmed.
     */
    private Simulation simulation;

    /**
     * The station to be evolved.
     */
    private Station station;

    /**
     * Default constructor.
     */
    public StationEvolveConfirmationController() {
    }

    /**
     * Sets the simulation in which the station evolution is being confirmed.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Sets the station to be evolved.
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Gets the simulation in which the station evolution is being confirmed.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Gets the station to be evolved.
     * @return the station
     */
    public Station getStation() {
        return station;
    }

}
