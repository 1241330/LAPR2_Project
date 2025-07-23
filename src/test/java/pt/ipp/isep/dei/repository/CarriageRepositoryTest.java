package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Train.Carriage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CarriageRepository} class.
 * This test class validates all repository functionality for storing,
 * retrieving, clearing, updating, and edge cases for {@link pt.ipp.isep.dei.domain.Train.Carriage} objects.
 */
class CarriageRepositoryTest {

    private CarriageRepository repository;
    private Carriage carriage;

    /**
     * Sets up a clean {@link CarriageRepository} and a test {@link Carriage} before each test case.
     */
    @BeforeEach
    void setUp() {
        repository = new CarriageRepository();
        repository.clear();
        carriage = new Carriage("Test Carriage", "img.png", 1900, 500, 100);
    }

    /**
     * Tests if a {@link Carriage} can be successfully saved and retrieved from the repository.
     */
    @Test
    void testSaveCarriage() {
        repository.save(carriage);
        List<Carriage> carriages = repository.getCarriageList();
        assertEquals(1, carriages.size(), "Repository should contain one carriage after save");
        assertEquals(carriage, carriages.get(0), "Saved carriage should match the retrieved one");
    }

    /**
     * Tests saving multiple carriages and retrieving them.
     */
    @Test
    void testSaveMultipleCarriages() {
        Carriage carriage2 = new Carriage("Test Carriage 2", "img2.png", 1910, 600, 120);
        repository.save(carriage);
        repository.save(carriage2);
        List<Carriage> carriages = repository.getCarriageList();
        assertEquals(2, carriages.size(), "Repository should contain two carriages");
        assertTrue(carriages.contains(carriage), "Repository should contain the first carriage");
        assertTrue(carriages.contains(carriage2), "Repository should contain the second carriage");
    }

    /**
     * Tests saving the same carriage twice (should allow duplicates if implementation allows).
     */
    @Test
    void testSaveDuplicateCarriage() {
        repository.save(carriage);
        repository.save(carriage);
        List<Carriage> carriages = repository.getCarriageList();
        assertEquals(2, carriages.size(), "Repository should allow duplicate carriages");
        assertEquals(carriage, carriages.get(0));
        assertEquals(carriage, carriages.get(1));
    }

    /**
     * Tests that the repository is empty after calling {@link CarriageRepository#clear()}.
     */
    @Test
    void testClear() {
        repository.save(carriage);
        repository.clear();
        List<Carriage> carriages = repository.getCarriageList();
        assertTrue(carriages.isEmpty(), "Repository should be empty after clear");
    }

    /**
     * Tests replacing the carriage list using {@link CarriageRepository#setCarriageList(List)}.
     */
    @Test
    void testSetCarriageList() {
        List<Carriage> newList = new ArrayList<>();
        Carriage carriage2 = new Carriage("Test Carriage 2", "img2.png", 1910, 600, 120);
        newList.add(carriage2);
        repository.setCarriageList(newList);
        List<Carriage> carriages = repository.getCarriageList();
        assertEquals(1, carriages.size(), "Repository should contain one carriage after setCarriageList");
        assertEquals(carriage2, carriages.get(0), "Carriage in repository should match the one set");
    }

    /**
     * Tests that setCarriageList replaces the static list and getCarriageList reflects the change.
     */
    @Test
    void testSetCarriageListReflectsInGetCarriageList() {
        List<Carriage> newList = new ArrayList<>();
        Carriage carriage2 = new Carriage("CarriageX", "img3.png", 2000, 700, 150);
        newList.add(carriage2);
        repository.setCarriageList(newList);
        assertEquals(1, repository.getCarriageList().size());
        assertEquals(carriage2, repository.getCarriageList().get(0));
    }

    /**
     * Tests getting the carriage list when it is empty.
     */
    @Test
    void testGetCarriageListWhenEmpty() {
        repository.clear();
        List<Carriage> carriages = repository.getCarriageList();
        assertNotNull(carriages, "Carriage list should not be null");
        assertTrue(carriages.isEmpty(), "Carriage list should be empty");
    }

    /**
     * Tests that modifying the returned list from getCarriageList does not affect the repository.
     */
    @Test
    void testGetCarriageListImmutability() {
        repository.save(carriage);
        List<Carriage> carriages = repository.getCarriageList();
        int originalSize = carriages.size();
        carriages.clear();
        assertEquals(originalSize, repository.getCarriageList().size(), "Modifying returned list should not affect repository");
    }

    /**
     * Tests saving a null carriage (should throw NullPointerException or not add).
     */
    @Test
    void testSaveNullCarriage() {
        assertThrows(NullPointerException.class, () -> repository.save(null), "Saving null carriage should throw NullPointerException");
    }

    /**
     * Tests setting the carriage list to null (should throw NullPointerException).
     */
    @Test
    void testSetCarriageListToNull() {
        assertThrows(NullPointerException.class, () -> repository.setCarriageList(null), "Setting carriage list to null should throw NullPointerException");
    }
}
