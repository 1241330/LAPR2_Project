package pt.ipp.isep.dei.controller.simulation.CreateEventsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.CreateEvents.CreateAvailableDateEvent;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.StartCarriageOperationEvent;
import pt.ipp.isep.dei.domain.Event.StartLocomotiveOperationEvent;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.repository.CarriageRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateAvailableDateEvent class.
 */
public class CreateAvailableDateEventTest {
    private CreateAvailableDateEvent creator;
    private Simulation simulation;
    private List<Locomotive> locomotives;
    private List<Carriage> carriages;
    private CarriageRepository carriageRepository;

    @BeforeEach
    void setUp() {
        locomotives = new ArrayList<>();
        carriages = new ArrayList<>();
        // Instanciar Locomotives
        Locomotive loco1 = new Locomotive("L1", "img", 1.0, 1.0, 1.0, 2000, 1000, null, 5, 100);
        Locomotive loco2 = new Locomotive("L2", "img", 1.0, 1.0, 1.0, 2010, 1000, null, 5, 100);
        locomotives.add(loco1);
        locomotives.add(loco2);
        // Instanciar Carriages
        Carriage carr1 = new Carriage("C1", "img", 2005, 100, 10);
        Carriage carr2 = new Carriage("C2", "img", 2015, 100, 10);
        carriages.add(carr1);
        carriages.add(carr2);
        // Instanciar Scenario
        Scenario scenario = new Scenario(null, "cenario", 1000, null, null);
        scenario.getAvailableLocomotiveList().addAll(locomotives);
        // Instanciar Simulation
        simulation = new Simulation("simulacao", scenario);
        // Instanciar CarriageRepository
        carriageRepository = new CarriageRepository() {
            @Override
            public List<Carriage> getCarriageList() {
                return carriages;
            }
        };
        creator = new CreateAvailableDateEvent(simulation);
        creator.setCarriageRepository(carriageRepository);
    }

    /**
     * Tests if the constructor and getters work as expected.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals(simulation, creator.getSimulation());
        assertEquals(carriageRepository, creator.getCarriageRepository());
        assertEquals(locomotives, creator.getLocomotivesList());
        assertNotNull(creator.getDateEventsList());
    }

    /**
     * Tests all setters for the CreateAvailableDateEvent class.
     */
    @Test
    void testSetters() {
        Simulation sim2 = simulation;
        creator.setSimulation(sim2);
        assertEquals(sim2, creator.getSimulation());
        CarriageRepository repo2 = carriageRepository;
        creator.setCarriageRepository(repo2);
        assertEquals(repo2, creator.getCarriageRepository());
        List<Locomotive> locs2 = new ArrayList<>();
        creator.setLocomotivesList(locs2);
        assertEquals(locs2, creator.getLocomotivesList());
        List<Event> events2 = new ArrayList<>();
        creator.setDateEventsList(events2);
        assertEquals(events2, creator.getDateEventsList());
    }

    /**
     * Tests addEventsToList with empty lists (should not create any events).
     */
    @Test
    void testAddEventsToListWithEmptyLists() {
        creator.setLocomotivesList(new ArrayList<>());
        creator.setCarriageRepository(new CarriageRepository() {
            @Override
            public List<Carriage> getCarriageList() {
                return new ArrayList<>();
            }
        });
        creator.addEventsToList();
        assertTrue(creator.getDateEventsList().isEmpty());
    }

    /**
     * Tests addEventsToList with real locomotives and carriages (should create one event per item).
     */
    @Test
    void testAddEventsToListWithLocomotivesAndCarriages() {
        creator.setLocomotivesList(locomotives);
        creator.setCarriageRepository(carriageRepository);
        creator.addEventsToList();
        List<Event> events = creator.getDateEventsList();
        assertEquals(4, events.size());
        assertTrue(events.stream().anyMatch(e -> e instanceof StartLocomotiveOperationEvent));
        assertTrue(events.stream().anyMatch(e -> e instanceof StartCarriageOperationEvent));
        assertTrue(events.stream().anyMatch(e -> e.getName().contains("L1 available in 2000")));
        assertTrue(events.stream().anyMatch(e -> e.getName().contains("C2 available in 2015")));
    }

    /**
     * Tests that addEventsToList does not duplicate events if called multiple times.
     */
    @Test
    void testAddEventsToListDoesNotDuplicateEvents() {
        creator.setLocomotivesList(locomotives);
        creator.setCarriageRepository(carriageRepository);
        creator.addEventsToList();
        int initialSize = creator.getDateEventsList().size();
        creator.addEventsToList(); // Chamar de novo não deve duplicar
        assertEquals(initialSize, creator.getDateEventsList().size());
    }

    /**
     * Tests setDateEventsList replaces the event list.
     */
    @Test
    void testSetDateEventsList() {
        List<Event> customList = new ArrayList<>();
        creator.setDateEventsList(customList);
        assertEquals(customList, creator.getDateEventsList());
    }
}
