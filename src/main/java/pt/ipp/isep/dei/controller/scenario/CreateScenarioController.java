package pt.ipp.isep.dei.controller.scenario;

import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for creating and configuring scenarios.
 * Manages resources, locomotives, industries, and maps associated with the scenario.
 */
public class CreateScenarioController {

    private Scenario scenario;
    private MapRepository mapRepository;
    private LocomotiveRepository locomotiveRepository;
    private HouseBlockResourceRepository houseBlockResourceRepository;
    private PrimaryResourceRepository primaryResourceRepository;
    private TransformingResourceRepository transformingResourceRepository;

    private List<HouseBlockResource> houseBlockResourceList;
    private List<PrimaryResource> primaryResourceList;
    private List<TransformingResource> transformingResourceList;
    private List<ResourcesType> allResourceListIndustry = new ArrayList<>();
    private List<ResourcesType> allResourcesList = new ArrayList<>();

    private Map actualMap;
    private String name;
    private TimeDate initialDate;
    private TimeDate endDate;
    private int initialMoney;
    private List<Industry> industriesList;
    private List<FuelType> fuelTypeList = new ArrayList<>(List.of(FuelType.values()));

    private List<ResourcesType> resourceListImport = new ArrayList<>();
    private List<ResourcesType> resourceListExport = new ArrayList<>();
    private List<ResourcesType> resourceListTransforming = new ArrayList<>();
    private List<FuelType> availableFuelTypeList = new ArrayList<>();
    private List<HouseBlockResource> availableHouseBlockResourceList = new ArrayList<>();

    /**
     * Constructor. Initializes repositories and resource lists.
     */
    public CreateScenarioController() {
        initializeRepositories();
        houseBlockResourceList = new ArrayList<>(houseBlockResourceRepository.getCopyOfHouseBlockResources());
        primaryResourceList = new ArrayList<>(primaryResourceRepository.getCopyPrimaryResources());
        transformingResourceList = new ArrayList<>(transformingResourceRepository.getCopyTransformingResources());
        allResourceListIndustry.addAll(primaryResourceList);
        allResourceListIndustry.addAll(transformingResourceList);
        allResourcesList.addAll(houseBlockResourceList);
        allResourcesList.addAll(allResourceListIndustry);
        resourceListImport.addAll(allResourceListIndustry);
        resourceListExport.addAll(allResourceListIndustry);
        resourceListTransforming.addAll(transformingResourceList);
    }

    /**
     * Initializes all required repositories if not already initialized.
     */
    private void initializeRepositories() {
        if (mapRepository == null) {
            Repositories repositories = Repositories.getInstance();
            mapRepository = repositories.getMapRepository();
            locomotiveRepository = repositories.getLocomotiveRepository();
            primaryResourceRepository = repositories.getPrimaryResourceRepository();
            transformingResourceRepository = repositories.getTransformingTypeRepository();
            houseBlockResourceRepository = repositories.getHouseBlockResourceRepository();
        }
    }

