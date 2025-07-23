package pt.ipp.isep.dei.controller.simulation.RailwayRelated.ShowRailwayList;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for providing details about a specific railway line in the simulation.
 */
public class ViewDetailsRailwayController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * The railway line whose details are to be displayed.
     */
    private RailwayLine railwayLine;

    /**
     * Default constructor.
     */
    public ViewDetailsRailwayController() {
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
     * Gets the railway line whose details are to be displayed.
     *
     * @return the railway line
     */
    public RailwayLine getRailwayLine() {
        return railwayLine;
    }

    /**
     * Sets the railway line whose details are to be displayed.
     *
     * @param railwayLine the railway line
     */
    public void setRailwayLine(RailwayLine railwayLine) {
        this.railwayLine = railwayLine;
    }

    /**
     * Gets the name of the departure station of the railway line.
     *
     * @return the name of the departure station
     */
    public String getDepartureStation() {
        return railwayLine.getStation1().getName();
    }

    /**
     * Gets the name of the arrival station of the railway line.
     *
     * @return the name of the arrival station
     */
    public String getArrivalStation() {
        return railwayLine.getStation2().getName();
    }

    /**
     * Gets the type of the railway line.
     *
     * @return the railway type as a string
     */
    public String getRailwayType() {
        return railwayLine.getRailwayType().name();
    }

    /**
     * Gets the size of the railway line, represented by the number of positions.
     *
     * @return the size of the railway line as a string
     */
    public String getRailwaySize() {
        return String.valueOf(railwayLine.getPositionsRailwayLine().size());
    }
}