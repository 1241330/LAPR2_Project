package pt.ipp.isep.dei.domain.Industry;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing the different types of industries.
 * Each industry type has a description associated with it.
 */
public enum IndustryType {
    /**
     * Represents a primary sector industry.
     */
    PRIMARY_SECTOR("Primary Sector Industry"),

    /**
     * Represents a transforming industry.
     */
    TRANSFORMING("Transforming Industry"),

    /**
     * Represents a mixed industry.
     */
    MIXED("Mixed Industry");

    /**
     * The description of the industry type.
     */
    private final String description;

    /**
     * Constructs an IndustryType with the specified description.
     *
     * @param description The description of the industry type.
     */
    IndustryType(String description) {
        this.description = description;
    }

    /**
     * Retrieves a list of all available industry types.
     *
     * @return A list containing all industry types.
     */
    public static List<IndustryType> getAllIndustryTypes() {
        return Arrays.asList(IndustryType.values());
    }

    /**
     * Finds an industry type by its description.
     *
     * @param description The description of the industry type.
     * @return The matching IndustryType, or {@code null} if no match is found.
     */
    public static IndustryType getIndustryTypeByDescription(String description) {
        for (IndustryType type : IndustryType.values()) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the description of the industry type.
     *
     * @return The description of the industry type.
     */
    public String getDescription() {
        return description;
    }
}