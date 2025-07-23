package pt.ipp.isep.dei.domain.Event;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.FinancialResult.Demand;
import pt.ipp.isep.dei.domain.FinancialResult.UnloadCargoLogs;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TypeOfCargoMode;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Train;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a route event in the simulation, responsible for managing the movement of trains,
 * loading and unloading resources, and updating the simulation state.
 */
public class RouteEvent extends Event {

    /** Cost per km for electric fuel. */
    private final double ELECTRIC_FUEL_COST = 0.1;
    /** Cost per km for diesel fuel. */
    private final double DIESEL_FUEL_COST = 0.2;
    /** Cost per km for steam fuel. */
    private final double STEAM_FUEL_COST = 0.4;

    /** Maximum hours of service per day for a train. */
    private final int MAX_HOURS_OF_SERVICE = 14;
    /** Speed debuff per carriage in percentage points. */
    private final int DEBUFF_SPEED_PER_CARRIAGE = 5;
    /** Maximum total speed debuff allowed. */
    private final int MAX_SPEED_DEEBUF = 70;
    /** Resource transfer price per unit. */
    private final int RESOURCE_TRANSFER_PRICE = 1;

    /** The simulation instance associated with this event. */
    private Simulation simulation;
    /** The scenario instance associated with this event. */
    private Scenario scenario;
    /** Indicates if the route event is currently active. */
    private boolean activeStatus;
    /** The route associated with this event. */
    private Route route;
    /** The complete path of the route, as a list of station and railway line identifiers. */
    private List<String> completePath;

    /**
     * Constructs a RouteEvent with the specified parameters.
     *
     * @param name the name of the event
     * @param interval the interval between event triggers
     * @param actualDate the current date in the simulation
     * @param route the route to be managed by this event
     * @param simulation the simulation instance
     * @param scenario the scenario instance
     */
    public RouteEvent(String name, int interval, int actualDate, Route route, Simulation simulation, Scenario scenario) {
        super(name, interval, actualDate);
        this.simulation = simulation;
        this.scenario = scenario;
        this.activeStatus = false;
        this.route = route;
        this.completePath = findCompletePath();
    }

    /**
     * Builds the complete path of the route, alternating between stations and railway lines.
     *
     * @return a list of string identifiers representing the path
     */
    private List<String> findCompletePath() {
        List<String> completeList = new ArrayList<>();
        List<Station> pointOfRouteStations = new LinkedList<>(route.getPointOfRouteList().stream().map(PointOfRoute::getStation).toList());
        List<RailwayLine> usedRailwayLines = new LinkedList<>(route.getPath());

        if (pointOfRouteStations.isEmpty()) return completeList;

        Station oldStation = pointOfRouteStations.remove(0);
        addItemToPathList(oldStation, completeList);

        for (Station station : pointOfRouteStations) {
            while (!usedRailwayLines.isEmpty()) {
                RailwayLine railwayLine = usedRailwayLines.remove(0);
                addItemToPathList(railwayLine, completeList);

                if (railwayLine.getStation1().equals(station) || railwayLine.getStation2().equals(station)) {
                    addItemToPathList(station, completeList);
                    break;
                }
            }
        }

        for (RailwayLine remainingLine : usedRailwayLines) {
            addItemToPathList(remainingLine, completeList);
        }

        addItemToPathList(oldStation, completeList);
        return completeList;
    }

    /**
     * Adds a station or railway line to the path list with a unique identifier.
     *
     * @param item the station or railway line to add
     * @param completeList the list to add the identifier to
     */
    private void addItemToPathList(Object item, List<String> completeList) {
        int counter = 0;
        if (item instanceof Station) {
            for (String string : completeList) {
                if (string.charAt(0) == 'S') counter++;
            }
            completeList.add("S" + counter);
        } else if (item instanceof RailwayLine) {
            for (String string : completeList) {
                if (string.charAt(0) == 'R') counter++;
            }
            completeList.add("R" + counter);
        }
    }

    /**
     * Gets the simulation instance.
     *
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation instance.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the scenario instance.
     *
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets the scenario instance.
     *
     * @param scenario the scenario to set
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Checks if the route event is active.
     *
     * @return true if active, false otherwise
     */
    public boolean isActiveStatus() {
        return activeStatus;
    }

