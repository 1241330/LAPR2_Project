package pt.ipp.isep.dei.ui.console.city;

import pt.ipp.isep.dei.controller.city.AddCityController;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * User interface for adding a city to a map via console interaction.
 * Implements the Runnable interface to be executed as a menu option.
 */
public class AddCityUI implements Runnable {

    private AddCityController controller;
    private Map actualMap;

    /**
     * Constructs an AddCityUI instance with the specified map ID.
     * Initializes the controller for managing city addition.
     *
     * @param mapId The ID of the map where the city will be added.
     */
    public AddCityUI(int mapId) {
        this.controller = new AddCityController(mapId);
        this.actualMap = controller.getActualMap();
    }

    /**
     * Executes the process of adding a new city to the map.
     * Guides the user through entering the city name, position, and house blocks.
     * Handles errors and user cancellations.
     */
    @Override
    public void run() {
        boolean validCity;
        do {
            if (readCityName()) {
                return;
            }
            if (readCityPosition()) {
                return;
            }
            if (chooseAndReadHouseBlocks()) {
                return;
            }
            validCity = controller.addCityToMap();
            if (validCity) {
                Utils.printMessage("Add " + controller.getCityName() + " to the map");
            } else {
                Utils.printMessage("Don't add city to the map");
            }
            Utils.printMap(actualMap);
        } while (!validCity);
    }

    /**
     * Reads and validates the city name from user input.
     * Only alphabetic characters are allowed.
     *
     * @return true if the user cancels the operation, false otherwise.
     */
    private boolean readCityName() {
        System.out.println("\n╔═════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           Create a City: name                       ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ - Only alphabetic characters are allowed                            ║");
        System.out.println("║ - No special characters, or accented letters are permitted          ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  0. Cancel                                                          ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════╝");
        String cityName = Utils.validName("Name:");
        if (cityName.equals("0")) {
            return true;
        }
        controller.setCityName(cityName);
        return false;
    }

    /**
     * Reads and validates the city position from user input.
     * Ensures the position is not already occupied.
     *
     * @return true if the user cancels the operation, false otherwise.
     */
    private boolean readCityPosition() {
        Utils.chooseOptionPrintMenuAndManualReturn("Create a City: position", null, "Cancel", null);
        int xValue;
        int yValue;
        Position cityPosition;
        while (true) {
            xValue = Utils.readIntegerInRange("X:", 0, controller.getWidthMap());
            if (xValue == 0) {
                return true;
            }
            yValue = Utils.readIntegerInRange("Y:", 0, controller.getHeightMap());
            if (yValue == 0) {
                return true;
            }
            cityPosition = new Position(xValue - 1, yValue - 1);
            if (Utils.positionOccupiedList(cityPosition, controller.getOccupiedPositionsMap())) {
                Utils.printMessage("< Position already occupied >");
            } else {
                break;
            }
        }
        controller.setPositionCity(cityPosition);
        return false;
    }

    /**
     * Allows the user to choose between automatic or manual house block creation.
     * Handles user cancellation and errors.
     *
     * @return true if the user cancels the operation, false otherwise.
     */
    private boolean chooseAndReadHouseBlocks() {
        try {
            List<MenuItem> options = new ArrayList<>();
            options.add(new MenuItem("Automatic", this::readAutomaticHouseBlocks));
            options.add(new MenuItem("Manual", this::readManualHouseBlocks));
            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Create House Blocks",
                    Utils.convertObjectsToDescriptions(options),
                    "Cancel",
                    "Choice"
            );
            if (option == 0) {
                return true;
            }
            if (option < 1 || option > options.size()) {
                throw new IllegalArgumentException("Invalid edit option");
            }
            options.get(option - 1).run();
            if (controller.getHouseBlockList().isEmpty()) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            Utils.printMessage("Error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Reads the number of house blocks to create automatically.
     * Sets the house blocks in the controller.
     */
    private void readAutomaticHouseBlocks() {
        Utils.chooseOptionPrintMenuAndManualReturn("Create a City: nª house blocks", null, "Cancel", null);
        int value = Utils.readIntegerInRange("Quantity: ", 0, controller.getNumberFreePositions());
        if (value == 0) {
            controller.clearHouseBlockList();
            return;
        }
        controller.setNumberHouseBlocks(value);
        controller.setAutomaticHouseBlocks();
    }

    /**
     * Reads house block positions manually from user input.
     * Ensures each position is valid and not already occupied.
     * At least one house block is required.
     */
    private void readManualHouseBlocks() {
        Utils.chooseOptionPrintMenuAndManualReturn("Create a City: House Blocks", null, "Save", null);
        List<HouseBlock> houseBlocksList = new ArrayList<>();
        List<Position> occupiedPositionsMap = actualMap.getOccupiedPositions();
        while (true) {
            Utils.printMessage("New House Block");
            int xValue = Utils.readIntegerInRange("X:", 0, actualMap.getPixelSize().getWidth());
            if (xValue == 0) {
                if (houseBlocksList.isEmpty()) {
                    Utils.printMessage("< City must have at least one house block, city not created >");
                    controller.clearHouseBlockList();
                    return;
                }
                controller.setHouseBlockList(houseBlocksList);
                return;
            }
            int yValue = Utils.readIntegerInRange("Y:", 0, actualMap.getPixelSize().getHeight());
            if (yValue == 0) {
                if (houseBlocksList.isEmpty()) {
                    Utils.printMessage("< City must have at least one house block, city not created >");
                    controller.clearHouseBlockList();
                    return;
                }
                controller.setHouseBlockList(houseBlocksList);
                return;
            }
            Position newPosition = new Position(xValue - 1, yValue - 1);
            if (Utils.positionOccupiedList(newPosition, occupiedPositionsMap)) {
                Utils.printMessage("< Position already occupied >");
            } else {
                if (controller.cityFarFromTheCentre(newPosition)) {
                    Utils.printMessage("< New House Block significantly away from the City centre >");
                }
                houseBlocksList.add(new HouseBlock(newPosition, controller.getCityName()));
                occupiedPositionsMap.add(newPosition);
            }
        }
    }

    /**
     * Gets the controller for city addition.
     *
     * @return the AddCityController instance.
     */
    public AddCityController getController() {
        return controller;
    }

    /**
     * Sets the controller for city addition.
     *
     * @param controller the AddCityController to set.
     */
    public void setController(AddCityController controller) {
        this.controller = controller;
    }

    /**
     * Gets the actual map where the city is being added.
     *
     * @return the Map instance.
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the actual map where the city is being added.
     *
     * @param actualMap the Map to set.
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
    }
}