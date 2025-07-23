package pt.ipp.isep.dei.controller.simulation.InitializeSimulation;

import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for managing the initialization of a simulation.
 * It handles the association between a simulation and its scenario,
 * and provides access to relevant information such as map and scenario names.
 */
public class InitializeSimulationController {

    private Simulation simulation;
    private Scenario actualScenario;
    private boolean newSimulation;

    /**
     * Constructs an InitializeSimulationController.
     */
    public InitializeSimulationController() {}

    public boolean isNewSimulation() {
        return newSimulation;
    }

    public void setNewSimulation(boolean newSimulation) {
        this.newSimulation = newSimulation;
    }

    /**
     * Sets the simulation and updates the current scenario accordingly.
     *
     * @param simulation the simulation to be set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        this.actualScenario = simulation.getScenario();
    }

    /**
     * Returns the name of the map associated with the current scenario.
     *
     * @return the name of the map
     */
    public String getNameMap() {
        return actualScenario.getMap().getName();
    }

    /**
     * Returns the name of the current scenario.
     *
     * @return the name of the scenario
     */
    public String getNameScenario() {
        return actualScenario.getName();
    }

    /**
     * Returns the current scenario.
     *
     * @return the current {@link Scenario}
     */
    public Scenario getActualScenario() {
        return actualScenario;
    }

    /**
     * Returns the current simulation.
     *
     * @return the current {@link Simulation}
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Removes the current simulation from the scenario.
     */
    public void deleteSimulation() {
        if (newSimulation) {
            actualScenario.removeSimulation(simulation);
        }
    }
}