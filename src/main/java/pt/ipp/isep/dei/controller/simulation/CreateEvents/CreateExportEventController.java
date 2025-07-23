package pt.ipp.isep.dei.controller.simulation.CreateEvents;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.domain.Event.ExportEvent;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for generating export events based on the current scenario.
 * It analyzes each station and its associations to create corresponding {@link ExportEvent}s.
 */
public class CreateExportEventController {

    private List<Station> stationList;
    private List<ResourcesType> resourcesListConfiguration;
    private Scenario scenario;
    private Map actualMap;
    private int actualTime;
    private List<Event> exportEventList = new ArrayList<>();

    /**
     * Constructs a controller that manages the creation of export events
     * using data from a specific scenario and time.
     *
     * @param scenario   The current simulation scenario.
     * @param actualTime The current time (in months or ticks) for event creation.
     */
    public CreateExportEventController(Scenario scenario, int actualTime) {
        this.scenario = scenario;
        this.resourcesListConfiguration = scenario.getTypeResourceList();
        this.actualMap = scenario.getMap();
        this.stationList = actualMap.getStationList();
        this.actualTime = actualTime;
    }

    /**
     * Analyzes the stations in the map and their {@link MixedIndustry} and {@link HouseBlock} associations
     * to create and register new export events. Duplicate events are avoided based on their name.
     */
    public void addEventsToList() {
        for (Station station : stationList) {
            station.assignGenerationPosts(scenario);
            List<StationAssociations> associations = station.getAllAssociations();
            for (StationAssociations association : associations) {
                if (association instanceof MixedIndustry) {
                    MixedIndustry mixedIndustryScenario = ((MixedIndustry) association).getClonedMixedIndustry(scenario);
                    List<ResourcesType> exports = mixedIndustryScenario.getExportedResources();
                    for (ResourcesType export : exports) {
                        String nameExportEvent = "Export of " + export.getName() + " from " + mixedIndustryScenario.getName() + " in " + station.getName();
                        ExportEvent exportEvent = new ExportEvent(nameExportEvent, 1, actualTime, export, association);
                        if (!Utils.eventExistsByName(nameExportEvent, exportEventList)) {
                            exportEventList.add(exportEvent);
                        }
                    }
                }
                if (association instanceof HouseBlock houseBlock) {
                    List<ResourcesType> exports = houseBlock.getConsumableResources();
                    for (ResourcesType export : exports) {
                        String nameExportEvent = "Export of " + export.getName() + " from " + houseBlock.getCityName() + " House Block in " + houseBlock.getPosition().toString();
                        ExportEvent exportEvent = new ExportEvent(nameExportEvent, 1, actualTime, export, houseBlock);
                        if (!Utils.eventExistsByName(nameExportEvent, exportEventList)) {
                            exportEventList.add(exportEvent);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the list of export events created by this controller.
     *
     * @return A list of {@link Event} objects (specifically {@link ExportEvent}).
     */
    public List<Event> getExportEventList() {
        return exportEventList;
    }

    /**
     * Sets the list of export events. Will only update if the provided list is not null.
     *
     * @param exportEventList A list of events to replace the current list.
     */
    public void setExportEventList(List<Event> exportEventList) {
        if (exportEventList != null) {
            this.exportEventList = exportEventList;
        }
    }

    /**
     * Returns the list of stations used for event creation.
     *
     * @return the list of stations
     */
    public List<Station> getStationList() {
        return stationList;
    }

    /**
     * Sets the list of stations for event creation.
     *
     * @param stationList the list of stations to set
     */
    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    /**
     * Returns the list of resource types configured in the scenario.
     *
     * @return the list of resource types
     */
    public List<ResourcesType> getResourcesListConfiguration() {
        return resourcesListConfiguration;
    }

    /**
     * Sets the list of resource types for the scenario.
     *
     * @param resourcesListConfiguration the list of resource types to set
     */
    public void setResourcesListConfiguration(List<ResourcesType> resourcesListConfiguration) {
        this.resourcesListConfiguration = resourcesListConfiguration;
    }

    /**
     * Returns the scenario associated with this controller.
     *
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets the scenario for this controller.
     *
     * @param scenario the scenario to set
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Returns the map used in the scenario.
     *
     * @return the map
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the map for the scenario.
     *
     * @param actualMap the map to set
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
    }

    /**
     * Returns the current time used for event creation.
     *
     * @return the current time
     */
    public int getActualTime() {
        return actualTime;
    }

    /**
     * Sets the current time for event creation.
     *
     * @param actualTime the time to set
     */
    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }
}