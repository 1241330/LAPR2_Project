package pt.ipp.isep.dei.ui.console.map;

import pt.ipp.isep.dei.controller.map.DeleteMapController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for handling the deletion of a map.
 * Presents a confirmation prompt to the user before deleting the selected map.
 */
public class DeleteMapUI implements Runnable {

    private final DeleteMapController controller;

    /**
     * Constructs a DeleteMapUI instance with the specified map to delete.
     *
     * @param actualMap the map to be deleted
     */
    public DeleteMapUI(Map actualMap) {
        controller = new DeleteMapController();
        controller.setActualMap(actualMap);
    }

    /**
     * Runs the UI logic for deleting a map.
     * Prompts the user for confirmation before proceeding with deletion.
     */
    @Override
    public void run() {
        try {
            String mapName = controller.getNameMap();
            if (mapName == null) {
                throw new IllegalStateException("No map selected for deletion");
            }
            List<String> options = new ArrayList<>();
            options.add("Yes");
            Utils.printMessage("< If you delete the map, its corresponding scenarios and simulations will also be deleted too >");
            String question = "Do you want to delete it?";
            int confirmation = Utils.chooseOptionPrintMenuAndManualReturn(question, options, "No", "Choice");
            if (confirmation != 0) {
                controller.deleteMap();
                Utils.printMessage("< Deleted " + mapName + " >");
            }
        } catch (IllegalStateException e) {
            Utils.printMessage("Deletion error: " + e.getMessage());
        }
    }
}