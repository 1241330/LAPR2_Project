package pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine;

import pt.ipp.isep.dei.domain.RailwayLine.Node;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for selecting the track type and calculating the path and cost
 * when creating a new railway line in the simulation.
 */
public class SelectTrackTypeController {

    private Simulation simulation;
    private Station beginningStation;
    private Station arrivalStation;

    /**
     * Default constructor.
     */
    public SelectTrackTypeController() {}

    /**
     * Gets the current simulation.
     *
     * @return the simulation instance
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the beginning station for the new railway line.
     *
     * @return the beginning station
     */
    public Station getBeginningStation() {
        return beginningStation;
    }

    /**
     * Sets the beginning station for the new railway line.
     *
     * @param beginningStation the station to set as the beginning
     */
    public void setBeginningStation(Station beginningStation) {
        this.beginningStation = beginningStation;
    }

    /**
     * Gets the arrival station for the new railway line.
     *
     * @return the arrival station
     */
    public Station getArrivalStation() {
        return arrivalStation;
    }

    /**
     * Sets the arrival station for the new railway line.
     *
     * @param arrivalStation the station to set as the arrival
     */
    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    /**
     * Returns a list of all available railway line types.
     *
     * @return list of railway line types
     */
    public List<RailwayLineType> getRailwayTypes() {
        return List.of(RailwayLineType.values());
    }

    /**
     * Gets the cost per position for SINGLE_NON_ELECTRIFIED type.
     *
     * @return cost for SINGLE_NON_ELECTRIFIED
     */
    public int getSNEPrice() {
        return RailwayLineType.SINGLE_NON_ELECTRIFIED.getCost();
    }

    /**
     * Gets the cost per position for SINGLE_ELECTRIFIED type.
     *
     * @return cost for SINGLE_ELECTRIFIED
     */
    public int getSEPrice() {
        return RailwayLineType.SINGLE_ELECTRIFIED.getCost();
    }

    /**
     * Gets the cost per position for DOUBLE_NON_ELECTRIFIED type.
     *
     * @return cost for DOUBLE_NON_ELECTRIFIED
     */
    public int getDNEPrice() {
        return RailwayLineType.DOUBLE_NON_ELECTRIFIED.getCost();
    }

    /**
     * Gets the cost per position for DOUBLE_ELECTRIFIED type.
     *
     * @return cost for DOUBLE_ELECTRIFIED
     */
    public int getDEPrice() {
        return RailwayLineType.DOUBLE_ELECTRIFIED.getCost();
    }

    /**
     * Calculates the total cost of a railway line based on the path and type.
     *
     * @param path the list of positions representing the path
     * @param isDoubleTrack true if the line is double track
     * @param isElectrified true if the line is electrified
     * @return total cost of the railway line
     */
    public int returnRailwayCost(List<Position> path, boolean isDoubleTrack, boolean isElectrified) {
        int cost = 0;
        int costPerPosition;
        if (isDoubleTrack) {
            if (isElectrified) {
                costPerPosition = RailwayLineType.DOUBLE_ELECTRIFIED.getCost();
            } else {
                costPerPosition = RailwayLineType.DOUBLE_NON_ELECTRIFIED.getCost();
            }
        } else {
            if (isElectrified) {
                costPerPosition = RailwayLineType.SINGLE_ELECTRIFIED.getCost();
            } else {
                costPerPosition = RailwayLineType.SINGLE_NON_ELECTRIFIED.getCost();
            }
        }
        for (Position position : path) {
            cost += costPerPosition;
        }
        return cost;
    }

    /**
     * Calculates the path between the beginning and arrival stations, avoiding obstacles.
     *
     * @param type the type of railway line
     * @return list of positions representing the path
     */
    public List<Position> getPath(RailwayLineType type) {
        Position startStation = beginningStation.getPosition();
        Position endStation = arrivalStation.getPosition();

        int width = simulation.getMap().getPixelSize().getWidth();
        int height = simulation.getMap().getPixelSize().getHeight();
        List<Position> obstacles = simulation.getMap().getOccupiedPositionsWithoutRespectiveLines(type);

        Position start = findBestAdjacentPosition(startStation, obstacles, width, height, endStation);
        Position end = findBestAdjacentPosition(endStation, obstacles, width, height, startStation);

        if (start == null || end == null) {
            System.out.println("No free adjacent position to start or end station.");
        }

        obstacles.remove(start);
        obstacles.remove(end);

        List<Position> path = findPath(width, height, obstacles, start, end);

        return path;
    }

