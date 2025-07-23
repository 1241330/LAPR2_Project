package pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.BuyCarriage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.repository.CarriageRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for handling the logic related to buying carriages in a simulation.
 */
public class BuyCarriageController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for BuyCarriageController.
     */
    public BuyCarriageController() {}

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
     * Retrieves the list of available carriages for the current simulation date as an observable list.
     *
     * @return ObservableList of available Carriage objects
     */
    public ObservableList<Carriage> getCarriagesList(){
        List<Carriage> carriages = simulation.getAvailableDateCarriages();
        return FXCollections.observableArrayList(carriages);
    }

    /**
     * Checks if there is not enough money in the simulation to buy the selected carriage.
     *
     * @param selectedItem the selected carriage object
     * @return true if there is not enough money, false otherwise
     */
    public boolean notEnoughMoney(Object selectedItem) {
        Carriage carriage = (Carriage) selectedItem;
        return simulation.getActualMoney() < carriage.getAcquisitionCost();
    }
}
