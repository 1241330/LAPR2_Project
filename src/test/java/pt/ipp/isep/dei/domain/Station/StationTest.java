package pt.ipp.isep.dei.domain.Station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Station class.
 */
class StationTest {

    private Station station;
    private Position position;
    private Map map;
    private Scenario scenario;

    /**
     * Initializes common test variables before each test.
     */
    @BeforeEach
    void setUp() {
        position = new Position(2, 3);
        Size size = new Size(10, 10);
        map = new Map("MapaTeste", size);
        scenario = new Scenario(map, "CenarioTeste", 1000, null, null);
        station = new Station(StationType.STATION, position, map.getId(), "North", scenario);
    }

    /**
     * Tests constructor with a name argument.
     */
    @Test
    void testConstructorWithName() {
        Station s = new Station("EstacaoTeste");
        assertEquals("EstacaoTeste", s.getName());
        assertNotNull(s.getBuildings());
    }

    /**
     * Tests setting and getting the station name.
     */
    @Test
    void testGetAndSetName() {
        station.setName("NovaEstacao");
        assertEquals("NovaEstacao", station.getName());
    }

    /**
     * Tests retrieving the position of the station.
     */
    @Test
    void testGetPosition() {
        assertEquals(position, station.getPosition());
    }

    /**
     * Tests getting the station type.
     */
    @Test
    void testGetStationType() {
        assertEquals("STATION", station.getStationType());
    }

    /**
     * Tests setting the station type.
     */
    @Test
    void testSetStationType() {
        station.setStationType("TERMINAL");
        assertEquals("TERMINAL", station.getStationType());
        station.setStationType("DEPOT");
        assertEquals("DEPOT", station.getStationType());
    }

    /**
     * Tests getting and setting the direction of the station.
     */
    @Test
    void testGetAndSetDirection() {
        station.setDirection("South");
        assertEquals("South", station.getDirection());
    }

    /**
     * Tests the return of the type method.
     */
    @Test
    void testGetType() {
        assertEquals("Station", station.getType());
    }

    /**
     * Tests adding and removing buildings from the station.
     */
    @Test
    void testAddAndRemoveBuilding() {
        Building b = new Building(BuildingType.TELEGRAPH);
        station.addBuilding(b);
        assertTrue(station.getBuildings().contains(b));
        station.removeBuilding(b);
        assertFalse(station.getBuildings().contains(b));
    }

    /**
     * Tests getting the buildings list.
     */
    @Test
    void testGetBuildings() {
        assertNotNull(station.getBuildings());
        assertTrue(station.getBuildings().isEmpty());
    }

    /**
     * Tests getting the construction cost from the station type.
     */
    @Test
    void testGetConstructionCost() {
        assertEquals(StationType.STATION.getConstructionCost(), station.getConstructionCost());
    }

    /**
     * Tests retrieving a valid identifier.
     */
    @Test
    void testGetIdentifier() {
        int id = station.getIdentifier();
        assertTrue(id >= 0);
    }

    /**
     * Tests setting the type using the enum directly.
     */
    @Test
    void testSetType() {
        station.setType(StationType.DEPOT);
        assertEquals("DEPOT", station.getStationType());
    }

    /**
     * Tests the string representation of the station.
     */
    @Test
    void testToString() {
        String expected = station.getName() + new Position(position.getX() + 1, position.getY() + 1);
        assertEquals(expected, station.toString());
    }

    /**
     * Tests assigning generation posts when the scenario has empty lists.
     * Should not throw exceptions.
     */
    @Test
    void testAssignGenerationPostsWithEmptyLists() {
        station.assignGenerationPosts(scenario);
        assertNotNull(station.getAllAssociations());
    }

    /**
     * Tests setting the resources type requested with no associations.
     */
    @Test
    void testSetResourcesTypeRequestedWithEmptyAssociations() {
        station.setResourcesTypeRequested(scenario);
        assertNotNull(station.getResourcesTypeRequested());
    }

    /**
     * Tests getting the demand list.
     */
    @Test
    void testGetDemandList() {
        assertNotNull(station.getDemandList());
    }

    /**
     * Tests setting the demand list.
     */
    @Test
    void testSetDemandList() {
        List<pt.ipp.isep.dei.domain.FinancialResult.Demand> list = new ArrayList<>();
        station.setDemandList(list);
        assertEquals(list, station.getDemandList());
    }

    /**
     * Tests setting a new map to the station.
     */
    @Test
    void testSetActualMap() {
        Map newMap = new Map("NovoMapa", new Size(5, 5));
        station.setActualMap(newMap);
    }
}
