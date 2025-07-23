package pt.ipp.isep.dei.domain.Resource;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void testConstructorAndGetters() {
        ResourcesType type = new ResourcesType("Iron", 100, 10, 5);
        Resource resource = new Resource(type, 50);

        assertEquals(type, resource.getResourceType());
        assertEquals(50, resource.getQuantity());
    }

    @Test
    void testSetResourceType() {
        ResourcesType type1 = new ResourcesType("Iron", 100, 10, 5);
        ResourcesType type2 = new ResourcesType("Electricity", 200, 20, 10);
        Resource resource = new Resource(type1, 30);

        resource.setResourceType(type2);
        assertEquals(type2, resource.getResourceType());
    }

    @Test
    void testSetQuantity() {
        ResourcesType type = new ResourcesType("Iron", 100, 10, 5);
        Resource resource = new Resource(type, 30);

        resource.setQuantity(80);
        assertEquals(80, resource.getQuantity());
    }

    @Test
    void testToString() {
        ResourcesType type = new ResourcesType("Iron", 100, 10, 5);
        Resource resource = new Resource(type, 25);

        String result = resource.toString();
        assertTrue(result.contains("resourceType="));
        assertTrue(result.contains("quantity=25"));
        assertTrue(result.contains("Iron"));
    }
}
