package pt.ipp.isep.dei.controller.simulation.CreateEvents;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.GenerationEvent;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for generating {@link GenerationEvent}s based on active stations
 * and their associated entities (HouseBlocks, Primary Industries, and Mixed Industries).
 * <p>
 * It uses the current simulation {@link Scenario} and time to determine which resources
 * should be generated, at which intervals, and associates these events with the corresponding stations.
 */
public class CreateGenerationEventController {

    /** List of stations in the current map. */
    private List<Station> stationList;

    /** The scenario context containing the map, stations, and resource settings. */
    private Scenario scenario;

    /** The map associated with the current scenario. */
    private Map actualMap;

    /** The current time (tick/month) of the simulation. */
    private int actualTime;

    /** List of generation events created by this controller. */
    private List<Event> generationEventList = new ArrayList<>();

    /**
     * Constructs the controller for creating generation events using a scenario and a given time.
     *
     * @param scenario   the scenario context containing the map, stations, and resource settings
     * @param actualTime the current time (tick/month) of the simulation
     */
    public CreateGenerationEventController(Scenario scenario, int actualTime) {
        this.scenario = scenario;
        this.actualMap = scenario.getMap();
        this.stationList = actualMap.getStationList();
        this.actualTime = actualTime;
    }

    /**
     * Iterates through all stations and their associated elements (HouseBlocks, PrimaryIndustries, MixedIndustries)
     * to generate corresponding {@link GenerationEvent}s. Ensures events are not duplicated by name.
     */
    public void addEventsToList() {
        for (Station station : stationList) {
            station.assignGenerationPosts(scenario);
            List<StationAssociations> associations = station.getAllAssociations();
            for (StationAssociations association : associations) {

                // Handle HouseBlock resource generation
                if (association instanceof HouseBlock) {
                    List<HouseBlockResource> productions = scenario.getHouseBlockResourceList();
                    for (ResourcesType production : productions) {
                        Resource resource = new Resource(production, production.getQuantityProduced());
                        String eventName = "Generation of " + production.getName() + " in " + ((HouseBlock) association).getId() + "House Block" + " in " + station.getName();
                        GenerationEvent generationEvent = new GenerationEvent(eventName, production.getIntervalBetweenResourceGeneration(), actualTime, resource, association);

                        if (!Utils.eventExistsByName(eventName, generationEventList)) {
                            generationEventList.add(generationEvent);
                        }
                    }
                }

                // Handle PrimaryIndustry resource generation
                if (association instanceof PrimaryIndustry) {
                    PrimaryIndustry primaryIndustryScenario = ((PrimaryIndustry) association).getClonedPrimaryIndustry(scenario);
                    ResourcesType primaryResource = primaryIndustryScenario.getPrimaryResource();
                    Resource resource = new Resource(primaryResource, primaryResource.getQuantityProduced());

                    String eventName = "Generation of " + primaryResource.getName() + " in " + primaryIndustryScenario.getName() + " in " + station.getName();

                    GenerationEvent generationEvent = new GenerationEvent(eventName, primaryIndustryScenario.getIntervalBetweenResourceGeneration(), actualTime, resource, association);
                    if (!Utils.eventExistsByName(eventName, generationEventList)) {
                        generationEventList.add(generationEvent);
                    }
                }

                // Handle MixedIndustry importations as generation events
                if (association instanceof MixedIndustry) {
                    MixedIndustry mixedIndustryScenario = ((MixedIndustry) association).getClonedMixedIndustry(scenario);
                    List<ResourcesType> importations = mixedIndustryScenario.getImportedResources();

                    for (ResourcesType production : importations) {
                        Resource resource = new Resource(production, production.getQuantityProduced());
                        String eventName = "Generation of " + production.getName() + " in " + mixedIndustryScenario.getName() + " in " + station.getName();

                        GenerationEvent generationEvent = new GenerationEvent(eventName, production.getIntervalBetweenResourceGeneration(), actualTime, resource, association);

                        if (!Utils.eventExistsByName(eventName, generationEventList)) {
                            generationEventList.add(generationEvent);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the list of {@link GenerationEvent}s created by this controller.
     *
     * @return a list of generation events
     */
    public List<Event> getGenerationEventList() {
        return generationEventList;
    }

    /**
     * Sets the list of generation events. Ignores null assignments.
     *
     * @param generationEventList the new list of generation events
     */
    public void setGenerationEventList(List<Event> generationEventList) {
        if (generationEventList != null) {
            this.generationEventList = generationEventList;
        }
    }
}