package pt.ipp.isep.dei.controller.industry;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain._Others_.Position;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DeleteIndustryController} class.
 * These tests validate the behavior of deleting industries from a map.
 */
class DeleteIndustryControllerTest {

    /**
     * Test that creating a controller with a non-existent map ID throws an exception.
     * Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    void ensureConstructorWithInvalidMapIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DeleteIndustryController(9999));
    }

    /**
     * Test that deleting a null industry from the map throws an exception.
     * Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    void ensureDeleteIndustryFromMapWithNullThrows() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());

        assertThrows(IllegalArgumentException.class, () -> controller.deleteIndustryFromMap(null));
    }

    /**
     * Test that getting the list of actual industries does not return null.
     * Ensures the list is properly initialized.
     */
    @Test
    void ensureGetActualIndustriesReturnsList() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());

        assertNotNull(controller.getActualIndustries());
    }

    /**
     * Test that checking for active industries returns false when none exist.
     * Ensures {@code thereAreActiveIndustries()} behaves correctly.
     */
    @Test
    void ensureThereAreActiveIndustriesReturnsBoolean() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());

        assertFalse(controller.thereAreActiveIndustries());
    }

    /**
     * Test the setter and getter for the actual industry.
     * Sets the actual industry to {@code null} and verifies the result.
     */
    @Test
    void ensureSetAndGetActualIndustryWorks() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());

        controller.setActualIndustry(null);
        assertNull(controller.getActualIndustry());
    }

    /**
     * Test that deleting an industry from a map with no industries does nothing (no exception, list remains empty).
     */
    @Test
    void ensureDeleteIndustryFromEmptyMapDoesNothing() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());
        // Try to delete a non-existent industry (simulate with a new Industry object)
        Industry fakeIndustry = new Industry("Fake", IndustryType.PRIMARY_SECTOR, new Position(0, 0));
        assertDoesNotThrow(() -> controller.deleteIndustryFromMap(fakeIndustry));
        assertTrue(controller.getActualIndustries().isEmpty());
    }

    /**
     * Test that deleting an existing industry actually removes it from the map.
     */
    @Test
    void ensureDeleteExistingIndustryRemovesIt() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController addController = new AddIndustryController(map.getId());
        addController.addIndustry("Primary Sector Industry", "Industry", 1, 1);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());
        Industry industry = controller.getActualIndustries().get(0);
        controller.deleteIndustryFromMap(industry);
        assertTrue(controller.getActualIndustries().isEmpty());
    }

    /**
     * Test that thereAreActiveIndustries returns true when there is at least one industry.
     */
    @Test
    void ensureThereAreActiveIndustriesReturnsTrueWhenIndustryExists() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController addController = new AddIndustryController(map.getId());
        addController.addIndustry("Primary Sector Industry", "Industry", 1, 1);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());
        assertTrue(controller.thereAreActiveIndustries());
    }

    /**
     * Test that setActualIndustry and getActualIndustry work with a real industry object.
     */
    @Test
    void ensureSetAndGetActualIndustryWithRealIndustry() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController addController = new AddIndustryController(map.getId());
        addController.addIndustry("Primary Sector Industry", "Industry", 1, 1);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());
        Industry industry = controller.getActualIndustries().get(0);
        controller.setActualIndustry(industry);
        assertEquals(industry, controller.getActualIndustry());
    }

    /**
     * Test that deleting the same industry twice does not throw and leaves the list empty.
     */
    @Test
    void ensureDeleteSameIndustryTwiceIsSafe() {
        MapRepository repo = new MapRepository();
        Map map = new Map("Map", new Size(5, 5));
        repo.addMap(map);
        AddIndustryController addController = new AddIndustryController(map.getId());
        addController.addIndustry("Primary Sector Industry", "Industry", 1, 1);
        DeleteIndustryController controller = new DeleteIndustryController(map.getId());
        Industry industry = controller.getActualIndustries().get(0);
        controller.deleteIndustryFromMap(industry);
        assertDoesNotThrow(() -> controller.deleteIndustryFromMap(industry));
        assertTrue(controller.getActualIndustries().isEmpty());
    }
}
