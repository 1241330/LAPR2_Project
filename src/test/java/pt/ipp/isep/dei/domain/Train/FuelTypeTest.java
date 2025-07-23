package pt.ipp.isep.dei.domain.Train;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link FuelType} enum.
 */
class FuelTypeTest {

    /**
     * Tests if all enum values exist and are not null.
     */
    @Test
    void testEnumValuesExist() {
        assertNotNull(FuelType.STEAM);
        assertNotNull(FuelType.DIESEL);
        assertNotNull(FuelType.ELECTRICITY);
    }

    /**
     * Tests the valueOf method for each enum constant.
     */
    @Test
    void testValueOf() {
        assertEquals(FuelType.STEAM, FuelType.valueOf("STEAM"));
        assertEquals(FuelType.DIESEL, FuelType.valueOf("DIESEL"));
        assertEquals(FuelType.ELECTRICITY, FuelType.valueOf("ELECTRICITY"));
    }

    /**
     * Tests the order of the enum values.
     */
    @Test
    void testValuesOrder() {
        FuelType[] values = FuelType.values();
        assertArrayEquals(
            new FuelType[]{FuelType.STEAM, FuelType.DIESEL, FuelType.ELECTRICITY},
            values
        );
    }
}