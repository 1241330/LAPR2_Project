package pt.ipp.isep.dei.controller._MDISC_;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils_MDISC;

import java.util.*;

/**
 * Controller that handles connectivity between stations using available railway lines.
 */
public class ConnectivityBetweenStationsController {

    private List<Station> stationList;
    private List<RailwayLine> allLines;
    private List<RailwayLine> availableLines;
    private String scenarioName;
    private String selectedTrainType;

    // ===============================
    // ========= Constructor =========
    // ===============================

    /**
     * Controller constructor that receives the stations, railway lines, and scenario name.
     *
     * @param stationList list of stations.
     * @param lines list of all railway lines.
     * @param scenarioName name of the scenario.
     */
    public ConnectivityBetweenStationsController(List<Station> stationList, List<RailwayLine> lines, String scenarioName) {
        this.stationList = stationList;
        this.allLines = lines;
        this.availableLines = new ArrayList<>(lines);
        this.scenarioName = scenarioName;
    }

    // ===============================
    // ====== Getters & Setters ======
    // ===============================

    /**
     * Gets the list of stations.
     *
     * @return list of stations.
     */
    public List<Station> getStationList() {
        return stationList;
    }

    /**
     * Sets the list of stations.
     *
     * @param stationList list of stations.
     */
    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    /**
     * Gets all railway lines.
     *
     * @return list of all lines.
     */
    public List<RailwayLine> getAllLines() {
        return allLines;
    }

    /**
     * Sets all railway lines.
     *
     * @param allLines list of railway lines.
     */
    public void setAllLines(List<RailwayLine> allLines) {
        this.allLines = allLines;
    }

    /**
     * Gets the available railway lines.
     *
     * @return list of available lines.
     */
    public List<RailwayLine> getAvailableLines() {
        return availableLines;
    }

    /**
     * Sets the available railway lines.
     *
     * @param availableLines list of available lines.
     */
    public void setAvailableLines(List<RailwayLine> availableLines) {
        this.availableLines = availableLines;
    }

    /**
     * Gets the scenario name.
     *
     * @return scenario name.
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * Sets the scenario name.
     *
     * @param scenarioName scenario name.
     */
    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    /**
     * Gets the number of stations in the list.
     *
     * @return size of the station list.
     */
    public int getSizeStationList() {
        return stationList.size();
    }

    /**
     * Sets the selected train type for filtering.
     *
     * @param selectedTrainType train type.
     */
    public void setTrainType(String selectedTrainType) {
        this.selectedTrainType = selectedTrainType;
    }

    // ===============================
    // ========= Core Logic ==========
    // ===============================

    /**
     * Filters the available railway lines based on the selected train type.
     * If the type is "All", no filtering is performed.
     *
     * @throws IllegalArgumentException if the selected train type is null.
     */
    public void filterRailwayLinesType() {
        if (selectedTrainType == null) {
            throw new IllegalArgumentException("Train type cannot be null");
        }
        if (selectedTrainType.contains("All")) {
            return;
        }

        List<RailwayLine> currentAvailableLines = new ArrayList<>(availableLines);
        for (RailwayLine line : currentAvailableLines) {
            String type = line.getTypeEnum().getType();
            if ((!selectedTrainType.contains("Non") && type.contains("Non")) ||
                    (selectedTrainType.contains("Non") && !type.contains("Non"))) {
                removeLineFromAvailableLines(line);
            }
        }
    }

    /**
     * Removes a railway line from the list of available lines.
     *
     * @param line railway line to remove.
     * @throws IllegalArgumentException if the line is not available.
     */
    public void removeLineFromAvailableLines(RailwayLine line) {
        if (!availableLines.contains(line)) {
            throw new IllegalArgumentException("Line is not available");
        }
        availableLines.remove(line);
    }

    /**
     * Builds the adjacency matrix of stations based on available railway lines.
     *
     * @return adjacency matrix (int[][]).
     */
    public int[][] buildAdjacencyMatrix() {
        return Utils_MDISC.buildAdjacencyMatrix(stationList, availableLines);
    }

