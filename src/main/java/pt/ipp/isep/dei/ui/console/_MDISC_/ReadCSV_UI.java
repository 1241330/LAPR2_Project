package pt.ipp.isep.dei.ui.console._MDISC_;

import pt.ipp.isep.dei.controller._MDISC_.ReadCSV_Controller;
import pt.ipp.isep.dei.ui.console.utils.Utils;

/**
 * UI class responsible for reading a scenario from CSV files.
 * Handles user input for file paths and triggers the scenario reading process.
 */
public class ReadCSV_UI implements Runnable {

    /**
     * Controller responsible for reading the scenario from CSV files.
     */
    private ReadCSV_Controller controller;

    /**
     * Constructs a ReadCSV_UI and initializes its controller.
     */
    public ReadCSV_UI() {
        controller = new ReadCSV_Controller();
    }

    /**
     * Runs the UI logic for reading a scenario from CSV files.
     * Prompts the user for the stations and lines CSV file paths,
     * validates their existence, and triggers the scenario reading process.
     */
    @Override
    public void run() {
        Utils.chooseOptionPrintMenuAndManualReturn("Read from Scenario CSV", null, "Return", null);

        String pathCSVStations;
        while (true) {
            pathCSVStations = Utils.readLineFromConsole("Stations file path: ");
            if (pathCSVStations.equals("0")) {
                return;
            }
            if (Utils.isValidCSVFile(pathCSVStations)) {
                break;
            }
            Utils.printMessage("< File doesn't exist or isn't a CSV >");
        }

        String pathCSVLines;
        while (true) {
            pathCSVLines = Utils.readLineFromConsole("Lines file path: ");
            if (pathCSVLines.equals("0")) {
                return;
            }
            if (Utils.isValidCSVFile(pathCSVLines)) {
                break;
            }
            Utils.printMessage("< File doesn't exist or isn't a CSV >");
        }

        controller.setPathCSVStations(pathCSVStations);
        controller.setPathCSVLines(pathCSVLines);
        controller.readCSVScenario();
        controller.setIsValidRead();
    }

    /**
     * Gets the controller responsible for reading the scenario.
     *
     * @return the ReadCSV_Controller instance
     */
    public ReadCSV_Controller getController() {
        return controller;
    }

    /**
     * Sets the controller responsible for reading the scenario.
     *
     * @param controller the ReadCSV_Controller instance to set
     */
    public void setController(ReadCSV_Controller controller) {
        this.controller = controller;
    }
}