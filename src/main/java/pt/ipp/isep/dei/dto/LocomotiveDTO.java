package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Train.FuelType;

/**
 * Data Transfer Object for Locomotive details.
 * Encapsulates all relevant attributes for transferring locomotive data between layers.
 */
public class LocomotiveDTO {

    /** The name of the locomotive. */
    private String name;

    /** The image path of the locomotive. */
    private String imagePath;

    /** The power of the locomotive. */
    private double power;

    /** The acceleration of the locomotive. */
    private double acceleration;

    /** The top speed of the locomotive. */
    private double topSpeed;

    /** The year the locomotive started operation. */
    private int startYearOperation;

    /** The acquisition price of the locomotive. */
    private int acquisitionPrice;

    /** The fuel type of the locomotive. */
    private FuelType fuelType;

    /** The associated railroad line of the locomotive. */
    private int railroadLineAssosiated;

    /** The maximum number of carriages the locomotive can pull. */
    private int maxCarriages;

    /** Indicates if the locomotive is currently in use. */
    private boolean inUse;

    /** The maintenance cost of the locomotive. */
    private int maintenanceCost;

    /**
     * Constructs a LocomotiveDTO with all attributes.
     *
     * @param name the name of the locomotive
     * @param imagePath the image path of the locomotive
     * @param power the power of the locomotive
     * @param acceleration the acceleration of the locomotive
     * @param topSpeed the top speed of the locomotive
     * @param startYearOperation the year the locomotive started operation
     * @param acquisitionPrice the acquisition price of the locomotive
     * @param fuelType the fuel type of the locomotive
     * @param railroadLineAssosiated the associated railroad line
     * @param maxCarriages the maximum number of carriages
     * @param inUse whether the locomotive is in use
     * @param maintenanceCost the maintenance cost of the locomotive
     */
    public LocomotiveDTO(String name, String imagePath, double power, double acceleration, double topSpeed,
                         int startYearOperation, int acquisitionPrice, FuelType fuelType,
                         int railroadLineAssosiated, int maxCarriages, boolean inUse, int maintenanceCost) {
        this.name = name;
        this.imagePath = imagePath;
        this.power = power;
        this.acceleration = acceleration;
        this.topSpeed = topSpeed;
        this.startYearOperation = startYearOperation;
        this.acquisitionPrice = acquisitionPrice;
        this.fuelType = fuelType;
        this.railroadLineAssosiated = railroadLineAssosiated;
        this.maxCarriages = maxCarriages;
        this.inUse = inUse;
        this.maintenanceCost = maintenanceCost;
    }

    /**
     * Gets the name of the locomotive.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the locomotive.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the image path of the locomotive.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path of the locomotive.
     *
     * @param imagePath the image path to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Gets the power of the locomotive.
     *
     * @return the power
     */
    public double getPower() {
        return power;
    }

    /**
     * Sets the power of the locomotive.
     *
     * @param power the power to set
     */
    public void setPower(double power) {
        this.power = power;
    }

    /**
     * Gets the acceleration of the locomotive.
     *
     * @return the acceleration
     */
    public double getAcceleration() {
        return acceleration;
    }

    /**
     * Sets the acceleration of the locomotive.
     *
     * @param acceleration the acceleration to set
     */
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Gets the top speed of the locomotive.
     *
     * @return the top speed
     */
    public double getTopSpeed() {
        return topSpeed;
    }

    /**
     * Sets the top speed of the locomotive.
     *
     * @param topSpeed the top speed to set
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
     * @param startYearOperation the start year of operation to set
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
     * @param acquisitionPrice the acquisition price to set
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
     * @param fuelType the fuel type to set
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
     * @param railroadLineAssosiated the associated railroad line to set
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
     * @param maxCarriages the maximum number of carriages to set
     */
    public void setMaxCarriages(int maxCarriages) {
        this.maxCarriages = maxCarriages;
    }

    /**
     * Checks if the locomotive is currently in use.
     *
     * @return true if in use, false otherwise
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * Sets the in-use status of the locomotive.
     *
     * @param inUse true if in use, false otherwise
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
     * @param maintenanceCost the maintenance cost to set
     */
    public void setMaintenanceCost(int maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }
}