package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Resource.TransformingResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing TransformingResource entities.
 * Provides methods to add, retrieve, and copy transforming resources.
 */
public class TransformingResourceRepository implements Serializable {

    /**
     * Static list that stores all transforming resources.
     */
    private static List<TransformingResource> transformingResources = new ArrayList<>();

    /**
     * Constructs an empty TransformingResourceRepository.
     */
    public TransformingResourceRepository() {
        // Default constructor
    }

    /**
     * Adds a transforming resource to the repository.
     *
     * @param transformingResource The transforming resource to add.
     * @throws IllegalArgumentException if the transforming resource is null.
     */
    public void addTransformingType(TransformingResource transformingResource) {
        if (transformingResource == null) {
            throw new IllegalArgumentException("Transforming type cannot be null");
        }
        transformingResources.add(transformingResource);
    }

    /**
     * Retrieves a list of all transforming resources in the repository.
     *
     * @return A new list containing all transforming resources.
     */
    public List<TransformingResource> getAllTransformingResources() {
        return new ArrayList<>(transformingResources);
    }

    /**
     * Retrieves a transforming resource by its name.
     *
     * @param name The name of the transforming resource.
     * @return The transforming resource with the specified name, or null if not found.
     */
    public TransformingResource getTransformingTypeByName(String name) {
        for (TransformingResource transformingResource : transformingResources) {
            if (transformingResource.getName().equals(name)) {
                return transformingResource;
            }
        }
        return null;
    }

    /**
     * Retrieves a copy of all transforming resources, creating new instances for each.
     *
     * @return A list of copied transforming resources.
     */
    public List<TransformingResource> getCopyTransformingResources() {
        List<TransformingResource> copiedList = new ArrayList<>();
        for (TransformingResource resource : transformingResources) {
            copiedList.add(new TransformingResource(
                    resource.getName(),
                    resource.getMaxResources(),
                    resource.getIntervalBetweenResourceGeneration(),
                    resource.getQuantityProduced(),
                    new ArrayList<>(resource.getNeededResources())
            ));
        }
        return copiedList;
    }

    /**
     * Gets the static list of transforming resources.
     *
     * @return The list of transforming resources.
     */
    public static List<TransformingResource> getTransformingResources() {
        return transformingResources;
    }

    /**
     * Sets the static list of transforming resources.
     *
     * @param transformingResources The list of transforming resources to set.
     */
    public static void setTransformingResources(List<TransformingResource> transformingResources) {
        TransformingResourceRepository.transformingResources = transformingResources;
    }
}