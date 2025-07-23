package pt.ipp.isep.dei.domain.Station;

import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Event.RouteEvent;
import pt.ipp.isep.dei.domain.FinancialResult.Demand;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.repository.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.ui.console.utils.Utils.getEuclideanDistance;

/**
 * Represents a station on the map, which can be associated with industries and house blocks.
 */
public class Station extends MapElement implements Serializable {

    /** The name of the station. */
    private String name;

    /** The unique identifier of the station. */
    private int identifier;

    /** The type of the station. */
    private StationType type;

    /** The position of the station on the map. */
    private Position position;

    /** Repository for station data. */
    private StationRepository stationRepository;

    /** Repository for map data. */
    private MapRepository mapRepository;

    /** Repository for house block resources. */
    private HouseBlockResourceRepository houseBlockResourceRepository;

    /** Repository for primary resources. */
    private PrimaryResourceRepository primaryResourceRepository;

    /** Repository for transforming resources. */
    private TransformingResourceRepository transformingResourceRepository;

    /** Singleton instance for repositories. */
    private transient Repositories repositories;

    /** The map where the station is located. */
    private Map actualMap;

    /** The direction of the station (used for STATION type). */
    private String direction;

    /** List of buildings associated with the station. */
    private List<Building> buildings;

    /** List of associations (industries and house blocks) for the station. */
    private List<StationAssociations> associations;

    /** Counter for created stations (used for unique identifiers). */
    private static int counterCreatedStations = 0;

    /** List of resource types requested by the station. */
    private List<ResourcesType> resourcesTypeRequested = new ArrayList<>();

    /** List of demands for the station. */
    private List<Demand> demandList = new ArrayList<>();

    /**
     * Constructs a Station with the specified type, position, map id, direction, and scenario.
     *
     * @param type      the type of the station
     * @param position  the position of the station
     * @param idMap     the map id
     * @param direction the direction (required for STATION type)
     * @param scenario  the scenario context
     */
    public Station(StationType type, Position position, int idMap, String direction, Scenario scenario) {
        super(position, calculateOccupiedPositions(position));
        initializeRepositories();
        this.type = type;
        this.position = position;
        this.actualMap = mapRepository.getMapById(idMap);
        this.name = generateStationName(type, position, actualMap);

        if (this.type == StationType.STATION) {
            if (direction == null) {
                throw new IllegalArgumentException("Direction must be provided for STATION type.");
            }
            this.direction = direction;
        } else {
            this.direction = null;
        }

        this.identifier = counterCreatedStations;
        counterCreatedStations++;
        this.buildings = new ArrayList<>();
        assignGenerationPosts(scenario);
        createDemandList();
    }

    /**
     * Constructs a Station with a given name.
     *
     * @param name the name of the station
     */
    public Station(String name) {
        super(null);
        this.name = name;
        this.buildings = new ArrayList<>();
    }

    /**
     * Sets the map where the station is located.
     *
     * @param actualMap the map to set
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
    }

    /**
     * Assigns industries and house blocks within the influential radius to this station.
     *
     * @param scenario the scenario context
     */
    public void assignGenerationPosts(Scenario scenario) {
        setActualMap(scenario.getMap());
        associations = new ArrayList<>();
        List<Industry> industries = scenario.getIndustriesList();
        List<HouseBlock> houseBlocks = actualMap.getHouseBlockList();

        Position startPosition = findStartPosition();
        int positionX = startPosition.getX();
        int positionY = startPosition.getY();
        Position verificationPosition;
        for (int loop = 0; loop < type.getInfluentialRadius(); loop++) {

            if (positionX < 0 || positionY < 0) {
                continue;
            }
            verificationPosition = new Position(positionX, positionY);
            for (int loop2 = 0; loop2 < type.getInfluentialRadius(); loop2++) {
                int currentX = startPosition.getX() + loop;
                int currentY = startPosition.getY() + loop2;
                verificationPosition.setX(currentX);
                verificationPosition.setY(currentY);

                for (Industry industry : industries) {
                    if (industry.getPosition().getX() == currentX &&
                            industry.getPosition().getY() == currentY) {
                        associations.add(industry);
                        industry.setAssignedStation(this);
                    }
                }

                for (HouseBlock houseBlock : houseBlocks) {
                    if (houseBlock.getPosition().getX() == currentX &&
                            houseBlock.getPosition().getY() == currentY) {
                        associations.add(houseBlock);
                        houseBlock.setAssignedStation(this);
                    }
                }
            }
        }
    }

