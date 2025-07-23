package pt.ipp.isep.dei.ui.console.city;

import pt.ipp.isep.dei.controller.city.ListCityController;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for listing cities on a map.
 * Allows the user to view a list of cities and see details for a selected city.
 */
public class ListCityUI implements Runnable {

    /** The identifier of the map. */
    private final int mapId;

    /** The controller responsible for listing cities. */
    private final ListCityController controller;

    /**
     * Constructs a ListCityUI for a specific map.
     *
     * @param mapId the identifier of the map
     */
    public ListCityUI(int mapId) {
        this.mapId = mapId;
        this.controller = new ListCityController(mapId);
    }

    /**
     * Runs the UI logic for listing cities.
     * Displays a list of cities and shows details for the selected city.
     */
    @Override
    public void run() {
        List<City> cities = controller.getActualCities();

        if (cities == null || cities.isEmpty()) {
            Utils.printMessage("< No cities found >");
            return;
        }

        List<String> cityNames = new ArrayList<>();
        for (City city : cities) {
            cityNames.add(city.getName());
        }

        int option = Utils.chooseOptionPrintMenuAndManualReturn(
                "List of Cities",
                cityNames,
                "Return",
                "Choice"
        );
        if (option == 0) {
            return;
        }

        City selectedCity = cities.get(option - 1);
        showCityDetails(selectedCity);
    }

    /**
     * Displays the details of a selected city.
     *
     * @param city the city whose details will be shown
     */
    private void showCityDetails(City city) {
        List<String> details = new ArrayList<>();
        details.add("Name: " + city.getName());
        details.add("Position: " + city.getPosition());
        details.add("Number of Houses: " + city.getHouseBlocks().size());

        Utils.printMenu("City Details", details);
    }

    /**
     * Gets the map identifier.
     *
     * @return the map id
     */
    public int getMapId() {
        return mapId;
    }

    /**
     * Gets the controller responsible for listing cities.
     *
     * @return the ListCityController instance
     */
    public ListCityController getController() {
        return controller;
    }
}