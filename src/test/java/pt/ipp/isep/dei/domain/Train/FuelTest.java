package pt.ipp.isep.dei.domain.Train;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuelTest {

    @Test
    void testConstructorAndGetter() {
        Fuel fuel = new Fuel(FuelType.STEAM);
        assertEquals(FuelType.STEAM, fuel.getFuelType());
    }

    @Test
    void testSetter() {
        Fuel fuel = new Fuel(FuelType.ELECTRICITY);
        fuel.setFuelType(FuelType.DIESEL);
        assertEquals(FuelType.DIESEL, fuel.getFuelType());
    }

    @Test
    void testToString() {
        Fuel fuel = new Fuel(FuelType.STEAM);
        String expected = "Fuel{fuelType=ELECTRIC}";
        assertEquals(expected, fuel.toString());
    }

    @Test
    void testNullFuelType() {
        Fuel fuel = new Fuel(null);
        assertNull(fuel.getFuelType());

        fuel.setFuelType(FuelType.DIESEL);
        assertEquals(FuelType.DIESEL, fuel.getFuelType());
    }
}
