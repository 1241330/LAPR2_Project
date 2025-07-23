package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Station.Station;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link StationRepository} class.
 * This class covers all scenarios for adding, removing, checking and retrieving stations.
 */
class StationRepositoryTest {

    private StationRepository repository;

    /**
     * Sets up a new repository before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new StationRepository();
    }

    /**
     * Tests that adding a null station throws IllegalArgumentException.
     */
    @Test
    void testAddStation_Null_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.addStation(null), "Adding null station should throw IllegalArgumentException");
    }

    /**
     * Tests that a valid station is added successfully.
     */
    @Test
    void testAddStation_Valid_AddsStation() {
        Station station = new Station("Station1");
        assertTrue(repository.addStation(station), "Should add valid station");
        assertTrue(repository.stationExists(station), "Repository should contain the added station");
    }

    /**
     * Tests that adding a station with a duplicate name returns false and does not add the station.
     */
    @Test
    void testAddStation_DuplicateName_ReturnsFalse() {
        Station station1 = new Station("Station1");
        Station station2 = new Station("Station1");
        repository.addStation(station1);
        assertFalse(repository.addStation(station2), "Should not add station with duplicate name");
    }

    /**
     * Tests that removing a null station throws IllegalArgumentException.
     */
    @Test
    void testRemoveStation_Null_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeStation(null), "Removing null station should throw IllegalArgumentException");
    }

    /**
     * Tests that removing a station that does not exist returns false.
     */
    @Test
    void testRemoveStation_NotExists_ReturnsFalse() {
        Station station = new Station("Station1");
        assertFalse(repository.removeStation(station), "Should return false when removing non-existent station");
    }

    /**
     * Tests that removing an existing station returns true and the station no longer exists.
     */
    @Test
    void testRemoveStation_Valid_RemovesStation() {
        Station station = new Station("Station1");
        repository.addStation(station);
        assertTrue(repository.removeStation(station), "Should remove existing station");
        assertFalse(repository.stationExists(station), "Repository should not contain the removed station");
    }

    /**
     * Tests that checking existence for an added station returns true.
     */
    @Test
    void testStationExists_Exists_ReturnsTrue() {
        Station station = new Station("Station1");
        repository.addStation(station);
        assertTrue(repository.stationExists(station), "Should return true for existing station");
    }

    /**
     * Tests that checking existence for a station not added returns false.
     */
    @Test
    void testStationExists_NotExists_ReturnsFalse() {
        Station station = new Station("Station1");
        assertFalse(repository.stationExists(station), "Should return false for non-existing station");
    }

    /**
     * Tests that checking if a station name exists returns true for an existing station name.
     */
    @Test
    void testStationNameExists_Exists_ReturnsTrue() {
        Station station = new Station("Station1");
        repository.addStation(station);
        assertTrue(repository.stationNameExists("Station1"), "Should return true for existing station name");
    }

    /**
     * Tests that checking if a station name exists returns false for a non-existent station name.
     */
    @Test
    void testStationNameExists_NotExists_ReturnsFalse() {
        Station station = new Station("Station1");
        repository.addStation(station);
        assertFalse(repository.stationNameExists("Station2"), "Should return false for non-existing station name");
    }

    /**
     * Tests that retrieving all station names returns a list containing the names of all added stations.
     */
    @Test
    void testGetAllStationNames_ReturnsAllNames() {
        Station station1 = new Station("Station1");
        Station station2 = new Station("Station2");
        repository.addStation(station1);
        repository.addStation(station2);
        List<String> names = repository.getAllStationNames();
        assertEquals(2, names.size());
        assertTrue(names.contains("Station1"));
        assertTrue(names.contains("Station2"));
    }

    /**
     * Tests that retrieving all stations returns a list containing all added stations.
     */
    @Test
    void testGetStations_ReturnsAllStations() {
        Station station1 = new Station("Station1");
        Station station2 = new Station("Station2");
        repository.addStation(station1);
        repository.addStation(station2);
        List<Station> stations = repository.getStations();
        assertEquals(2, stations.size());
        assertTrue(stations.contains(station1));
        assertTrue(stations.contains(station2));
    }

    /**
     * Tests that setting a new list of stations replaces the current list of stations in the repository.
     */
    @Test
    void testSetStations_SetsNewList() {
        Station station1 = new Station("Station1");
        Station station2 = new Station("Station2");
        List<Station> newStations = new ArrayList<>();
        newStations.add(station1);
        newStations.add(station2);
        repository.setStations(newStations);
        List<Station> stations = repository.getStations();
        assertEquals(2, stations.size());
        assertTrue(stations.contains(station1));
        assertTrue(stations.contains(station2));
    }
}
