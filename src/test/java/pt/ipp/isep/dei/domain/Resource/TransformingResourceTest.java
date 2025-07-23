package pt.ipp.isep.dei.domain.Resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TransformingResource} class.
 */
class TransformingResourceTest {

    private ResourcesType needed1;
    private ResourcesType needed2;
    private List<ResourcesType> neededList;
    private TransformingResource transformingResource;

    /**
     * Mock implementation of {@link ResourcesType} for testing purposes.
     */
    static class MockResource extends ResourcesType {
        /**
         * Constructs a MockResource with the given name.
         *
         * @param name the name of the resource
         */
        public MockResource(String name) {
            super(name, 10, 5, 2);
        }
    }

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        needed1 = new MockResource("Water");
        needed2 = new MockResource("Electricity");
        neededList = Arrays.asList(needed1, needed2);
        transformingResource = new TransformingResource("Bread", 100, 10, 5, neededList);
    }

    /**
     * Tests the constructor and getter methods of {@link TransformingResource}.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("Bread", transformingResource.getName());
        assertEquals(100, transformingResource.getMaxResources());
        assertEquals(10, transformingResource.getIntervalBetweenResourceGeneration());
        assertEquals(5, transformingResource.getQuantityProduced());
        assertEquals(neededList, transformingResource.getNeededResources());
    }

    /**
     * Tests if getNeededResources throws an exception when the list is null or empty.
     */
    @Test
    void testGetNeededResourcesThrowsIfNullOrEmpty() {
        TransformingResource trNull = new TransformingResource("Test", 1, 1, 1, null);
        assertThrows(IllegalArgumentException.class, trNull::getNeededResources);

        TransformingResource trEmpty = new TransformingResource("Test", 1, 1, 1, Collections.emptyList());
        assertThrows(IllegalArgumentException.class, trEmpty::getNeededResources);
    }

    /**
     * Tests the setter method for needed resources.
     */
    @Test
    void testSetNeededResources() {
        List<ResourcesType> newList = Collections.singletonList(needed1);
        transformingResource.setNeededResources(newList);
        assertEquals(newList, transformingResource.getNeededResources());
    }

    /**
     * Tests the getTransformation method.
     */
    @Test
    void testGetTransformation() {
        String expected = "Bread: (Water + Electricity)";
        assertEquals(expected, transformingResource.getTransformation());
    }

    /**
     * Tests the toString method of {@link TransformingResource}.
     */
    @Test
    void testToString() {
        String result = transformingResource.toString();
        assertTrue(result.contains("Bread"));
        assertTrue(result.contains("Max resources storage: 100"));
        assertTrue(result.contains("Interval between resource generation: 10"));
        assertTrue(result.contains("Quantity produced per generation: 5"));
    }
}