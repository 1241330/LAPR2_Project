package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.ManageRoute;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.ChooseRouteController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.PointOfRoute;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.RouteRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GUI controller for choosing and managing routes in the simulation.
 * <p>
 * Allows users to view available routes, visualize them graphically,
 * activate/deactivate routes, and navigate between related simulation screens.
 * </p>
 * <p>
 * Interacts with {@link ChooseRouteController} to retrieve simulation data
 * and execute route-related actions.
 * </p>
 */
public class ChooseRouteGUI implements Initializable {

    /**
     * Controller responsible for handling route-related logic.
     */
    private ChooseRouteController controller;

    /**
     * Map of stations to their graphical representations (Circle nodes) in the graph.
     */
    private final Map<Station, Circle> stationCircles = new HashMap<>();

    /**
     * Label for displaying errors or warning messages to the user.
     */
    @FXML
    private Label errorLabel;

    /**
     * TableView displaying available routes for selection.
     */
    @FXML
    private transient TableView<Route> routeTableView;

    /**
     * Column for displaying route names in the TableView.
     */
    @FXML
    private transient TableColumn<Route, String> routeNameColumn;

    /**
     * Column for displaying the active/inactive status of each route in the TableView.
     */
    @FXML
    private transient TableColumn<Route, String> routeStatusColumn;

    /**
     * AnchorPane used to graphically represent the route and its stations.
     */
    @FXML
    private AnchorPane graphContainer;

    /**
     * Initializes the GUI controller. Called automatically after the FXML has been loaded.
     *
     * @param url            URL to the FXML file.
     * @param resourceBundle Resource bundle for localization (unused).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseRouteController();
    }

    /**
     * Sets the simulation data for this screen and initializes the graphical elements.
     *
     * @param simulation The simulation to display and manage routes for.
     */
    public void setData(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes TableView columns, listeners, and data population for the GUI.
     * Also disables the horizontal scrollbar for improved UI aesthetics.
     */
    private void initializeGUIItems() {
        routeNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        routeStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getActiveFlagOnString()));