    /**
     * Sets the active status of the route event.
     *
     * @param activeStatus true to activate, false to deactivate
     */
    public void setActiveStatus(boolean activeStatus) {
        if (activeStatus) {
            super.setNextGenerationDate(simulation.getCurrentTime() + 1);
        } else {
            super.setNextGenerationDate(0);
        }
        this.activeStatus = activeStatus;
    }

    /**
     * Gets the route associated with this event.
     *
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the route associated with this event.
     *
     * @param route the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Gets the complete path of the route.
     *
     * @return the complete path as a list of string identifiers
     */
    public List<String> getCompletePath() {
        return completePath;
    }

    /**
     * Sets the complete path of the route.
     *
     * @param completePath the complete path to set
     */
    public void setCompletePath(List<String> completePath) {
        this.completePath = completePath;
    }

    /**
     * Triggers the route event, managing loading/unloading and advancing the simulation.
     *
     * @return a list of log messages generated during the event
     */
    @Override
    public List<String> trigger() {
        List<String> newLogs = new ArrayList<>();
        if (!route.getActiveFlag()) {
            super.setNextGenerationDate(simulation.getCurrentTime() + 1);
            return newLogs;
        }
        newLogs.add(" ");
        newLogs.add("🚂 Route Event Triggered: " + route.getName());

        PointOfRoute actualPointOfRoute = route.getPointOfRouteList().get(route.getPointPathPosition());
        actualPointOfRoute.getStation().setResourcesTypeRequested(scenario);

        Train train = route.getAssignedTrain();
        String stationName = actualPointOfRoute.getStation().getName();

        newLogs.add("🔄 Starting route event at point: " + stationName);

        List<Resource> unloadedResources = new ArrayList<>();
        // Unload resources
        if (route.getOldStatus()){
            unloadedResources = unloadResources(actualPointOfRoute);
            if (!unloadedResources.isEmpty()) {
                newLogs.add("📦 Resources unloaded from the train:");
                for (Resource r : unloadedResources) {
                    newLogs.add("   ↪️ " + r.getQuantity() + "x " + r.getResourceType().getName());
                }
            } else {
                newLogs.add("📭 No resources to loaded.");
            }
        }

        simulation.getUnloadCargoLogsList().add(new UnloadCargoLogs(unloadedResources, actualPointOfRoute.getStation()));

        // Load resources
        loadResources(actualPointOfRoute);

        // Cargo mode check
        if (actualPointOfRoute.getCargoMode() == TypeOfCargoMode.FULL &&
                train.getInventorySpaceOcupied() != train.getMaxInventorySpace()) {
            newLogs.add("⏳ Waiting for train to fill up completely to continue (FULL mode)");
            newLogs.add(" ");
            super.setNextGenerationDate(simulation.getCurrentTime() + 1);
            route.setOldStatus(false);
            return newLogs;
        } else if (actualPointOfRoute.getCargoMode() == TypeOfCargoMode.HALF &&
                train.getInventorySpaceOcupied() < train.getMaxInventorySpace() / 2) {
            newLogs.add("⏳ Waiting for train to reach half load (HALF mode)");
            newLogs.add(" ");
            super.setNextGenerationDate(simulation.getCurrentTime() + 1);
            route.setOldStatus(false);
            return newLogs;
        }

        // Update route position and simulation timing
        int travelTime = calculateTimeForThePath();
        super.setNextGenerationDate(simulation.getCurrentTime() + travelTime);
        route.nextPointPathPosition();
        route.setOldStatus(true);

        String days = " days";
        if (travelTime == 1){
            days = " day";
        }
        newLogs.add("🛤 Train departing for the next stop! ⏱ Estimated time: " + travelTime + days);

        // Add money from resources
        int moneyBefore = simulation.getActualMoney();
        accountancy(unloadedResources, actualPointOfRoute);
        int earnedMoney = simulation.getActualMoney() - moneyBefore;

        simulation.getActualFinancialResult().setEarning(simulation.getActualFinancialResult().getEarning() + earnedMoney);

        newLogs.add("💰 Revenue obtained from delivery: " + earnedMoney);

        // Expenses
        int fuelExpenses = removeFuelCost();
        simulation.getActualFinancialResult().setFuelCost(simulation.getActualFinancialResult().getFuelCost() - fuelExpenses);

        newLogs.add("💸 Fuel cost deducted: " + fuelExpenses);

        return newLogs;
    }

    // ##############################
    //          Expenses
    // ##############################

