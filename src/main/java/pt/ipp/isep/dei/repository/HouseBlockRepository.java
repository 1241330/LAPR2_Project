package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.City.HouseBlock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing HouseBlock entities.
 * Provides methods to add, remove, retrieve, and check house blocks.
 */
public class HouseBlockRepository implements Serializable {

    /**
     * List that stores all house blocks.
     */
    private List<HouseBlock> houseBlocks = new ArrayList<>();

    /**
     * Constructs a HouseBlockRepository and initializes the house block list.
     */
    public HouseBlockRepository() {
    }

    /**
     * Adds a house block to the repository.
     *
     * @param houseBlock the house block to be added
     * @return true if the house block was added successfully
     * @throws IllegalArgumentException if the house block is null
     */
    public boolean addHouseBlock(HouseBlock houseBlock) {
        if (houseBlock == null) {
            throw new IllegalArgumentException("HouseBlock cannot be null");
        }
        houseBlocks.add(houseBlock);
        return true;
    }

    /**
     * Removes a house block from the repository.
     *
     * @param houseBlock the house block to be removed
     * @return true if the house block was removed successfully, false if it does not exist
     */
    public boolean removeHouseBlock(HouseBlock houseBlock) {
        if (!houseBlockExists(houseBlock)) {
            return false;
        }
        houseBlocks.remove(houseBlock);
        return true;
    }

    /**
     * Checks if a house block exists in the repository.
     *
     * @param actualHouseBlock the house block to check
     * @return true if the house block exists, false otherwise
     */
    public boolean houseBlockExists(HouseBlock actualHouseBlock) {
        for (HouseBlock houseBlock : houseBlocks) {
            if (houseBlock == actualHouseBlock) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of all house blocks.
     *
     * @return the list of house blocks
     */
    public List<HouseBlock> getAllHouseBlocks() {
        return houseBlocks;
    }

    /**
     * Gets the list of house blocks.
     *
     * @return the list of house blocks
     */
    public List<HouseBlock> getHouseBlocks() {
        return houseBlocks;
    }

    /**
     * Sets the list of house blocks.
     *
     * @param houseBlocks the list of house blocks to set
     */
    public void setHouseBlocks(List<HouseBlock> houseBlocks) {
        this.houseBlocks = houseBlocks;
    }
}