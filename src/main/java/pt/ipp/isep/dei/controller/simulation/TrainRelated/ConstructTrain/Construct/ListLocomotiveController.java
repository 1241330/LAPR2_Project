package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for listing all acquired locomotives that are not currently in use
 * for a given simulation.
 */
public class ListLocomotiveController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for ListLocomotiveController.
     */
    public ListLocomotiveController() {}

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
     * Retrieves the list of acquired locomotives that are not currently in use as an observable list.
     *
     * @return ObservableList of available Locomotive objects
     */
    public ObservableList<Locomotive> getLocomotivesList(){
        List<Locomotive> locomotives = simulation.getAcquiredLocomotives();
        List<Locomotive> availableLocomotive = new ArrayList<>();

        for (Locomotive locomotive : locomotives){
            if (!locomotive.getInUse()){
                availableLocomotive.add(locomotive);
            }
        }
        return FXCollections.observableArrayList(availableLocomotive);
    }
}
