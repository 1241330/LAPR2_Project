package pt.ipp.isep.dei.ui.console.map;

import pt.ipp.isep.dei.controller.map.ShowMapForUIController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.ui.console.utils.Utils;

/**
 * UI class responsible for displaying a map.
 */
public class ShowMapUI implements Runnable {

    /** Controller responsible for map operations. */
    private final ShowMapForUIController controller;

    /**
     * Constructs a ShowMapUI with the specified map.
     *
     * @param actualMap the map to be displayed
     */
    public ShowMapUI(Map actualMap) {
        controller = new ShowMapForUIController();
        controller.setActualMap(actualMap);
    }

    /**
     * Runs the UI logic to display the map.
     * If no map is selected, throws an IllegalArgumentException.
     */
    @Override
    public void run() {
        if (controller.getActualMap() != null) {
            Utils.printMap(controller.getActualMap());
        } else {
            throw new IllegalArgumentException("No map selected");
        }
    }

    /**
     * Gets the controller responsible for map operations.
     *
     * @return the ShowMapController instance
     */
    public ShowMapForUIController getController() {
        return controller;
    }
}