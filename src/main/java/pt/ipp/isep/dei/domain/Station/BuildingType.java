package pt.ipp.isep.dei.domain.Station;

/**
 * Enum representing different types of buildings, each with its own properties such as
 * identifier, construction cost, replacement ID, operation year, and deletion ID.
 */
public enum BuildingType {
    TELEGRAPH(1, 30, -1, 1844, -1),
    TELEPHONE(2, 60, 1, 1876, 1),
    SMALL_COFFEE(3, 40, -1, 1900, -1),
    LARGE_COFFEE(4, 70, 3, 1920, -1),
    CUSTOMS(5, 80, -1, 1850, -1),
    POST_OFFICE(6, 50, -1, 1837, -1),
    SMALL_HOTEL(7, 60, -1, 1850, -1),
    LARGE_HOTEL(8, 120, 7, 1900, -1),
    SILO(9, 70, -1, 1840, -1),
    LIQUID_STORAGE(10, 50, -1, 1860, -1);

    /** Unique identifier for the building type. */
    private final int identifier;

    /** Cost to construct the building. */
    private final int constructionCost;

    /** ID of the building type that this building replaces, or -1 if none. */
    private final int replacesByID;

    /** Year the building type starts operating. */
    private final int operationYear;

    /** ID of the building type to be deleted from available types, or -1 if none. */
    private final int deleteFromAvailableID;

    /** Number of days per year (used for operation year in days). */
    private static final int DAYS_PER_YEAR = 360;

    /**
     * Constructs a BuildingType enum constant with the specified properties.
     *
     * @param identifier             Unique identifier for the building type.
     * @param constructionCost       Cost to construct the building.
     * @param replacesByID           ID of the building type that this building replaces, or -1 if none.
     * @param operationYear          Year the building type starts operating.
     * @param deleteFromAvailableID  ID of the building type to be deleted from available types, or -1 if none.
     */
    BuildingType(int identifier, int constructionCost, int replacesByID, int operationYear, int deleteFromAvailableID) {
        this.identifier = identifier;
        this.constructionCost = constructionCost;
        this.replacesByID = replacesByID;
        this.operationYear = operationYear;
        this.deleteFromAvailableID = deleteFromAvailableID;
    }

    /**
     * Gets the construction cost of the building type.
     *
     * @return The construction cost.
     */
    public int getConstructionCost() {
        return constructionCost;
    }

    /**
     * Gets the unique identifier of the building type.
     *
     * @return The building type identifier.
     */
    public int getBuildingID() {
        return identifier;
    }

    /**
     * Gets the ID of the building type that this building replaces.
     *
     * @return The ID of the replaced building type, or -1 if none.
     */
    public int getBuildingReplacesByID() {
        return replacesByID;
    }

    /**
     * Gets the formatted name of the building type.
     * The name is converted to lowercase, underscores are replaced by spaces,
     * and the first letter is capitalized.
     *
     * @return The formatted building name.
     */
    public String getName() {
        String buildingName = this.name().toLowerCase();
        buildingName = buildingName.replace('_', ' ');
        return buildingName.substring(0, 1).toUpperCase() + buildingName.substring(1);
    }

    /**
     * Gets the operation year of the building type in days.
     *
     * @return The operation year in days.
     */
    public int getOperationYearInDays() {
        return operationYear * DAYS_PER_YEAR;
    }

    /**
     * Gets the ID of the building type to be deleted from available types.
     *
     * @return The ID of the building type to be deleted, or -1 if none.
     */
    public int getDeleteFromAvailableID() {
        return deleteFromAvailableID;
    }
}