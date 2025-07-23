package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Resource.PrimaryResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing PrimaryResource entities.
 * Provides methods to add, retrieve, and copy primary resources.
 */
public class PrimaryResourceRepository implements Serializable {

    /**
     * Static list that stores all primary resources.
     */
    private static List<PrimaryResource> primaryResources = new ArrayList<>();

    /**
     * Constructs an empty PrimaryResourceRepository.
     */
    public PrimaryResourceRepository() {
        // Default constructor
    }

    /**
     * Adds a primary resource to the repository.
     *
     * @param primaryResource The primary resource to add.
     * @throws IllegalArgumentException if the primary resource is null.
     */
    public void addPrimaryResource(PrimaryResource primaryResource) {
        if (primaryResource == null) {
            throw new IllegalArgumentException("Primary resource cannot be null");
        }
        primaryResources.add(primaryResource);
    }

    /**
     * Retrieves a copy of the list of all primary resources in the repository.
     *
     * @return A new list containing all primary resources.
     */
    public List<PrimaryResource> getAllPrimaryResources() {
        return new ArrayList<>(primaryResources);
    }

    /**
     * Retrieves a primary resource by its name.
     *
     * @param name The name of the primary resource.
     * @return The primary resource with the specified name, or null if not found.
     */
    public PrimaryResource getPrimaryResourceByName(String name) {
        for (PrimaryResource primaryResource : primaryResources) {
            if (primaryResource.getName().equals(name)) {
                return primaryResource;
            }
        }
        return null;
    }

    /**
     * Retrieves a copy of all primary resources as new instances.
     *
     * @return A list of copied primary resources.
     */
    public List<PrimaryResource> getCopyPrimaryResources() {
        List<PrimaryResource> copiedList = new ArrayList<>();
        for (PrimaryResource resource : primaryResources) {
            copiedList.add(new PrimaryResource(
                    resource.getName(),
                    resource.getMaxResources(),
                    resource.getIntervalBetweenResourceGeneration(),
                    resource.getQuantityProduced()
            ));
        }
        return copiedList;
    }

    /**
     * Gets the static list of primary resources.
     *
     * @return The list of primary resources.
     */
    public static List<PrimaryResource> getPrimaryResources() {
        return primaryResources;
    }

    /**
     * Sets the static list of primary resources.
     *
     * @param primaryResources The list of primary resources to set.
     */
    public static void setPrimaryResources(List<PrimaryResource> primaryResources) {
        PrimaryResourceRepository.primaryResources = primaryResources;
    }
}