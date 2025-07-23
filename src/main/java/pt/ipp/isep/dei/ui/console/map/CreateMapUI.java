package pt.ipp.isep.dei.ui.console.map;

import pt.ipp.isep.dei.controller.map.CreateMapController;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for creating a new map.
 * Allows the user to define the map's name and size.
 */
public class CreateMapUI implements Runnable {

    private final CreateMapController controller;
    private boolean defaultNameMap;
    private boolean enterNameReturn;
    private static final int MAX_SIZE = 256;

    /**
     * Constructs a CreateMapUI instance.
     */
    public CreateMapUI() {
        controller = new CreateMapController();
    }

    /**
     * Runs the UI logic for creating a map.
     * Handles user input for map name and size.
     */
    @Override
    public void run() {
        try {
            if (selectNameMap()) {
                return;
            }
            if (defaultNameMap) {
                controller.createMapDefault();
            } else {
                controller.createMap();
            }
            Utils.printMessage("New map created: " + controller.getMapCreated());
        } catch (IllegalArgumentException e) {
            Utils.printMessage("Error creating map: " + e.getMessage());
        } catch (Exception e) {
            Utils.printMessage("Unexpected error: " + e.getMessage());
        }
        new SaveMapUI(controller.getMap(),true).run();
    }

    /**
     * Allows the user to select the map's name.
     * @return true if the user chooses to return, false otherwise
     */
    private boolean selectNameMap() {
        while (true) {
            try {
                defaultNameMap = false;
                enterNameReturn = false;
                List<MenuItem> options = new ArrayList<>();
                options.add(new MenuItem("Enter a name", this::enterTypeNameMap));
                options.add(new MenuItem("Default name", this::enterDefaultNameMap));
                int option = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Create a Map: name",
                        Utils.convertObjectsToDescriptions(options),
                        "Return",
                        "Choice"
                );
                if (option == 0) {
                    return true;
                } else {
                    options.get(option - 1).run();
                    if (enterNameReturn) {
                        continue;
                    }
                    if (selectSize()) {
                        continue;
                    }
                    return false;
                }
            } catch (IllegalArgumentException e) {
                Utils.printMessage("Invalid selection: " + e.getMessage());
            }
        }
    }

    /**
     * Allows the user to select the map's size.
     * @return true if the user chooses to return, false otherwise
     */
    private boolean selectSize() {
        try {
            Utils.chooseOptionPrintMenuAndManualReturn(
                    "Create a Map: enter a size",
                    null,
                    "Return",
                    null
            );
            int width = Utils.readIntegerInRange("Width: ", 0, MAX_SIZE);
            if (width == 0) {
                return true;
            }
            controller.setWidthMap(width);
            int height = Utils.readIntegerInRange("Height: ", 0, MAX_SIZE);
            if (height == 0) {
                return true;
            }
           controller.setHeightMap(height);
            return false;
        } catch (IllegalArgumentException e) {
            Utils.printMessage("Invalid size: " + e.getMessage());
            return true;
        }
    }

    /**
     * Handles the logic for entering a custom map name.
     */
    private void enterTypeNameMap() {
        while (true) {
            try {
                System.out.println("\n╔═════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                     Create a Map: enter a name                      ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ - Only alphanumeric characters and underscores are allowed          ║");
                System.out.println("║ - No spaces, special characters, or accented letters are permitted  ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
                System.out.println("║  0. Return                                                          ║");
                System.out.println("╚═════════════════════════════════════════════════════════════════════╝");
                String name = Utils.readSimpleWord("Name: ");
                if (name.equals("0")) {
                    enterNameReturn = true;
                    return;
                } else {
                    if (name.trim().isEmpty()) {
                        throw new IllegalArgumentException("Map name cannot be empty");
                    }
                    if (controller.alreadyExistNameMapInMapRepository(name)) {
                        Utils.printMessage("< Map name already exists >");
                    } else {
                        controller.setNameMap(name);
                        break;
                    }
                }
            } catch (IllegalArgumentException e) {
                Utils.printMessage("Invalid name: " + e.getMessage());
                enterNameReturn = true;
            }
        }
    }

    /**
     * Sets the flag to use a default map name.
     */
    private void enterDefaultNameMap() {
        defaultNameMap = true;
    }
}