package pt.ipp.isep.dei.domain.Station;

import java.io.Serializable;

/**
 * Represents a building in the system.
 * Each building has a specific type, which defines its name and construction cost.
 */
public class Building implements Serializable {

    /** The type of the building. */
    private final BuildingType type;

    /**
     * Constructs a Building with the specified type.
     *
     * @param type The type of the building.
     */
    public Building(BuildingType type) {
        this.type = type;
    }

    /**
     * Gets the type of the building.
     *
     * @return The building type.
     */
    public BuildingType getType() {
        return type;
    }

    /**
     * Gets the name of the building.
     *
     * @return The name of the building.
     */
    public String getName() {
        return type.getName();
    }

    /**
     * Gets the construction cost of the building.
     *
     * @return The construction cost.
     */
    public int getConstructionCost() {
        return type.getConstructionCost();
    }

    /**
     * Returns a string representation of the building, including its name and construction cost.
     *
     * @return A formatted string representing the building.
     */
    @Override
    public String toString() {
        return getName() + " (Cost: " + getConstructionCost() + ")";
    }
}