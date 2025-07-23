package pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.StationType;

/**
 * Controller responsible for managing the direction and type of a station to be added in a simulation.
 * Provides methods to set and retrieve the simulation, direction, and station type.
 */
public class AddStationDirectionController {

    /**
     * The simulation in which the station will be added.
     */
    private Simulation simulation;

    /**
     * The direction for the new station.
     */
    private String direction;

    /**
     * The type of the station to be added.
     */
    private StationType stationType;

    /**
     * Default constructor.
     */
    public AddStationDirectionController() {
    }

    /**
     * Gets the current simulation.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation in which the station will be added.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Sets the direction for the new station.
     * @param newDirection the direction to set
     */
    public void setDirection(String newDirection){
        this.direction = newDirection;
    }

    /**
     * Gets the direction for the new station.
     * @return the direction
     */
    public String getDirection(){
        return direction;
    }

    /**
     * Sets the type of the station to be added.
     * @param stationType the station type to set
     */
    public void setStationType(StationType stationType) {
        this.stationType = stationType;
    }

    /**
     * Gets the type of the station to be added.
     * @return the station type
     */
    public StationType getStationType(){
        return stationType;
    }
}
