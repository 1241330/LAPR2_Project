package pt.ipp.isep.dei.controller.simulation.RailwayRelated.ShowRailwayLine;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.ui.console.utils.Utils;

/**
 * Controller responsible for displaying the details of a RailwayLine on the map.
 */
public class ShowRailwayLineDetailsInMapController {

    /**
     * The railway line whose details are to be displayed.
     */
    private RailwayLine railwayLine;

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Sets the railway line to be displayed.
     *
     * @param railwayLine the railway line
     */
    public void setRailwayLine(RailwayLine railwayLine) {
        this.railwayLine = railwayLine;
    }

    /**
     * Gets the railway line being displayed.
     *
     * @return the railway line
     */
    public RailwayLine getRailwayLine() {
        return railwayLine;
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
     * Gets the simulation instance associated with this controller.
     *
     * @return the simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Gets the connection name of the railway line.
     *
     * @return the connection name
     */
    public String getConnectionName() {
        return railwayLine.getConnectionName();
    }

    /**
     * Gets the type of the railway line.
     *
     * @return the type of the railway line
     */
    public String getType() {
        return railwayLine.getRailwayType().getType();
    }

    /**
     * Gets the distance of the railway line in kilometers.
     *
     * @return the distance as a string in the format "X km"
     */
    public String getDistance() {
        return String.format("%d km", railwayLine.getPositionsRailwayLine().size());
    }

    /**
     * Gets the construction date of the railway line in the format "dd/MM/yyyy".
     *
     * @return the construction date as a string
     */
    public String getConstructionDate() {
        TimeDate constructionDate = railwayLine.getConstructionDate();
        return String.format("%02d/%02d/%04d",
                constructionDate.getDay(),
                constructionDate.getMonth(),
                constructionDate.getYear());
    }

    /**
     * Gets the next maintenance date of the railway line in the format "dd/MM/yyyy".
     * If the current date is before the construction date, returns "N/A".
     *
     * @return the next maintenance date as a string
     */
    public String getNextMaintenanceDate() {
        TimeDate actualDate = Utils.convertToDate(simulation.getCurrentTime());
        TimeDate constructionDate = railwayLine.getConstructionDate();

        // If the current date is before the construction date
        if (actualDate.before(constructionDate)) {
            return "N/A";
        }

        // Calculate the next maintenance date
        TimeDate nextMaintenanceDate = new TimeDate(constructionDate.getYear(), constructionDate.getMonth(), constructionDate.getDay());

        // Add years until the next maintenance date is after the current date
        while (nextMaintenanceDate.before(actualDate)) {
            nextMaintenanceDate.setYear(nextMaintenanceDate.getYear() + 1);
        }

        return String.format("%02d/%02d/%04d",
                nextMaintenanceDate.getDay(),
                nextMaintenanceDate.getMonth(),
                nextMaintenanceDate.getYear());
    }
}