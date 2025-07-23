package pt.ipp.isep.dei.domain.Simulation;

import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a point in a route, including the station, the cargo to pick, and the cargo mode.
 */
public class PointOfRoute implements Serializable {

    /** The list of resource types to pick up at this point. */
    private List<ResourcesType> cargoToPick;

    /** The station associated with this point of the route. */
    private Station station;

    /** The mode of cargo operation at this point. */
    private TypeOfCargoMode cargoMode;

    /**
     * Constructs a PointOfRoute with the specified cargo to pick, station, and cargo mode.
     *
     * @param cargoToPick the list of resource types to pick up
     * @param station the station at this point of the route
     * @param cargoMode the mode of cargo operation
     */
    public PointOfRoute(List<ResourcesType> cargoToPick, Station station, TypeOfCargoMode cargoMode) {
        this.cargoToPick = cargoToPick;
        this.station = station;
        this.cargoMode = cargoMode;
    }

    /**
     * Returns the list of resource types to pick up at this point.
     *
     * @return the list of resource types to pick up
     */
    public List<ResourcesType> getCargoToPick() {
        return cargoToPick;
    }

    /**
     * Sets the list of resource types to pick up at this point.
     *
     * @param cargoToPick the list of resource types to set
     */
    public void setCargoToPick(List<ResourcesType> cargoToPick) {
        this.cargoToPick = cargoToPick;
    }

    /**
     * Returns the station associated with this point of the route.
     *
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station for this point of the route.
     *
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Returns the mode of cargo operation at this point.
     *
     * @return the cargo mode
     */
    public TypeOfCargoMode getCargoMode() {
        return cargoMode;
    }

    /**
     * Sets the mode of cargo operation at this point.
     *
     * @param cargoMode the cargo mode to set
     */
    public void setCargoMode(TypeOfCargoMode cargoMode) {
        this.cargoMode = cargoMode;
    }
}