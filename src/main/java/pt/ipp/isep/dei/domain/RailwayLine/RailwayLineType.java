package pt.ipp.isep.dei.domain.RailwayLine;

/**
 * Enum representing the different types of railway lines,
 * each with a description, unique identifier, construction cost, and maintenance cost.
 */
public enum RailwayLineType {

    /**
     * Single electrified track.
     */
    SINGLE_ELECTRIFIED("Single Electrified Track", 0, 11, 3),

    /**
     * Double electrified track.
     */
    DOUBLE_ELECTRIFIED("Double Electrified Track", 1, 14, 5),

    /**
     * Single non-electrified track.
     */
    SINGLE_NON_ELECTRIFIED("Single Non-Electrified Track", 2, 6, 2),

    /**
     * Double non-electrified track.
     */
    DOUBLE_NON_ELECTRIFIED("Double Non-Electrified Track", 3, 9, 1);

    /**
     * The description of the railway line type.
     */
    private final String type;

    /**
     * The unique identifier for the railway line type.
     */
    private final int id;

    /**
     * The construction cost for the railway line type.
     */
    private final int cost;

    /**
     * The maintenance cost for the railway line type.
     */
    private final int maintenanceCost;

    /**
     * Constructs a RailwayLineType enum constant.
     *
     * @param type            The description of the railway line type.
     * @param id              The unique identifier for the railway line type.
     * @param cost            The construction cost for the railway line type.
     * @param maintenanceCost The maintenance cost for the railway line type.
     */
    RailwayLineType(String type, int id, int cost, int maintenanceCost) {
        this.type = type;
        this.id = id;
        this.cost = cost;
        this.maintenanceCost = maintenanceCost;
    }

    /**
     * Gets the description of the railway line type.
     *
     * @return The type description.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the unique identifier of the railway line type.
     *
     * @return The type id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the construction cost of the railway line type.
     *
     * @return The construction cost.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the maintenance cost of the railway line type.
     *
     * @return The maintenance cost.
     */
    public int getMaintenanceCost() {
        return maintenanceCost;
    }
}