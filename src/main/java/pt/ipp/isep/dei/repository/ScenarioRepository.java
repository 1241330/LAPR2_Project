package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Scenario entities.
 * Provides methods to add, remove, retrieve, and check the existence of scenarios.
 */
public class ScenarioRepository implements Serializable {

    /**
     * List that stores all scenarios.
     */
    private List<Scenario> scenarios = new ArrayList<>();

    /**
     * Constructs an empty ScenarioRepository.
     */
    public ScenarioRepository() {
        // Default constructor
    }

    /**
     * Adds a scenario to the repository.
     *
     * @param scenario The scenario to add.
     * @return true if the scenario was added successfully, false if a scenario with the same name already exists.
     * @throws IllegalArgumentException if the scenario is null.
     */
    public boolean addScenario(Scenario scenario) {
        if (scenario == null) {
            throw new IllegalArgumentException("Scenario cannot be null");
        }
        if (scenarioNameExists(scenario.getName())) {
            return false;
        }
        scenarios.add(scenario);
        return true;
    }

    /**
     * Removes a scenario from the repository.
     *
     * @param scenario The scenario to remove.
     * @return true if the scenario was removed successfully, false if it does not exist.
     * @throws IllegalArgumentException if the scenario is null.
     */
    public boolean removeScenario(Scenario scenario) {
        if (scenario == null) {
            throw new IllegalArgumentException("Scenario cannot be null");
        }
        if (!scenarioExists(scenario)) {
            Utils.printMessage("< Scenario doesn't exist >");
            return false;
        }
        scenarios.remove(scenario);
        return true;
    }

    /**
     * Retrieves a scenario by its name.
     *
     * @param name The name of the scenario.
     * @return The scenario with the specified name, or null if not found.
     */
    public Scenario getScenarioByName(String name) {
        for (Scenario scenario : scenarios) {
            if (scenario.getName().equalsIgnoreCase(name)) {
                return scenario;
            }
        }
        return null;
    }

    /**
     * Retrieves the total number of scenarios in the repository.
     *
     * @return The number of scenarios.
     */
    public int getScenarioCount() {
        return scenarios.size();
    }

    /**
     * Retrieves a list of all scenarios in the repository.
     *
     * @return A list of scenarios.
     */
    public List<Scenario> getAllScenarios() {
        return scenarios;
    }

    /**
     * Checks if a specific scenario exists in the repository.
     *
     * @param actualScenario The scenario to check.
     * @return true if the scenario exists, false otherwise.
     */
    public boolean scenarioExists(Scenario actualScenario) {
        return scenarios.contains(actualScenario);
    }

    /**
     * Checks if a scenario with the given name already exists in the repository.
     *
     * @param name The name to check.
     * @return true if a scenario with the given name exists, false otherwise.
     */
    public boolean scenarioNameExists(String name) {
        for (Scenario scenario : scenarios) {
            if (scenario.getName().equalsIgnoreCase(name)) {
                Utils.printMessage("<Scenario name already exists>");
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of scenarios.
     *
     * @return The list of scenarios.
     */
    public List<Scenario> getScenarios() {
        return scenarios;
    }

    /**
     * Sets the list of scenarios.
     *
     * @param scenarios The list of scenarios to set.
     */
    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    /**
     * Removes all scenarios from the repository.
     */
    public void clear() {
        scenarios.clear();
    }
}