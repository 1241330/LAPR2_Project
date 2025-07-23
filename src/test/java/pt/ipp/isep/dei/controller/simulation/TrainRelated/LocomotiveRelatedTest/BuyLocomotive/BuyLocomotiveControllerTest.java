package pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelatedTest.BuyLocomotive;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.dto.LocomotiveDTO;
import pt.ipp.isep.dei.mapper.LocomotiveMapper;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BuyLocomotiveController class.
 */
public class BuyLocomotiveControllerTest {
    private Scenario createScenario() {
        Size size = new Size(100, 100);
        Map map = new Map("mapaTeste", size);
        TimeDate inicio = new TimeDate(2020, 1, 1);
        TimeDate fim = new TimeDate(2030, 12, 31);
        return new Scenario(map, "cenarioTeste", 10000, inicio, fim);
    }

    private Simulation createSimulation(int money) {
        Simulation sim = new Simulation("SimTest", createScenario());
        try {
            java.lang.reflect.Field f = Simulation.class.getDeclaredField("actualMoney");
            f.setAccessible(true);
            f.setInt(sim, money);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sim;
    }

    private Locomotive createLocomotive(String name, int price) {
        return new Locomotive(name, "img.png", 1000.0, 2.0, 120.0, 2000, price, FuelType.DIESEL, 10, 1000);
    }

    /**
     * Tests setting and getting the Simulation in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        BuyLocomotiveController controller = new BuyLocomotiveController();
        Simulation sim = createSimulation(10000);
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests getting the list of available locomotives as DTOs.
     */
    @Test
    void testGetLocomotivesList() {
        BuyLocomotiveController controller = new BuyLocomotiveController();
        Simulation sim = createSimulation(10000);
        Locomotive l1 = createLocomotive("L1", 5000);
        Locomotive l2 = createLocomotive("L2", 7000);
        sim.getAvailableDateLocomotives().add(l1);
        sim.getAvailableDateLocomotives().add(l2);
        controller.setSimulation(sim);
        ObservableList<LocomotiveDTO> result = controller.getLocomotivesList();
        assertEquals(2, result.size());
        assertEquals("L1", result.get(0).getName());
        assertEquals("L2", result.get(1).getName());
    }

    /**
     * Tests notEnoughMoney returns true if money is insufficient, false otherwise.
     */
    @Test
    void testNotEnoughMoney() {
        BuyLocomotiveController controller = new BuyLocomotiveController();
        Simulation sim = createSimulation(4000);
        controller.setSimulation(sim);
        LocomotiveDTO dto = LocomotiveMapper.toDTO(createLocomotive("L1", 5000));
        assertTrue(controller.notEnoughMoney(dto));
        LocomotiveDTO dto2 = LocomotiveMapper.toDTO(createLocomotive("L2", 3000));
        assertFalse(controller.notEnoughMoney(dto2));
    }

    /**
     * Tests converting a LocomotiveDTO to a domain Locomotive.
     */
    @Test
    void testGetDomainLocomotiveFromDTO() {
        BuyLocomotiveController controller = new BuyLocomotiveController();
        Locomotive l = createLocomotive("L1", 5000);
        LocomotiveDTO dto = LocomotiveMapper.toDTO(l);
        Locomotive domain = controller.getDomainLocomotiveFromDTO(dto);
        assertEquals(l.getName(), domain.getName());
        assertEquals(l.getAcquisitionPrice(), domain.getAcquisitionPrice());
    }
}
