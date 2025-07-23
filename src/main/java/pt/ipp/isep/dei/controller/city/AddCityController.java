package pt.ipp.isep.dei.controller.city;

import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller responsible for managing the creation and addition of a city to a specific map.
 * Allows configuring the city's name, position, number of house blocks,
 * and handles both manual and automatic generation of residential blocks.
 */
public class AddCityController {

    // === Instance Variables ===

    private MapRepository mapRepository;
    private Map actualMap;
    private City city;
    private String cityName;
    private Position positionCity;
    private int numberHouseBlocks;
    private List<HouseBlock> houseBlockList = new ArrayList<>();

    // === Constructor ===

    /**
     * Constructs a controller for managing city addition for a specific map ID.
     *
     * @param idMap ID of the map to which the city will be added
     */
    public AddCityController(int idMap) {
        getMapRepository();
        actualMap = mapRepository.getMapById(idMap);
    }

    // === Repository Setup ===

    /**
     * Initializes the map repository instance if it has not been already.
     */
    private void getMapRepository() {
        if (mapRepository == null) {
            Repositories repositories = Repositories.getInstance();
            mapRepository = repositories.getMapRepository();
        }
    }

    // === Getters and Setters ===

    /**
     * Gets the current map.
     * @return the actual map
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Gets the current city.
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city.
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Gets the city name.
     * @return the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the city name.
     * @param cityName the city name to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Gets the position of the city.
     * @return the city position
     */
    public Position getPositionCity() {
        return positionCity;
    }

    /**
     * Sets the position of the city.
     * @param positionCity the position to set
     */
    public void setPositionCity(Position positionCity) {
        this.positionCity = positionCity;
    }

    /**
     * Gets the list of house blocks.
     * @return the house block list
     */
    public List<HouseBlock> getHouseBlockList() {
        return houseBlockList;
    }

    /**
     * Sets the list of house blocks.
     * @param houseBlockList the house block list to set
     */
    public void setHouseBlockList(List<HouseBlock> houseBlockList) {
        this.houseBlockList = houseBlockList;
    }

    /**
     * Gets the number of house blocks.
     * @return the number of house blocks
     */
    public int getNumberHouseBlocks() {
        return numberHouseBlocks;
    }

    /**
     * Sets the number of house blocks.
     * @param numberHouseBlocks the number of house blocks to set
     */
    public void setNumberHouseBlocks(int numberHouseBlocks) {
        this.numberHouseBlocks = numberHouseBlocks;
    }

    // === Map and Position Utilities ===

    /**
     * Gets the X coordinate of the city center position.
     * @return the X coordinate
     */
    public int getPositionX() {
        return positionCity.getX();
    }

    /**
     * Gets the Y coordinate of the city center position.
     * @return the Y coordinate
     */
    public int getPositionY() {
        return positionCity.getY();
    }

    /**
     * Gets the map's width.
     * @return the map width
     */
    public int getWidthMap() {
        return actualMap.getPixelSize().getWidth();
    }

    /**
     * Gets the map's height.
     * @return the map height
     */
    public int getHeightMap() {
        return actualMap.getPixelSize().getHeight();
    }

    /**
     * Gets the list of currently occupied positions on the map.
     * @return the list of occupied positions
     */
    public List<Position> getOccupiedPositionsMap() {
        return actualMap.getOccupiedPositions();
    }

    /**
     * Clears the current house block list.
     */
    public void clearHouseBlockList() {
        houseBlockList.clear();
    }

    /**
     * Gets the number of available positions (unoccupied) on the map.
     * @return the number of free positions
     */
    public int getNumberFreePositions() {
        return (getHeightMap() * getWidthMap()) - getOccupiedPositionsMap().size();
    }

    // === House Block Generation ===

    /**
     * Automatically generates house blocks distributed around the city center
     * using a normal (Gaussian) distribution.
     * Ensures positions are not already occupied and simulates realistic urban sprawl.
     */
    public void setAutomaticHouseBlocks() {
        houseBlockList.clear();
        List<Position> occupied = getOccupiedPositionsMap();
        List<Position> addedHouses = new ArrayList<>();
        int targetHouses = getNumberHouseBlocks();
        Random rand = new Random();

        int width = getWidthMap();
        int height = getHeightMap();
        double centerX = getPositionX();
        double centerY = getPositionY();
        double scaleValue = 0.065;
        int nAttempts = 0;

        while (addedHouses.size() < targetHouses) {
            if (nAttempts == 0) {
                scaleValue = 0.065;
            } else {
                scaleValue *= 1.005; // slightly increase dispersion if failing
            }

            double sigma = targetHouses * scaleValue;
            double dx = rand.nextGaussian() * sigma;
            double dy = rand.nextGaussian() * sigma;
            int x = (int) Math.round(centerX + dx);
            int y = (int) Math.round(centerY + dy);

            // Ignore positions outside the map boundaries
            if (x < 0 || x >= width || y < 0 || y >= height) {
                continue;
            }

            Position pos = new Position(x, y);
            boolean doContinue = false;

            // Check if the position is already occupied
            for (Position occupiedPos : occupied) {
                if (occupiedPos.getX() == x && occupiedPos.getY() == y) {
                    doContinue = true;
                    nAttempts++;
                    break;
                }
            }

            // Check if the position was already added
            if (!doContinue) {
                for (Position added : addedHouses) {
                    if (added.getX() == x && added.getY() == y) {
                        doContinue = true;
                        nAttempts++;
                        break;
                    }
                }
            }

            if (doContinue) {
                continue;
            }

            // Gaussian acceptance probability favors proximity to city center
            double distance = Math.sqrt(dx * dx + dy * dy);
            double acceptanceProbability = Math.exp(-0.5 * Math.pow(distance / sigma, 2));
            if (rand.nextDouble() < acceptanceProbability) {
                nAttempts = 0;
                houseBlockList.add(new HouseBlock(pos, cityName));
                addedHouses.add(pos);
            }
        }
    }

    // === Validations and Final Actions ===

    /**
     * Checks if the given position is too far from the city center.
     *
     * @param position the position to evaluate
     * @return true if the position is further than the allowed radius
     */
    public boolean cityFarFromTheCentre(Position position) {
        final int LIMIT_HOUSE_BLOCK = 20;
        return Utils.getEuclideanDistance(positionCity, position) > LIMIT_HOUSE_BLOCK;
    }

    /**
     * Creates a city with the current name, position and house blocks,
     * and adds it to the map.
     *
     * @return true if the city was successfully added to the map
     */
    public boolean addCityToMap() {
        if (cityName == null || cityName.isEmpty()) {
            Utils.printMessage("City name is empty");
        }
        if (positionCity == null) {
            Utils.printMessage("Position city is empty");
        }
        if (houseBlockList == null) {
            Utils.printMessage("House block list is empty");
        }

        city = new City(cityName, positionCity, houseBlockList, Utils.getPositionsHouseBlock(houseBlockList));
        return actualMap.addElement(city);
    }
}