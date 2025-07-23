package pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.dto.StationDTO;
import pt.ipp.isep.dei.mapper.StationMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for displaying the list of stations in a simulation.
 * Provides methods to set and retrieve the simulation, obtain the list of stations as StationDTOs,
 * and retrieve a Station by its DTO.
 */
public class ShowStationListController {

    /**
     * The simulation whose stations are to be displayed.
     */
    private Simulation simulation;

    /**
     * Gets the current simulation.
     *
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation whose stations are to be displayed.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Retrieves the list of stations from the simulation as an ObservableList of StationDTOs.
     *
     * @return an observable list of StationDTOs
     */
    public ObservableList<StationDTO> getStationList() {
        List<Station> stations = simulation.getScenario().getMap().getStationList();
        List<StationDTO> dtos = new ArrayList<>();
        for (Station s : stations) {
            dtos.add(StationMapper.toDTO(s));
        }
        return FXCollections.observableArrayList(dtos);
    }

    /**
     * Retrieves a {@link Station} object from the simulation's map that matches the given {@link StationDTO}.
     * <p>
     * The comparison is based on the station's name. It is assumed that station names are unique
     * within the map. If no matching station is found, {@code null} is returned.
     * </p>
     *
     * @param dto the StationDTO containing the name of the station to search for
     * @return the corresponding Station object if found; {@code null} otherwise
     */
    public Station getStationByDTO(StationDTO dto) {
        for (Station station : simulation.getScenario().getMap().getStationList()) {
            if (station.getName().equals(dto.getName())) {
                return station;
            }
        }
        return null;
    }
}