package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for listing all acquired carriages that are not currently in use
 * for a given simulation and locomotive.
 */
public class ListCarriageController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * The locomotive for which carriages are being listed.
     */
    private Locomotive locomotive;

    /**
     * Default constructor for ListCarriageController.
     */
    public ListCarriageController() {}

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
     * Gets the current locomotive instance.
     *
     * @return the current Locomotive object
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Sets the locomotive for which carriages are being listed.
     *
     * @param locomotive the Locomotive object to set
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    /**
     * Retrieves the list of acquired carriages that are not currently in use as an observable list.
     *
     * @return ObservableList of available Carriage objects
     */
    public ObservableList<Carriage> getAcquiredCarriagesList(){
        List<Carriage> carriages = simulation.getBoughtCarriages();
        List<Carriage> availableCarriages = new ArrayList<>();

        for (Carriage carriage : carriages){
            if (!carriage.getInUse()){
                availableCarriages.add(carriage);
            }
        }
        return FXCollections.observableArrayList(availableCarriages);
    }
}
