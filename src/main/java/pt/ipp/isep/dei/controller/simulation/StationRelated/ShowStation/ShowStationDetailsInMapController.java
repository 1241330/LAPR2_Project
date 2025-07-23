package pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for displaying the details of a station on the map in a simulation.
 * Provides methods to set and retrieve the scenario and station, as well as to obtain station details
 * such as name, type, position, associations, and requested resources.
 */
public class ShowStationDetailsInMapController {

    /**
     * The scenario in which the station exists.
     */
    private Scenario scenario;

    /**
     * The station whose details are being displayed.
     */
    private Station station;

    /**
     * Default constructor.
     */
    public ShowStationDetailsInMapController() {
        // Default constructor
    }

    /**
     * Gets the scenario in which the station exists.
     *
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets the scenario in which the station exists.
     *
     * @param scenario the scenario to set
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Gets the station whose details are being displayed.
     *
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station whose details are being displayed.
     *
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Gets the name of the station.
     *
     * @return the station name
     */
    public String getStationName() {
        return station.getName();
    }

    /**
     * Gets the type of the station as a formatted string.
     *
     * @return the station type description
     */
    public String getStationType() {
        String stationType = station.getStationType();
        if (stationType.equals(StationType.STATION.toString())) {
            return "Station (" + station.getDirection() + ")";
        }
        if (stationType.equals(StationType.DEPOT.toString())) {
            return "Depot";
        }
        if (stationType.equals(StationType.TERMINAL.toString())) {
            return "Terminal";
        }
        return "Error: Unknown Station Type";
    }

    /**
     * Gets the position of the station as a string, incremented by 1 in both X and Y axes.
     *
     * @return the station position as a string
     */
    public String getStationPosition() {
        Position position = new Position(station.getPosition().getX() + 1, station.getPosition().getY() + 1);
        return position.toString();
    }

    /**
     * Gets the associations of the station (e.g., industries and house blocks) as a list of strings.
     *
     * @return a list of association descriptions
     */
    public List<String> getAssociations() {
        List<String> associations = new ArrayList<>();
        for (StationAssociations association : station.getAllAssociations()) {
            if (association instanceof Industry industry) {
                Position position = new Position(industry.getPosition().getX() + 1, industry.getPosition().getY() + 1);
                String industryInfo = industry.getIndustryType().getDescription() + ": '" + industry.getName() +
                        "' " + position;
                associations.add(industryInfo);
            } else if (association instanceof HouseBlock houseBlock) {
                Position position = new Position(houseBlock.getPosition().getX() + 1, houseBlock.getPosition().getY() + 1);
                String houseBlockInfo = "House Block City: '" + houseBlock.getCityName() + "' " + position;
                associations.add(houseBlockInfo);
            } else {
                associations.add("Unknown Association: " + association.toString());
            }
        }
        return associations;
    }

    /**
     * Gets the list of resources requested by the station as a list of strings.
     *
     * @return a list of requested resource names
     */
    public List<String> getResourcesRequested() {
        List<String> resourcesRequested = new ArrayList<>();
        station.setResourcesTypeRequested(scenario);
        if (station.getResourcesTypeRequested() != null) {
            for (ResourcesType resource : station.getResourcesTypeRequested()) {
                resourcesRequested.add(resource.getName());
            }
        }
        return resourcesRequested;
    }
}