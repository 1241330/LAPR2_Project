package pt.ipp.isep.dei.controller.simulation.StationRelatedTest.AddStation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation.AddStationController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for the {@link AddStationController} class.
 *
 * This test class validates the behavior of the AddStationController, including setting and retrieving simulation data,
 * handling station types, verifying available money, and retrieving properties like radius and price based on station types.
 */
public class AddStationControllerTest {
    private AddStationController controller;
    private SimulationFake simulationFake;

    /**
     * A fake implementation of {@link Simulation} for testing purposes,
     * allowing direct control of the available money.
     */
    static class SimulationFake extends Simulation {
        private int actualMoney;

        /**
         * Constructor for the fake simulation.
         *
         * @param money Initial amount of money.
         */
        public SimulationFake(int money) {
            super(null, null);
            this.actualMoney = money;
        }

        /**
         * Gets the currently available money.
         *
         * @return the actual money.
         */
        @Override
        public int getActualMoney() {
            return actualMoney;
        }

        /**
         * Sets the amount of available money.
         *
         * @param money the new amount of money.
         */
        public void setActualMoney(int money) {
            this.actualMoney = money;
        }
    }

    /**
     * Initializes the controller and the fake simulation before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new AddStationController();
        simulationFake = new SimulationFake(0);
    }

    /**
     * Tests the set and get functionality for the simulation in the controller.
     */
    @Test
    void testGetAndSetSimulation() {
        controller.setSimulation(simulationFake);
        assertEquals(simulationFake, controller.getSimulation());
    }

    /**
     * Tests whether the controller returns the correct list of available station types.
     */
    @Test
    void testGetStationTypes() {
        List<String> types = controller.getStationTypes();
        assertEquals(3, types.size());
        assertTrue(types.contains(StationType.DEPOT.toString()));
        assertTrue(types.contains(StationType.STATION.toString()));
        assertTrue(types.contains(StationType.TERMINAL.toString()));
    }

    /**
     * Tests setting and getting a specific station type in the controller.
     */
    @Test
    void testSetAndGetStationType() {
        controller.setStationType(StationType.STATION);
        assertEquals(StationType.STATION, controller.getStationType());
    }

    /**
     * Tests the money verification logic when the user has enough money for the selected station type.
     */
    @Test
    void testVerifyIfYouHaveMoney_EnoughMoney() {
        simulationFake.setActualMoney(100);
        controller.setSimulation(simulationFake);
        controller.setStationType(StationType.DEPOT);
        assertTrue(controller.verifyIfYouHaveMoney());
    }

    /**
     * Tests the money verification logic when the user does not have enough money for the selected station type.
     */
    @Test
    void testVerifyIfYouHaveMoney_NotEnoughMoney() {
        simulationFake.setActualMoney(100);
        controller.setSimulation(simulationFake);
        controller.setStationType(StationType.TERMINAL);
        assertFalse(controller.verifyIfYouHaveMoney());
    }

    /**
     * Tests that the controller returns the correct radius for each station type.
     */
    @Test
    void testGetStationTypeRadius() {
        assertEquals(3, controller.getStationTypeRadius(StationType.DEPOT));
        assertEquals(4, controller.getStationTypeRadius(StationType.STATION));
        assertEquals(5, controller.getStationTypeRadius(StationType.TERMINAL));
    }

    /**
     * Tests that the controller returns the correct price for each station type.
     */
    @Test
    void testGetStationTypePrice() {
        assertEquals(50, controller.getStationTypePrice(StationType.DEPOT));
        assertEquals(100, controller.getStationTypePrice(StationType.STATION));
        assertEquals(200, controller.getStationTypePrice(StationType.TERMINAL));
    }
}
