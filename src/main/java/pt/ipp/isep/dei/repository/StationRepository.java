package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Station entities.
 * Provides methods to add, remove, retrieve, and check the existence of stations.
 */
public class StationRepository implements Serializable {

    /**
     * List that stores all stations.
     */
    private List<Station> stations = new ArrayList<>();

    /**
     * Constructs an empty StationRepository.
     */
    public StationRepository() {
        // Default constructor
    }

    /**
     * Adds a station to the repository.
     *
     * @param station The station to add.
     * @return true if the station was added successfully, false if a station with the same name already exists.
     * @throws IllegalArgumentException if the station is null.
     */
    public boolean addStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Station cannot be null");
        }
        if (stationNameExists(station.getName())) {
            return false;
        }
        stations.add(station);
        return true;
    }

    /**
     * Removes a station from the repository.
     *
     * @param station The station to remove.
     * @return true if the station was removed successfully, false if it does not exist.
     * @throws IllegalArgumentException if the station is null.
     */
    public boolean removeStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Station cannot be null");
        }
        if (!stationExists(station)) {
            Utils.printMessage("< Station doesn't exist >");
            return false;
        }
        removeStationByName(station);
        return true;
    }

    private void removeStationByName(Station actualStation) {
        for (Station station : stations) {
            if (station.getName().equals(actualStation.getName())) {
                stations.remove(station);
               return;
            }
        }
    }

    /**
     * Checks if a specific station exists in the repository.
     *
     * @param actualStation The station to check.
     * @return true if the station exists, false otherwise.
     */
    public boolean stationExists(Station actualStation) {
        for (Station station : stations) {
            if (station.getName().equals(actualStation.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a station with the given name already exists in the repository.
     *
     * @param name The name to check.
     * @return true if a station with the given name exists, false otherwise.
     */
    public boolean stationNameExists(String name) {
        for (Station station : stations) {
            if (station.getName().equalsIgnoreCase(name)) {
                Utils.printMessage("<Station name already exist>");
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of all station names in the repository.
     *
     * @return A list of station names.
     */
    public List<String> getAllStationNames() {
        List<String> stationNames = new ArrayList<>();
        for (Station station : stations) {
            String name = station.getName();
            if (!stationNames.contains(name)) {
                stationNames.add(name);
            }
        }
        return stationNames;
    }

    /**
     * Retrieves a list of all stations in the repository.
     *
     * @return The list of stations.
     */
    public List<Station> getStations() {
        return stations;
    }

    /**
     * Sets the list of stations in the repository.
     *
     * @param stations The list of stations to set.
     */
    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}