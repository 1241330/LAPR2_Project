package pt.ipp.isep.dei.controller.simulation.InSimulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ipp.isep.dei.controller.simulation.CreateEvents.*;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Event.*;
import pt.ipp.isep.dei.domain.FinancialResult.Demand;
import pt.ipp.isep.dei.domain.FinancialResult.UnloadCargoLogs;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the simulation run.
 * Handles simulation speed, events, logs, and map modifications during the simulation.
 */
public class RunSimulationController {

    private static final Logger log = LoggerFactory.getLogger(RunSimulationController.class);
    private static final int NUMBER_OF_CARGO_TO_DOWNGRADE_DEMAND = 30;
    private static final int NUMBER_OF_CARGO_TO_UPGRADE_DEMAND = 10;
    private static final int MAX_NUMBER_LOGS = 400;

    private Simulation simulation;
    private Scenario actualScenario;
    private Map actualMap;
    private List<Event> events = new ArrayList<>();
    private int maxTime;

    private final double[] allowedSpeeds = {0.1, 0.5, 1, 2, 3, 5, 10, 20};
    private int currentSpeedIndex = 2; // Default speed is 1x

    private List<Station> stationsList = new ArrayList<>();
    private List<RailwayLine> railwayLinesList = new ArrayList<>();
    private List<Event> generationEventList = new ArrayList<>();
    private List<Event> transformingEventList = new ArrayList<>();
    private List<Event> exportEventList = new ArrayList<>();
    private List<Event> routeEventList = new ArrayList<>();
    private List<String> logs = new ArrayList<>();

    private boolean dateAlertLogs = true;
    private boolean exportAlertLogs = true;
    private boolean generationAlertLogs = true;
    private boolean transformingAlertLogs = true;
    private boolean routeAlertLogs = true;

    /**
     * Default constructor.
     */
    public RunSimulationController() {}

    /**
     * Sets the simulation and initializes related fields.
     *
     * @param simulation the simulation to be set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        this.actualScenario = simulation.getScenario();
        this.actualMap = actualScenario.getMap();
        this.maxTime = simulation.getMaxTime();
        this.stationsList = simulation.getStations();
        this.railwayLinesList = simulation.getRailwayLines();
    }

    /**
     * Returns the current simulation.
     *
     * @return the current {@link Simulation}
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Returns the current simulation time.
     *
     * @return the current time as an integer
     */
    public int getCurrentTime() {
        return simulation.getCurrentTime();
    }

    /**
     * Sets the current simulation time.
     *
     * @param currentTime the time to set
     */
    public void setCurrentTime(int currentTime) {
        simulation.setCurrentTime(currentTime);
    }

    /**
     * Returns the current available budget in the simulation.
     *
     * @return the current budget as an integer
     */
    public int getActualBudget() {
        return simulation.getActualMoney();
    }

    /**
     * Returns the current date of the simulation.
     *
     * @return the current {@link TimeDate}
     */
    public TimeDate getActualDate() {
        return Utils.convertToDate(simulation.getCurrentTime());
    }

    /**
     * Increases the simulation speed to the next allowed value.
     */
    public void increaseSimulationSpeed() {
        if (currentSpeedIndex < allowedSpeeds.length - 1) {
            currentSpeedIndex++;
        }
    }

    /**
     * Decreases the simulation speed to the previous allowed value.
     */
    public void decreaseSimulationSpeed() {
        if (currentSpeedIndex > 0) {
            currentSpeedIndex--;
        }
    }

    /**
     * Returns the current simulation speed multiplier.
     *
     * @return the current speed as a double
     */
    public double getSimulationSpeed() {
        return allowedSpeeds[currentSpeedIndex];
    }

    /**
     * Checks and triggers events scheduled for the current simulation time.
     */
    public void checkEvents() {
        List<String> newLogs = new ArrayList<>();
        for (Event event : events) {
            if (simulation.getCurrentTime() == event.getNextGenerationDate()) {
                newLogs.addAll(filterLogs(event.trigger(), event));
            }
        }
        if (!newLogs.isEmpty()) {
            newLogs.addFirst("==========================================");
            newLogs.addFirst(" 🕒 Simulation Day " + getActualDate());
            newLogs.addFirst("==========================================");
            newLogs.add(" ");
        }
        logs.addAll(newLogs);
        while (logs.size() > MAX_NUMBER_LOGS) {
            logs.removeFirst();
        }
    }

