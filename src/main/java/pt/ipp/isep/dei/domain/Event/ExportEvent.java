package pt.ipp.isep.dei.domain.Event;

import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Station.StationAssociations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an export event that removes a specific type of resource from a station's inventory at regular intervals.
 */
public class ExportEvent extends Event {

    /** The association (station or house block) from which resources will be exported. */
    private StationAssociations association;

    /** The type of resource to be exported. */
    private ResourcesType resourceType;

    /** Flag to indicate if the export menu should be printed first. */
    private static boolean printFirstMenu;

    /**
     * Constructs an ExportEvent with the specified parameters.
     *
     * @param name         the name of the event
     * @param interval     the interval between event generations
     * @param actualDate   the current date (used to calculate the next generation date)
     * @param resourceType the type of resource to export
     * @param association  the association from which to export resources
     */
    public ExportEvent(String name, int interval, int actualDate, ResourcesType resourceType, StationAssociations association) {
        super(name, interval, actualDate);
        this.resourceType = resourceType;
        this.association = association;
        printFirstMenu = true;
    }

    /**
     * Triggers the export event, removing the specified resource from the association's inventory if it exists.
     * Also prints export information to the console and updates the next generation date.
     *
     * @return a list of log messages generated by the export event
     */
    @Override
    public List<String> trigger() {
        List<String> newLogs = new ArrayList<>();
        int quantity = association.getResourceQuantity(resourceType);
        if (quantity == 0) {
            return newLogs; // No resource to export
        }
        association.removeResourceFromInventory(new Resource(resourceType, quantity));
        if (printFirstMenu) {
            newLogs.add(" ");
            newLogs.add("📦  Export:");
            printFirstMenu = false;
        }
        newLogs.add(String.format("   ➜  %-4d × %-12s", quantity, resourceType.getName()));
        association.setUpdatedInventory(true);
        this.setNextGenerationDate(getNextGenerationDate() + getInterval());
        return newLogs;
    }

    /**
     * Gets the association from which resources are exported.
     *
     * @return the association
     */
    public StationAssociations getAssociation() {
        return association;
    }

    /**
     * Sets the association from which resources are exported.
     *
     * @param association the association to set
     */
    public void setAssociation(StationAssociations association) {
        this.association = association;
    }

    /**
     * Gets the type of resource to be exported.
     *
     * @return the resource type
     */
    public ResourcesType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the type of resource to be exported.
     *
     * @param resourceType the resource type to set
     */
    public void setResourceType(ResourcesType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * Checks if the export menu should be printed first.
     *
     * @return true if the menu should be printed, false otherwise
     */
    public static boolean isPrintFirstMenu() {
        return printFirstMenu;
    }

    /**
     * Sets whether the export menu should be printed first.
     *
     * @param printFirstMenu the value to set
     */
    public static void setPrintFirstMenu(boolean printFirstMenu) {
        ExportEvent.printFirstMenu = printFirstMenu;
    }
}