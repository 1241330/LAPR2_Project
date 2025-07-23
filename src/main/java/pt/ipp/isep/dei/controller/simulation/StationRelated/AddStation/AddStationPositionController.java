package pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Station.StationType;

/**
 * Controller responsible for managing the position and type of a station to be added in a simulation.
 * Provides methods to set and retrieve the simulation, direction, and station type, as well as to validate position values.
 */
public class AddStationPositionController {
    /**
     * The direction for the new station.
     */
    private String direction;

    /**
     * The simulation in which the station will be added.
     */
    private Simulation simulation;

    /**
     * The type of the station to be added.
     */
    private StationType stationType;

    /**
     * Default constructor.
     */
    public AddStationPositionController() {
        // Default constructor
    }

    /**
     * Gets the direction for the new station.
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction for the new station.
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
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
     * Verifies if the provided X and Y values are valid for the map size.
     * @param text the X coordinate as a string
     * @param text1 the Y coordinate as a string
     * @return a message indicating if the values are valid or describing the error
     */
    public String verifyValues(String text, String text1) {
        Size size =  simulation.getScenario().getMap().getPixelSize();
        try {
            int x = Integer.parseInt(text);
            int y = Integer.parseInt(text1);

            if (x <= 0 || y <= 0){
                return "Every value must be Positive!!";
            } else if (x > size.getWidth() || y > size.getHeight()){
                return "Values out of Bound for the Map!! x: " + size.getWidth() + " y: " + size.getHeight();
            } else {
                return "no problem";
            }
        } catch (NumberFormatException e) {
            return "Insert only Numbers!!";
        }
    }

    /**
     * Sets the type of the station to be added.
     * @param stationType the station type to set
     */
    public void setStationType(StationType stationType) {
        this.stationType= stationType;
    }

    /**
     * Gets the type of the station to be added.
     * @return the station type
     */
    public StationType getStationType(){
        return stationType;
    }
}
