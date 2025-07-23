package pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute;

import pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway.ChooseNewRailwayTypeController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for managing the selection of the railway type for a route in a simulation.
 * Provides accessors for the simulation context.
 */
public class ChooseRailwayTypeForRouteController {

    /**
     * The simulation context for which the controller operates.
     */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public ChooseRailwayTypeForRouteController() {
        // Default constructor
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
