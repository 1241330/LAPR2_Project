package pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute;

import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for deactivating a route in a simulation.
 * Handles the removal of the assigned train and updates the route and train status.
 */
public class DeactivateRoutePOPController {

    /**
     * The route to be deactivated.
     */
    private Route route;
    /**
     * The simulation context in which the route is being deactivated.
     */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public DeactivateRoutePOPController(){

    }

    /**
     * Gets the route to be deactivated.
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the route to be deactivated.
     * @param route the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Gets the simulation context.
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

    /**
     * Deactivates the route and its assigned train, and removes the train assignment from the route.
     */
    public void deactivateRoute() {
        route.setActiveFlag(false);
        route.getAssignedTrain().setActiveFlag(false);
        route.setAssignedTrain(null);
    }
}
