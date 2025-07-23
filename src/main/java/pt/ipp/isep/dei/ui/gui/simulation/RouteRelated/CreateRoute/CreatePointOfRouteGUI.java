package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.CreateRoute;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.CreateRoute.CreatePointOfRouteController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TypeOfCargoMode;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI class for creating points of a route in the simulation.
 * Handles the user interface logic for adding, visualizing, and confirming route points.
 * Integrates with the controller to manage simulation data and updates the graph accordingly.
 */
public class CreatePointOfRouteGUI implements Initializable {

    private CreatePointOfRouteController controller;

    private final String GRAPHSTREAM_SELECTED_STATION_COLOR = "#7fc9ac";

    private final List<Node> routeGraphElements = new ArrayList<>();
    private final List<Node> tempGraphElements = new ArrayList<>();

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane graphContainer;

    @FXML
    private ComboBox cargoModeComboBox;

    @FXML
    private TableView<Station> stationTableView;

    @FXML
    private TableView<ResourcesType> cargoTableView;

    @FXML
    private TableColumn<Station, String> stationNameColumn;

    @FXML
    private TableColumn<ResourcesType, String> resourceNameColumn;

    @FXML
    private Button createRouteButton;

    /**
     * Initializes the controller and sets up listeners for the graph container's width and height properties.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new CreatePointOfRouteController();

        graphContainer.widthProperty().addListener((obs, oldVal, newVal) -> initializeGraphStreamGraph());
        graphContainer.heightProperty().addListener((obs, oldVal, newVal) -> initializeGraphStreamGraph());
    }

    /**
     * Sets the simulation data, railway type flag, and departure station, then initializes GUI items.
     *
     * @param simulation               The simulation instance.
     * @param railwayTypeAvailableFlag Flag indicating if the railway type is available.
     * @param departureStation         The departure station for the route.
     */
    public void setData(Simulation simulation, boolean railwayTypeAvailableFlag, PointOfRoute departureStation) {
        controller.setSimulation(simulation);
        controller.setDeparturePoint(departureStation);
        controller.setRailwayTypeAvailableFlag(railwayTypeAvailableFlag);

        initializeGUIItems();
    }

