package pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TypeOfCargoMode;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for creating and managing points of a route in a simulation.
 * Provides methods for handling stations, railway lines, Dijkstra pathfinding, and GraphStream visualization properties.
 */
public class CreatePointOfRouteController {

    // GraphStream Station Related
    /** Size for depot stations in GraphStream visualization. */
    private final int GRAPHSTREAM_DEPOT_SIZE = 12;
    /** Size for regular stations in GraphStream visualization. */
    private final int GRAPHSTREAM_STATION_SIZE = 15;
    /** Size for terminal stations in GraphStream visualization. */
    private final int GRAPHSTREAM_TERMINAL_SIZE = 18;

    /** Base label distance for station labels in GraphStream. */
    private final int GRAPHSTREAM_BASE_LABEL_DISTANCE = 9;

    /** Color for depot stations in GraphStream. */
    private final String GRAPHSTREAM_DEPOT_COLOR = "#de5959";
    /** Color for regular stations in GraphStream. */
    private final String GRAPHSTREAM_STATION_COLOR = "#c23232";
    /** Color for terminal stations in GraphStream. */
    private final String GRAPHSTREAM_TERMINAL_COLOR = "#a60518";

    // GraphStream Railway Related
    /** Color for electric railway lines in GraphStream. */
    private final String GRAPHSTREAM_ELECTRIC_RAILWAYLINE_COLOR = "#d1882e";
    /** Color for non-electric railway lines in GraphStream. */
    private final String GRAPHSTREAM_NON_ELECTRIC_RAILWAYLINE_COLOR = "#635544";
    /** Size for double railway lines in GraphStream. */
    private final int GRAPHSTREAM_DOUBLE_RAILWAYLINE_SIZE = 4;
    /** Size for single railway lines in GraphStream. */
    private final int GRAPHSTREAM_SINGLE_RAILWAYLINE_SIZE = 2;

    /** Padding for station positions in GraphStream visualization. */
    private final int GRAPHSTREAM_PADDING = 40;
    /** Initial divider for position scaling in GraphStream. */
    private final int GRAPHSTREAM_INITIAL_DIVIDER = 2;
    /** Position divider for scaling station positions in GraphStream. */
    private final int GRAPHSTREAM_POSITION_DIVIDER = 8; // Map size 50x50 - Divider = 10

    // ############# END OF GRAPHSTREAM #####################

    /** The simulation context for which the controller operates. */
    private Simulation simulation;
    /** The departure station for the route. */
    private Station departureStation;
    /** List of points of the route. */
    private List<PointOfRoute> pointOfRouteList;
    /** Flag indicating if only electric railway lines are available (true) or all (false). */
    private boolean railwayTypeAvailableFlag;
    /** List of stations representing the path. */
    private List<Station> stationPath;
    /** List of stations representing the Dijkstra path. */
    private List<Station> DjikstraPath;

    /**
     * Default constructor. Initializes the pointOfRouteList and stationPath.
     */
    public CreatePointOfRouteController() {
        this.pointOfRouteList = new ArrayList<>();
        this.stationPath = new ArrayList<>();
    }

    /**
     * Gets the departure point of the route.
     * @return the first point of the route
     */
    public PointOfRoute getDeparturePoint() {
        return pointOfRouteList.getFirst();
    }

    /**
     * Sets the departure point of the route and updates the departure station and station path.
     * @param departurePoint the departure point to set
     */
    public void setDeparturePoint(PointOfRoute departurePoint) {
        this.pointOfRouteList.addFirst(departurePoint);
        this.departureStation = departurePoint.getStation();
        this.stationPath.add(departureStation);
    }

