package pt.ipp.isep.dei.controller.simulation.StationRelated;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.ui.console.utils.Utils;

/**
 * Controller responsible for handling station-related operations within a simulation.
 * Provides methods to set and get the simulation, retrieve the current simulation date,
 * and obtain the current money value.
 */
public class StationRelatedController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for StationRelatedController.
     */
    public StationRelatedController() {
        // Default constructor
    }

    /**
     * Gets the current simulation instance.
     *
     * @return the current Simulation object
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance for this controller.
     *
     * @param simulation the Simulation object to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Retrieves the actual date of the simulation as a string.
     *
     * @return the current date in string format
     */
    public String getActualDate() {
        TimeDate date = Utils.convertToDate(simulation.getCurrentTime());
        return date.toString();
    }

    /**
     * Retrieves the actual money value of the simulation as a string.
     *
     * @return the current money value in string format
     */
    public String getActualMoney() {
        String actualMoney = String.valueOf(simulation.getActualMoney());
        return actualMoney;
    }
}