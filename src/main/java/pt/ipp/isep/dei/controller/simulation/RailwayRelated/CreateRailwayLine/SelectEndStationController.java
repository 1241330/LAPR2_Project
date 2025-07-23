package pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for selecting the end station when creating a new railway line.
 */
public class SelectEndStationController {

    private Simulation simulation;
    private Station beginningStation;

    /**
     * Default constructor.
     */
    public SelectEndStationController() {}

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
     * Gets the beginning station for the new railway line.
     *
     * @return the beginning station
     */
    public Station getBeginningStation() {
        return beginningStation;
    }

    /**
     * Sets the beginning station for the new railway line.
     *
     * @param beginningStation the station to set as the beginning
     */
    public void setBeginningStation(Station beginningStation) {
        this.beginningStation = beginningStation;
    }

    /**
     * Retrieves the list of available stations for selection as the end station,
     * excluding the beginning station.
     *
     * @return an observable list of available stations
     */
    public ObservableList<Station> getAvailableStations() {
        List<Station> stations = simulation.getStations();
        List<Station> stationArrayList = new ArrayList<>();

        for (Station station : stations) {
            stationArrayList.add(station);
        }

        stationArrayList.remove(beginningStation);
        return FXCollections.observableArrayList(stationArrayList);
    }

    /**
     * Checks if a railway line already exists between the beginning station and the given end station.
     *
     * @param endStation the station to check as the end station
     * @return true if a line exists between the two stations, false otherwise
     */
    public boolean checkExistingLineBetweenStations(Station endStation) {
        for (RailwayLine line : simulation.getMap().getRailwayLines()) {
            if ((line.getStation1().equals(beginningStation) && line.getStation2().equals(endStation)) ||
                (line.getStation1().equals(endStation) && line.getStation2().equals(beginningStation))) {
                return true;
            }
        }
        return false;
    }
}