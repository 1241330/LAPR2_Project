package pt.ipp.isep.dei.domain.City;

import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a city on the map, composed of a name, a central position,
 * a list of house blocks, and their respective positions.
 * Each city also has a unique ID assigned automatically upon creation.
 */
public class City extends MapElement implements Serializable {

    private String name;
    private Position position;
    private int id;
    private static int counterCreatedCities = 0;
    private List<Position> houseBlocksPositions;
    private List<HouseBlock> houseBlocks;

    /**
     * Constructs a new City object with the specified name, position,
     * list of house blocks, and their positions.
     * Automatically assigns a unique ID to the city.
     *
     * @param name                 the name of the city
     * @param position             the central position of the city
     * @param houseBlocks          the list of HouseBlock objects
     * @param houseBlocksPositions the list of positions for the house blocks
     */
    public City(String name, Position position, List<HouseBlock> houseBlocks, List<Position> houseBlocksPositions) {
        super(position, houseBlocksPositions);
        this.name = name;
        this.position = position;
        this.houseBlocks = houseBlocks;
        this.houseBlocksPositions = houseBlocksPositions;
        this.id = counterCreatedCities;
        counterCreatedCities++;
    }

    /**
     * Returns the name of the city.
     *
     * @return the name of the city
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the city.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the central position of the city.
     *
     * @return the central position of the city
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the central position of the city.
     *
     * @param position the new central position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns the unique ID of the city.
     *
     * @return the unique ID of the city
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the number of cities created so far.
     *
     * @return the number of cities created
     */
    public static int getCounterCreatedCities() {
        return counterCreatedCities;
    }

    /**
     * Returns the list of positions of the house blocks.
     *
     * @return the list of house block positions
     */
    public List<Position> getHouseBlocksPositions() {
        return houseBlocksPositions;
    }

    /**
     * Sets the list of house block positions.
     *
     * @param houseBlocksPositions the new list of house block positions
     */
    public void setHouseBlocksPositions(List<Position> houseBlocksPositions) {
        this.houseBlocksPositions = houseBlocksPositions;
    }

    /**
     * Updates the list of house block positions based on the current list of house blocks.
     * Assumes the list of houseBlocks is already initialized.
     */
    public void updateHouseBlocksPositions() {
        houseBlocksPositions.clear();
        for (HouseBlock houseBlock : houseBlocks) {
            houseBlocksPositions.add(houseBlock.getPosition());
        }
    }

    /**
     * Returns the list of house blocks belonging to the city.
     *
     * @return the list of house blocks
     */
    public List<HouseBlock> getHouseBlocks() {
        return houseBlocks;
    }

    /**
     * Sets the list of house blocks for the city and updates the positions list accordingly.
     *
     * @param houseBlocks the new list of HouseBlock objects
     */
    public void setHouseBlocks(List<HouseBlock> houseBlocks) {
        this.houseBlocks = houseBlocks;
        updateHouseBlocksPositions();
    }

    /**
     * Returns a string indicating the type of this map element.
     *
     * @return the type of the map element ("City")
     */
    @Override
    public String getType() {
        return "City";
    }

    /**
     * Returns a string representation of the city (name and position).
     *
     * @return a string representation of the city
     */
    @Override
    public String toString() {
        return name + " " + position;
    }
}