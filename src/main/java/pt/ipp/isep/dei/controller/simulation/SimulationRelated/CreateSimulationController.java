package pt.ipp.isep.dei.controller.simulation.SimulationRelated;

import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

/**
 * Controller responsible for creating a new Simulation within a given Scenario.
 * Provides methods to set and retrieve the scenario, simulation name, and simulation instance.
 * Also includes validation for simulation name and checks for name uniqueness within the scenario.
 */
public class CreateSimulationController {
    /**
     * The simulation instance being created.
     */
    private Simulation simulation;

    /**
     * The scenario in which the simulation will be created.
     */
    private Scenario actualScenario;

    /**
     * The name of the simulation to be created.
     */
    private String simulationName;

    /**
     * Default constructor.
     */
    public CreateSimulationController() {
    }

    /**
     * Sets the scenario in which the simulation will be created.
     * @param scenario the scenario to set
     */
    public void setActualScenario(Scenario scenario) {
        this.actualScenario = scenario;
    }

    /**
     * Gets the current scenario.
     * @return the current scenario
     */
    public Scenario getActualScenario() {
        return actualScenario;
    }

    /**
     * Sets the name for the simulation to be created.
     * @param simulationName the name to set
     */
    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    /**
     * Gets the name of the simulation to be created.
     * @return the simulation name
     */
    public String getSimulationName() {
        return simulationName;
    }

    /**
     * Checks if the simulation name is empty or null.
     * @return true if the name is empty or null, false otherwise
     */
    public boolean nameSimulationIsEmpty() {
        return simulationName == null || simulationName.trim().isEmpty();
    }

    /**
     * Validates the simulation name against the allowed pattern (alphanumeric and underscores).
     * @return true if the name is valid, false otherwise
     */
    public boolean isValidName() {
        return simulationName.matches("^[A-Za-z0-9_]+$");
    }

    /**
     * Creates a new simulation and adds it to the current scenario.
     * Throws IllegalArgumentException if no scenario is selected.
     */
    public void createSimulation() {
        if (actualScenario == null) {
            throw new IllegalArgumentException("No scenario selected");
        }
        simulation = new Simulation(simulationName, actualScenario);
        actualScenario.addSimulation(simulation);
    }

    /**
     * Checks if a simulation with the same name already exists in the current scenario.
     * @return true if a simulation with the same name exists, false otherwise
     */
    public boolean alreadyExistsNameSimulationInScenario() {
        if (actualScenario == null) {
            return false;
        }
        for (Simulation existingSimulation : actualScenario.getSimulations()) {
            if (existingSimulation.getName().equals(simulationName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the created simulation instance.
     * @return the simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }
}
