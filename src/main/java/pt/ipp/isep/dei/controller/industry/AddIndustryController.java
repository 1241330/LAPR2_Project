package pt.ipp.isep.dei.controller.industry;

import pt.ipp.isep.dei.domain.Industry.*;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.repository.MapRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the addition of industries to a map.
 */
public class AddIndustryController {

    /**
     * Repository for accessing map data.
     */
    private final MapRepository mapRepo;

    /**
     * The currently selected map.
     */
    private Map selectedMap;

    /**
     * The type of industry to be added.
     */
    private IndustryType type;

    /**
     * Constructs the controller with the map repository and selects the map by its ID.
     *
     * @param mapId The ID of the map to be selected.
     */
    public AddIndustryController(int mapId) {
        this.mapRepo = new MapRepository();
        this.selectedMap = mapRepo.getMapById(mapId);
    }

    /**
     * Retrieves all available industry types as a list of descriptions.
     *
     * @return A list of strings containing the descriptions of all available industry types.
     */
    public List<String> getAllIndustryTypes() {
        List<String> types = new ArrayList<>();
        List<IndustryType> industryTypes = IndustryType.getAllIndustryTypes();
        for (IndustryType type : industryTypes) {
            types.add(type.getDescription());
        }
        return types;
    }

    /**
     * Adds a new industry to the currently selected map.
     *
     * @param industryTypeDesc The description of the industry type to add.
     * @param industryName     The name to assign to the new industry.
     * @param x                The x-coordinate of the industry's position.
     * @param y                The y-coordinate of the industry's position.
     * @throws IllegalArgumentException If the industry type description or name is invalid,
     *                                  or if an industry with the same name already exists on the map.
     * @throws IllegalStateException    If no map is selected.
     */
    public void addIndustry(String industryTypeDesc, String industryName, int x, int y) {
        if (industryTypeDesc == null || industryTypeDesc.trim().isEmpty()) {
            throw new IllegalArgumentException("Industry type is required.");
        }
        if (industryName == null || industryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Industry name is required.");
        }
        if (selectedMap == null) {
            throw new IllegalStateException("No map selected.");
        }
        for (MapElement element : selectedMap.getMapElementsUsed()) {
            if (element instanceof Industry && industryName.equalsIgnoreCase(((Industry) element).getName())) {
                throw new IllegalArgumentException("An industry with this name already exists.");
            }
        }

        IndustryType type = IndustryType.getIndustryTypeByDescription(industryTypeDesc);
        if (type == null) {
            throw new IllegalArgumentException("Invalid industry type.");
        }

        Position position = new Position(x, y);
        if (type.equals(IndustryType.PRIMARY_SECTOR)) {
            PrimaryIndustry primaryIndustry = new PrimaryIndustry(industryName, type, position);
            selectedMap.addElement(primaryIndustry);
        }
        if (type.equals(IndustryType.TRANSFORMING)) {
            TransformingIndustry transformingIndustry = new TransformingIndustry(industryName, type, position);
            selectedMap.addElement(transformingIndustry);
        }
        if (type.equals(IndustryType.MIXED)) {
            MixedIndustry mixedIndustry = new MixedIndustry(industryName, type, position);
            selectedMap.addElement(mixedIndustry);
        }
    }

    /**
     * Gets the selected map.
     *
     * @return The selected map.
     */
    public Map getSelectedMap() {
        return selectedMap;
    }

    /**
     * Sets the selected map.
     *
     * @param selectedMap The map to set as selected.
     */
    public void setSelectedMap(Map selectedMap) {
        this.selectedMap = selectedMap;
    }

    /**
     * Gets the width of the selected map.
     *
     * @return The width of the map.
     */
    public int getWidthMap() {
        return selectedMap.getPixelSize().getWidth();
    }

    /**
     * Gets the height of the selected map.
     *
     * @return The height of the map.
     */
    public int getHeightMap() {
        return selectedMap.getPixelSize().getHeight();
    }

    /**
     * Gets the industry type.
     *
     * @return The industry type.
     */
    public IndustryType getType() {
        return type;
    }

    /**
     * Sets the industry type.
     *
     * @param type The industry type to set.
     */
    public void setType(IndustryType type) {
        this.type = type;
    }

    /**
     * Gets the map repository.
     *
     * @return The map repository.
     */
    public MapRepository getMapRepo() {
        return mapRepo;
    }
}