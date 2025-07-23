package pt.ipp.isep.dei.domain._Others_;

import java.io.Serializable;

/**
 * Represents a position on a 2D map with x and y coordinates.
 * Provides methods for retrieving and modifying the coordinates,
 * as well as comparing positions for equality.
 */
public class Position implements Serializable {
    private int x;
    private int y;

    /**
     * Constructs a Position with specified x and y coordinates.
     * Ensures that the coordinates are non-negative.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @throws IllegalArgumentException if x or y is negative.
     */
    public Position(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be positive");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x-coordinate of the position.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the position.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retrieves the y-coordinate of the position.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the position.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Compares this position with another position for equality.
     * Two positions are equal if their x and y coordinates are the same.
     *
     * @param position The position to compare with.
     * @return True if the positions are equal, false otherwise.
     */
    public boolean equalsPosition(Position position) {
        return x == position.getX() && y == position.getY();
    }

    /**
     * Returns a string representation of the position in the format "(x = x, y = y)".
     *
     * @return A string representation of the position.
     */
    @Override
    public String toString() {
        return "(x = " + x + ", y = " + y + ")";
    }
}