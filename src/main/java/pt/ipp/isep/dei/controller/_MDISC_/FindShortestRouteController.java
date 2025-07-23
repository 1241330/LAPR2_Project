package pt.ipp.isep.dei.controller._MDISC_;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils_MDISC;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller responsible for finding the shortest route between stations,
 * filtering railway lines by train type, and managing the pathfinding logic.
 */
public class FindShortestRouteController {

    private List<Station> stationsList;
    private List<RailwayLine> railwayLinesList;
    private List<RailwayLine> filteredLines = new ArrayList<>();
    private List<Station> connectedStations = new ArrayList<>();
    private List<Station> reachableStations = new ArrayList<>();
    private List<Station> intermediateStations = new ArrayList<>();
    private Integer[][] weightMatrix;
    private String csvPath;
    private List<Station> path = new ArrayList<>();
    private int lengthOfPath;
    private boolean isElectricTrain;
    private Station startStation;
    private Station finalStation;

    /**
     * Constructor for FindShortestRouteController.
     *
     * @param stationsList     List of all stations.
     * @param railwayLinesList List of all railway lines.
     */
    public FindShortestRouteController(List<Station> stationsList, List<RailwayLine> railwayLinesList) {
        this.stationsList = stationsList;
        this.railwayLinesList = railwayLinesList;
    }

    /**
     * Sets whether the train is electric and filters lines accordingly.
     *
     * @param isElectricTrain true if the train is electric, false otherwise.
     */
    public void setElectricTrain(boolean isElectricTrain) {
        this.isElectricTrain = isElectricTrain;
        filterLinesByTrainType();
    }

    /**
     * Filters railway lines based on the train type.
     */
    public void filterLinesByTrainType() {
        for (RailwayLine line : railwayLinesList) {
            if (!isElectricTrain || line.getTypeEnum().getId() == 0) {
                filteredLines.add(line);
            }
        }
    }

    /**
     * Checks if the connected stations list is empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean connectedStationsIsEmpty() {
        return connectedStations.isEmpty();
    }

    /**
     * Checks if the reachable stations list is empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean reachableStationsIsEmpty() {
        return reachableStations.isEmpty();
    }

    /**
     * Checks if the path is empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean pathIsEmpty() {
        return path.isEmpty();
    }

    /**
     * Gets the list of connected stations.
     *
     * @return List of connected stations.
     */
    public List<Station> getConnectedStations() {
        return connectedStations;
    }

    /**
     * Gets the list of reachable stations.
     *
     * @return List of reachable stations.
     */
    public List<Station> getReachableStations() {
        return reachableStations;
    }

