package pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.dto.StationDTO;
import pt.ipp.isep.dei.mapper.StationMapper;

/**
 * Controller responsible for displaying the details of a station in a simulation.
 * Provides methods to set and retrieve the simulation and selected station,
 * and to obtain station details such as buildings and cargos as observable lists.
 */
public class ShowStationDetailsController {

    /**
     * The simulation in which the station exists.
     */
    private Simulation simulation;

    /**
     * The station selected for displaying details.
     */
    private Station selectedStation;

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
     * Sets the station selected for displaying details.
     * @param selectedStation the station to set
     */
    public void setSelectedStation(Station selectedStation) {
        this.selectedStation = selectedStation;
    }

    /**
     * Gets the StationDTO for the selected station.
     * @return the StationDTO
     */
    public StationDTO getStationDTO() {
        return StationMapper.toDTO(selectedStation);
    }

    /**
     * Gets the list of buildings of the selected station as an observable list.
     * @return an observable list of building names
     */
    public ObservableList<String> getStationBuildings() {
        return FXCollections.observableArrayList(getStationDTO().getBuildings());
    }

    /**
     * Gets the list of cargos of the selected station as an observable list.
     * @return an observable list of cargo names
     */
    public ObservableList<String> getStationCargos() {
        return FXCollections.observableArrayList(getStationDTO().getCargoes());
    }
}
