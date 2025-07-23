package pt.ipp.isep.dei.domain.Simulation;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.FinancialResult.UnloadCargoLogs;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.FinancialResult.YearFinancialResult;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.repository.CarriageRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simulation of a scenario, managing time, money, routes, trains, stations, and railway lines.
 */
public class Simulation implements Serializable {

    private CarriageRepository carriageRepository;
    private Scenario scenario;
    private Map map;
    private int currentTime;
    private int maxTime;
    private int converterMonthSecond = 2;
    private int actualMoney;
    private String name;

    private List<HouseBlock> houseBlocks = new ArrayList<>();
    private List<Industry> industries = new ArrayList<>();
    private List<Station> stations = new ArrayList<>();
    private List<RailwayLine> railwayLines = new ArrayList<>();
    private List<Locomotive> availableDateLocomotives = new ArrayList<>();
    private List<Carriage> availableDateCarriages = new ArrayList<>();
    private List<Locomotive> boughtLocomotives = new ArrayList<>();
    private List<Carriage> boughtCarriages = new ArrayList<>();
    private List<Train> trainList = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
    private List<YearFinancialResult> financialResults = new ArrayList<>();
    private List<UnloadCargoLogs> unloadCargoLogsList = new ArrayList<>();

    /**
     * Constructs a Simulation with the given name and scenario.
     *
     * @param name     the name of the simulation
     * @param scenario the scenario to simulate
     */
    public Simulation(String name, Scenario scenario) {
        initializeCarriageRepository();
        this.name = name;
        setScenario(scenario);
        setInformationScenario(scenario);
        financialResults.add(new YearFinancialResult(Utils.convertToDate(currentTime).getYear()));
    }

    /**
     * Initializes the carriage repository if it is not already initialized.
     */
    private void initializeCarriageRepository() {
        if (carriageRepository == null) {
            Repositories repositories = Repositories.getInstance();
            carriageRepository = repositories.getCarriageRepository();
        }
    }

    /**
     * Sets the list of house blocks.
     *
     * @param houseBlocks the list of house blocks to set
     */
    public void setHouseBlocks(List<HouseBlock> houseBlocks) {
        this.houseBlocks = houseBlocks;
    }

    /**
     * Gets the list of house blocks.
     *
     * @return the list of house blocks
     */
    public List<HouseBlock> getHouseBlocks() {
        return houseBlocks;
    }

    /**
     * Sets the list of industries.
     *
     * @param industries the list of industries to set
     */
    public void setIndustries(List<Industry> industries) {
        this.industries = industries;
    }

    /**
     * Gets the list of industries.
     *
     * @return the list of industries
     */
    public List<Industry> getIndustries() {
        return industries;
    }

    /**
     * Sets the scenario for the simulation and initializes related data.
     *
     * @param scenario the scenario to set
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        this.map = scenario.getMap();
    }

    /**
     * Gets the scenario of the simulation.
     *
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets the information of the scenario, such as max time, initial money, and available locomotives/carriages.
     *
     * @param scenario the scenario to use for information
     */
    public void setInformationScenario(Scenario scenario) {
        this.maxTime = convertTime(scenario.getEndDate());
        this.actualMoney = scenario.getInitialMoney();
        this.currentTime = scenario.getBeginningDate().getTotalDays();
        setAvailableDateLocomotivesAndCarriages(scenario.getBeginningDate().getYear());
    }

    /**
     * Converts a TimeDate object to an integer value representing the total days.
     *
     * @param date date to be converted
     * @return total days
     */
    private int convertTime(TimeDate date) {
        return date.getTotalDays();
    }

    /**
     * Sets the available locomotives and carriages based on the start year.
     *
     * @param startYear the year to filter available locomotives and carriages
     */
    private void setAvailableDateLocomotivesAndCarriages(int startYear) {
        for (Locomotive locomotive : scenario.getAvailableLocomotiveList()) {
            if (locomotive.getStartYearOperation() <= startYear) {
                if (!existLocomotiveInAvailableDateLocomotives(locomotive)) {
                    availableDateLocomotives.add(locomotive);
                }
            }
        }
        for (Carriage carriage : carriageRepository.getCarriageList()) {
            if (carriage.getStartYearOperation() <= startYear) {
                if (!existCarriageInAvailableDateCarriages(carriage)) {
                    availableDateCarriages.add(carriage);
                }
            }
        }
    }

