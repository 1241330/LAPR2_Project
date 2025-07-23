package pt.ipp.isep.dei.domain.Resource;

/**
 * Represents a primary resource with specific properties such as maximum storage,
 * interval between resource generation, and quantity produced per generation.
 * Extends the {@link ResourcesType} class.
 */
public class PrimaryResource extends ResourcesType {

    /**
     * Constructs a PrimaryResource with the specified parameters.
     *
     * @param name The name of the resource.
     * @param maxResources The maximum number of resources that can be stored.
     * @param intervalBetweenResourceGeneration The interval between resource generation cycles.
     * @param quantityProduced The quantity produced per generation cycle.
     */
    public PrimaryResource(String name, int maxResources, int intervalBetweenResourceGeneration, int quantityProduced) {
        super(name, maxResources, intervalBetweenResourceGeneration, quantityProduced);
    }

    /**
     * Returns a string representation of the primary resource, including its properties.
     *
     * @return A formatted string with the resource's details.
     */
    @Override
    public String toString() {
        return getName() + " (Max resources storage: " + getMaxResources()
                + ", Interval between resource generation: " + getIntervalBetweenResourceGeneration()
                + ", Quantity produced per generation: " + getQuantityProduced() + ")";
    }

    /**
     * Gets the name of the resource.
     *
     * @return The name of the resource.
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Sets the name of the resource.
     *
     * @param name The new name of the resource.
     */
    @Override
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Gets the maximum number of resources that can be stored.
     *
     * @return The maximum resources storage.
     */
    @Override
    public int getMaxResources() {
        return super.getMaxResources();
    }

    /**
     * Sets the maximum number of resources that can be stored.
     *
     * @param maxResources The new maximum resources storage.
     */
    @Override
    public void setMaxResources(int maxResources) {
        super.setMaxResources(maxResources);
    }

    /**
     * Gets the interval between resource generation cycles.
     *
     * @return The interval between resource generation.
     */
    @Override
    public int getIntervalBetweenResourceGeneration() {
        return super.getIntervalBetweenResourceGeneration();
    }

    /**
     * Sets the interval between resource generation cycles.
     *
     * @param intervalBetweenResourceGeneration The new interval between resource generation.
     */
    @Override
    public void setIntervalBetweenResourceGeneration(int intervalBetweenResourceGeneration) {
        super.setIntervalBetweenResourceGeneration(intervalBetweenResourceGeneration);
    }

    /**
     * Gets the quantity produced per generation cycle.
     *
     * @return The quantity produced per generation.
     */
    @Override
    public int getQuantityProduced() {
        return super.getQuantityProduced();
    }

    /**
     * Sets the quantity produced per generation cycle.
     *
     * @param quantityProduced The new quantity produced per generation.
     */
    @Override
    public void setQuantityProduced(int quantityProduced) {
        super.setQuantityProduced(quantityProduced);
    }
}