    /**
     * Gets the current simulation.
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
     * Gets the departure station.
     * @return the departure station
     */
    public Station getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the departure station.
     * @param departureStation the departure station to set
     */
    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

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
     * Gets the railway type available flag.
     * @return true if only electric railway lines are available, false otherwise
     */
    public boolean getIsRailwayTypeAvailableFlag() {
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
     * Checks if only electric railway lines are available.
     * @return true if only electric railway lines are available, false otherwise
     */
    public boolean isRailwayTypeAvailableFlag() {
        return railwayTypeAvailableFlag;
    }

    /**
     * Gets the list of stations representing the path.
     * @return the list of stations in the path
     */
    public List<Station> getStationPath() {
        return stationPath;
    }

    /**
     * Sets the list of stations representing the path.
     * @param stationPath the list of stations to set
     */
    public void setStationPath(List<Station> stationPath) {
        this.stationPath = stationPath;
    }

    /**
     * Adds a point of route to the list.
     * @param poinOfRoute the point of route to add
     */
    public void addPointOfRoute(PointOfRoute poinOfRoute){
        pointOfRouteList.add(poinOfRoute);
    }

    /**
     * Gets the available cargo mode types for the simulation.
     * @return observable list of cargo mode types
     */
    public ObservableList getCargoModeTypes() {
        return FXCollections.observableArrayList(TypeOfCargoMode.values());
    }

    /**
     * Gets the list of available stations, excluding the specified station.
     * @param stationToRemove the station to exclude from the list
     * @return observable list of available stations
     */
    public ObservableList<Station> getAvailableStations(Station stationToRemove) {

        List<RailwayLine> availableRailwayLineList = findAvailableRailwayLines();
        List<Station> availableStations = new ArrayList<>();
        availableStations.addAll(simulation.getStations());
        boolean isConnected ;

        for (Station station : simulation.getStations()){
            isConnected = false;
            for (RailwayLine railwayLine: availableRailwayLineList){
                if (railwayLine.getStation1().equals(station) || railwayLine.getStation2().equals(station)){
                    isConnected = true;
                    break;
                }
            }
            if (!isConnected){
                availableStations.remove(station);
            }
        }
        availableStations.remove(stationToRemove);

        return FXCollections.observableArrayList(availableStations);
    }

    /**
     * Gets the available resource types for the simulation scenario.
     * @return observable list of resource types
     */
    public ObservableList<ResourcesType> getResourceType() {
        return FXCollections.observableArrayList(simulation.getScenario().getTypeResourceList());
    }

    //###################################################
    //                   DJIKSTRA PATH
    //###################################################

    /**
     * Finds the Dijkstra path from the last point of route to the selected station.
     * @param selectedStation the destination station
     * @return list of stations representing the path
     */
    public List<Station> findDjikstraPath(Station selectedStation) {
        Station startStation = pointOfRouteList.getLast().getStation();
        List<RailwayLine> railways = findAvailableRailwayLines();

        List<Station> stations = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();
        List<Station> previous = new ArrayList<>();
        List<Station> visited = new ArrayList<>();

        for (RailwayLine line : railways) {
            if (!stations.contains(line.getStation1())) {
                stations.add(line.getStation1());
                distances.add(line.getStation1().equals(startStation) ? 0 : Integer.MAX_VALUE);
                previous.add(null);
            }
            if (!stations.contains(line.getStation2())) {
                stations.add(line.getStation2());
                distances.add(line.getStation2().equals(startStation) ? 0 : Integer.MAX_VALUE);
                previous.add(null);
            }
        }

        while (true) {
            int minIndex = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < stations.size(); i++) {
                Station station = stations.get(i);
                if (!visited.contains(station) && distances.get(i) < minDistance) {
                    minDistance = distances.get(i);
                    minIndex = i;
                }
            }

            if (minIndex == -1 || stations.get(minIndex).equals(selectedStation)) {
                break;
            }

            Station current = stations.get(minIndex);
            visited.add(current);

            for (RailwayLine line : railways) {
                Station neighbor = null;
                if (line.getStation1().equals(current)) {
                    neighbor = line.getStation2();
                } else if (line.getStation2().equals(current)) {
                    neighbor = line.getStation1();
                }

                if (neighbor != null && !visited.contains(neighbor)) {
                    int currentIndex = stations.indexOf(current);
                    int neighborIndex = stations.indexOf(neighbor);
                    int newDist = distances.get(currentIndex) + line.getDistance();
                    if (newDist < distances.get(neighborIndex)) {
                        distances.set(neighborIndex, newDist);
                        previous.set(neighborIndex, current);
                    }
                }
            }
        }

        // Reconstruir caminho
        List<Station> path = new ArrayList<>();
        Station step = selectedStation;
        while (step != null) {
            path.add(0, step);
            int index = stations.indexOf(step);
            step = previous.get(index);
        }

        // Verifica se o caminho começa na estação de origem
        if (!path.isEmpty() && !path.get(0).equals(startStation)) {
            return new ArrayList<>(); // Caminho não encontrado
        }

        return path;
    }

    /**
     * Finds the railway lines used in the given path.
     * @param path the list of stations representing the path
     * @return list of railway lines used in the path
     */
    public List<RailwayLine> findUsedRailwaysInPath(List<Station> path){
        List<RailwayLine> availableRailways = findAvailableRailwayLines();
        List<RailwayLine> usedRailwayLine = new ArrayList<>();

        if (path.isEmpty() || path == null){
            return null;
        }

        List<Station> modifiedPath = new ArrayList<>();
        for (Station station: path){
            if (station != path.getFirst()){
                modifiedPath.add(station);
            }
        }

        Station actualStation = path.getFirst();
        for (Station nextStation: modifiedPath ){
            for (RailwayLine railway : availableRailways){
                if (railway.getStation1() == actualStation && railway.getStation2() == nextStation ||
                        railway.getStation2() == actualStation && railway.getStation1() == nextStation ){
                    usedRailwayLine.add(railway);
                }
            }
            actualStation = nextStation;
        }
        return usedRailwayLine;
    }

