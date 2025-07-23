package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.FuelType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StartLocomotiveOperationEventTest {
    private Simulation simulation;
    private Locomotive locomotive;
    private StartLocomotiveOperationEvent event;

    @BeforeEach
    void setUp() {
        simulation = new Simulation("SimulacaoTeste", null);
        locomotive = new Locomotive("Loco1", "img.png", 1000.0, 2.0, 120.0, 2024, 5000, FuelType.DIESEL, 10, 100);
        event = new StartLocomotiveOperationEvent("StartLoco", 5, 2024, simulation, locomotive);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(simulation, event.getSimulation());
        assertEquals(locomotive, event.getLocomotive());
        assertNotNull(event.getAlertMessages());
        assertFalse(event.getAlertMessages().isEmpty());
    }

    @Test
    void testSetters() {
        Simulation sim2 = new Simulation("OutraSimulacao", null);
        Locomotive loco2 = new Locomotive("Loco2", "img2.png", 900.0, 1.5, 110.0, 2025, 4000, FuelType.ELECTRICITY, 8, 80);
        event.setSimulation(sim2);
        event.setLocomotive(loco2);
        assertEquals(sim2, event.getSimulation());
        assertEquals(loco2, event.getLocomotive());
    }

    @Test
    void testSetAndGetAlertMessages() {
        List<String> msgs = List.of("msg1", "msg2");
        event.setAlertMessages(msgs);
        assertEquals(msgs, event.getAlertMessages());
    }

    @Test
    void testTriggerAddsLocomotiveAndReturnsAlert() {
        List<Locomotive> before = simulation.getAvailableDateLocomotives();
        List<String> logs = event.trigger();
        assertNotNull(logs);
        assertTrue(logs.size() >= 2);
        assertTrue(logs.get(1).contains(locomotive.getName()));
        assertTrue(simulation.getAvailableDateLocomotives().contains(locomotive));
    }
}
