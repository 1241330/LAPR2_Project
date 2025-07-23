package pt.ipp.isep.dei.domain.RailwayLine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RailwayLine} class.
 */
class RailwayLineTest {

    private Station station1;
    private Station station2;
    private RailwayLineType type;
    private List<Position> path;
    private RailwayLine railwayLine;
    private TimeDate constructionDate;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        station1 = new Station("A");
        station2 = new Station("B");
        type = RailwayLineType.SINGLE_ELECTRIFIED;
        path = Arrays.asList(new Position(0, 0), new Position(0, 1));
        constructionDate = new TimeDate(2024, 1, 1);
        railwayLine = new RailwayLine(path, station1, station2, type, constructionDate);
    }

    /**
     * Tests the constructor and getter methods of {@link RailwayLine}.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals(path, railwayLine.getPositionsRailwayLine());
        assertEquals(station1, railwayLine.getStation1());
        assertEquals(station2, railwayLine.getStation2());
        assertEquals(type, railwayLine.getRailwayType());
    }

    /**
     * Tests the setter for positions of the railway line.
     */
    @Test
    void testSetPositionsRailwayLine() {
        List<Position> newPath = Collections.singletonList(new Position(1, 1));
        railwayLine.setPositionsRailwayLine(newPath);
        assertEquals(newPath, railwayLine.getPositionsRailwayLine());
    }

    /**
     * Tests getting the stations of the railway line.
     */
    @Test
    void testGetStations() {
        List<Station> stations = railwayLine.getStations();
        assertTrue(stations.contains(station1));
        assertTrue(stations.contains(station2));
    }

    /**
     * Tests getting the names of both stations.
     */
    @Test
    void testGetNameStation1And2() {
        assertEquals("A", railwayLine.getNameStation1());
        assertEquals("B", railwayLine.getNameStation2());
    }

    /**
     * Tests the cost calculation of the railway line.
     */
    @Test
    void testCalculateCost() {
        // Creates a map with one occupied position
        Map map = new Map("TestMap", new Size(10, 10));
        // Adds a dummy line occupying (0,0)
        map.getMapElementsUsed().add(new MapElement(new Position(0, 0), Collections.singletonList(new Position(0, 0))) {
            @Override
            public String getType() {
                return "TestElement";
            }
        });
        // Only (0,1) is not occupied, so cost = type.getCost()
        assertEquals(type.getCost(), railwayLine.calculateCost(map));
    }

    /**
     * Tests getting and setting the type enum of the railway line.
     */
    @Test
    void testGetTypeAndSetType() {
        assertEquals(type, railwayLine.getTypeEnum());
        RailwayLineType newType = RailwayLineType.DOUBLE_ELECTRIFIED;
        railwayLine.setTypeEnum(newType);
        assertEquals(newType, railwayLine.getTypeEnum());
    }

    /**
     * Tests getting the type string of the railway line.
     */
    @Test
    void testGetTypeString() {
        assertEquals("RailwayLine", railwayLine.getType());
    }

    /**
     * Tests getting the connection name of the railway line.
     */
    @Test
    void testGetConnectionName() {
        assertEquals("A ↔ B", railwayLine.getConnectionName());
    }

    /**
     * Tests the toString method of {@link RailwayLine}.
     */
    @Test
    void testToString() {
        assertTrue(railwayLine.toString().contains(type.getType()));
        assertTrue(railwayLine.toString().contains("A"));
        assertTrue(railwayLine.toString().contains("B"));
    }

    /**
     * Tests the constructor with null values.
     */
    @Test
    void testConstructorWithNulls() {
        RailwayLine line = new RailwayLine(null, null, null, null, null);
        assertNull(line.getPositionsRailwayLine());
        assertNull(line.getStation1());
        assertNull(line.getStation2());
        assertNull(line.getRailwayType());
        assertNull(line.getTypeEnum());
        assertEquals("RailwayLine", line.getType());
    }

    /**
     * Tests setting the type enum to null.
     */
    @Test
    void testSetTypeEnumNull() {
        railwayLine.setTypeEnum(null);
        assertNull(railwayLine.getTypeEnum());
    }

    /**
     * Tests setting the positions of the railway line to null.
     */
    @Test
    void testSetPositionsRailwayLineNull() {
        railwayLine.setPositionsRailwayLine(null);
        assertNull(railwayLine.getPositionsRailwayLine());
    }

    /**
     * Tests getting the connection name with null stations.
     */
    @Test
    void testGetConnectionNameWithNullStations() {
        RailwayLine line = new RailwayLine(path, null, null, type, constructionDate);
        assertEquals("null ↔ null", line.getConnectionName());
    }

    /**
     * Tests the toString method with null stations.
     */
    @Test
    void testToStringWithNullStations() {
        RailwayLine line = new RailwayLine(path, null, null, type, constructionDate);
        String str = line.toString();
        assertTrue(str.contains("null"));
    }

    /**
     * Tests the cost calculation with a null map.
     */
    @Test
    void testCalculateCostWithNullMap() {
        assertEquals(0, railwayLine.calculateCost(null));
    }

    /**
     * Tests the cost calculation with all positions occupied.
     */
    @Test
    void testCalculateCostWithAllOccupied() {
        Map map = new Map("TestMap", new Size(10, 10));
        // Occupies all positions in the path
        for (Position pos : path) {
            map.getMapElementsUsed().add(new MapElement(pos, Collections.singletonList(pos)) {
                @Override
                public String getType() {
                    return "TestElement";
                }
            });
        }
        // No free positions, cost should be 0
        assertEquals(0, railwayLine.calculateCost(map));
    }
}