    /**
     * Sets the start station.
     *
     * @param startStation The start station.
     */
    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    /**
     * Sets the CSV path for reading stations.
     *
     * @param csvPath Path to the CSV file.
     */
    public void setCsvPath(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Sets the final station.
     *
     * @param finalStation The final station.
     */
    public void setFinalStation(Station finalStation) {
        this.finalStation = finalStation;
    }

    /**
     * Gets the start station.
     *
     * @return The start station.
     */
    public Station getStartStation() {
        return startStation;
    }

    /**
     * Gets the final station.
     *
     * @return The final station.
     */
    public Station getFinalStation() {
        return finalStation;
    }

    /**
     * Gets the current path.
     *
     * @return List of stations representing the path.
     */
    public List<Station> getPath() {
        return path;
    }

    /**
     * Adds an intermediate station to the list.
     *
     * @param station The station to add.
     */
    public void addIntermediateStation(Station station) {
        if (!intermediateStations.contains(station)) {
            intermediateStations.add(station);
        } else {
            throw new IllegalArgumentException("Station already exists in the list of intermediate stations.");
        }
    }

    /**
     * Removes a station from the reachable stations list.
     *
     * @param station The station to remove.
     */
    public void removeStationFromReachable(Station station) {
        for (int i = reachableStations.size() - 1; i >= 0; i--) {
            Station actualStation = reachableStations.get(i);
            if (actualStation.getName().equals(station.getName())) {
                reachableStations.remove(i);
            }
        }
    }

    /**
     * Finds the best path including start, intermediate, and final stations.
     */
    public void findBestPath() {
        List<Station> orderedStations = new ArrayList<>();
        orderedStations.add(startStation);
        orderedStations.addAll(intermediateStations);
        orderedStations.add(finalStation);

        List<Station> currentPath = new ArrayList<>();
        boolean valid = true;
        int totalDistance = 0;

        for (int i = 0; i < orderedStations.size() - 1; i++) {
            Station from = orderedStations.get(i);
            Station to = orderedStations.get(i + 1);

            List<Station> subPath = dijkstra(stationsList, filteredLines, from, to);
            if (subPath.isEmpty()) {
                valid = false;
                break;
            }

            totalDistance += calculatePathDistance(subPath, filteredLines);

            if (i > 0) {
                subPath.removeFirst();
            }

            currentPath.addAll(subPath);
        }

        if (valid) {
            path = currentPath;
            lengthOfPath = totalDistance;
        } else {
            path.clear();
            lengthOfPath = 0;
        }
    }

    /**
     * Sets the list of reachable stations from the start station.
     */
    public void setReachableStations() {
        for (Station actualStation : stationsList) {
            List<Station> path = dijkstra(stationsList, filteredLines, startStation, actualStation);
            if (!path.isEmpty()) {
                reachableStations.add(actualStation);
            }
        }
    }

    /**
     * Calculates the total distance of a path.
     *
     * @param path  List of stations in the path.
     * @param lines List of railway lines.
     * @return Total distance.
     */
    private int calculatePathDistance(List<Station> path, List<RailwayLine> lines) {
        int total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Station s1 = path.get(i);
            Station s2 = path.get(i + 1);
            for (RailwayLine line : lines) {
                if ((line.getStation1().getName().equals(s1.getName()) &&
                        line.getStation2().getName().equals(s2.getName())) ||
                        (line.getStation1().getName().equals(s2.getName()) &&
                                line.getStation2().getName().equals(s1.getName()))) {
                    total += line.getDistance();
                    break;
                }
            }
        }
        return total;
    }

