package pt.ipp.isep.dei.domain.Resource;

import java.io.Serializable;

/**
 * Represents a resource with a specific type and quantity.
 * This class encapsulates the concept of a resource, which is defined by its type and the amount available.
 */
public class Resource implements Serializable {

    /** The type of the resource. */
    private ResourcesType resourceType;

    /** The quantity of the resource. */
    private int quantity;

    /**
     * Constructs a Resource with the specified type and quantity.
     *
     * @param resourceType The type of the resource.
     * @param quantity The quantity of the resource.
     */
    public Resource(ResourcesType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    /**
     * Gets the type of the resource.
     *
     * @return The resource type.
     */
    public ResourcesType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the type of the resource.
     *
     * @param resourceType The new resource type.
     */
    public void setResourceType(ResourcesType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * Gets the quantity of the resource.
     *
     * @return The quantity of the resource.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the resource.
     *
     * @param quantity The new quantity of the resource.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns a string representation of the resource, including its type and quantity.
     *
     * @return A formatted string with the resource's details.
     */
    @Override
    public String toString() {
        return resourceType.getName() + " - " + quantity + " units";
    }
}