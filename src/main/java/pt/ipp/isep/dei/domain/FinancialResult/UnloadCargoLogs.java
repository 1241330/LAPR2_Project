package pt.ipp.isep.dei.domain.FinancialResult;

import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the logs of resources unloaded at a specific station.
 * Stores the list of unloaded resources and the station where the unloading occurred.
 */
public class UnloadCargoLogs implements Serializable {

    /** List of resources that were unloaded. */
    private List<Resource> unloadedResources;

    /** The station where the resources were unloaded. */
    private Station station;

    /**
     * Constructs a new UnloadCargoLogs with the specified unloaded resources and station.
     *
     * @param unloadedResources the list of unloaded resources
     * @param station the station where the resources were unloaded
     */
    public UnloadCargoLogs(List<Resource> unloadedResources, Station station) {
        this.unloadedResources = unloadedResources;
        this.station = station;
    }

    /**
     * Gets the list of unloaded resources.
     *
     * @return the list of unloaded resources
     */
    public List<Resource> getUnloadedResources() {
        return unloadedResources;
    }

    /**
     * Sets the list of unloaded resources.
     *
     * @param unloadedResources the list of unloaded resources to set
     */
    public void setUnloadedResources(List<Resource> unloadedResources) {
        this.unloadedResources = unloadedResources;
    }

    /**
     * Gets the station where the resources were unloaded.
     *
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station where the resources were unloaded.
     *
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }
}