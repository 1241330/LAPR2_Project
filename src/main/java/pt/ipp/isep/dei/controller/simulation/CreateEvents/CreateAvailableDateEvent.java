package pt.ipp.isep.dei.controller.simulation.CreateEvents;

import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.StartCarriageOperationEvent;
import pt.ipp.isep.dei.domain.Event.StartLocomotiveOperationEvent;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.repository.CarriageRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for creating events related to the availability dates
 * of locomotives and carriages in a simulation.
 */
public class CreateAvailableDateEvent {

    private Simulation simulation;
    private CarriageRepository carriageRepository;
    private List<Locomotive> locomotivesList;
    private List<Event> dateEventsList = new ArrayList<>();

    /**
     * Constructor that initializes the class with a simulation.
     *
     * @param simulation the simulation to be used for event creation
     */
    public CreateAvailableDateEvent(Simulation simulation) {
        initializeCarriageRepository();
        this.simulation = simulation;
        this.locomotivesList = simulation.getScenario().getAvailableLocomotiveList();
    }

    /**
     * Initializes the carriage repository if it is not already initialized.
     */
    private void initializeCarriageRepository() {
        if (carriageRepository == null) {
            Repositories repositories = Repositories.getInstance();
            carriageRepository = repositories.getCarriageRepository();
        }
    }

    /**
     * Adds events to the list for each locomotive and carriage available in the simulation.
     * Each event represents the year when a locomotive or carriage becomes available.
     */
    public void addEventsToList() {
        for (Locomotive locomotive : locomotivesList) {
            int startYearOperation = locomotive.getStartYearOperation();
            String nameEventLocomotive = locomotive.getName() + " available in " + startYearOperation;
            StartLocomotiveOperationEvent startLocomotiveOperationEvent = new StartLocomotiveOperationEvent(
                    nameEventLocomotive, 0, startYearOperation, simulation, locomotive);
            if (!Utils.eventExistsByName(nameEventLocomotive, dateEventsList)) {
                dateEventsList.add(startLocomotiveOperationEvent);
            }
        }

        for (Carriage carriage : carriageRepository.getCarriageList()) {
            int startYearOperation = carriage.getStartYearOperation();
            String nameEventCarriage = carriage.getName() + " available in " + startYearOperation;
            StartCarriageOperationEvent startCarriageOperationEvent = new StartCarriageOperationEvent(
                    nameEventCarriage, 0, startYearOperation, simulation, carriage);
            if (!Utils.eventExistsByName(nameEventCarriage, dateEventsList)) {
                dateEventsList.add(startCarriageOperationEvent);
            }
        }
    }

    /**
     * Returns the list of date events created.
     *
     * @return the list of date events
     */
    public List<Event> getDateEventsList() {
        return dateEventsList;
    }

    /**
     * Returns the simulation associated with this event creator.
     *
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation for this event creator.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Returns the carriage repository used by this event creator.
     *
     * @return the carriage repository
     */
    public CarriageRepository getCarriageRepository() {
        return carriageRepository;
    }

    /**
     * Sets the carriage repository for this event creator.
     *
     * @param carriageRepository the carriage repository to set
     */
    public void setCarriageRepository(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }

    /**
     * Returns the list of locomotives used for event creation.
     *
     * @return the list of locomotives
     */
    public List<Locomotive> getLocomotivesList() {
        return locomotivesList;
    }

    /**
     * Sets the list of locomotives for event creation.
     *
     * @param locomotivesList the list of locomotives to set
     */
    public void setLocomotivesList(List<Locomotive> locomotivesList) {
        this.locomotivesList = locomotivesList;
    }

    /**
     * Sets the list of date events.
     *
     * @param dateEventsList the list of date events to set
     */
    public void setDateEventsList(List<Event> dateEventsList) {
        this.dateEventsList = dateEventsList;
    }
}