    /**
     * Filters logs based on event type and alert flags.
     *
     * @param logs  the logs to filter
     * @param event the event that generated the logs
     * @return a filtered list of logs
     */
    public List<String> filterLogs(List<String> logs, Event event) {
        if ((event instanceof StartCarriageOperationEvent || event instanceof StartLocomotiveOperationEvent) && dateAlertLogs) {
            return logs;
        }
        if (event instanceof ExportEvent && exportAlertLogs) {
            return logs;
        }
        if (event instanceof GenerationEvent && generationAlertLogs) {
            return logs;
        }
        if (event instanceof TranformingEvent && transformingAlertLogs) {
            return logs;
        }
        if (event instanceof RouteEvent && routeAlertLogs) {
            return logs;
        }
        return new ArrayList<>();
    }

    /**
     * Returns the list of logs generated during the simulation.
     *
     * @return a list of log messages
     */
    public List<String> getLogs() {
        List<String> safeLogs = new ArrayList<>();
        for (String log : logs) {
            if (log != null && !log.isEmpty()) {
                safeLogs.add(log);
            }
        }
        return safeLogs;
    }

    /**
     * Returns the ID of the map associated with the current scenario.
     *
     * @return the map ID as an integer
     */
    public int getMapId() {
        return actualScenario.getMap().getId();
    }

    /**
     * Returns the current scenario.
     *
     * @return the current {@link Scenario}
     */
    public Scenario getActualScenario() {
        return actualScenario;
    }

    /**
     * Sets the current scenario.
     *
     * @param actualScenario the scenario to set
     */
    public void setActualScenario(Scenario actualScenario) {
        this.actualScenario = actualScenario;
    }

    /**
     * Returns the list of events in the simulation.
     *
     * @return a list of {@link Event}
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events for the simulation.
     *
     * @param events the list of events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Returns the maximum simulation time.
     *
     * @return the maximum time as a long
     */
    public long getMaxTime() {
        return maxTime;
    }

    /**
     * Initializes the map with the current stations and railway lines.
     */
    public void initializeMapModifications() {
        Map actualMap = actualScenario.getMap();
        for (Station station : stationsList) {
            actualMap.addElement(station);
        }
        for (RailwayLine railwayLine : railwayLinesList) {
            actualMap.addElement(railwayLine);
        }
        for (HouseBlock houseBlock : simulation.getHouseBlocks()) {
            for (HouseBlock houseBlockMap : actualMap.getHouseBlockList()) {
                if (houseBlock.getPosition().toString().equals(houseBlockMap.getPosition().toString())) {
                    for (Resource resource : houseBlock.getInventory().getAllResources()) {
                        houseBlockMap.addResourceToInventory(resource);
                    }
                }
            }
        }
        for (Industry industry : simulation.getIndustries()) {
            for (Industry industryMap : actualMap.getIndustriesList()) {
                if (industry.getPosition().toString().equals(industryMap.getPosition().toString())) {
                    for (Resource resource : industry.getInventory().getAllResources()) {
                        industryMap.addResourceToInventory(resource);
                    }
                }
            }
        }
    }

    /**
     * Sets all update inventory flags to false for all station associations in the map.
     */
    public void setAllUpdateInventoryFalse() {
        for (Station station : actualMap.getStationList()) {
            for (StationAssociations association : station.getAllAssociations()) {
                association.setUpdatedInventory(false);
            }
        }
    }

    /**
     * Refreshes and rebuilds the list of events for the simulation.
     */
    public void refreshEvents() {
        events.clear();

        CreateAvailableDateEvent createAvailableDateEvent = new CreateAvailableDateEvent(simulation);
        createAvailableDateEvent.addEventsToList();
        List<Event> dateEventsList = new ArrayList<>(createAvailableDateEvent.getDateEventsList());
        events.addAll(dateEventsList);

        CreateGenerationEventController createGenerationEventController = new CreateGenerationEventController(actualScenario, simulation.getCurrentTime());
        createGenerationEventController.setGenerationEventList(generationEventList);
        createGenerationEventController.addEventsToList();
        generationEventList = new ArrayList<>(createGenerationEventController.getGenerationEventList());
        events.addAll(generationEventList);

        CreateTransformingEventController createTransformingEventController = new CreateTransformingEventController(actualScenario, simulation.getCurrentTime());
        createTransformingEventController.setTransformingEventList(transformingEventList);
        createTransformingEventController.addEventsToList();
        transformingEventList = new ArrayList<>(createTransformingEventController.getTransformingEventList());
        events.addAll(transformingEventList);

        CreateExportEventController createExportEventController = new CreateExportEventController(actualScenario, simulation.getCurrentTime());
        createExportEventController.setExportEventList(exportEventList);
        createExportEventController.addEventsToList();
        exportEventList = new ArrayList<>(createExportEventController.getExportEventList());
        events.addAll(exportEventList);

        CreateRouteEventController createRouteEventController = new CreateRouteEventController(simulation, actualScenario);
        createRouteEventController.setRouteEventList(routeEventList);
        createRouteEventController.addRouteEventsToList();
        routeEventList = new ArrayList<>(createRouteEventController.getRouteEventList());
        events.addAll(routeEventList);
    }

