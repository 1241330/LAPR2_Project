package pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

/**
 * Controller responsible for editing the name of a station in a simulation.
 * Provides methods to set and retrieve the station, simulation, and new name,
 * as well as to change and validate the station's name.
 */
public class EditStationNameController {

    /**
     * The station being edited.
     */
    private Station station;

    /**
     * The simulation in which the station exists.
     */
    private Simulation simulation;

    /**
     * The new name to assign to the station.
     */
    private String newName;

    /**
     * Default constructor.
     */
    public EditStationNameController() {
        // Default constructor
    }

    /**
     * Gets the new name to assign to the station.
     * @return the new name
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Sets the new name to assign to the station.
     * @param newName the new name to set
     */
    public void setNewName(String newName) {
        this.newName = newName;
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
     * Gets the simulation in which the station exists.
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
     * Gets the current name of the station.
     * @return the station's current name
     */
    public String getStationName() {
        return station.getName();
    }

    /**
     * Changes the station's name to the specified new name.
     * @param newName the new name to assign
     */
    public void changeStationName(String newName){
        station.setName(newName);
    }

    /**
     * Edits the station's name if the new name is unique and different from the current name.
     * @return true if the name was changed, false otherwise
     */
    public boolean editName() {
        if (station.getName().equals(newName)) {
            return false;
        }
        for(Station station1 : simulation.getStations()) {
            if (station1.getName().equals(newName)) {
                return false;
            }
        }
        station.setName(newName);
        return true;
    }
}
