package pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for handling the confirmation of a railway line upgrade.
 * It manages the simulation, the selected railway line, the new railway line type,
 * and the upgrade cost. It also applies the upgrade and updates the simulation's money.
 */
public class UpgradeConfirmationPopController {

    private Simulation simulation;
    private RailwayLine railwayLine;
    private RailwayLineType newRailwayLineType;
    private int upgradeCost;

    /**
     * Default constructor.
     */
    public UpgradeConfirmationPopController() {
    }

    /**
     * Gets the current simulation.
     *
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation.
     *
     * @param simulation the simulation to set
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
     * Sets the railway line.
     *
     * @param railwayLine the railway line to set
     */
    public void setRailwayLine(RailwayLine railwayLine) {
        this.railwayLine = railwayLine;
    }

    /**
     * Gets the new railway line type to upgrade to.
     *
     * @return the new railway line type
     */
    public RailwayLineType getNewRailwayLineType() {
        return newRailwayLineType;
    }

    /**
     * Sets the new railway line type to upgrade to.
     *
     * @param newRailwayLineType the new railway line type to set
     */
    public void setNewRailwayLineType(RailwayLineType newRailwayLineType) {
        this.newRailwayLineType = newRailwayLineType;
    }

    /**
     * Gets the cost of the upgrade.
     *
     * @return the upgrade cost
     */
    public int getUpgradeCost() {
        return upgradeCost;
    }

    /**
     * Sets the cost of the upgrade.
     *
     * @param upgradeCost the upgrade cost to set
     */
    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    /**
     * Applies the upgrade to the railway line by setting its new type and
     * deducting the upgrade cost from the simulation's available money.
     */
    public void modifyRailwayLineType() {
        railwayLine.setTypeEnum(newRailwayLineType);
        simulation.setActualMoney(simulation.getActualMoney() - upgradeCost);
    }
}