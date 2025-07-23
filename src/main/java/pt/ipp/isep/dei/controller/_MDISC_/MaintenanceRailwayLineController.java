package pt.ipp.isep.dei.controller._MDISC_;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils_MDISC;

import java.io.IOException;
import java.util.*;

/**
 * Controller responsible for managing maintenance operations over a railway line network.
 * Supports the construction and verification of Eulerian paths, filtering of railway lines,
 * and graph connectivity checks for the railway network.
 */
public class MaintenanceRailwayLineController {

    // ================================
    // === Fields =====================
    // ================================

    private List<Station> stationList;
    private List<Station> validStations = new ArrayList<>();
    private List<String> oddDegreeStations = new ArrayList<>();
    private List<RailwayLine> allLines;
    private List<RailwayLine> availableLines;
    private List<Station> selectedStartingStations = new ArrayList<>();
    private List<Integer> vertexDegrees = new ArrayList<>();
    private String scenarioName;
    private Station firstStationAlgorithm;
    private String railwayLinesType;
    private boolean semiEulerian = false;
    private boolean eulerian = false;
    private List<Station> finalMaintenancePath;

    // ================================
    // === Constructor ================
    // ================================

    /**
     * Constructs the controller with the specified station list, railway lines and scenario name.
     *
     * @param stationList  list of stations
     * @param lines        list of all railway lines
     * @param scenarioName name of the scenario
     */
    public MaintenanceRailwayLineController(List<Station> stationList, List<RailwayLine> lines, String scenarioName) {
        this.stationList = stationList;
        this.allLines = lines;
        this.availableLines = new ArrayList<>(lines);
        this.scenarioName = scenarioName;
    }

    // ================================
    // === Getters & Setters ==========
    // ================================

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public List<RailwayLine> getAllLines() {
        return allLines;
    }

    public void setAllLines(List<RailwayLine> allLines) {
        this.allLines = allLines;
    }

    public List<RailwayLine> getAvailableLines() {
        return availableLines;
    }

    public void setAvailableLines(List<RailwayLine> availableLines) {
        this.availableLines = availableLines;
    }

    public List<Station> getSelectedStartingStations() {
        return selectedStartingStations;
    }

