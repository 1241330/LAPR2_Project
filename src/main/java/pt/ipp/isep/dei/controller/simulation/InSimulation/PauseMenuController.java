package pt.ipp.isep.dei.controller.simulation.InSimulation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.ui.console.utils.Utils;

/**
 * Controller responsible for managing the pause menu during a simulation.
 * Provides access to the current simulation, its date, and available money.
 */
public class PauseMenuController {

    private Simulation simulation;

    /**
     * Constructs a PauseMenuController.
     */
    public PauseMenuController() {
    }

    /**
     * Sets the current simulation.
     *
     * @param simulation the simulation to be set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Returns the current simulation.
     *
     * @return the current {@link Simulation}
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Returns the current date of the simulation as a string.
     *
     * @return the current date in string format
     */
    public String getActualDate() {
        TimeDate date = Utils.convertToDate(simulation.getCurrentTime());
        return date.toString();
    }

    /**
     * Returns the current amount of money in the simulation as a string.
     *
     * @return the current money in string format
     */
    public String getActualMoney() {
        String actualMoney = String.valueOf(simulation.getActualMoney());
        return actualMoney;
    }
}