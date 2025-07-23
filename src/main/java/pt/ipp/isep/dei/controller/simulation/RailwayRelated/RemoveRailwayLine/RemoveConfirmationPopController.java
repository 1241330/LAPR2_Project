package pt.ipp.isep.dei.controller.simulation.RailwayRelated.RemoveRailwayLine;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for handling the confirmation and removal of a railway line from the simulation.
 */
public class RemoveConfirmationPopController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * The railway line to be removed.
     */
    private RailwayLine railwayLine;

    /**
     * Default constructor.
     */
    public RemoveConfirmationPopController() {
        // No-args constructor
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
     * Sets the simulation instance to be used by this controller.
     *
     * @param simulation the simulation instance
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the railway line to be removed.
     *
     * @return the railway line
     */
    public RailwayLine getRailwayLine() {
        return railwayLine;
    }

    /**
     * Sets the railway line to be removed.
     *
     * @param railwayLine the railway line
     */
    public void setRailwayLine(RailwayLine railwayLine) {
        this.railwayLine = railwayLine;
    }

    /**
     * Removes the specified railway line from the simulation.
     */
    public void removeRailwayLine() {
        simulation.removeRailwayLine(railwayLine);
    }
}