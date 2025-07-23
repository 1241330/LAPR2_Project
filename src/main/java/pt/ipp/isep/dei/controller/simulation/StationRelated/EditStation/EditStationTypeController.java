package pt.ipp.isep.dei.controller.simulation.StationRelated.EditStation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;

/**
 * Controller responsible for editing the type and direction of a station in a simulation.
 * Provides methods to set and retrieve the simulation, station, and station type,
 * as well as to check station type, calculate evolution costs, and update station properties.
 */
public class EditStationTypeController {

    /**
     * Constant for the TERMINAL station type name.
     */
    private final String TERMINAL_NAME = "TERMINAL";
    /**
     * Constant for the STATION station type name.
     */
    private final String STATION_NAME = "STATION";

    /**
     * The simulation in which the station exists.
     */
    private Simulation simulation;
    /**
     * The station being edited.
     */
    private Station station;
    /**
     * The type of the station as a string.
     */
    private String stationType;

    /**
     * Default constructor.
     */
    public EditStationTypeController(){
    }

    /**
     * Gets the current simulation.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation in which the station exists.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the station being edited.
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station to be edited.
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Checks if the station is of type STATION.
     * @return true if the station is of type STATION, false otherwise
     */
    public boolean stationIsOfTypeStation() {
        return station.getStationType().equals(StationType.STATION.toString());
    }

    /**
     * Gets the type of the station as a string.
     * @return the station type
     */
    public String getStationType() {
        return stationType;
    }

    /**
     * Sets the type of the station as a string.
     * @param stationType the station type to set
     */
    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    /**
     * Calculates the cost to evolve the station to TERMINAL.
     * @return the cost to evolve to TERMINAL
     */
    public int getTerminalEvolveCost() {
        int evolveCost = StationType.TERMINAL.getConstructionCost() - station.getConstructionCost();
        return evolveCost;
    }

    /**
     * Calculates the cost to evolve the station to STATION.
     * @return the cost to evolve to STATION
     */
    public int getStationEvolveCost() {
        int evolveCost = StationType.STATION.getConstructionCost() - station.getConstructionCost();
        return evolveCost;
    }

    /**
     * Checks if the simulation has enough money for the given evolution cost.
     * @param terminalEvolveCost the cost to check
     * @return true if there is enough money, false otherwise
     */
    public boolean checkIfHasMoney(int terminalEvolveCost) {
        return terminalEvolveCost <= simulation.getActualMoney();
    }

    /**
     * Sets the station type to TERMINAL.
     */
    public void setStationTypeToTerminal() {
        station.setStationType(TERMINAL_NAME);
        station.assignGenerationPosts(simulation.getScenario());
    }

    /**
     * Sets the station type to STATION.
     */
    public void setStationTypeToStation() {
        station.setStationType(STATION_NAME);
        station.assignGenerationPosts(simulation.getScenario());
    }

    /**
     * Sets the direction of the station.
     * @param direction the direction to set
     */
    public void stationDirection(String direction) {
        station.setDirection(direction);
    }

    /**
     * Sets the station type to the default value stored in stationType.
     */
    public void setStationTypeDefault() {
        station.setStationType(stationType);
        station.assignGenerationPosts(simulation.getScenario());
    }
}
