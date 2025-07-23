package pt.ipp.isep.dei.domain.Industry;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain._Others_.Inventory;

import static org.junit.jupiter.api.Assertions.*;

class IndustryTest {


    @Test
    void testConstructorAndGetters() {
        Position pos = new Position(2, 3);
        IndustryType type = IndustryType.MIXED;
        Industry industry = new Industry("Industry A", type, pos);
        assertEquals("Industry A", industry.getName());
        assertEquals(type, industry.getIndustryType());
        assertEquals(pos, industry.getPosition());
        assertEquals("Industry", industry.getType());
        assertNotNull(industry.toString());
    }


    @Test
    void testStationAssignment() {
        Industry industry = new Industry("Industry B", IndustryType.MIXED, new Position(1, 1));
        assertNull(industry.getStationIdentifier());
        Station station = new Station("Station X");
        industry.setAssignedStation(station);
        assertEquals(station, industry.getStationIdentifier());
    }


    @Test
    void testIdIsUnique() {
        Industry industry1 = new Industry("Industry 1", IndustryType.MIXED, new Position(0, 0));
        Industry industry2 = new Industry("Industry 2", IndustryType.MIXED, new Position(1, 1));
        assertNotEquals(industry1.getId(), industry2.getId());
    }


    @Test
    void testToString() {
        Industry industry = new Industry("Industry G", IndustryType.MIXED, new Position(9, 9));
        String str = industry.toString();
        assertTrue(str.contains("Industry G"));
        assertTrue(str.contains("MIXED"));
        assertTrue(str.contains("9,9") || str.contains("9, 9"));
    }


    @Test
    void testSetAssignedStationNull() {
        Industry industry = new Industry("Industry H", IndustryType.MIXED, new Position(1, 1));
        industry.setAssignedStation(null);
        assertNull(industry.getStationIdentifier());
    }


    @Test
    void testInventoryMethods() {
        Industry industry = new Industry("Industry Inv", IndustryType.MIXED, new Position(1, 1));
        ResourcesType waterType = new ResourcesType("WATER", 100, 1, 10);
        Resource resource = new Resource(waterType, 10);
        assertEquals(0, industry.getResourceQuantity(waterType));
        assertFalse(industry.existsResourceInInventory(waterType));
        int qtd = industry.addResourceToInventory(resource);
        assertEquals(10, qtd);
        assertEquals(10, industry.getResourceQuantity(waterType));
        assertTrue(industry.existsResourceInInventory(waterType));
        int qtdRem = industry.removeResourceFromInventory(resource);
        assertEquals(0, qtdRem);
        assertEquals(0, industry.getResourceQuantity(waterType));
        assertFalse(industry.existsResourceInInventory(waterType));
        Inventory inv = industry.getInventory();
        assertNotNull(inv);
        assertFalse(industry.isUpdatedInventory());
        industry.setUpdatedInventory(true);
        assertTrue(industry.isUpdatedInventory());
    }

    @Test
    void testConstructorWithNullPosition() {
        Industry industry = new Industry("Industry Null", IndustryType.MIXED, null);
        assertNull(industry.getPosition());
        assertEquals("Industry Null", industry.getName());
    }

    @Test
    void testToStringWithNullPosition() {
        Industry industry = new Industry("Industry Null", IndustryType.MIXED, null);
        String str = industry.toString();
        assertNotNull(str);
        assertTrue(str.contains("Industry Null"));
    }
}
