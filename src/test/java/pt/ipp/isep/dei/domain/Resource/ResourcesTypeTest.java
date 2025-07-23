package pt.ipp.isep.dei.domain.Resource;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourcesTypeTest {

    @Test
    void testConstructorAndGetters() {
        ResourcesType resource = new ResourcesType("Iron", 50, 10, 5);

        assertEquals("Iron", resource.getName());
        assertEquals(50, resource.getMaxResources());
        assertEquals(10, resource.getIntervalBetweenResourceGeneration());
        assertEquals(5, resource.getQuantityProduced());
    }

    @Test
    void testSetName() {
        ResourcesType resource = new ResourcesType("Iron", 50, 10, 5);
        resource.setName("Electricity");
        assertEquals("Electricity", resource.getName());
    }

    @Test
    void testSetMaxResources() {
        ResourcesType resource = new ResourcesType("Iron", 50, 10, 5);
        resource.setMaxResources(100);
        assertEquals(100, resource.getMaxResources());
    }

    @Test
    void testSetIntervalBetweenResourceGeneration() {
        ResourcesType resource = new ResourcesType("Iron", 50, 10, 5);
        resource.setIntervalBetweenResourceGeneration(20);
        assertEquals(20, resource.getIntervalBetweenResourceGeneration());
    }

    @Test
    void testSetQuantityProduced() {
        ResourcesType resource = new ResourcesType("Iron", 50, 10, 5);
        resource.setQuantityProduced(15);
        assertEquals(15, resource.getQuantityProduced());
    }

    @Test
    void testToString() {
        ResourcesType resource = new ResourcesType("Iron", 50, 10, 5);
        String expected = "ResourcesType{name='Iron', maxResources=50, intervalBetweenResourceGeneration=10, quantityProduced=5}";
        assertEquals(expected, resource.toString());
    }
}
