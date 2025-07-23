package pt.ipp.isep.dei.domain.City;

import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain._Others_.Inventory;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.TransformingResourceRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block of houses in the system.
 * Each HouseBlock has a position, can be assigned to a station,
 * and can produce resources. It also manages its own inventory.
 */
public class HouseBlock implements StationAssociations, Serializable {

    /** The name of the city this house block belongs to. */
    private String cityName;

    /** The position of the house block. */
    private Position position;

    /** The station assigned to this house block. */
    private Station assignedStation;

    /** The list of resources produced by this house block. */
    private List<HouseBlockResource> productions = new ArrayList<>();

    /** The unique identifier of the house block. */
    private int id;

    /** Static counter to generate unique IDs for each house block. */
    private static int idCounter = 0;

    /** The inventory of the house block. */
    private Inventory inventory = new Inventory();

    /** Indicates if the inventory has been updated. */
    private boolean updatedinventory = false;

    /** Repository for transforming resource types. */
    private TransformingResourceRepository transformingResourceRepository;

    /** List of consumable resource types for this house block. */
    private final List<ResourcesType> consumableResources;

    /**
     * Constructs a HouseBlock with the specified position and city name.
     * The assigned station is initially null. The ID is automatically generated.
     *
     * @param position the position of the house block
     * @param cityName the name of the city this house block belongs to
     */
    public HouseBlock(Position position, String cityName) {
        this.position = position;
        this.cityName = cityName;
        this.assignedStation = null;
        idCounter++;
        this.id = idCounter;
        initializeTransformingResourceRepository();
        consumableResources = List.of(
                transformingResourceRepository.getTransformingTypeByName("Food"),
                transformingResourceRepository.getTransformingTypeByName("Oil Goods"),
                transformingResourceRepository.getTransformingTypeByName("Jewellery"),
                transformingResourceRepository.getTransformingTypeByName("Beer"),
                transformingResourceRepository.getTransformingTypeByName("Electronic"),
                transformingResourceRepository.getTransformingTypeByName("Car"),
                transformingResourceRepository.getTransformingTypeByName("Furniture")
        );
    }

    /**
     * Initializes the transforming resource repository if it is not already initialized.
     */
    private void initializeTransformingResourceRepository() {
        if (transformingResourceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            transformingResourceRepository = repositories.getTransformingTypeRepository();
        }
    }

    /**
     * Gets the list of consumable resource types for this house block.
     *
     * @return the list of consumable resource types
     */
    public List<ResourcesType> getConsumableResources() {
        return consumableResources;
    }

    /**
     * Gets the position of the house block.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the house block.
     *
     * @param position the new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the name of the city this house block belongs to.
     *
     * @return the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Gets the assigned station of the house block.
     *
     * @return the assigned station
     */
    public Station getAssignedStation() {
        return assignedStation;
    }

    /**
     * Sets the assigned station of the house block.
     *
     * @param assignedStation the station to assign
     */
    public void setAssignedStation(Station assignedStation) {
        this.assignedStation = assignedStation;
    }

    /**
     * Gets the list of resources produced by the house block.
     *
     * @return the list of productions
     */
    public List<HouseBlockResource> getProductions() {
        return productions;
    }

    /**
     * Gets the unique identifier of the house block.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Adds a resource to the house block's inventory.
     *
     * @param resource the resource to add
     * @return the new quantity of the resource in the inventory
     */
    @Override
    public int addResourceToInventory(Resource resource) {
        return this.inventory.addResource(resource);
    }

    /**
     * Removes a resource from the house block's inventory.
     *
     * @param resource the resource to remove
     * @return the new quantity of the resource in the inventory
     */
    @Override
    public int removeResourceFromInventory(Resource resource) {
        return this.inventory.removeResource(resource);
    }

    /**
     * Gets the quantity of a specific resource type in the inventory.
     *
     * @param resourceType the type of resource
     * @return the quantity of the resource
     */
    @Override
    public int getResourceQuantity(ResourcesType resourceType) {
        return inventory.getResourceQuantity(resourceType.getName());
    }

    /**
     * Checks if a specific resource exists in the inventory.
     *
     * @param resource the resource type to check
     * @return true if the resource exists, false otherwise
     */
    @Override
    public boolean existsResourceInInventory(ResourcesType resource) {
        for (Resource res : inventory.getAllResources()) {
            if (res.getResourceType().getName().equals(resource.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the inventory of the house block.
     *
     * @return the inventory
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Checks if the inventory has been updated.
     *
     * @return true if updated, false otherwise
     */
    @Override
    public boolean isUpdatedInventory() {
        return updatedinventory;
    }

    /**
     * Sets the updated status of the inventory.
     *
     * @param updatedinventory true if updated, false otherwise
     */
    @Override
    public void setUpdatedInventory(boolean updatedinventory) {
        this.updatedinventory = updatedinventory;
    }

    /**
     * Returns a string representation of the house block.
     *
     * @return a string with the position and assigned station
     */
    @Override
    public String toString() {
        return "HouseBlock{" +
                "position=" + position +
                ", assignedStation=" + assignedStation +
                '}';
    }
}