    public void setSelectedStartingStations(List<Station> selectedStartingStations) {
        this.selectedStartingStations = selectedStartingStations;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public Station getFirstStationAlgorithm() {
        return firstStationAlgorithm;
    }

    public void setFirstStationAlgorithm(Station firstStation) {
        this.firstStationAlgorithm = firstStation;
    }

    public String getRailwayLinesType() {
        return railwayLinesType;
    }

    public void setRailwayLinesType(String railwayLinesType) {
        this.railwayLinesType = railwayLinesType;
    }

    public List<String> getOddDegreeStations() {
        return oddDegreeStations;
    }

    public List<Station> getValidStations() {
        return validStations;
    }

    public List<Station> getFinalMaintenancePath() {
        return finalMaintenancePath;
    }

    // ================================
    // === Station List Helpers =======
    // ================================

    /**
     * Returns the number of stations in the main list.
     */
    public int getSizeStationList() {
        return stationList.size();
    }

    /**
     * Returns the station at the specified index.
     */
    public Station getStationByIndex(int index) {
        return stationList.get(index);
    }

    /**
     * Returns the number of starting stations selected.
     */
    public int getSizeStartingStationsList() {
        return selectedStartingStations.size();
    }

    /**
     * Returns the number of valid stations.
     */
    public int getSizeValidStationsList() {
        return validStations.size();
    }

    /**
     * Converts a list of stations to their corresponding names.
     */
    public List<String> getStationsNamesList() {
        return convertStationsToNames(stationList);
    }

    /**
     * Adds a station to the list of selected starting stations.
     */
    public void addStationToStartingStations(Station station) {
        selectedStartingStations.add(station);
    }

    /**
     * Returns the last selected starting station.
     */
    public Station getLastStartingStation() {
        return selectedStartingStations.getLast();
    }

    /**
     * Converts a list of stations to a list of their names.
     */
    public List<String> convertStationsToNames(List<Station> stations) {
        List<String> names = new ArrayList<>();
        for (Station station : stations) {
            names.add(station.getName());
        }
        return names;
    }

    /**
     * Returns a new list by removing specified stations from the given list.
     */
    public List<Station> removeStationsInList(List<Station> stations, List<Station> stationsRemoves) {
        if (stations == null || stations.isEmpty()) {
            return new ArrayList<>();
        }
        List<Station> finalStations = new ArrayList<>(stations);
        for (Station stationRemove : stationsRemoves) {
            for (Station station : stations) {
                if (station.getName().equals(stationRemove.getName())) {
                    finalStations.remove(station);
                }
            }
        }
        return finalStations;
    }

    /**
     * Returns a station with the specified name from the list.
     */
    public Station getStationByName(String name) {
        for (Station station : stationList) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }

    // ================================
    // === Railway Line Logic =========
    // ================================

    /**
     * Builds and returns the adjacency matrix for the railway network.
     */
    public int[][] buildAdjacencyMatrix() {
        return Utils_MDISC.buildAdjacencyMatrix(stationList, availableLines);
    }

    /**
     * Returns the railway line connecting two stations, if it exists.
     */
    public RailwayLine getRailwayLineBetweenStations(String station1, String station2) {
        for (RailwayLine line : availableLines) {
            if ((line.getNameStation1().equals(station1) && line.getNameStation2().equals(station2)) ||
                    (line.getNameStation2().equals(station1) && line.getNameStation1().equals(station2))) {
                return line;
            }
        }
        return null;
    }

    /**
     * Removes the specified railway line from the list of available lines.
     */
    public void removeLineFromAvailableLines(RailwayLine line) {
        if (!availableLines.contains(line)) {
            throw new IllegalArgumentException("Line is not available");
        }
        availableLines.remove(line);
    }

    /**
     * Filters the available railway lines based on the selected type.
     */
    public void filterRailwayLinesType() {
        if (railwayLinesType == null) {
            throw new IllegalArgumentException("Railway lines type cannot be null");
        }
        if (railwayLinesType.contains("All")) {
            return;
        }
        List<RailwayLine> linesCurrentlyAvailable = new ArrayList<>(availableLines);
        for (RailwayLine line : linesCurrentlyAvailable) {
            String type = line.getTypeEnum().getType();
            if ((!railwayLinesType.contains("Non") && type.contains("Non")) ||
                    (railwayLinesType.contains("Non") && !type.contains("Non"))) {
                removeLineFromAvailableLines(line);
            }
        }
    }

    // ================================
    // === Eulerian Path ==============
    // ================================

    /**
     * Determines if the graph is Eulerian, semi-Eulerian, or not Eulerian at all.
     */
    public boolean setEulerianPathType() {
        findStationsWithOddDegree();
        semiEulerian = (oddDegreeStations.size() == 2);
        eulerian = oddDegreeStations.isEmpty();
        return !semiEulerian && !eulerian;
    }

    /**
     * Returns the type of Eulerian graph.
     */
    public String typeOfEulerianGraph() {
        if (eulerian) return "Eulerian graph";
        if (semiEulerian) return "Semi-Eulerian graph";
        return "Not Eulerian graph";
    }

    /**
     * Returns the list of valid initial stations for Eulerian path traversal.
     */
    public List<Station> getValidInitialStations() {
        if (!semiEulerian && !eulerian) {
            throw new IllegalArgumentException("Eulerian path is not valid");
        }
        if (semiEulerian) {
            List<Station> initialStations = new ArrayList<>();
            for (String oddDegreeStation : oddDegreeStations) {
                initialStations.add(getStationByName(oddDegreeStation));
            }
            return initialStations;
        }
        return validStations;
    }

    /**
     * Sets and validates the final Eulerian maintenance path.
     */
    public boolean setFinalPath() {
        List<Station> eulerianPath = findEulerianMaintenancePath();
        if (eulerianPath.isEmpty()) return true;

        finalMaintenancePath = new ArrayList<>(selectedStartingStations);
        if (!finalMaintenancePath.isEmpty()) {
            finalMaintenancePath.removeLast();
        }
        finalMaintenancePath.addAll(eulerianPath);
        return false;
    }

    /**
     * Finds the final maintenance path based on Eulerian path logic.
     */
    public List<Station> findEulerianMaintenancePath() {
        for (int i = 1; i < selectedStartingStations.size(); i++) {
            RailwayLine toRemove = getRailwayLineBetweenStations(
                    selectedStartingStations.get(i).getName(),
                    selectedStartingStations.get(i - 1).getName()
            );
            removeLineFromAvailableLines(toRemove);
        }
        Station start = getLastStartingStation();
        List<Station> path = findEulerianPath(start, availableLines);
        if (path.size() < availableLines.size() + 1) {
            return new ArrayList<>();
        }
        return path;
    }

    private void findStationsWithOddDegree() {
        int[][] adjacencyMatrix = buildAdjacencyMatrix();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int degree = 0;
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                degree += adjacencyMatrix[i][j];
            }
            if (degree % 2 != 0) {
                oddDegreeStations.add(stationList.get(i).getName());
            }
        }
    }

    public void calculateVertexDegrees() {
        int[][] adjacencyMatrix = buildAdjacencyMatrix();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int degree = 0;
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                degree += adjacencyMatrix[i][j];
            }
            vertexDegrees.add(degree);
        }
    }

    public boolean isNotGraphConnected() {
        if (availableLines == null || availableLines.isEmpty()) return true;

        List<String> visitedStations = new ArrayList<>();
        List<String> stationsToVisit = new ArrayList<>();

        RailwayLine firstLine = availableLines.getFirst();
        String startStation = firstLine.getNameStation1();
        stationsToVisit.add(startStation);

        while (!stationsToVisit.isEmpty()) {
            String current = stationsToVisit.removeLast();
            if (!visitedStations.contains(current)) {
                visitedStations.add(current);

                for (RailwayLine line : availableLines) {
                    if (line.getNameStation1().equals(current)) {
                        String neighbor = line.getNameStation2();
                        if (!visitedStations.contains(neighbor)) stationsToVisit.add(neighbor);
                    } else if (line.getNameStation2().equals(current)) {
                        String neighbor = line.getNameStation1();
                        if (!visitedStations.contains(neighbor)) stationsToVisit.add(neighbor);
                    }
                }
            }
        }

        List<String> allStationsInGraph = new ArrayList<>();
        for (RailwayLine line : availableLines) {
            if (!allStationsInGraph.contains(line.getNameStation1())) allStationsInGraph.add(line.getNameStation1());
            if (!allStationsInGraph.contains(line.getNameStation2())) allStationsInGraph.add(line.getNameStation2());
        }

        for (String s : allStationsInGraph) {
            if (!visitedStations.contains(s)) return true;
        }
        return false;
    }

    private List<Station> findEulerianPath(Station start, List<RailwayLine> allLines) {
        List<RailwayLine> copyLines = new ArrayList<>(allLines);
        List<Station> path = new ArrayList<>();
        List<Station> stack = new ArrayList<>();
        stack.add(start);

        while (!stack.isEmpty()) {
            Station current = stack.getLast();
            RailwayLine line = findUnvisitedLine(current, copyLines);
            if (line != null) {
                Station next = getOppositeStation(current, line);
                stack.add(next);
                copyLines.remove(line);
            } else {
                path.add(stack.removeLast());
            }
        }

        List<Station> reversedPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }
        return reversedPath;
    }

    private RailwayLine findUnvisitedLine(Station station, List<RailwayLine> lines) {
        for (RailwayLine line : lines) {
            if (line.getNameStation1().equals(station.getName()) || line.getNameStation2().equals(station.getName())) {
                return line;
            }
        }
        return null;
    }

    private Station getOppositeStation(Station station, RailwayLine line) {
        String name = station.getName();
        if (line.getNameStation1().equals(name)) return getStationByName(line.getNameStation2());
        if (line.getNameStation2().equals(name)) return getStationByName(line.getNameStation1());
        return null;
    }

    // ================================
    // === Station Connections ========
    // ================================

    /**
     * Sets the valid stations that have at least one connected railway line.
     */
    public void setValidStationsConnectedLines() {
        for (Station station : stationList) {
            if (findUnvisitedLine(station, availableLines) != null) {
                validStations.add(station);
            }
        }
    }

    /**
     * Returns the list of stations connected to the specified station.
     */
    public List<Station> getStationsConnectedAnotherStation(Station station) {
        List<Station> stations = new ArrayList<>();
        for (RailwayLine line : availableLines) {
            if (line.getNameStation1().equals(station.getName())) {
                stations.add(line.getStation2());
            } else if (line.getNameStation2().equals(station.getName())) {
                stations.add(line.getStation1());
            }
        }
        return stations;
    }

    // ================================
    // === Visualization ==============
    // ================================

    /**
     * Generates visual representations of the railway network on stream and in a file.
     */
    public void visualisationOfRailwayNetwork() throws IOException {
        Utils_MDISC.visualizeRailwayNetworkStream(
                findSameTypeStations('D', stationList),
                findSameTypeStations('S', stationList),
                findSameTypeStations('T', stationList),
                allLines
        );
        Utils_MDISC.visualizeRailwayNetworkFile(stationList, allLines, "FullRailwayNetwork_" + scenarioName);
    }

    /**
     * Returns the list of stations of a given type.
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
}
