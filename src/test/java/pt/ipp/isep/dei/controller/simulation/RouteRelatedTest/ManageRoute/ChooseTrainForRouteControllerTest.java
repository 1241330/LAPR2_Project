package pt.ipp.isep.dei.controller.simulation.RouteRelatedTest.ManageRoute;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.ChooseTrainForRouteController;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ChooseTrainForRouteControllerTest class.
 */
public class ChooseTrainForRouteControllerTest {
    private ChooseTrainForRouteController controller;
    private Simulation simulation;
    private Route routeElectric;
    private Route routeNonElectric;
    private Train trainElectricInactive;
    private Train trainDieselInactive;
    private Train trainElectricActive;
    private Train trainDieselActive;

    @BeforeEach
    void setUp() {
        controller = new ChooseTrainForRouteController();
        simulation = new Simulation("simulacao", null);
        // Locomotives
        Locomotive locoElectric = new Locomotive("Locomotiva1", "img.png", 1000.0, 2.0, 120.0, 2000, 5000, FuelType.ELECTRICITY, 10, 100);
        Locomotive locoDiesel = new Locomotive("Locomotiva2", "img.png", 1000.0, 2.0, 120.0, 2000, 5000, FuelType.DIESEL, 10, 100);
        // Data para aquisição
        TimeDate acquisitionDate = new TimeDate(2024, 1, 1);
        // Trains
        trainElectricInactive = new Train(locoElectric, new ArrayList<Carriage>(), acquisitionDate);
        trainElectricInactive.setActiveFlag(false);
        trainDieselInactive = new Train(locoDiesel, new ArrayList<Carriage>(), acquisitionDate);
        trainDieselInactive.setActiveFlag(false);
        trainElectricActive = new Train(locoElectric, new ArrayList<Carriage>(), acquisitionDate);
        trainElectricActive.setActiveFlag(true);
        trainDieselActive = new Train(locoDiesel, new ArrayList<Carriage>(), acquisitionDate);
        trainDieselActive.setActiveFlag(true);
        controller.setSimulation(simulation);
        // Routes reais sem mocks
        routeElectric = new Route(new ArrayList<>(), new ArrayList<>(), "rotaE", true);
        routeNonElectric = new Route(new ArrayList<>(), new ArrayList<>(), "rotaNE", false);
        controller.setRoute(routeElectric);
    }

    /**
     * Tests the getter and setter for simulation.
     */
    @Test
    void testGetAndSetSimulation() {
        assertEquals(simulation, controller.getSimulation());
        Simulation sim2 = new Simulation("simulacaoTeste", null);
        controller.setSimulation(sim2);
        assertEquals(sim2, controller.getSimulation());
    }

    /**
     * Tests the getter and setter for route.
     */
    @Test
    void testGetAndSetRoute() {
        controller.setRoute(routeElectric);
        assertEquals(routeElectric, controller.getRoute());
        controller.setRoute(routeNonElectric);
        assertEquals(routeNonElectric, controller.getRoute());
    }

    /**
     * Tests getAvailableTrains for electric route (should return all inactive trains).
     */
    @Test
    void testGetAvailableTrains_ElectricRoute() {
        simulation.getTrainList().clear();
        simulation.getTrainList().add(trainElectricInactive);
        simulation.getTrainList().add(trainDieselInactive);
        simulation.getTrainList().add(trainElectricActive);
        controller.setRoute(routeElectric);
        ObservableList<Train> result = controller.getAvailableTrains();
        assertTrue(result.contains(trainElectricInactive));
        assertTrue(result.contains(trainDieselInactive));
        assertFalse(result.contains(trainElectricActive));
    }

    /**
     * Tests getAvailableTrains for non-electric route (should return only inactive non-electric trains).
     */
    @Test
    void testGetAvailableTrains_NonElectricRoute() {
        simulation.getTrainList().clear();
        simulation.getTrainList().add(trainElectricInactive);
        simulation.getTrainList().add(trainDieselInactive);
        simulation.getTrainList().add(trainDieselActive);
        controller.setRoute(routeNonElectric);
        ObservableList<Train> result = controller.getAvailableTrains();
        assertTrue(result.contains(trainDieselInactive));
        assertFalse(result.contains(trainElectricInactive));
        assertFalse(result.contains(trainDieselActive));
    }

    /**
     * Tests getAvailableTrains when there are no trains.
     */
    @Test
    void testGetAvailableTrains_NoTrains() {
        simulation.getTrainList().clear();
        controller.setRoute(routeElectric);
        ObservableList<Train> result = controller.getAvailableTrains();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests getAvailableTrains when all trains are active.
     */
    @Test
    void testGetAvailableTrains_AllActive() {
        simulation.getTrainList().clear();
        simulation.getTrainList().add(trainElectricActive);
        simulation.getTrainList().add(trainDieselActive);
        controller.setRoute(routeElectric);
        ObservableList<Train> result = controller.getAvailableTrains();
        assertTrue(result.isEmpty());
    }

    /**
     * Tests getAvailableTrains when all trains are inactive.
     */
    @Test
    void testGetAvailableTrains_AllInactive() {
        simulation.getTrainList().clear();
        simulation.getTrainList().add(trainElectricInactive);
        simulation.getTrainList().add(trainDieselInactive);
        controller.setRoute(routeElectric);
        ObservableList<Train> result = controller.getAvailableTrains();
        assertEquals(2, result.size());
        assertTrue(result.contains(trainElectricInactive));
        assertTrue(result.contains(trainDieselInactive));
    }
}