    /**
     * Sets the list of resource types requested by the station based on its associations.
     *
     * @param scenario the scenario context
     */
    public void setResourcesTypeRequested(Scenario scenario) {
        assignGenerationPosts(scenario);
        resourcesTypeRequested.clear();
        for (StationAssociations association : associations) {
            if (association instanceof TransformingIndustry) {
                TransformingIndustry industry = ((TransformingIndustry) association).getClonedTransformingIndustry(scenario);
                List<ResourcesType> primaryResources = industry.getPrimaryResources();
                for (ResourcesType primaryResource : primaryResources) {
                    if (!resourcesTypeRequested.contains(primaryResource)) {
                        resourcesTypeRequested.add(primaryResource);
                    }
                }
            }
            if (association instanceof MixedIndustry) {
                MixedIndustry industry = ((MixedIndustry) association).getClonedMixedIndustry(scenario);
                List<ResourcesType> transformedResources = industry.getTransformedResources();
                for (ResourcesType transformedResource : transformedResources) {
                    TransformingResource transformingResource = (TransformingResource) transformedResource;
                    List<ResourcesType> primaryResources = transformingResource.getNeededResources();
                    for (ResourcesType primaryResource : primaryResources) {
                        if (!resourcesTypeRequested.contains(primaryResource)) {
                            resourcesTypeRequested.add(primaryResource);
                        }
                    }
                }
                List<ResourcesType> exportedResources = industry.getExportedResources();
                for (ResourcesType exportedResource : exportedResources) {
                    if (!resourcesTypeRequested.contains(exportedResource)) {
                        resourcesTypeRequested.add(exportedResource);
                    }
                }
            }
            if (association instanceof HouseBlock houseBlock) {
                List<ResourcesType> resources = houseBlock.getConsumableResources();
                for (ResourcesType resource : resources) {
                    if (!resourcesTypeRequested.contains(resource)) {
                        resourcesTypeRequested.add(resource);
                    }
                }
            }
        }
    }

    /**
     * Finds the starting position for the influential area of the station.
     *
     * @return the starting position
     */
    private Position findStartPosition() {
        int positionX = position.getX();
        int positionY = position.getY();
        Position initialPosition = new Position(positionX, positionY);

        if (type != StationType.STATION) {
            int influentialRadius = (type.getInfluentialRadius() - 1) / 2;
            positionX = initialPosition.getX() - influentialRadius;
            positionY = initialPosition.getY() - influentialRadius;
        } else {
            int influentialRadius = (type.getInfluentialRadius()) / 2;
            if(direction != null) {
                switch (direction) {
                    case "North":
                        positionX = initialPosition.getX() - (influentialRadius - 1);
                        positionY = initialPosition.getY() - (influentialRadius - 1);
                        break;
                    case "South":
                        positionX = initialPosition.getX() - (influentialRadius);
                        positionY = initialPosition.getY() - (influentialRadius);
                        break;
                    case "West":
                        positionX = initialPosition.getX() - (influentialRadius - 1);
                        positionY = initialPosition.getY() - (influentialRadius);
                        break;
                    case "East":
                        positionX = initialPosition.getX() - (influentialRadius);
                        positionY = initialPosition.getY() - (influentialRadius - 1);
                        break;
                    default:
                        positionX = initialPosition.getX();
                        positionY = initialPosition.getY();
                }
            }
        }
        initialPosition.setX(positionX);
        initialPosition.setY(positionY);
        return initialPosition;
    }

    /**
     * Initializes the repositories if not already initialized.
     */
    private void initializeRepositories() {
        if (stationRepository == null) {
            repositories = Repositories.getInstance();
            mapRepository = repositories.getMapRepository();
            houseBlockResourceRepository = repositories.getHouseBlockResourceRepository();
            primaryResourceRepository = repositories.getPrimaryResourceRepository();
            transformingResourceRepository = repositories.getTransformingTypeRepository();
        }
        stationRepository = new StationRepository();
    }

    /**
     * Calculates the occupied positions for the station.
     *
     * @param position the position of the station
     * @return a list with the occupied positions
     */
    private static List<Position> calculateOccupiedPositions(Position position) {
        List<Position> positions = new ArrayList<>();
        positions.add(position);
        return positions;
    }

    /**
     * Generates a unique name for the station based on its type and closest city.
     *
     * @param type     the type of the station
     * @param position the position of the station
     * @param actualMap the map context
     * @return the generated name
     */
    private String generateStationName(StationType type, Position position, Map actualMap) {
        String cityName = getClosestCityName(position, actualMap);
        if (cityName == null) {
            cityName = "UnknownCity";
        }
        String baseName = type.toString().toLowerCase() + "_" + cityName;
        int count = 0;

        for (Station station : this.actualMap.getStationList()) {
            if (station.getStationType().equalsIgnoreCase(type.toString()) &&
                station.getName().startsWith(baseName)) {
                count++;
            }
        }

        if (count == 0) {
            return baseName;
        } else {
            return baseName + "_" + (count);
        }
    }

