package pt.ipp.isep.dei.ui.console.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.editor.EditorController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.ui.console.map.*;
import pt.ipp.isep.dei.ui.console.scenario.CreateScenarioUI;
import pt.ipp.isep.dei.ui.console.scenario.DeleteScenarioUI;
import pt.ipp.isep.dei.ui.console.scenario.ShowScenariosUI;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the user interface for the editor mode,
 * allowing users to manage maps and scenarios.
 */
public class EditorUI implements Runnable {
    private EditorController controller;

    /**
     * Constructs an EditorUI instance initializing the controller.
     */
    public EditorUI() {
        controller = new EditorController();
    }

    /**
     * Runs the editor user interface loop,
     * displaying the main menu and handling user input.
     */
    public void run() {
        do {
            List<MenuItem> options = new ArrayList<>();
            options.add(new MenuItem("Map", this::MapRelated));
            options.add(new MenuItem("Scenario", this::ScenarioRelated));
            int option = Utils.chooseOptionPrintMenuAndManualReturn("Editor Mode", Utils.convertObjectsToDescriptions(options), "Logout", "Choice");
            if (option == 0) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu/MainMenu.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.setTitle("MABEC - Main Menu");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            options.get(option - 1).run();
        } while (true);
    }

    /**
     * Displays the map-related menu and processes user choices
     * for creating, showing, editing, or deleting maps.
     */
    private void MapRelated() {
        do {
            List<String> options = new ArrayList<>();
            options.add("Create a Map");
            options.add("Show a Map");
            options.add("Edit a Map");
            options.add("Delete a Map");
            int option = Utils.chooseOptionPrintMenuAndManualReturn("Map Related", options, "Return", "Choice");
            if (option == 0) {
                return;
            }
            if (option != 1) {
                if (!chooseMap()) {
                    switch (option) {
                        case 2:
                            new ShowMapUI(controller.getActualMap()).run();
                            break;
                        case 3:
                            new EditMapUI(controller.getActualMap()).run();
                            break;
                        case 4:
                            new DeleteMapUI(controller.getActualMap()).run();
                            break;
                        default:
                            Utils.printMessage("< Invalid option >");
                    }
                }
            } else new CreateMapUI().run();
        } while (true);
    }

    /**
     * Displays the scenario-related menu and processes user choices
     * for creating or showing scenarios.
     */
    private void ScenarioRelated() {
        do {
            List<String> options = new ArrayList<>();
            options.add("Create a Scenario");
            options.add("Show a Scenario");
            options.add("Delete a Scenario");
            int option = Utils.chooseOptionPrintMenuAndManualReturn("Scenario Related", options, "Return", "Choice");
            if (option == 0) {
                return;
            }
            if (chooseMap()) {
                return;
            }
            switch (option) {
                case 1:
                    new CreateScenarioUI(controller.getActualMap()).run();
                    break;
                case 2:
                    new ShowScenariosUI(controller.getActualMap()).run();
                    break;
                case 3:
                    new DeleteScenarioUI(controller.getActualMap()).run();
                    break;
                default:
                    Utils.printMessage("< Invalid option >");
            }
        } while (true);
    }

    /**
     * Prompts the user to select a map from the list of active maps.
     * Sets the chosen map as the current active map in the controller.
     *
     * @return true if the user chooses to return or if no valid map is selected,
     * false if a map is successfully selected.
     */
    private boolean chooseMap() {
        try {
            if (controller.thereAreActiveMaps()) {
                List<Map> listMaps = controller.listMaps();
                if (listMaps == null || listMaps.isEmpty()) {
                    throw new IllegalStateException("No maps available for editing");
                }
                int option = Utils.chooseOptionPrintMenuAndManualReturn("List of maps", Utils.convertObjectsToDescriptions(listMaps), "Return", "Choice");
                if (option == 0) {
                    return true;
                } else {
                    if (option < 1 || option > listMaps.size()) {
                        throw new IllegalArgumentException("Invalid map selection");
                    }
                    controller.setActualMap(listMaps.get(option - 1));
                    return false;
                }
            } else {
                Utils.printMessage("< No active maps found >");
            }
        } catch (IllegalArgumentException e) {
            Utils.printMessage("Error: " + e.getMessage());
        } catch (Exception e) {
            Utils.printMessage("Unexpected error: " + e.getMessage());
        }
        return true;
    }
}
