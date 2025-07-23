package pt.ipp.isep.dei.domain.Train;

import java.io.Serializable;

/**
 * Represents a locomotive, which is a type of train with specific attributes such as power,
 * acceleration, top speed, year of operation start, acquisition price, fuel type, associated railroad line,
 * and maximum number of carriages it can pull.
 */
public class Locomotive extends Train implements Serializable {

    /** The name of the locomotive */
    private String name;

    /** The path to the image representing the locomotive */
    private String imagePath;

    /** The power of the locomotive */
    private double power;

    /** The acceleration of the locomotive */
    private double acceleration;

    /** The top speed of the locomotive */
    private double topSpeed;

    /** The year the locomotive started operation */
    private int startYearOperation;

    /** The acquisition price of the locomotive */
    private int acquisitionPrice;

    /** The type of fuel used by the locomotive */
    private FuelType fuelType;

    /** The associated railroad line of the locomotive */
    private int railroadLineAssosiated;

    /** The maximum number of carriages the locomotive can pull */
    private int maxCarriages;

    /** Indicates if the locomotive is currently in use */
    private boolean inUse;

    /** The maintenance cost of the locomotive */
    private int maintenanceCost;

    /**
     * Constructs a new Locomotive with the specified parameters.
     *
     * @param name                the name of the locomotive
     * @param imagePath           the path to the image representing the locomotive
     * @param power               the power of the locomotive
     * @param acceleration        the acceleration of the locomotive
     * @param topSpeed            the top speed of the locomotive
     * @param startYearOperation  the year the locomotive started operation
     * @param acquisitionPrice    the acquisition price of the locomotive
     * @param fuelType            the type of fuel used by the locomotive
     * @param maxCarriages        the maximum number of carriages the locomotive can pull
     * @param maintenanceCost     the maintenance cost of the locomotive
     */
    public Locomotive(String name, String imagePath, double power, double acceleration, double topSpeed,
                      int startYearOperation, int acquisitionPrice, FuelType fuelType, int maxCarriages, int maintenanceCost) {
        super();
        this.name = name;
        this.imagePath = imagePath;
        this.power = power;
        this.acceleration = acceleration;
        this.topSpeed = topSpeed;
        this.startYearOperation = startYearOperation;
        this.acquisitionPrice = acquisitionPrice;
        this.fuelType = fuelType;
        this.railroadLineAssosiated = 0; // Default value indicating no association
        this.maxCarriages = maxCarriages;
        this.inUse = false;
        this.maintenanceCost = maintenanceCost;
    }

    /**
     * Gets the name of the locomotive.
     *
     * @return the name of the locomotive
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the locomotive.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the path to the image representing the locomotive.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the path to the image representing the locomotive.
     *
     * @param imagePath the new image path to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Gets the power of the locomotive.
     *
     * @return the power of the locomotive
     */
    public double getPower() {
        return power;
    }

    /**
     * Sets the power of the locomotive.
     *
     * @param power the new power to set
     */
    public void setPower(double power) {
        this.power = power;
    }

    /**
     * Gets the acceleration of the locomotive.
     *
     * @return the acceleration of the locomotive
     */
    public double getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the acceleration of the locomotive.
     *
     * @param acceleration the new acceleration to set
     */
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Gets the top speed of the locomotive.
     *
     * @return the top speed of the locomotive
     */
    public double getTopSpeed() {
        return topSpeed;
    }

    /**
     * Sets the top speed of the locomotive.
     *
     * @param topSpeed the new top speed to set
     */
    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    /**
     * Gets the year the locomotive started operation.
     *
     * @return the start year of operation
     */
    public int getStartYearOperation() {
        return startYearOperation;
    }

    /**
     * Sets the year the locomotive started operation.
     *
     * @param startYearOperation the new start year to set
     */
    public void setStartYearOperation(int startYearOperation) {
        this.startYearOperation = startYearOperation;
    }

    /**
     * Gets the acquisition price of the locomotive.
     *
     * @return the acquisition price
     */
    public int getAcquisitionPrice() {
        return acquisitionPrice;
    }

    /**
     * Sets the acquisition price of the locomotive.
     *
     * @param acquisitionPrice the new acquisition price to set
     */
    public void setAcquisitionPrice(int acquisitionPrice) {
        this.acquisitionPrice = acquisitionPrice;
    }

    /**
     * Gets the fuel type of the locomotive.
     *
     * @return the fuel type
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Sets the fuel type of the locomotive.
     *
     * @param fuelType the new fuel type to set
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Gets the associated railroad line of the locomotive.
     *
     * @return the associated railroad line
     */
    public int getRailroadLineAssosiated() {
        return railroadLineAssosiated;
    }

    /**
     * Sets the associated railroad line of the locomotive.
     *
     * @param railroadLineAssosiated the new associated railroad line to set
     */
    public void setRailroadLineAssosiated(int railroadLineAssosiated) {
        this.railroadLineAssosiated = railroadLineAssosiated;
    }

    /**
     * Gets the maximum number of carriages the locomotive can pull.
     *
     * @return the maximum number of carriages
     */
    public int getMaxCarriages() {
        return maxCarriages;
    }

    /**
     * Sets the maximum number of carriages the locomotive can pull.
     *
     * @param maxCarriages the new maximum number of carriages
     */
    public void setMaxCarriages(int maxCarriages) {
        this.maxCarriages = maxCarriages;
    }

    /**
     * Checks if the locomotive is currently in use.
     *
     * @return true if the locomotive is in use, false otherwise
     */
    public boolean getInUse() {
        return inUse;
    }

    /**
     * Sets the in-use status of the locomotive.
     *
     * @param inUse true if the locomotive is in use, false otherwise
     */
    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    /**
     * Gets the maintenance cost of the locomotive.
     *
     * @return the maintenance cost
     */
    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    /**
     * Sets the maintenance cost of the locomotive.
     *
     * @param maintenanceCost the new maintenance cost to set
     */
    public void setMaintenanceCost(int maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    /**
     * Returns a string representation of the locomotive.
     *
     * @return a string with the main attributes of the locomotive
     */
    @Override
    public String toString() {
        return "Locomotive → " +
                "name: " + name +
                " | acquisitionPrice: " + acquisitionPrice + " €" +
                " | power: " + power +
                " | acceleration: " + acceleration +
                " | topSpeed: " + topSpeed +
                " | fuel: " + fuelType +
                " | railroadLineAssosiated: " + railroadLineAssosiated;
    }
}