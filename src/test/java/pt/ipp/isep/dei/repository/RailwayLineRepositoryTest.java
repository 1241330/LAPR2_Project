package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RailwayLineRepository} class.
 * This class covers all scenarios.
 */
class RailwayLineRepositoryTest {

    private RailwayLineRepository repository;
    private RailwayLine railwayLine1;
    private RailwayLine railwayLine2;
    private Station stationA;
    private Station stationB;
    private Station stationC;

    /**
     * Sets up a new repository and sample RailwayLines before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new RailwayLineRepository();
        stationA = new Station("A");
        stationB = new Station("B");
        stationC = new Station("C");
        railwayLine1 = new RailwayLine(stationA, stationB, RailwayLineType.SINGLE_ELECTRIFIED);
        railwayLine2 = new RailwayLine(stationB, stationC, RailwayLineType.DOUBLE_ELECTRIFIED);
    }

    /**
     * Tests adding a RailwayLine successfully.
     */
    @Test
    void testAddRailwayLineSuccess() {
        assertTrue(repository.addRailwayLine(railwayLine1), "Should add railway line successfully");
        assertTrue(repository.railwayLineExists(railwayLine1), "Repository should contain the added railway line");
    }

    /**
     * Tests that adding a null RailwayLine throws IllegalArgumentException.
     */
    @Test
    void testAddRailwayLineNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.addRailwayLine(null), "Adding null railway line should throw IllegalArgumentException");
    }

    /**
     * Tests removing an existing RailwayLine.
     */
    @Test
    void testRemoveRailwayLineSuccess() {
        repository.addRailwayLine(railwayLine1);
        assertTrue(repository.removeRailwayLine(railwayLine1), "Should remove railway line successfully");
        assertFalse(repository.railwayLineExists(railwayLine1), "Repository should not contain the removed railway line");
    }

    /**
     * Tests removing a RailwayLine that does not exist returns false.
     */
    @Test
    void testRemoveRailwayLineNotExists() {
        assertFalse(repository.removeRailwayLine(railwayLine1), "Should return false when removing non-existent railway line");
    }

    /**
     * Tests removing a null RailwayLine throws IllegalArgumentException.
     */
    @Test
    void testRemoveRailwayLineNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeRailwayLine(null), "Removing null railway line should throw IllegalArgumentException");
    }

    /**
     * Tests existence check for RailwayLines.
     */
    @Test
    void testRailwayLineExists() {
        repository.addRailwayLine(railwayLine1);
        assertTrue(repository.railwayLineExists(railwayLine1), "Should return true for existing railway line");
        assertFalse(repository.railwayLineExists(railwayLine2), "Should return false for non-existing railway line");
    }

    /**
     * Tests retrieval of all RailwayLines.
     */
    @Test
    void testGetAllRailwayLines() {
        repository.addRailwayLine(railwayLine1);
        repository.addRailwayLine(railwayLine2);
        List<RailwayLine> lines = repository.getAllRailwayLines();
        assertEquals(2, lines.size(), "Repository should contain two railway lines");
        assertTrue(lines.contains(railwayLine1), "Repository should contain railwayLine1");
        assertTrue(lines.contains(railwayLine2), "Repository should contain railwayLine2");
    }

    /**
     * Tests setting the RailwayLines list directly.
     */
    @Test
    void testSetRailwayLines() {
        List<RailwayLine> newList = new ArrayList<>();
        newList.add(railwayLine1);
        repository.setRailwayLines(newList);
        assertEquals(1, repository.getRailwayLines().size(), "Repository should contain one railway line after setRailwayLines");
        assertTrue(repository.getRailwayLines().contains(railwayLine1), "Repository should contain the set railway line");
    }

    /**
     * Tests getRailwayLinesCount returns the correct number of railway lines.
     */
    @Test
    void testGetRailwayLinesCount() {
        assertEquals(0, repository.getRailwayLinesCount(), "Repository should initially be empty");
        repository.addRailwayLine(railwayLine1);
        assertEquals(1, repository.getRailwayLinesCount(), "Repository should have one railway line after addition");
    }
}
