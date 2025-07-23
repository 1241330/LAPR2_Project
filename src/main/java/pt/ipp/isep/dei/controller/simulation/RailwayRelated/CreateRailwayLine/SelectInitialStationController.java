package pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

/**
 * Controller responsible for selecting the initial station
 * when creating a new railway line in the simulation.
 */
public class SelectInitialStationController {

    /** The simulation instance. */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public SelectInitialStationController() {
        // No-args constructor
    }

    /**
     * Gets the current simulation.
     *
     * @return the simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Retrieves the list of available stations for selection as the initial station.
     *
     * @return an observable list of available stations
     */
    public ObservableList<Station> getAvailableStations() {
        return FXCollections.observableArrayList(simulation.getStations());
    }
}