package pt.ipp.isep.dei.domain.Station;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTypeTest {

    @Test
    void testGetBuildingID() {
        assertEquals(1, BuildingType.TELEGRAPH.getBuildingID());
        assertEquals(4, BuildingType.LARGE_COFFEE.getBuildingID());
    }

    @Test
    void testGetConstructionCost() {
        assertEquals(30, BuildingType.TELEGRAPH.getConstructionCost());
        assertEquals(120, BuildingType.LARGE_HOTEL.getConstructionCost());
    }

    @Test
    void testGetBuildingReplacesByID() {
        assertEquals(-1, BuildingType.TELEGRAPH.getBuildingReplacesByID());
        assertEquals(3, BuildingType.LARGE_COFFEE.getBuildingReplacesByID());
        assertEquals(7, BuildingType.LARGE_HOTEL.getBuildingReplacesByID());
    }

    @Test
    void testGetDeleteFromAvailableID() {
        assertEquals(-1, BuildingType.TELEGRAPH.getDeleteFromAvailableID());
        assertEquals(1, BuildingType.TELEPHONE.getDeleteFromAvailableID());
    }

    @Test
    void testGetOperationYearInDays() {
        assertEquals(1844 * 360, BuildingType.TELEGRAPH.getOperationYearInDays());
        assertEquals(1920 * 360, BuildingType.LARGE_COFFEE.getOperationYearInDays());
    }

    @Test
    void testGetName() {
        assertEquals("Telegraph", BuildingType.TELEGRAPH.getName());
        assertEquals("Large coffee", BuildingType.LARGE_COFFEE.getName());
        assertEquals("Liquid storage", BuildingType.LIQUID_STORAGE.getName());
    }

    @Test
    void testAllEnumValues() {
        for (BuildingType type : BuildingType.values()) {
            assertNotNull(type.getName());
            assertTrue(type.getBuildingID() > 0);
            assertTrue(type.getConstructionCost() > 0);
            assertTrue(type.getOperationYearInDays() > 0);
        }
    }
}
