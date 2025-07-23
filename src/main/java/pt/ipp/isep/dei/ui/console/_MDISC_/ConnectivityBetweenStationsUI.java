package pt.ipp.isep.dei.ui.console._MDISC_;

import pt.ipp.isep.dei.controller._MDISC_.ConnectivityBetweenStationsController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.ui.console.utils.Utils;
import pt.ipp.isep.dei.ui.console.utils.Utils_MDISC;

import java.util.ArrayList;
import java.util.List;

/**
 * User interface for checking connectivity between stations.
 * Allows filtering railway lines by train type and
 * provides several connectivity-related operations.
 */
public class ConnectivityBetweenStationsUI implements Runnable {

    /**
     * Controller responsible for connectivity operations.
     */
    private final ConnectivityBetweenStationsController controller;

    /**
     * Constructs the UI with the given station list, railway line list, and scenario name.
     *
     * @param stationList    List of stations.
     * @param railwayLineList List of railway lines.
     * @param scenarioName   Name of the scenario.
     */
    public ConnectivityBetweenStationsUI(List<Station> stationList, List<RailwayLine> railwayLineList, String scenarioName) {
        this.controller = new ConnectivityBetweenStationsController(stationList, railwayLineList, scenarioName);
    }

    /**
     * Runs the connectivity UI, showing menus and handling user interaction.
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

        do {
            List<MenuItem> options = new ArrayList<>();
            options.add(new MenuItem("Check Connectivity Between Stations", this::checkConnectivityBetweenStations));
            options.add(new MenuItem("Check Connectivity Between Two Stations", this::checkConnectivityBetweenTwoStations));
            options.add(new MenuItem("Visualize Railway Network", this::visualizeRailwayNetwork));

            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Connectivity Menu",
                    Utils.convertObjectsToDescriptions(options),
                    "Return",
                    "Choose an option"
            );
            if (option == 0) {
                return;
            }
            options.get(option - 1).run();
        } while (true);
    }

    /**
     * Allows the user to select the train type filter for railway lines.
     *
     * @return true if user chooses to return, false otherwise.
     */
    private boolean chooseTypeOfTrain() {
        List<String> options = new ArrayList<>();
        options.add("Electrical Trains");
        options.add("All Train Types");

        int option = Utils.chooseOptionPrintMenuAndManualReturn("Select Train Type", options, "Return", "Choice");
        if (option == 0) {
            return true;
        }

        controller.setTrainType(options.get(option - 1));
        controller.filterRailwayLinesType();
        return false;
    }

    /**
     * Checks connectivity between all valid stations excluding selected station types.
     * Asks user which station types to exclude, up to three times.
     */
    public void checkConnectivityBetweenStations() {
        List<Character> excludedTypes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            char option = chooseTypeOfStation("Remove stations by Type:");
            if (option == 'N') { // Return
                break;
            }
            if (!excludedTypes.contains(option)) {
                excludedTypes.add(option);
            }
        }

        char[] excludedArray = new char[excludedTypes.size()];
        for (int i = 0; i < excludedTypes.size(); i++) {
            excludedArray[i] = excludedTypes.get(i);
        }

        boolean isConnected = controller.verifyConnectivity(excludedArray);
        System.out.printf("\nAll valid stations are connected (excluding types %s): %s\n",
                excludedTypes.toString(), isConnected ? "YES" : "NO");
    }

    /**
     * Checks connectivity (transitive path) between two stations chosen by the user.
     * Prints whether a path exists between the selected origin and destination stations.
     */
    public void checkConnectivityBetweenTwoStations() {
        List<Station> stations = controller.getStationList();

        // Show available stations
        System.out.println("\nStation list:");
        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, stations.get(i).getName());
        }

        // Ask for user input
        int originIndex = Utils.readIntegerFromConsole("\nChoose the ORIGIN station: ") - 1;
        int destinationIndex = Utils.readIntegerFromConsole("Choose the DESTINATION station: ") - 1;

        // Validate input
        if (originIndex < 0 || originIndex >= stations.size()
                || destinationIndex < 0 || destinationIndex >= stations.size()) {
            System.out.println("Invalid station numbers. Operation canceled.");
            return;
        }

        Station origin = stations.get(originIndex);
        Station destination = stations.get(destinationIndex);

        // Check transitive connectivity
        boolean isConnected = controller.isConnectedTransitive(origin, destination);

        if (isConnected) {
            System.out.printf("\nIs there a path between '%s' and '%s'? YES\n", origin.getName(), destination.getName());
        } else {
            System.out.printf("\nIs there a path between '%s' and '%s'? NO\n", origin.getName(), destination.getName());
        }
    }

    /**
     * Displays a menu to choose a type of station to exclude or save the selection.
     *
     * @param text The message to display on the menu.
     * @return the character code representing the selected station type, or 'N' for cancel.
     */
    private char chooseTypeOfStation(String text) {
        List<String> options = List.of("Depot", "Station", "Terminal");
        int selected = Utils.chooseOptionPrintMenuAndManualReturn(text, options, "Save", "Choose an Option");

        switch (selected) {
            case 1:
                return 'D';
            case 2:
                return 'S';
            case 3:
                return 'T';
            default:
                return 'N';
        }
    }

    /**
     * Visualizes the railway network using utility methods,
     * showing depots, stations, terminals, and available railway lines.
     */
    private void visualizeRailwayNetwork() {
        Utils_MDISC.visualizeRailwayNetworkStream(
                controller.findSameTypeStations('D'),
                controller.findSameTypeStations('S'),
                controller.findSameTypeStations('T'),
                controller.getAvailableLines());
    }

    /**
     * Gets the controller used by this UI.
     *
     * @return the ConnectivityBetweenStationsController instance.
     */
    public ConnectivityBetweenStationsController getController() {
        return controller;
    }
}