    /**
     * Removes the fuel cost from the simulation's money based on the train's fuel type and distance.
     *
     * @return the final cost deducted
     */
    private int removeFuelCost(){
        double fuelCostPerKM = 1;
        if (route.getAssignedTrain().getLocomotive().getFuelType().equals(FuelType.ELECTRICITY)){
            fuelCostPerKM = ELECTRIC_FUEL_COST;
        } else if (route.getAssignedTrain().getLocomotive().getFuelType().equals(FuelType.DIESEL)){
            fuelCostPerKM = DIESEL_FUEL_COST;
        } else if (route.getAssignedTrain().getLocomotive().getFuelType().equals(FuelType.STEAM)){
            fuelCostPerKM = STEAM_FUEL_COST;
        }

        int finalCost = (int) (fuelCostPerKM * findDistanceToNextPoint());

        simulation.setActualMoney(simulation.getActualMoney()-finalCost);
        return finalCost;
    }

    // ##############################
    //     Accountancy Resources
    // ##############################

    /**
     * Adds money to the simulation for each unloaded resource.
     *
     * @param unloadedResources the list of resources unloaded
     * @param pointOfRoute the current point of route
     */
    private void accountancy(List<Resource> unloadedResources, PointOfRoute pointOfRoute) {
        int moneyToAdd = 0;

        for (Resource resource : unloadedResources){
            moneyToAdd += findMoneyFromResourceTransference(resource, pointOfRoute);
        }

        simulation.setActualMoney(simulation.getActualMoney() + moneyToAdd);
    }

    /**
     * Calculates the money earned from transferring a resource.
     *
     * @param resource the resource transferred
     * @param pointOfRoute the current point of route
     * @return the money earned
     */
    private int findMoneyFromResourceTransference(Resource resource, PointOfRoute pointOfRoute) {
        double boosterMultiplyer = findMultiplyer(resource, pointOfRoute);
        int cost = RESOURCE_TRANSFER_PRICE * resource.getQuantity();
        double demandModifiedCost = cost * boosterMultiplyer;
        return (int) Math.round(demandModifiedCost);
    }

    /**
     * Finds the multiplier for a resource based on demand at the station.
     *
     * @param resource the resource
     * @param pointOfRoute the current point of route
     * @return the multiplier
     */
    private double findMultiplyer(Resource resource, PointOfRoute pointOfRoute) {
        for (Demand demand : pointOfRoute.getStation().getDemandList()){
            if (demand.getResourcesType().getName().equals(resource.getResourceType().getName())){
                return demand.getBooster();
            }
        }
        return 1;
    }

    // ##############################
    //        Unload Resources
    // ##############################

    /**
     * Unloads all resources from the train at the current point of route.
     *
     * @param actualPointOfRoute the current point of route
     * @return the list of resources unloaded
     */
    private List<Resource> unloadResources(PointOfRoute actualPointOfRoute) {
        List<Resource> unloadedResourcesList = new ArrayList<>();
        Train train = route.getAssignedTrain();

        unloadedResourcesList.addAll(train.getInventory().getAllResources());

        for (Resource resource : unloadedResourcesList){
            putResourceInInventory(resource, actualPointOfRoute);
            train.getInventory().removeResource(resource);
        }

        return unloadedResourcesList;
    }

    /**
     * Puts a resource into the inventory of the appropriate station association.
     *
     * @param resourceToLoad the resource to load
     * @param actualPointOfRoute the current point of route
     */
    private void putResourceInInventory(Resource resourceToLoad, PointOfRoute actualPointOfRoute) {
        for (StationAssociations associations : actualPointOfRoute.getStation().getAllAssociations()){
            if (!associationsNeedResource(associations, resourceToLoad)){
                continue;
            }
            addResourceToInvAssociation(associations, resourceToLoad);
            return;
        }
    }

