package pt.ipp.isep.dei.matcp;

import java.io.*;
import java.nio.file.*;
import java.util.Locale;
import java.util.*;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.controller._MDISC_.ConnectivityBetweenStationsController;

/**
 * This class benchmarks the execution time of the connectivity verification algorithm
 * for different railway network scenarios. It loads station and line data from CSV files,
 * runs the connectivity check, and writes the results to a CSV file.
 */
public class US13ExecutionTime {

    /**
     * Main method to execute the benchmark for all scenarios found in the data directory.
     *
     * @param args command line arguments (not used)
     * @throws IOException if there is an error reading or writing files
     */
    public static void main(String[] args) throws IOException {
        // Directory containing the input data files
        String dataDir = "MATCP/Cargo and Algorithm Statistical Analysis - SPRINT 3/SPRINT 3/US29/US29_data";
        // Directory where the results will be saved
        String resultsDir = "MATCP/Cargo and Algorithm Statistical Analysis - SPRINT 3/SPRINT 3/US29/US29_csv_results";
        // Output CSV file path
        String resultsFile = resultsDir + "/US13_results.csv";

        // List all station CSV files in the data directory
        List<Path> stationFiles = Files.list(Paths.get(dataDir))
                .filter(p -> p.getFileName().toString().endsWith("_stations.csv"))
                .toList();

        // Prepare the output lines for the CSV file
        List<String> outputLines = new ArrayList<>();
        outputLines.add("size, time");

        // For each scenario (station file)
        for (Path stationFile : stationFiles) {
            // Get the base name to find the corresponding lines file
            String baseName = stationFile.getFileName().toString().replace("_stations.csv", "");
            Path linesFile = Paths.get(dataDir, baseName + "_lines.csv");

            // Load stations and lines from CSV files
            List<Station> stations = loadStations(stationFile);
            List<RailwayLine> lines = loadLines(linesFile, stations);

            // Create the controller for connectivity verification
            ConnectivityBetweenStationsController controller =
                    new ConnectivityBetweenStationsController(stations, lines, baseName);

            // No excluded types for this benchmark
            char[] excludedTypes = new char[0];

            // Measure the execution time of the connectivity verification
            long start = System.nanoTime();
            controller.verifyConnectivity(excludedTypes);
            long end = System.nanoTime();

            double timeSeconds = (end - start) / 1_000_000_000.0;
            outputLines.add(String.format(Locale.US, "%d,%.3f", stations.size(), timeSeconds));
        }

        // Write all results to the CSV file
        Files.write(Paths.get(resultsFile), outputLines);

        // Optionally, print the results to the console
        for (String line : outputLines) {
            System.out.println(line);
        }
    }

    /**
     * Loads the list of stations from a CSV file (first line, comma-separated).
     *
     * @param file the path to the stations CSV file
     * @return a list of Station objects
     * @throws IOException if there is an error reading the file
     */
    private static List<Station> loadStations(Path file) throws IOException {
        String line = Files.readAllLines(file).get(0);
        String[] names = line.split(",");
        List<Station> stations = new ArrayList<>();
        for (String name : names) stations.add(new Station(name.trim()));
        return stations;
    }

    /**
     * Loads the list of railway lines from a CSV file (each line: station1;station2;typeId).
     *
     * @param file the path to the lines CSV file
     * @param stations the list of Station objects to match by name
     * @return a list of RailwayLine objects
     * @throws IOException if there is an error reading the file
     */
    private static List<RailwayLine> loadLines(Path file, List<Station> stations) throws IOException {
        Map<String, Station> stationMap = new HashMap<>();
        for (Station s : stations) stationMap.put(s.getName(), s);
        List<RailwayLine> lines = new ArrayList<>();
        for (String l : Files.readAllLines(file)) {
            String[] parts = l.split(";");
            Station s1 = stationMap.get(parts[0]);
            Station s2 = stationMap.get(parts[1]);
            int typeId = Integer.parseInt(parts[2]);
            RailwayLineType type = getRailwayLineTypeById(typeId);
            lines.add(new RailwayLine(s1, s2, type));
        }
        return lines;
    }

    /**
     * Finds the RailwayLineType by its ID.
     *
     * @param id the ID of the railway line type
     * @return the corresponding RailwayLineType
     * @throws IllegalArgumentException if the type ID is unknown
     */
    private static RailwayLineType getRailwayLineTypeById(int id) {
        for (RailwayLineType t : RailwayLineType.values()) {
            if (t.getId() == id) return t;
        }
        throw new IllegalArgumentException("Unknown railway line type: " + id);
    }
}

