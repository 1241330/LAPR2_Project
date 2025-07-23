package pt.ipp.isep.dei.ui.console._MDISC_;

import pt.ipp.isep.dei.controller._MDISC_.MaintenanceRailwayLineController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User interface class for managing the maintenance of railway lines.
 * Implements Runnable to execute the maintenance process logic.
 */
public class MaintenanceRailwayLineUI implements Runnable {

    private MaintenanceRailwayLineController controller;

    /**
     * Constructor initializes the controller with the list of stations, railway lines, and scenario name.
     *
     * @param stationList     List of stations in the scenario.
     * @param railwayLineList List of railway lines in the scenario.
     * @param scenarioName    Name of the scenario being worked on.
     */
    public MaintenanceRailwayLineUI(List<Station> stationList, List<RailwayLine> railwayLineList, String scenarioName) {
        controller = new MaintenanceRailwayLineController(stationList, railwayLineList, scenarioName);
    }

    /**
     * Runs the maintenance process UI.
     * It validates line and station data, filters lines by type,
     * checks graph connectivity and Eulerian path conditions,
     * allows the user to select initial stations, calculates maintenance path,
     * and finally visualizes the railway network.
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
        if (chooseRailwayLineType()) {
            return;
        }
        if (controller.isNotGraphConnected()) {
            Utils.printMessage("< It isn't possible to form an Eulerian path: the graph isn´t connected (não é conexo) >");
            return;
        } else {
            Utils.printMessage("< The graph is connected (conexo) >");
        }
        controller.setValidStationsConnectedLines();
        controller.calculateVertexDegrees();
        if (controller.setEulerianPathType()) {
            Utils.printMessage("< It's not possible to go through all the lines: the graph is neither Eulerian nor semi-Eulerian >");
            return;
        }
        Utils.printMessage("< Is a " + controller.typeOfEulerianGraph() + " >");
        if (chooseInitialStation()) {
            return;
        }
        if (controller.setFinalPath()) {
            Utils.printMessage("< Eulerian path could not be formed: not all lines were visited >");
            return;
        }
        if (controller.getFinalMaintenancePath().isEmpty()) {
            Utils.printMessage("< No maintenance path found >");
        } else {
            printFinalMaintenancePath();
        }
        try {
            controller.visualisationOfRailwayNetwork();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays options to choose the type of railway lines for maintenance.
     * Filters the railway lines accordingly.
     *
     * @return true if the user selects to return/cancel, false otherwise.
     */
    private boolean chooseRailwayLineType() {
        List<String> options = new ArrayList<>();
        options.add("Electrified Railway Line");
        options.add("Non-Electrified Railway Line");
        options.add("All Railway Lines");
        int option = Utils.chooseOptionPrintMenuAndManualReturn("Type of Railway Lines", options, "Return", "Choice");
        if (option == 0) {
            return true;
        }
        controller.setRailwayLinesType(options.get(option - 1));
        controller.filterRailwayLinesType();
        return false;
    }

    /**
     * Allows the user to choose the initial station to start the maintenance path.
     *
     * @return true if the user cancels the selection, false otherwise.
     */
    private boolean chooseInitialStation() {
        while (true) {
            List<Station> validInitialStations = controller.getValidInitialStations();
            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Choose First Station",
                    controller.convertStationsToNames(validInitialStations),
                    "Cancel",
                    "Choice"
            );
            if (option == 0) {
                return true;
            }
            controller.addStationToStartingStations(validInitialStations.get(option - 1));
            chooseTheNextStations();
            return false;
        }
    }

    /**
     * Allows the user to choose the next stations to form the maintenance path,
     * until all valid stations are included or the user saves the path.
     */
    private void chooseTheNextStations() {
        while (controller.getSizeStartingStationsList() != controller.getSizeValidStationsList()) {
            List<Station> validStations = controller.removeStationsInList(
                    controller.getStationsConnectedAnotherStation(controller.getLastStartingStation()),
                    controller.getSelectedStartingStations()
            );
            if (validStations.isEmpty()) {
                return;
            }
            if (validStations.size() != 1) {
                Utils.printMenu(
                        "Current path formed",
                        Utils.convertObjectsToDescriptions(controller.convertStationsToNames(controller.getSelectedStartingStations()))
                );
                int option = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Next Station",
                        controller.convertStationsToNames(validStations),
                        "Save",
                        "Choice"
                );
                if (option == 0) {
                    return;
                }
                controller.addStationToStartingStations(validStations.get(option - 1));
            } else {
                controller.addStationToStartingStations(validStations.getFirst());
            }
        }
    }

    /**
     * Prints the final maintenance path of railway lines.
     */
    private void printFinalMaintenancePath() {
        List<String> maintenancePathNames = controller.convertStationsToNames(controller.getFinalMaintenancePath());
        Utils.printMenu("Railway line maintenance path", Utils.convertObjectsToDescriptions(maintenancePathNames));
    }

    /**
     * Gets the controller instance.
     *
     * @return The MaintenanceRailwayLineController instance.
     */
    public MaintenanceRailwayLineController getController() {
        return controller;
    }

    /**
     * Sets the controller instance.
     *
     * @param controller The MaintenanceRailwayLineController to set.
     */
    public void setController(MaintenanceRailwayLineController controller) {
        this.controller = controller;
    }
}