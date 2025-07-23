package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link HouseBlockRepository} class.
 * This class covers all scenarios for adding, removing, checking and retrieving house blocks.
 */
class HouseBlockRepositoryTest {

    private HouseBlockRepository repository;
    private HouseBlock houseBlock1;
    private HouseBlock houseBlock2;

    /**
     * Sets up a new repository and sample HouseBlocks before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new HouseBlockRepository();
        Position position1 = new Position(0, 0);
        Position position2 = new Position(1, 1);
        houseBlock1 = new HouseBlock(position1, "CityA");
        houseBlock2 = new HouseBlock(position2, "CityB");
    }

    /**
     * Tests adding a HouseBlock successfully.
     * Verifies that the block is added and exists in the repository.
     */
    @Test
    void testAddHouseBlockSuccess() {
        assertTrue(repository.addHouseBlock(houseBlock1), "Should add house block successfully");
        assertTrue(repository.houseBlockExists(houseBlock1), "Repository should contain the added house block");
    }

    /**
     * Tests that adding a null HouseBlock throws IllegalArgumentException.
     */
    @Test
    void testAddHouseBlockNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.addHouseBlock(null), "Adding null house block should throw IllegalArgumentException");
    }

    /**
     * Tests removing an existing HouseBlock.
     * Verifies the block is removed and no longer exists.
     */
    @Test
    void testRemoveHouseBlockSuccess() {
        repository.addHouseBlock(houseBlock1);
        assertTrue(repository.removeHouseBlock(houseBlock1), "Should remove house block successfully");
        assertFalse(repository.houseBlockExists(houseBlock1), "Repository should not contain the removed house block");
    }

    /**
     * Tests removing a HouseBlock that does not exist returns false.
     */
    @Test
    void testRemoveHouseBlockNotExists() {
        assertFalse(repository.removeHouseBlock(houseBlock1), "Should return false when removing non-existent house block");
    }

    /**
     * Tests existence check for HouseBlocks.
     * Verifies existence for added block and non-existence for a block not added.
     */
    @Test
    void testHouseBlockExists() {
        repository.addHouseBlock(houseBlock1);
        assertTrue(repository.houseBlockExists(houseBlock1), "Should return true for existing house block");
        assertFalse(repository.houseBlockExists(houseBlock2), "Should return false for non-existing house block");
    }

    /**
     * Tests retrieval of all HouseBlocks.
     * Verifies the returned list contains all added blocks.
     */
    @Test
    void testGetAllHouseBlocks() {
        repository.addHouseBlock(houseBlock1);
        repository.addHouseBlock(houseBlock2);
        List<HouseBlock> blocks = repository.getAllHouseBlocks();
        assertEquals(2, blocks.size(), "Repository should contain two house blocks");
        assertTrue(blocks.contains(houseBlock1), "Repository should contain houseBlock1");
        assertTrue(blocks.contains(houseBlock2), "Repository should contain houseBlock2");
    }

    /**
     * Tests setting the HouseBlocks list directly.
     * Verifies that the repository contains exactly the blocks in the new list.
     */
    @Test
    void testSetHouseBlocks() {
        List<HouseBlock> newList = new ArrayList<>();
        newList.add(houseBlock1);
        repository.setHouseBlocks(newList);
        assertEquals(1, repository.getHouseBlocks().size(), "Repository should contain one house block after setHouseBlocks");
        assertTrue(repository.getHouseBlocks().contains(houseBlock1), "Repository should contain the set house block");
    }
}