    /**
     * Gets the name of the closest city to the given position.
     *
     * @param position  the position to check
     * @param actualMap the map context
     * @return the name of the closest city, or null if none found
     */
    private String getClosestCityName(Position position, Map actualMap) {
        List<City> cities = actualMap.getCitiesList();

        City closestCity = null;
        double minDistance = Double.MAX_VALUE;

        for (City city : cities) {
            double distance = getEuclideanDistance(position, city.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                closestCity = city;
            }
        }

        return closestCity != null ? closestCity.getName() : null;
    }

    /**
     * Gets the name of the station.
     *
     * @return the station name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the station.
     *
     * @param newName the new name
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Gets the position of the station.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the type of the station as a string.
     *
     * @return the station type
     */
    public String getStationType() {
        return type.toString();
    }

    /**
     * Sets the station type by string.
     *
     * @param stationType the station type as string
     */
    public void setStationType(String stationType) {
        if (stationType.equalsIgnoreCase(StationType.STATION.name())) {
            this.type = StationType.STATION;
        } else if (stationType.equalsIgnoreCase(StationType.TERMINAL.name())) {
            this.type = StationType.TERMINAL;
        } else {
            this.type = StationType.DEPOT;
        }
    }

    /**
     * Gets the type of the map element.
     *
     * @return "Station"
     */
    @Override
    public String getType() {
        return "Station";
    }

    /**
     * Gets the direction of the station.
     *
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the station.
     *
     * @param direction the direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Gets all associations (industries and house blocks) of the station.
     *
     * @return the list of associations
     */
    public List<StationAssociations> getAllAssociations() {
        return associations;
    }

    /**
     * Gets the construction cost of the station.
     *
     * @return the construction cost
     */
    public int getConstructionCost() {
        return type.getConstructionCost();
    }

    /**
     * Gets the identifier of the station.
     *
     * @return the identifier
     */
    public int getIdentifier() {
        return this.identifier;
    }

    /**
     * Sets the type of the station.
     *
     * @param newType the new station type
     */
    public void setType(StationType newType) {
        this.type = newType;
    }

    /**
     * Returns a string representation of the station.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        Position adjustedPosition = new Position(position.getX() + 1, position.getY() + 1);
        return name + adjustedPosition;
    }

    /**
     * Adds a building to the station.
     *
     * @param building the building to add
     */
    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    /**
     * Removes a building from the station.
     *
     * @param building the building to remove
     */
    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    /**
     * Gets the list of buildings in the station.
     *
     * @return the list of buildings
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * Gets the list of resource types requested by the station.
     *
     * @return the list of resource types
     */
    public List<ResourcesType> getResourcesTypeRequested() {
        return resourcesTypeRequested;
    }

    /**
     * Gets the list of demands for the station.
     *
     * @return the list of demands
     */
    public List<Demand> getDemandList() {
        return demandList;
    }

    /**
     * Sets the list of demands for the station.
     *
     * @param demandList the list of demands to set
     */
    public void setDemandList(List<Demand> demandList) {
        this.demandList = demandList;
    }

    /**
     * Creates the demand list for the station based on available resources.
     */
    private void createDemandList() {
        List<ResourcesType> allResourceTypes = new ArrayList<>();

        for (ResourcesType resourcesType : houseBlockResourceRepository.getAllHouseBlockResources()){
            if(!verifyIfAlreadyHasResource(resourcesType, allResourceTypes)){
                allResourceTypes.add(resourcesType);
            }
        }

        for (ResourcesType resourcesType : primaryResourceRepository.getAllPrimaryResources()){
            if(!verifyIfAlreadyHasResource(resourcesType, allResourceTypes)){
                allResourceTypes.add(resourcesType);
            }
        }

        for (ResourcesType resourcesType : transformingResourceRepository.getAllTransformingResources()){
            if(!verifyIfAlreadyHasResource(resourcesType, allResourceTypes)){
                allResourceTypes.add(resourcesType);
            }
        }

        for (ResourcesType resourcesType: allResourceTypes){
            demandList.add(new Demand(resourcesType));
        }
    }

    /**
     * Verifies if a resource type is already present in the list.
     *
     * @param resourcesType the resource type to check
     * @param allResourceTypes the list of resource types
     * @return true if already present, false otherwise
     */
    private boolean verifyIfAlreadyHasResource(ResourcesType resourcesType, List<ResourcesType> allResourceTypes) {
        for (ResourcesType resourcesTypeToCompare : allResourceTypes){
            if (resourcesTypeToCompare.getName().equals(resourcesType.getName())){
                return true;
            }
        }
        return false;
    }
}