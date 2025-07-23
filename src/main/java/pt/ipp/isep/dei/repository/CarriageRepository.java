package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Train.Carriage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing Carriage entities.
 * Provides methods to save, retrieve, set, and clear carriages.
 */
public class CarriageRepository implements Serializable {

    /**
     * Static list that stores all carriages.
     */
    private static List<Carriage> carriageList;

    /**
     * Constructs a CarriageRepository and initializes the carriage list.
     */
    public CarriageRepository() {
        carriageList = new ArrayList<Carriage>();
    }

    /**
     * Saves a carriage to the repository.
     *
     * @param carriage the carriage to be saved
     */
    public void save(Carriage carriage) {
        carriageList.add(carriage);
    }

    /**
     * Returns the list of all carriages.
     *
     * @return list of carriages
     */
    public List<Carriage> getCarriageList() {
        return carriageList;
    }

    /**
     * Sets the list of carriages.
     *
     * @param carriageList the list of carriages to set
     */
    public void setCarriageList(List<Carriage> carriageList) {
        CarriageRepository.carriageList = carriageList;
    }

    /**
     * Clears all carriages from the repository.
     */
    public void clear() {
        carriageList.clear();
    }
}