package pt.ipp.isep.dei.domain.Scenario;

import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.repository.SimulationRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scenario in the game, containing information about the map,
 * industries, resources, locomotives, and simulation period.
 */
public class Scenario implements Serializable {

    /** The map associated with the scenario. */
    private Map map;

    /** The name of the scenario. */
    private String name;

    /** The list of industries available in the map. */
    private List<Industry> industriesList;

    /** The list of industries present in the scenario. */
    private List<Industry> industriesListInScenario = new ArrayList<>();

    /** The list of house block resources in the scenario. */
    private List<HouseBlockResource> houseBlockResourceList = new ArrayList<>();

    /** The list of available locomotives in the scenario. */
    private List<Locomotive> availableLocomotiveList = new ArrayList<>();

    /** The initial money available in the scenario. */
    private int initialMoney;

    /** The start date of the scenario. */
    private TimeDate beginningDate;

    /** The end date of the scenario. */
    private TimeDate endDate;

    /** The list of resource types in the scenario. */
    private List<ResourcesType> typeResourceList = new ArrayList<>();

    /** The repository for managing simulations. */
    private SimulationRepository simulationRepository;

    /**
     * Constructs a new Scenario with the specified map, name, initial money, beginning date, and end date.
     *
     * @param map           the map associated with the scenario
     * @param name          the name of the scenario
     * @param initialMoney  the initial money available in the scenario
     * @param beginningDate the start date of the scenario
     * @param endDate       the end date of the scenario
     */
    public Scenario(Map map, String name, int initialMoney, TimeDate beginningDate, TimeDate endDate) {
        setMap(map);
        this.name = name;
        this.initialMoney = initialMoney;
        this.beginningDate = beginningDate;
        this.endDate = endDate;
        this.simulationRepository = new SimulationRepository();
    }

    /**
     * Sets the map for the scenario and updates the industries list from the map.
     *
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
        this.industriesList = map.getIndustriesList();
    }

    /**
     * Returns the map associated with the scenario.
     *
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the name of the scenario.
     *
     * @return the scenario name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of industries available in the map.
     *
     * @return the list of industries
     */
    public List<Industry> getIndustriesList() {
        return industriesList;
    }

    /**
     * Returns the list of industries present in the scenario.
     *
     * @return the list of industries in the scenario
     */
    public List<Industry> getIndustriesListInScenario() {
        return industriesListInScenario;
    }

    /**
     * Returns the list of house block resources in the scenario.
     *
     * @return the list of house block resources
     */
    public List<HouseBlockResource> getHouseBlockResourceList() {
        return houseBlockResourceList;
    }

    /**
     * Sets the list of house block resources for the scenario.
     *
     * @param houseBlockResourceList the list of house block resources to set
     */
    public void setHouseBlockResourceList(List<HouseBlockResource> houseBlockResourceList) {
        this.houseBlockResourceList = houseBlockResourceList;
    }

    /**
     * Returns the list of available locomotives in the scenario.
     *
     * @return the list of available locomotives
     */
    public List<Locomotive> getAvailableLocomotiveList() {
        return availableLocomotiveList;
    }

    /**
     * Adds a locomotive to the list of available locomotives.
     *
     * @param locomotive the locomotive to add
     * @throws IllegalArgumentException if the locomotive is null or already exists in the list
     */
    public void addLocomotive(Locomotive locomotive) {
        if (locomotive == null) {
            throw new IllegalArgumentException("Locomotive cannot be null");
        }
        if (availableLocomotiveList.contains(locomotive)) {
            throw new IllegalArgumentException("Locomotive already exists in the list");
        }
        availableLocomotiveList.add(locomotive);
    }

    /**
     * Clears the list of available locomotives.
     */
    public void clearAvailableLocomotiveList() {
        availableLocomotiveList.clear();
    }

    /**
     * Returns the initial money for the scenario.
     *
     * @return the initial money
     */
    public int getInitialMoney() {
        return initialMoney;
    }

    /**
     * Returns the beginning date of the scenario.
     *
     * @return the beginning date
     */
    public TimeDate getBeginningDate() {
        return beginningDate;
    }

    /**
     * Returns the end date of the scenario.
     *
     * @return the end date
     */
    public TimeDate getEndDate() {
        return endDate;
    }

    /**
     * Returns the list of resource types in the scenario.
     *
     * @return the list of resource types
     */
    public List<ResourcesType> getTypeResourceList() {
        return typeResourceList;
    }

    /**
     * Sets the list of resource types for the scenario.
     *
     * @param typeResourceList the list of resource types to set
     */
    public void setTypeResourceList(List<ResourcesType> typeResourceList) {
        this.typeResourceList = typeResourceList;
    }

    /**
     * Adds a primary industry to the scenario by cloning it from the map.
     *
     * @param primaryIndustry the primary industry to add
     */
    public void addPrimaryIndustryScenarioByIndustryMap(PrimaryIndustry primaryIndustry) {
        industriesListInScenario.add(primaryIndustry.getClonedPrimaryIndustry(this));
    }

    /**
     * Adds a transforming industry to the scenario by cloning it from the map.
     *
     * @param transformingIndustry the transforming industry to add
     */
    public void addTransformingIndustryScenarioByIndustryMap(TransformingIndustry transformingIndustry) {
        industriesListInScenario.add(transformingIndustry.getClonedTransformingIndustry(this));
    }

    /**
     * Adds a mixed industry to the scenario by cloning it from the map.
     *
     * @param mixedIndustry the mixed industry to add
     */
    public void addMixedIndustryScenarioByIndustryMap(MixedIndustry mixedIndustry) {
        industriesListInScenario.add(mixedIndustry.getClonedMixedIndustry(this));
    }

    /**
     * Returns the number of simulations in the scenario.
     *
     * @return the number of simulations
     */
    public int getNumSimulations() {
        return simulationRepository.getSimulationCount();
    }

    /**
     * Adds a simulation to the scenario.
     *
     * @param simulation the simulation to add
     */
    public void addSimulation(Simulation simulation) {
        simulationRepository.addSimulation(simulation);
    }

    /**
     * Removes a simulation from the scenario.
     *
     * @param simulation the simulation to remove
     */
    public void removeSimulation(Simulation simulation) {
        simulationRepository.removeSimulation(simulation);
    }

    /**
     * Returns the list of all simulations in the scenario.
     *
     * @return the list of simulations
     */
    public List<Simulation> getSimulations() {
        return simulationRepository.getAllSimulations();
    }

    /**
     * Returns the last simulation added to the scenario.
     *
     * @return the last simulation
     */
    public Simulation getLastSimulation() {
        return simulationRepository.getLastSimulation();
    }

    /**
     * Clears all simulations from the scenario.
     */
    public void clearSimulations() {
        simulationRepository.clear();
    }

    /**
     * Returns a string representation of the scenario (its name).
     *
     * @return the scenario name
     */
    @Override
    public String toString() {
        return this.name;
    }
}