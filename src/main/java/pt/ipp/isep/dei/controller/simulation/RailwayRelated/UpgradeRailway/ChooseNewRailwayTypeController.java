package pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for managing the selection and upgrade of railway line types in the simulation.
 */
public class ChooseNewRailwayTypeController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * The railway line to be upgraded.
     */
    private RailwayLine railwayLine;

    /**
     * Default constructor.
     */
    public ChooseNewRailwayTypeController() {
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
     * Gets the current railway line.
     *
     * @return the railway line
     */
    public RailwayLine getRailwayLine() {
        return railwayLine;
    }

    /**
     * Sets the railway line to be upgraded.
     *
     * @param railwayLine the railway line
     */
    public void setRailwayLine(RailwayLine railwayLine) {
        this.railwayLine = railwayLine;
    }

    /**
     * Gets the cost of a single electrified railway line as a string.
     *
     * @return the cost as a string
     */
    public String getSEPrice() {
        return RailwayLineType.SINGLE_ELECTRIFIED.getCost() + "";
    }

    /**
     * Gets the cost of a double non-electrified railway line as a string.
     *
     * @return the cost as a string
     */
    public String getDNEPrice() {
        return RailwayLineType.DOUBLE_NON_ELECTRIFIED.getCost() + "";
    }

    /**
     * Gets the cost of a double electrified railway line as a string.
     *
     * @return the cost as a string
     */
    public String getDEPrice() {
        return RailwayLineType.DOUBLE_ELECTRIFIED.getCost() + "";
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

    /**
     * Calculates the upgrade cost for the railway line to a new railway type.
     *
     * @param newRailwayType the new railway line type
     * @return the upgrade cost
     */
    public int getUpgradeCost(RailwayLineType newRailwayType) {
        RailwayLineType oldRailwayType = railwayLine.getRailwayType();
        int costDifferencePerUnity = newRailwayType.getCost() - oldRailwayType.getCost();
        int upgradeCost = railwayLine.getPositionsRailwayLine().size() * costDifferencePerUnity;
        return upgradeCost;
    }

    /**
     * Verifies if the simulation has enough money to upgrade the railway line to the given type.
     *
     * @param railwayLineType the new railway line type
     * @return true if there is enough money, false otherwise
     */
    public boolean verifyIfHasMoney(RailwayLineType railwayLineType) {
        return getUpgradeCost(railwayLineType) <= getSimulation().getActualMoney();
    }
}