    // ===============================
    // ===== Connectivity Search =====
    // ===============================

    /**
     * Reconstructs the path between stations from a predecessors array.
     *
     * @param previous array with indices of predecessors.
     * @param originIndex index of the origin station.
     * @param destinationIndex index of the destination station.
     * @return list of stations that compose the path.
     */
    private List<Station> reconstructPath(int[] previous, int originIndex, int destinationIndex) {
        List<Station> path = new ArrayList<>();
        for (int at = destinationIndex; at != originIndex; at = previous[at]) {
            path.add(stationList.get(at));
        }
        path.add(stationList.get(originIndex));
        Collections.reverse(path);
        return path;
    }

    /**
     * Finds stations whose name starts with a specific character.
     *
     * @param type initial character of the station name.
     * @return list of stations matching this type.
     */
    public List<Station> findSameTypeStations(char type) {
        List<Station> sameTypeStations = new ArrayList<>();
        for (Station station : stationList) {
            if (station.getName().charAt(0) == type) {
                sameTypeStations.add(station);
            }
        }
        return sameTypeStations;
    }

    /**
     * Checks if all valid stations are connected to each other.
     *
     * @param impossibleStationType array of characters representing station types that are impossible to connect.
     * @return {@code true} if all valid stations are connected, {@code false} otherwise.
     */
    public boolean verifyConnectivity(char[] impossibleStationType) {
        int stationCount = stationList.size();
        boolean[] isImpossible = new boolean[stationCount];
        int[][] transitiveClosureMatrix = getTransitiveClosureMatrix();
        fillImpossibleStations(impossibleStationType, stationList, isImpossible);

        for (int i = 0; i < stationCount; i++) {
            if (isImpossible[i]) continue;

            for (int j = 0; j < stationCount; j++) {
                if (isImpossible[j]) continue;

                if (transitiveClosureMatrix[i][j] == 0) {
                    return false; // Valid stations i and j are not connected
                }
            }
        }

        return true; // All valid stations are connected
    }

    /**
     * Marks which stations are considered impossible to connect, based on given types.
     *
     * @param impossibleStationType array of impossible station types.
     * @param stationList list of stations.
     * @param isImpossible boolean array indicating which stations are impossible.
     */
    public void fillImpossibleStations(char[] impossibleStationType, List<Station> stationList, boolean[] isImpossible) {
        for (int i = 0; i < stationList.size(); i++) {
            char stationType = stationList.get(i).getName().charAt(0);
            for (char impossibleType : impossibleStationType) {
                if (stationType == impossibleType) {
                    isImpossible[i] = true;
                    break;
                }
            }
        }
    }

    /**
     * Calculates the transitive closure matrix to determine connectivity between stations.
     *
     * @return transitive closure matrix (int[][]).
     */
    public int[][] getTransitiveClosureMatrix() {
        int size = stationList.size();
        int[][] closure = buildAdjacencyMatrix();

        // Warshall's algorithm
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    closure[i][j] = (closure[i][j] == 1 || (closure[i][k] == 1 && closure[k][j] == 1)) ? 1 : 0;
                }
            }
        }

        return closure;
    }

    /**
     * Checks if two stations are connected according to the transitive closure.
     *
     * @param origin origin station.
     * @param destination destination station.
     * @return {@code true} if they are connected, {@code false} otherwise.
     * @throws IllegalArgumentException if any station is not in the list.
     */
    public boolean isConnectedTransitive(Station origin, Station destination) {
        int[][] closure = getTransitiveClosureMatrix();
        int originIndex = stationList.indexOf(origin);
        int destinationIndex = stationList.indexOf(destination);

        if (originIndex == -1 || destinationIndex == -1) {
            throw new IllegalArgumentException("Stations not found in the list");
        }

        return closure[originIndex][destinationIndex] == 1;
    }
}
