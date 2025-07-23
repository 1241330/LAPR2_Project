package pt.ipp.isep.dei.controller.simulation.StationRelated.RemoveStation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.List;

/**
 * Controller responsible for displaying the list of stations in a simulation for removal purposes.
 * Provides methods to set and retrieve the simulation, and to obtain the list of stations as an ObservableList.
 */
public class ShowStationListController2 {

    /**
     * The simulation whose stations are to be displayed.
     */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public ShowStationListController2() {
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
     * Sets the simulation whose stations are to be displayed.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Retrieves the list of stations from the simulation as an ObservableList.
     * @return an observable list of stations
     */
    public ObservableList<Station> getStationList(){
        List<Station> stations = simulation.getScenario().getMap().getStationList();
        return FXCollections.observableArrayList(stations);
    }
}
