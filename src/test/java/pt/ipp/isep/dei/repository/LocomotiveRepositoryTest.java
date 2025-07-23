package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Locomotive;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link LocomotiveRepository} class.
 * <p>
 * This class covers all scenarios for storing, retrieving, clearing,
 * and updating the static list of {@link Locomotive} instances.
 */
class LocomotiveRepositoryTest {

    private LocomotiveRepository repository;
    private Locomotive locomotive;

    /**
     * Sets up a clean repository and a sample {@link Locomotive} before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new LocomotiveRepository();
        repository.clear();
        locomotive = new Locomotive("Teste Locomotiva", "img.png", 100.0, 20.0, 120.0, 1900, 500, FuelType.STEAM, 5, 100);
    }

    /**
     * Ensures that a locomotive can be saved and retrieved correctly.
     */
    @Test
    void testSaveLocomotive() {
        repository.save(locomotive);
        List<Locomotive> locomotives = repository.getAllLocomotives();

        assertEquals(1, locomotives.size());
        assertEquals(locomotive, locomotives.get(0));
    }

    /**
     * Ensures that saving a null locomotive throws an IllegalArgumentException.
     */
    @Test
    void testSaveNullLocomotive() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null), "Saving null locomotive should throw IllegalArgumentException");
    }

    /**
     * Ensures that saving the same locomotive twice stores both instances (if allowed by implementation).
     */
    @Test
    void testSaveDuplicateLocomotive() {
        repository.save(locomotive);
        repository.save(locomotive);
        List<Locomotive> locomotives = repository.getAllLocomotives();
        assertEquals(2, locomotives.size());
        assertEquals(locomotive, locomotives.get(0));
        assertEquals(locomotive, locomotives.get(1));
    }

    /**
     * Ensures that multiple locomotives are stored and retrieved properly.
     */
    @Test
    void testGetAllLocomotives() {
        repository.save(locomotive);
        Locomotive locomotive2 = new Locomotive("Teste Locomotiva 2", "img2.png", 150.0, 25.0, 140.0, 1910, 600, FuelType.DIESEL, 6, 100);
        repository.save(locomotive2);

        List<Locomotive> locomotives = repository.getAllLocomotives();

        assertEquals(2, locomotives.size());
        assertTrue(locomotives.contains(locomotive));
        assertTrue(locomotives.contains(locomotive2));
    }

    /**
     * Ensures that the returned list from getAllLocomotives is a copy and not modifiable.
     */
    @Test
    void testGetAllLocomotivesImmutability() {
        repository.save(locomotive);
        List<Locomotive> locomotives = repository.getAllLocomotives();
        int originalSize = locomotives.size();
        locomotives.clear();
        assertEquals(originalSize, repository.getAllLocomotives().size(), "Modifying returned list should not affect repository");
    }

    /**
     * Ensures that setLocomotiveList replaces the static list and getLocomotiveList reflects the change.
     */
    @Test
    void testSetLocomotiveListReflectsInGetLocomotiveList() {
        List<Locomotive> newList = new ArrayList<>();
        Locomotive loco = new Locomotive("Locomotiva", "img3.png", 1, 1, 1, 1, 1, FuelType.DIESEL, 1, 100);
        newList.add(loco);
        LocomotiveRepository.setLocomotiveList(newList);
        assertEquals(1, LocomotiveRepository.getLocomotiveList().size());
        assertEquals(loco, LocomotiveRepository.getLocomotiveList().get(0));
    }

    /**
     * Ensures that clearing the repository does not throw and results in an empty static list.
     */
    @Test
    void testClearStaticList() {
        LocomotiveRepository.setLocomotiveList(new ArrayList<>());
        repository.clear();
        assertTrue(LocomotiveRepository.getLocomotiveList().isEmpty());
    }

    /**
     * Ensures that setLocomotiveList(null) throws an exception.
     */
    @Test
    void testSetLocomotiveListNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> LocomotiveRepository.setLocomotiveList(null));
    }

    /**
     * Ensures that getLocomotiveList returns the same list as set by setLocomotiveList.
     */
    @Test
    void testGetLocomotiveList() {
        List<Locomotive> list = new ArrayList<>();
        Locomotive loco = new Locomotive("Locomotiva2", "img4.png", 2, 2, 2, 2, 2, FuelType.ELECTRICITY, 2, 100);
        list.add(loco);
        LocomotiveRepository.setLocomotiveList(list);
        assertEquals(list, LocomotiveRepository.getLocomotiveList());
    }
}
