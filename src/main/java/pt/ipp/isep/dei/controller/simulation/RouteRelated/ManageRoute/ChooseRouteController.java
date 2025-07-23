package pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

/**
 * Controller responsible for managing the selection and visualization of routes in a simulation.
 * Provides methods for retrieving routes, assigned trains, and GraphStream visual properties for stations and railway lines.
 */
public class ChooseRouteController {

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

    /** The simulation context for which the controller operates. */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public ChooseRouteController(){}

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
     * Gets the list of routes in the current simulation.
     * @return observable list of routes
     */
    public ObservableList<Route> getRoutes() {
        return  FXCollections.observableArrayList(simulation.getRoutes());
    }

    /**
     * Gets the name of the assigned train for the selected route.
     * @param selectedTrain the selected route
     * @return the name of the assigned train
     */
    public String getAssignedTrain(Route selectedTrain) {
        return selectedTrain.getName();
    }

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

}
