package pt.ipp.isep.dei.domain.Train;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Locomotive} class.
 */
class LocomotiveTest {

    /**
     * Tests constructor and all getter methods of the Locomotive class.
     */
    @Test
    void testConstructorAndGetters() {
        Locomotive loco = new Locomotive("L1", "/img/l1.png", 2000.0, 1.5, 120.0, 1990,
                500000, FuelType.DIESEL, 10, 10000);

        assertEquals("L1", loco.getName());
        assertEquals("/img/l1.png", loco.getImagePath());
        assertEquals(2000.0, loco.getPower());
        assertEquals(1.5, loco.getAcceleration());
        assertEquals(120.0, loco.getTopSpeed());
        assertEquals(1990, loco.getStartYearOperation());
        assertEquals(500000, loco.getAcquisitionPrice());
        assertEquals(FuelType.DIESEL, loco.getFuelType());
        assertEquals(0, loco.getRailroadLineAssosiated());
        assertEquals(10, loco.getMaxCarriages());
        assertFalse(loco.getInUse());
        assertEquals(10000, loco.getMaintenanceCost());
    }

    /**
     * Tests setting a new name.
     */
    @Test
    void testSetName() {
        Locomotive loco = createDefaultLocomotive();
        loco.setName("L2");
        assertEquals("L2", loco.getName());
    }

    /**
     * Tests setting a new image path.
     */
    @Test
    void testSetImagePath() {
        Locomotive loco = createDefaultLocomotive();
        loco.setImagePath("/img/l2.png");
        assertEquals("/img/l2.png", loco.getImagePath());
    }

    /**
     * Tests setting a new power value.
     */
    @Test
    void testSetPower() {
        Locomotive loco = createDefaultLocomotive();
        loco.setPower(3000.0);
        assertEquals(3000.0, loco.getPower());
    }

    /**
     * Tests setting a new acceleration value.
     */
    @Test
    void testSetAcceleration() {
        Locomotive loco = createDefaultLocomotive();
        loco.setAcceleration(2.0);
        assertEquals(2.0, loco.getAcceleration());
    }

    /**
     * Tests setting a new top speed value.
     */
    @Test
    void testSetTopSpeed() {
        Locomotive loco = createDefaultLocomotive();
        loco.setTopSpeed(150.0);
        assertEquals(150.0, loco.getTopSpeed());
    }

    /**
     * Tests setting a new start year of operation.
     */
    @Test
    void testSetStartYearOperation() {
        Locomotive loco = createDefaultLocomotive();
        loco.setStartYearOperation(2000);
        assertEquals(2000, loco.getStartYearOperation());
    }

    /**
     * Tests setting a new acquisition price.
     */
    @Test
    void testSetAcquisitionPrice() {
        Locomotive loco = createDefaultLocomotive();
        loco.setAcquisitionPrice(600000);
        assertEquals(600000, loco.getAcquisitionPrice());
    }

    /**
     * Tests setting a new fuel type.
     */
    @Test
    void testSetFuelType() {
        Locomotive loco = createDefaultLocomotive();
        loco.setFuelType(FuelType.ELECTRICITY);
        assertEquals(FuelType.ELECTRICITY, loco.getFuelType());
    }

    /**
     * Tests setting a new associated railroad line.
     */
    @Test
    void testSetRailroadLineAssosiated() {
        Locomotive loco = createDefaultLocomotive();
        loco.setRailroadLineAssosiated(5);
        assertEquals(5, loco.getRailroadLineAssosiated());
    }

    /**
     * Tests setting a new maximum number of carriages.
     */
    @Test
    void testSetMaxCarriages() {
        Locomotive loco = createDefaultLocomotive();
        loco.setMaxCarriages(20);
        assertEquals(20, loco.getMaxCarriages());
    }

    /**
     * Tests setting and getting the 'in use' status.
     */
    @Test
    void testGetAndSetInUse() {
        Locomotive loco = createDefaultLocomotive();
        assertFalse(loco.getInUse());

        loco.setInUse(true);
        assertTrue(loco.getInUse());

        loco.setInUse(false);
        assertFalse(loco.getInUse());
    }

    /**
     * Tests setting a new maintenance cost.
     */
    @Test
    void testSetMaintenanceCost() {
        Locomotive loco = createDefaultLocomotive();
        loco.setMaintenanceCost(20000);
        assertEquals(20000, loco.getMaintenanceCost());
    }

    /**
     * Tests the string representation of a Locomotive instance.
     */
    @Test
    void testToString() {
        Locomotive loco = new Locomotive("L1", "/img/l1.png", 2000.0, 1.5, 120.0, 1990,
                500000, FuelType.DIESEL, 10, 10000);
        String expected = "Locomotive → name: L1 | acquisitionPrice: 500000 € | power: 2000.0 | acceleration: 1.5 | topSpeed: 120.0 | fuel: DIESEL | railroadLineAssosiated: 0";
        assertEquals(expected, loco.toString());
    }

    /**
     * Creates and returns a default Locomotive instance for testing.
     *
     * @return a default Locomotive object
     */
    private Locomotive createDefaultLocomotive() {
        return new Locomotive("L1", "/img/l1.png", 2000.0, 1.5, 120.0, 1990,
                500000, FuelType.DIESEL, 10, 10000);
    }
}
