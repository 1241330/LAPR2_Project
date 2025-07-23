package pt.ipp.isep.dei.ui.console._MDISC_;

import pt.ipp.isep.dei.controller._MDISC_.ConnectivityBetweenStationsController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * User interface class to display the transitive closure matrix of connectivity between stations.
 * Implements Runnable to execute the transitive matrix viewing logic.
 */
public class ViewTransitiveMatrixUI implements Runnable {

    /**
     * Controller responsible for managing connectivity between stations.
     */
    private ConnectivityBetweenStationsController controller;

    /**
     * Constructs a ViewTransitiveMatrixUI and initializes its controller.
     *
     * @param stationList     List of stations in the scenario.
     * @param railwayLineList List of railway lines in the scenario.
     * @param scenarioName    Name of the scenario.
     */
    public ViewTransitiveMatrixUI(List<Station> stationList, List<RailwayLine> railwayLineList, String scenarioName) {
        controller = new ConnectivityBetweenStationsController(stationList, railwayLineList, scenarioName);
    }

    /**
     * Runs the UI process to show the transitive closure matrix.
     * Validates input data, allows train type filtering, then prints the matrix.
     */
    @Override
    public void run() {
        if (controller.getAllLines().isEmpty()) {
            Utils.printMessage("< List of lines is empty >");
            return;
        }
        if (controller.getSizeStationList() < 2) {
            Utils.printMessage("< There must be at least two stations >");
            return;
        }

        if (chooseTypeOfTrain()) {
            return;
        }

        // Print transitive closure matrix
        int[][] matrix = controller.getTransitiveClosureMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Displays options to choose the type of train for filtering railway lines.
     *
     * @return true if the user cancels the selection, false otherwise
     */
    private boolean chooseTypeOfTrain() {
        List<String> options = new ArrayList<>();
        options.add("Electrical Trains");
        options.add("All Train Types");
        int option = Utils.chooseOptionPrintMenuAndManualReturn("Types of Trains", options, "Return", "Choice");
        if (option == 0) {
            return true;
        }
        controller.setTrainType(options.get(option - 1));
        controller.filterRailwayLinesType();
        return false;
    }

    /**
     * Gets the controller responsible for managing connectivity between stations.
     *
     * @return the ConnectivityBetweenStationsController instance
     */
    public ConnectivityBetweenStationsController getController() {
        return controller;
    }

    /**
     * Sets the controller responsible for managing connectivity between stations.
     *
     * @param controller the ConnectivityBetweenStationsController instance to set
     */
    public void setController(ConnectivityBetweenStationsController controller) {
        this.controller = controller;
    }
}