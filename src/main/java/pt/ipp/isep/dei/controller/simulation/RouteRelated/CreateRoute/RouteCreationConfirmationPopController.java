package pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for confirming the creation of a route in a simulation.
 * Handles the management of points of route, station path, and railway line selection for route creation.
 */
public class RouteCreationConfirmationPopController {

    /**
     * List of points of the route to be included in the new route.
     */
    private List<PointOfRoute> pointOfRouteList;
    /**
     * List of stations representing the path of the route.
     */
    private List<Station> stationPath;
    /**
     * The simulation context in which the route is being created.
     */
    private Simulation simulation;
    /**
     * Flag indicating if only electric railway lines are available (true) or all (false).
     */
    private boolean railwayTypeAvailableFlag;

    /**
     * Default constructor.
     */
    public RouteCreationConfirmationPopController(){}

    /**
     * Gets the list of points of the route.
     * @return the list of points of the route
     */
    public List<PointOfRoute> getPointOfRouteList() {
        return pointOfRouteList;
    }

    /**
     * Sets the list of points of the route.
     * @param pointOfRouteList the list of points of the route to set
     */
    public void setPointOfRouteList(List<PointOfRoute> pointOfRouteList) {
        this.pointOfRouteList = pointOfRouteList;
    }

    /**
     * Gets the list of stations representing the path of the route.
     * @return the list of stations in the path
     */
    public List<Station> getStationPath() {
        return stationPath;
    }

    /**
     * Sets the list of stations representing the path of the route.
     * @param stationPath the list of stations to set
     */
    public void setStationPath(List<Station> stationPath) {
        this.stationPath = stationPath;
    }

    /**
     * Gets the simulation context.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation context.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the railway type available flag.
     * @param isRailwayTypeAvailableFlag ignored parameter (kept for compatibility)
     * @return true if only electric railway lines are available, false otherwise
     */
    public boolean isRailwayTypeAvailableFlag(boolean isRailwayTypeAvailableFlag) {
        return railwayTypeAvailableFlag;
    }

    /**
     * Sets the railway type available flag.
     * @param railwayTypeAvailableFlag true if only electric railway lines are available, false otherwise
     */
    public void setRailwayTypeAvailableFlag(boolean railwayTypeAvailableFlag) {
        this.railwayTypeAvailableFlag = railwayTypeAvailableFlag;
    }

    /**
     * Creates a new route in the simulation using the current points of route and station path.
     * The route is added to the simulation's list of routes.
     */
    public void createRoute() {
        List<RailwayLine> railwayLineList = new ArrayList<>();
        Station oldStation = stationPath.getFirst();
        for (Station station : stationPath){
            railwayLineList.add(getRailwayLineBetween(oldStation, station));
            oldStation = station;
        }
        railwayLineList.removeFirst();
        Route route = new Route(pointOfRouteList,railwayLineList, getRouteName(), railwayTypeAvailableFlag);
        simulation.addRoute(route);
    }

    /**
     * Generates a name for the new route based on the number of existing routes in the simulation.
     * @return the generated route name
     */
    private String getRouteName() {
        return "route_" + (simulation.getRoutes().size() + 1);
    }

    /**
     * Gets the railway line between two stations.
     * @param s1 the first station
     * @param s2 the second station
     * @return the railway line between the two stations, or null if not found
     */
    public RailwayLine getRailwayLineBetween(Station s1, Station s2) {
        for (RailwayLine line : findAvailableRailwayLines()) {
            if ((line.getStation1().equals(s1) && line.getStation2().equals(s2)) ||
                    (line.getStation1().equals(s2) && line.getStation2().equals(s1))) {
                return line;
            }
        }
        return null;
    }

    /**
     * Finds the available railway lines based on the railway type flag.
     * @return list of available railway lines
     */
    public List<RailwayLine> findAvailableRailwayLines() {
        if (!railwayTypeAvailableFlag){
            return simulation.getMap().getRailwayLines();
        }
        List<RailwayLine> availableRailwayLine = new ArrayList<>();
        for (RailwayLine railwayLine : simulation.getMap().getRailwayLines()){
            if (railwayLine.getRailwayType().getId() == 0 || railwayLine.getRailwayType().getId() == 1){
                availableRailwayLine.add(railwayLine);
            }
        }
        return availableRailwayLine;
    }
}
