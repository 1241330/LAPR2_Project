package pt.ipp.isep.dei.controller.industry;

import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for displaying details of a MixedIndustry on the map and scenario.
 */
public class ShowMixedIndustryDetailsInMapController {

    /**
     * The MixedIndustry instance from the map.
     */
    private MixedIndustry mixedIndustryMap;

    /**
     * The MixedIndustry instance from the scenario.
     */
    private MixedIndustry mixedIndustryScenario;

    /**
     * Default constructor.
     */
    public ShowMixedIndustryDetailsInMapController() {
    }

    /**
     * Sets the MixedIndustry instance from the map.
     *
     * @param mixedIndustryMap the MixedIndustry from the map
     */
    public void setMixedIndustryMap(MixedIndustry mixedIndustryMap) {
        this.mixedIndustryMap = mixedIndustryMap;
    }

    /**
     * Sets the MixedIndustry instance from the scenario.
     *
     * @param mixedIndustryScenario the MixedIndustry from the scenario
     */
    public void setMixedIndustryScenario(MixedIndustry mixedIndustryScenario) {
        this.mixedIndustryScenario = mixedIndustryScenario;
    }

    /**
     * Returns the name of the industry.
     *
     * @return the industry name
     */
    public String getIndustryName() {
        return mixedIndustryMap.getName();
    }

    /**
     * Returns the position of the industry as a string, with coordinates incremented by 1.
     *
     * @return the industry position as a string
     */
    public String getIndustryPosition() {
        Position position = new Position(
                mixedIndustryMap.getPosition().getX() + 1,
                mixedIndustryMap.getPosition().getY() + 1
        );
        return position.toString();
    }

    /**
     * Returns the name of the assigned station, or "None" if not assigned.
     *
     * @return the assigned station name or "None"
     */
    public String getAssignedStationName() {
        return mixedIndustryMap.getStationIdentifier() != null
                ? mixedIndustryMap.getStationIdentifier().getName()
                : "None";
    }

    /**
     * Returns a list of names of exported resources in the scenario.
     *
     * @return list of exported resource names
     */
    public List<String> getExportedResourcesNames() {
        return getResourceNames(
                mixedIndustryScenario != null
                        ? mixedIndustryScenario.getExportedResources()
                        : new ArrayList<>()
        );
    }

    /**
     * Returns a list of names of imported resources in the scenario.
     *
     * @return list of imported resource names
     */
    public List<String> getImportedResourcesNames() {
        return getResourceNames(
                mixedIndustryScenario != null
                        ? mixedIndustryScenario.getImportedResources()
                        : new ArrayList<>()
        );
    }

    /**
     * Returns a list of names of transformed resources in the scenario.
     *
     * @return list of transformed resource names
     */
    public List<String> getTransformedResourcesNames() {
        return getResourceNames(
                mixedIndustryScenario != null
                        ? mixedIndustryScenario.getTransformedResources()
                        : new ArrayList<>()
        );
    }

    /**
     * Returns a list of resource names from a list of ResourcesType.
     *
     * @param resources the list of ResourcesType
     * @return list of resource names
     */
    private List<String> getResourceNames(List<ResourcesType> resources) {
        List<String> names = new ArrayList<>();
        for (ResourcesType resource : resources) {
            names.add(resource.getName());
        }
        return names;
    }

    /**
     * Returns a list of resources in the industry inventory as strings.
     *
     * @return list of inventory resources as strings
     */
    public List<String> getIndustryInventory() {
        List<String> resources = new ArrayList<>();
        if (mixedIndustryMap.getInventory() != null) {
            for (Resource resource : mixedIndustryMap.getInventory().getAllResources()) {
                resources.add(resource.toString());
            }
        }
        return resources;
    }

    /**
     * Returns the MixedIndustry instance from the map.
     *
     * @return the MixedIndustry from the map
     */
    public MixedIndustry getMixedIndustryMap() {
        return mixedIndustryMap;
    }

    /**
     * Returns the MixedIndustry instance from the scenario.
     *
     * @return the MixedIndustry from the scenario
     */
    public MixedIndustry getMixedIndustryScenario() {
        return mixedIndustryScenario;
    }
}