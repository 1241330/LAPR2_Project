package pt.ipp.isep.dei.ui.console.scenario;

import pt.ipp.isep.dei.controller.scenario.CreateScenarioController;
import pt.ipp.isep.dei.domain.Industry.*;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for creating a scenario.
 * Handles user input and configuration for scenario creation.
 */
public class CreateScenarioUI implements Runnable {

    private final CreateScenarioController controller;
    private final int MIN_INITIAL_MONEY = 0;
    private final int MAX_INITIAL_MONEY = 100000;
    private final int MIN_MAX_STORAGE = 1;
    private final int MAX_MAX_STORAGE = 1000;
    private final int MIN_GENERATION = 1;
    private final int MAX_GENERATION = 100;
    private final int MIN_PRODUCED = 1;
    private final int MAX_PRODUCED = 100;
    private boolean isValidScenario = false;

    private List<Industry> industryList;
    private List<PrimaryResource> primaryResourceList;
    private List<TransformingResource> transformingResourceList;

    /**
     * Constructs a CreateScenarioUI with the specified map.
     *
     * @param actualMap the map to associate with the scenario
     */
    public CreateScenarioUI(Map actualMap) {
        controller = new CreateScenarioController();
        controller.setActualMap(actualMap);
        primaryResourceList = controller.getPrimaryResourceList();
        transformingResourceList = controller.getTransformingResourceList();
    }

    /**
     * Runs the scenario creation UI logic.
     * Guides the user through the scenario creation process.
     */
    @Override
    public void run() {
        industryList = controller.getIndustriesList();

        if (industryList == null || industryList.isEmpty()) {
            Utils.printMessage("< No industries available >");
            return;
        }
        readNameScenario();
        if (!isValidScenario) {
            Utils.printMessage("< Scenario not created >");
            controller.removeScenario();
            return;
        }
        Utils.printMessage("< Scenario " + controller.getName() + " created successfully >");
        new SaveScenarioUI(controller.getScenario()).run();
    }

    /**
     * Reads and validates the scenario name from user input.
     */
    private void readNameScenario() {
        boolean printInformationMessage = true;
        while (true) {
            if (printInformationMessage) {
                System.out.println("\n╔═════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                   Create a Scenario: enter a name                   ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ - Only alphanumeric characters and underscores are allowed          ║");
                System.out.println("║ - No spaces, special characters, or accented letters are permitted  ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════╣");
                System.out.println("║  0. Return                                                          ║");
                System.out.println("╚═════════════════════════════════════════════════════════════════════╝");
            }
            printInformationMessage = false;
            String nameScenario = Utils.readSimpleWord("Name: ");
            if (nameScenario.equals("0")) {
                return;
            }
            if (controller.alreadyExistsNameScenarioInMap(nameScenario)) {
                Utils.printMessage("< Scenario name already exists >");
                continue;
            } else controller.setName(nameScenario);
            readMoneyScenario();
            if (isValidScenario) {
                return;
            }
        }
    }

    /**
     * Reads and validates the initial money for the scenario.
     */
    private void readMoneyScenario() {
        while (true) {
            Utils.chooseOptionPrintMenuAndManualReturn("Initial money", null, "Return", null);
            int initialMoney = Utils.readIntegerInRange("Initial money: ", MIN_INITIAL_MONEY, MAX_INITIAL_MONEY);
            if (initialMoney == 0) {
                return;
            }
            controller.setInitialMoney(initialMoney);
            readDatesScenario();
            if (isValidScenario) {
                return;
            }
        }
    }

