package pt.ipp.isep.dei.ui.console.map;

import pt.ipp.isep.dei.controller.map.SaveMapController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SaveMapUI implements Runnable {

    private SaveMapController controller;
    private boolean newMap;

    /**
     * Constructor for SaveMapUI.
     * @param actualMap the map to be saved or updated
     * @param newMap true if it is a new map, false if editing an existing map
     */
    public SaveMapUI(Map actualMap, boolean newMap) {
        controller = new SaveMapController(actualMap);
        this.newMap = newMap;
    }

    /**
     * Runs the UI logic, prompting the user to save or discard the map or changes.
     */
    @Override
    public void run() {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Yes");
            String question;
            if (newMap) {
                question = "Do you want to save the new map?";
            } else {
                question = "Do you want to save the changes made to the map?";
            }
            int option = Utils.chooseOptionPrintMenuAndManualReturn(question, options, "No", "Choose");
            String messageSaved;
            String messageNotSaved;
            if (newMap) {
                messageSaved = "New map successfully saved!";
                messageNotSaved = "New map not saved";
            } else {
                messageSaved = "Map changes successfully saved!";
                messageNotSaved = "Changes to the map not saved";
            }
            switch (option) {
                case 1:
                    controller.saveMap();
                    Utils.printMessage("< " + messageSaved + " >");
                    return;
                case 0:
                    controller.dontSaveMap();
                    Utils.printMessage("< " + messageNotSaved + " >");
                    return;
            }
        }
    }
}

