package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.CreateRoute;

import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.ChooseDepartureStationController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TypeOfCargoMode;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * GUI class for selecting the departure station during the creation of a route in the simulation.
 * This class handles the display and interaction logic for choosing a station, selecting cargo modes,
 * and initializing a graph-based visualization of the available stations and railway lines.
 *
 * It also allows the user to proceed to the next step (creating points of the route), go back
 * to the railway type selection screen, or exit to the pause menu.
 *
 * FXML file: /fxml/RouteRelated/CreateRoute/ChooseDepartureStation.fxml
 */
public class ChooseDepartureStationGUI implements Initializable {

    /**
     * Color used to highlight the selected station on the graph.
     */
    private final String GRAPHSTREAM_SELECTED_STATION_COLOR = "#7fc9ac";

    private ChooseDepartureStationController controller;

    @FXML
    private Label errorLabel;

    @FXML
    private transient ComboBox cargoModeComboBox;

    @FXML
    private transient TableView<Station> stationsAvailableTableView;

    @FXML
    private transient TableView<ResourcesType> cargoTableView;

    @FXML
    private transient TableColumn<Station, String> nameColumn;

    @FXML
    private transient TableColumn<Station, String> typeColumn;

    @FXML
    private transient TableColumn<ResourcesType, String> resourceNameColumn;

    @FXML
    private AnchorPane graphContainer;

    /**
     * Map that keeps track of Circle objects for each station, allowing for dynamic color updates.
     */
    private Map<Station, Circle> stationCircles = new HashMap<>();

    /**
     * Called when the GUI is initialized. Sets up listeners to resize the graph dynamically with the pane.
     *
     * @param url            URL location of the FXML file.
     * @param resourceBundle Resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseDepartureStationController();

        graphContainer.widthProperty().addListener((obs, oldVal, newVal) -> initializeGraphStreamGraph());
        graphContainer.heightProperty().addListener((obs, oldVal, newVal) -> initializeGraphStreamGraph());
    }

    /**
     * Sets the simulation and railway type flag, then initializes GUI components.
     *
     * @param simulation                The simulation object.
     * @param railwayTypeAvailableFlag Whether the railway type is already chosen.
     */
    public void setData(Simulation simulation, boolean railwayTypeAvailableFlag) {
        controller.setSimulation(simulation);
        controller.setRailwayTypeAvailableFlag(railwayTypeAvailableFlag);

        initializeGUIItems();
    }

