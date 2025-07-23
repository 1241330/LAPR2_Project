package pt.ipp.isep.dei.domain.Simulation;

/**
 * Enum representing the type of cargo mode for a transport.
 * <ul>
 *     <li>FULL - The cargo is fully loaded.</li>
 *     <li>HALF - The cargo is half loaded.</li>
 *     <li>AVAILABLE - The cargo space is available.</li>
 * </ul>
 */
public enum TypeOfCargoMode {
    /**
     * The cargo is fully loaded.
     */
    FULL,

    /**
     * The cargo is half loaded.
     */
    HALF,

    /**
     * The cargo space is available.
     */
    AVAILABLE
}