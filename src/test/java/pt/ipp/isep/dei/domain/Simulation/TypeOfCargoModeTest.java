package pt.ipp.isep.dei.domain.Simulation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TypeOfCargoMode} enum.
 * These tests ensure that the enum values, valueOf behavior, and toString method are working correctly.
 */
class TypeOfCargoModeTest {

    /**
     * Test that all enum values are present and in the correct order.
     */
    @Test
    void testEnumValues() {
        TypeOfCargoMode[] values = TypeOfCargoMode.values();
        assertEquals(3, values.length, "Enum should have 3 values.");
        assertArrayEquals(
                new TypeOfCargoMode[]{
                        TypeOfCargoMode.FULL,
                        TypeOfCargoMode.HALF,
                        TypeOfCargoMode.AVAILABLE
                },
                values,
                "Enum values should be FULL, HALF, and AVAILABLE in this order."
        );
    }

    /**
     * Test valueOf method returns FULL when input is "FULL".
     */
    @Test
    void testValueOfFull() {
        assertEquals(TypeOfCargoMode.FULL, TypeOfCargoMode.valueOf("FULL"));
    }

    /**
     * Test valueOf method returns HALF when input is "HALF".
     */
    @Test
    void testValueOfHalf() {
        assertEquals(TypeOfCargoMode.HALF, TypeOfCargoMode.valueOf("HALF"));
    }

    /**
     * Test valueOf method returns AVAILABLE when input is "AVAILABLE".
     */
    @Test
    void testValueOfAvailable() {
        assertEquals(TypeOfCargoMode.AVAILABLE, TypeOfCargoMode.valueOf("AVAILABLE"));
    }

    /**
     * Test that valueOf throws an IllegalArgumentException when given an invalid value.
     */
    @Test
    void testValueOfThrowsExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> TypeOfCargoMode.valueOf("INVALID"));
    }

    /**
     * Test toString method returns the correct string representation for each enum constant.
     */
    @Test
    void testToString() {
        assertEquals("FULL", TypeOfCargoMode.FULL.toString());
        assertEquals("HALF", TypeOfCargoMode.HALF.toString());
        assertEquals("AVAILABLE", TypeOfCargoMode.AVAILABLE.toString());
    }
}
