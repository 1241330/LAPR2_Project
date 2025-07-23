package pt.ipp.isep.dei.domain.Train;

import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain._Others_.Inventory;
import pt.ipp.isep.dei.domain.Resource.Resource;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a train composed of a locomotive and carriages, with an inventory for resources.
 */
public class Train implements Serializable {

    private Locomotive locomotive;
    private List<Carriage> carriages;
    private String name;
    private Inventory inventory;
    private int maxInventorySpace;
    private boolean activeFlag;
    private TimeDate acquisitionDate;

    /**
     * Constructs a Train with the specified locomotive, carriages, and acquisition date.
     *
     * @param locomotive      the locomotive of the train
     * @param carriages       the list of carriages attached to the train
     * @param acquisitionDate the date the train was acquired
     */
    public Train(Locomotive locomotive, List<Carriage> carriages, TimeDate acquisitionDate) {
        this.locomotive = locomotive;
        this.carriages = carriages;
        this.name = locomotive.getName() + " Train";
        this.maxInventorySpace = findMaxInventorySpace();
        this.inventory = new Inventory();
        this.activeFlag = false;
        this.acquisitionDate = acquisitionDate;
    }

    /**
     * Default constructor for Train.
     */
    public Train() {
    }

    /**
     * Gets the locomotive of the train.
     *
     * @return the locomotive
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Sets the locomotive of the train.
     *
     * @param locomotive the locomotive to set
     * @throws IllegalArgumentException if the locomotive is null
     */
    public void setLocomotive(Locomotive locomotive) {
        if (locomotive != null) {
            this.locomotive = locomotive;
        } else {
            throw new IllegalArgumentException("You must have a locomotive.");
        }
    }

    /**
     * Gets the list of carriages attached to the train.
     *
     * @return the list of carriages
     */
    public List<Carriage> getCarriages() {
        return carriages;
    }

    /**
     * Sets the list of carriages for the train.
     *
     * @param carriages the list of carriages to set
     * @throws IllegalArgumentException if the list is empty
     */
    public void setCarriages(List<Carriage> carriages) {
        if (carriages.size() == 0) {
            throw new IllegalArgumentException("You must have at least one carriage.");
        }
        this.carriages = carriages;
    }

    /**
     * Gets the name of the train.
     *
     * @return the train name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the inventory of the train.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the maximum inventory space available in the train.
     *
     * @return the maximum inventory space
     */
    public int getMaxInventorySpace() {
        return maxInventorySpace;
    }

    /**
     * Gets the active flag of the train.
     *
     * @return true if the train is active, false otherwise
     */
    public boolean getActiveFlag() {
        return activeFlag;
    }

    /**
     * Sets the active flag of the train.
     *
     * @param activeFlag the new active flag value
     */
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * Gets the acquisition date of the train.
     *
     * @return the acquisition date
     */
    public TimeDate getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Sets the acquisition date of the train.
     *
     * @param acquisitionDate the new acquisition date
     */
    public void setAcquisitionDate(TimeDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    /**
     * Calculates the total inventory space occupied by resources in the train.
     *
     * @return the occupied inventory space
     */
    public int getInventorySpaceOcupied() {
        int occupiedSpace = 0;
        if (inventory == null || inventory.getAllResources().isEmpty()) {
            return 0;
        }
        for (Resource resource : inventory.getAllResources()) {
            occupiedSpace += resource.getQuantity();
        }
        return occupiedSpace;
    }

    /**
     * Calculates the maximum inventory space based on the carriages' capacities.
     *
     * @return the maximum inventory space
     */
    private int findMaxInventorySpace() {
        int maxSpace = 0;
        for (Carriage carriage : carriages) {
            maxSpace += carriage.getMaxResourceCapacity();
        }
        return maxSpace;
    }

    /**
     * Returns a string representation of the train, including its name, locomotive, and carriages.
     *
     * @return the string representation of the train
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Train: ").append(name).append("\n");
        sb.append("Locomotive: ").append(locomotive.getName()).append("\n");
        sb.append("Carriages: ");
        for (Carriage carriage : carriages) {
            sb.append(carriage.toString()).append(" ");
        }
        return sb.toString();
    }
}