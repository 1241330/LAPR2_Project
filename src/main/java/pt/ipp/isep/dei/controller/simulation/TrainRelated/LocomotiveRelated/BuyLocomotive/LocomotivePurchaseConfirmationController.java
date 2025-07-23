package pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive;

import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for confirming and processing the purchase of a locomotive in a simulation.
 */
public class LocomotivePurchaseConfirmationController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * The locomotive to be purchased.
     */
    private Locomotive locomotive;

    /**
     * Default constructor for LocomotivePurchaseConfirmationController.
     */
    public LocomotivePurchaseConfirmationController() {
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
     * Sets the locomotive to be purchased.
     *
     * @param locomotive the Locomotive object to set
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
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
     * Gets the locomotive to be purchased.
     *
     * @return the current Locomotive object
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Processes the purchase of the locomotive, updating the simulation's money and bought locomotives list.
     */
    public void buyLocomotive() {
        simulation.setActualMoney(simulation.getActualMoney() - locomotive.getAcquisitionPrice());
        simulation.addBoughtLocomotive(new Locomotive(locomotive.getName(), locomotive.getImagePath(), locomotive.getPower(), locomotive.getAcceleration(), locomotive.getTopSpeed(), locomotive.getStartYearOperation(),locomotive.getAcquisitionPrice(),locomotive.getFuelType(),locomotive.getMaxCarriages(), locomotive.getMaintenanceCost()));
    }
}
