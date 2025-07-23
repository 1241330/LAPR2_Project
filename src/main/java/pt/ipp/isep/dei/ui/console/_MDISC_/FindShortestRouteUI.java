package pt.ipp.isep.dei.ui.console._MDISC_;

import pt.ipp.isep.dei.controller._MDISC_.FindShortestRouteController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class for finding the shortest route between stations.
 * Handles user interaction for reading stations, selecting train type,
 * and displaying results.
 */
public class FindShortestRouteUI implements Runnable {

    private final FindShortestRouteController controller;

    /**
     * Constructor for FindShortestRouteUI.
     *
     * @param stationsList     List of all stations.
     * @param railwayLinesList List of all railway lines.
     */
    public FindShortestRouteUI(List<Station> stationsList, List<RailwayLine> railwayLinesList) {
        this.controller = new FindShortestRouteController(stationsList, railwayLinesList);
    }

    /**
     * Entry point for the UI logic.
     */
    @Override
    public void run() {
        chooseTypeReadStations();
    }

    /**
     * Allows the user to choose the type of train and set connections.
     *
     * @return true if the user chooses to return, false otherwise.
     */
    private boolean chooseTypeTrain() {
        while (true) {
            int typeChoice = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Select Train Type",
                    List.of("Electric", "Diesel"),
                    "Return",
                    "Choice"
            );
            if (typeChoice == 0) {
                return true;
            }
            controller.setElectricTrain(typeChoice == 1);
            if (setConnections()) {
                return true;
            }
            if (readStationsManually()) {
                continue;
            }
            return false;
        }
    }

    /**
     * Sets the connected stations in the controller.
     *
     * @return true if no stations have connections, false otherwise.
     */
    private boolean setConnections() {
        controller.setConnectedStations();
        if (controller.connectedStationsIsEmpty()) {
            Utils.printMessage("< No stations have connections >");
            return true;
        }
        return false;
    }

    /**
     * Allows the user to choose how to read stations (CSV or manual input).
     */
    private void chooseTypeReadStations() {
        while (true) {
            List<String> options = new ArrayList<>();
            options.add("By CSV File");
            options.add("By Manual Input");
            int choice = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Choose how to read stations",
                    options,
                    "Return",
                    "Choice"
            );
            if (choice == 0) {
                return;
            }
            switch (choice) {
                case 1: {
                    controller.setElectricTrain(false);
                    if (setConnections()) {
                        continue;
                    }
                    if (readStationFromCSV()) {
                        continue;
                    }
                    break;
                }
                case 2: {
                    if (chooseTypeTrain()) {
                        continue;
                    }
                    break;
                }
                default:
                    break;
            }
            findShortestRoute();
            return;
        }
    }

    /**
     * Reads stations from a CSV file.
     *
     * @return true if the user chooses to return, false otherwise.
     */
    private boolean readStationFromCSV() {
        Utils.chooseOptionPrintMenuAndManualReturn("Read station CSV", null, "Return", null);
        String pathCSVStations;
        while (true) {
            pathCSVStations = Utils.readLineFromConsole("Stations file path: ");
            if (pathCSVStations.equals("0")) {
                return true;
            }
            if (Utils.isValidCSVFile(pathCSVStations)) {
                break;
            }
            Utils.printMessage("< File doesn't exist or isn't a CSV >");
        }
        controller.createWeightMatrix();
        printWeightMatrix(controller.getWeightMatrix(), controller.getStationsList());
        controller.setCsvPath(pathCSVStations);
        controller.setAutomaticStations();
        return false;
    }

    /**
     * Reads stations manually from user input.
     *
     * @return true if the user chooses to return, false otherwise.
     */
    private boolean readStationsManually() {
        while (true) {
            int stationOption = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Select the Start Station",
                    controller.getStationsNames(controller.getConnectedStations()),
                    "Return",
                    "Choice"
            );
            if (stationOption == 0) {
                return true;
            }
            controller.setStartStation(controller.getConnectedStations().get(stationOption - 1));

            controller.setReachableStations();
            if (controller.reachableStationsIsEmpty()) {
                Utils.printMessage("< No reachable stations from start >");
                return true;
            }

            Utils.printMessage("< You may select the same station as Start and Final if a valid route exists >");

            stationOption = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Select the Final Station",
                    controller.getStationsNames(controller.getReachableStations()),
                    null,
                    "Choice"
            );
            controller.setFinalStation(controller.getReachableStations().get(stationOption - 1));

            controller.removeStationFromReachable(controller.getStartStation());
            controller.removeStationFromReachable(controller.getFinalStation());

            while (!controller.reachableStationsIsEmpty()) {
                stationOption = Utils.chooseOptionPrintMenuAndManualReturn(
                        "Select an Intermediate Station",
                        controller.getStationsNames(controller.getReachableStations()),
                        "Save",
                        "Choice"
                );
                if (stationOption == 0) {
                    break;
                }

                Station selectedStation = controller.getReachableStations().get(stationOption - 1);
                controller.addIntermediateStation(selectedStation);
                controller.removeStationFromReachable(selectedStation);
            }
            return false;
        }
    }

    /**
     * Finds and displays the shortest route.
     */
    private void findShortestRoute() {
        controller.findBestPath();
        if (controller.pathIsEmpty()) {
            Utils.printMessage("< No valid path found >");
        } else {
            Utils.printMessage("< Shortest path found >");
            Utils.printMenu(
                    "Shortest Path: " + controller.getLengthOfPath() + " km",
                    controller.getStationsNames(controller.getPath())
            );
            controller.visualisationOfRailwayNetwork();
        }
    }

    /**
     * Prints the weight matrix for the stations.
     *
     * @param weightMatrix The weight matrix.
     * @param stations     The list of stations.
     */
    private void printWeightMatrix(Integer[][] weightMatrix, List<Station> stations) {
        Utils.chooseOptionPrintMenuAndManualReturn(
                "Station Captions",
                controller.getStationsNames(stations),
                null,
                null
        );

        System.out.println("\nWEIGHT MATRIX (∞ = no direct connection):");

        int colWidth = 5;
        for (Integer[] row : weightMatrix) {
            for (Integer val : row) {
                if (val != null && val.toString().length() + 1 > colWidth) {
                    colWidth = val.toString().length() + 1;
                }
            }
        }

        // Print column headers
        System.out.printf("%" + colWidth + "s", "");
        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("%" + colWidth + "d", i + 1);
        }
        System.out.println();

        // Print horizontal line
        System.out.printf("%" + colWidth + "s", "");
        for (int i = 0; i < stations.size(); i++) {
            System.out.print("─".repeat(colWidth));
        }
        System.out.println();

        // Print matrix rows
        for (int i = 0; i < weightMatrix.length; i++) {
            System.out.printf("%" + colWidth + "d", i + 1);
            for (int j = 0; j < weightMatrix[i].length; j++) {
                String value = (weightMatrix[i][j] == null) ? "∞" : weightMatrix[i][j].toString();
                System.out.printf("%" + colWidth + "s", value);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Gets the controller instance.
     *
     * @return The FindShortestRouteController instance.
     */
    public FindShortestRouteController getController() {
        return controller;
    }
}