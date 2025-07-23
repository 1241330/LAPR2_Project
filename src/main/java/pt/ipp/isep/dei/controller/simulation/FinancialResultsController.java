package pt.ipp.isep.dei.controller.simulation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.FinancialResult.YearFinancialResult;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for handling financial results operations within a simulation.
 */
public class FinancialResultsController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for FinancialResultsController.
     */
    public FinancialResultsController() {
        // Default constructor
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
     * Sets the simulation instance for this controller.
     *
     * @param simulation the Simulation object to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Retrieves the list of financial results for each year in the simulation as an observable list.
     *
     * @return ObservableList of YearFinancialResult objects
     */
    public ObservableList<YearFinancialResult> getFinancialResults() {
        return FXCollections.observableArrayList(simulation.getFinancialResults());
    }
}
