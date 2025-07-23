package pt.ipp.isep.dei.controller.simulation.RailwayRelated;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.ui.console.utils.Utils;

/**
 * Controller responsible for handling railway-related operations in the simulation.
 */
public class RailwayRelatedController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public RailwayRelatedController() {
    }

    /**
     * Gets the current simulation instance.
     *
     * @return the simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the current date of the simulation as a string.
     *
     * @return the current date in string format
     */
    public String getActualDate() {
        TimeDate date = Utils.convertToDate(simulation.getCurrentTime());
        return date.toString();
    }

    /**
     * Gets the current amount of money in the simulation as a string.
     *
     * @return the current money as a string
     */
    public String getActualMoney() {
        String actualMoney = String.valueOf(simulation.getActualMoney());
        return actualMoney;
    }
}