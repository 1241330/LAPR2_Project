package pt.ipp.isep.dei.controller.industry;

import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.domain.Map.MapElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the deletion of {@code Industry} elements from a {@code Map}.
 */
public class DeleteIndustryController {

    /**
     * Repository for accessing map data.
     */
    private final MapRepository mapRepo;

    /**
     * The currently selected map.
     */
    private final Map selectedMap;

    /**
     * The currently selected industry.
     */
    private Industry actualIndustry;

    /**
     * Constructs a {@code DeleteIndustryController} for a specific map.
     *
     * @param mapId the ID of the map to be used
     * @throws IllegalArgumentException if no map is found with the specified ID
     */
    public DeleteIndustryController(int mapId) {
        this.mapRepo = new MapRepository();
        this.selectedMap = mapRepo.getMapById(mapId);
        if (selectedMap == null) {
            throw new IllegalArgumentException("Map with ID " + mapId + " not found");
        }
    }

    /**
     * Checks whether the selected map contains any active industries.
     *
     * @return {@code true} if at least one industry is present; {@code false} otherwise
     */
    public boolean thereAreActiveIndustries() {
        return !getActualIndustries().isEmpty();
    }

    /**
     * Returns the list of {@code Industry} elements currently present in the selected map.
     *
     * @return a list of industries; empty if none are found
     */
    public List<Industry> getActualIndustries() {
        List<Industry> industries = new ArrayList<>();
        if (selectedMap != null) {
            for (MapElement element : selectedMap.getMapElementsUsed()) {
                if (element instanceof Industry) {
                    industries.add((Industry) element);
                }
            }
        }
        return industries;
    }

    /**
     * Deletes the specified {@code Industry} from the selected map.
     *
     * @param industry the industry to remove
     * @throws IllegalArgumentException if the industry is {@code null}
     * @throws IllegalStateException if the industry could not be removed from the map
     */
    public void deleteIndustryFromMap(Industry industry) {
        if (industry == null) {
            throw new IllegalArgumentException("Industry cannot be null");
        }
        if (!selectedMap.removeElement(industry)) {
            throw new IllegalStateException("Failed to delete industry");
        }
    }

    /**
     * Returns the currently selected {@code Industry}.
     *
     * @return the selected industry, or {@code null} if none is set
     */
    public Industry getActualIndustry() {
        return actualIndustry;
    }

    /**
     * Sets the currently selected {@code Industry}.
     *
     * @param industry the industry to set
     */
    public void setActualIndustry(Industry industry) {
        this.actualIndustry = industry;
    }

    /**
     * Returns the currently selected map.
     *
     * @return the selected map
     */
    public Map getSelectedMap() {
        return selectedMap;
    }

    /**
     * Returns the map repository.
     *
     * @return the map repository
     */
    public MapRepository getMapRepo() {
        return mapRepo;
    }
}