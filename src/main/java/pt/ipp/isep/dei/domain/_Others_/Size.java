package pt.ipp.isep.dei.domain._Others_;

import java.io.Serializable;

/**
 * Represents a two-dimensional size with width and height.
 * Both dimensions must be positive values.
 */
public class Size implements Serializable {
    private double width;
    private double height;

    /**
     * Constructs a Size object with the specified width and height.
     *
     * @param width  the width of the size (must be positive)
     * @param height the height of the size (must be positive)
     * @throws IllegalArgumentException if width or height is not positive
     */
    public Size(double width, double height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                "Dimensions must be positive values. Received: width=" + width + ", height=" + height
            );
        }
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of this size as an integer.
     *
     * @return the width
     */
    public int getWidth() {
        return (int) width;
    }

    /**
     * Sets the width of this size.
     *
     * @param width the new width (must be positive)
     * @throws IllegalArgumentException if width is not positive
     */
    public void setWidth(int width) throws IllegalArgumentException {
        if (width <= 0) {
            throw new IllegalArgumentException(
                "Width must be a positive value. Received: " + width
            );
        }
        this.width = width;
    }

    /**
     * Returns the height of this size as an integer.
     *
     * @return the height
     */
    public int getHeight() {
        return (int) height;
    }

    /**
     * Sets the height of this size.
     *
     * @param height the new height (must be positive)
     * @throws IllegalArgumentException if height is not positive
     */
    public void setHeight(int height) throws IllegalArgumentException {
        if (height <= 0) {
            throw new IllegalArgumentException(
                "Height must be a positive value. Received: " + height
            );
        }
        this.height = height;
    }

    /**
     * Returns a string representation of this size in the format (width: x, height: y).
     *
     * @return a string representation of the size
     */
    @Override
    public String toString() {
        return "(width: " + width + ", height: " + height + ")";
    }
}