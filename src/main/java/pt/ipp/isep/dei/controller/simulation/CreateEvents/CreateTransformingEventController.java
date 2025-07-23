package pt.ipp.isep.dei.controller.simulation.CreateEvents;

import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.TranformingEvent;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for generating {@link TranformingEvent}s based on the transformation logic
 * defined in {@link TransformingIndustry} and {@link MixedIndustry} entities associated with
 * {@link Station}s in the simulation's map.
 * <p>
 * Transformation events represent the conversion of resources at specific stations.
 */
public class CreateTransformingEventController {

    private List<Station> stationList;
    private List<ResourcesType> resourcesListConfiguration;
    private Scenario scenario;
    private Map actualMap;
    private int actualTime;
    private List<Event> transformingEventList = new ArrayList<>();

    /**
     * Constructs a controller to generate transformation events for a given scenario and simulation time.
     *
     * @param scenario   the scenario context providing map and resource definitions
     * @param actualTime the current time of the simulation when the events are generated
     */
    public CreateTransformingEventController(Scenario scenario, int actualTime) {
        this.scenario = scenario;
        this.resourcesListConfiguration = scenario.getTypeResourceList();
        this.actualMap = scenario.getMap();
        this.stationList = actualMap.getStationList();
        this.actualTime = actualTime;
    }

    /**
     * Iterates through all stations and their associations in the current map to create and add
     * {@link TranformingEvent}s based on associated transforming or mixed industries.
     * <p>
     * Ensures that no duplicate events (by name) are added.
     */
    public void addEventsToList() {
        for (Station station : stationList) {
            station.assignGenerationPosts(scenario);
            List<StationAssociations> associations = station.getAllAssociations();

            for (StationAssociations association : associations) {

                // Case: Transforming Industry
                if (association instanceof TransformingIndustry) {
                    TransformingIndustry transformingIndustryScenario =
                            ((TransformingIndustry) association).getClonedTransformingIndustry(scenario);

                    TransformingResource transformingResource =
                            transformingIndustryScenario.getTransformingResource();

                    String nameTransformingEvent = "Transforming of " + transformingResource.getName() +
                            " in " + transformingIndustryScenario.getName() +
                            " in " + station.getName();

                    TranformingEvent transformingEvent = new TranformingEvent(
                            nameTransformingEvent,
                            1,
                            actualTime,
                            transformingResource,
                            (TransformingIndustry) association
                    );

                    if (!Utils.eventExistsByName(nameTransformingEvent, transformingEventList)) {
                        transformingEventList.add(transformingEvent);
                    }
                }

                // Case: Mixed Industry
                if (association instanceof MixedIndustry) {
                    MixedIndustry mixedIndustryScenario =
                            ((MixedIndustry) association).getClonedMixedIndustry(scenario);

                    List<ResourcesType> transformingResourceList = mixedIndustryScenario.getTransformedResources();

                    for (ResourcesType transformingResource : transformingResourceList) {
                        String nameTransformingEvent = "Transforming of " + transformingResource.getName() +
                                " in " + mixedIndustryScenario.getName() +
                                " in " + station.getName();

                        TranformingEvent transformingEvent = new TranformingEvent(
                                nameTransformingEvent,
                                1,
                                actualTime,
                                (TransformingResource) transformingResource,
                                (MixedIndustry) association
                        );

                        if (!Utils.eventExistsByName(nameTransformingEvent, transformingEventList)) {
                            transformingEventList.add(transformingEvent);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the list of generated transformation events.
     *
     * @return a list of {@link Event} objects representing resource transformations
     */
    public List<Event> getTransformingEventList() {
        return transformingEventList;
    }

    /**
     * Sets the list of transformation events, replacing the current one if the parameter is not null.
     *
     * @param transformingEventList the new list of transformation events
     */
    public void setTransformingEventList(List<Event> transformingEventList) {
        if (transformingEventList != null) {
            this.transformingEventList = transformingEventList;
        }
    }

    /**
     * Returns the list of stations used in this controller.
     *
     * @return the list of {@link Station}
     */
    public List<Station> getStationList() {
        return stationList;
    }

    /**
     * Sets the list of stations for this controller.
     *
     * @param stationList the new list of {@link Station}
     */
    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    /**
     * Returns the list of resource types configured for this controller.
     *
     * @return the list of {@link ResourcesType}
     */
    public List<ResourcesType> getResourcesListConfiguration() {
        return resourcesListConfiguration;
    }

    /**
     * Sets the list of resource types configuration.
     *
     * @param resourcesListConfiguration the new list of {@link ResourcesType}
     */
    public void setResourcesListConfiguration(List<ResourcesType> resourcesListConfiguration) {
        this.resourcesListConfiguration = resourcesListConfiguration;
    }

    /**
     * Returns the scenario used in this controller.
     *
     * @return the {@link Scenario}
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets the scenario for this controller.
     *
     * @param scenario the new {@link Scenario}
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Returns the map used in this controller.
     *
     * @return the {@link Map}
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the map for this controller.
     *
     * @param actualMap the new {@link Map}
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
    }

    /**
     * Returns the current simulation time.
     *
     * @return the current time as int
     */
    public int getActualTime() {
        return actualTime;
    }

    /**
     * Sets the current simulation time.
     *
     * @param actualTime the new current time
     */
    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }
}