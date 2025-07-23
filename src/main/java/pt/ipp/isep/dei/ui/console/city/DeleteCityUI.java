package pt.ipp.isep.dei.ui.console.city;

import pt.ipp.isep.dei.controller.city.DeleteCityController;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for handling the deletion of cities from a map.
 */
public class DeleteCityUI implements Runnable {

    private final DeleteCityController controller;
    private City actualCity;

    /**
     * Constructs a DeleteCityUI with the specified map ID.
     *
     * @param mapId the identifier of the map
     */
    public DeleteCityUI(int mapId) {
        controller = new DeleteCityController(mapId);
    }

    /**
     * Runs the UI logic for deleting a city.
     * Checks if there are active cities and proceeds with the deletion process.
     */
    @Override
    public void run() {
        if (controller.thereAreActiveCities()) {
            chooseCity();
        } else {
            Utils.printMessage("< No active cities found >");
        }
    }

    /**
     * Allows the user to choose a city from the list of active cities.
     * If a city is selected, proceeds to confirmation and deletion.
     */
    private void chooseCity() {
        List<City> citiesList = controller.getActualCities();
        int option = Utils.chooseOptionPrintMenuAndManualReturn(
                "List of cities",
                Utils.convertObjectsToDescriptions(citiesList),
                "Return",
                "Choice"
        );
        if (option != 0) {
            actualCity = citiesList.get(option - 1);
            confirmationAndDeleteCity();
        }
    }

    /**
     * Asks the user for confirmation before deleting the selected city.
     * If confirmed, deletes the city from the map.
     */
    private void confirmationAndDeleteCity() {
        List<String> options = new ArrayList<>();
        options.add("Yes");
        int confirmation = Utils.chooseOptionPrintMenuAndManualReturn(
                "Delete " + actualCity.getName() + "?",
                options,
                "No",
                "Choice"
        );
        if (confirmation != 0) {
            controller.deleteCityToMap(actualCity);
        }
    }

    /**
     * Gets the current city selected for deletion.
     *
     * @return the actual city
     */
    public City getActualCity() {
        return actualCity;
    }

    /**
     * Sets the current city selected for deletion.
     *
     * @param actualCity the city to set
     */
    public void setActualCity(City actualCity) {
        this.actualCity = actualCity;
    }

    /**
     * Gets the controller responsible for city deletion.
     *
     * @return the DeleteCityController instance
     */
    public DeleteCityController getController() {
        return controller;
    }
}