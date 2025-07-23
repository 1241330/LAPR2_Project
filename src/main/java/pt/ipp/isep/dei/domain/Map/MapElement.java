package pt.ipp.isep.dei.domain.Map;

import pt.ipp.isep.dei.domain._Others_.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a map element that can occupy positions on a map.
 * Provides methods to manage the element's position and the positions it occupies.
 */
public abstract class MapElement implements Serializable {

    /** The main position of the map element. */
    private Position position;

    /** The list of positions occupied by the map element. */
    private List<Position> occupiedPositions = new ArrayList<>();

    /**
     * Constructs a MapElement with a specified position and a list of occupied positions.
     *
     * @param position          The position of the map element.
     * @param occupiedPositions The list of positions occupied by the map element.
     */
    public MapElement(Position position, List<Position> occupiedPositions) {
        this.position = position;
        this.occupiedPositions = occupiedPositions;
    }

    /**
     * Constructs a MapElement with a specified position.
     *
     * @param position The position of the map element.
     */
    public MapElement(Position position) {
        this.position = position;
    }

    /**
     * Retrieves the position of the map element.
     *
     * @return The position of the map element.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the map element.
     *
     * @param position The new position of the map element.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Retrieves the list of positions occupied by the map element.
     *
     * @return A list of occupied positions.
     */
    public List<Position> getOccupiedPositions() {
        return occupiedPositions;
    }

    /**
     * Sets the list of positions occupied by the map element.
     *
     * @param occupiedPositions The new list of occupied positions.
     */
    public void setOccupiedPositions(List<Position> occupiedPositions) {
        this.occupiedPositions = occupiedPositions;
    }

    /**
     * Abstract method to retrieve the type of the map element.
     *
     * @return The type of the map element as a string.
     */
    public abstract String getType();
}