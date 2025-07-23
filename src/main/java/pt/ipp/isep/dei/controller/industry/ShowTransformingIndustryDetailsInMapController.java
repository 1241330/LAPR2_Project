package pt.ipp.isep.dei.controller.industry;

import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for displaying details of a Transforming Industry on the map.
 */
public class ShowTransformingIndustryDetailsInMapController {

    private TransformingIndustry transformingIndustryMap;
    private TransformingIndustry transformingIndustryScenario;

    /**
     * Default constructor.
     */
    public ShowTransformingIndustryDetailsInMapController() {
    }

    /**
     * Sets the TransformingIndustry instance representing the industry on the map.
     *
     * @param transformingIndustryMap the TransformingIndustry on the map
     */
    public void setTransformingIndustryMap(TransformingIndustry transformingIndustryMap) {
        this.transformingIndustryMap = transformingIndustryMap;
    }

    /**
     * Sets the TransformingIndustry instance representing the industry scenario.
     *
     * @param transformingIndustryScenario the TransformingIndustry scenario
     */
    public void setTransformingIndustryScenario(TransformingIndustry transformingIndustryScenario) {
        this.transformingIndustryScenario = transformingIndustryScenario;
    }

    /**
     * Gets the name of the industry.
     *
     * @return the industry name
     */
    public String getIndustryName() {
        return transformingIndustryMap.getName();
    }

    /**
     * Gets the position of the industry on the map (1-based index).
     *
     * @return the position as a string
     */
    public String getIndustryPosition() {
        Position position = new Position(
                transformingIndustryMap.getPosition().getX() + 1,
                transformingIndustryMap.getPosition().getY() + 1
        );
        return position.toString();
    }

    /**
     * Gets the name of the assigned station, or "None" if not assigned.
     *
     * @return the assigned station name or "None"
     */
    public String getAssignedStationName() {
        return transformingIndustryMap.getStationIdentifier() != null
                ? transformingIndustryMap.getStationIdentifier().getName()
                : "None";
    }

    /**
     * Gets the name of the transforming resource for the scenario.
     *
     * @return the transforming resource name or "N/A"
     */
    public String getTransformingResourceName() {
        if (transformingIndustryScenario != null && transformingIndustryScenario.getTransformingResource() != null) {
            return transformingIndustryScenario.getTransformingResource().getName();
        }
        return "N/A";
    }

    /**
     * Gets the names of the primary resources for the scenario, separated by commas.
     *
     * @return the primary resources names or "N/A"
     */
    public String getPrimaryResourcesNames() {
        if (transformingIndustryScenario != null && transformingIndustryScenario.getPrimaryResources() != null) {
            StringBuilder names = new StringBuilder();
            for (ResourcesType resource : transformingIndustryScenario.getPrimaryResources()) {
                names.append(resource.getName()).append(", ");
            }
            // Remove the last comma and space, if any
            if (!names.isEmpty()) {
                names.setLength(names.length() - 2);
            }
            return names.toString();
        }
        return "N/A";
    }

    /**
     * Gets the inventory of the industry as a list of strings.
     *
     * @return the list of resources in the inventory
     */
    public List<String> getIndustryInventory() {
        List<String> resources = new ArrayList<>();
        if (transformingIndustryMap.getInventory() != null) {
            for (Resource resource : transformingIndustryMap.getInventory().getAllResources()) {
                resources.add(resource.toString());
            }
        }
        return resources;
    }

    /**
     * Gets the TransformingIndustry instance representing the industry on the map.
     *
     * @return the TransformingIndustry on the map
     */
    public TransformingIndustry getTransformingIndustryMap() {
        return transformingIndustryMap;
    }

    /**
     * Gets the TransformingIndustry instance representing the industry scenario.
     *
     * @return the TransformingIndustry scenario
     */
    public TransformingIndustry getTransformingIndustryScenario() {
        return transformingIndustryScenario;
    }
}