package pt.ipp.isep.dei.domain.RailwayLine;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Station.Station;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a railway line connecting two stations with a specific type and path.
 * Stores the list of positions that the railway line occupies on the map.
 */
public class RailwayLine extends MapElement implements Serializable {

    /** List of positions that the railway line occupies on the map. */
    private List<Position> positionsRailwayLine;

    /** The first station connected by the railway line. */
    private final Station station1;

    /** The second station connected by the railway line. */
    private final Station station2;

    /** The type of the railway line. */
    private RailwayLineType type;

    /** Static id for all railway lines. */
    private static int id = 0;

    /** The distance of the railway line. */
    private int distance;

    /** The construction date of the railway line. */
    private TimeDate constructionDate;

    /**
     * Constructs a RailwayLine with a given path, two stations, and a type.
     *
     * @param path             List of positions representing the railway line's path.
     * @param station1         The first station connected by the railway line.
     * @param station2         The second station connected by the railway line.
     * @param type             The type of the railway line.
     * @param constructionDate The construction date of the railway line.
     */
    public RailwayLine(List<Position> path, Station station1, Station station2, RailwayLineType type, TimeDate constructionDate) {
        super(path.getFirst());
        this.positionsRailwayLine = path;
        this.station1 = station1;
        this.station2 = station2;
        this.type = type;
        this.setOccupiedPositions(positionsRailwayLine);
        this.id = id;
        id++;
        this.constructionDate = constructionDate;
    }

    /**
     * Constructs a RailwayLine with two stations and a type.
     * The path is not set in this constructor.
     *
     * @param station1 The first station connected by the railway line.
     * @param station2 The second station connected by the railway line.
     * @param type     The type of the railway line.
     */
    public RailwayLine(Station station1, Station station2, RailwayLineType type) {
        super(null);
        this.station1 = station1;
        this.station2 = station2;
        this.type = type;
        this.setOccupiedPositions(positionsRailwayLine);
    }

    /**
     * Returns the static id of the railway line.
     *
     * @return The id.
     */
    public static int getId() {
        return id;
    }

    /**
     * Sets the static id of the railway line.
     *
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the list of positions that the railway line occupies.
     *
     * @return List of positions.
     */
    public List<Position> getPositionsRailwayLine() {
        return positionsRailwayLine;
    }

    /**
     * Sets the list of positions for the railway line and updates the occupied positions.
     *
     * @param positionsRailwayLine List of positions to set.
     */
    public void setPositionsRailwayLine(List<Position> positionsRailwayLine) {
        this.positionsRailwayLine = positionsRailwayLine;
        this.setOccupiedPositions(positionsRailwayLine);
    }

    /**
     * Returns the first station connected by the railway line.
     *
     * @return The first station.
     */
    public Station getStation1() {
        return station1;
    }

    /**
     * Returns the second station connected by the railway line.
     *
     * @return The second station.
     */
    public Station getStation2() {
        return station2;
    }

    /**
     * Returns a list containing both stations connected by the railway line.
     *
     * @return List of stations.
     */
    public List<Station> getStations() {
        List<Station> list = new ArrayList<>();
        list.add(station1);
        list.add(station2);
        return list;
    }

    /**
     * Returns the name of the first station.
     *
     * @return Name of the first station.
     */
    public String getNameStation1() {
        return station1.getName();
    }

    /**
     * Returns the name of the second station.
     *
     * @return Name of the second station.
     */
    public String getNameStation2() {
        return station2.getName();
    }

    /**
     * Calculates the cost of building the railway line on the map,
     * considering already occupied positions.
     *
     * @param map The map to check for occupied positions.
     * @return The total cost.
     */
    public int calculateCost(Map map) {
        List<Position> alreadyOccupied = map.getOccupiedPositions();
        int cost = 0;

        for (Position p : this.positionsRailwayLine) {
            boolean exists = false;
            for (Position existing : alreadyOccupied) {
                if (p.equalsPosition(existing)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                cost += type.getCost();
            }
        }
        return cost;
    }

    /**
     * Returns the type of the railway line.
     *
     * @return The railway line type.
     */
    public RailwayLineType getRailwayType() {
        return type;
    }

    /**
     * Returns the type of the railway line as an enum.
     *
     * @return The railway line type.
     */
    public RailwayLineType getTypeEnum() {
        return type;
    }

    /**
     * Sets the type of the railway line.
     *
     * @param type The type to set.
     */
    public void setTypeEnum(RailwayLineType type) {
        this.type = type;
    }

    /**
     * Returns the type of the map element.
     *
     * @return The string "RailwayLine".
     */
    @Override
    public String getType() {
        return "RailwayLine";
    }

    /**
     * Returns a string representing the connection between the two stations.
     *
     * @return The connection name.
     */
    public String getConnectionName() {
        return station1.getName() + " ↔ " + station2.getName();
    }

    /**
     * Sets the distance of the railway line.
     *
     * @param distance The distance to set.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Returns the distance of the railway line.
     *
     * @return The distance.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Returns a string representation of the railway line.
     *
     * @return String with type and station names.
     */
    @Override
    public String toString() {
        return type.getType() + " | " + station1.getName() + " ↔ " + station2.getName();
    }

    /**
     * Returns the number of positions (stations) in the railway line.
     *
     * @return The number of positions.
     */
    public int getNumberStations() {
        return positionsRailwayLine.size();
    }

    /**
     * Returns the construction date of the railway line.
     *
     * @return The construction date.
     */
    public TimeDate getConstructionDate() {
        return constructionDate;
    }

    /**
     * Sets the construction date of the railway line.
     *
     * @param constructionDate The construction date to set.
     */
    public void setConstructionDate(TimeDate constructionDate) {
        this.constructionDate = constructionDate;
    }
}