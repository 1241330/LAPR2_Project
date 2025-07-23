package pt.ipp.isep.dei.domain.Station;

/**
 * Enum representing the different types of stations.
 * Each type has a construction cost and an influential radius.
 */
public enum StationType {

    /**
     * Depot station type.
     * Construction cost: 50
     * Influential radius: 3
     */
    DEPOT(50, 3),

    /**
     * Regular station type.
     * Construction cost: 100
     * Influential radius: 4
     */
    STATION(100, 4),

    /**
     * Terminal station type.
     * Construction cost: 200
     * Influential radius: 5
     */
    TERMINAL(200, 5);

    /** The construction cost of the station type. */
    private final int constructionCost;

    /** The influential radius of the station type. */
    private final int influentialRadius;

    /**
     * Constructs a StationType with the specified construction cost and influential radius.
     *
     * @param constructionCost   the construction cost of the station type
     * @param influentialRadius  the influential radius of the station type
     */
    StationType(int constructionCost, int influentialRadius) {
        this.constructionCost = constructionCost;
        this.influentialRadius = influentialRadius;
    }

    /**
     * Gets the construction cost of the station type.
     *
     * @return the construction cost
     */
    public int getConstructionCost() {
        return constructionCost;
    }

    /**
     * Gets the influential radius of the station type.
     *
     * @return the influential radius
     */
    public int getInfluentialRadius() {
        return influentialRadius;
    }
}