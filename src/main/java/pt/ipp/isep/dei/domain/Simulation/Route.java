package pt.ipp.isep.dei.domain.Simulation;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Train.Train;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a route in the simulation, containing a sequence of points, a path, and related properties.
 */
public class Route implements Serializable {

    /** The departure station of the route. */
    private Station departureStation;

    /** The list of points of the route. */
    private List<PointOfRoute> pointOfRouteList;

    /** The path of the route as a list of railway lines. */
    private List<RailwayLine> path;

    /** Indicates if the route is active (true) or deactivated (false). */
    private boolean activeFlag;

    /** The train assigned to the route. */
    private Train assignedTrain;

    /** The name of the route. */
    private String name;

    /** Indicates if the route is electric (true) or non-electric (false). */
    private boolean electricFlag;

    /** The current position in the point path. */
    private int pointPathPosition;

    /** Indicates the old status: true if transferring, false if waiting. */
    private boolean oldStatus;

    /**
     * Constructs a Route with the specified points, path, name, and electric flag.
     *
     * @param pointOfRouteList the list of points of the route
     * @param path the list of railway lines representing the path
     * @param name the name of the route
     * @param electricFlag true if the route is electric, false otherwise
     */
    public Route(List<PointOfRoute> pointOfRouteList, List<RailwayLine> path, String name, boolean electricFlag) {
        this.departureStation = pointOfRouteList.getFirst().getStation();
        this.pointOfRouteList = pointOfRouteList;
        this.name = name;
        this.path = path;
        this.activeFlag = false;
        this.electricFlag = electricFlag;
        this.pointPathPosition = 0;
        this.oldStatus = false;
    }

    /**
     * Returns the departure station of the route.
     *
     * @return the departure station
     */
    public Station getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the departure station of the route.
     *
     * @param departureStation the station to set as departure
     */
    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    /**
     * Returns the list of points of the route.
     *
     * @return the list of points of the route
     */
    public List<PointOfRoute> getPointOfRouteList() {
        return pointOfRouteList;
    }

    /**
     * Sets the list of points of the route.
     *
     * @param pointOfRouteList the list of points to set
     */
    public void setPointOfRouteList(List<PointOfRoute> pointOfRouteList) {
        this.pointOfRouteList = pointOfRouteList;
    }

    /**
     * Returns the path of the route as a list of railway lines.
     *
     * @return the path of the route
     */
    public List<RailwayLine> getPath() {
        return path;
    }

    /**
     * Sets the path of the route.
     *
     * @param path the list of railway lines to set as the path
     */
    public void setPath(List<RailwayLine> path) {
        this.path = path;
    }

    /**
     * Returns whether the route is active.
     *
     * @return true if active, false otherwise
     */
    public boolean getActiveFlag() {
        return activeFlag;
    }

    /**
     * Sets the active flag of the route.
     *
     * @param activeFlag true to activate, false to deactivate
     */
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * Returns the train assigned to the route.
     *
     * @return the assigned train
     */
    public Train getAssignedTrain() {
        return assignedTrain;
    }

    /**
     * Sets the train assigned to the route.
     *
     * @param assignedTrain the train to assign
     */
    public void setAssignedTrain(Train assignedTrain) {
        this.assignedTrain = assignedTrain;
    }

    /**
     * Returns the name of the route.
     *
     * @return the route name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the route.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns whether the route is electric.
     *
     * @return true if electric, false otherwise
     */
    public boolean getElectricFlag() {
        return electricFlag;
    }

    /**
     * Sets the electric flag of the route.
     *
     * @param electricFlag true if electric, false otherwise
     */
    public void setElectricFlag(boolean electricFlag) {
        this.electricFlag = electricFlag;
    }

    /**
     * Returns the current position in the point path.
     *
     * @return the current point path position
     */
    public int getPointPathPosition() {
        return pointPathPosition;
    }

    /**
     * Sets the current position in the point path.
     *
     * @param pointPathPosition the position to set
     */
    public void setPointPathPosition(int pointPathPosition) {
        this.pointPathPosition = pointPathPosition;
    }

    /**
     * Returns the old status of the route.
     *
     * @return true if transferring, false if waiting
     */
    public boolean getOldStatus() {
        return oldStatus;
    }

    /**
     * Sets the old status of the route.
     *
     * @param oldStatus true if transferring, false if waiting
     */
    public void setOldStatus(boolean oldStatus) {
        this.oldStatus = oldStatus;
    }

    /**
     * Advances to the next point in the route. If at the end, resets to the first point.
     */
    public void nextPointPathPosition() {
        if (pointPathPosition == pointOfRouteList.size() - 1) {
            pointPathPosition = 0;
            return;
        }
        pointPathPosition += 1;
    }

    /**
     * Returns a string representation of the active flag.
     *
     * @return "Active" if active, "Deactivated" otherwise
     */
    public String getActiveFlagOnString() {
        if (this.activeFlag) {
            return "Active";
        }
        return "Deactivated";
    }
}