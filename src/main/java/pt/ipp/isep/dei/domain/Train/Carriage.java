package pt.ipp.isep.dei.domain.Train;

import java.io.Serializable;

/**
 * Represents a carriage that can be attached to a train.
 * Each carriage has a name, an image path, the year it started operation,
 * its acquisition cost, its maximum resource capacity, and a flag indicating if it is in use.
 */
public class Carriage extends Train implements Serializable {

    /** The name of the carriage */
    private String name;

    /** The path to the image representing the carriage */
    private String imagePath;

    /** The year the carriage started operation */
    private int startYearOperation;

    /** The acquisition cost of the carriage */
    private int acquisitionCost;

    /** The maximum resource capacity of the carriage */
    private int maxResourceCapacity;

    /** Indicates if the carriage is currently in use */
    private boolean inUse;

    /**
     * Constructs a new Carriage with the specified parameters.
     *
     * @param name                the name of the carriage
     * @param imagePath           the path to the image representing the carriage
     * @param startYearOperation  the year the carriage started operation
     * @param acquisitionCost     the acquisition cost of the carriage
     * @param maxResourceCapacity the maximum resource capacity of the carriage
     */
    public Carriage(String name, String imagePath, int startYearOperation, int acquisitionCost, int maxResourceCapacity) {
        this.name = name;
        this.imagePath = imagePath;
        this.startYearOperation = startYearOperation;
        this.acquisitionCost = acquisitionCost;
        this.maxResourceCapacity = maxResourceCapacity;
        this.inUse = false;
    }

    /**
     * Gets the name of the carriage.
     *
     * @return the name of the carriage
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the carriage.
     *
     * @param name the new name of the carriage
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the path to the image representing the carriage.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the path to the image representing the carriage.
     *
     * @param imagePath the new image path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Gets the year the carriage started operation.
     *
     * @return the start year of operation
     */
    public int getStartYearOperation() {
        return startYearOperation;
    }

    /**
     * Sets the year the carriage started operation.
     *
     * @param startYearOperation the new start year of operation
     */
    public void setStartYearOperation(int startYearOperation) {
        this.startYearOperation = startYearOperation;
    }

    /**
     * Gets the acquisition cost of the carriage.
     *
     * @return the acquisition cost
     */
    public int getAcquisitionCost() {
        return acquisitionCost;
    }

    /**
     * Sets the acquisition cost of the carriage.
     *
     * @param acquisitionCost the new acquisition cost
     */
    public void setAcquisitionCost(int acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
    }

    /**
     * Gets the maximum resource capacity of the carriage.
     *
     * @return the maximum resource capacity
     */
    public int getMaxResourceCapacity() {
        return maxResourceCapacity;
    }

    /**
     * Sets the maximum resource capacity of the carriage.
     *
     * @param maxResourceCapacity the new maximum resource capacity
     */
    public void setMaxResourceCapacity(int maxResourceCapacity) {
        this.maxResourceCapacity = maxResourceCapacity;
    }

    /**
     * Checks if the carriage is currently in use.
     *
     * @return true if the carriage is in use, false otherwise
     */
    public boolean getInUse() {
        return inUse;
    }

    /**
     * Sets the in-use status of the carriage.
     *
     * @param inUse true if the carriage is in use, false otherwise
     */
    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    /**
     * Returns a string representation of the carriage.
     *
     * @return a string with the carriage details
     */
    @Override
    public String toString() {
        return "Carriage → " +
                "name: " + name +
                " | Acquisition Price: " + acquisitionCost + " €";
    }
}