    /**
     * Creates the weight matrix representing distances between stations.
     */
    public void createWeightMatrix() {
        int n = stationsList.size();
        weightMatrix = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weightMatrix[i][j] = null;
            }
        }

        for (RailwayLine line : railwayLinesList) {
            int index1 = -1, index2 = -1;
            for (int i = 0; i < n; i++) {
                if (stationsList.get(i).getName().equals(line.getStation1().getName())) {
                    index1 = i;
                }
                if (stationsList.get(i).getName().equals(line.getStation2().getName())) {
                    index2 = i;
                }
            }
            if (index1 != -1 && index2 != -1) {
                weightMatrix[index1][index2] = line.getDistance();
                weightMatrix[index2][index1] = line.getDistance();
            }
        }
    }

    /**
     * Dijkstra's algorithm to find the shortest path between two stations.
     *
     * @param stations     List of stations.
     * @param railwayLines List of railway lines.
     * @param start        Start station.
     * @param end          End station.
     * @return List of stations representing the shortest path.
     */
    private List<Station> dijkstra(List<Station> stations, List<RailwayLine> railwayLines, Station start, Station end) {
        int n = stations.size();
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
            prev[i] = -1;
        }

        int startIndex = -1, endIndex = -1;
        for (int i = 0; i < n; i++) {
            if (stations.get(i).getName().equals(start.getName())) startIndex = i;
            if (stations.get(i).getName().equals(end.getName())) endIndex = i;
        }

        if (startIndex == -1 || endIndex == -1) return new ArrayList<>();

        dist[startIndex] = 0;

        while (true) {
            int u = -1, minDist = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    u = i;
                }
            }
            if (u == -1 || u == endIndex) break;

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (weightMatrix[u][v] != null && !visited[v]) {
                    int alt = dist[u] + weightMatrix[u][v];
                    if (alt < dist[v]) {
                        dist[v] = alt;
                        prev[v] = u;
                    }
                }
            }
        }

        List<Station> path = new ArrayList<>();
        int cur = endIndex;
        while (cur != -1) {
            path.addFirst(stations.get(cur));
            cur = prev[cur];
        }

        if (!path.isEmpty() && path.getFirst().getName().equals(start.getName())) {
            return path;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Sets the list of connected stations based on filtered lines.
     */
    public void setConnectedStations() {
        for (Station station : stationsList) {
            if (hasConnection(filteredLines, station)) {
                connectedStations.add(station);
            }
        }
    }

    /**
     * Checks if a station has any connection in the given railway lines.
     *
     * @param railwayLinesList List of railway lines.
     * @param station          The station to check.
     * @return true if connected, false otherwise.
     */
    public boolean hasConnection(List<RailwayLine> railwayLinesList, Station station) {
        for (RailwayLine line : railwayLinesList) {
            if (line.getStation1().getName().equals(station.getName()) ||
                    line.getStation2().getName().equals(station.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the names of the stations from a list.
     *
     * @param stationsList List of stations.
     * @return List of station names.
     */
    public List<String> getStationsNames(List<Station> stationsList) {
        List<String> stationNames = new ArrayList<>();
        for (Station station : stationsList) {
            stationNames.add(station.getName());
        }
        return stationNames;
    }

    /**
     * Gets the railway lines used in the current path.
     *
     * @return List of railway lines used.
     */
    public List<RailwayLine> getRailwayLinesUsedInPath() {
        List<RailwayLine> usedLines = new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i++) {
            Station s1 = path.get(i);
            Station s2 = path.get(i + 1);

            for (RailwayLine line : filteredLines) {
                boolean matches =
                        (line.getStation1().getName().equals(s1.getName()) && line.getStation2().getName().equals(s2.getName())) ||
                                (line.getStation1().getName().equals(s2.getName()) && line.getStation2().getName().equals(s1.getName()));

                if (matches) {
                    usedLines.add(line);
                }
            }
        }
        return usedLines;
    }

    /**
     * Visualizes the railway network and the shortest route.
     */
    public void visualisationOfRailwayNetwork() {
        try {
            Utils_MDISC.visualizeShortestRouteStream(
                    findSameTypeStations('D', stationsList),
                    findSameTypeStations('S', stationsList),
                    findSameTypeStations('T', stationsList),
                    railwayLinesList,
                    getRailwayLinesUsedInPath()
            );
            Utils_MDISC.visualizeShortestRouteFile(stationsList, railwayLinesList, getRailwayLinesUsedInPath(), "Shortest Route");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds stations of the same type.
     *
     * @param type        The type character.
     * @param stationList List of stations.
     * @return List of stations of the same type.
     */
    public List<Station> findSameTypeStations(char type, List<Station> stationList) {
        List<Station> sameTypeStations = new ArrayList<>();
        for (Station station : stationList) {
            if (station.getName().charAt(0) == type) {
                sameTypeStations.add(station);
            }
        }
        return sameTypeStations;
    }

    /**
     * Gets the length of the current path.
     *
     * @return Length of the path.
     */
    public int getLengthOfPath() {
        return lengthOfPath;
    }

    /**
     * Sets the start, intermediate, and final stations automatically from a CSV file.
     */
    public void setAutomaticStations() {
        List<String> stationsNames = convertArrayToList(readCSV());
        startStation = findStationByName(stationsNames.getFirst());
        finalStation = findStationByName(stationsNames.getLast());
        for (int i = 1; i < stationsNames.size() - 1; i++) {
            Station station = findStationByName(stationsNames.get(i));
            if (station != null) {
                addIntermediateStation(station);
            }
        }
    }

    /**
     * Reads the CSV file and returns an array of station names.
     *
     * @return Array of station names.
     */
    private String[] readCSV() {
        File file = new File(csvPath);
        String[] array;
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextLine()) {
                System.out.println("File is empty!");
                return null;
            }
            String line = scanner.nextLine();
            array = line.split(";");
        } catch (FileNotFoundException e) {
            System.out.println("File didn't find!");
            e.printStackTrace();
            return null;
        }
        return array;
    }

    /**
     * Converts an array of strings to a list.
     *
     * @param array Array of strings.
     * @return List of strings.
     */
    private List<String> convertArrayToList(String[] array) {
        List<String> list = new ArrayList<>();
        for (String item : array) {
            list.add(item);
        }
        return list;
    }

    /**
     * Finds a station by its name in the connected stations list.
     *
     * @param name Name of the station.
     * @return The station if found, null otherwise.
     */
    private Station findStationByName(String name) {
        for (Station station : connectedStations) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }

    /**
     * Gets the weight matrix.
     *
     * @return The weight matrix.
     */
    public Integer[][] getWeightMatrix() {
        return weightMatrix;
    }

    /**
     * Gets the list of all stations.
     *
     * @return List of stations.
     */
    public List<Station> getStationsList() {
        return stationsList;
    }
}