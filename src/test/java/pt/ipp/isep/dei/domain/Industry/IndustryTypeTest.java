package pt.ipp.isep.dei.domain.Industry;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link IndustryType} enum.
 * This class verifies the correct behavior of description retrieval,
 * type listing, and type lookup based on description.
 */
class IndustryTypeTest {

    /**
     * Tests the {@code getDescription()} method of each industry type.
     * Verifies that the returned descriptions match the expected values.
     */
    @Test
    void testGetDescription() {
        assertEquals("Primary Sector Industry", IndustryType.PRIMARY_SECTOR.getDescription());
        assertEquals("Transforming Industry", IndustryType.TRANSFORMING.getDescription());
        assertEquals("Mixed Industry", IndustryType.MIXED.getDescription());
    }

    /**
     * Tests the {@code getAllIndustryTypes()} static method.
     * Ensures that the returned list contains all defined industry types.
     */
    @Test
    void testGetAllIndustryTypes() {
        List<IndustryType> types = IndustryType.getAllIndustryTypes();
        assertTrue(types.contains(IndustryType.PRIMARY_SECTOR));
        assertTrue(types.contains(IndustryType.TRANSFORMING));
        assertTrue(types.contains(IndustryType.MIXED));
        assertEquals(3, types.size());
    }

    /**
     * Tests the {@code getIndustryTypeByDescription(String)} static method.
     * Validates correct enum type retrieval based on its description.
     * Also checks that null is returned when the description does not match any type.
     */
    @Test
    void testGetIndustryTypeByDescription() {
        assertEquals(IndustryType.PRIMARY_SECTOR, IndustryType.getIndustryTypeByDescription("Primary Sector Industry"));
        assertEquals(IndustryType.TRANSFORMING, IndustryType.getIndustryTypeByDescription("Transforming Industry"));
        assertEquals(IndustryType.MIXED, IndustryType.getIndustryTypeByDescription("Mixed Industry"));
        assertNull(IndustryType.getIndustryTypeByDescription("Nonexistent Industry"));
    }
}
