package pt.ipp.isep.dei.controller.industry;

import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for displaying details of a Primary Industry on the map.
 */
public class ShowPrimaryIndustryDetailsInMapController {

    private PrimaryIndustry primaryIndustryMap;
    private PrimaryIndustry primaryIndustryScenario;

    /**
     * Default constructor.
     */
    public ShowPrimaryIndustryDetailsInMapController() {
    }

    /**
     * Sets the PrimaryIndustry instance representing the industry on the map.
     *
     * @param primaryIndustryMap the PrimaryIndustry on the map
     */
    public void setPrimaryIndustryMap(PrimaryIndustry primaryIndustryMap) {
        this.primaryIndustryMap = primaryIndustryMap;
    }

    /**
     * Gets the PrimaryIndustry instance representing the industry on the map.
     *
     * @return the PrimaryIndustry on the map
     */
    public PrimaryIndustry getPrimaryIndustryMap() {
        return primaryIndustryMap;
    }

    /**
     * Sets the PrimaryIndustry instance representing the industry scenario.
     *
     * @param primaryIndustryScenario the PrimaryIndustry scenario
     */
    public void setPrimaryIndustryScenario(PrimaryIndustry primaryIndustryScenario) {
        this.primaryIndustryScenario = primaryIndustryScenario;
    }

    /**
     * Gets the PrimaryIndustry instance representing the industry scenario.
     *
     * @return the PrimaryIndustry scenario
     */
    public PrimaryIndustry getPrimaryIndustryScenario() {
        return primaryIndustryScenario;
    }

    /**
     * Gets the name of the industry.
     *
     * @return the industry name
     */
    public String getIndustryName() {
        return primaryIndustryMap.getName();
    }

    /**
     * Gets the position of the industry on the map, incremented by 1 for both X and Y.
     *
     * @return the position as a string
     */
    public String getIndustryPosition() {
        Position position = new Position(
                primaryIndustryMap.getPosition().getX() + 1,
                primaryIndustryMap.getPosition().getY() + 1
        );
        return position.toString();
    }

    /**
     * Gets the name of the station assigned to the industry, or "None" if not assigned.
     *
     * @return the assigned station name or "None"
     */
    public String getAssignedStationName() {
        return primaryIndustryMap.getStationIdentifier() != null
                ? primaryIndustryMap.getStationIdentifier().getName()
                : "None";
    }

    /**
     * Gets a list of resources in the industry's inventory as strings.
     *
     * @return a list of resource descriptions
     */
    public List<String> getIndustryInventory() {
        List<String> resources = new ArrayList<>();
        if (primaryIndustryMap.getInventory() != null) {
            for (Resource resource : primaryIndustryMap.getInventory().getAllResources()) {
                resources.add(resource.toString());
            }
        }
        return resources;
    }

    /**
     * Gets the name of the primary resource produced by the industry scenario.
     *
     * @return the primary resource name, or "N/A" if not available
     */
    public String getPrimaryResourceName() {
        if (primaryIndustryScenario != null && primaryIndustryScenario.getPrimaryResource() != null) {
            return primaryIndustryScenario.getPrimaryResource().getName();
        }
        return "N/A";
    }

    /**
     * Gets the maximum number of resources the industry scenario can store.
     *
     * @return the maximum resources, or 0 if not available
     */
    public int getMaxResources() {
        if (primaryIndustryScenario != null) {
            return primaryIndustryScenario.getMaxResources();
        }
        return 0;
    }

    /**
     * Gets the interval between resource generation for the industry scenario.
     *
     * @return the interval in time units, or 0 if not available
     */
    public int getIntervalBetweenResourceGeneration() {
        if (primaryIndustryScenario != null) {
            return primaryIndustryScenario.getIntervalBetweenResourceGeneration();
        }
        return 0;
    }

    /**
     * Gets the quantity of resources produced per generation for the industry scenario.
     *
     * @return the quantity produced, or 0 if not available
     */
    public int getQuantityProduced() {
        if (primaryIndustryScenario != null) {
            return primaryIndustryScenario.getQuantityProduced();
        }
        return 0;
    }
}