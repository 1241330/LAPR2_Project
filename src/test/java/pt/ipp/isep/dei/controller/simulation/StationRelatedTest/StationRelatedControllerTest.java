package pt.ipp.isep.dei.controller.simulation.StationRelatedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.StationRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for StationRelatedController.
 */
public class StationRelatedControllerTest {
    private StationRelatedController controller;
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        controller = new StationRelatedController();
        // Criação de um Scenario real para evitar NullPointerException
        Size size = new Size(1, 1);
        Map map = new Map("mapa", size);
        pt.ipp.isep.dei.domain.Simulation.TimeDate begin = new pt.ipp.isep.dei.domain.Simulation.TimeDate(2020, 1, 1);
        pt.ipp.isep.dei.domain.Simulation.TimeDate end = new pt.ipp.isep.dei.domain.Simulation.TimeDate(2021, 1, 1);
        int dinheiro = 1000;
        Scenario scenario = new Scenario(map, "cenário", dinheiro, begin, end);
        simulation = new Simulation("TestSimulation", scenario);
        controller.setSimulation(simulation);
    }

    /**
     * Tests setSimulation and getSimulation.
     */
    @Test
    void testSetAndGetSimulation() {
        StationRelatedController c2 = new StationRelatedController();
        c2.setSimulation(simulation);
        assertEquals(simulation, c2.getSimulation());
    }

    /**
     * Tests getActualDate returns a string (delegates to Utils and TimeDate).
     */
    @Test
    void testGetActualDate() {
        // Por padrão, retorna uma string (não testamos lógica interna de Utils/TimeDate aqui)
        String date = controller.getActualDate();
        assertNotNull(date);
        assertTrue(date instanceof String);
    }

    /**
     * Tests getActualMoney returns the simulation's money as string.
     */
    @Test
    void testGetActualMoney() {
        simulation.setActualMoney(1234);
        assertEquals("1234", controller.getActualMoney());
    }
}
