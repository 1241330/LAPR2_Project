package pt.ipp.isep.dei.controller.city.houseBlock;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for displaying details of a HouseBlock on the map.
 * Provides methods to retrieve information such as city name, position, assigned station,
 * inventory, and productions related to a HouseBlock in a given Scenario.
 */
public class ShowHouseBlockDetailsInMapController {
    /**
     * The HouseBlock whose details are to be displayed.
     */
    private HouseBlock houseBlock;

    /**
     * The Scenario context in which the HouseBlock exists.
     */
    private Scenario scenario;

    /**
     * Sets the HouseBlock for which details will be shown.
     *
     * @param houseBlock the HouseBlock to set
     */
    public void setHouseBlock(HouseBlock houseBlock) {
        this.houseBlock = houseBlock;
    }

    /**
     * Gets the name of the city where the HouseBlock is located.
     *
     * @return the city name
     */
    public String getCityName() {
        return houseBlock.getCityName();
    }

    /**
     * Sets the Scenario context for the controller.
     *
     * @param scenario the Scenario to set
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Gets the position of the HouseBlock, incremented by 1 in both X and Y axes.
     *
     * @return the position as a String
     */
    public String getPosition() {
        Position position = new Position(houseBlock.getPosition().getX() + 1, houseBlock.getPosition().getY() + 1);
        return position.toString();
    }

    /**
     * Gets the name of the station assigned to the HouseBlock, or "None" if not assigned.
     *
     * @return the assigned station name or "None"
     */
    public String getAssignedStationName() {
        return houseBlock.getAssignedStation() != null ? houseBlock.getAssignedStation().getName() : "None";
    }

    /**
     * Gets a list of inventory resources in the HouseBlock as strings.
     *
     * @return a list of inventory resource descriptions
     */
    public List<String> getHouseBlockInventory() {
        List<String> inventoryStrings = new ArrayList<>();
        for (Resource r : houseBlock.getInventory().getAllResources()) {
            inventoryStrings.add(r.toString());
        }
        return inventoryStrings;
    }

    /**
     * Gets a list of productions for the HouseBlock in the current Scenario as strings.
     *
     * @return a list of production descriptions
     */
    public List<String> getHouseBlockProductions() {
        List<String> productions = new ArrayList<>();
        for (HouseBlockResource resource : scenario.getHouseBlockResourceList()) {
            productions.add(resource.toString());
        }
        return productions;
    }
}
