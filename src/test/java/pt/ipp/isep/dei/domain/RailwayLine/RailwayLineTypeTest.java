package pt.ipp.isep.dei.domain.RailwayLine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RailwayLineTypeTest {

    @Test
    void testSingleElectrifiedTrack() {
        RailwayLineType type = RailwayLineType.SINGLE_ELECTRIFIED;
        assertEquals("Single Electrified Track", type.getType());
        assertEquals(0, type.getId());
        assertEquals(11, type.getCost());
    }

    @Test
    void testDoubleElectrifiedTrack() {
        RailwayLineType type = RailwayLineType.DOUBLE_ELECTRIFIED;
        assertEquals("Double Electrified Track", type.getType());
        assertEquals(1, type.getId());
        assertEquals(14, type.getCost());
    }

    @Test
    void testSingleNonElectrifiedTrack() {
        RailwayLineType type = RailwayLineType.SINGLE_NON_ELECTRIFIED;
        assertEquals("Single Non-Electrified Track", type.getType());
        assertEquals(2, type.getId());
        assertEquals(6, type.getCost());
    }

    @Test
    void testDoubleNonElectrifiedTrack() {
        RailwayLineType type = RailwayLineType.DOUBLE_NON_ELECTRIFIED;
        assertEquals("Double Non-Electrified Track", type.getType());
        assertEquals(3, type.getId());
        assertEquals(9, type.getCost());
    }
}
