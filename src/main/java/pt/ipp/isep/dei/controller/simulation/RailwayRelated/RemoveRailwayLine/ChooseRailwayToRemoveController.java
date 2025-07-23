package pt.ipp.isep.dei.controller.simulation.RailwayRelated.RemoveRailwayLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for managing the selection and removal of railway lines in the simulation.
 */
public class ChooseRailwayToRemoveController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor.
     */
    public ChooseRailwayToRemoveController() {
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

    /**
     * Returns a string representation of the two stations connected by the given railway line.
     *
     * @param railwayLine the railway line
     * @return a string in the format "Station1 ⇆ Station2"
     */
    public String getStations(RailwayLine railwayLine) {
        return railwayLine.getStation1().getName() + " ⇆ " + railwayLine.getStation2().getName();
    }
}