package pt.ipp.isep.dei.controller.simulation.InitializeSimulation;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;

/**
 * Controller responsible for managing the selection of simulation types.
 * It allows setting and retrieving the selected scenario and obtaining the map associated with it.
 */
public class TypesSimulationSelectionController {

    /**
     * The currently selected scenario.
     */
    private Scenario selectedScenario;

    /**
     * Constructs a TypesSimulationSelectionController.
     */
    public TypesSimulationSelectionController() {}

    /**
     * Sets the selected scenario.
     *
     * @param scenario the scenario to be set as selected
     */
    public void setSelectedScenario(Scenario scenario) {
        this.selectedScenario = scenario;
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
     * Returns the map associated with the selected scenario.
     *
     * @return the {@link Map} of the selected scenario
     * @throws IllegalStateException if no scenario is selected
     */
    public Map getMapFromScenario() {
        if (selectedScenario == null) {
            throw new IllegalStateException("No scenario selected");
        }
        return selectedScenario.getMap();
    }
}