    //###################################################
    //                FIM DJIKSTRA PATH
    //###################################################

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

    //###################################################
    //                   GRAPH STREAM
    //###################################################

    /**
     * Gets the size for a railway line for GraphStream visualization.
     * @param line the railway line
     * @return the size for the railway line
     */
    public int getRailwayLineSize(RailwayLine line) {
        if (line.getRailwayType().getId() == 1 || line.getRailwayType().getId() == 3){
            return  GRAPHSTREAM_DOUBLE_RAILWAYLINE_SIZE;
        } else {
            return  GRAPHSTREAM_SINGLE_RAILWAYLINE_SIZE;
        }
    }

    /**
     * Gets the color for a railway line for GraphStream visualization.
     * @param line the railway line
     * @return the color for the railway line
     */
    public String getRailwayLineColor(RailwayLine line) {
        if (line.getRailwayType().getId() == 0 || line.getRailwayType().getId() == 1){
            return GRAPHSTREAM_ELECTRIC_RAILWAYLINE_COLOR;
        } else {
            return GRAPHSTREAM_NON_ELECTRIC_RAILWAYLINE_COLOR;
        }

    }

    /**
     * Finds the scaled positions for a station for GraphStream visualization.
     * @param station the station
     * @param paneWidth the width of the pane
     * @param paneHeight the height of the pane
     * @return array with scaled x and y positions
     */
    public double[] findScaledPositions(Station station, double paneWidth, double paneHeight) {
        double padding = GRAPHSTREAM_PADDING;

        int xDivider = GRAPHSTREAM_INITIAL_DIVIDER + station.getPosition().getX() / GRAPHSTREAM_POSITION_DIVIDER;
        int xMultiplayer = station.getPosition().getX() / xDivider;

        int yDivider = GRAPHSTREAM_INITIAL_DIVIDER + station.getPosition().getY() / GRAPHSTREAM_POSITION_DIVIDER;
        int yMultiplayer = station.getPosition().getY() / yDivider;

        double normalizedX = (station.getPosition().getX() * xMultiplayer)  / paneWidth;
        double normalizedY = (station.getPosition().getY() * yMultiplayer)  / paneHeight;

        double scaledX = padding + normalizedX * paneWidth;
        double scaledY = padding + normalizedY * paneHeight;

        double[] positions = new double[] { scaledX, scaledY };

        return positions;
    }

    /**
     * Gets the color for a station for GraphStream visualization.
     * @param station the station
     * @return the color for the station
     */
    public String getStationColor(Station station) {
        if (station.getStationType() == StationType.DEPOT.name()){
            return GRAPHSTREAM_DEPOT_COLOR;
        } else if (station.getStationType() == StationType.STATION.name()) {
            return GRAPHSTREAM_STATION_COLOR;
        } else{
            return GRAPHSTREAM_TERMINAL_COLOR;
        }
    }

    /**
     * Gets the size for a station for GraphStream visualization.
     * @param station the station
     * @return the size for the station
     */
    public int getStationSize(Station station) {
        if (station.getStationType() == StationType.DEPOT.name()){
            return GRAPHSTREAM_DEPOT_SIZE;
        } else if (station.getStationType() == StationType.STATION.name()) {
            return GRAPHSTREAM_STATION_SIZE;
        } else{
            return GRAPHSTREAM_TERMINAL_SIZE;
        }
    }

    /**
     * Gets the label distance for a station label in GraphStream visualization.
     * @param stationSize the size of the station
     * @return the label distance
     */
    public int getLabelDistance(int stationSize) {
        return GRAPHSTREAM_BASE_LABEL_DISTANCE + stationSize;
    }

    /**
     * Adds the positions of the Dijkstra path to the station path, excluding the first station.
     * @param djikstraPath the Dijkstra path to add
     */
    public void addPathPositions(List<Station> djikstraPath) {
        djikstraPath.removeFirst();
        stationPath.addAll(djikstraPath);
    }

    /**
     * Gets the Dijkstra path.
     * @return the Dijkstra path
     */
    public List<Station> getDjikstraPath() {
        return DjikstraPath;
    }

    /**
     * Sets the Dijkstra path.
     * @param DjikstraPath the Dijkstra path to set
     */
    public void setDjikstraPath(List<Station> DjikstraPath) {
        this.DjikstraPath = DjikstraPath;
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
}