    /**
     * Checks if a locomotive already exists in the available date locomotives list.
     *
     * @param locomotive the locomotive to check
     * @return true if exists, false otherwise
     */
    private boolean existLocomotiveInAvailableDateLocomotives(Locomotive locomotive) {
        for (Locomotive loc : availableDateLocomotives) {
            if (loc.getName().equals(locomotive.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a carriage already exists in the available date carriages list.
     *
     * @param carriage the carriage to check
     * @return true if exists, false otherwise
     */
    private boolean existCarriageInAvailableDateCarriages(Carriage carriage) {
        for (Carriage car : availableDateCarriages) {
            if (car.getName().equals(carriage.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the name of the simulation.
     *
     * @return the simulation name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the simulation.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the current time in the simulation.
     *
     * @return the current time
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Sets the current time in the simulation.
     *
     * @param currentTime the current time to set
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * Gets the maximum time allowed in the simulation.
     *
     * @return the maximum time
     */
    public int getMaxTime() {
        return maxTime;
    }

    /**
     * Sets the maximum time allowed in the simulation.
     *
     * @param maxTime the maximum time to set
     */
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    /**
     * Gets the number of seconds that correspond to one month in the simulation.
     *
     * @return the converter month second value
     */
    public int getConverterMonthSecond() {
        return converterMonthSecond;
    }

    /**
     * Sets the number of seconds that correspond to one month in the simulation.
     *
     * @param converterMonthSecond the value to set
     */
    public void setConverterMonthSecond(int converterMonthSecond) {
        this.converterMonthSecond = converterMonthSecond;
    }

    /**
     * Gets the current amount of money in the simulation.
     *
     * @return the current money
     */
    public int getActualMoney() {
        return actualMoney;
    }

    /**
     * Sets the current amount of money in the simulation.
     *
     * @param actualMoney the amount of money to set
     */
    public void setActualMoney(int actualMoney) {
        this.actualMoney = actualMoney;
    }

    /**
     * Adds a specified amount of money to the current money in the simulation.
     *
     * @param quantityMoney the amount of money to add
     */
    public void addMoney(int quantityMoney) {
        this.actualMoney += quantityMoney;
    }

    /**
     * Gets the map of the simulation.
     *
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Gets the list of stations in the simulation.
     *
     * @return the list of stations
     */
    public List<Station> getStations() {
        return stations;
    }

    /**
     * Adds a station to the simulation and the map.
     *
     * @param station the station to add
     * @return true if the station was added, false otherwise
     */
    public boolean addStation(Station station) {
        if (map.addElement(station)) {
            stations.add(station);
            return true;
        }
        return false;
    }

    /**
     * Removes a station from the simulation and the map.
     *
     * @param station the station to remove
     */
    public void removeStation(Station station) {
        map.removeElement(station);
        stations.remove(station);
    }

    /**
     * Gets the list of railway lines in the simulation.
     *
     * @return the list of railway lines
     */
    public List<RailwayLine> getRailwayLines() {
        return railwayLines;
    }

    /**
     * Adds a railway line to the simulation and the map.
     *
     * @param railwayLine the railway line to add
     */
    public void addRailwayLine(RailwayLine railwayLine) {
        map.addElement(railwayLine);
        railwayLines.add(railwayLine);
    }

    /**
     * Removes a railway line from the simulation and the map.
     *
     * @param railwayLine the railway line to remove
     * @throws IllegalArgumentException if the railway line is null
     */
    public void removeRailwayLine(RailwayLine railwayLine) {
        if (railwayLine == null) {
            throw new IllegalArgumentException("Railway line cannot be null");
        }
        railwayLines.remove(railwayLine);
        map.removeElement(railwayLine);
    }

    /**
     * Gets the list of routes in the simulation.
     *
     * @return the list of routes
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * Gets the list of routes in the simulation.
     *
     * @return the list of routes
     */
    public List<Route> getRouteList() {
        return routes;
    }

    /**
     * Adds a route to the simulation.
     *
     * @param route the route to add
     */
    public void addRoute(Route route) {
        this.routes.add(route);
    }

    /**
     * Removes a route from the simulation.
     *
     * @param route the route to remove
     * @throws IllegalArgumentException if the route is null
     */
    public void removeRoute(Route route) {
        if (route == null) {
            throw new IllegalArgumentException("route cannot be null");
        }
        routes.remove(route);
    }

    /**
     * Gets the list of available locomotives for the current date.
     *
     * @return the list of available locomotives
     */
    public List<Locomotive> getAvailableDateLocomotives() {
        return availableDateLocomotives;
    }

    /**
     * Adds a locomotive to the list of available locomotives for the current date.
     *
     * @param locomotive the locomotive to add
     * @throws IllegalArgumentException if the locomotive is null
     */
    public void addAvailableDateLocomotive(Locomotive locomotive) {
        if (locomotive == null) {
            throw new IllegalArgumentException("Locomotive cannot be null");
        }
        if (!existLocomotiveInAvailableDateLocomotives(locomotive)) {
            availableDateLocomotives.add(locomotive);
        }
    }

    /**
     * Gets the list of available carriages for the current date.
     *
     * @return the list of available carriages
     */
    public List<Carriage> getAvailableDateCarriages() {
        return availableDateCarriages;
    }

    /**
     * Adds a carriage to the list of available carriages for the current date.
     *
     * @param carriage the carriage to add
     * @throws IllegalArgumentException if the carriage is null
     */
    public void addAvailableDateCarriage(Carriage carriage) {
        if (carriage == null) {
            throw new IllegalArgumentException("Carriage cannot be null");
        }
        if (!existCarriageInAvailableDateCarriages(carriage)) {
            availableDateCarriages.add(carriage);
        }
    }

    /**
     * Gets the list of bought locomotives.
     *
     * @return the list of bought locomotives
     */
    public List<Locomotive> getBoughtLocomotives() {
        return boughtLocomotives;
    }

    /**
     * Adds a locomotive to the list of bought locomotives.
     *
     * @param locomotive the locomotive to add
     * @throws IllegalArgumentException if the locomotive is null
     */
    public void addBoughtLocomotive(Locomotive locomotive) {
        if (locomotive == null) {
            throw new IllegalArgumentException("Locomotive cannot be null");
        }
        boughtLocomotives.add(locomotive);
    }

    /**
     * Removes a locomotive from the list of bought locomotives.
     *
     * @param locomotive the locomotive to remove
     * @throws IllegalArgumentException if the locomotive is null
     */
    public void removeLocomotive(Locomotive locomotive) {
        if (locomotive == null) {
            throw new IllegalArgumentException("Locomotive cannot be null");
        }
        boughtLocomotives.remove(locomotive);
    }

    /**
     * Gets the list of bought carriages.
     *
     * @return the list of bought carriages
     */
    public List<Carriage> getBoughtCarriages() {
        return boughtCarriages;
    }

    /**
     * Adds a carriage to the list of bought carriages.
     *
     * @param carriage the carriage to add
     * @throws IllegalArgumentException if the carriage is null
     */
    public void addBoughtCarriages(Carriage carriage) {
        if (carriage == null) {
            throw new IllegalArgumentException("Carriage cannot be null");
        }
        boughtCarriages.add(carriage);
    }

    /**
     * Removes a carriage from the list of bought carriages.
     *
     * @param carriage the carriage to remove
     * @throws IllegalArgumentException if the carriage is null
     */
    public void removeCarriage(Carriage carriage) {
        if (carriage == null) {
            throw new IllegalArgumentException("Carriage cannot be null");
        }
        boughtCarriages.remove(carriage);
    }

    /**
     * Gets the list of acquired (bought) locomotives.
     *
     * @return the list of acquired locomotives
     */
    public List<Locomotive> getAcquiredLocomotives() {
        return boughtLocomotives;
    }

    /**
     * Gets the list of acquired (bought) carriages.
     *
     * @return the list of acquired carriages
     */
    public List<Carriage> getAcquiredCarriages() {
        return boughtCarriages;
    }

    /**
     * Adds a train to the simulation.
     *
     * @param train the train to add
     * @throws IllegalArgumentException if the train is null
     */
    public void addTrain(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("Train cannot be null");
        }
        trainList.add(train);
    }

    /**
     * Gets the list of trains in the simulation.
     *
     * @return the list of trains
     */
    public List<Train> getTrainList() {
        return trainList;
    }

    /**
     * Removes a train from the simulation.
     *
     * @param train the train to remove
     * @throws IllegalArgumentException if the train is null
     */
    public void removeTrain(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("Train cannot be null");
        }
        trainList.remove(train);
    }

    /**
     * Gets the list of financial results for each year.
     *
     * @return the list of year financial results
     */
    public List<YearFinancialResult> getFinancialResults() {
        return financialResults;
    }

    /**
     * Sets the list of financial results.
     *
     * @param financialResults the list of year financial results to set
     */
    public void setFinancialResults(List<YearFinancialResult> financialResults) {
        this.financialResults = financialResults;
    }

    /**
     * Gets the actual (current) financial result.
     *
     * @return the current year financial result
     */
    public YearFinancialResult getActualFinancialResult() {
        return financialResults.getLast();
    }

    /**
     * Gets the name of the scenario.
     *
     * @return the scenario name
     */
    public String getScenarioName() {
        return scenario.getName();
    }

    /**
     * Gets the list of unload cargo logs.
     *
     * @return the list of unload cargo logs
     */
    public List<UnloadCargoLogs> getUnloadCargoLogsList() {
        return unloadCargoLogsList;
    }

    /**
     * Sets the list of unload cargo logs.
     *
     * @param unloadCargoLogsList the list of unload cargo logs to set
     */
    public void setUnloadCargoLogsList(List<UnloadCargoLogs> unloadCargoLogsList) {
        this.unloadCargoLogsList = unloadCargoLogsList;
    }

    /**
     * Returns a string representation of the simulation.
     *
     * @return a string with the simulation name, current time, and money
     */
    @Override
    public String toString() {
        return name + " - Current Time: " + currentTime + " | Money: " + actualMoney;
    }
}