package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing HouseBlockResource entities.
 * Provides methods to add, retrieve, and manage house block resources.
 */
public class HouseBlockResourceRepository implements Serializable {

    /**
     * Static list that stores all house block resources.
     */
    private static List<HouseBlockResource> houseBlockResources = new ArrayList<>();

    /**
     * Constructs a HouseBlockResourceRepository.
     */
    public HouseBlockResourceRepository() {
    }

    /**
     * Adds a house block resource to the repository.
     *
     * @param houseBlockResource the house block resource to be added
     * @throws IllegalArgumentException if the house block resource is null
     */
    public void addHouseBlockResource(HouseBlockResource houseBlockResource) {
        if (houseBlockResource == null) {
            throw new IllegalArgumentException("House block resource cannot be null");
        }
        houseBlockResources.add(houseBlockResource);
    }

    /**
     * Returns a copy of the list of all house block resources.
     *
     * @return a new list containing all house block resources
     */
    public List<HouseBlockResource> getAllHouseBlockResources() {
        return new ArrayList<>(houseBlockResources);
    }

    /**
     * Retrieves a house block resource by its name.
     *
     * @param name the name of the house block resource
     * @return the house block resource with the specified name, or null if not found
     */
    public HouseBlockResource getHouseBlockResourceByName(String name) {
        for (HouseBlockResource houseBlockResource : houseBlockResources) {
            if (houseBlockResource.getName().equals(name)) {
                return houseBlockResource;
            }
        }
        return null;
    }

    /**
     * Returns a deep copy of the list of house block resources.
     *
     * @return a new list containing copies of all house block resources
     */
    public List<HouseBlockResource> getCopyOfHouseBlockResources() {
        List<HouseBlockResource> copiedList = new ArrayList<>();
        for (HouseBlockResource resource : houseBlockResources) {
            copiedList.add(new HouseBlockResource(
                    resource.getName(),
                    resource.getMaxResources(),
                    resource.getIntervalBetweenResourceGeneration(),
                    resource.getQuantityProduced()
            ));
        }
        return copiedList;
    }

    /**
     * Gets the static list of house block resources.
     *
     * @return the list of house block resources
     */
    public static List<HouseBlockResource> getHouseBlockResources() {
        return houseBlockResources;
    }

    /**
     * Sets the static list of house block resources.
     *
     * @param houseBlockResources the list of house block resources to set
     */
    public static void setHouseBlockResources(List<HouseBlockResource> houseBlockResources) {
        HouseBlockResourceRepository.houseBlockResources = houseBlockResources;
    }
}