package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StartCarriageOperationEventTest {

    private StartCarriageOperationEvent event;
    private TestSimulation simulation;
    private TestCarriage carriage;

    private static final String EVENT_NAME = "Start Carriage";
    private static final int INTERVAL = 5;
    private static final int YEAR = 2024;
    private static final String CARRIAGE_NAME = "CargoX";

    // Classe de teste para Carriage com todos os argumentos obrigatórios
    static class TestCarriage extends Carriage {
        private final String name;

        public TestCarriage(String name) {
            super(name, "testType", 2024, 100, 20);
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    // Implementação mínima de Simulation
    static class TestSimulation extends Simulation {
        private final List<Carriage> availableCarriages = new ArrayList<>();

        public TestSimulation() {
            super("test", null);
        }

        @Override
        public void addAvailableDateCarriage(Carriage carriage) {
            availableCarriages.add(carriage);
        }

        @Override
        public List<Carriage> getAvailableDateCarriages() {
            return availableCarriages;
        }
    }

    @BeforeEach
    void setUp() {
        simulation = new TestSimulation();
        carriage = new TestCarriage(CARRIAGE_NAME);
        event = new StartCarriageOperationEvent(EVENT_NAME, INTERVAL, YEAR, simulation, carriage);
    }

    @Test
    void testConstructorInitializesFields() {
        assertEquals(EVENT_NAME, event.getName());
        assertEquals(INTERVAL, event.getInterval());
        assertEquals(carriage, event.getCarriage());
        assertEquals(simulation, event.getSimulation());
        assertNotNull(event.getAlertMessages());
        assertFalse(event.getAlertMessages().isEmpty());
    }

    @Test
    void testSetAndGetSimulation() {
        TestSimulation newSimulation = new TestSimulation();
        event.setSimulation(newSimulation);
        assertEquals(newSimulation, event.getSimulation());
    }

    @Test
    void testSetAndGetCarriage() {
        TestCarriage newCarriage = new TestCarriage("PassengerY");
        event.setCarriage(newCarriage);
        assertEquals(newCarriage, event.getCarriage());
    }

    @Test
    void testSetAndGetAlertMessages() {
        List<String> newMessages = new ArrayList<>();
        newMessages.add("Mensagem personalizada");
        event.setAlertMessages(newMessages);
        assertEquals(newMessages, event.getAlertMessages());
    }

    @Test
    void testTriggerAddsCarriageAndReturnsAlert() {
        List<String> logs = event.trigger();
        assertEquals(1, logs.size());
        assertTrue(logs.get(0).contains(CARRIAGE_NAME));
        assertTrue(simulation.getAvailableDateCarriages().contains(carriage));
    }

    @Test
    void testAlertMessagesContent() {
        List<String> messages = event.getAlertMessages();
        boolean found = false;
        for (String msg : messages) {
            if (msg.contains(CARRIAGE_NAME)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Pelo menos uma mensagem deve conter o nome da carruagem");
    }
}