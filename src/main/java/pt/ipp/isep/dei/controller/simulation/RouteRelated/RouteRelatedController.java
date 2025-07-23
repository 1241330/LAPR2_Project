package pt.ipp.isep.dei.controller.simulation.RouteRelated;

import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for managing route-related operations in a simulation.
 * Provides accessors for the simulation context.
 */
public class RouteRelatedController {

    /**
     * The simulation context for which the controller operates.
     */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public RouteRelatedController(){

    }

    /**
     * Gets the current simulation.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation context.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}
