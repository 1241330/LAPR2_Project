package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.List;

/**
 * Controller responsible for confirming and processing the purchase of a new train in a simulation.
 */
public class ConstructTrainPurchaseConfirmationPopupController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * The locomotive to be used in the new train.
     */
    private Locomotive locomotive;

    /**
     * The list of carriages to be attached to the new train.
     */
    private List<Carriage> carriages;

    /**
     * Default constructor for ConstructTrainPurchaseConfirmationPopupController.
     */
    public ConstructTrainPurchaseConfirmationPopupController() {}

    /**
     * Sets the simulation instance for this controller.
     *
     * @param simulation the Simulation object to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Sets the locomotive to be used in the new train.
     *
     * @param locomotive the Locomotive object to set
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    /**
     * Sets the list of carriages to be attached to the new train.
     *
     * @param carriages the list of Carriage objects to set
     */
    public void setCarriages(List<Carriage> carriages) {
        this.carriages = carriages;
    }

    /**
     * Confirms the creation of a new train, setting the locomotive and carriages as in use and adding the train to the simulation.
     */
    public void confirmTrainCreation() {
        locomotive.setInUse(true);
        for (Carriage carriage : carriages){
            carriage.setInUse(true);
        }
        Train newTrain = new Train(locomotive, carriages, Utils.convertToDate(simulation.getCurrentTime()));
        simulation.addTrain(newTrain);
    }

    /**
     * Gets the current simulation instance.
     *
     * @return the current Simulation object
     */
    public Simulation getSimulation() {
        return simulation;
    }
}
