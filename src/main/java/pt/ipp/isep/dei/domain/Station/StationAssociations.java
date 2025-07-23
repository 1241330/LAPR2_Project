package pt.ipp.isep.dei.domain.Station;

import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain._Others_.Inventory;

import java.io.Serializable;

/**
 * Interface representing associations for a station, such as industries or house blocks.
 * Provides methods to manage resources in the inventory and track inventory updates.
 */
public interface StationAssociations extends Serializable {

    /**
     * Adds a resource to the inventory.
     *
     * @param resource the resource to add
     * @return the new quantity of the resource in the inventory
     */
    int addResourceToInventory(Resource resource);

    /**
     * Removes a resource from the inventory.
     *
     * @param resource the resource to remove
     * @return the new quantity of the resource in the inventory
     */
    int removeResourceFromInventory(Resource resource);

    /**
     * Gets the quantity of a specific resource type in the inventory.
     *
     * @param resourceType the type of resource
     * @return the quantity of the resource
     */
    int getResourceQuantity(ResourcesType resourceType);

    /**
     * Checks if a specific resource type exists in the inventory.
     *
     * @param resource the type of resource
     * @return true if the resource exists, false otherwise
     */
    boolean existsResourceInInventory(ResourcesType resource);

    /**
     * Gets the inventory associated with the station.
     *
     * @return the inventory
     */
    Inventory getInventory();

    /**
     * Checks if the inventory has been updated.
     *
     * @return true if the inventory is updated, false otherwise
     */
    boolean isUpdatedInventory();

    /**
     * Sets the inventory update status.
     *
     * @param updatedInventory true if the inventory is updated, false otherwise
     */
    void setUpdatedInventory(boolean updatedInventory);
}