    /**
     * Reads and validates the start and end dates for the scenario.
     */
    private void readDatesScenario() {
        while (true) {
            Utils.chooseOptionPrintMenuAndManualReturn("Date", null, "Return", null);
            TimeDate initialDate = Utils.readDateFromConsole("Beginning Date");
            if (initialDate == null) {
                return;
            }
            TimeDate endDate;
            do {
                endDate = Utils.readDateFromConsole("Ending Date");
                if (endDate == null) {
                    break;
                }
                if (endDate.before(initialDate)) {
                    Utils.printMessage("< The End Date must be after the Start Date >");
                }
            } while (endDate.before(initialDate));
            if (endDate == null) {
                continue;
            }
            controller.setInitialDate(initialDate);
            controller.setEndDate(endDate);
            controller.createScenario();
            chooseLocomotiveTypes();
            if (isValidScenario) {
                return;
            }
        }
    }

    /**
     * Allows the user to select available locomotive types for the scenario.
     */
    private void chooseLocomotiveTypes() {
        while (true) {
            List<FuelType> locomotiveTypes = new ArrayList<>(controller.getFuelTypeList());
            List<FuelType> selectedTypes = new ArrayList<>();
            while (true) {
                int option;
                if (selectedTypes.isEmpty()) {
                    option = Utils.chooseOptionPrintMenuAndManualReturn(
                            "Choose locomotive types to make available",
                            Utils.convertObjectsToDescriptions(locomotiveTypes),
                            null,
                            "Choice"
                    );
                } else {
                    option = Utils.chooseOptionPrintMenuAndManualReturn(
                            "Choose locomotive types to make available",
                            Utils.convertObjectsToDescriptions(locomotiveTypes),
                            "Save",
                            "Choice"
                    );
                }
                if (option == 0) {
                    break;
                }
                FuelType selectedType = locomotiveTypes.get(option - 1);
                selectedTypes.add(selectedType);
                locomotiveTypes.remove(selectedType);
                if (locomotiveTypes.isEmpty()) {
                    break;
                }
            }
            controller.setAvailableFuelTypeList(selectedTypes);
            Utils.printMessage("< Locomotive types configured successfully >");
            controller.setLocomotiveListAvailable();
            chooseResourceConfig();
            if (isValidScenario) {
                return;
            } else {
                controller.clearLocomotiveListAvailable();
            }
        }
    }

    /**
     * Allows the user to configure resources for the scenario.
     */
    private void chooseResourceConfig() {
        List<String> options = new ArrayList<>();
        options.add("Default configuration");
        options.add("Modified configuration");
        int option1 = Utils.chooseOptionPrintMenuAndManualReturn(
                "Configuration Resources",
                options,
                "Return",
                "Choice"
        );
        if (option1 == 0) {
            return;
        }
        if (option1 == 2) {
            while (true) {
                List<ResourcesType> listResourceType = controller.getAllResourcesList();
                int option2 = Utils.chooseOptionPrintMenuAndManualReturn(
                        " Edit configs resources type",
                        Utils.convertObjectsToDescriptions(listResourceType),
                        "Save",
                        "Choice"
                );
                if (option2 == 0) {
                    break;
                }
                chooseWhatEdit(listResourceType.get(option2 - 1));
            }
        } else {
            Utils.printMessage("< Default configuration defined successfully >");
        }
        controller.setResourcesListScenario();
        chooseResourcesHouseBlock();
    }

