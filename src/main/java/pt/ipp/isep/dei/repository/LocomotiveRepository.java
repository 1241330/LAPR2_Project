package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Train.Locomotive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing Locomotive entities.
 * Provides methods to save, retrieve, and clear locomotives.
 */
public class LocomotiveRepository implements Serializable {

    /**
     * Static list that stores all locomotives.
     */
    private static List<Locomotive> locomotiveList = new ArrayList<>();

    /**
     * Constructs a LocomotiveRepository.
     * No initialization required as the list is already initialized.
     */
    public LocomotiveRepository() {
        // Default constructor
    }

    /**
     * Saves a locomotive to the repository.
     *
     * @param loco the locomotive to be saved
     */
    public void save(Locomotive loco) {
        locomotiveList.add(loco);
    }

    /**
     * Returns the list of all locomotives.
     *
     * @return the list of locomotives
     */
    public List<Locomotive> getAllLocomotives() {
        return locomotiveList;
    }

    /**
     * Clears all locomotives from the repository.
     */
    public void clear() {
        locomotiveList.clear();
    }

    /**
     * Gets the static list of locomotives.
     *
     * @return the list of locomotives
     */
    public static List<Locomotive> getLocomotiveList() {
        return locomotiveList;
    }

    /**
     * Sets the static list of locomotives.
     *
     * @param locomotiveList the list of locomotives to set
     */
    public static void setLocomotiveList(List<Locomotive> locomotiveList) {
        LocomotiveRepository.locomotiveList = locomotiveList;
    }
}