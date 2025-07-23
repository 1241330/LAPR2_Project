package pt.ipp.isep.dei.controller.simulation.InitializeSimulation;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the selection of maps in the simulation initialization process.
 * It retrieves available maps from the repository and allows the user to select one.
 */
public class ChooseMapController {

    private MapRepository mapRepository;
    private List<Map> mapList;
    private List<String> listOfNamesMaps = new ArrayList<>();
    private Map selectedMap;

    /**
     * Constructs a ChooseMapController, initializing the map repository and loading all available maps.
     */
    public ChooseMapController() {
        initializeMapRepository();
        this.mapList = mapRepository.getAllMaps();
        setListOfNamesMaps();
    }

    /**
     * Initializes the map repository if it has not been initialized yet.
     */
    private void initializeMapRepository() {
        if (mapRepository == null) {
            Repositories repositories = Repositories.getInstance();
            mapRepository = repositories.getMapRepository();
        }
    }

    /**
     * Populates the list of map names from the current list of maps.
     */
    public void setListOfNamesMaps() {
        listOfNamesMaps.clear();
        for (Map map : mapList) {
            listOfNamesMaps.add(map.getName());
        }
    }

    /**
     * Returns the list of names of all available maps.
     *
     * @return a list of map names
     */
    public List<String> getListOfNamesMaps() {
        return listOfNamesMaps;
    }

    /**
     * Sets the selected map based on the given index in the map list.
     *
     * @param index the index of the map to select
     */
    public void setSelectedMap(int index) {
        selectedMap = mapList.get(index);
    }

    /**
     * Returns the currently selected map.
     *
     * @return the selected {@link Map}
     */
    public Map getSelectedMap() {
        return selectedMap;
    }

    /**
     * Checks if the list of available maps is empty.
     *
     * @return true if the map list is empty, false otherwise
     */
    public boolean listOfMapsIsEmpty() {
        return mapList.isEmpty();
    }
}