package pt.ipp.isep.dei.domain._Others_;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Inventory;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    /**
     * Sets up a new Inventory instance before each test.
     */
    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    /**
     * TestResourcesType subclass of ResourcesType used for testing purposes.
     */
    static class TestResourcesType extends ResourcesType {
        /**
         * Constructs a TestResourcesType with the specified parameters.
         *
         * @param name                          the resource name
         * @param maxResources                  maximum number of resources that can be stored
         * @param intervalBetweenResourceGeneration interval between resource generation cycles
         * @param quantityProduced              quantity produced per generation cycle
         */
        TestResourcesType(String name, int maxResources, int intervalBetweenResourceGeneration, int quantityProduced) {
            super(name, maxResources, intervalBetweenResourceGeneration, quantityProduced);
        }
    }

    /**
     * Simple TestResource class extending Resource for testing.
     */
    static class TestResource extends Resource {
        private ResourcesType resourceType;
        private int quantity;

        /**
         * Constructs a TestResource with the given type and quantity.
         *
         * @param resourceType the type of the resource
         * @param quantity     the quantity of the resource
         */
        TestResource(ResourcesType resourceType, int quantity) {
            super(resourceType, quantity);
            this.resourceType = resourceType;
            this.quantity = quantity;
        }

        @Override
        public ResourcesType getResourceType() {
            return resourceType;
        }

        @Override
        public int getQuantity() {
            return quantity;
        }

        @Override
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    /**
     * Tests adding a new resource adds the correct quantity.
     */
    @Test
    void testAddResource_NewResource_AddsQuantityCorrectly() {
        TestResourcesType rt = new TestResourcesType("Fresh Water", 10, 5, 3);
        TestResource r = new TestResource(rt, 0);

        int added = inventory.addResource(r);
        assertEquals(3, added);
        assertEquals(0, inventory.getResourceQuantity("Fresh Water"));
        assertTrue(inventory.existsResourceInInventory(rt));
    }

    /**
     * Tests adding an existing resource increases quantity without exceeding max capacity.
     */
    @Test
    void testAddResource_ExistingResource_IncreasesQuantityWithoutExceedingMax() {
        TestResourcesType rt = new TestResourcesType("Fresh Water", 10, 5, 3);
        TestResource r1 = new TestResource(rt, 0);
        inventory.addResource(r1);

        int added = inventory.addResource(new TestResource(rt, 0));
        assertEquals(3, added);
        assertEquals(3, inventory.getResourceQuantity("Fresh Water"));

        added = inventory.addResource(new TestResource(rt, 0));
        assertEquals(3, added);
        assertEquals(6, inventory.getResourceQuantity("Fresh Water"));

        added = inventory.addResource(new TestResource(rt, 0));
        assertEquals(3, added);
        assertEquals(9, inventory.getResourceQuantity("Fresh Water"));

        added = inventory.addResource(new TestResource(rt, 0));
        assertEquals(1, added);
        assertEquals(10, inventory.getResourceQuantity("Fresh Water"));
    }

    /**
     * Tests removing a valid quantity of a resource updates quantity correctly.
     */
    @Test
    void testRemoveResource_ValidQuantity_RemovesAndUpdates() {
        TestResourcesType rt = new TestResourcesType("Organic Food", 5, 10, 2);
        TestResource r = new TestResource(rt, 5);
        inventory.addResource(r);

        int removed = inventory.removeResource(new TestResource(rt, 3));
        assertEquals(3, removed);
        assertEquals(2, inventory.getResourceQuantity("Organic Food"));
    }

    /**
     * Tests that attempting to remove more than available quantity returns -1.
     */
    @Test
    void testRemoveResource_QuantityExceedsAvailable_ReturnsMinusOne() {
        TestResourcesType rt = new TestResourcesType("Organic Food", 5, 10, 2);
        TestResource r = new TestResource(rt, 3);
        inventory.addResource(r);

        int removed = inventory.removeResource(new TestResource(rt, 4));
        assertEquals(-1, removed);
        assertEquals(3, inventory.getResourceQuantity("Organic Food"));
    }

    /**
     * Tests removing all quantity of a resource removes it from inventory.
     */
    @Test
    void testRemoveResource_RemovesResourceWhenQuantityBecomesZero() {
        TestResourcesType rt = new TestResourcesType("Diesel Fuel", 10, 7, 5);
        TestResource r = new TestResource(rt, 5);
        inventory.addResource(r);

        int removed = inventory.removeResource(new TestResource(rt, 5));
        assertEquals(5, removed);
        assertEquals(0, inventory.getResourceQuantity("Diesel Fuel"));
        assertFalse(inventory.existsResourceInInventory(rt));
    }

    /**
     * Tests that removing a resource not in inventory returns -2.
     */
    @Test
    void testRemoveResource_ResourceNotFound_ReturnsMinusTwo() {
        TestResourcesType rt = new TestResourcesType("24K Gold", 10, 8, 2);

        int removed = inventory.removeResource(new TestResource(rt, 1));
        assertEquals(-2, removed);
    }

    /**
     * Tests whether inventory correctly reports full status.
     */
    @Test
    void testInventoryIsFull() {
        assertFalse(inventory.inventoryIsFull());

        TestResourcesType rt = new TestResourcesType("Refined Iron", 5, 3, 2);
        TestResource r = new TestResource(rt, 3);
        inventory.addResource(r);

        assertFalse(inventory.inventoryIsFull());

        // Adiciona quantidade diretamente ao recurso
        Resource resource = inventory.getResourceByType(rt);
        resource.setQuantity(resource.getQuantity() + 2);
        assertTrue(inventory.inventoryIsFull());
    }

    /**
     * Tests retrieving resources by type and by name.
     */
    @Test
    void testGetResourceByTypeAndByName() {
        TestResourcesType rt = new TestResourcesType("Anthracite Coal", 10, 5, 3);
        TestResource r = new TestResource(rt, 5);
        inventory.addResource(r);

        Resource fetchedByType = inventory.getResourceByType(rt);
        assertNotNull(fetchedByType);
        assertEquals("Anthracite Coal", fetchedByType.getResourceType().getName());

        Resource fetchedByName = inventory.getResourceTypeByName("Anthracite Coal");
        assertNotNull(fetchedByName);
        assertEquals(rt.getName(), fetchedByName.getResourceType().getName());

        assertNull(inventory.getResourceTypeByName("NonExistent"));
    }

    /**
     * Tests existence checks for resources by type and name.
     */
    @Test
    void testExistsResourceInInventoryAndByName() {
        TestResourcesType rt = new TestResourcesType("Sterling Silver", 10, 4, 2);
        TestResource r = new TestResource(rt, 4);
        inventory.addResource(r);

        assertTrue(inventory.existsResourceInInventory(rt));
        assertTrue(inventory.existsResourceInInventoryByName("Sterling Silver"));
        assertFalse(inventory.existsResourceInInventoryByName("Diamond"));
    }

    /**
     * Tests adding and removing quantities from resources.
     */
    @Test
    void testAddAndRemoveQuantityFromResource() {
        TestResourcesType rt = new TestResourcesType("Copper Wire", 20, 5, 5);
        TestResource r = new TestResource(rt, 5);
        inventory.addResource(r);

        // Adiciona quantidade diretamente ao recurso
        Resource resource = inventory.getResourceByType(rt);
        resource.setQuantity(resource.getQuantity() + 3);
        assertEquals(8, inventory.getResourceQuantity("Copper Wire"));

        // Remove quantidade diretamente do recurso
        resource.setQuantity(resource.getQuantity() - 2);
        assertEquals(6, inventory.getResourceQuantity("Copper Wire"));
    }

    /**
     * Tests removing a resource by its type.
     */
    @Test
    void testRemoveResourceByType() {
        TestResourcesType rt = new TestResourcesType("Maple Syrup", 10, 6, 5);
        TestResource r = new TestResource(rt, 5);
        inventory.addResource(r);

        assertTrue(inventory.existsResourceInInventory(rt));
        inventory.remove(rt);
        assertFalse(inventory.existsResourceInInventory(rt));
    }

    /**
     * Tests addResourceWithoutLimit adds quantity regardless of max limit.
     */
    @Test
    void testAddResourceWithoutLimit() {
        TestResourcesType rt = new TestResourcesType("Silver", 5, 2, 2);
        TestResource r = new TestResource(rt, 2);
        inventory.addResource(r);
        int added = inventory.addResourceWithoutLimit(new TestResource(rt, 0));
        assertEquals(2, added);
        assertEquals(4, inventory.getResourceQuantity("Silver"));
        // Adiciona novamente, ultrapassando o limite
        added = inventory.addResourceWithoutLimit(new TestResource(rt, 0));
        assertEquals(2, added);
        assertEquals(6, inventory.getResourceQuantity("Silver"));
    }

    /**
     * Tests addAll adds all resources from a list, ignoring limits.
     */
    @Test
    void testAddAll() {
        TestResourcesType rt1 = new TestResourcesType("Gold", 10, 2, 2);
        TestResourcesType rt2 = new TestResourcesType("Bronze", 10, 2, 2);
        TestResource r1 = new TestResource(rt1, 3);
        TestResource r2 = new TestResource(rt2, 4);
        List<Resource> list = new java.util.ArrayList<>();
        list.add(r1);
        list.add(r2);
        inventory.addAll(list);
        assertEquals(3, inventory.getResourceQuantity("Gold"));
        assertEquals(4, inventory.getResourceQuantity("Bronze"));
    }

    /**
     * Tests getAllResources returns the correct list.
     */
    @Test
    void testGetAllResources() {
        TestResourcesType rt = new TestResourcesType("Platinum", 10, 2, 2);
        TestResource r = new TestResource(rt, 2);
        inventory.addResource(r);
        assertEquals(1, inventory.getAllResources().size());
        assertEquals("Platinum", inventory.getAllResources().get(0).getResourceType().getName());
    }

    /**
     * Tests getAllResourcesType returns all resource types.
     */
    @Test
    void testGetAllResourcesType() {
        TestResourcesType rt1 = new TestResourcesType("Nickel", 10, 2, 2);
        TestResourcesType rt2 = new TestResourcesType("Zinc", 10, 2, 2);
        inventory.addResource(new TestResource(rt1, 1));
        inventory.addResource(new TestResource(rt2, 1));
        List<ResourcesType> types = inventory.getAllResourcesType();
        assertEquals(2, types.size());
        assertTrue(types.stream().anyMatch(t -> t.getName().equals("Nickel")));
        assertTrue(types.stream().anyMatch(t -> t.getName().equals("Zinc")));
    }

    /**
     * Tests getResources returns the internal list.
     */
    @Test
    void testGetResources() {
        TestResourcesType rt = new TestResourcesType("Lead", 10, 2, 2);
        TestResource r = new TestResource(rt, 2);
        inventory.addResource(r);
        assertEquals(inventory.getAllResources(), inventory.getResources());
    }

    /**
     * Tests setResources sets the internal list.
     */
    @Test
    void testSetResources() {
        TestResourcesType rt = new TestResourcesType("Tin", 10, 2, 2);
        TestResource r = new TestResource(rt, 2);
        List<Resource> list = new java.util.ArrayList<>();
        list.add(r);
        inventory.setResources(list);
        assertEquals(1, inventory.getResources().size());
        assertEquals("Tin", inventory.getResources().get(0).getResourceType().getName());
    }

    /**
     * Tests getResourceQuantity returns 0 if resource not found.
     */
    @Test
    void testGetResourceQuantityNotFound() {
        assertEquals(0, inventory.getResourceQuantity("NonExisting"));
    }
}