    /**
     * Adds a log entry to the logs list.
     *
     * @param s the log message to add
     */
    public void addLogs(String s) {
        logs.add(s);
    }

    /**
     * Calculates and applies train maintenance costs for the current simulation day.
     * Updates the simulation's budget and logs the maintenance actions.
     */
    public void trainMaintenanceCost() {
        int maintenanceCount = 0;
        int totalCost = 0;

        for (Train train : simulation.getTrainList()) {
            if (train.getAcquisitionDate().getMonth() == Utils.convertToDate(simulation.getCurrentTime()).getMonth() &&
                    train.getAcquisitionDate().getDay() == Utils.convertToDate(simulation.getCurrentTime()).getDay() &&
                    train.getAcquisitionDate().getYear() != Utils.convertToDate(simulation.getCurrentTime()).getYear()) {

                int cost = train.getLocomotive().getMaintenanceCost();
                simulation.setActualMoney(simulation.getActualMoney() - cost);

                maintenanceCount++;
                totalCost += cost;
            }
        }

        simulation.getActualFinancialResult().setTrainMaintenance(simulation.getActualFinancialResult().getTrainMaintenance() - totalCost);

        if (totalCost != 0) {
            addLogs("===============================================");
            addLogs("             🚂 Train Maintenance Day           ");
            addLogs("===============================================");

            for (Train train : simulation.getTrainList()) {
                if (train.getAcquisitionDate().getMonth() == Utils.convertToDate(simulation.getCurrentTime()).getMonth() &&
                        train.getAcquisitionDate().getDay() == Utils.convertToDate(simulation.getCurrentTime()).getDay()) {

                    int cost = train.getLocomotive().getMaintenanceCost();

                    addLogs("Train Name: " + train.getLocomotive().getName() +
                            " | Maintenance Cost: 💰 " + cost);
                }
            }
            addLogs("-----------------------------------------------");
            addLogs(" Total Trains Maintained: " + maintenanceCount);
            addLogs(" Total Maintenance Cost:  💰 " + totalCost);
            addLogs(" Remaining Budget:        💰 " + simulation.getActualMoney());
            addLogs("===============================================");
            addLogs("");
        }
    }

    /**
     * Calculates and applies railway line maintenance costs for the current simulation day.
     * Updates the simulation's budget and logs the maintenance actions.
     */
    public void railwayLineMaintenanceCost() {
        int maintenanceCount = 0;
        int totalCost = 0;

        for (RailwayLine railwayLine : simulation.getRailwayLines()) {
            if (railwayLine.getConstructionDate().getMonth() == Utils.convertToDate(simulation.getCurrentTime()).getMonth() &&
                    railwayLine.getConstructionDate().getDay() == Utils.convertToDate(simulation.getCurrentTime()).getDay() &&
                    railwayLine.getConstructionDate().getYear() != Utils.convertToDate(simulation.getCurrentTime()).getYear()) {

                int cost = railwayLine.getRailwayType().getMaintenanceCost() * railwayLine.getPositionsRailwayLine().size();
                simulation.setActualMoney(simulation.getActualMoney() - cost);

                maintenanceCount++;
                totalCost += cost;
            }
        }

        simulation.getActualFinancialResult().setTrackMaintenance(simulation.getActualFinancialResult().getTrackMaintenance() - totalCost);

        if (totalCost != 0) {
            addLogs("===============================================");
            addLogs("         🛤 Railway Maintenance Day            ");
            addLogs("===============================================");

            for (RailwayLine railwayLine : simulation.getRailwayLines()) {
                if (totalCost != 0) {
                    addLogs("");
                }
                if (railwayLine.getConstructionDate().getMonth() == Utils.convertToDate(simulation.getCurrentTime()).getMonth() &&
                        railwayLine.getConstructionDate().getDay() == Utils.convertToDate(simulation.getCurrentTime()).getDay() &&
                        railwayLine.getConstructionDate().getYear() != Utils.convertToDate(simulation.getCurrentTime()).getYear()) {

                    int cost = railwayLine.getRailwayType().getMaintenanceCost() * railwayLine.getPositionsRailwayLine().size();

                    addLogs("Railway Line: \n" + railwayLine.getStation1().getName() + " \uD83D\uDD01 " + railwayLine.getStation2().getName() +
                            "\nMaintenance Cost: 💰 " + cost);
                }
            }

            addLogs("-----------------------------------------------");
            addLogs(" Total Railway Lines Maintained: " + maintenanceCount);
            addLogs(" Total Maintenance Cost:         💰 " + totalCost);
            addLogs(" Remaining Budget:               💰 " + simulation.getActualMoney());
            addLogs("===============================================");
            addLogs("");
        }
    }

