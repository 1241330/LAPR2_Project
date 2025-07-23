package pt.ipp.isep.dei.domain.Simulation;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Station.Station;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PointOfRouteTest {
    @Test
    void testConstructorAndGetters() {
        List<ResourcesType> cargo = new ArrayList<>();
        ResourcesType res = new ResourcesType("Coal", 100, 1, 10);
        cargo.add(res);
        Station station = new Station("S1");
        PointOfRoute point = new PointOfRoute(cargo, station, TypeOfCargoMode.FULL);
        assertEquals(cargo, point.getCargoToPick());
        assertEquals(station, point.getStation());
        assertEquals(TypeOfCargoMode.FULL, point.getCargoMode());
    }

    @Test
    void testSetters() {
        PointOfRoute point = new PointOfRoute(new ArrayList<>(), null, null);
        List<ResourcesType> cargo = new ArrayList<>();
        ResourcesType res = new ResourcesType("Iron", 50, 2, 5);
        cargo.add(res);
        point.setCargoToPick(cargo);
        assertEquals(cargo, point.getCargoToPick());
        Station station = new Station("S2");
        point.setStation(station);
        assertEquals(station, point.getStation());
        point.setCargoMode(TypeOfCargoMode.HALF);
        assertEquals(TypeOfCargoMode.HALF, point.getCargoMode());
    }

    @Test
    void testNullValues() {
        PointOfRoute point = new PointOfRoute(null, null, null);
        assertNull(point.getCargoToPick());
        assertNull(point.getStation());
        assertNull(point.getCargoMode());
        point.setCargoToPick(null);
        point.setStation(null);
        point.setCargoMode(null);
        assertNull(point.getCargoToPick());
        assertNull(point.getStation());
        assertNull(point.getCargoMode());
    }
}
