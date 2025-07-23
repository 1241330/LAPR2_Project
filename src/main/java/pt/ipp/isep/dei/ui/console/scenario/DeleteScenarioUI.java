package pt.ipp.isep.dei.ui.console.scenario;

import pt.ipp.isep.dei.controller.scenario.DeleteScenarioController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for handling the deletion of a map.
 * Presents a confirmation prompt to the user before deleting the selected map.
 */
public class DeleteScenarioUI implements Runnable {

    private final DeleteScenarioController controller;

    public DeleteScenarioUI(Map actualMap) {
        controller = new DeleteScenarioController();
        controller.setActualMap(actualMap);
    }

    /**
     * Runs the UI logic for deleting a map.
     * Prompts the user for confirmation before proceeding with deletion.
     */
    @Override
    public void run() {
        if (controller.getActualMap() != null) {
            chooseScenario();
        } else {
            throw new IllegalArgumentException("No map selected");
        }
    }

    private void chooseScenario() {
        if (!controller.thereAreScenarios()) {
            Utils.printMessage("< There aren't scenarios in this map >");
            return;
        }
        List<Scenario> scenariosList = controller.getActualMap().getScenarios();
        int option = Utils.chooseOptionPrintMenuAndManualReturn(
                "Existing scenarios in " + controller.getNameMap(),
                Utils.convertObjectsToDescriptions(scenariosList),
                "Return",
                "Choice"
        );
        if (option == 0) {
            return;
        }
        controller.setActualScenario(scenariosList.get(option - 1));
        deleteScenario();
    }

    private void deleteScenario() {
        try {
            String nameScenario = controller.getNameScenario();
            if (nameScenario == null) {
                throw new IllegalStateException("No scenario selected for deletion");
            }
            List<String> options = new ArrayList<>();
            options.add("Yes");
            Utils.printMessage("< If you delete the scenario, its corresponding simulations will also be deleted too >");
            String question = "Do you want to delete it?";
            int confirmation = Utils.chooseOptionPrintMenuAndManualReturn(question, options, "No", "Choice");
            if (confirmation != 0) {
                controller.deleteScenario();
                Utils.printMessage("< Deleted " + nameScenario + " >");
            }
        } catch (IllegalStateException e) {
            Utils.printMessage("Deletion error: " + e.getMessage());
        }
    }
}
