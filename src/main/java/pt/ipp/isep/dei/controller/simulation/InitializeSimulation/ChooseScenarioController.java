package pt.ipp.isep.dei.controller.simulation.InitializeSimulation;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the selection of scenarios
 * during the simulation initialization process.
 * It allows setting the current map, retrieving available scenarios,
 * and selecting a scenario.
 */
public class ChooseScenarioController {

    private Map actualMap;
    private List<Scenario> scenarioList = new ArrayList<>();
    private List<String> listOfNamesScenarios = new ArrayList<>();
    private Scenario selectedScenario;

    /**
     * Constructs a ChooseScenarioController.
     */
    public ChooseScenarioController() {
    }

    /**
     * Populates the list of scenario names from the current scenario list.
     */
    public void setListOfNamesScenarios() {
        listOfNamesScenarios.clear();
        for (Scenario scenario : scenarioList) {
            listOfNamesScenarios.add(scenario.getName());
        }
    }

    /**
     * Sets the current map and updates the scenario list and names accordingly.
     *
     * @param map the map to set as current
     */
    public void setActualMap(Map map) {
        this.actualMap = map;
        this.scenarioList = actualMap.getScenarios();
        setListOfNamesScenarios();
    }

    /**
     * Returns the list of names of all available scenarios.
     *
     * @return a list of scenario names
     */
    public List<String> getListOfNamesScenarios() {
        return listOfNamesScenarios;
    }

    /**
     * Sets the selected scenario based on the given index in the scenario list.
     *
     * @param index the index of the scenario to select
     */
    public void setSelectedScenario(int index) {
        selectedScenario = scenarioList.get(index);
    }

    /**
     * Returns the currently selected scenario.
     *
     * @return the selected {@link Scenario}
     */
    public Scenario getSelectedScenario() {
        return selectedScenario;
    }

    /**
     * Checks if the list of available scenarios is empty.
     *
     * @return true if the scenario list is empty, false otherwise
     */
    public boolean listOfScenariosIsEmpty() {
        return scenarioList.isEmpty();
    }

    /**
     * Returns the current map used in this controller.
     *
     * @return the current {@link Map}
     */
    public Map getActualMap() {
        return actualMap;
    }
}