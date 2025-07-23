package pt.ipp.isep.dei.domain.Industry;

import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mixed industry that can import, export, and transform resources.
 * Extends the Industry class to include specific functionalities for mixed industries.
 */
public class MixedIndustry extends Industry {

    /** List of resources exported by the industry. */
    private List<ResourcesType> exportedResources = new ArrayList<>();

    /** List of resources imported by the industry. */
    private List<ResourcesType> importedResources = new ArrayList<>();

    /** List of resources transformed by the industry. */
    private List<ResourcesType> transformedResources = new ArrayList<>();

    /** List of cloned industries associated with different scenarios. */
    private List<MixedIndustry> clonedIndustryList = new ArrayList<>();

    /** Name of the scenario associated with this industry. */
    private String scenarioName;

    /**
     * Constructs a MixedIndustry with the specified name, type, and position.
     *
     * @param name     The name of the industry.
     * @param type     The type of the industry.
     * @param position The position of the industry on the map.
     */
    public MixedIndustry(String name, IndustryType type, Position position) {
        super(name, type, position);
    }

    /**
     * Constructs a MixedIndustry with the specified name and scenario name.
     *
     * @param name         The name of the industry.
     * @param scenarioName The name of the scenario associated with the industry.
     */
    public MixedIndustry(String name, String scenarioName) {
        super(name, null, null);
        this.scenarioName = scenarioName;
    }

    /**
     * Gets the list of resources exported by the industry.
     *
     * @return List of exported resources.
     */
    public List<ResourcesType> getExportedResources() {
        return exportedResources;
    }

    /**
     * Gets the list of resources imported by the industry.
     *
     * @return List of imported resources.
     */
    public List<ResourcesType> getImportedResources() {
        return importedResources;
    }

    /**
     * Gets the list of resources transformed by the industry.
     *
     * @return List of transformed resources.
     */
    public List<ResourcesType> getTransformedResources() {
        return transformedResources;
    }

    /**
     * Gets the list of cloned industries associated with this industry.
     *
     * @return List of cloned industries.
     */
    public List<MixedIndustry> getClonedIndustryList() {
        return clonedIndustryList;
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
     * Adds a transforming resource to the list of transformed resources.
     *
     * @param resource The resource to be added.
     */
    public void addTransformingResource(TransformingResource resource) {
        transformedResources.add(resource);
    }

    /**
     * Adds an exported resource to the list of exported resources.
     *
     * @param resource The resource to be added.
     */
    public void addExportedResource(ResourcesType resource) {
        exportedResources.add(resource);
    }

    /**
     * Adds an imported resource to the list of imported resources.
     *
     * @param resource The resource to be added.
     */
    public void addImportedResource(ResourcesType resource) {
        importedResources.add(resource);
    }

    /**
     * Clones the current industry and associates it with a specific scenario.
     *
     * @param scenario The scenario to associate with the cloned industry.
     */
    public void cloneMixedIndustry(Scenario scenario) {
        MixedIndustry clonedIndustry = new MixedIndustry(this.getName(), scenario.getName());
        clonedIndustryList.add(clonedIndustry);
    }

    /**
     * Retrieves a cloned industry associated with a specific scenario.
     *
     * @param scenario The scenario to search for.
     * @return The cloned industry associated with the scenario, or null if not found.
     */
    public MixedIndustry getClonedMixedIndustry(Scenario scenario) {
        for (MixedIndustry industry : clonedIndustryList) {
            if (industry.scenarioName.equals(scenario.getName())) {
                return industry;
            }
        }
        System.out.println("No cloned industry found for the given scenario.");
        return null;
    }

    /**
     * Adds a transforming resource to a cloned industry associated with a specific scenario.
     *
     * @param resource The resource to be added.
     * @param scenario The scenario associated with the cloned industry.
     */
    public void transformedResource(TransformingResource resource, Scenario scenario) {
        MixedIndustry clonedIndustry = getClonedMixedIndustry(scenario);
        if (clonedIndustry != null) {
            clonedIndustry.addTransformingResource(resource);
        }
    }

    /**
     * Adds an exported resource to a cloned industry associated with a specific scenario.
     *
     * @param resource The resource to be added.
     * @param scenario The scenario associated with the cloned industry.
     */
    public void exportResource(ResourcesType resource, Scenario scenario) {
        MixedIndustry clonedIndustry = getClonedMixedIndustry(scenario);
        if (clonedIndustry != null) {
            clonedIndustry.addExportedResource(resource);
        }
    }

    /**
     * Adds an imported resource to a cloned industry associated with a specific scenario.
     *
     * @param resource The resource to be added.
     * @param scenario The scenario associated with the cloned industry.
     */
    public void importResource(ResourcesType resource, Scenario scenario) {
        MixedIndustry clonedIndustry = getClonedMixedIndustry(scenario);
        if (clonedIndustry != null) {
            clonedIndustry.addImportedResource(resource);
        }
    }

    /**
     * Returns a string representation of the mixed industry, including its imported,
     * exported, and transformed resources.
     *
     * @return A formatted string representation of the industry.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName().toLowerCase()).append(" makes (");
        sb.append("Imports: ").append(formatResourceList(importedResources)).append(" | ");
        sb.append("Exports: ").append(formatResourceList(exportedResources)).append(" | ");
        sb.append("Transformation: ").append(formatResourceList(transformedResources)).append(")");
        return sb.toString();
    }

    /**
     * Formats a list of resources into a string representation.
     *
     * @param resources The list of resources to format.
     * @return A formatted string of resource names, or "none" if the list is empty.
     */
    private String formatResourceList(List<ResourcesType> resources) {
        if (resources.isEmpty()) {
            return "none";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resources.size(); i++) {
            sb.append(resources.get(i).getName().toLowerCase());
            if (i < resources.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}