    /**
     * Initializes combo boxes, tables, and their listeners, and prepares data to be displayed.
     */
    private void initializeGUIItems() {
        cargoModeComboBox.setItems(controller.getCargoModeTypes());

        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        typeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStationType()));

        stationsAvailableTableView.setItems(controller.getAvailableStations());

        stationsAvailableTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        updateGraphForStation(newSelection);
                    }
                });

        resourceNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));

        // Remove horizontal scrollbars from cargoTableView
        Platform.runLater(() -> {
            cargoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : cargoTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar &&
                        scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                }
            }
        });

        // Remove horizontal scrollbars from stationsAvailableTableView
        Platform.runLater(() -> {
            stationsAvailableTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : cargoTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar &&
                        scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                }
            }
        });

        cargoTableView.setItems(controller.getResourceType());
        cargoTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Creates and displays the graphical representation of stations and railway lines.
     * This is called whenever the size of the container changes.
     */
    private void initializeGraphStreamGraph() {
        List<Station> stationList = controller.getSimulation().getStations();
        List<RailwayLine> railwayLineList = controller.findAvailableRailwayLines();

        if (graphContainer.getWidth() == 0 || graphContainer.getHeight() == 0) {
            return;
        }

        Platform.runLater(() -> {
            graphContainer.getChildren().clear();

            double paneWidth = graphContainer.getWidth();
            double paneHeight = graphContainer.getHeight();

            stationCircles.clear();
            Map<Station, Point2D> stationPositions = new HashMap<>();

            for (Station station : stationList) {
                double[] scaledPositions = controller.findScaledPositions(station, paneWidth, paneHeight);
                stationPositions.put(station, new Point2D(scaledPositions[0], scaledPositions[1]));

                String stationColor = controller.getStationColor(station);
                int stationSize = controller.getStationSize(station);

                Circle stationCircle = new Circle(scaledPositions[0], scaledPositions[1], stationSize);
                stationCircle.setStyle("-fx-fill: " + stationColor + "; -fx-stroke: black;");
                stationCircles.put(station, stationCircle);

                int labelDistance = controller.getLabelDistance(stationSize);
                Text stationLabel = new Text(scaledPositions[0] - 30, scaledPositions[1] + labelDistance, station.getName());

                graphContainer.getChildren().addAll(stationCircle, stationLabel);
            }

            for (RailwayLine line : railwayLineList) {
                Station a = line.getStation1();
                Station b = line.getStation2();

                Point2D pointA = stationPositions.get(a);
                Point2D pointB = stationPositions.get(b);

                if (pointA != null && pointB != null) {
                    Line edge = new Line(pointA.getX(), pointA.getY(), pointB.getX(), pointB.getY());
                    String railwayColor = controller.getRailwayLineColor(line);
                    int railwaySize = controller.getRailwayLineSize(line);

                    edge.setStyle("-fx-stroke: " + railwayColor + "; -fx-stroke-width: " + railwaySize + ";");
                    graphContainer.getChildren().add(0, edge);
                }
            }
        });
    }

    /**
     * Highlights the selected station in the graph and resets the others to their default color.
     *
     * @param station The station to highlight.
     */
    private void updateGraphForStation(Station station) {
        stationCircles.forEach((st, circle) -> {
            String defaultColor = controller.getStationColor(st);
            circle.setStyle("-fx-fill: " + defaultColor + "; -fx-stroke: black;");
        });

        Circle selectedCircle = stationCircles.get(station);
        if (selectedCircle != null) {
            selectedCircle.setStyle("-fx-fill: " + GRAPHSTREAM_SELECTED_STATION_COLOR + "; -fx-stroke: black;");
        }
    }

    /**
     * Event handler for the "Select" button.
     * Validates user input and moves to the next screen to define the points of the route.
     *
     * @param event The action event triggered by the button.
     */
    @FXML
    public void handleSelectButtonOnAction(ActionEvent event) {
        if (stationsAvailableTableView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Choose a Station line first!!");
            return;
        }
        if (cargoModeComboBox.getValue() == null) {
            errorLabel.setText("Choose a Cargo Mode first!!");
            return;
        }

        List<ResourcesType> selectedResources = new ArrayList<>(cargoTableView.getSelectionModel().getSelectedItems());

        controller.createPointOfRoute(
                stationsAvailableTableView.getSelectionModel().getSelectedItem(),
                (TypeOfCargoMode) cargoModeComboBox.getValue(),
                selectedResources
        );

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/CreateRoute/CreatePointOfRoute.fxml"));
            Parent root = loader.load();

            CreatePointOfRouteGUI createPointOfRouteGUI = loader.getController();
            createPointOfRouteGUI.setData(controller.getSimulation(), controller.getRailwayTypeAvailableFlag(), controller.getDeparturePoint());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Create Points of Route");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for the "Back" button.
     * Navigates to the previous screen where the user can choose the railway type.
     *
     * @param event The action event triggered by the button.
     */
    @FXML
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/CreateRoute/ChooseRailwayTypeForRoute.fxml"));
            Parent root = loader.load();

            ChooseRailwayTypeForRouteGUI chooseRailwayTypeForRouteGUI = loader.getController();
            chooseRailwayTypeForRouteGUI.setData(controller.getSimulation());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Available Railway Type");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for the "Exit" button.
     * Navigates to the pause menu screen of the simulation.
     *
     * @param event The action event triggered by the button.
     */
    @FXML
    public void handleExitButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/PauseMenu.fxml"));
            Parent root = loader.load();

            PauseMenuGUI pauseMenuGUI = loader.getController();
            pauseMenuGUI.setSimulation(controller.getSimulation());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Pause Menu");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
