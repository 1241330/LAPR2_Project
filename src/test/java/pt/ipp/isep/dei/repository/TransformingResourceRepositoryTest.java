package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TransformingResourceRepository} class.
 * This class covers all scenarios for adding, retrieving, copying and setting transforming resources.
 */
class TransformingResourceRepositoryTest {

    private TransformingResourceRepository repository;

    /**
     * Sets up a new repository and resets the static list before each test.
     */
    @BeforeEach
    void setUp() {
        TransformingResourceRepository.setTransformingResources(new ArrayList<>());
        repository = new TransformingResourceRepository();
    }

    /**
     * Tests that adding a null transforming resource throws IllegalArgumentException.
     */
    @Test
    void testAddTransformingType_NullResource_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.addTransformingType(null),
                "Adding null transforming resource should throw IllegalArgumentException");
    }

    /**
     * Tests that a valid transforming resource is added successfully.
     */
    @Test
    void testAddTransformingType_ValidResource_AddsResource() {
        List<ResourcesType> neededResources = new ArrayList<>();
        TransformingResource resource = new TransformingResource("TransformingResource1", 10, 5, 2, neededResources);
        repository.addTransformingType(resource);
        List<TransformingResource> resources = repository.getAllTransformingResources();
        assertEquals(1, resources.size());
        assertEquals(resource, resources.get(0));
    }

    /**
     * Tests that adding a duplicate transforming resource adds it again (no uniqueness enforced).
     */
    @Test
    void testAddTransformingType_DuplicateResource_AddsAgain() {
        List<ResourcesType> neededResources = new ArrayList<>();
        TransformingResource resource = new TransformingResource("TransformingResource1", 10, 5, 2, neededResources);
        repository.addTransformingType(resource);
        repository.addTransformingType(resource);
        List<TransformingResource> resources = repository.getAllTransformingResources();
        assertEquals(2, resources.size());
        assertSame(resource, resources.get(0));
        assertSame(resource, resources.get(1));
    }

    /**
     * Tests that getting all transforming resources from an empty repository returns an empty list.
     */
    @Test
    void testGetAllTransformingResources_EmptyRepository_ReturnsEmptyList() {
        List<TransformingResource> resources = repository.getAllTransformingResources();
        assertTrue(resources.isEmpty());
    }

    /**
     * Tests that getting all transforming resources from a non-empty repository returns all added resources.
     */
    @Test
    void testGetAllTransformingResources_NonEmptyRepository_ReturnsAllResources() {
        List<ResourcesType> neededResources1 = new ArrayList<>();
        List<ResourcesType> neededResources2 = new ArrayList<>();
        TransformingResource resource1 = new TransformingResource("TransformingResource1", 10, 5, 2, neededResources1);
        TransformingResource resource2 = new TransformingResource("TransformingResource2", 20, 10, 4, neededResources2);
        repository.addTransformingType(resource1);
        repository.addTransformingType(resource2);
        List<TransformingResource> resources = repository.getAllTransformingResources();
        assertEquals(2, resources.size());
        assertTrue(resources.contains(resource1));
        assertTrue(resources.contains(resource2));
    }

    /**
     * Tests that searching for a transforming resource by name returns the correct resource when it exists.
     */
    @Test
    void testGetTransformingTypeByName_ResourceExists_ReturnsResource() {
        List<ResourcesType> neededResources = new ArrayList<>();
        TransformingResource resource = new TransformingResource("TransformingResource1", 10, 5, 2, neededResources);
        repository.addTransformingType(resource);
        TransformingResource foundResource = repository.getTransformingTypeByName("TransformingResource1");
        assertEquals(resource, foundResource);
    }

    /**
     * Tests that searching for a non-existent transforming resource by name returns null.
     */
    @Test
    void testGetTransformingTypeByName_ResourceDoesNotExist_ReturnsNull() {
        List<ResourcesType> neededResources = new ArrayList<>();
        TransformingResource resource = new TransformingResource("TransformingResource1", 10, 5, 2, neededResources);
        repository.addTransformingType(resource);
        TransformingResource foundResource = repository.getTransformingTypeByName("NonExistentResource");
        assertNull(foundResource);
    }

    /**
     * Tests that getCopyTransformingResources returns a list of new TransformingResource instances with the same data.
     */
    @Test
    void testGetCopyTransformingResources_ReturnsNewInstances() {
        List<ResourcesType> neededResources = new ArrayList<>();
        TransformingResource resource = new TransformingResource("TransformingResource1", 10, 5, 2, neededResources);
        repository.addTransformingType(resource);
        List<TransformingResource> copy = repository.getCopyTransformingResources();
        assertEquals(1, copy.size());
        assertNotSame(resource, copy.get(0));
        assertEquals(resource.getName(), copy.get(0).getName());
        assertEquals(resource.getMaxResources(), copy.get(0).getMaxResources());
        assertEquals(resource.getIntervalBetweenResourceGeneration(), copy.get(0).getIntervalBetweenResourceGeneration());
        assertEquals(resource.getQuantityProduced(), copy.get(0).getQuantityProduced());
        assertEquals(resource.getNeededResources(), copy.get(0).getNeededResources());
    }

    /**
     * Tests the static method that sets the transforming resources list in the repository.
     */
    @Test
    void testSetTransformingResources_SetsResources() {
        List<ResourcesType> neededResources1 = new ArrayList<>();
        List<ResourcesType> neededResources2 = new ArrayList<>();
        TransformingResource resource1 = new TransformingResource("TransformingResource1", 10, 5, 2, neededResources1);
        TransformingResource resource2 = new TransformingResource("TransformingResource2", 20, 10, 4, neededResources2);
        List<TransformingResource> newResources = List.of(resource1, resource2);
        TransformingResourceRepository.setTransformingResources(newResources);
        List<TransformingResource> resources = TransformingResourceRepository.getTransformingResources();
        assertEquals(2, resources.size());
        assertTrue(resources.contains(resource1));
        assertTrue(resources.contains(resource2));
    }

    /**
     * Tests that setTransformingResources with null sets the static list to null (if allowed by implementation).
     */
    @Test
    void testSetTransformingResources_Null() {
        TransformingResourceRepository.setTransformingResources(null);
        assertNull(TransformingResourceRepository.getTransformingResources());
    }
}