    /**
     * Allows the user to edit specific resource type configurations.
     *
     * @param resourceType the resource type to edit
     */
    private void chooseWhatEdit(ResourcesType resourceType) {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("Maximum possible storage per station");
            options.add("Generation rate in months");
            options.add("Quantity produced per month");
            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Modify resource type" + resourceType.getName(),
                    options,
                    "Save",
                    "Choice"
            );
            switch (option) {
                case 0:
                    return;
                case 1: {
                    int beforeMaximum = resourceType.getMaxResources();
                    Utils.printMessage("Actual maximum possible storage per station: " + beforeMaximum);
                    int change = Utils.readIntegerInRange("New maximum: ", MIN_MAX_STORAGE, MAX_MAX_STORAGE);
                    resourceType.setMaxResources(change);
                    Utils.printMessage("< Maximum possible storage per station: " + beforeMaximum + " --> " + change + " >");
                }
                break;
                case 2: {
                    int beforeGeneration = resourceType.getIntervalBetweenResourceGeneration();
                    Utils.printMessage("Actual generation rate in months: " + beforeGeneration);
                    int change = Utils.readIntegerInRange("New generation rate: ", MIN_GENERATION, MAX_GENERATION);
                    resourceType.setIntervalBetweenResourceGeneration(change);
                    Utils.printMessage("< Generation rate in months: " + beforeGeneration + " --> " + change + " >");
                }
                break;
                case 3: {
                    int beforeProduced = resourceType.getQuantityProduced();
                    Utils.printMessage("Actual quantity produced per month: " + beforeProduced);
                    int change = Utils.readIntegerInRange("New quantity produced: ", MIN_PRODUCED, MAX_PRODUCED);
                    resourceType.setQuantityProduced(change);
                    Utils.printMessage("< Quantity produced per month: " + beforeProduced + " --> " + change + " >");
                }
                break;
            }
        }
    }

    /**
     * Allows the user to select available house block resources for the scenario.
     */
    private void chooseResourcesHouseBlock() {
        while (true) {
            List<HouseBlockResource> houseBlockResources = new ArrayList<>(controller.getHouseBlockResourceList());
            List<HouseBlockResource> selectedResources = new ArrayList<>();
            while (true) {
                int option;
                if (selectedResources.isEmpty()) {
                    option = Utils.chooseOptionPrintMenuAndManualReturn(
                            "Choose house block resources to make available",
                            Utils.convertObjectsToDescriptions(houseBlockResources),
                            null,
                            "Choice"
                    );
                } else {
                    option = Utils.chooseOptionPrintMenuAndManualReturn(
                            "Choose house block resources to make available",
                            Utils.convertObjectsToDescriptions(houseBlockResources),
                            "Save",
                            "Choice"
                    );
                }
                if (option == 0) {
                    break;
                }
                HouseBlockResource selectedType = houseBlockResources.get(option - 1);
                selectedResources.add(selectedType);
                houseBlockResources.remove(selectedType);
                if (houseBlockResources.isEmpty()) {
                    break;
                }
            }
            controller.setAvailableHouseBlockResourceList(selectedResources);
            Utils.printMessage("< House block resources configured successfully >");
            controller.setHouseBlockResourceListScenario();
            configIndustries();
            if (isValidScenario) {
                return;
            } else {
                controller.clearAvailableHouseBlockResourceList();
            }
        }
    }

    /**
     * Configures industries for the scenario.
     */
    private void configIndustries() {
        for (Industry industry : industryList) {
            if (industry instanceof PrimaryIndustry) {
                int generation = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Generation in the " + industry.getName() + " industry",
                        Utils.convertObjectsToDescriptions(primaryResourceList),
                        null,
                        "Choice"
                );
                controller.setResourceInPrimaryIndustry(industry, generation - 1);
                Utils.printMessage("< Resource " + primaryResourceList.get(generation - 1).getName() +
                        " configured successfully in the " + industry.getName() + " industry >");
            }
        }
        for (Industry industry : industryList) {
            if (industry instanceof TransformingIndustry) {
                List<String> transformations = new ArrayList<>();
                for (TransformingResource transformingResource : transformingResourceList) {
                    transformations.add(transformingResource.getTransformation());
                }
                int transformation = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Transformation in the " + industry.getName() + " industry",
                        transformations,
                        null,
                        "Choice"
                );
                controller.setResourceInTransformingIndustry(industry, transformation - 1);
                Utils.printMessage("< Resource " + transformingResourceList.get(transformation - 1).getName() +
                        " configured successfully in the " + industry.getName() + " industry >");
            }
        }
        for (Industry industry : industryList) {
            if (industry instanceof MixedIndustry) {
                controller.initializeMixedIndustry(industry);
                configMixedIndustries(industry);
            }
        }
        isValidScenario = true;
    }

    /**
     * Configures mixed industries for the scenario.
     *
     * @param industry the mixed industry to configure
     */
    private void configMixedIndustries(Industry industry) {
        while (true) {
            List<String> options = new ArrayList<>();
            if (!controller.resourceListImportIsEmpty()) {
                options.add("Import");
            }
            if (!controller.resourceListExportIsEmpty()) {
                options.add("Export");
            }
            if (!controller.resourceListTransformingIsEmpty()) {
                options.add("Transformation");
            }
            if (options.isEmpty()) {
                return;
            }
            int option1;
            if (isValidScenario) {
                option1 = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Generation in the " + industry.getName() + " industry",
                        options,
                        "Save",
                        "Choice"
                );
            } else {
                option1 = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Generation in the " + industry.getName() + " industry",
                        options,
                        null,
                        "Choice"
                );
            }
            switch (option1) {
                case 1: {
                    int option2 = Utils.chooseOptionPrintMenuAndManualReturn(
                            "Import in the " + industry.getName() + " industry",
                            Utils.convertObjectsToDescriptions(controller.getResourceListImport()),
                            "Return",
                            "Choice"
                    );
                    if (option2 != 0) {
                        controller.addImportMixedIndustry(industry, option2 - 1);
                        isValidScenario = true;
                    }
                }
                break;
                case 2: {
                    int option3 = Utils.chooseOptionPrintMenuAndManualReturn(
                            "Export in the " + industry.getName() + " industry",
                            Utils.convertObjectsToDescriptions(controller.getResourceListExport()),
                            "Return",
                            "Choice"
                    );
                    if (option3 != 0) {
                        controller.addExportMixedIndustry(industry, option3 - 1);
                        isValidScenario = true;
                    }
                }
                break;
                case 3: {
                    int option4 = Utils.chooseOptionPrintMenuAndManualReturn(
                            "Transformation in the " + industry.getName() + " industry",
                            Utils.convertObjectsToDescriptions(controller.getResourceListTransforming()),
                            "Return",
                            "Choice"
                    );
                    if (option4 != 0) {
                        controller.addTransformingMixedIndustry(industry, option4 - 1);
                        isValidScenario = true;
                    }
                }
                break;
                case 0:
                    return;
            }
        }
    }

    /**
     * Gets the scenario controller.
     *
     * @return the CreateScenarioController instance
     */
    public CreateScenarioController getController() {
        return controller;
    }

    /**
     * Gets the list of industries.
     *
     * @return the list of Industry objects
     */
    public List<Industry> getIndustryList() {
        return industryList;
    }

    /**
     * Sets the list of industries.
     *
     * @param industryList the list of Industry objects to set
     */
    public void setIndustryList(List<Industry> industryList) {
        this.industryList = industryList;
    }

    /**
     * Gets the list of primary resources.
     *
     * @return the list of PrimaryResource objects
     */
    public List<PrimaryResource> getPrimaryResourceList() {
        return primaryResourceList;
    }

    /**
     * Sets the list of primary resources.
     *
     * @param primaryResourceList the list of PrimaryResource objects to set
     */
    public void setPrimaryResourceList(List<PrimaryResource> primaryResourceList) {
        this.primaryResourceList = primaryResourceList;
    }

    /**
     * Gets the list of transforming resources.
     *
     * @return the list of TransformingResource objects
     */
    public List<TransformingResource> getTransformingResourceList() {
        return transformingResourceList;
    }

    /**
     * Sets the list of transforming resources.
     *
     * @param transformingResourceList the list of TransformingResource objects to set
     */
    public void setTransformingResourceList(List<TransformingResource> transformingResourceList) {
        this.transformingResourceList = transformingResourceList;
    }

    /**
     * Checks if the scenario is valid.
     *
     * @return true if the scenario is valid, false otherwise
     */
    public boolean isValidScenario() {
        return isValidScenario;
    }

    /**
     * Sets the scenario validity.
     *
     * @param validScenario true if the scenario is valid, false otherwise
     */
    public void setValidScenario(boolean validScenario) {
        isValidScenario = validScenario;
    }
}