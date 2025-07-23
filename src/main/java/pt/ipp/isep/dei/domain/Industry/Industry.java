package pt.ipp.isep.dei.domain.Industry;

import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain._Others_.Inventory;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an industry as a map element with a name, type, and position.
 * Extends MapElement to inherit basic map element functionality.
 * Implements StationAssociations and Serializable.
 */
public class Industry extends MapElement implements StationAssociations, Serializable {

    /** Unique identifier for the industry. */
    private int id;

    /** Name of the industry. */
    private final String name;

    /** Type of the industry. */
    private final IndustryType type;

    /** Station assigned to this industry. */
    private Station assignedStation;

    /** Inventory of resources for this industry. */
    private Inventory inventory = new Inventory();

    /** Flag indicating if the inventory was updated. */
    private boolean updatedinventory = false;

    /** Counter for created stations (used for unique IDs). */
    private static int counterCreatedStations = 0;

    /**
     * Constructs an Industry with the specified name, type, and position.
     *
     * @param name the name of the industry
     * @param type the type of industry
     * @param position the position of the industry on the map
     */
    public Industry(String name, IndustryType type, Position position) {
        super(position, calculateOccupiedPositions(position));
        this.name = name;
        this.type = type;
        this.id = counterCreatedStations;
        counterCreatedStations++;
        assignedStation = null;
    }

    /**
     * Calculates the positions occupied by this industry.
     *
     * @param position the position of the industry
     * @return a list containing the position occupied by the industry, or null if position is null
     */
    private static List<Position> calculateOccupiedPositions(Position position) {
        if (position != null) {
            List<Position> positions = new ArrayList<>();
            positions.add(position);
            return positions;
        }
        return null;
    }

    /**
     * Gets the name of the industry.
     *
     * @return the industry name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique identifier of the industry.
     *
     * @return the industry ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the type of the map element.
     *
     * @return the string "Industry"
     */
    @Override
    public String getType() {
        return "Industry";
    }

    /**
     * Returns a string representation of the industry.
     *
     * @return a formatted string with name, type, and position
     */
    @Override
    public String toString() {
        return name + " - " + type.getDescription() + " at (" + getPosition().getX() + "," + getPosition().getY() + ")";
    }

    /**
     * Gets the station associated with this industry.
     *
     * @return the assigned station
     */
    public Station getStationIdentifier() {
        return assignedStation;
    }

    /**
     * Sets the station associated with this industry.
     *
     * @param assignedStation the station to associate with this industry
     */
    public void setAssignedStation(Station assignedStation) {
        this.assignedStation = assignedStation;
    }

    /**
     * Gets the type of the industry.
     *
     * @return the industry type
     */
    public IndustryType getIndustryType() {
        return type;
    }

    /**
     * Adds a resource to the industry's inventory.
     *
     * @param resource the resource to add
     * @return the new quantity of the resource in the inventory
     */
    @Override
    public int addResourceToInventory(Resource resource) {
        return this.inventory.addResource(resource);
    }

    /**
     * Removes a resource from the industry's inventory.
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
     * Gets the inventory of the industry.
     *
     * @return the inventory
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Checks if the inventory was updated.
     *
     * @return true if updated, false otherwise
     */
    @Override
    public boolean isUpdatedInventory() {
        return updatedinventory;
    }

    /**
     * Sets the updated inventory flag.
     *
     * @param updatedinventory true if updated, false otherwise
     */
    @Override
    public void setUpdatedInventory(boolean updatedinventory) {
        this.updatedinventory = updatedinventory;
    }
}