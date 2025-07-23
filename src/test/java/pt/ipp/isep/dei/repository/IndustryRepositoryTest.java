package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link IndustryRepository} class.
 */
class IndustryRepositoryTest {

    private IndustryRepository repository;
    private Industry industry1;
    private Industry industry2;

    /**
     * Setup method executed before each test to initialize common objects.
     */
    @BeforeEach
    void setUp() {
        repository = new IndustryRepository();
        industry1 = new Industry("Industry A", IndustryType.PRIMARY_SECTOR, new Position(1, 1));
        industry2 = new Industry("Industry B", IndustryType.TRANSFORMING, new Position(2, 2));
    }

    /**
     * Tests that an industry can be successfully added to the repository.
     */
    @Test
    void testAddIndustrySuccessfully() {
        assertTrue(repository.addIndustry(industry1));
        assertEquals(1, repository.getIndustryCount());
    }

    /**
     * Tests that adding a null industry throws an IllegalArgumentException.
     */
    @Test
    void testAddIndustryNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.addIndustry(null));
    }

    /**
     * Tests that adding an industry with an existing name returns false and does not increase the count.
     */
    @Test
    void testAddIndustryWithExistingNameReturnsFalse() {
        repository.addIndustry(industry1);
        Industry duplicate = new Industry("Industry A", IndustryType.MIXED, new Position(3, 3));
        assertFalse(repository.addIndustry(duplicate));
        assertEquals(1, repository.getIndustryCount());
    }

    /**
     * Tests successful removal of an industry from the repository.
     */
    @Test
    void testRemoveIndustrySuccessfully() {
        repository.addIndustry(industry1);
        assertTrue(repository.removeIndustry(industry1));
        assertEquals(0, repository.getIndustryCount());
    }

    /**
     * Tests that removing a non-existent industry returns false.
     */
    @Test
    void testRemoveIndustryNotExistsReturnsFalse() {
        assertFalse(repository.removeIndustry(industry1));
    }

    /**
     * Tests that removing a null industry throws an IllegalArgumentException.
     */
    @Test
    void testRemoveIndustryNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeIndustry(null));
    }

    /**
     * Tests that getIndustryById returns the correct industry when the ID exists.
     */
    @Test
    void testGetIndustryByIdReturnsCorrectIndustry() {
        repository.addIndustry(industry1);
        repository.addIndustry(industry2);
        assertEquals(industry2, repository.getIndustryById(industry2.getId()));
    }

    /**
     * Tests that getIndustryById returns null if the ID does not match any industry.
     */
    @Test
    void testGetIndustryByIdNotFoundReturnsNull() {
        assertNull(repository.getIndustryById(99));
    }

    /**
     * Tests that getAllIndustries returns a list containing all added industries.
     */
    @Test
    void testGetAllIndustriesReturnsList() {
        repository.addIndustry(industry1);
        repository.addIndustry(industry2);
        List<Industry> industries = repository.getAllIndustries();
        assertEquals(2, industries.size());
        assertTrue(industries.contains(industry1));
        assertTrue(industries.contains(industry2));
    }

    /**
     * Tests that industryExists returns true for a previously added industry.
     */
    @Test
    void testIndustryExistsReturnsTrue() {
        repository.addIndustry(industry1);
        assertTrue(repository.industryExists(industry1));
    }

    /**
     * Tests that industryExists returns false for an industry that has not been added.
     */
    @Test
    void testIndustryExistsReturnsFalse() {
        assertFalse(repository.industryExists(industry1));
    }

    /**
     * Tests that industryNameExists returns true for a name already in the repository.
     */
    @Test
    void testIndustryNameExistsReturnsTrue() {
        repository.addIndustry(industry1);
        assertTrue(repository.industryNameExists("Industry A"));
    }

    /**
     * Tests that industryNameExists returns false for a name not in the repository.
     */
    @Test
    void testIndustryNameExistsReturnsFalse() {
        assertFalse(repository.industryNameExists("Industry X"));
    }

    /**
     * Tests that getIndustryCount returns 0 for a new repository.
     */
    @Test
    void testGetIndustryCountEmpty() {
        assertEquals(0, repository.getIndustryCount(), "New repository should have zero industries");
    }

    /**
     * Tests that getAllIndustries returns an empty list for a new repository.
     */
    @Test
    void testGetAllIndustriesEmpty() {
        List<Industry> industries = repository.getAllIndustries();
        assertNotNull(industries, "Returned list should not be null");
        assertTrue(industries.isEmpty(), "Returned list should be empty");
    }

    /**
     * Tests that getIndustryById returns null for a negative ID.
     */
    @Test
    void testGetIndustryByIdNegativeId() {
        assertNull(repository.getIndustryById(-1), "Should return null for negative ID");
    }

    /**
     * Tests that industryExists returns false for null input.
     */
    @Test
    void testIndustryExistsNull() {
        assertFalse(repository.industryExists(null), "Should return false for null input");
    }

    /**
     * Tests that industryNameExists returns false for null input.
     */
    @Test
    void testIndustryNameExistsNull() {
        assertFalse(repository.industryNameExists(null), "Should return false for null name");
    }

    /**
     * Tests that industryNameExists returns false for empty string input.
     */
    @Test
    void testIndustryNameExistsEmptyString() {
        assertFalse(repository.industryNameExists(""), "Should return false for empty string name");
    }
}
