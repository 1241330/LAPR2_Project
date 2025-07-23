package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.ViewCarriages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;

import java.util.List;

/**
 * Controller responsible for listing all carriages bought in a simulation.
 */
public class ListBoughtCarriageController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for ListBoughtCarriageController.
     */
    public ListBoughtCarriageController() {}

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
     * Retrieves the list of carriages bought in the simulation as an observable list.
     *
     * @return ObservableList of bought Carriage objects
     */
    public ObservableList<Carriage> getCarriagesList(){
        List<Carriage> carriages = simulation.getBoughtCarriages();
        return FXCollections.observableArrayList(carriages);
    }
}