    /**
     * Directions for adjacent positions (8 directions).
     */
    private final int[][] directions = {
            {-1, 0}, {0, -1}, {0, 1}, {1, 0},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    /**
     * Finds the best adjacent position to a given station, avoiding obstacles and minimizing distance to target.
     *
     * @param pos the position of the station
     * @param obstacles list of occupied positions
     * @param width map width
     * @param height map height
     * @param target target position to minimize distance
     * @return the best adjacent position or null if none available
     */
    private Position findBestAdjacentPosition(Position pos, List<Position> obstacles, int width, int height, Position target) {
        Position bestPosition = null;
        int bestHeuristic = Integer.MAX_VALUE;

        for (int[] dir : directions) {
            int nx = pos.getX() + dir[0];
            int ny = pos.getY() + dir[1];

            if (isValid(nx, ny, width, height, obstacles)) {
                int currentHeuristic = heuristic(nx, ny, target.getX(), target.getY());
                if (currentHeuristic < bestHeuristic) {
                    bestHeuristic = currentHeuristic;
                    bestPosition = new Position(nx, ny);
                }
            }
        }
        return bestPosition;
    }

    /**
     * Checks if a position is valid (inside bounds and not occupied).
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param width map width
     * @param height map height
     * @param obstacles list of occupied positions
     * @return true if valid, false otherwise
     */
    private boolean isValid(int x, int y, int width, int height, List<Position> obstacles) {
        return x >= 0 && y >= 0 &&
                x < width && y < height &&
                !isOccupied(obstacles, x, y);
    }

    /**
     * Heuristic function for pathfinding (Chebyshev distance).
     *
     * @param x1 x coordinate of first point
     * @param y1 y coordinate of first point
     * @param x2 x coordinate of second point
     * @param y2 y coordinate of second point
     * @return heuristic value
     */
    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * Checks if a position is occupied.
     *
     * @param obstacles list of occupied positions
     * @param x x coordinate
     * @param y y coordinate
     * @return true if occupied, false otherwise
     */
    private boolean isOccupied(List<Position> obstacles, int x, int y) {
        for (Position p : obstacles) {
            if (p.getX() == x && p.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a path from start to goal using a simple A* algorithm.
     *
     * @param width map width
     * @param height map height
     * @param obstacles list of occupied positions
     * @param start starting position
     * @param goal goal position
     * @return list of positions representing the path, or null if no path found
     */
    public List<Position> findPath(int width, int height, List<Position> obstacles,
                                   Position start, Position goal) {
        boolean[][] visited = new boolean[width][height];
        List<Node> openList = new ArrayList<>();

        Node startNode = new Node(start.getX(), start.getY(), 0,
                heuristic(start.getX(), start.getY(), goal.getX(), goal.getY()), null);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            int bestIndex = 0;
            for (int i = 1; i < openList.size(); i++) {
                Node a = openList.get(i);
                Node b = openList.get(bestIndex);
                int fa = a.getCost() + a.getEstimate();
                int fb = b.getCost() + b.getEstimate();
                if (fa < fb) {
                    bestIndex = i;
                }
            }

            Node current = openList.remove(bestIndex);
            int x = current.getX();
            int y = current.getY();
            if (visited[x][y]) continue;
            visited[x][y] = true;

            if (x == goal.getX() && y == goal.getY()) {
                List<Position> path = new ArrayList<>();
                Node temp = current;
                while (temp != null) {
                    path.add(0, new Position(temp.getX(), temp.getY()));
                    temp = temp.getParent();
                }
                return path;
            }

            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (isValid(nx, ny, width, height, obstacles) && !visited[nx][ny]) {
                    int newCost = current.getCost() + 1;
                    int estimate = heuristic(nx, ny, goal.getX(), goal.getY());
                    Node neighbor = new Node(nx, ny, newCost, estimate, current);
                    openList.add(neighbor);
                }
            }
        }
        return null;
    }

    /**
     * Verifies if the simulation has enough money to build a railway line of the given type.
     *
     * @param railwayLineType the type of railway line
     * @return true if there is enough money, false otherwise
     */
    public boolean verifyIfHasMoney(RailwayLineType railwayLineType) {
        boolean isElectric = railwayLineType.getId() == 0 || railwayLineType.getId() == 1;
        boolean isDoubleTrack = railwayLineType.getId() == 1 || railwayLineType.getId() == 3;

        return simulation.getActualMoney() >= returnRailwayCost(getPath(railwayLineType), isDoubleTrack, isElectric);
    }
}