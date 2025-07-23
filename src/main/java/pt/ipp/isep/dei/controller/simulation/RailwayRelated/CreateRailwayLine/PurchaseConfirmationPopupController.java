package pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.List;

/**
 * Controller responsible for handling the purchase confirmation popup logic
 * when creating a new railway line in the simulation.
 */
public class PurchaseConfirmationPopupController {

    private Simulation simulation;
    private List<Position> path;
    private Station departureStation;
    private Station arrivalStation;
    private RailwayLineType type;
    private int cost;

    /**
     * Default constructor.
     */
    public PurchaseConfirmationPopupController() {}

    /**
     * Gets the current simulation.
     *
     * @return the simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the path of the railway line.
     *
     * @return the list of positions representing the path
     */
    public List<Position> getPath() {
        return path;
    }

    /**
     * Sets the path of the railway line.
     *
     * @param path the list of positions to set
     */
    public void setPath(List<Position> path) {
        this.path = path;
    }

    /**
     * Gets the departure station.
     *
     * @return the departure station
     */
    public Station getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the departure station.
     *
     * @param departureStation the departure station to set
     */
    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    /**
     * Gets the arrival station.
     *
     * @return the arrival station
     */
    public Station getArrivalStation() {
        return arrivalStation;
    }

    /**
     * Sets the arrival station.
     *
     * @param arrivalStation the arrival station to set
     */
    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    /**
     * Gets the railway line type.
     *
     * @return the railway line type
     */
    public RailwayLineType getType() {
        return type;
    }

    /**
     * Sets the railway line type.
     *
     * @param type the railway line type to set
     */
    public void setType(RailwayLineType type) {
        this.type = type;
    }

    /**
     * Gets the cost of the railway line.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of the railway line.
     *
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Adds a new railway line to the simulation, deducting the cost from the simulation's budget.
     */
    public void addRailwayLine() {
        RailwayLine line = new RailwayLine(path, departureStation, arrivalStation, type, Utils.convertToDate(simulation.getCurrentTime()));
        simulation.setActualMoney(simulation.getActualMoney() - cost);
        simulation.addRailwayLine(line);
    }
}