    /**
     * Checks if a station association needs a given resource.
     *
     * @param association the station association
     * @param resourceToLoad the resource to check
     * @return true if needed, false otherwise
     */
    private boolean associationsNeedResource(StationAssociations association, Resource resourceToLoad) {
        if (association instanceof HouseBlock){
            for (ResourcesType resourcesType : ((HouseBlock) association).getConsumableResources()){
                if (resourcesType.getName().equals(resourceToLoad.getResourceType().getName())){
                    return true;
                }
            }
            return false;
        }

        if (association instanceof TransformingIndustry){
            for (ResourcesType resourcesType : ((TransformingIndustry) association).getClonedTransformingIndustry(scenario).getPrimaryResources()){
                if (resourcesType.getName().equals(resourceToLoad.getResourceType().getName())){
                    return true;
                }
            }
        }

        if (association instanceof MixedIndustry){
            List<ResourcesType> usedResourceTypes = new ArrayList<>();

            usedResourceTypes.addAll(((MixedIndustry) association).getClonedMixedIndustry(scenario).getExportedResources());
            List<ResourcesType> transformedResources = ((MixedIndustry) association).getClonedMixedIndustry(scenario).getTransformedResources();

            for (ResourcesType resourcesType: transformedResources){
                TransformingResource resource = (TransformingResource) resourcesType;
                usedResourceTypes.addAll(resource.getNeededResources());
            }

            for (ResourcesType resourcesType : usedResourceTypes){
                if (resourcesType.getName().equals(resourceToLoad.getResourceType().getName())){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Adds a resource to the inventory of a station association.
     *
     * @param associations the station association
     * @param resourceToLoad the resource to add
     */
    private void addResourceToInvAssociation(StationAssociations associations, Resource resourceToLoad) {
        for (Resource resource : associations.getInventory().getResources()){
            if (resource.getResourceType().getName().equals(resourceToLoad.getResourceType().getName())){
                associations.getInventory().addResource(resourceToLoad);
                return;
            }
        }

        if (associations instanceof TransformingIndustry){
            associations.getInventory().addResourceWithoutLimit(resourceToLoad);
        } else if (associations instanceof MixedIndustry){
            associations.getInventory().addResourceWithoutLimit(resourceToLoad);
        } else if (associations instanceof HouseBlock){
            associations.getInventory().addResourceWithoutLimit(resourceToLoad);
        }
    }

    // ##############################
    //          Load Resources
    // ##############################

    /**
     * Loads resources onto the train from the current point of route.
     *
     * @param actualPointOfRoute the current point of route
     * @return the number of cargo units picked
     */
    private int loadResources(PointOfRoute actualPointOfRoute) {
        List<ResourcesType> cargoToPickList = actualPointOfRoute.getCargoToPick();
        Train train = route.getAssignedTrain();
        int numberOfCargoPicked = 0;

        for (ResourcesType cargoToPick : cargoToPickList) {
            if (train.getInventory().inventoryIsFull()) {
                break;
            }
            int picked = pickCargo(actualPointOfRoute, train, cargoToPick);
            numberOfCargoPicked += picked;
        }

        return numberOfCargoPicked;
    }

    /**
     * Picks cargo from station associations and loads it onto the train.
     *
     * @param actualPointOfRoute the current point of route
     * @param train the train
     * @param cargoToPick the type of cargo to pick
     * @return the quantity picked
     */
    private int pickCargo(PointOfRoute actualPointOfRoute, Train train, ResourcesType cargoToPick) {
        int pickedCargo = 0;

        for (StationAssociations stationAssociation : actualPointOfRoute.getStation().getAllAssociations()) {

            if (!associationHasResource(stationAssociation, cargoToPick)) {
                continue;
            }

            int quantityAtStation = associationGetResourceQuantity(stationAssociation, cargoToPick);

            int availableSpaceInTrain = train.getMaxInventorySpace() - train.getInventorySpaceOcupied();

            int quantityToPick = Math.min(quantityAtStation, availableSpaceInTrain);

            if (quantityToPick > 0) {
                addResourceToInvOfTrain(train, quantityToPick, cargoToPick);

                stationAssociation.getInventory().removeResource(new Resource(cargoToPick, quantityToPick));

                Resource resource = stationAssociation.getInventory().getResourceByType(cargoToPick);
                if (resource != null && resource.getQuantity() <= 0) {
                    stationAssociation.getInventory().removeResource(resource);
                }

                pickedCargo += quantityToPick;
            }

            if (train.getInventory().inventoryIsFull()) {
                break;
            }
        }

        return pickedCargo;
    }

    /**
     * Adds a resource to the train's inventory.
     *
     * @param train the train
     * @param quantityOfResource the quantity to add
     * @param cargoToPick the type of resource
     */
    private void addResourceToInvOfTrain(Train train, int quantityOfResource, ResourcesType cargoToPick) {
        for (Resource resource : train.getInventory().getResources()) {
            if (resource.getResourceType().equals(cargoToPick)) {
                train.getInventory().addResource(resource);
                return;
            }
        }
        train.getInventory().addResource(new Resource(cargoToPick, quantityOfResource));
    }

    /**
     * Checks if a station association has a given resource.
     *
     * @param stationAssociation the station association
     * @param cargoToPick the resource type
     * @return true if available, false otherwise
     */
    private boolean associationHasResource(StationAssociations stationAssociation, ResourcesType cargoToPick) {
        for (Resource resource : stationAssociation.getInventory().getAllResources()) {
            if (resource.getResourceType().getName().equals(cargoToPick.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the quantity of a resource in a station association.
     *
     * @param stationAssociation the station association
     * @param cargoToPick the resource type
     * @return the quantity available
     */
    private int associationGetResourceQuantity(StationAssociations stationAssociation, ResourcesType cargoToPick) {
        for (Resource resource : stationAssociation.getInventory().getAllResources()) {
            if (resource.getResourceType().getName().equals(cargoToPick.getName())) {
                return resource.getQuantity();
            }
        }
        return 0;
    }

    // ##############################
    //  calculate Time For The Path
    // ##############################

    /**
     * Calculates the time required to travel to the next point.
     *
     * @return the number of days required
     */
    private int calculateTimeForThePath() {
        int distance = findDistanceToNextPoint();
        int maxDistancePerDay = debuffSpeedByCarriage(findMaxDistanceTravelledByDay());

        int totalDays = distance / maxDistancePerDay;
        if (distance % maxDistancePerDay != 0) {
            totalDays += 1;
        }

        return totalDays;
    }

    /**
     * Applies speed debuff based on the number of carriages.
     *
     * @param maxDistanceTravelledByDay the max distance per day without debuff
     * @return the debuffed max distance per day
     */
    private int debuffSpeedByCarriage(int maxDistanceTravelledByDay) {
        int carriageNumber = route.getAssignedTrain().getCarriages().size();
        int debuffPercentage = carriageNumber * DEBUFF_SPEED_PER_CARRIAGE;

        if (debuffPercentage > MAX_SPEED_DEEBUF) {
            debuffPercentage = MAX_SPEED_DEEBUF;
        }

        double debuffedDistance = maxDistanceTravelledByDay * (1 - debuffPercentage / 100.0);
        return (int) debuffedDistance;
    }

    /**
     * Finds the maximum distance a train can travel in a day.
     *
     * @return the max distance per day
     */
    public int findMaxDistanceTravelledByDay() {
        double acceleration = route.getAssignedTrain().getLocomotive().getAcceleration();
        double topSpeed = route.getAssignedTrain().getLocomotive().getTopSpeed();

        if (acceleration <= 0 || topSpeed <= 0) return 0;

        double timeToTopSpeed = topSpeed / acceleration;

        if (timeToTopSpeed >= MAX_HOURS_OF_SERVICE) {
            return (int) Math.floor(0.5 * acceleration * MAX_HOURS_OF_SERVICE);
        }

        double distanceAccelerating = 0.5 * acceleration * timeToTopSpeed * timeToTopSpeed;
        double distanceAtTopSpeed = topSpeed * (MAX_HOURS_OF_SERVICE - timeToTopSpeed);

        return (int) Math.floor(distanceAccelerating + distanceAtTopSpeed);
    }

    /**
     * Finds the distance to the next point in the route.
     *
     * @return the distance in units
     */
    private int findDistanceToNextPoint() {
        int pointPathPosition = route.getPointPathPosition();
        int totalDistance = 0;

        for (RailwayLine railwayLine : getUsedRailways(pointPathPosition)) {
            totalDistance += railwayLine.getPositionsRailwayLine().size();
        }

        return totalDistance;
    }

    /**
     * Gets the list of railway lines used between two points in the route.
     *
     * @param pointPathPosition the current point position
     * @return the list of railway lines used
     */
    private List<RailwayLine> getUsedRailways(int pointPathPosition) {
        List<RailwayLine> result = new ArrayList<>();
        String fromStation = "S" + pointPathPosition;
        String toStation = "S" + (pointPathPosition + 1);
        boolean collecting = false;

        for (String entry : completePath) {
            if (entry.equals(fromStation)) {
                collecting = true;
                continue;
            }
            if (entry.equals(toStation)) break;

            if (collecting && entry.startsWith("R")) {
                int index = Integer.parseInt(entry.substring(1));
                if (index >= 0 && index < route.getPath().size()) {
                    result.add(route.getPath().get(index));
                }
            }
        }

        return result;
    }
}