package pt.ipp.isep.dei.domain.Resource;

import java.io.Serializable;

/**
 * Represents a type of resource with specific properties such as name, maximum storage,
 * interval between resource generation, and quantity produced per generation.
 */
public class ResourcesType implements Serializable {

    /** The name of the resource type. */
    private String name;

    /** The maximum number of resources that can be stored. */
    private int maxResources;

    /** The interval between resource generation cycles. */
    private int intervalBetweenResourceGeneration;

    /** The quantity produced per generation cycle. */
    private int quantityProduced;

    /**
     * Constructs a ResourcesType with the specified parameters.
     *
     * @param name The name of the resource type.
     * @param maxResources The maximum number of resources that can be stored.
     * @param intervalBetweenResourceGeneration The interval between resource generation cycles.
     * @param quantityProduced The quantity produced per generation cycle.
     */
    public ResourcesType(String name, int maxResources, int intervalBetweenResourceGeneration, int quantityProduced) {
        this.name = name;
        this.maxResources = maxResources;
        this.intervalBetweenResourceGeneration = intervalBetweenResourceGeneration;
        this.quantityProduced = quantityProduced;
    }

    /**
     * Gets the name of the resource type.
     *
     * @return The name of the resource type.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the resource type.
     *
     * @param name The new name of the resource type.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the maximum number of resources that can be stored.
     *
     * @return The maximum resources storage.
     */
    public int getMaxResources() {
        return maxResources;
    }

    /**
     * Sets the maximum number of resources that can be stored.
     *
     * @param maxResources The new maximum resources storage.
     */
    public void setMaxResources(int maxResources) {
        this.maxResources = maxResources;
    }

    /**
     * Gets the interval between resource generation cycles.
     *
     * @return The interval between resource generation.
     */
    public int getIntervalBetweenResourceGeneration() {
        return intervalBetweenResourceGeneration;
    }

    /**
     * Sets the interval between resource generation cycles.
     *
     * @param intervalBetweenResourceGeneration The new interval between resource generation.
     */
    public void setIntervalBetweenResourceGeneration(int intervalBetweenResourceGeneration) {
        this.intervalBetweenResourceGeneration = intervalBetweenResourceGeneration;
    }

    /**
     * Gets the quantity produced per generation cycle.
     *
     * @return The quantity produced per generation.
     */
    public int getQuantityProduced() {
        return quantityProduced;
    }

    /**
     * Sets the quantity produced per generation cycle.
     *
     * @param quantityProduced The new quantity produced per generation.
     */
    public void setQuantityProduced(int quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    /**
     * Returns a string representation of the resource type, including its properties.
     *
     * @return A formatted string with the resource type's details.
     */
    @Override
    public String toString() {
        return "ResourcesType{" +
                "name='" + name + '\'' +
                ", maxResources=" + maxResources +
                ", intervalBetweenResourceGeneration=" + intervalBetweenResourceGeneration +
                ", quantityProduced=" + quantityProduced +
                '}';
    }
}