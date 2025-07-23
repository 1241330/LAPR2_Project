package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Simulation entities.
 * Provides methods to add, remove, retrieve, and check the existence of simulations.
 */
public class SimulationRepository implements Serializable {

    /**
     * List that stores all simulations.
     */
    private List<Simulation> simulations = new ArrayList<>();

    /**
     * Constructs an empty SimulationRepository.
     */
    public SimulationRepository() {
        // Default constructor
    }

    /**
     * Adds a simulation to the repository.
     *
     * @param simulation The simulation to add.
     * @return true if the simulation was added successfully, false if a simulation with the same name already exists.
     * @throws IllegalArgumentException if the simulation is null.
     */
    public boolean addSimulation(Simulation simulation) {
        if (simulation == null) {
            throw new IllegalArgumentException("Simulation cannot be null");
        }
        if (simulationNameExists(simulation.getName())) {
            return false;
        }
        simulations.add(simulation);
        return true;
    }

    /**
     * Removes a simulation from the repository.
     *
     * @param simulation The simulation to remove.
     * @return true if the simulation was removed successfully, false if it does not exist.
     * @throws IllegalArgumentException if the simulation is null.
     */
    public boolean removeSimulation(Simulation simulation) {
        if (simulation == null) {
            throw new IllegalArgumentException("Simulation cannot be null");
        }
        if (!simulationExists(simulation)) {
            Utils.printMessage("< Simulation doesn't exist >");
            return false;
        }
        simulations.remove(simulation);
        return true;
    }

    /**
     * Retrieves a simulation by its name.
     *
     * @param name The name of the simulation.
     * @return The simulation with the specified name, or null if not found.
     */
    public Simulation getSimulationByName(String name) {
        for (Simulation simulation : simulations) {
            if (simulation.getName().equalsIgnoreCase(name)) {
                return simulation;
            }
        }
        return null;
    }

    /**
     * Retrieves the total number of simulations in the repository.
     *
     * @return The number of simulations.
     */
    public int getSimulationCount() {
        return simulations.size();
    }

    /**
     * Retrieves a list of all simulations in the repository.
     *
     * @return A list of simulations.
     */
    public List<Simulation> getAllSimulations() {
        return simulations;
    }

    /**
     * Checks if a specific simulation exists in the repository.
     *
     * @param actualSimulation The simulation to check.
     * @return true if the simulation exists, false otherwise.
     */
    public boolean simulationExists(Simulation actualSimulation) {
        for (Simulation simulation : simulations) {
            if (simulation.getName().equals(actualSimulation.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a simulation with the given name already exists in the repository.
     *
     * @param name The name to check.
     * @return true if a simulation with the given name exists, false otherwise.
     */
    public boolean simulationNameExists(String name) {
        for (Simulation simulation : simulations) {
            if (simulation.getName().equalsIgnoreCase(name)) {
                Utils.printMessage("<Simulation name already exists>");
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of simulations.
     *
     * @return The list of simulations.
     */
    public List<Simulation> getSimulations() {
        return simulations;
    }

    /**
     * Sets the list of simulations.
     *
     * @param simulations The list of simulations to set.
     */
    public void setSimulations(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    /**
     * Retrieves the last simulation added to the repository.
     *
     * @return The last simulation, or null if the list is empty.
     */
    public Simulation getLastSimulation() {
        if (simulations.isEmpty()) {
            return null;
        }
        return simulations.getLast();
    }

    /**
     * Removes all simulations from the repository.
     */
    public void clear() {
        simulations.clear();
    }
}