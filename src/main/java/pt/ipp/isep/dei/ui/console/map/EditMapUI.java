package pt.ipp.isep.dei.ui.console.map;

import pt.ipp.isep.dei.controller.map.EditMapController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.ui.console.city.AddCityUI;
import pt.ipp.isep.dei.ui.console.city.DeleteCityUI;
import pt.ipp.isep.dei.ui.console.city.ListCityUI;
import pt.ipp.isep.dei.ui.console.industry.AddIndustryUI;
import pt.ipp.isep.dei.ui.console.industry.DeleteIndustryUI;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for editing a map.
 * Allows editing the map's name and managing its buildings (industries and cities).
 */
public class EditMapUI implements Runnable {

    private final EditMapController controller;
    private String oldName;
    private String oldSize;
    private boolean setNewNameMap;
    private boolean setNewSizeMap;
    private String newNameMap;
    private int newWidthMap;
    private int newHeightMap;
    private static final int MAX_SIZE = 256;

    /**
     * Constructs an EditMapUI instance for the specified map.
     *
     * @param actualMap the map to be edited
     */
    public EditMapUI(Map actualMap) {
        controller = new EditMapController();
        controller.setActualMap(actualMap);
    }

    /**
     * Runs the UI logic for editing a map.
     */
    @Override
    public void run() {
        try {
            chooseWhatEdit();
        } catch (IllegalArgumentException e) {
            Utils.printMessage("Error: " + e.getMessage());
        } catch (Exception e) {
            Utils.printMessage("Unexpected error: " + e.getMessage());
        }
        new SaveMapUI(controller.getActualMap(),false).run();
    }

    /**
     * Presents the main edit options to the user (name or buildings).
     */
    private void chooseWhatEdit() {
        while (true) {
            try {
                List<MenuItem> options = new ArrayList<>();
                options.add(new MenuItem("Name", this::editName));
                options.add(new MenuItem("Buildings", this::buildingsDefinitions));
                int option = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Edit a Map",
                        Utils.convertObjectsToDescriptions(options),
                        "Return",
                        "Choice"
                );
                if (option == 0) {
                    return;
                }
                if (option < 1 || option > options.size()) {
                    throw new IllegalArgumentException("Invalid edit option");
                }
                options.get(option - 1).run();
            } catch (IllegalArgumentException e) {
                Utils.printMessage("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Handles the logic for editing the map's name.
     */
    private void editName() {
        try {
            System.out.println("\n╔═════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                           Edit a Map: name                          ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ - Only alphanumeric characters and underscores are allowed          ║");
            System.out.println("║ - No spaces, special characters, or accented letters are permitted  ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  0. Return                                                          ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════╝");

            newNameMap = Utils.readSimpleWord("New name: ").trim();

            if (newNameMap.equals("0")) return;
            if (newNameMap.trim().isEmpty()) throw new IllegalArgumentException("Map name cannot be empty");
            if (controller.alreadyExistNameMapInMapRepository(newNameMap)) {
                Utils.printMessage("< Map name already exists >");
            } else {
                String oldName = controller.getName();
                controller.setNewName(newNameMap);
                Utils.printMessage("< New name updated: " + oldName + " -> " + newNameMap + " >");
            }
        } catch (IllegalArgumentException e) {
            Utils.printMessage("Invalid name: " + e.getMessage());
        }
    }

    /**
     * Presents building management options (industry or city) to the user.
     */
    private void buildingsDefinitions() {
        while (true) {
            List<MenuItem> options = new ArrayList<>();
            options.add(new MenuItem("Industry", this::industryRelated));
            options.add(new MenuItem("City", this::cityRelated));
            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Buildings",
                    Utils.convertObjectsToDescriptions(options),
                    "Return",
                    "Choice"
            );
            if (option == 0) {
                return;
            }
            options.get(option - 1).run();
        }
    }

    /**
     * Presents industry-related operations (add or remove) to the user.
     */
    private void industryRelated() {
        do {
            List<MenuItem> options = new ArrayList<>();
            int mapId = controller.getIdMap();
            options.add(new MenuItem("Add Industry", new AddIndustryUI(mapId)));
            options.add(new MenuItem("Remove Industry", new DeleteIndustryUI(mapId)));
            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Industry Operations",
                    Utils.convertObjectsToDescriptions(options),
                    "Return",
                    "Choice"
            );
            if (option == 0) {
                return;
            }
            options.get(option - 1).run();
        } while (true);
    }

    /**
     * Presents city-related operations (add, list, or remove) to the user.
     */
    private void cityRelated() {
        do {
            List<MenuItem> options = new ArrayList<>();
            int mapId = controller.getIdMap();
            options.add(new MenuItem("Add City", new AddCityUI(mapId)));
            options.add(new MenuItem("List City", new ListCityUI(mapId)));
            options.add(new MenuItem("Remove City", new DeleteCityUI(mapId)));
            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "City Operations",
                    Utils.convertObjectsToDescriptions(options),
                    "Return",
                    "Choice"
            );
            if (option == 0) {
                return;
            }
            options.get(option - 1).run();
        } while (true);
    }

    /**
     * Gets the controller responsible for editing the map.
     *
     * @return the EditMapController instance
     */
    public EditMapController getController() {
        return controller;
    }
}