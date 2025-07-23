package pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.ViewLocomotive;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;

import java.util.List;

/**
 * Controller responsible for listing all acquired locomotives in a simulation.
 */
public class ListBoughtLocomotiveController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for ListBoughtLocomotiveController.
     */
    public ListBoughtLocomotiveController() {}

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
     * Retrieves the list of acquired locomotives in the simulation as an observable list.
     *
     * @return ObservableList of acquired Locomotive objects
     */
    public ObservableList<Locomotive> getLocomotivesList(){
        List<Locomotive> locomotive = simulation.getAcquiredLocomotives();
        return FXCollections.observableArrayList(locomotive);
    }
}
