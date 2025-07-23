package pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Train;

import java.util.List;

/**
 * Controller responsible for viewing and managing the list of trains in a simulation.
 */
public class ViewTrainController {

    /**
     * The simulation instance associated with this controller.
     */
    private Simulation simulation;

    /**
     * Sets the simulation instance for this controller.
     *
     * @param simulation the Simulation object to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the current simulation instance.
     *
     * @return the current Simulation object
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Retrieves the list of trains in the simulation as an observable list,
     * sorted by locomotive fuel type and then by locomotive name.
     *
     * @return ObservableList of Train objects
     */
    public ObservableList<Train> getTrainList() {
        List<Train> trains = simulation.getTrainList();

        for (int i = 1; i < trains.size(); i++) {
            Train key = trains.get(i);
            int j = i - 1;

            while (j >= 0 && compareTrains(trains.get(j), key) > 0) {
                trains.set(j + 1, trains.get(j));
                j = j - 1;
            }
            trains.set(j + 1, key);
        }

        return FXCollections.observableArrayList(trains);
    }

    /**
     * Compares two trains first by their locomotive's fuel type, then by locomotive name.
     *
     * @param t1 the first Train to compare
     * @param t2 the second Train to compare
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     *         equal to, or greater than the second
     */
    private int compareTrains(Train t1, Train t2) {
        String fuelType1 = t1.getLocomotive().getFuelType().toString();
        String fuelType2 = t2.getLocomotive().getFuelType().toString();

        int fuelCompare = fuelType1.compareTo(fuelType2);
        if (fuelCompare != 0) {
            return fuelCompare;
        }

        String name1 = t1.getLocomotive().getName();
        String name2 = t2.getLocomotive().getName();
        return name1.compareTo(name2);
    }
}