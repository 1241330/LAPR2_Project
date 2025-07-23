package pt.ipp.isep.dei.ui.console.industry;

import pt.ipp.isep.dei.controller.industry.AddIndustryController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.ui.console.utils.Utils;
import java.util.List;
/**
 * User interface for adding new industries to a map through console interaction.
 * Implements the Runnable interface to be executed as a menu option.
 */
public class AddIndustryUI implements Runnable {
    private final AddIndustryController controller;

    /**
     * Constructs an AddIndustryUI instance with the specified map ID.
     * Initializes the controller for managing industry addition.
     *
     * @param mapId The ID of the map where industries will be added.
     */
    public AddIndustryUI(int mapId) {
        this.controller = new AddIndustryController(mapId);
    }

    /**
     * Displays a menu for entering the industry name and validates the input.
     * Only alphabetic characters are allowed, and special or accented characters are not permitted.
     *
     * @return A valid industry name or null if the user cancels the operation.
     */
    private String getIndustryName() {
        System.out.println("\n╔═════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              Industry Name                          ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ - Only alphabetic characters are allowed                            ║");
        System.out.println("║ - No special characters, or accented letters are permitted          ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  0. Cancel                                                          ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════╝");

        String name = Utils.validName("Name: ");
        if (name.equals("0")) {
            return null;
        }
        return name;
    }

    /**
     * Executes the process of adding a new industry to the map.
     * Allows the user to select an industry type, enter a name, and specify coordinates.
     * Handles errors such as invalid input or unavailable coordinates.
     */
    @Override
    public void run() {
        List<String> industryTypes = controller.getAllIndustryTypes();
        if (industryTypes.isEmpty()) {
            Utils.printMessage("No industry types available!");
            return;
        }

        int typeChoice = Utils.chooseOptionPrintMenuAndManualReturn(
                "Select Industry Type",
                industryTypes,
                "Return",
                "Choice");

        if (typeChoice == 0) return;

        String selectedType = industryTypes.get(typeChoice - 1);

        String industryName = getIndustryName();
        if (industryName == null) return;

        boolean success = false;
        while (!success) {
            Utils.printMessage("Enter Coordinates");
            int x = Utils.readIntegerInRange("X: ", 0, controller.getWidthMap());
            if (x == 0) {
                return;
            }
            int y = Utils.readIntegerInRange("Y: ", 0, controller.getHeightMap());
            if (y == 0) {
                return;
            }
            try {
                controller.addIndustry(selectedType, industryName, x - 1, y - 1);
                Utils.printMessage("Industry '" + industryName + "' added successfully at (X:" + x + ", Y:" + y + ")");
                Utils.printMap(controller.getSelectedMap());
                success = true;
            } catch (IllegalArgumentException | IllegalStateException e) {
                Utils.printMessage("Error: " + e.getMessage());
                Utils.printMessage("Please enter new coordinates.");
            }
        }
    }
}