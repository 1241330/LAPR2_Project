package pt.ipp.isep.dei.ui.console.scenario;

import pt.ipp.isep.dei.controller.scenario.SaveScenarioController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SaveScenarioUI implements Runnable {

    private SaveScenarioController controller;

    /**
     * Constructor for SaveScenarioUI. Initializes the controller with the given scenario.
     * @param scenario the scenario to be saved or updated
     */
    public SaveScenarioUI(Scenario scenario) {
        controller = new SaveScenarioController(scenario);
    }

    /**
     * Runs the UI logic, prompting the user to save or discard the scenario.
     */
    @Override
    public void run() {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Yes");
            int option = Utils.chooseOptionPrintMenuAndManualReturn("Do you want to save the new scenario?", options, "No", "Choose");
            switch (option) {
                case 1:
                    controller.saveScenario();
                    Utils.printMessage("< New scenario successfully saved! >");
                    return;
                case 0:
                    controller.dontSaveScenario();
                    Utils.printMessage("< New scenario not saved >");
                    return;
            }
        }
    }
}

