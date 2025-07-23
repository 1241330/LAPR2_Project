package pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Train;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the selection of trains for a route in a simulation.
 * Provides methods to retrieve available trains based on route requirements and train status.
 */
public class ChooseTrainForRouteController {

    /**
     * The simulation context for which the controller operates.
     */
    private Simulation simulation;
    /**
     * The route for which a train is being selected.
     */
    private Route route;

    /**
     * Default constructor.
     */
    public ChooseTrainForRouteController(){}

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

    /**
     * Gets the current route.
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the route for which a train is being selected.
     * @param route the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Retrieves the list of available trains for the current route.
     * Only trains that are deactivated (not active) are considered.
     * If the route requires electric trains, only deactivated trains are returned.
     * Otherwise, only deactivated trains that are not electric are returned.
     *
     * @return observable list of available trains
     */
    public ObservableList<Train> getAvailableTrains() {
        List<Train> availableTrains = new ArrayList<>();
        List<Train> deactiveTrains = new ArrayList<>();
        List<Train> totalTrainList = simulation.getTrainList();

        for(Train train : totalTrainList){
            if (!train.getActiveFlag()){
                deactiveTrains.add(train);
            }
        }

        if (route.getElectricFlag()){
            return FXCollections.observableArrayList(deactiveTrains);
        }

        for (Train train : deactiveTrains){
            if (train.getLocomotive().getFuelType() != FuelType.ELECTRICITY){
                availableTrains.add(train);
            }
        }

        return FXCollections.observableArrayList(availableTrains);
    }
}

