package pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.dto.LocomotiveDTO;
import pt.ipp.isep.dei.mapper.LocomotiveMapper;

import java.util.List;

/**
 * Controller responsible for handling the logic related to buying locomotives in a simulation.
 */
public class BuyLocomotiveController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Default constructor for BuyLocomotiveController.
     */
    public BuyLocomotiveController() {}

    /**
     * Gets the current simulation instance.
     *
     * @return the current Simulation object
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance for this controller.
     *
     * @param simulation the Simulation object to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Retrieves the list of available locomotives for the current simulation date as an observable list of DTOs.
     *
     * @return ObservableList of available LocomotiveDTO objects
     */
    public ObservableList<LocomotiveDTO> getLocomotivesList() {
        List<Locomotive> locomotives = simulation.getAvailableDateLocomotives();
        List<LocomotiveDTO> dtos = locomotives.stream()
                .map(LocomotiveMapper::toDTO)
                .toList();
        return FXCollections.observableArrayList(dtos);
    }

    /**
     * Checks if there is not enough money in the simulation to buy the selected locomotive.
     *
     * @param selectedDto the selected LocomotiveDTO object
     * @return true if there is not enough money, false otherwise
     */
    public boolean notEnoughMoney(LocomotiveDTO selectedDto) {
        return simulation.getActualMoney() < selectedDto.getAcquisitionPrice();
    }

    /**
     * Converts a LocomotiveDTO to its corresponding domain Locomotive object.
     *
     * @param dto the LocomotiveDTO to convert
     * @return the corresponding Locomotive domain object
     */
    public Locomotive getDomainLocomotiveFromDTO(LocomotiveDTO dto) {
        return LocomotiveMapper.toDomain(dto);
    }
}
