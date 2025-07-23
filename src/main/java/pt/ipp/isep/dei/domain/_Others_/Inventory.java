package pt.ipp.isep.dei.domain._Others_;

import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory that manages a collection of resources.
 */
public class Inventory implements Serializable {

    private List<Resource> resources;

    /**
     * Constructs an empty inventory.
     */
    public Inventory() {
        this.resources = new ArrayList<>();
    }

    /**
     * Adds a resource to the inventory.
     * If the resource type already exists, increases its quantity up to the maximum allowed.
     *
     * @param newResource the resource to add
     * @return the quantity actually added, -1 if the resource is already at max capacity
     */
    public int addResource(Resource newResource) {
        int maxResources = newResource.getResourceType().getMaxResources();
        int quantityProduced = newResource.getResourceType().getQuantityProduced();
        for (Resource existing : resources) {
            if (existing.getResourceType().getName().equals(newResource.getResourceType().getName())) {
                int actualQuantity = existing.getQuantity();
                if (actualQuantity == maxResources) {
                    return -1;
                }
                int returnValue = quantityProduced;
                int total = actualQuantity + quantityProduced;
                if (total > maxResources) {
                    total = maxResources;
                    returnValue = maxResources - actualQuantity;
                }
                existing.setQuantity(total);
                return returnValue;
            }
        }
        if (maxResources < newResource.getQuantity()) {
            newResource.setQuantity(maxResources);
        }
        resources.add(newResource);
        return quantityProduced;
    }

    /**
     * Adds a resource to the inventory without considering the maximum limit.
     *
     * @param newResource the resource to add
     * @return the quantity added
     */
    public int addResourceWithoutLimit(Resource newResource) {
        for (Resource existing : resources) {
            if (existing.getResourceType().getName().equals(newResource.getResourceType().getName())) {
                int actualQuantity = existing.getQuantity();
                int quantityProduced = newResource.getResourceType().getQuantityProduced();
                existing.setQuantity(actualQuantity + quantityProduced);
                return quantityProduced;
            }
        }
        resources.add(newResource);
        return newResource.getResourceType().getQuantityProduced();
    }

    /**
     * Removes a resource from the inventory.
     *
     * @param resource the resource to remove
     * @return the quantity removed, -1 if not enough quantity, -2 if resource not found
     */
    public int removeResource(Resource resource) {
        for (Resource existing : resources) {
            if (existing.getResourceType().getName().equals(resource.getResourceType().getName())) {
                int actualQuantity = existing.getQuantity();
                int newQuantity = actualQuantity - resource.getQuantity();
                if (newQuantity < 0) {
                    return -1;
                }
                if (newQuantity == 0) {
                    resources.remove(existing);
                } else {
                    existing.setQuantity(newQuantity);
                }
                return resource.getQuantity();
            }
        }
        return -2;
    }

    /**
     * Adds all resources from a list to the inventory, ignoring maximum limits.
     *
     * @param list the list of resources to add
     */
    public void addAll(List<Resource> list) {
        for (Resource resource : list) {
            addResourceWithoutLimit(new Resource(resource.getResourceType(), resource.getQuantity()));
        }
    }

    /**
     * Returns all resources in the inventory.
     *
     * @return the list of resources
     */
    public List<Resource> getAllResources() {
        return resources;
    }

    /**
     * Returns all resource types present in the inventory.
     *
     * @return the list of resource types
     */
    public List<ResourcesType> getAllResourcesType() {
        List<ResourcesType> resourcesTypes = new ArrayList<>();
        for (Resource resource : resources) {
            resourcesTypes.add(resource.getResourceType());
        }
        return resourcesTypes;
    }

    /**
     * Gets a resource by its type.
     *
     * @param resourceType the type of the resource
     * @return the resource, or null if not found
     */
    public Resource getResourceByType(ResourcesType resourceType) {
        for (Resource resource : getAllResources()) {
            if (resource.getResourceType().getName().equals(resourceType.getName())) {
                return resource;
            }
        }
        return null;
    }

    /**
     * Checks if the inventory is full for all resources.
     *
     * @return true if all resources are at max capacity, false otherwise
     */
    public boolean inventoryIsFull() {
        if (resources.isEmpty()) {
            return false;
        }
        for (Resource resource : resources) {
            if (resource.getQuantity() < resource.getResourceType().getMaxResources()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a resource exists in the inventory by type.
     *
     * @param resource the resource type to check
     * @return true if exists, false otherwise
     */
    public boolean existsResourceInInventory(ResourcesType resource) {
        for (Resource res : getAllResources()) {
            if (res.getResourceType().getName().equals(resource.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a resource by its name.
     *
     * @param resourceName the name of the resource
     * @return the resource, or null if not found
     */
    public Resource getResourceTypeByName(String resourceName) {
        for (Resource resource : resources) {
            if (resource.getResourceType().getName().equals(resourceName)) {
                return resource;
            }
        }
        return null;
    }

    /**
     * Checks if a resource exists in the inventory by name.
     *
     * @param resource the name of the resource
     * @return true if exists, false otherwise
     */
    public boolean existsResourceInInventoryByName(String resource) {
        for (Resource res : getAllResources()) {
            if (res.getResourceType().getName().equals(resource)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a resource from the inventory by type.
     *
     * @param resource the resource type to remove
     */
    public void remove(ResourcesType resource) {
        for (Resource res : getAllResources()) {
            if (res.getResourceType().getName().equals(resource.getName())) {
                resources.remove(res);
                break;
            }
        }
    }

    /**
     * Gets the quantity of a resource by its name.
     *
     * @param resourceName the name of the resource
     * @return the quantity, or 0 if not found
     */
    public int getResourceQuantity(String resourceName) {
        for (Resource resource : resources) {
            if (resource.getResourceType().getName().equals(resourceName)) {
                return resource.getQuantity();
            }
        }
        return 0;
    }

    /**
     * Gets the list of resources.
     *
     * @return the list of resources
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * Sets the list of resources.
     *
     * @param resources the list of resources to set
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    /**
     * Gets the empty space available in the inventory.
     *
     * @param maxSpace the maximum space of the inventory
     * @return the empty space available
     */
    public int getEmptySpace(int maxSpace) {
        int resourceQuantity = 0;
        for (Resource resource : resources) {
            resourceQuantity += resource.getQuantity();
        }
        return maxSpace - resourceQuantity;
    }

    /**
     * Adds a resource directly to the inventory without any checks.
     *
     * @param resource the resource to add
     */
    public void addResourceDirecly(Resource resource) {
        resources.add(resource);
    }
}