    /**
     * Creates a new scenario and associates it with the current map.
     * Throws exception if dates are not set.
     * @throws IllegalArgumentException if initial or end date is null
     */
    public void createScenario() {
        if (initialDate == null) {
            throw new IllegalArgumentException("Initial date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        scenario = new Scenario(actualMap, name, initialMoney, initialDate, endDate);
        actualMap.addScenario(scenario);
    }

    /**
     * Removes the current scenario from the map.
     */
    public void removeScenario() {
        if (scenario != null) {
            actualMap.removeScenario(scenario);
            scenario = null;
        }
    }

    /**
     * Sets the available locomotives in the scenario according to the available fuel types.
     */
    public void setLocomotiveListAvailable() {
        List<Locomotive> locomotiveList = locomotiveRepository.getAllLocomotives();
        for (Locomotive locomotive : locomotiveList) {
            if (locomotive.getFuelType() != null && availableFuelTypeList.contains(locomotive.getFuelType())) {
                scenario.addLocomotive(locomotive);
            }
        }
    }

    /**
     * Clears the available locomotive list in the scenario.
     */
    public void clearLocomotiveListAvailable() {
        scenario.clearAvailableLocomotiveList();
    }

    /**
     * Sets the resource type list in the scenario.
     */
    public void setResourcesListScenario() {
        scenario.setTypeResourceList(allResourcesList);
    }

    /**
     * Sets the available house block resource list in the scenario.
     */
    public void setHouseBlockResourceListScenario() {
        scenario.setHouseBlockResourceList(availableHouseBlockResourceList);
    }

    /**
     * Sets the default house block resource list in the scenario.
     */
    public void setHouseBlockResourceListScenarioDefault() {
        scenario.setHouseBlockResourceList(houseBlockResourceList);
    }

    // === Getters and Setters ===

    /**
     * Gets the primary resource repository.
     * @return the primary resource repository
     */
    public PrimaryResourceRepository getPrimaryResourceRepository() {
        return primaryResourceRepository;
    }

    /**
     * Sets the primary resource repository.
     * @param primaryResourceRepository the primary resource repository
     */
    public void setPrimaryResourceRepository(PrimaryResourceRepository primaryResourceRepository) {
        this.primaryResourceRepository = primaryResourceRepository;
    }

    /**
     * Gets the transforming resource repository.
     * @return the transforming resource repository
     */
    public TransformingResourceRepository getTransformingTypeRepository() {
        return transformingResourceRepository;
    }

    /**
     * Sets the transforming resource repository.
     * @param transformingResourceRepository the transforming resource repository
     */
    public void setTransformingTypeRepository(TransformingResourceRepository transformingResourceRepository) {
        this.transformingResourceRepository = transformingResourceRepository;
    }

    /**
     * Gets the list of primary resources.
     * @return the list of primary resources
     */
    public List<PrimaryResource> getPrimaryResourceList() {
        return primaryResourceList;
    }

    /**
     * Sets the list of primary resources.
     * @param primaryResourceList the list of primary resources
     */
    public void setPrimaryResourceList(List<PrimaryResource> primaryResourceList) {
        this.primaryResourceList = primaryResourceList;
    }

    /**
     * Gets the list of transforming resources.
     * @return the list of transforming resources
     */
    public List<TransformingResource> getTransformingResourceList() {
        return transformingResourceList;
    }

    /**
     * Sets the list of transforming resources.
     * @param transformingResourceList the list of transforming resources
     */
    public void setTransformingResourceList(List<TransformingResource> transformingResourceList) {
        this.transformingResourceList = transformingResourceList;
    }

    /**
     * Gets the list of all resources.
     * @return the list of all resources
     */
    public List<ResourcesType> getAllResourcesList() {
        return allResourcesList;
    }

    /**
     * Sets the list of all resources.
     * @param allResourcesList the list of all resources
     */
    public void setAllResourcesList(List<ResourcesType> allResourcesList) {
        this.allResourcesList = allResourcesList;
    }

    /**
     * Gets the list of house block resources.
     * @return the list of house block resources
     */
    public List<HouseBlockResource> getHouseBlockResourceList() {
        return houseBlockResourceList;
    }

    /**
     * Sets the list of house block resources.
     * @param houseBlockResourceList the list of house block resources
     */
    public void setHouseBlockResourceList(List<HouseBlockResource> houseBlockResourceList) {
        this.houseBlockResourceList = houseBlockResourceList;
    }

    /**
     * Gets the transforming resource repository.
     * @return the transforming resource repository
     */
    public TransformingResourceRepository getTransformingResourceRepository() {
        return transformingResourceRepository;
    }

    /**
     * Sets the transforming resource repository.
     * @param transformingResourceRepository the transforming resource repository
     */
    public void setTransformingResourceRepository(TransformingResourceRepository transformingResourceRepository) {
        this.transformingResourceRepository = transformingResourceRepository;
    }

    /**
     * Gets the list of available house block resources.
     * @return the list of available house block resources
     */
    public List<HouseBlockResource> getAvailableHouseBlockResourceList() {
        return availableHouseBlockResourceList;
    }

    /**
     * Sets the list of available house block resources.
     * @param availableHouseBlockResourceList the list of available house block resources
     */
    public void setAvailableHouseBlockResourceList(List<HouseBlockResource> availableHouseBlockResourceList) {
        this.availableHouseBlockResourceList = availableHouseBlockResourceList;
    }

    /**
     * Clears the list of available house block resources.
     */
    public void clearAvailableHouseBlockResourceList() {
        availableHouseBlockResourceList.clear();
    }

    /**
     * Gets the house block resource repository.
     * @return the house block resource repository
     */
    public HouseBlockResourceRepository getHouseBlockResourceRepository() {
        return houseBlockResourceRepository;
    }

    /**
     * Sets the house block resource repository.
     * @param houseBlockResourceRepository the house block resource repository
     */
    public void setHouseBlockResourceRepository(HouseBlockResourceRepository houseBlockResourceRepository) {
        this.houseBlockResourceRepository = houseBlockResourceRepository;
    }

    /**
     * Gets the list of all resources for industry.
     * @return the list of all resources for industry
     */
    public List<ResourcesType> getAllResourceListIndustry() {
        return allResourceListIndustry;
    }

    /**
     * Sets the list of all resources for industry.
     * @param allResourceListIndustry the list of all resources for industry
     */
    public void setAllResourceListIndustry(List<ResourcesType> allResourceListIndustry) {
        this.allResourceListIndustry = allResourceListIndustry;
    }

    /**
     * Updates the industries list from the current map.
     */
    private void setIndustriesList() {
        industriesList = actualMap.getIndustriesList();
    }

    /**
     * Gets the locomotive repository.
     * @return the locomotive repository
     */
    public LocomotiveRepository getLocomotiveRepository() {
        return locomotiveRepository;
    }

    /**
     * Gets the map repository.
     * @return the map repository
     */
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    /**
     * Sets the map repository.
     * @param mapRepository the map repository
     */
    public void setMapRepository(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    /**
     * Checks if there are active maps.
     * @return true if there are active maps, false otherwise
     */
    public boolean thereAreActiveMaps() {
        return (mapRepository.nActiveMaps() != 0);
    }

    /**
     * Returns the list of all maps.
     * @return list of maps
     */
    public List<Map> listMaps() {
        return mapRepository.getAllMaps();
    }

    /**
     * Returns the ID of the current map.
     * @return map id or 0 if not set
     */
    public int getIdMap() {
        if (actualMap != null) {
            return actualMap.getId();
        }
        return 0;
    }

    /**
     * Gets the list of industries.
     * @return the list of industries
     */
    public List<Industry> getIndustriesList() {
        return industriesList;
    }

    /**
     * Gets the current map.
     * @return the current map
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the current map and updates the industries list.
     * @param actualMap the current map
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
        setIndustriesList();
    }

    /**
     * Gets the scenario name.
     * @return the scenario name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the scenario name.
     * @param name the scenario name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the initial date.
     * @return the initial date
     */
    public TimeDate getInitialDate() {
        return initialDate;
    }

    /**
     * Sets the initial date.
     * @param initialDate the initial date
     */
    public void setInitialDate(TimeDate initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * Gets the end date.
     * @return the end date
     */
    public TimeDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     * @param endDate the end date
     */
    public void setEndDate(TimeDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the initial money.
     * @return the initial money
     */
    public int getInitialMoney() {
        return initialMoney;
    }

    /**
     * Sets the initial money.
     * @param initialMoney the initial money
     */
    public void setInitialMoney(int initialMoney) {
        this.initialMoney = initialMoney;
    }

    /**
     * Gets the scenario.
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets the scenario.
     * @param scenario the scenario
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Sets the locomotive repository.
     * @param locomotiveRepository the locomotive repository
     */
    public void setLocomotiveRepository(LocomotiveRepository locomotiveRepository) {
        this.locomotiveRepository = locomotiveRepository;
    }

    /**
     * Sets the list of industries.
     * @param industriesList the list of industries
     */
    public void setIndustriesList(List<Industry> industriesList) {
        this.industriesList = industriesList;
    }

    /**
     * Sets the list of fuel types.
     * @param fuelTypeList the list of fuel types
     */
    public void setFuelTypeList(List<FuelType> fuelTypeList) {
        this.fuelTypeList = fuelTypeList;
    }

    /**
     * Gets the list of import resources.
     * @return the list of import resources
     */
    public List<ResourcesType> getResourceListImport() {
        return resourceListImport;
    }

    /**
     * Sets the list of import resources.
     * @param resourceListImport the list of import resources
     */
    public void setResourceListImport(List<ResourcesType> resourceListImport) {
        this.resourceListImport = resourceListImport;
    }

    /**
     * Gets the list of export resources.
     * @return the list of export resources
     */
    public List<ResourcesType> getResourceListExport() {
        return resourceListExport;
    }

    /**
     * Sets the list of export resources.
     * @param resourceListExport the list of export resources
     */
    public void setResourceListExport(List<ResourcesType> resourceListExport) {
        this.resourceListExport = resourceListExport;
    }

    /**
     * Gets the list of transforming resources.
     * @return the list of transforming resources
     */
    public List<ResourcesType> getResourceListTransforming() {
        return resourceListTransforming;
    }

    /**
     * Sets the list of transforming resources.
     * @param resourceListTransforming the list of transforming resources
     */
    public void setResourceListTransforming(List<ResourcesType> resourceListTransforming) {
        this.resourceListTransforming = resourceListTransforming;
    }

    /**
     * Gets the list of fuel types.
     * @return the list of fuel types
     */
    public List<FuelType> getFuelTypeList() {
        return fuelTypeList;
    }

    /**
     * Gets the list of available fuel types.
     * @return the list of available fuel types
     */
    public List<FuelType> getAvailableFuelTypeList() {
        return availableFuelTypeList;
    }

    /**
     * Sets the list of available fuel types.
     * @param availableFuelTypeList the list of available fuel types
     */
    public void setAvailableFuelTypeList(List<FuelType> availableFuelTypeList) {
        this.availableFuelTypeList = availableFuelTypeList;
    }

    // === Industry and Resource Configuration Methods ===

    /**
     * Associates a primary resource to a primary industry and adds it to the scenario.
     * @param industry primary industry
     * @param iResourceGenerated index of the generated resource
     */
    public void setResourceInPrimaryIndustry(Industry industry, int iResourceGenerated) {
        ((PrimaryIndustry) industry).clonePrimaryIndustry(primaryResourceList.get(iResourceGenerated), scenario);
        scenario.addPrimaryIndustryScenarioByIndustryMap((PrimaryIndustry) industry);
    }

    /**
     * Associates a transforming resource to a transforming industry and adds it to the scenario.
     * @param industry transforming industry
     * @param iResourceTransforming index of the transforming resource
     */
    public void setResourceInTransformingIndustry(Industry industry, int iResourceTransforming) {
        ((TransformingIndustry) industry).cloneTransformingIndustry(transformingResourceList.get(iResourceTransforming), scenario);
        scenario.addTransformingIndustryScenarioByIndustryMap((TransformingIndustry) industry);
    }

    /**
     * Initializes a mixed industry and adds it to the scenario.
     * @param industry mixed industry
     */
    public void initializeMixedIndustry(Industry industry) {
        ((MixedIndustry) industry).cloneMixedIndustry(scenario);
        scenario.addMixedIndustryScenarioByIndustryMap((MixedIndustry) industry);
    }

    /**
     * Adds an import resource to a mixed industry and removes it from the export list if needed.
     * @param industry mixed industry
     * @param iResource index of the resource
     */
    public void addImportMixedIndustry(Industry industry, int iResource) {
        ((MixedIndustry) industry).importResource(resourceListImport.get(iResource), scenario);
        if (resourceListExport.contains(resourceListImport.get(iResource))) {
            resourceListExport.remove(resourceListImport.get(iResource));
        }
        resourceListImport.remove(iResource);
    }

    /**
     * Adds an export resource to a mixed industry and removes it from the import and transforming lists if needed.
     * @param industry mixed industry
     * @param iResource index of the resource
     */
    public void addExportMixedIndustry(Industry industry, int iResource) {
        ((MixedIndustry) industry).exportResource(resourceListExport.get(iResource), scenario);
        if (resourceListImport.contains(resourceListExport.get(iResource))) {
            resourceListImport.remove(resourceListExport.get(iResource));
        }
        List<ResourcesType> removedResources = new ArrayList<>();
        for (ResourcesType resource : resourceListTransforming) {
            if (((TransformingResource) resource).getNeededResources().contains(resourceListExport.get(iResource))) {
                removedResources.add(resource);
            }
        }
        resourceListTransforming.removeAll(removedResources);
        resourceListExport.remove(iResource);
    }

    /**
     * Adds a transforming resource to a mixed industry and removes needed export resources and the transforming resource from the list.
     * @param industry mixed industry
     * @param iResource index of the resource
     */
    public void addTransformingMixedIndustry(Industry industry, int iResource) {
        ((MixedIndustry) industry).transformedResource((TransformingResource) (resourceListTransforming.get(iResource)), scenario);
        List<ResourcesType> neededResources = ((TransformingResource) resourceListTransforming.get(iResource)).getNeededResources();
        for (ResourcesType resource : neededResources) {
            if (resourceListExport.contains(resource)) {
                resourceListExport.remove(resource);
            }
        }
        resourceListTransforming.remove(iResource);
    }

    /**
     * Checks if the import resource list is empty.
     * @return true if empty, false otherwise
     */
    public boolean resourceListImportIsEmpty() {
        return resourceListImport.isEmpty();
    }

    /**
     * Checks if the export resource list is empty.
     * @return true if empty, false otherwise
     */
    public boolean resourceListExportIsEmpty() {
        return resourceListExport.isEmpty();
    }

    /**
     * Checks if the transforming resource list is empty.
     * @return true if empty, false otherwise
     */
    public boolean resourceListTransformingIsEmpty() {
        return resourceListTransforming.isEmpty();
    }

    /**
     * Checks if a scenario with the given name already exists in the current map.
     * @param name scenario name
     * @return true if exists, false otherwise
     */
    public boolean alreadyExistsNameScenarioInMap(String name) {
        if (actualMap == null) {
            return false;
        }
        for (Scenario existingScenario : actualMap.getScenarios()) {
            if (existingScenario.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}