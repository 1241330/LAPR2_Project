package pt.ipp.isep.dei.controller.industry;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link AddIndustryController}.
 */
class AddIndustryControllerTest {

    /**
     * Test that adding an industry with valid data works correctly.
     * Verifies that the industry is added to the map's industry list.
     */
    @Test
    void ensureAddIndustryWithValidDataWorks() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());

        controller.addIndustry("Primary Sector Industry", "Industry", 1, 1);

        assertEquals(1, map.getIndustriesList().size());
    }

    /**
     * Test that adding an industry with an invalid type throws an exception.
     * Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    void ensureAddIndustryWithInvalidTypeThrows() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());

        assertThrows(IllegalArgumentException.class, () ->
                controller.addIndustry("Non-existent Type", "Name", 0, 0));
    }

    /**
     * Test that adding an industry with an empty name throws an exception.
     * Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    void ensureAddIndustryWithEmptyNameThrows() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());

        assertThrows(IllegalArgumentException.class, () ->
                controller.addIndustry("Mixed Industry", "", 0, 0));
    }

    /**
     * Test that adding an industry with a null type throws an exception.
     * Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    void ensureAddIndustryWithNullTypeThrows() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());

        assertThrows(IllegalArgumentException.class, () ->
                controller.addIndustry(null, "Name", 0, 0));
    }

    /**
     * Test that trying to add an industry to a non-existent map throws an exception.
     * Expects an {@link IllegalStateException} to be thrown.
     */
    @Test
    void ensureAddIndustryWithNonExistentMapThrows() {
        AddIndustryController controller = new AddIndustryController(9999);

        assertThrows(IllegalStateException.class, () ->
                controller.addIndustry("Mixed Industry", "Name", 0, 0));
    }

    /**
     * Test that adding a duplicate industry (same type, name, and position) is allowed or handled as expected.
     * This test ensures the behaviour is defined (either allows duplicates or prevents them).
     */
    @Test
    void ensureAddDuplicateIndustryBehaviour() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());
        controller.addIndustry("Primary Sector Industry", "Industry", 1, 1);
        // Try to add the same industry again
        controller.addIndustry("Primary Sector Industry", "Industry", 1, 1);
        // Accepts duplicates or not, check the size accordingly (adjust if business logic changes)
        assertEquals(2, map.getIndustriesList().size());
    }

    /**
     * Test that adding an industry with a name containing only spaces throws an exception.
     */
    @Test
    void ensureAddIndustryWithBlankNameThrows() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());
        assertThrows(IllegalArgumentException.class, () ->
                controller.addIndustry("Primary Sector Industry", "   ", 1, 1));
    }

    /**
     * Test that adding an industry with a negative position throws an exception.
     */
    @Test
    void ensureAddIndustryWithNegativePositionThrows() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());
        assertThrows(IllegalArgumentException.class, () ->
                controller.addIndustry("Primary Sector Industry", "Industry", -1, 1));
        assertThrows(IllegalArgumentException.class, () ->
                controller.addIndustry("Primary Sector Industry", "Industry", 1, -1));
    }

    /**
     * Test that adding an industry outside the map boundaries throws an exception.
     */
    @Test
    void ensureAddIndustryOutsideMapThrows() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(2, 2));
        repo.addMap(map);
        AddIndustryController controller = new AddIndustryController(map.getId());
        assertThrows(IllegalArgumentException.class, () ->
                controller.addIndustry("Primary Sector Industry", "Industry", 5, 5));
    }
}
