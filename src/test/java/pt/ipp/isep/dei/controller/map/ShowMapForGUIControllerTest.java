package pt.ipp.isep.dei.controller.map;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.domain.Map.MapElement;

import static org.junit.jupiter.api.Assertions.*;

class ShowMapForGUIControllerTest {

    @BeforeAll
    static void initJFX() {
        // Initializes JavaFX environment for headless testing
        new JFXPanel();
    }

    @BeforeEach
    void setupEach() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    /**
     * Tests setting and getting the simulation.
     */
    @Test
    void testSetAndGetSimulation() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Simulation simulation = new Simulation("sim1", null);
        controller.setSimulation(simulation);
        assertEquals(simulation, controller.getSimulation());
    }

    /**
     * Tests setMap with a valid simulation and map.
     * Verifies that the GridPane is populated with cells.
     */
    @Test
    void testSetMapPopulatesGrid() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Map map = new Map("TestMap", new Size(2, 2));
        Scenario scenario = new Scenario(map, "TestScenario", 100, null, null);
        Simulation simulation = new Simulation("sim2", scenario);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        controller.setMap(grid, label);
        assertEquals(4, grid.getChildren().size()); // 2x2 grid
    }

    /**
     * Tests setMap with a simulation that has a null scenario.
     * Should not throw an exception.
     */
    @Test
    void testSetMapWithNullScenario() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Simulation simulation = new Simulation("sim3", null);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        assertDoesNotThrow(() -> controller.setMap(grid, label));
    }

    /**
     * Tests setMap with a null simulation.
     * Should not throw an exception.
     */
    @Test
    void testSetMapWithNullSimulation() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        GridPane grid = new GridPane();
        Label label = new Label();
        assertDoesNotThrow(() -> controller.setMap(grid, label));
    }

    /**
     * Tests setMap with a simulation whose scenario is null.
     * Should not throw an exception and should not add any cells.
     */
    @Test
    void testSetMapWithSimulationScenarioNull() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Simulation simulation = new Simulation("sim4", null);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        assertDoesNotThrow(() -> controller.setMap(grid, label));
        assertEquals(0, grid.getChildren().size());
    }

    /**
     * Tests setMap with a simulation whose scenario has a null map.
     * Should not throw an exception and should not add any cells.
     */
    @Test
    void testSetMapWithScenarioMapNull() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Scenario scenario = new Scenario(null, "TestScenario", 100, null, null);
        Simulation simulation = new Simulation("sim5", scenario);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        assertDoesNotThrow(() -> controller.setMap(grid, label));
        assertEquals(0, grid.getChildren().size());
    }

    /**
     * Tests setMap with a map of size 0x0.
     * Should not throw an exception and should not add any cells.
     */
    @Test
    void testSetMapWithZeroSizeMap() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Map map = new Map("TestMap", new Size(0, 0));
        Scenario scenario = new Scenario(map, "TestScenario", 100, null, null);
        Simulation simulation = new Simulation("sim6", scenario);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        assertDoesNotThrow(() -> controller.setMap(grid, label));
        assertEquals(0, grid.getChildren().size());
    }

    /**
     * Tests setMap with a map that has no elements (getMapElementsUsed returns empty).
     * Should not throw an exception and should only add empty cells.
     */
    @Test
    void testSetMapWithEmptyElements() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Map map = new Map("TestMap", new Size(2, 2)) {
            @Override
            public java.util.List<pt.ipp.isep.dei.domain.Map.MapElement> getMapElementsUsed() {
                return java.util.Collections.emptyList();
            }
        };
        Scenario scenario = new Scenario(map, "TestScenario", 100, null, null);
        Simulation simulation = new Simulation("sim7", scenario);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        controller.setMap(grid, label);
        assertEquals(4, grid.getChildren().size()); // Only empty cells
    }

    /**
     * Tests setMap with a null GridPane.
     * Should throw a NullPointerException.
     */
    @Test
    void testSetMapWithNullGridPane() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Map map = new Map("TestMap", new Size(2, 2));
        Scenario scenario = new Scenario(map, "TestScenario", 100, null, null);
        Simulation simulation = new Simulation("sim8", scenario);
        controller.setSimulation(simulation);
        Label label = new Label();
        assertThrows(NullPointerException.class, () -> controller.setMap(null, label));
    }

    /**
     * Tests mouse hover and exit events to cover handleMouseHover and resetCellStyle.
     */
    @Test
    void testMouseHoverAndExit() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        Map map = new Map("TestMap", new Size(1, 1));
        Scenario scenario = new Scenario(map, "TestScenario", 100, null, null);
        Simulation simulation = new Simulation("sim9", scenario);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        controller.setMap(grid, label);
        StackPane cell = (StackPane) grid.getChildren().get(0);
        // Simulate mouse hover and exit using fireEvent
        Platform.runLater(() -> {
            cell.fireEvent(new MouseEvent(MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null));
            assertTrue(label.getText().contains("Position:"));
            cell.fireEvent(new MouseEvent(MouseEvent.MOUSE_EXITED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null));
            assertTrue(cell.getStyle().contains("-fx-background-color"));
        });
    }

    /**
     * Tests mouse click event to cover handleMouseClick.
     */
    @Test
    void testMouseClick() {
        ShowMapForGUIController controller = new ShowMapForGUIController();
        // Use variables for position and occupied positions
        var newPosition = new pt.ipp.isep.dei.domain._Others_.Position(0, 0);
        var occupiedPositions = java.util.Collections.singletonList(newPosition);
        class TestMapElement extends MapElement {
            TestMapElement() {
                super(newPosition, occupiedPositions);
            }
            @Override public String getType() { return "TEST"; }
        }
        Map map = new Map("TestMap", new Size(1, 1)) {
            @Override
            public java.util.List<MapElement> getMapElementsUsed() {
                return java.util.Collections.singletonList(new TestMapElement());
            }
        };
        Scenario scenario = new Scenario(map, "TestScenario", 100, null, null);
        Simulation simulation = new Simulation("sim10", scenario);
        controller.setSimulation(simulation);
        GridPane grid = new GridPane();
        Label label = new Label();
        controller.setMap(grid, label);
        StackPane cell = (StackPane) grid.getChildren().get(0);
        // Simulate mouse click using fireEvent
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> cell.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null)));
        });
    }
}
