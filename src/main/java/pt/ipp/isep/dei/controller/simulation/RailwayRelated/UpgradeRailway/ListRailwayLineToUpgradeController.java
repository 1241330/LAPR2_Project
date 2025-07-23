package pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for managing and providing the list of railway lines
 * available for upgrade in the simulation.
 */
public class ListRailwayLineToUpgradeController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public ListRailwayLineToUpgradeController() {
        // No-args constructor
    }

    /**
     * Gets the current simulation instance.
     *
     * @return the simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance to be used by this controller.
     *
     * @param simulation the simulation instance
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Retrieves the list of railway lines present in the simulation map.
     *
     * @return an observable list of railway lines
     */
    public ObservableList<RailwayLine> getRailwayList() {
        return FXCollections.observableArrayList(simulation.getMap().getRailwayLines());
    }
}