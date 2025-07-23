package pt.ipp.isep.dei.controller._MDISC_;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller responsible for reading scenario data from CSV files,
 * including stations and railway lines.
 */
public class ReadCSV_Controller {

    private static String pathCSVStations;
    private static String pathCSVLines;
    private static List<Station> stationsList = new ArrayList<>();
    private static List<RailwayLine> linesList = new ArrayList<>();
    private static boolean isValidRead = false;

    /**
     * Default constructor.
     */
    public ReadCSV_Controller() {
    }

    /**
     * Gets the path to the stations CSV file.
     *
     * @return the path to the stations CSV file
     */
    public String getPathCSVStations() {
        return pathCSVStations;
    }

    /**
     * Sets the path to the stations CSV file.
     *
     * @param pathCSVStations the path to the stations CSV file
     */
    public void setPathCSVStations(String pathCSVStations) {
        ReadCSV_Controller.pathCSVStations = pathCSVStations;
    }

    /**
     * Gets the path to the lines CSV file.
     *
     * @return the path to the lines CSV file
     */
    public String getPathCSVLines() {
        return pathCSVLines;
    }

    /**
     * Sets the path to the lines CSV file.
     *
     * @param pathCSVLines the path to the lines CSV file
     */
    public void setPathCSVLines(String pathCSVLines) {
        ReadCSV_Controller.pathCSVLines = pathCSVLines;
    }

    /**
     * Gets the list of stations read from the CSV file.
     *
     * @return the list of stations
     */
    public static List<Station> getStationsList() {
        return stationsList;
    }

    /**
     * Sets the list of stations.
     *
     * @param stationsList the list of stations
     */
    public void setStationsList(List<Station> stationsList) {
        ReadCSV_Controller.stationsList = stationsList;
    }

    /**
     * Gets the list of railway lines read from the CSV file.
     *
     * @return the list of railway lines
     */
    public static List<RailwayLine> getLinesList() {
        return linesList;
    }

    /**
     * Sets the list of railway lines.
     *
     * @param linesList the list of railway lines
     */
    public void setLinesList(List<RailwayLine> linesList) {
        ReadCSV_Controller.linesList = linesList;
    }

    /**
     * Checks if the last read operation was valid.
     *
     * @return true if the read was valid, false otherwise
     */
    public static boolean isValidRead() {
        return isValidRead;
    }

    /**
     * Sets the read operation as valid.
     */
    public void setIsValidRead() {
        isValidRead = true;
    }

    /**
     * Gets the file name from the path of the lines CSV file.
     *
     * @return the file name, or null if the path is not set
     */
    public static String getFileName() {
        if (pathCSVLines == null) {
            return null;
        }
        return Utils.getFileName(pathCSVLines);
    }

    /**
     * Reads the scenario from the CSV files, populating the stations and lines lists.
     * Prints messages if files are missing or empty.
     */
    public void readCSVScenario() {
        stationsList.clear();
        linesList.clear();

        if (pathCSVStations == null || pathCSVLines == null) {
            Utils.printMessage("< Path is null >");
            return;
        }

        String[][] stations = readCSV(pathCSVStations);
        if (stations == null) {
            Utils.printMessage("< Stations file empty >");
            return;
        }

        for (int i = 0; i < stations.length; i++) {
            if (stations[0][i] != null) {
                stationsList.add(new Station(stations[0][i]));
            }
        }

        String[][] lines = readCSV(pathCSVLines);
        if (lines == null) {
            Utils.printMessage("< Lines file empty >");
            return;
        }

        for (int i = 0; i < lines.length; i++) {
            if (lines[i][0] != null && lines[i][1] != null && lines[i][2] != null) {
                Station s1 = new Station(lines[i][0]);
                Station s2 = new Station(lines[i][1]);
                int typeId = Integer.parseInt(lines[i][2]);
                RailwayLineType type = switch (typeId) {
                    case 1 -> RailwayLineType.SINGLE_ELECTRIFIED;
                    case 0 -> RailwayLineType.SINGLE_NON_ELECTRIFIED;
                    default -> throw new IllegalArgumentException("Invalid railway line type ID in CSV");
                };
                RailwayLine railwayLine = new RailwayLine(s1, s2, type);
                railwayLine.setDistance(Integer.parseInt(lines[i][3]));
                linesList.add(railwayLine);
            }
        }
    }

    /**
     * Reads a CSV file and returns its contents as a matrix of strings.
     *
     * @param path the path to the CSV file
     * @return a matrix of strings with the file contents, or null if not found
     */
    private String[][] readCSV(String path) {
        File file = new File(path);
        String[][] matrix = new String[500][500];
        try (Scanner scanner = new Scanner(file)) {
            int nLine = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] array = line.split(";");
                for (int i = 0; i < array.length; i++) {
                    matrix[nLine][i] = array[i];
                }
                nLine++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File didn't find!");
            e.printStackTrace();
            return null;
        }
        return matrix;
    }
}