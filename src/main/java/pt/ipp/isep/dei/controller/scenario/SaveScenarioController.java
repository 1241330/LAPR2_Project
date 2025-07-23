package pt.ipp.isep.dei.controller.scenario;

import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.*;

/**
 * Controller responsible for saving and discarding scenarios.
 * Handles serialization and deserialization of scenario objects.
 */
public class SaveScenarioController {

    private Scenario actualScenario;
    private Map mapOfScenario;
    private String scenarioName;
    private String mapName;
    private static final String NAME_DIRECTORY = "data/Scenario/";
    private static final String EXTENSION = ".ser";

    /**
     * Constructs a SaveScenarioController with the given scenario.
     * @param scenario the scenario to be managed
     */
    public SaveScenarioController(Scenario scenario) {
        this.actualScenario = scenario;
        this.scenarioName = scenario.getName();
        this.mapOfScenario = scenario.getMap();
        this.mapName = mapOfScenario.getName();
    }

    /**
     * Saves the current scenario to a file using serialization.
     */
    public void saveScenario() {
        try {
            FileOutputStream fileOut = new FileOutputStream(NAME_DIRECTORY + scenarioName + "-" + mapName + EXTENSION);
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(actualScenario);
            outStream.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Discards the current scenario from the map.
     * If a backup exists, restores it to the map.
     */
    public void dontSaveScenario() {
        mapOfScenario.removeScenario(actualScenario);
        if (alreadyExistScenario()) {
            Scenario backupScenario = null;
            try {
                FileInputStream fileIn = new FileInputStream(NAME_DIRECTORY + scenarioName + "-" + mapName + EXTENSION);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                backupScenario = (Scenario) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
            if (backupScenario != null) {
                mapOfScenario.addScenario(backupScenario);
            }
        }
    }

    /**
     * Checks if a scenario file already exists in the directory.
     * @return true if the file exists, false otherwise
     */
    private boolean alreadyExistScenario() {
        return Utils.fileExistsInFolder(NAME_DIRECTORY, scenarioName + "-" + mapName + EXTENSION);
    }

    /**
     * Gets the current scenario.
     * @return the actual scenario
     */
    public Scenario getActualScenario() {
        return actualScenario;
    }

    /**
     * Sets the current scenario.
     * @param actualScenario the scenario to set
     */
    public void setActualScenario(Scenario actualScenario) {
        this.actualScenario = actualScenario;
    }

    /**
     * Gets the map of the scenario.
     * @return the map of the scenario
     */
    public Map getMapOfScenario() {
        return mapOfScenario;
    }

    /**
     * Sets the map of the scenario.
     * @param mapOfScenario the map to set
     */
    public void setMapOfScenario(Map mapOfScenario) {
        this.mapOfScenario = mapOfScenario;
    }

    /**
     * Gets the scenario name.
     * @return the scenario name
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * Sets the scenario name.
     * @param scenarioName the scenario name to set
     */
    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    /**
     * Gets the map name.
     * @return the map name
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Sets the map name.
     * @param mapName the map name to set
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}