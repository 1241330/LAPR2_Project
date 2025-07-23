package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.ui.console.utils.Utils;

/**
 * Controller responsible for handling carriage-related operations within a simulation.
 */
public class CarriageRelatedController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for CarriageRelatedController.
     */
    public CarriageRelatedController() {
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
