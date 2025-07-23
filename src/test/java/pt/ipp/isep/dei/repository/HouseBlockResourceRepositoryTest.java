package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.*;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link HouseBlockResourceRepository} class.
 */
class HouseBlockResourceRepositoryTest {

    private HouseBlockResourceRepository repository;
    private HouseBlockResource resource1;
    private HouseBlockResource resource2;

    /**
     * Sets up a new repository and sample HouseBlockResources before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new HouseBlockResourceRepository();
        resource1 = new HouseBlockResource("Resource1", 10, 5, 2);
        resource2 = new HouseBlockResource("Resource2", 20, 10, 4);
        HouseBlockResourceRepository.setHouseBlockResources(new ArrayList<>());
    }

    /**
     * Tests adding a HouseBlockResource successfully.
     * Verifies that the resource is added and exists in the repository.
     */
    @Test
    void testAddHouseBlockResource() {
        repository.addHouseBlockResource(resource1);
        assertTrue(repository.getAllHouseBlockResources().contains(resource1));
    }

    /**
     * Tests that adding a null HouseBlockResource throws IllegalArgumentException.
     */
    @Test
    void testAddHouseBlockResource_Null() {
        assertThrows(IllegalArgumentException.class, () -> repository.addHouseBlockResource(null));
    }

    /**
     * Tests adding a duplicate HouseBlockResource (should not add again).
     */
    @Test
    void testAddDuplicateHouseBlockResource() {
        repository.addHouseBlockResource(resource1);
        repository.addHouseBlockResource(resource1);
        List<HouseBlockResource> all = repository.getAllHouseBlockResources();
        assertEquals(1, all.size(), "Repository should not add duplicate resources");
    }

    /**
     * Tests retrieval of all HouseBlockResources.
     * Verifies the returned list contains all added resources.
     */
    @Test
    void testGetAllHouseBlockResources() {
        repository.addHouseBlockResource(resource1);
        repository.addHouseBlockResource(resource2);
        List<HouseBlockResource> all = repository.getAllHouseBlockResources();
        assertEquals(2, all.size());
        assertTrue(all.contains(resource1));
        assertTrue(all.contains(resource2));
    }

    /**
     * Tests getting a HouseBlockResource by name when it exists.
     */
    @Test
    void testGetHouseBlockResourceByName_Found() {
        repository.addHouseBlockResource(resource1);
        assertEquals(resource1, repository.getHouseBlockResourceByName("Resource1"));
    }

    /**
     * Tests getting a HouseBlockResource by name when it does not exist.
     */
    @Test
    void testGetHouseBlockResourceByName_NotFound() {
        assertNull(repository.getHouseBlockResourceByName("NonExistent"));
    }

    /**
     * Tests getting a copy of the HouseBlockResources list.
     * Verifies the copy is not the same instance and has the same data.
     */
    @Test
    void testGetCopyOfHouseBlockResources() {
        repository.addHouseBlockResource(resource1);
        List<HouseBlockResource> copy = repository.getCopyOfHouseBlockResources();
        assertEquals(1, copy.size());
        assertNotSame(resource1, copy.get(0));
        assertEquals(resource1.getName(), copy.get(0).getName());
    }

    /**
     * Tests getting and setting the static HouseBlockResources list.
     */
    @Test
    void testGetAndSetHouseBlockResources_Static() {
        List<HouseBlockResource> list = new ArrayList<>();
        list.add(resource1);
        HouseBlockResourceRepository.setHouseBlockResources(list);
        assertEquals(list, HouseBlockResourceRepository.getHouseBlockResources());
    }

    /**
     * Tests setting the static HouseBlockResources list to null (should throw IllegalArgumentException or handle gracefully).
     */
    @Test
    void testSetHouseBlockResourcesToNull() {
        assertThrows(IllegalArgumentException.class, () -> HouseBlockResourceRepository.setHouseBlockResources(null), "Setting static list to null should throw IllegalArgumentException");
    }
}

