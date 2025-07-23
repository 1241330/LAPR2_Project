package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrainTest.Construct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct.ConstructTrainPurchaseConfirmationPopupController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.Train;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ConstructTrainPurchaseConfirmationPopupController} class.
 */
class ConstructTrainPurchaseConfirmationPopupControllerTest {

    private ConstructTrainPurchaseConfirmationPopupController controller;

    /**
     * Initializes a new instance of the controller before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new ConstructTrainPurchaseConfirmationPopupController();
    }

    /**
     * Creates a {@link Scenario} instance with just enough data to support {@link Simulation} creation.*
     * @return a scenario instance
     */
    private Scenario createMinimalScenario() {
        return new Scenario(null, "Test", 1000,
                new TimeDate(2000, 1, 1),
                new TimeDate(2000, 1, 31)) {
            @Override
            public void setMap(Map map) {
            }

            @Override
            public List<Locomotive> getAvailableLocomotiveList() {
                return java.util.Collections.emptyList();
            }
        };
    }

    /**
     * Tests that a simulation can be set and retrieved correctly in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        Scenario scenario = createMinimalScenario();
        Simulation sim = new Simulation("Sim1", scenario);
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests that a train can be successfully created with a valid simulation,
     * locomotive, and carriage list. Also checks that all components are marked
     * as "in use" and the train is added to the simulation.
     */
    @Test
    void testConfirmTrainCreationWithRealSimulation() {
        Scenario scenario = createMinimalScenario();
        Simulation simulation = new Simulation("Sim2", scenario);

        Locomotive locomotive = new Locomotive(
                "Loco1", "path.png", 4000.0, 2.5, 120.0,
                2010, 500000, FuelType.DIESEL, 10, 1000
        );

        Carriage carriage1 = new Carriage("Carr1", "img1.png", 2012, 100000, 50);
        Carriage carriage2 = new Carriage("Carr2", "img2.png", 2015, 120000, 60);

        List<Carriage> carriages = Arrays.asList(carriage1, carriage2);

        controller.setSimulation(simulation);
        controller.setLocomotive(locomotive);
        controller.setCarriages(carriages);

        controller.confirmTrainCreation();

        assertTrue(locomotive.getInUse());
        assertTrue(carriage1.getInUse());
        assertTrue(carriage2.getInUse());

        List<Train> trains = simulation.getTrainList();
        assertNotNull(trains);
        assertEquals(1, trains.size());
        Train createdTrain = trains.get(0);
        assertEquals(locomotive, createdTrain.getLocomotive());
        assertEquals(carriages, createdTrain.getCarriages());
    }

    /**
     * Tests that {@code confirmTrainCreation} throws a {@link NullPointerException}
     * when the locomotive or carriages have not been set.
     */
    @Test
    void testConfirmTrainCreationThrowsOnNullTrain() {
        Scenario scenario = createMinimalScenario();
        Simulation simulation = new Simulation("Sim3", scenario);
        controller.setSimulation(simulation);

        assertThrows(NullPointerException.class, () -> controller.confirmTrainCreation());
    }
}