    /**
     * Initializes GUI components, sets up table columns, listeners, and populates data.
     */
    private void initializeGUIItems() {
        createRouteButton.setVisible(false);
        cargoModeComboBox.setItems(controller.getCargoModeTypes());

        stationNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        resourceNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        Platform.runLater(() -> {
            cargoTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : cargoTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                }
            }
        });

        Platform.runLater(() -> {
            stationTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : stationTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                }
            }
        });

        stationTableView.setItems(controller.getAvailableStations(controller.getDepartureStation()));
        cargoTableView.setItems(controller.getResourceType());
        cargoTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        stationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateGraphForStation(newSelection);
            }
        });
    }

    /**
     * Initializes the graph visualization for the current departure station.
     */
    private void initializeGraphStreamGraph() {
        Station departureStation = controller.getPointOfRouteList().getLast().getStation();

        if (departureStation == null || graphContainer.getWidth() == 0 || graphContainer.getHeight() == 0) {
            return;
        }

        if (stationTableView.getSelectionModel().getSelectedItem() != null) {
            return;
        }

        Platform.runLater(() -> {
            graphContainer.getChildren().clear();
            routeGraphElements.clear();

            double paneWidth = graphContainer.getWidth();
            double paneHeight = graphContainer.getHeight();

            double[] scaledPositions = controller.findScaledPositions(departureStation, paneWidth, paneHeight);
            double centerX = scaledPositions[0];
            double centerY = scaledPositions[1];

            String stationColor = controller.getStationColor(departureStation);
            int stationSize = controller.getStationSize(departureStation);

            Circle stationCircle = new Circle(centerX, centerY, stationSize);
            stationCircle.setStyle("-fx-fill: " + stationColor + "; -fx-stroke: black;");

            Polygon star = createStar(centerX, centerY, stationSize, stationSize / 2);
            star.setStyle("-fx-fill: gold; -fx-stroke: black; -fx-stroke-width: 1;");

            int labelDistance = controller.getLabelDistance(stationSize);
            Text stationLabel = new Text(centerX - 30, centerY + labelDistance, departureStation.getName());

            List<Node> elements = List.of(stationCircle, star, stationLabel);
            graphContainer.getChildren().addAll(elements);
            routeGraphElements.addAll(elements);
        });
    }

    /**
     * Updates the graph after a point of the route has been saved, drawing all confirmed route points and connections.
     */
    private void updateGraphAfterPointSaved() {
        List<PointOfRoute> routePoints = controller.getPointOfRouteList();
        if (routePoints.isEmpty()) return;

        Platform.runLater(() -> {
            double paneWidth = graphContainer.getWidth();
            double paneHeight = graphContainer.getHeight();

            // Draw all previous connections (confirmed path)
            List<Station> stationPath = controller.getStationPath();
            for (int i = 0; i < stationPath.size() - 1; i++) {
                Station s1 = stationPath.get(i);
                Station s2 = stationPath.get(i + 1);

                RailwayLine railwayLine = controller.getRailwayLineBetween(s1, s2);
                if (railwayLine == null) continue;

                double[] pos1 = controller.findScaledPositions(s1, paneWidth, paneHeight);
                double[] pos2 = controller.findScaledPositions(s2, paneWidth, paneHeight);

                javafx.scene.shape.Line fxLine = new javafx.scene.shape.Line(pos1[0], pos1[1], pos2[0], pos2[1]);
                fxLine.setStroke(javafx.scene.paint.Paint.valueOf(controller.getRailwayLineColor(railwayLine)));
                fxLine.setStrokeWidth(controller.getRailwayLineSize(railwayLine));

                graphContainer.getChildren().add(fxLine);
                routeGraphElements.add(fxLine);
            }

            // Redraw route points (confirmed stations)
            for (int i = 0; i < routePoints.size(); i++) {
                PointOfRoute point = routePoints.get(i);
                Station station = point.getStation();

                double[] position = controller.findScaledPositions(station, paneWidth, paneHeight);
                double x = position[0];
                double y = position[1];

                String color = controller.getStationColor(station);
                int size = controller.getStationSize(station);

                Circle stationCircle = new Circle(x, y, size);
                stationCircle.setStyle("-fx-fill: " + color + "; -fx-stroke: black;");

                Text stationLabel = new Text(x - 30, y + controller.getLabelDistance(size), station.getName());

                Polygon diamond = createDiamond(x, y, 10);
                diamond.setStyle("-fx-fill: blue; -fx-stroke: black;");

                Text number = new Text(x - 4, y + 4, String.valueOf(i + 1));
                number.setStyle("-fx-fill: white; -fx-font-weight: bold;");

                List<Node> newElements = List.of(stationCircle, stationLabel, diamond, number);
                graphContainer.getChildren().addAll(newElements);
                routeGraphElements.addAll(newElements);
            }

            // Draw star at departure station
            Station departure = controller.getDepartureStation();
            double[] departurePos = controller.findScaledPositions(departure, paneWidth, paneHeight);
            Polygon star = createStar(departurePos[0], departurePos[1], 10, 5);
            star.setStyle("-fx-fill: gold; -fx-stroke: black;");
            graphContainer.getChildren().add(star);
        });
    }

    /**
     * Creates a diamond-shaped polygon centered at the given coordinates.
     *
     * @param centerX The x-coordinate of the center.
     * @param centerY The y-coordinate of the center.
     * @param size    The size of the diamond.
     * @return The created diamond polygon.
     */
    private Polygon createDiamond(double centerX, double centerY, double size) {
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(
                centerX, centerY - size,
                centerX + size, centerY,
                centerX, centerY + size,
                centerX - size, centerY
        );
        return diamond;
    }

    /**
     * Creates a star-shaped polygon centered at the given coordinates.
     *
     * @param centerX     The x-coordinate of the center.
     * @param centerY     The y-coordinate of the center.
     * @param outerRadius The outer radius of the star.
     * @param innerRadius The inner radius of the star.
     * @return The created star polygon.
     */
    private Polygon createStar(double centerX, double centerY, double outerRadius, double innerRadius) {
        Polygon star = new Polygon();
        double angle = Math.PI / 5;
        for (int i = 0; i < 10; i++) {
            double r = (i % 2 == 0) ? outerRadius : innerRadius;
            double x = centerX + r * Math.sin(i * angle);
            double y = centerY - r * Math.cos(i * angle);
            star.getPoints().addAll(x, y);
        }
        return star;
    }

    /**
     * Updates the graph to display the path to the selected station, including temporary and permanent route elements.
     *
     * @param station The selected station to update the graph for.
     */
    private void updateGraphForStation(Station station) {
        errorLabel.setText("");
        List<Station> path = controller.findDjikstraPath(station);
        controller.setDjikstraPath(path);
        List<RailwayLine> usedRailwayLines = controller.findUsedRailwaysInPath(path);

        if (path.isEmpty()) {
            errorLabel.setText("No valid path found!");
            return;
        }

        Platform.runLater(() -> {
            graphContainer.getChildren().removeAll(tempGraphElements);
            tempGraphElements.clear();

            double paneWidth = graphContainer.getWidth();
            double paneHeight = graphContainer.getHeight();

            // Draw all permanent route lines (already used path)
            List<Station> stationPath = controller.getStationPath();
            for (int i = 0; i < stationPath.size() - 1; i++) {
                Station s1 = stationPath.get(i);
                Station s2 = stationPath.get(i + 1);

                double[] pos1 = controller.findScaledPositions(s1, paneWidth, paneHeight);
                double[] pos2 = controller.findScaledPositions(s2, paneWidth, paneHeight);

                RailwayLine railwayLine = controller.getRailwayLineBetween(s1, s2);
                javafx.scene.shape.Line fxLine = new javafx.scene.shape.Line(pos1[0], pos1[1], pos2[0], pos2[1]);
                fxLine.setStroke(javafx.scene.paint.Paint.valueOf(controller.getRailwayLineColor(railwayLine)));
                fxLine.setStrokeWidth(controller.getRailwayLineSize(railwayLine));

                graphContainer.getChildren().add(fxLine);
            }

            // Draw all temporary path lines
            for (RailwayLine line : usedRailwayLines) {
                Station s1 = line.getStation1();
                Station s2 = line.getStation2();

                double[] pos1 = controller.findScaledPositions(s1, paneWidth, paneHeight);
                double[] pos2 = controller.findScaledPositions(s2, paneWidth, paneHeight);

                javafx.scene.shape.Line fxLine = new javafx.scene.shape.Line(pos1[0], pos1[1], pos2[0], pos2[1]);
                fxLine.setStroke(javafx.scene.paint.Paint.valueOf(controller.getRailwayLineColor(line)));
                fxLine.setStrokeWidth(controller.getRailwayLineSize(line));

                graphContainer.getChildren().add(fxLine);
                tempGraphElements.add(fxLine);
            }

            // Draw permanent stations (circles, labels, diamonds, numbers)
            List<PointOfRoute> routePoints = controller.getPointOfRouteList();
            for (int i = 0; i < routePoints.size(); i++) {
                PointOfRoute point = routePoints.get(i);
                Station savedStation = point.getStation();

                double[] pos = controller.findScaledPositions(savedStation, paneWidth, paneHeight);
                double x = pos[0];
                double y = pos[1];

                String color = controller.getStationColor(savedStation);
                int size = controller.getStationSize(savedStation);

                Circle circle = new Circle(x, y, size);
                circle.setStyle("-fx-fill: " + color + "; -fx-stroke: black;");

                Text label = new Text(x - 30, y + controller.getLabelDistance(size), savedStation.getName());

                Polygon diamond = createDiamond(x, y, 10);
                diamond.setStyle("-fx-fill: blue; -fx-stroke: black;");

                Text number = new Text(x - 4, y + 4, String.valueOf(i + 1));
                number.setStyle("-fx-fill: white; -fx-font-weight: bold;");

                graphContainer.getChildren().addAll(circle, label, diamond, number);
            }

            // Draw departure station (star)
            Station departure = controller.getDepartureStation();
            double[] departurePos = controller.findScaledPositions(departure, paneWidth, paneHeight);
            Polygon star = createStar(departurePos[0], departurePos[1], 10, 5);
            star.setStyle("-fx-fill: gold; -fx-stroke: black;");
            graphContainer.getChildren().add(star);

            // Draw stations of the temporary path (circles and labels)
            for (Station pathStation : path) {
                double[] position = controller.findScaledPositions(pathStation, paneWidth, paneHeight);
                double x = position[0];
                double y = position[1];

                String color = controller.getStationColor(pathStation);
                int size = controller.getStationSize(pathStation);

                if (pathStation.equals(path.getLast()) && !pathStation.equals(controller.getDepartureStation())) {
                    color = GRAPHSTREAM_SELECTED_STATION_COLOR;
                }

                Circle stationCircle = new Circle(x, y, size);
                stationCircle.setStyle("-fx-fill: " + color + "; -fx-stroke: black;");

                Text stationLabel = new Text(x - 30, y + controller.getLabelDistance(size), pathStation.getName());

                graphContainer.getChildren().addAll(stationCircle, stationLabel);
                tempGraphElements.add(stationCircle);
                tempGraphElements.add(stationLabel);
            }
        });
    }

    /**
     * Handles the action of saving a point of the route, updating the controller and graph accordingly.
     *
     * @param event The action event triggered by the user.
     */
    public void handleSavePointButtonOnAction(ActionEvent event) {
        if (stationTableView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Choose a Station first!!");
            return;
        } else if (cargoModeComboBox.getValue() == null) {
            errorLabel.setText("Choose a Cargo Mode first!!");
            return;
        }

        List<ResourcesType> cargoToPick = new ArrayList<>(cargoTableView.getSelectionModel().getSelectedItems());

        controller.addPointOfRoute(new PointOfRoute(cargoToPick, stationTableView.getSelectionModel().getSelectedItem(), (TypeOfCargoMode) cargoModeComboBox.getValue()));
        controller.addPathPositions(controller.getDjikstraPath());

        stationTableView.setItems(controller.getAvailableStations(controller.getPointOfRouteList().getLast().getStation()));
        createRouteButton.setVisible(true);
        errorLabel.setText("");
        updateGraphAfterPointSaved();
    }

    /**
     * Handles the action of creating the route, opening the confirmation popup and updating the route path.
     *
     * @param event The action event triggered by the user.
     */
    public void handleCreateRouteButtonOnAction(ActionEvent event) {
        if (controller.getStationPath().getLast() != controller.getStationPath().getFirst()) {
            controller.getStationPath().removeLast();
            controller.getStationPath().addAll(controller.findDjikstraPath(controller.getStationPath().getFirst()));
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/PurchaseConfirmation2.fxml"));
            RouteCreationConfirmationPopGUI routeCreationConfirmationPopGUI = new RouteCreationConfirmationPopGUI();
            loader.setController(routeCreationConfirmationPopGUI);

            Parent root = loader.load();

            RouteCreationConfirmationPopGUI routeCreationConfirmationPopGUI1 = loader.getController();
            routeCreationConfirmationPopGUI1.setData(controller.getPointOfRouteList(), controller.getStationPath(), controller.getSimulation(), controller.getIsRailwayTypeAvailableFlag(), (Stage) ((Node) event.getSource()).getScene().getWindow());

            routeCreationConfirmationPopGUI1.initializeGUIItems("Create Route", "The route has " + controller.getPointOfRouteList().size() + " points of Route", "This route will be deactivated till you assign a train");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Create Route Confirmation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of returning to the previous screen to choose the departure station.
     *
     * @param event The action event triggered by the user.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/CreateRoute/ChooseDepartureStation.fxml"));
            Parent root = loader.load();

            ChooseDepartureStationGUI chooseDepartureStationGUI = loader.getController();
            chooseDepartureStationGUI.setData(controller.getSimulation(), controller.getIsRailwayTypeAvailableFlag());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Departure Station");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of exiting to the pause menu.
     *
     * @param event The action event triggered by the user.
     */
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

    /**
     * Gets the controller for this GUI.
     *
     * @return The CreatePointOfRouteController instance.
     */
    public CreatePointOfRouteController getController() {
        return controller;
    }

    /**
     * Sets the controller for this GUI.
     *
     * @param controller The CreatePointOfRouteController instance.
     */
    public void setController(CreatePointOfRouteController controller) {
        this.controller = controller;
    }

    /**
     * Gets the error label.
     *
     * @return The error label.
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the error label.
     *
     * @param errorLabel The error label.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the graph container.
     *
     * @return The graph container.
     */
    public AnchorPane getGraphContainer() {
        return graphContainer;
    }

    /**
     * Sets the graph container.
     *
     * @param graphContainer The graph container.
     */
    public void setGraphContainer(AnchorPane graphContainer) {
        this.graphContainer = graphContainer;
    }

    /**
     * Gets the cargo mode combo box.
     *
     * @return The cargo mode combo box.
     */
    public ComboBox getCargoModeComboBox() {
        return cargoModeComboBox;
    }

    /**
     * Sets the cargo mode combo box.
     *
     * @param cargoModeComboBox The cargo mode combo box.
     */
    public void setCargoModeComboBox(ComboBox cargoModeComboBox) {
        this.cargoModeComboBox = cargoModeComboBox;
    }

    /**
     * Gets the station table view.
     *
     * @return The station table view.
     */
    public TableView<Station> getStationTableView() {
        return stationTableView;
    }

    /**
     * Sets the station table view.
     *
     * @param stationTableView The station table view.
     */
    public void setStationTableView(TableView<Station> stationTableView) {
        this.stationTableView = stationTableView;
    }

    /**
     * Gets the cargo table view.
     *
     * @return The cargo table view.
     */
    public TableView<ResourcesType> getCargoTableView() {
        return cargoTableView;
    }

    /**
     * Sets the cargo table view.
     *
     * @param cargoTableView The cargo table view.
     */
    public void setCargoTableView(TableView<ResourcesType> cargoTableView) {
        this.cargoTableView = cargoTableView;
    }

    /**
     * Gets the station name column.
     *
     * @return The station name column.
     */
    public TableColumn<Station, String> getStationNameColumn() {
        return stationNameColumn;
    }

    /**
     * Sets the station name column.
     *
     * @param stationNameColumn The station name column.
     */
    public void setStationNameColumn(TableColumn<Station, String> stationNameColumn) {
        this.stationNameColumn = stationNameColumn;
    }

    /**
     * Gets the resource name column.
     *
     * @return The resource name column.
     */
    public TableColumn<ResourcesType, String> getResourceNameColumn() {
        return resourceNameColumn;
    }

    /**
     * Sets the resource name column.
     *
     * @param resourceNameColumn The resource name column.
     */
    public void setResourceNameColumn(TableColumn<ResourcesType, String> resourceNameColumn) {
        this.resourceNameColumn = resourceNameColumn;
    }

    /**
     * Gets the create route button.
     *
     * @return The create route button.
     */
    public Button getCreateRouteButton() {
        return createRouteButton;
    }

    /**
     * Sets the create route button.
     *
     * @param createRouteButton The create route button.
     */
    public void setCreateRouteButton(Button createRouteButton) {
        this.createRouteButton = createRouteButton;
    }
}