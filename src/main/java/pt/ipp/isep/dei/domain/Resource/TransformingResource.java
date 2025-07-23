package pt.ipp.isep.dei.domain.Resource;

import java.util.List;

/**
 * Represents a transforming resource type that requires other resource types for its transformation.
 * Inherits properties from {@link ResourcesType} and adds a list of needed resources for transformation.
 */
public class TransformingResource extends ResourcesType {

    /** The list of resource types needed for the transformation. */
    private List<ResourcesType> neededResources;

    /**
     * Constructs a TransformingResource with the specified parameters.
     *
     * @param name The name of the resource type.
     * @param maxResources The maximum number of resources that can be stored.
     * @param intervalBetweenResourceGeneration The interval between resource generation cycles.
     * @param quantityProduced The quantity produced per generation cycle.
     * @param neededResources The list of resource types needed for the transformation.
     */
    public TransformingResource(String name, int maxResources, int intervalBetweenResourceGeneration, int quantityProduced, List<ResourcesType> neededResources) {
        super(name, maxResources, intervalBetweenResourceGeneration, quantityProduced);
        this.neededResources = neededResources;
    }

    /**
     * Gets the list of resource types needed for the transformation.
     *
     * @return The list of needed resource types.
     * @throws IllegalArgumentException if the list is null or empty.
     */
    public List<ResourcesType> getNeededResources() {
        if (neededResources == null || neededResources.isEmpty()) {
            throw new IllegalArgumentException("No resources needed for transformation.");
        }
        return neededResources;
    }

    /**
     * Sets the list of resource types needed for the transformation.
     *
     * @param neededResources The new list of needed resource types.
     */
    public void setNeededResources(List<ResourcesType> neededResources) {
        this.neededResources = neededResources;
    }

    /**
     * Gets a string representation of the transformation, showing the resource and its needed resources.
     *
     * @return A formatted string representing the transformation.
     */
    public String getTransformation() {
        StringBuilder transformation = new StringBuilder(getName()).append(": (");
        for (int i = 0; i < neededResources.size(); i++) {
            transformation.append(neededResources.get(i).getName());
            if (i < neededResources.size() - 1) {
                transformation.append(" + ");
            }
        }
        transformation.append(")");
        return transformation.toString();
    }

    /**
     * Returns a string representation of the transforming resource, including its properties.
     *
     * @return A formatted string with the transforming resource's details.
     */
    @Override
    public String toString() {
        return getName() + " (Max resources storage: " + getMaxResources() +
                ", Interval between resource generation: " + getIntervalBetweenResourceGeneration() +
                ", Quantity produced per generation: " + getQuantityProduced() + ")";
    }
}