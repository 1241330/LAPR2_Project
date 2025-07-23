package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Industry entities.
 * Provides methods to add, remove, retrieve, and check the existence of industries.
 */
public class IndustryRepository implements Serializable {

    /**
     * List that stores all industries.
     */
    private List<Industry> industries = new ArrayList<>();

    /**
     * Constructs an empty IndustryRepository.
     */
    public IndustryRepository() {
    }

    /**
     * Adds an industry to the repository if it does not already exist by name.
     *
     * @param industry The industry to add.
     * @return True if the industry was added successfully, false otherwise.
     * @throws IllegalArgumentException if the industry is null.
     */
    public boolean addIndustry(Industry industry) {
        if (industry == null) {
            throw new IllegalArgumentException("Industry cannot be null");
        }
        if (industryNameExists(industry.getName())) {
            return false;
        }
        industries.add(industry);
        return true;
    }

    /**
     * Removes an industry from the repository if it exists.
     *
     * @param industry The industry to remove.
     * @return True if the industry was removed successfully, false otherwise.
     * @throws IllegalArgumentException if the industry is null.
     */
    public boolean removeIndustry(Industry industry) {
        if (industry == null) {
            throw new IllegalArgumentException("Industry cannot be null");
        }
        if (!industryExists(industry)) {
            Utils.printMessage("< Industry doesn't exist >");
            return false;
        }
        industries.remove(industry);
        return true;
    }

    /**
     * Retrieves an industry by its unique identifier.
     *
     * @param id The ID of the industry.
     * @return The industry with the specified ID, or null if not found.
     */
    public Industry getIndustryById(int id) {
        for (Industry industry : industries) {
            if (industry.getId() == id) {
                return industry;
            }
        }
        return null;
    }

    /**
     * Retrieves the total number of industries in the repository.
     *
     * @return The number of industries.
     */
    public int getIndustryCount() {
        return industries.size();
    }

    /**
     * Retrieves a list of all industries in the repository.
     *
     * @return A list of industries.
     */
    public List<Industry> getAllIndustries() {
        return industries;
    }

    /**
     * Checks if a specific industry exists in the repository.
     *
     * @param actualIndustry The industry to check.
     * @return True if the industry exists, false otherwise.
     */
    public boolean industryExists(Industry actualIndustry) {
        for (Industry industry : industries) {
            if (industry == actualIndustry) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an industry with a specific name already exists in the repository.
     *
     * @param name The name of the industry to check.
     * @return True if an industry with the name exists, false otherwise.
     */
    public boolean industryNameExists(String name) {
        for (Industry industry : industries) {
            if (industry.getName().equalsIgnoreCase(name)) {
                Utils.printMessage("< Industry name already exist >");
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of industries.
     *
     * @return The list of industries.
     */
    public List<Industry> getIndustries() {
        return industries;
    }

    /**
     * Sets the list of industries.
     *
     * @param industries The list of industries to set.
     */
    public void setIndustries(List<Industry> industries) {
        this.industries = industries;
    }
}