package pt.ipp.isep.dei.ui.console.industry;

import pt.ipp.isep.dei.controller.industry.DeleteIndustryController;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Console-based user interface for deleting industries from a map.
 * This class implements {@code Runnable} and is intended to be used as a menu option in a user interface.
 */
public class DeleteIndustryUI implements Runnable {
    private final DeleteIndustryController controller;
    private Industry actualIndustry;

    /**
     * Constructs a new {@code DeleteIndustryUI} for a specific map.
     *
     * @param mapId the ID of the map from which industries can be deleted
     */
    public DeleteIndustryUI(int mapId) {
        controller = new DeleteIndustryController(mapId);
    }

    /**
     * Runs the UI interaction for deleting an industry.
     * If there are active industries on the map, prompts the user to select one for deletion.
     */
    @Override
    public void run() {
        if (controller.thereAreActiveIndustries()) {
            chooseIndustry();
        } else {
            Utils.printMessage("< No active industries found >");
        }
    }

    /**
     * Displays a list of active industries and prompts the user to select one.
     * Proceeds to confirmation and deletes the selected industry if confirmed.
     */
    private void chooseIndustry() {
        List<Industry> industriesList = controller.getActualIndustries();
        List<String> industriesNames = new ArrayList<>();
        for (Industry industry : industriesList) {
            industriesNames.add(industry.getName());
        }
        int option = Utils.chooseOptionPrintMenuAndManualReturn("List of industries", industriesNames, "Return", "Choice");
        if (option != 0) {
            actualIndustry = industriesList.get(option - 1);
            confirmationAndDeleteIndustry();
        }
    }

    /**
     * Prompts the user to confirm the deletion of the selected industry.
     * If confirmed, deletes the industry from the map and displays a confirmation message.
     */
    private void confirmationAndDeleteIndustry() {
        List<String> options = new ArrayList<>();
        options.add("Yes");
        int confirmation = Utils.chooseOptionPrintMenuAndManualReturn(
                "Delete " + actualIndustry.getName() + "?",
                options,
                "No",
                "Choice");
        if (confirmation != 0) {
            controller.deleteIndustryFromMap(actualIndustry);
            Utils.printMessage("< Industry " + actualIndustry.getName() + " removed from map >");
        }
    }
}
