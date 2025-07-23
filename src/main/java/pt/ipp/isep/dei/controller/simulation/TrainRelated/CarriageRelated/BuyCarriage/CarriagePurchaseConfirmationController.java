package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.BuyCarriage;

import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for confirming and processing the purchase of a carriage in a simulation.
 */
public class CarriagePurchaseConfirmationController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * The carriage to be purchased.
     */
    private Carriage carriage;

    /**
     * Default constructor for CarriagePurchaseConfirmationController.
     */
    public CarriagePurchaseConfirmationController() {
        // Default constructor
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
     * Sets the carriage to be purchased.
     *
     * @param carriage the Carriage object to set
     */
    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    /**
     * Gets the current simulation instance.
     *
     * @return the current Simulation object
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Gets the carriage to be purchased.
     *
     * @return the current Carriage object
     */
    public Carriage getCarriage() {
        return carriage;
    }

    /**
     * Processes the purchase of the carriage, updating the simulation's money and bought carriages list.
     * Also sets the carriage as not in use.
     */
    public void buyCarriage() {
        simulation.setActualMoney(simulation.getActualMoney() - carriage.getAcquisitionCost());
        simulation.addBoughtCarriages(new Carriage(carriage.getName(), carriage.getImagePath(), carriage.getStartYearOperation(), carriage.getAcquisitionCost(), carriage.getMaxResourceCapacity()));
        carriage.setInUse(false);
    }
}
