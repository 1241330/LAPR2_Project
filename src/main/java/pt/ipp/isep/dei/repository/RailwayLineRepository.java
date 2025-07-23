package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing RailwayLine entities.
 * Provides methods to add, remove, check existence, and retrieve railway lines.
 */
public class RailwayLineRepository implements Serializable {

    /**
     * List that stores all railway lines.
     */
    private List<RailwayLine> railwayLines = new ArrayList<>();

    /**
     * Constructs an empty RailwayLineRepository.
     */
    public RailwayLineRepository() {
        // Default constructor
    }

    /**
     * Adds a railway line to the repository.
     *
     * @param railwayLine The railway line to add.
     * @return true if the railway line was added successfully, false otherwise.
     * @throws IllegalArgumentException if the railway line is null.
     */
    public boolean addRailwayLine(RailwayLine railwayLine) {
        if (railwayLine == null) {
            throw new IllegalArgumentException("Railway line cannot be null");
        }
        if (existsRailwayLine(railwayLine)) {
            return false;
        }
        railwayLines.add(railwayLine);
        return true;
    }

    /**
     * Checks if a railway line with the same stations already exists in the repository.
     *
     * @param railwayLine The railway line to check.
     * @return true if the railway line exists, false otherwise.
     */
    public boolean existsRailwayLine(RailwayLine railwayLine) {
        for (RailwayLine rl : railwayLines) {
            if (rl.getStation1().getName().equals(railwayLine.getStation1().getName()) &&
                rl.getStation2().getName().equals(railwayLine.getStation2().getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a railway line from the repository.
     *
     * @param railwayLine The railway line to remove.
     * @return true if the railway line was removed, false if it does not exist.
     * @throws IllegalArgumentException if the railway line is null.
     */
    public boolean removeRailwayLine(RailwayLine railwayLine) {
        if (railwayLine == null) {
            throw new IllegalArgumentException("Railway line cannot be null");
        }
        if (!railwayLineExists(railwayLine)) {
            Utils.printMessage("< Railway line doesn't exist >");
            return false;
        }
        railwayLines.remove(railwayLine);
        return true;
    }

    /**
     * Checks if a specific railway line instance exists in the repository.
     *
     * @param railwayLine The railway line to check.
     * @return true if the railway line exists, false otherwise.
     */
    public boolean railwayLineExists(RailwayLine railwayLine) {
        for (RailwayLine rl : railwayLines) {
            if (railwayLine == rl) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of railway lines in the repository.
     *
     * @return The count of railway lines.
     */
    public int getRailwayLinesCount() {
        return railwayLines.size();
    }

    /**
     * Retrieves a list of all railway lines in the repository.
     *
     * @return The list of railway lines.
     */
    public List<RailwayLine> getAllRailwayLines() {
        return railwayLines;
    }

    /**
     * Gets the list of railway lines.
     *
     * @return The list of railway lines.
     */
    public List<RailwayLine> getRailwayLines() {
        return railwayLines;
    }

    /**
     * Sets the list of railway lines.
     *
     * @param railwayLines The list of railway lines to set.
     */
    public void setRailwayLines(List<RailwayLine> railwayLines) {
        this.railwayLines = railwayLines;
    }
}