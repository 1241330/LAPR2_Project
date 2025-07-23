package pt.ipp.isep.dei.controller.map;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.*;

/**
 * Controller responsible for saving and discarding changes to a map.
 * Handles serialization and file operations for map persistence.
 */
public class SaveMapController {
    private Map actualMap;
    private MapRepository mapRepository;
    private static final String NAME_DIRECTORY = "data/Map/";
    private static final String EXTENSION = ".ser";

    /**
     * Constructs a SaveMapController with the specified map.
     *
     * @param actualMap the map to be managed by this controller
     */
    public SaveMapController(Map actualMap) {
        this.mapRepository = Repositories.getInstance().getMapRepository();
        this.actualMap = actualMap;
    }

    /**
     * Saves the current map to a file. If the map name has changed,
     * renames the existing file before saving.
     */
    public void saveMap() {
        try {
            String oldNameMap = actualMap.getLastNameUsed();
            String newNameMap = actualMap.getName();
            if (!oldNameMap.equals(newNameMap)) {
                File oldFile = new File(NAME_DIRECTORY + oldNameMap + EXTENSION);
                File newFile = new File(NAME_DIRECTORY + newNameMap + EXTENSION);
                oldFile.renameTo(newFile);
            }
            FileOutputStream fileOut = new FileOutputStream(NAME_DIRECTORY + newNameMap + EXTENSION);
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(actualMap);
            outStream.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        actualMap.setLastNameUsed(actualMap.getName());
    }

    /**
     * Discards changes to the current map. Restores the map to its previous state
     * if a backup exists, otherwise removes it from the repository.
     */
    public void dontSaveMap() {
        actualMap.setName(actualMap.getLastNameUsed());
        mapRepository.removeMap(actualMap.getId());
        if (alreadyExistMap()) {
            Map backupMap = null;
            try {
                FileInputStream fileIn = new FileInputStream(NAME_DIRECTORY + actualMap.getName() + EXTENSION);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                backupMap = (Map) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
            if (backupMap != null) {
                mapRepository.addMap(backupMap);
            }
        }
    }

    /**
     * Checks if a map file with the current map's name exists in the directory.
     *
     * @return true if the file exists, false otherwise
     */
    private boolean alreadyExistMap() {
        return Utils.fileExistsInFolder(NAME_DIRECTORY, actualMap.getName() + EXTENSION);
    }

    /**
     * Gets the current map.
     *
     * @return the current map
     */
    public Map getActualMap() {
        return actualMap;
    }

    /**
     * Sets the current map.
     *
     * @param actualMap the map to set
     */
    public void setActualMap(Map actualMap) {
        this.actualMap = actualMap;
    }

    /**
     * Gets the map repository.
     *
     * @return the map repository
     */
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    /**
     * Sets the map repository.
     *
     * @param mapRepository the map repository to set
     */
    public void setMapRepository(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }
}