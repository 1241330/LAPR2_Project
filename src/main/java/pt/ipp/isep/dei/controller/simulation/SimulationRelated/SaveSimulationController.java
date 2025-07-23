package pt.ipp.isep.dei.controller.simulation.SimulationRelated;

import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for saving and discarding simulations in a scenario.
 * Handles serialization of simulation objects, backup and restore operations, and cleanup of map modifications and inventories.
 */
public class SaveSimulationController {
    /**
     * The simulation currently being managed.
     */
    private Simulation actualSimulation;

    /**
     * The scenario to which the simulation belongs.
     */
    private Scenario actualScenario;

    /**
     * Directory where simulation files are stored.
     */
    private static final String NAME_DIRECTORY = "data/Simulation/";

    /**
     * The name of the simulation.
     */
    private String nameSimulation;

    /**
     * The name of the scenario.
     */
    private String nameScenario;

    /**
     * File extension for simulation files.
     */
    private static final String EXTENSION = ".ser";

    /**
     * Default constructor.
     */
    public SaveSimulationController() {}

    /**
     * Sets the current simulation and updates related scenario and names.
     * @param actualSimulation the simulation to set as current
     */
    public void setActualSimulation(Simulation actualSimulation) {
        this.actualSimulation = actualSimulation;
        this.nameSimulation = actualSimulation.getName();
        this.actualScenario = actualSimulation.getScenario();
        this.nameScenario = actualSimulation.getScenarioName();
    }

    /**
     * Saves the current simulation to a file, serializing its state and cleaning up map modifications and inventories.
     * If the simulation contains house blocks or industries, their inventories are copied before saving.
     */
    public void saveSimulation() {
        List<HouseBlock> houseBlockList = new ArrayList<>();
        for (HouseBlock houseBlock : actualSimulation.getMap().getHouseBlockList()) {
            HouseBlock newHouseBlock = new HouseBlock(houseBlock.getPosition(), houseBlock.getCityName());
            for (Resource resource : houseBlock.getInventory().getAllResources()) {
                newHouseBlock.addResourceToInventory(resource);
            }
            houseBlockList.add(newHouseBlock);
        }
        actualSimulation.setHouseBlocks(houseBlockList);
        List<Industry> industryList = new ArrayList<>();
        for (Industry industry : actualSimulation.getMap().getIndustriesList()) {
            Industry newIndustry = new Industry(industry.getName(), industry.getIndustryType(), industry.getPosition());
            for (Resource resource : industry.getInventory().getAllResources()) {
                newIndustry.addResourceToInventory(resource);
            }
            industryList.add(newIndustry);
        }
        actualSimulation.setIndustries(industryList);
        clearMapModifications();
        try {
            FileOutputStream fileOut = new FileOutputStream(NAME_DIRECTORY + nameSimulation + "-" + nameScenario + EXTENSION);
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(actualSimulation);
            outStream.close();
            fileOut.close();
        } catch(IOException i) {
            i.printStackTrace();
        }
        clearInventories();
    }

    /**
     * Discards changes to the current simulation, restores backup if it exists, and cleans up map modifications and inventories.
     * Removes the simulation from the scenario if not saved.
     */
    public void dontSaveSimulation() {
        clearMapModifications();
        clearInventories();
        actualScenario.removeSimulation(actualSimulation);
        if (alreadyExistSimulation()) {
            Simulation backupSimulation = null;
            try {
                FileInputStream fileIn = new FileInputStream(NAME_DIRECTORY + nameSimulation + "-" + nameScenario + EXTENSION);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                backupSimulation = (Simulation) in.readObject();
                in.close();
                fileIn.close();
            } catch(IOException i) {
                i.printStackTrace();
            } catch(ClassNotFoundException c) {
                c.printStackTrace();
            }
            if (backupSimulation != null) {
                backupSimulation.setScenario(actualScenario);
                actualScenario.addSimulation(backupSimulation);
            }
        }
    }

    /**
     * Removes all stations and railway lines from the map of the current simulation.
     * Used to clean up temporary modifications before saving or discarding.
     */
    private void clearMapModifications() {
        Map map = actualSimulation.getMap();
        List<Station> stationList = actualSimulation.getStations();
        for (Station station : stationList) {
            map.removeElement(station);
        }

        List<RailwayLine> railwayLineList = actualSimulation.getRailwayLines();
        for (RailwayLine railwayLine : railwayLineList) {
            map.removeElement(railwayLine);
        }
    }

    /**
     * Clears all resources from the inventories of industries and house blocks in the map of the current simulation.
     * Used to reset inventories after saving or discarding.
     */
    private void clearInventories() {
        Map map = actualSimulation.getMap();
        List<Industry> industryList = map.getIndustriesList();
        for (Industry industry : industryList) {
            industry.getInventory().getAllResources().clear();
        }

        List<HouseBlock> houseBlockList = map.getHouseBlockList();
        for (HouseBlock houseBlock : houseBlockList) {
            houseBlock.getInventory().getAllResources().clear();
        }
    }

    /**
     * Checks if a simulation file already exists in the simulation directory.
     * @return true if the file exists, false otherwise
     */
    private boolean alreadyExistSimulation() {
        return Utils.fileExistsInFolder(NAME_DIRECTORY,nameSimulation + "-" + nameScenario + EXTENSION);
    }
}

