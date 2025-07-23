package pt.ipp.isep.dei.domain.Industry;

import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a transforming industry that converts resources into other forms.
 * Extends the Industry class to include specific functionalities for transforming industries.
 */
public class TransformingIndustry extends Industry {

    private TransformingResource transformingResource;
    private List<ResourcesType> primaryResources;
    private List<TransformingIndustry> clonedIndustryList = new ArrayList<>();
    private String scenarioName;

    /**
     * Constructs a TransformingIndustry with the specified name, type, and position.
     *
     * @param name     The name of the industry.
     * @param type     The type of the industry.
     * @param position The position of the industry on the map.
     */
    public TransformingIndustry(String name, IndustryType type, Position position) {
        super(name, type, position);
    }

    /**
     * Constructs a TransformingIndustry with the specified name, scenario name, and transforming resource.
     *
     * @param name                 The name of the industry.
     * @param scenarioName         The name of the scenario associated with the industry.
     * @param transformingResource The resource transformed by the industry.
     */
    public TransformingIndustry(String name, String scenarioName, TransformingResource transformingResource) {
        super(name, null, null);
        this.transformingResource = transformingResource;
        this.scenarioName = scenarioName;
        this.primaryResources = transformingResource.getNeededResources();
    }

    /**
     * Gets the transforming resource used by the industry.
     *
     * @return The transforming resource.
     */
    public TransformingResource getTransformingResource() {
        return transformingResource;
    }

    /**
     * Sets the transforming resource for the industry.
     *
     * @param transformingResource The transforming resource to set.
     */
    public void setTransformingResource(TransformingResource transformingResource) {
        this.transformingResource = transformingResource;
    }

    /**
     * Gets the list of primary resources required for transformation.
     *
     * @return A list of primary resources.
     */
    public List<ResourcesType> getPrimaryResources() {
        return primaryResources;
    }

    /**
     * Sets the list of primary resources required for transformation.
     *
     * @param primaryResources The list of primary resources to set.
     */
    public void setPrimaryResources(List<ResourcesType> primaryResources) {
        this.primaryResources = primaryResources;
    }

    /**
     * Gets the list of cloned industries associated with this industry.
     *
     * @return A list of cloned industries.
     */
    public List<TransformingIndustry> getClonedIndustryList() {
        return clonedIndustryList;
    }

    /**
     * Sets the list of cloned industries associated with this industry.
     *
     * @param clonedIndustryList The list of cloned industries to set.
     */
    public void setClonedIndustryList(List<TransformingIndustry> clonedIndustryList) {
        this.clonedIndustryList = clonedIndustryList;
    }

    /**
     * Gets the name of the scenario associated with this industry.
     *
     * @return The scenario name.
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * Sets the name of the scenario associated with this industry.
     *
     * @param scenarioName The scenario name to set.
     */
    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    /**
     * Clones the current transforming industry and associates it with a specific scenario.
     *
     * @param transformingResource The transforming resource for the cloned industry.
     * @param scenario             The scenario to associate with the cloned industry.
     */
    public void cloneTransformingIndustry(TransformingResource transformingResource, Scenario scenario) {
        TransformingIndustry clonedIndustry = new TransformingIndustry(this.getName(), scenario.getName(), transformingResource);
        clonedIndustryList.add(clonedIndustry);
    }

    /**
     * Retrieves a cloned transforming industry associated with a specific scenario.
     *
     * @param scenario The scenario to search for.
     * @return The cloned transforming industry associated with the scenario, or null if not found.
     */
    public TransformingIndustry getClonedTransformingIndustry(Scenario scenario) {
        for (TransformingIndustry industry : clonedIndustryList) {
            if (industry.scenarioName.equals(scenario.getName())) {
                return industry;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the transforming industry, including its transformation details.
     *
     * @return A formatted string representation of the industry.
     */
    @Override
    public String toString() {
        return getName() + " transforms " + transformingResource.getTransformation();
    }
}