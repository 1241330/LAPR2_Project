package pt.ipp.isep.dei.domain.Map;

import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.*;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a map containing cities, industries, stations, railway lines, and house blocks.
 * Provides methods for managing these elements, their positions, and associated repositories.
 */
public class Map implements Serializable {

    private static final String EXTENSION = ".ser";

    private MapRepository mapRepository;
    private CityRepository cityRepository;
    private StationRepository stationRepository;
    private IndustryRepository industryRepository;
    private RailwayLineRepository railwayLineRepository;
    private HouseBlockRepository houseBlockRepository;
    private ScenarioRepository scenarioRepository;

    private int id;
    private String name;
    private String lastNameUsed;
    private List<MapElement> mapElementsUsed;
    private List<Position> occupiedPositions;
    private Size pixelSize;
    private Size kmSize;
    private String savedFileName;

    /**
     * Constructs a Map with a specified name and pixel size.
     *
     * @param name      The name of the map.
     * @param pixelSize The pixel size of the map.
     * @throws IllegalArgumentException if the name is null/empty or the size is null.
     */
    public Map(String name, Size pixelSize) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Map name cannot be null or empty");
        }
        if (pixelSize == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        initializeRepositories();
        this.name = name;
        this.lastNameUsed = name;
        setIdMap();
        this.pixelSize = pixelSize;
        setKmSize();
        this.mapElementsUsed = new ArrayList<>();
        this.occupiedPositions = new ArrayList<>();
        setSavedFileName();
    }

    /**
     * Initializes all repositories used by the map.
     */
    private void initializeRepositories() {
        mapRepository = Repositories.getInstance().getMapRepository();
        cityRepository = new CityRepository();
        stationRepository = new StationRepository();
        railwayLineRepository = new RailwayLineRepository();
        industryRepository = new IndustryRepository();
        scenarioRepository = new ScenarioRepository();
        houseBlockRepository = new HouseBlockRepository();
    }

    /**
     * Sets a unique ID for the map.
     */
    public void setIdMap() {
        int counter = 0;
        do {
            this.id = counter;
            counter++;
        } while (mapRepository.existsMapWithID(id));
    }

    /**
     * Gets the name of the map.
     *
     * @return The name of the map.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the map.
     *
     * @param name The new name of the map.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Map name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Gets the last name used for the map.
     *
     * @return The last name used.
     */
    public String getLastNameUsed() {
        return lastNameUsed;
    }

    /**
     * Sets the last name used for the map.
     *
     * @param lastNameUsed The last name to set.
     */
    public void setLastNameUsed(String lastNameUsed) {
        this.lastNameUsed = lastNameUsed;
    }

    /**
     * Gets the saved file name for the map.
     *
     * @return The saved file name.
     */
    public String getSavedFileName() {
        return savedFileName;
    }

    /**
     * Sets the saved file name for the map.
     */
    public void setSavedFileName() {
        this.savedFileName = name + EXTENSION;
    }

    /**
     * Gets the list of map elements used in the map.
     *
     * @return A list of map elements.
     */
    public List<MapElement> getMapElementsUsed() {
        return mapElementsUsed;
    }

    /**
     * Gets the pixel size of the map.
     *
     * @return The pixel size.
     */
    public Size getPixelSize() {
        return pixelSize;
    }

    /**
     * Sets the pixel size of the map.
     *
     * @param pixelSize The new pixel size.
     * @throws IllegalArgumentException if the size is null.
     */
    public void setPixelSize(Size pixelSize) {
        if (pixelSize == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        this.pixelSize = pixelSize;
        setKmSize();
    }

    /**
     * Gets the kilometer size of the map.
     *
     * @return The kilometer size.
     */
    public Size getKmSize() {
        return kmSize;
    }

    /**
     * Sets the kilometer size of the map based on the pixel size.
     */
    private void setKmSize() {
        double widthInKm = Utils.convertBetweenPixeisAndKms(pixelSize.getWidth(), true);
        double heightInKm = Utils.convertBetweenPixeisAndKms(pixelSize.getHeight(), true);
        this.kmSize = new Size(widthInKm, heightInKm);
    }

    /**
     * Gets the unique identifier of the map.
     *
     * @return The map ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the number of active scenarios in the map.
     *
     * @return The number of active scenarios.
     */
    public int getNumActiveScenarios() {
        return scenarioRepository.getAllScenarios().size();
    }

    /**
     * Gets the list of stations in the map.
     *
     * @return A list of stations.
     */
    public List<Station> getStationList() {
        return stationRepository.getStations();
    }

    /**
     * Gets the list of cities in the map.
     *
     * @return A list of cities.
     */
    public List<City> getCitiesList() {
        return cityRepository.getAllCities();
    }

    /**
     * Gets the list of railway lines in the map.
     *
     * @return A list of railway lines.
     */
    public List<RailwayLine> getRailwayLines() {
        return railwayLineRepository.getAllRailwayLines();
    }

    /**
     * Gets the list of industries in the map.
     *
     * @return A list of industries.
     */
    public List<Industry> getIndustriesList() {
        return industryRepository.getAllIndustries();
    }

    /**
     * Gets the list of house blocks in the map.
     *
     * @return A list of house blocks.
     */
    public List<HouseBlock> getHouseBlockList() {
        return houseBlockRepository.getAllHouseBlocks();
    }

    /**
     * Gets the list of occupied positions in the map.
     *
     * @return A list of occupied positions.
     */
    public List<Position> getOccupiedPositions() {
        listAllOccupiedPositions();
        return new ArrayList<>(occupiedPositions);
    }

    /**
     * Updates the list of all occupied positions in the map.
     */
    public void listAllOccupiedPositions() {
        occupiedPositions.clear();
        for (MapElement elem : mapElementsUsed) {
            List<Position> occupiedByElement = elem.getOccupiedPositions();
            occupiedPositions.addAll(occupiedByElement);
        }
    }

    /**
     * Checks if a list of positions is occupied.
     *
     * @param positionsToCheck The list of positions to check.
     * @return True if any position is occupied, false otherwise.
     */
    public boolean positionOccupiedList(List<Position> positionsToCheck) {
        if (positionsToCheck.isEmpty()) {
            return true;
        }
        for (Position p1 : positionsToCheck) {
            for (Position p2 : occupiedPositions) {
                if (p1.equalsPosition(p2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a map element to the map.
     *
     * @param element The map element to add.
     * @return True if the element was added successfully, false otherwise.
     */
    public boolean addElement(MapElement element) {
        element.setPosition(new Position(element.getPosition().getX(), element.getPosition().getY()));
        listAllOccupiedPositions();
        if (element instanceof RailwayLine) {
            if (railwayLineRepository.addRailwayLine((RailwayLine) element)) {
                mapElementsUsed.add(element);
                listAllOccupiedPositions();
                return true;
            }
            return false;
        }
        if (positionOccupiedList(element.getOccupiedPositions())) {
            return false;
        }
        if (element instanceof City) {
            if (cityRepository.addCity((City) element)) {
                for (HouseBlock houseBlock : ((City) element).getHouseBlocks()) {
                    houseBlockRepository.addHouseBlock(houseBlock);
                }
                mapElementsUsed.add(element);
                listAllOccupiedPositions();
                return true;
            }
        } else if (element instanceof Industry) {
            if (industryRepository.addIndustry((Industry) element)) {
                mapElementsUsed.add(element);
                listAllOccupiedPositions();
                return true;
            }
        } else if (element instanceof Station) {
            if (stationRepository.addStation((Station) element)) {
                mapElementsUsed.add(element);
                listAllOccupiedPositions();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a map element from the map.
     *
     * @param element The map element to remove.
     * @return True if the element was removed successfully, false otherwise.
     */
    public boolean removeElement(MapElement element) {
        if (element instanceof City) {
            if (cityRepository.removeCity((City) element)) {
                for (HouseBlock houseBlock : ((City) element).getHouseBlocks()) {
                    houseBlockRepository.removeHouseBlock(houseBlock);
                }
                mapElementsUsed.remove(element);
                listAllOccupiedPositions();
                return true;
            }
        } else if (element instanceof Industry) {
            mapElementsUsed.remove(element);
            listAllOccupiedPositions();
            return true;
        } else if (element instanceof Station) {
            if (stationRepository.removeStation((Station) element)) {
                mapElementsUsed.remove(element);
                listAllOccupiedPositions();
                return true;
            }
        } else if (element instanceof RailwayLine) {
            if (railwayLineRepository.removeRailwayLine((RailwayLine) element)) {
                mapElementsUsed.remove(element);
                listAllOccupiedPositions();
                return true;
            }
        }
        listAllOccupiedPositions();
        return false;
    }

    /**
     * Gets the list of occupied positions excluding those of a specific railway line type.
     *
     * @param typeLine The railway line type to exclude.
     * @return A list of positions excluding the specified railway line type.
     */
    public List<Position> getOccupiedPositionsWithoutRespectiveLines(RailwayLineType typeLine) {
        List<Position> positions = new ArrayList<>(occupiedPositions);
        for (RailwayLine actualRailwayLine : railwayLineRepository.getAllRailwayLines()) {
            if (typeLine.equals(actualRailwayLine.getTypeEnum())) {
                for (Position position : actualRailwayLine.getPositionsRailwayLine()) {
                    positions.remove(position);
                }
            }
        }
        return positions;
    }

    /**
     * Adds a scenario to the map.
     *
     * @param scenario The scenario to add.
     */
    public void addScenario(Scenario scenario) {
        scenarioRepository.addScenario(scenario);
    }

    /**
     * Removes a scenario from the map.
     *
     * @param scenario The scenario to remove.
     */
    public void removeScenario(Scenario scenario) {
        scenarioRepository.removeScenario(scenario);
    }

    /**
     * Gets the list of scenarios in the map.
     *
     * @return A list of scenarios.
     */
    public List<Scenario> getScenarios() {
        return scenarioRepository.getAllScenarios();
    }

    /**
     * Clears all scenarios from the map.
     */
    public void clearScenarios() {
        scenarioRepository.clear();
    }

    /**
     * Gets the emoji matrix representation of the map.
     *
     * @return A 2D array of emojis representing the map.
     */
    public String[][] getEmojiMatrix() {
        int height = pixelSize.getHeight();
        int width = pixelSize.getWidth();
        String[][] mapa = new String[height][width];

        // Fill with empty string (white background)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapa[y][x] = "";
            }
        }

        for (MapElement element : mapElementsUsed) {
            String emoji;
            if (element instanceof City) emoji = "\uD83C\uDFE0";
            else if (element instanceof Industry) emoji = "🏭";
            else if (element instanceof Station) emoji = "🚉";
            else if (element instanceof RailwayLine) emoji = "⬛";
            else emoji = "⬛";

            for (Position pos : element.getOccupiedPositions()) {
                int x = pos.getX();
                int y = pos.getY();
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    mapa[y][x] = emoji;
                }
            }
        }

        return mapa;
    }

    /**
     * Returns a string representation of the map, including its name and size.
     *
     * @return A formatted string representation of the map.
     */
    @Override
    public String toString() {
        return name + ": (width: " + pixelSize.getWidth() + ", height: " + pixelSize.getHeight() + ")";
    }
}