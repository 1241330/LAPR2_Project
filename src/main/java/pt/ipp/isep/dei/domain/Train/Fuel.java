package pt.ipp.isep.dei.domain.Train;

import java.io.Serializable;

/**
 * Represents a fuel entity with a specific fuel type.
 * Implements Serializable for object serialization.
 */
public class Fuel implements Serializable {

    /** The type of fuel. */
    private FuelType fuelType;

    /**
     * Constructs a Fuel object with the specified fuel type.
     *
     * @param fuelType the type of fuel
     */
    public Fuel(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Gets the fuel type.
     *
     * @return the fuel type
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Sets the fuel type.
     *
     * @param fuelType the new fuel type to set
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Returns a string representation of the Fuel object.
     *
     * @return a string containing the fuel type
     */
    @Override
    public String toString() {
        return "Fuel{" +
                "fuelType=" + fuelType +
                '}';
    }
}