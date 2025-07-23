package pt.ipp.isep.dei.controller.simulation.StationRelated.RemoveStation;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for confirming the removal of a station from a simulation.
 * Provides methods to set and retrieve the simulation and selected station,
 * remove the selected station and its associated railway lines, and get the station name.
 */
public class RemoveStationConfirmController {

    /**
     * The simulation from which the station will be removed.
     */
    private Simulation simulation;

    /**
     * The station selected for removal.
     */
    private Station selectedStation;

    /**
     * Default constructor.
     */
    public RemoveStationConfirmController() {
    }

    /**
     * Gets the current simulation.
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation from which the station will be removed.
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the station selected for removal.
     * @return the selected station
     */
    public Station getSelectedStation() {
        return selectedStation;
    }

    /**
     * Sets the station selected for removal.
     * @param selectedStation the station to set
     */
    public void setStation(Station selectedStation) {
        this.selectedStation = selectedStation;
    }

    /**
     * Removes the selected station and all railway lines assigned to it from the simulation.
     * Also removes associations with industries and house blocks.
     */
    public void removeSelectedStation() {
        removeRailwayLinesAssigned();
        for (StationAssociations association : selectedStation.getAllAssociations()) {
            if (association instanceof Industry industry) {
                industry.setAssignedStation(null);
            }
            if (association instanceof HouseBlock houseBlock) {
                houseBlock.setAssignedStation(null);
            }
        }
        simulation.removeStation(selectedStation);
    }

    /**
     * Removes all railway lines assigned to the selected station from the simulation.
     */
    private void removeRailwayLinesAssigned() {
        List<RailwayLine> railwayLines = new ArrayList<>(simulation.getRailwayLines());
        for (RailwayLine railwayLine : railwayLines) {
            if (railwayLine.getStations().contains(selectedStation)) {
                simulation.removeRailwayLine(railwayLine);
            }
        }
    }

    /**
     * Gets the name of the selected station.
     * @return the name of the selected station
     */
    public String getStationName() {
        return selectedStation.getName();
    }
}