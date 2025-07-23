package pt.ipp.isep.dei;

import pt.ipp.isep.dei.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Resource.HouseBlockResource;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.FuelType;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.repository.*;
import pt.ipp.isep.dei.ui.console.utils.Utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Bootstrap class responsible for initializing the application with default data.
 * Implements Runnable to allow execution at application startup.
 */
public class Bootstrap implements Runnable {

    /**
     * Runs the bootstrap process, adding default data to repositories.
     */
    @Override
    public void run() {
        addUsers();
        addResources();
        addCarriages();
        addLocomotives();
        initializeMapsSaved();
        initializeScenariosSaved();
        initializeSimulationsSaved();
    }

    /**
     * Adds default users and roles to the authentication repository.
     */
    public void addUsers() {
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

        authenticationRepository.addUserRole(AuthenticationController.ROLE_EDITOR, AuthenticationController.ROLE_EDITOR);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_PLAYER, AuthenticationController.ROLE_PLAYER);

        authenticationRepository.addUserWithRole("Editor", "editor@this.app", "edt", AuthenticationController.ROLE_EDITOR);
        authenticationRepository.addUserWithRole("Player", "player@this.app", "ply", AuthenticationController.ROLE_PLAYER);
    }

    /**
     * Adds default resources (house blocks, primary, and transforming resources) to their repositories.
     */
    private void addResources() {
        HouseBlockResourceRepository houseBlockResourceRepository = Repositories.getInstance().getHouseBlockResourceRepository();

        houseBlockResourceRepository.addHouseBlockResource(new HouseBlockResource("Passenger", 30, 2, 1));
        houseBlockResourceRepository.addHouseBlockResource(new HouseBlockResource("Mail", 30, 2, 1));

        PrimaryResourceRepository primaryResourceRepository = Repositories.getInstance().getPrimaryResourceRepository();

        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Coal", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Iron", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Lumber", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Grain", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Cattle", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Oil", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Copper", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Gold", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Silver", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Sulfur", 30, 2, 1));
        primaryResourceRepository.addPrimaryResource(new PrimaryResource("Lead", 30, 2, 1));

        TransformingResourceRepository transformingResourceRepository = Repositories.getInstance().getTransformingTypeRepository();

        transformingResourceRepository.addTransformingType(new TransformingResource("Steel", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Coal"),
                        primaryResourceRepository.getPrimaryResourceByName("Iron"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Lumber Goods", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Lumber"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Food", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Cattle"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Oil Goods", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Oil"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Lead Goods", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Lead"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Jewellery", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Gold"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Chemical", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Sulfur"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Beer", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Grain"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Electronic", 30, 2, 1,
                List.of(primaryResourceRepository.getPrimaryResourceByName("Copper"),
                        transformingResourceRepository.getTransformingTypeByName("Chemical"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Car", 30, 2, 1,
                List.of(transformingResourceRepository.getTransformingTypeByName("Steel"),
                        transformingResourceRepository.getTransformingTypeByName("Electronic"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Plastics", 30, 2, 1,
                List.of(transformingResourceRepository.getTransformingTypeByName("Chemical"))));
        transformingResourceRepository.addTransformingType(new TransformingResource("Furniture", 30, 2, 1,
                List.of(transformingResourceRepository.getTransformingTypeByName("Lumber Goods"))));
    }

    /**
     * Adds default locomotives to the locomotive repository.
     */
    private void addLocomotives() {
        LocomotiveRepository locoRepo = Repositories.getInstance().getLocomotiveRepository();

        locoRepo.save(new Locomotive("Alfa Pendular", "/images/Locomotive/Locomotive_Alpha.png", 4000.0, 2.8, 220.0, 1999, 300, FuelType.ELECTRICITY, 2, 18));
        locoRepo.save(new Locomotive("ES64F4", "/images/Locomotive/Locomotive_ES64F4.png", 2000.0, 2.5, 140.0, 2000, 130, FuelType.ELECTRICITY, 3, 15));
        locoRepo.save(new Locomotive("Shinkansen N700S", "/images/Locomotive/Locomotive_ShinkansenN700S.png", 715000.0, 25.0, 360.0, 2020, 25000, FuelType.ELECTRICITY, 2, 20));

        locoRepo.save(new Locomotive("CP1900", "/images/Locomotive/Locomotive_CP1900.png", 2200.0, 1.8, 140.0, 2000, 240, FuelType.DIESEL, 4, 25));
        locoRepo.save(new Locomotive("GE AC4400CW", "/images/Locomotive/Locomotive_GE_AC4400CW.png", 4400, 2.0, 50.0, 1993, 350, FuelType.DIESEL, 8, 28));

        locoRepo.save(new Locomotive("CP E214", "/images/Locomotive/Locomotive_CP_E214.png", 2200.0, 1.2, 95.0, 1955, 150, FuelType.STEAM, 2, 35));
        locoRepo.save(new Locomotive("Penydarren", "/images/Locomotive/Locomotive_Penydarren.png", 5000.0, 4.4, 8.0, 1804, 5, FuelType.STEAM, 2, 40));
        locoRepo.save(new Locomotive("Thomas", "/images/Locomotive/Locomotive_Tom.png", 500.0, 2.0, 60.0, 2001, 40, FuelType.STEAM, 1, 32));

    }

    /**
     * Adds default carriages to the carriage repository.
     */
    private void addCarriages() {
        CarriageRepository carrRepo = Repositories.getInstance().getCarriageRepository();

        carrRepo.save(new Carriage("Amtrak Superliner","/images/Carriage/Carriage_Amtrak_Superliner.png", 1900, 80, 3));
        carrRepo.save(new Carriage("DOT-111 Tank Car","/images/Carriage/Carriage_DOT-111TankCar.png", 2000, 130, 5));
        carrRepo.save(new Carriage("Covered Hopper","/images/Carriage/Carriage_CoveredHopper.png", 2001, 150, 8));
        carrRepo.save(new Carriage("Rotary Dump Gondola","/images/Carriage/Carriage_RotaryDumpGondola.png", 1900, 190, 8));
    }

    /**
     * Loads and initializes all saved maps from the data directory into the map repository.
     * Each map is deserialized and added to the repository, clearing its scenarios first.
     */
    private void initializeMapsSaved() {
        final String NAME_DIRECTORY = "data/Map/";
        MapRepository mapRepository = Repositories.getInstance().getMapRepository();
        List<String> listFilesInDirectory = Utils.listFilesInDirectory(NAME_DIRECTORY);
        for (String mapFile : listFilesInDirectory) {
            Map map;
            try {
                FileInputStream fileIn = new FileInputStream(NAME_DIRECTORY + mapFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                map = (Map) in.readObject();
                in.close(); fileIn.close();
            } catch(IOException i) {
                i.printStackTrace();
                continue;
            } catch(ClassNotFoundException c) {
                System.out.println("Map class not found");
                c.printStackTrace();
                continue;
            }
            if (map != null) {
                map.clearScenarios();
                mapRepository.addMap(map);
            }
        }
    }

    /**
     * Loads and initializes all saved scenarios from the data directory into their respective maps.
     * Each scenario is deserialized, its simulations cleared, and added to the corresponding map.
     */
    private void initializeScenariosSaved() {
        final String NAME_DIRECTORY = "data/Scenario/";
        MapRepository mapRepository = Repositories.getInstance().getMapRepository();
        List<Map> maps = mapRepository.getAllMaps();
        List<String> listFilesInDirectory = Utils.listFilesInDirectory(NAME_DIRECTORY);
        for (Map map : maps) {
            for (String scenarioFile : listFilesInDirectory) {
                String[] parts = scenarioFile.split("-");
                String nameMapInScenario = parts[1].replace(".ser", "");
                if (nameMapInScenario.equals(map.getName())) {
                    Scenario scenario;
                    try {
                        FileInputStream fileIn = new FileInputStream(NAME_DIRECTORY + scenarioFile);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        scenario = (Scenario) in.readObject();
                        in.close(); fileIn.close();
                    } catch(IOException i) {
                        i.printStackTrace();
                        continue;
                    } catch(ClassNotFoundException c) {
                        System.out.println("Scenario class not found");
                        c.printStackTrace();
                        continue;
                    }
                    scenario.clearSimulations();
                    map.addScenario(scenario);
                }
            }
        }
    }

    /**
     * Loads and initializes all saved simulations from the data directory into their respective scenarios.
     * Each simulation is deserialized, linked to its scenario, and added to the scenario.
     */
    private void initializeSimulationsSaved() {
        final String NAME_DIRECTORY = "data/Simulation/";
        MapRepository mapRepository = Repositories.getInstance().getMapRepository();
        List<Map> maps = mapRepository.getAllMaps();
        List<String> listFilesInDirectory = Utils.listFilesInDirectory(NAME_DIRECTORY);
        for (Map map : maps) {
            for (Scenario scenario : map.getScenarios()) {
                for (String simulationFile : listFilesInDirectory) {
                    String[] parts = simulationFile.split("-");
                    String nameSimulationInScenario = parts[1].replace(".ser", "");
                    if (nameSimulationInScenario.equals(scenario.getName())) {
                        Simulation simulation;
                        try {
                            FileInputStream fileIn = new FileInputStream(NAME_DIRECTORY + simulationFile);
                            ObjectInputStream in = new ObjectInputStream(fileIn);
                            simulation = (Simulation) in.readObject();
                            in.close(); fileIn.close();
                        } catch(IOException i) {
                            i.printStackTrace();
                            continue;
                        } catch(ClassNotFoundException c) {
                            System.out.println("Simulation class not found");
                            c.printStackTrace();
                            continue;
                        }
                        simulation.setScenario(scenario);
                        scenario.addSimulation(simulation);
                    }
                }
            }
        }
    }
}

