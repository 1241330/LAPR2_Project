package pt.ipp.isep.dei.controller.simulation.RailwayRelatedTest.UpgradeRailway;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway.ListRailwayLineToUpgradeController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ListRailwayLineToUpgradeController} class.
 */
public class ListRailwayLineToUpgradeControllerTest {

    /**
     * Helper method to create a simulation with the given railway lines.
     *
     * @param lines the railway lines to add to the map
     * @return a new Simulation instance containing the specified railway lines
     */
    private Simulation createSimulationWithRailwayLines(RailwayLine... lines) {
        Size size = new Size(10, 10);
        Map map = new Map("mapaTeste", size);
        for (RailwayLine line : lines) {
            map.getRailwayLines().add(line);
        }
        TimeDate start = new TimeDate(2020, 1, 1);
        TimeDate end = new TimeDate(2030, 12, 31);
        Scenario scenario = new Scenario(map, "cenarioTeste", 1000, start, end);
        return new Simulation("SimTest", scenario);
    }

    /**
     * Tests the default constructor of the controller.
     */
    @Test
    void testDefaultConstructor() {
        ListRailwayLineToUpgradeController controller = new ListRailwayLineToUpgradeController();
        assertNotNull(controller);
    }

    /**
     * Tests the getSimulation and setSimulation methods.
     */
    @Test
    void testGetAndSetSimulation() {
        ListRailwayLineToUpgradeController controller = new ListRailwayLineToUpgradeController();
        assertNull(controller.getSimulation());
        Simulation sim = createSimulationWithRailwayLines();
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests the getRailwayList method when there are railway lines in the simulation.
     */
    @Test
    void testGetRailwayListWithLines() {
        RailwayLine line1 = new RailwayLine(new Station("A"), new Station("B"), RailwayLineType.SINGLE_ELECTRIFIED);
        RailwayLine line2 = new RailwayLine(new Station("C"), new Station("D"), RailwayLineType.DOUBLE_ELECTRIFIED);
        Simulation sim = createSimulationWithRailwayLines(line1, line2);

        ListRailwayLineToUpgradeController controller = new ListRailwayLineToUpgradeController();
        controller.setSimulation(sim);

        ObservableList<RailwayLine> list = controller.getRailwayList();
        assertEquals(2, list.size());
        assertTrue(list.contains(line1));
        assertTrue(list.contains(line2));
    }

    /**
     * Tests the getRailwayList method when there are no railway lines in the simulation.
     */
    @Test
    void testGetRailwayListEmpty() {
        Simulation sim = createSimulationWithRailwayLines();
        ListRailwayLineToUpgradeController controller = new ListRailwayLineToUpgradeController();
        controller.setSimulation(sim);

        ObservableList<RailwayLine> list = controller.getRailwayList();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }
}