    // ############################################
    //              Update Demand
    // ############################################

    /**
     * Updates the demand for each station based on the resources delivered.
     * Clears the unload cargo logs at the end of each year.
     */
    public void updateDemand() {
        List<Resource> resourcesFromStation;

        for (Station station : simulation.getStations()) {
            resourcesFromStation = findResourcesForStation(station);
            updateInStationDemand(station, resourcesFromStation);
            resourcesFromStation.clear();
        }

        // Clear the list at the end of each year to avoid issues in the following year
        simulation.getUnloadCargoLogsList().clear();
    }

    /**
     * Updates the demand grade for a station based on the quantity of resources delivered.
     *
     * @param station              the station to update
     * @param resourcesFromStation the list of resources delivered to the station
     */
    private void updateInStationDemand(Station station, List<Resource> resourcesFromStation) {
        List<ResourcesType> resourcesTypeList = new ArrayList<>();

        for (Demand demand : station.getDemandList()) {
            resourcesTypeList.add(demand.getResourcesType());
        }

        for (ResourcesType resourcesType : resourcesTypeList) {
            int quantity = findQuantityOfResource(resourcesType, resourcesFromStation);

            if (quantity >= NUMBER_OF_CARGO_TO_DOWNGRADE_DEMAND) {
                for (Demand demand : station.getDemandList()) {
                    if (demand.getResourcesType().getName().equals(resourcesType.getName())) {
                        demand.downGradeDemandGrade();
                    }
                }
            } else if (quantity <= NUMBER_OF_CARGO_TO_UPGRADE_DEMAND) {
                for (Demand demand : station.getDemandList()) {
                    if (demand.getResourcesType().getName().equals(resourcesType.getName())) {
                        demand.evolveDemandGrade();
                    }
                }
            }
        }
    }

    /**
     * Finds the total quantity of a specific resource type in a list of resources.
     *
     * @param resourcesType         the resource type to search for
     * @param resourcesFromStation  the list of resources
     * @return the total quantity found
     */
    private int findQuantityOfResource(ResourcesType resourcesType, List<Resource> resourcesFromStation) {
        int quantity = 0;
        for (Resource resource : resourcesFromStation) {
            if (resource.getResourceType().getName().equals(resourcesType.getName())) {
                quantity += resource.getQuantity();
            }
        }
        return quantity;
    }

    /**
     * Finds all resources delivered to a specific station.
     *
     * @param station the station to search for
     * @return a list of resources delivered to the station
     */
    private List<Resource> findResourcesForStation(Station station) {
        List<Resource> resourceList = new ArrayList<>();
        for (UnloadCargoLogs logs : simulation.getUnloadCargoLogsList()) {
            if (logs.getStation().equals(station)) {
                for (Resource resource : logs.getUnloadedResources()) {
                    resourceList.add(resource);
                }
            }
        }
        return resourceList;
    }

    /**
     * Sets the alert flag for date events logs.
     *
     * @param dateAlertLogs true to enable, false to disable
     */
    public void setDateAlertLogs(boolean dateAlertLogs) {
        this.dateAlertLogs = dateAlertLogs;
    }

    /**
     * Sets the alert flag for export events logs.
     *
     * @param exportAlertLogs true to enable, false to disable
     */
    public void setExportAlertLogs(boolean exportAlertLogs) {
        this.exportAlertLogs = exportAlertLogs;
    }

    /**
     * Sets the alert flag for generation events logs.
     *
     * @param generationAlertLogs true to enable, false to disable
     */
    public void setGenerationAlertLogs(boolean generationAlertLogs) {
        this.generationAlertLogs = generationAlertLogs;
    }

    /**
     * Sets the alert flag for transforming events logs.
     *
     * @param transformingAlertLogs true to enable, false to disable
     */
    public void setTransformingAlertLogs(boolean transformingAlertLogs) {
        this.transformingAlertLogs = transformingAlertLogs;
    }

    /**
     * Sets the alert flag for route events logs.
     *
     * @param routeAlertLogs true to enable, false to disable
     */
    public void setRouteAlertLogs(boolean routeAlertLogs) {
        this.routeAlertLogs = routeAlertLogs;
    }
}