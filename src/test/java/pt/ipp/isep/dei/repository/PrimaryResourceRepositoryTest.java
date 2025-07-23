package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link PrimaryResourceRepository}.
 * Verifies the behavior of methods related to adding, retrieving, copying, and setting primary resources in the repository.
 */
class PrimaryResourceRepositoryTest {

    private PrimaryResourceRepository repository;

    /**
     * Sets up the test environment by resetting the resource list
     * before each test method.
     */
    @BeforeEach
    void setUp() {
        // Reset the resource list before each test
        PrimaryResourceRepository.setPrimaryResources(new ArrayList<>());
        repository = new PrimaryResourceRepository();
    }

    /**
     * Tests that adding a null resource throws an {@link IllegalArgumentException}.
     */
    @Test
    void testAddPrimaryResource_NullResource_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.addPrimaryResource(null);
        });
    }

    /**
     * Tests that a valid resource is correctly added to the repository.
     */
    @Test
    void testAddPrimaryResource_ValidResource_AddsResource() {
        PrimaryResource resource = new PrimaryResource("Resource1", 10, 5, 2);
        repository.addPrimaryResource(resource);
        List<PrimaryResource> resources = repository.getAllPrimaryResources();
        assertEquals(1, resources.size());
        assertEquals(resource, resources.get(0));
    }

    /**
     * Tests that adding a duplicate resource adds it again (since no uniqueness enforced).
     */
    @Test
    void testAddPrimaryResource_DuplicateResource_AddsAgain() {
        PrimaryResource resource = new PrimaryResource("Resource1", 10, 5, 2);
        repository.addPrimaryResource(resource);
        repository.addPrimaryResource(resource);
        List<PrimaryResource> resources = repository.getAllPrimaryResources();
        assertEquals(2, resources.size());
        assertSame(resource, resources.get(0));
        assertSame(resource, resources.get(1));
    }

    /**
     * Tests that getting all resources from an empty repository
     * returns an empty list.
     */
    @Test
    void testGetAllPrimaryResources_EmptyRepository_ReturnsEmptyList() {
        List<PrimaryResource> resources = repository.getAllPrimaryResources();
        assertTrue(resources.isEmpty());
    }

    /**
     * Tests that getting all resources from a non-empty repository
     * returns all added resources.
     */
    @Test
    void testGetAllPrimaryResources_NonEmptyRepository_ReturnsAllResources() {
        PrimaryResource resource1 = new PrimaryResource("Resource1", 10, 5, 2);
        PrimaryResource resource2 = new PrimaryResource("Resource2", 20, 10, 4);
        repository.addPrimaryResource(resource1);
        repository.addPrimaryResource(resource2);
        List<PrimaryResource> resources = repository.getAllPrimaryResources();
        assertEquals(2, resources.size());
        assertTrue(resources.contains(resource1));
        assertTrue(resources.contains(resource2));
    }

    /**
     * Tests that searching a resource by name returns the correct resource
     * when it exists in the repository.
     */
    @Test
    void testGetPrimaryResourceByName_ResourceExists_ReturnsResource() {
        PrimaryResource resource = new PrimaryResource("Resource1", 10, 5, 2);
        repository.addPrimaryResource(resource);
        PrimaryResource foundResource = repository.getPrimaryResourceByName("Resource1");
        assertEquals(resource, foundResource);
    }

    /**
     * Tests that searching for a non-existent resource by name returns {@code null}.
     */
    @Test
    void testGetPrimaryResourceByName_ResourceDoesNotExist_ReturnsNull() {
        PrimaryResource resource = new PrimaryResource("Resource1", 10, 5, 2);
        repository.addPrimaryResource(resource);
        PrimaryResource foundResource = repository.getPrimaryResourceByName("NonExistentResource");
        assertNull(foundResource);
    }

    /**
     * Tests that getCopyPrimaryResources returns a list of new PrimaryResource instances with the same data.
     */
    @Test
    void testGetCopyPrimaryResources_ReturnsNewInstances() {
        PrimaryResource resource = new PrimaryResource("Resource1", 10, 5, 2);
        repository.addPrimaryResource(resource);
        List<PrimaryResource> copy = repository.getCopyPrimaryResources();
        assertEquals(1, copy.size());
        assertNotSame(resource, copy.get(0));
        assertEquals(resource.getName(), copy.get(0).getName());
        assertEquals(resource.getMaxResources(), copy.get(0).getMaxResources());
        assertEquals(resource.getIntervalBetweenResourceGeneration(), copy.get(0).getIntervalBetweenResourceGeneration());
        assertEquals(resource.getQuantityProduced(), copy.get(0).getQuantityProduced());
    }

    /**
     * Tests the static method that sets the primary resources list
     * in the repository, ensuring resources are correctly assigned.
     */
    @Test
    void testSetPrimaryResources_SetsResources() {
        PrimaryResource resource1 = new PrimaryResource("Resource1", 10, 5, 2);
        PrimaryResource resource2 = new PrimaryResource("Resource2", 20, 10, 4);
        List<PrimaryResource> newResources = List.of(resource1, resource2);
        PrimaryResourceRepository.setPrimaryResources(newResources);
        List<PrimaryResource> resources = PrimaryResourceRepository.getPrimaryResources();
        assertEquals(2, resources.size());
        assertTrue(resources.contains(resource1));
        assertTrue(resources.contains(resource2));
    }

    /**
     * Tests that setPrimaryResources with null throws an IllegalArgumentException.
     */
    @Test
    void testSetPrimaryResources_Null_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> PrimaryResourceRepository.setPrimaryResources(null));
    }
}
