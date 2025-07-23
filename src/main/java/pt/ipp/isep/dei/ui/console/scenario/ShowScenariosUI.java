package pt.ipp.isep.dei.ui.console.scenario;

import pt.ipp.isep.dei.controller.scenario.ShowScenariosController;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for displaying scenarios of a map.
 * Allows the user to select and view details of existing scenarios,
 * including resources, house blocks, and industries configurations.
 */
public class ShowScenariosUI implements Runnable {

    private ShowScenariosController controller;

    /**
     * Constructs a ShowScenariosUI with the given map.
     *
     * @param actualMap the map whose scenarios will be shown
     */
    public ShowScenariosUI(Map actualMap) {
        this.controller = new ShowScenariosController();
        controller.setActualMap(actualMap);
    }

    /**
     * Runs the UI logic for showing scenarios.
     * Throws an exception if no map is selected.
     */
    @Override
    public void run() {
        if (controller.getActualMap() != null) {
            chooseScenario();
        } else {
            throw new IllegalArgumentException("No map selected");
        }
    }

    /**
     * Allows the user to choose a scenario from the current map.
     * If there are no scenarios, a message is shown.
     */
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
        showScenario();
    }

    /**
     * Displays the details of the selected scenario and presents further options.
     */
    private void showScenario() {
        List<String> descriptions = new ArrayList<>();
        descriptions.add("Map: " + controller.getNameMap());
        descriptions.add("Initial Money: " + controller.getInitialMoney());
        descriptions.add("Beginning Date: " + controller.getBeginningDate());
        descriptions.add("End Date: " + controller.getEndDate());
        Utils.printMenu("Scenario: " + controller.getNameScenario(), descriptions);

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Resources configs", this::listResourcesConfigs));
        options.add(new MenuItem("House blocks configs", this::listHouseBlocksConfigs));
        options.add(new MenuItem("Industries configs", this::listIndustriesConfigs));

        while (true) {
            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Other characteristics",
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
     * Lists the resources configurations of the current scenario.
     */
    private void listResourcesConfigs() {
        List<ResourcesType> resourcesConfigs = controller.getResourcesList();
        Utils.printMenu("Resources configurations", Utils.convertObjectsToDescriptions(resourcesConfigs));
    }

    /**
     * Lists the house block resources of the current scenario.
     */
    private void listHouseBlocksConfigs() {
        List<HouseBlockResource> houseBlockResources = controller.getHouseBlocksResourceList();
        Utils.printMenu("House blocks resources", Utils.convertObjectsToDescriptions(houseBlockResources));
    }

    /**
     * Lists the industries configurations of the current scenario,
     * grouped by industry type.
     */
    private void listIndustriesConfigs() {
        List<Industry> industriesConfigs = controller.getIndustriesList();
        List<String> industries = new ArrayList<>();
        if (industriesConfigs.isEmpty()) {
            Utils.printMessage("< There aren't industries in this scenario >");
        } else {
            for (Industry industry : industriesConfigs) {
                if (industry instanceof PrimaryIndustry) {
                    industries.add(industry.toString());
                }
            }
            for (Industry industry : industriesConfigs) {
                if (industry instanceof TransformingIndustry) {
                    industries.add(industry.toString());
                }
            }
            for (Industry industry : industriesConfigs) {
                if (industry instanceof MixedIndustry) {
                    industries.add(industry.toString());
                }
            }
        }
        Utils.printMenu("Industries configurations", industries);
    }

    /**
     * Gets the controller responsible for showing scenarios.
     *
     * @return the ShowScenariosController instance
     */
    public ShowScenariosController getController() {
        return controller;
    }

    /**
     * Sets the controller responsible for showing scenarios.
     *
     * @param controller the ShowScenariosController to set
     */
    public void setController(ShowScenariosController controller) {
        this.controller = controller;
    }
}