        Platform.runLater(() -> {
            routeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : routeTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                }
            }
        });

        routeTableView.setItems(controller.getRoutes());

        routeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateGraphForStation(newSelection);
            }
        });
    }

    /**
     * Updates the graphical representation of the selected route.
     *
     * @param newSelection The route selected by the user.
     */
    private void updateGraphForStation(Route newSelection) {
        drawRouteGraph(newSelection);
    }

    /**
     * Draws the graphical representation of a route, including stations, railway lines, departure markers, and numbered positions.
     *
     * @param selectedRoute The route to be drawn.
     */
    private void drawRouteGraph(Route selectedRoute) {
        if (graphContainer.getWidth() == 0 || graphContainer.getHeight() == 0) {
            return;
        }

        Platform.runLater(() -> {
            graphContainer.getChildren().clear();

            List<PointOfRoute> pointOfRouteList = selectedRoute.getPointOfRouteList();
            List<RailwayLine> railwayLineList = selectedRoute.getPath();

            double paneWidth = graphContainer.getWidth();
            double paneHeight = graphContainer.getHeight();

            stationCircles.clear();
            Map<Station, Point2D> stationPositions = new HashMap<>();

            Set<Station> routeStations = pointOfRouteList.stream()
                    .map(PointOfRoute::getStation)
                    .collect(Collectors.toSet());

            Set<Station> allStationsInRoute = new HashSet<>();
            for (RailwayLine line : railwayLineList) {
                allStationsInRoute.add(line.getStation1());
                allStationsInRoute.add(line.getStation2());
            }

            // Draw stations
            for (Station station : allStationsInRoute) {
                double[] scaledPos = controller.findScaledPositions(station, paneWidth, paneHeight);
                Point2D pos = new Point2D(scaledPos[0], scaledPos[1]);
                stationPositions.put(station, pos);

                boolean isMainStation = routeStations.contains(station);
                int size = isMainStation ? controller.getStationSize(station) : 3;
                String color = isMainStation ? controller.getStationColor(station) : "#888888";

                Circle circle = new Circle(pos.getX(), pos.getY(), size);
                circle.setStyle("-fx-fill: " + color + "; -fx-stroke: black;");
                stationCircles.put(station, circle);
                graphContainer.getChildren().add(circle);

                int labelOffset = controller.getLabelDistance(size);
                Text label = new Text(pos.getX() - 30, pos.getY() + labelOffset, station.getName());
                if (!isMainStation) {
                    label.setStyle("-fx-font-size: 8px; -fx-fill: #555555;");
                }
                graphContainer.getChildren().add(label);
            }

            // Draw departure station star
            if (!pointOfRouteList.isEmpty()) {
                Station departureStation = pointOfRouteList.get(0).getStation();
                Point2D pos = stationPositions.get(departureStation);
                if (pos != null) {
                    Polygon star = new Polygon();
                    double centerX = pos.getX();
                    double centerY = pos.getY();
                    double radius = 10;
                    for (int i = 0; i < 10; i++) {
                        double angle = Math.toRadians(i * 36 - 90);
                        double r = (i % 2 == 0) ? radius : radius / 2.5;
                        double x = centerX + r * Math.cos(angle);
                        double y = centerY + r * Math.sin(angle);
                        star.getPoints().addAll(x, y);
                    }
                    star.setStyle("-fx-fill: gold; -fx-stroke: black;");
                    graphContainer.getChildren().add(star);
                }
            }

            // Draw numbered diamonds for intermediate stations
            for (int i = 1; i < pointOfRouteList.size(); i++) {
                Station station = pointOfRouteList.get(i).getStation();
                Point2D pos = stationPositions.get(station);
                if (pos != null) {
                    double cx = pos.getX();
                    double cy = pos.getY();
                    double r = 8;

                    Polygon diamond = new Polygon(
                            cx, cy - r,
                            cx + r, cy,
                            cx, cy + r,
                            cx - r, cy
                    );
                    diamond.setStyle("-fx-fill: white; -fx-stroke: black;");

                    Text number = new Text(String.valueOf(i + 1));
                    number.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: black;");
                    number.setTextOrigin(VPos.CENTER);
                    number.setBoundsType(TextBoundsType.VISUAL);

                    Bounds textBounds = number.getLayoutBounds();
                    number.setX(cx - textBounds.getWidth() / 2);
                    number.setY(cy);

                    graphContainer.getChildren().addAll(diamond, number);
                }
            }

            // Draw railway lines
            for (RailwayLine line : railwayLineList) {
                Station a = line.getStation1();
                Station b = line.getStation2();

                Point2D pointA = stationPositions.get(a);
                Point2D pointB = stationPositions.get(b);

                if (pointA != null && pointB != null) {
                    Line connection = new Line(pointA.getX(), pointA.getY(), pointB.getX(), pointB.getY());

                    String color = controller.getRailwayLineColor(line);
                    int width = controller.getRailwayLineSize(line);

                    connection.setStyle("-fx-stroke: " + color + "; -fx-stroke-width: " + width + ";");

                    graphContainer.getChildren().add(0, connection);
                }
            }
        });
    }

    /**
     * Handles the "Select" button click event.
     * <p>
     * If the selected route is already active, opens the deactivation confirmation popup.
     * Otherwise, proceeds to the train selection screen to assign a train to the route.
     * </p>
     *
     * @param event The event triggered by the button click.
     */
    public void handleSelectButtonOnAction(ActionEvent event) {
        if (routeTableView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Select a route first!");
        }

        if (routeTableView.getSelectionModel().getSelectedItem().getActiveFlag()) {
            deactivatePopMenu(event);
            return;
        }

        activateMenu(event);
    }

    /**
     * Opens the screen to select a train for the selected route.
     *
     * @param event The event triggered by the button click.
     */
    private void activateMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/MenageRoute/ChooseTrainForRoute.fxml"));
            Parent root = loader.load();

            ChooseTrainForRouteGUI chooseTrainForRouteGUI = loader.getController();
            chooseTrainForRouteGUI.setData(controller.getSimulation(), routeTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Assign train to route");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the confirmation popup to deactivate the currently selected route.
     *
     * @param event The event triggered by the button click.
     */
    private void deactivatePopMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/PurchaseConfirmation2.fxml"));
            DeactivateRoutePOPGUI deactivateRoutePOPGUI = new DeactivateRoutePOPGUI();
            loader.setController(deactivateRoutePOPGUI);

            Parent root = loader.load();

            DeactivateRoutePOPGUI deactivateRoutePOPGUI1 = loader.getController();
            deactivateRoutePOPGUI1.setData(controller.getSimulation(), routeTableView.getSelectionModel().getSelectedItem(), (Stage) ((Node) event.getSource()).getScene().getWindow());
            deactivateRoutePOPGUI1.initializeGUIItems("Deactivate Route",
                    "The train " + controller.getAssignedTrain(routeTableView.getSelectionModel().getSelectedItem()) + " will be deactivated",
                    "This route will be deactivated till you assign a train");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Deactivate Route Confirmation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Back" button click event, navigating back to the Route Related menu.
     *
     * @param event The event triggered by the button click.
     */
    public void handleBackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/RouteRelated.fxml"));
            Parent root = loader.load();

            RouteRelatedGUI routeRelatedGUI = loader.getController();
            routeRelatedGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Route Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Exit" button click event, navigating back to the simulation's pause menu.
     *
     * @param event The event triggered by the button click.
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
}
