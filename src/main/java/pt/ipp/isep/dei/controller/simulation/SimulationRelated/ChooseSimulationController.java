package pt.ipp.isep.dei.controller.simulation.SimulationRelated;

import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the selection of a simulation within a scenario.
 * Provides methods to set and retrieve the current scenario, list of simulations, simulation names,
 * and the selected simulation. Also provides utility methods to check if the simulation list is empty.
 */
public class ChooseSimulationController {

    /**
     * The current scenario in context.
     */
    private Scenario actualScenario;

    /**
     * The list of simulations associated with the current scenario.
     */
    private List<Simulation> simulationList = new ArrayList<>();

    /**
     * The list of simulation names for display or selection purposes.
     */
    private List<String> listOfNamesSimulations = new ArrayList<>();

    /**
     * The simulation currently selected by the user.
     */
    private Simulation selectedSimulation;

    /**
     * Default constructor.
     */
    public ChooseSimulationController() {}

    /**
     * Populates the list of simulation names from the current simulation list.
     */
    public void setListOfNamesSimulations(){
        listOfNamesSimulations.clear();
        for (Simulation simulation : simulationList) {
            listOfNamesSimulations.add(simulation.getName());
        }
    }

    /**
     * Sets the current scenario and updates the simulation list and names accordingly.
     * @param scenario the scenario to set as current
     */
    public void setActualScenario(Scenario scenario) {
        this.actualScenario = scenario;
        this.simulationList = scenario.getSimulations();
        setListOfNamesSimulations();
    }

    /**
     * Gets the list of simulation names.
     * @return the list of simulation names
     */
    public List<String> getListOfNamesSimulations() {
        return listOfNamesSimulations;
    }

    /**
     * Sets the selected simulation by its index in the simulation list.
     * @param index the index of the simulation to select
     */
    public void setSelectedSimulation(int index) {
        selectedSimulation = simulationList.get(index);
    }

    /**
     * Gets the currently selected simulation.
     * @return the selected simulation
     */
    public Simulation getSelectedSimulation() {
        return selectedSimulation;
    }

    /**
     * Checks if the simulation list is empty.
     * @return true if the simulation list is empty, false otherwise
     */
    public boolean listOfSimulationsIsEmpty() {
        return simulationList.isEmpty();
    }

    /**
     * Gets the current scenario.
     * @return the current scenario
     */
    public Scenario getActualScenario() {
        return actualScenario;
    }
}

