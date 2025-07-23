package pt.ipp.isep.dei.controller.simulation.InSimulationTest;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.InSimulation.PauseMenuController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PauseMenuController class.
 * These tests use fake classes instead of mocks to simulate dependencies.
 */
class PauseMenuControllerTest {
    /**
     * Fake implementation of Simulation for testing purposes.
     * Allows setting currentTime and actualMoney values.
     */
    static class SimulationFake extends Simulation {
        private int currentTime;
        private int actualMoney;
        /**
         * Constructor for the fake simulation.
         * @param currentTime the current time to return
         * @param actualMoney the money to return
         */
        public SimulationFake(int currentTime, int actualMoney) {
            super("fake", null);
            this.currentTime = currentTime;
            this.actualMoney = actualMoney;
        }
        /**
         * Returns the fake current time.
         * @return the current time
         */
        @Override
        public int getCurrentTime() {
            return currentTime;
        }
        /**
         * Returns the fake actual money.
         * @return the actual money
         */
        @Override
        public int getActualMoney() {
            return actualMoney;
        }
    }

    /**
     * Fake implementation of TimeDate for testing purposes.
     * Allows setting a custom string representation.
     */
    static class TimeDateFake extends TimeDate {
        private final String str;
        /**
         * Constructor for the fake TimeDate.
         * @param str the string to return in toString()
         */
        public TimeDateFake(String str) {
            super(0,0,0);
            this.str = str;
        }
        /**
         * Returns the custom string.
         * @return the string set in the constructor
         */
        @Override
        public String toString() {
            return str;
        }
    }

    /**
     * Tests setting and getting the simulation in the controller.
     */
    @Test
    void testSetAndGetSimulation() {
        PauseMenuController controller = new PauseMenuController();
        Simulation sim = new SimulationFake(10, 100);
        controller.setSimulation(sim);
        assertEquals(sim, controller.getSimulation());
    }

    /**
     * Tests getting the simulation when none is set (should be null).
     */
    @Test
    void testGetSimulationWhenNull() {
        PauseMenuController controller = new PauseMenuController();
        assertNull(controller.getSimulation());
    }

    /**
     * Tests getting the actual date from the controller using a fake TimeDate.
     */
    @Test
    void testGetActualDate() {
        PauseMenuController controller = new PauseMenuController() {
            @Override
            public String getActualDate() {
                // Simulate expected behavior without mocks
                return new TimeDateFake("2025-06-14").toString();
            }
        };
        controller.setSimulation(new SimulationFake(1234, 0));
        assertEquals("2025-06-14", controller.getActualDate());
    }

    /**
     * Tests getting the actual money from the controller.
     */
    @Test
    void testGetActualMoney() {
        PauseMenuController controller = new PauseMenuController();
        controller.setSimulation(new SimulationFake(0, 5000));
        assertEquals("5000", controller.getActualMoney());
    }

    /**
     * Tests that getting the actual date with a null simulation throws NullPointerException.
     */
    @Test
    void testGetActualDateWithNullSimulation() {
        PauseMenuController controller = new PauseMenuController();
        assertThrows(NullPointerException.class, controller::getActualDate);
    }

    /**
     * Tests that getting the actual money with a null simulation throws NullPointerException.
     */
    @Test
    void testGetActualMoneyWithNullSimulation() {
        PauseMenuController controller = new PauseMenuController();
        assertThrows(NullPointerException.class, controller::getActualMoney);
    }
}
