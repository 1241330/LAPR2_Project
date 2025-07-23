package pt.ipp.isep.dei.domain.Resource;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HouseBlockResourceTest {

    @Test
    void testConstructorAndGetters() {
        HouseBlockResource resource = new HouseBlockResource("Iron", 100, 5, 20);

        assertEquals("Iron", resource.getName());
        assertEquals(100, resource.getMaxResources());
        assertEquals(5, resource.getIntervalBetweenResourceGeneration());
        assertEquals(20, resource.getQuantityProduced());
    }

    @Test
    void testSetName() {
        HouseBlockResource resource = new HouseBlockResource("Iron", 100, 5, 20);
        resource.setName("Electricity");
        assertEquals("Electricity", resource.getName());
    }

    @Test
    void testSetMaxResources() {
        HouseBlockResource resource = new HouseBlockResource("Iron", 100, 5, 20);
        resource.setMaxResources(200);
        assertEquals(200, resource.getMaxResources());
    }

    @Test
    void testSetIntervalBetweenResourceGeneration() {
        HouseBlockResource resource = new HouseBlockResource("Iron", 100, 5, 20);
        resource.setIntervalBetweenResourceGeneration(10);
        assertEquals(10, resource.getIntervalBetweenResourceGeneration());
    }

    @Test
    void testSetQuantityProduced() {
        HouseBlockResource resource = new HouseBlockResource("Iron", 100, 5, 20);
        resource.setQuantityProduced(50);
        assertEquals(50, resource.getQuantityProduced());
    }

    @Test
    void testToString() {
        HouseBlockResource resource = new HouseBlockResource("Iron", 100, 5, 20);
        String expected = "Iron (Max resources storage: 100, Interval between resource generation: 5, Quantity produced per generation: 20)";
        assertEquals(expected, resource.toString());
    }
}
