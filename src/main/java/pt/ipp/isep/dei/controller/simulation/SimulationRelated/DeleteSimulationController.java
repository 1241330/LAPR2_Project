package pt.ipp.isep.dei.controller.simulation.SimulationRelated;

import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

import java.io.File;

/**
 * Controller responsible for deleting a Simulation from a Scenario.
 * Provides methods to set and retrieve the current simulation and its scenario,
 * and to delete the simulation both from the scenario and from the file system.
 */
public class DeleteSimulationController {

    /**
     * The simulation currently selected for deletion.
     */
    private Simulation actualSimulation;

    /**
     * The scenario to which the simulation belongs.
     */
    private Scenario scenarioOfSimulation;

    /**
     * Directory path where simulation files are stored.
     */
    private static final String PATH_DIRECTORY = "data/Simulation/";

    /**
     * File extension for simulation files.
     */
    private static final String EXTENSION = ".ser";

    /**
     * Default constructor.
     */
    public DeleteSimulationController() {
    }

    /**
     * Sets the simulation to be deleted and its scenario.
     * @param simulation the simulation to set as current
     */
    public void setActualSimulation(Simulation simulation) {
        this.actualSimulation = simulation;
        this.scenarioOfSimulation = simulation.getScenario();
    }

    /**
     * Gets the scenario of the simulation to be deleted.
     * @return the scenario of the simulation
     */
    public Scenario getScenarioOfSimulation() {
        return scenarioOfSimulation;
    }

    /**
     * Deletes the selected simulation from its scenario and removes its file from the file system.
     * Throws IllegalStateException if no simulation is selected or if the file cannot be deleted.
     */
    public void deleteSimulation() {
        if (actualSimulation == null) {
            throw new IllegalStateException("No simulation selected for deletion");
        }
        scenarioOfSimulation.removeSimulation(actualSimulation);
        File file = new File(PATH_DIRECTORY + actualSimulation.getName() + "-" + scenarioOfSimulation.getName() + EXTENSION);
        if (!file.delete()) {
            throw new IllegalStateException("Failed to delete simulation file: " + file.getAbsolutePath());
        }
    }
}
