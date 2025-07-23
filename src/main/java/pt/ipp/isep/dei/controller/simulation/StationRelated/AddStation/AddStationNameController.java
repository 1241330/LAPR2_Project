package pt.ipp.isep.dei.controller.simulation.StationRelated.AddStation;

import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.List;

import static pt.ipp.isep.dei.ui.console.utils.Utils.getEuclideanDistance;

/**
 * Controller responsible for managing the creation and naming of a new station in a simulation.
 * Provides methods to set and retrieve simulation, station type, direction, position, and name.
 * Also includes logic for auto-generating station names and creating stations at specified positions.
 */
public class AddStationNameController {

    /**
     * The simulation in which the station will be added.
     */
    private Simulation simulation;

    /**
     * The type of the station to be added.
     */
    private StationType stationType;

    /**
     * The direction for the new station.
     */
    private String direction;

    /**
     * The X coordinate for the new station's position.
     */
    private int xPosition;

    /**
     * The Y coordinate for the new station's position.
     */
    private int yPosition;

    /**
     * The name of the station to be created.
     */
    private String stationName;

    /**
     * Default constructor.
     */
    public AddStationNameController() {
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
     * Sets the position for the new station using string coordinates.
     * @param xPosition the X coordinate as a string
     * @param yPosition the Y coordinate as a string
     */
    public void setPosition(String xPosition, String yPosition) {
        this.xPosition = Integer.parseInt(xPosition);
        this.yPosition = Integer.parseInt(yPosition);
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
    public StationType getStationType() {
        return stationType;
    }

    /**
     * Sets the name for the station to be created.
     * @param name the station name to set
     */
    public void setStationName(String name) {
        this.stationName = name;
    }

    /**
     * Generates an automatic name for the station based on its type and the closest city.
     * Ensures uniqueness by appending a count if necessary.
     * @return the generated station name
     */
    public String getAutoStationName() {
        Position position = new Position(xPosition, yPosition);
        Map actualMap = simulation.getScenario().getMap();
        StationType type = stationType;

        String cityName = getClosestCityName(position, actualMap);
        if (cityName == null) {
            cityName = "UnknownCity";
        }
        String baseName = type.toString().toLowerCase() + "_" + cityName;
        int count = 0;

        for (Station station : this.simulation.getScenario().getMap().getStationList()) {
            if (station.getStationType().equalsIgnoreCase(type.toString()) &&
                    station.getName().startsWith(baseName)) {
                count++;
            }
        }

        if (count == 0) {
            this.stationName = baseName;
            return baseName;
        } else {
            this.stationName = baseName + "_" + (count);
            return baseName + "_" + (count);
        }
    }

    /**
     * Finds the name of the closest city to the given position on the map.
     * @param position the position to check
     * @param actualMap the map containing the cities
     * @return the name of the closest city, or null if none found
     */
    private String getClosestCityName(Position position, Map actualMap) {
        List<City> cities = actualMap.getCitiesList();

        City closestCity = null;
        double minDistance = Double.MAX_VALUE;

        for (City city : cities) {
            double distance = getEuclideanDistance(position, city.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                closestCity = city;
            }
        }

        return closestCity != null ? closestCity.getName() : null;
    }

    /**
     * Creates a new station at the specified position, sets its name and resources, and adds it to the simulation.
     * Deducts the construction cost from the simulation's money if successful.
     * @return true if the station was successfully created and added, false otherwise
     */
    public boolean createStation() {
        Map map = simulation.getScenario().getMap();
        Position position = new Position(xPosition - 1, yPosition - 1);
        Station station = new Station(stationType, position, map.getId(), direction, simulation.getScenario());
        station.setName(stationName);
        station.setResourcesTypeRequested(simulation.getScenario());

        if (map != null) {
            if (simulation != null && simulation.addStation(station)) {
                simulation.setActualMoney(simulation.getActualMoney() - stationType.getConstructionCost());
                return true;
            }
            return false;
        }
        return false;
    }
}
