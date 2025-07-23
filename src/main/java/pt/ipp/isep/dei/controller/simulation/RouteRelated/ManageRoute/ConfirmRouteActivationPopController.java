package pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute;

import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Train;

/**
 * Controller responsible for confirming the activation of a route in a simulation.
 * Handles the assignment and activation of a train to a route.
 */
public class ConfirmRouteActivationPopController {

    /**
     * The route to be activated.
     */
    private Route route;
    /**
     * The simulation context in which the route is being activated.
     */
    private Simulation simulation;
    /**
     * The train selected to be assigned to the route.
     */
    private Train selectedTrain;

    /**
     * Default constructor.
     */
    public ConfirmRouteActivationPopController(){

    }

    /**
     * Gets the route to be activated.
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the route to be activated.
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
     * Gets the selected train to be assigned to the route.
     * @return the selected train
     */
    public Train getSelectedTrain() {
        return selectedTrain;
    }

    /**
     * Sets the train to be assigned to the route.
     * @param selectedTrain the train to set
     */
    public void setTrain(Train selectedTrain) {
        this.selectedTrain = selectedTrain;
    }

    /**
     * Activates the route and assigns the selected train to it.
     * Sets both the route and the train as active.
     */
    public void activateRoute() {
        route.setActiveFlag(true);
        selectedTrain.setActiveFlag(true);
        route.setAssignedTrain(selectedTrain);
    }
}
