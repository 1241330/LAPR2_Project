package pt.ipp.isep.dei.controller.map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.ipp.isep.dei.domain.City.City;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.Map.MapElement;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.ui.gui.houseBlock.ShowHouseBlockDetailsInMapGUI;
import pt.ipp.isep.dei.ui.gui.industry.ShowMixedIndustryDetailsInMapGUI;
import pt.ipp.isep.dei.ui.gui.industry.ShowPrimaryIndustryDetailsInMapGUI;
import pt.ipp.isep.dei.ui.gui.industry.ShowTransformingIndustryDetailsInMapGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.ShowRailwayLine.ShowRailwayLineDetailsInMapGUI;
import pt.ipp.isep.dei.ui.gui.simulation.StationRelated.ShowStation.ShowStationDetailsInMapGUI;

import java.io.IOException;

/**
 * Controller responsible for displaying the map in the GUI.
 * Handles the rendering of map elements and the grid layout.
 */
public class ShowMapForGUIController {

    private static final int CELL_SIZE = 40;
    private Simulation simulation;

    // Images for map elements
    private final Image cityImg = new Image(getClass().getResourceAsStream("/images/city.png"));
    private final Image stationDepotImg = new Image(getClass().getResourceAsStream("/images/Stations/Station_depot.png"));
    private final Image stationStationImg = new Image(getClass().getResourceAsStream("/images/Stations/Station_station.png"));
    private final Image stationTerminalImg = new Image(getClass().getResourceAsStream("/images/Stations/Station_terminal.png"));
    private final Image industryImg = new Image(getClass().getResourceAsStream("/images/industry.png"));
    private final Image railwayImgSne = new Image(getClass().getResourceAsStream("/images/RailwayLines/SingleNonElectrifiedLine.png"));
    private final Image railwayImgSe = new Image(getClass().getResourceAsStream("/images/RailwayLines/SingleElectrifiedLine.png"));
    private final Image railwayImgDne = new Image(getClass().getResourceAsStream("/images/RailwayLines/DoubleNonElectrifiedLine.png"));
    private final Image railwayImgDe = new Image(getClass().getResourceAsStream("/images/RailwayLines/DoubleElectrifiedLine.png"));

    /**
     * Default constructor.
     */
    public ShowMapForGUIController() {}

    /**
     * Sets the simulation instance to be used by this controller.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the current simulation instance.
     *
     * @return the current simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Renders the map on the provided GridPane, including grid labels and all map elements.
     *
     * @param mapGrid the GridPane where the map will be displayed
     * @param actualPositionLabel the label to show the current mouse position
     */
    public void setMap(GridPane mapGrid, Label actualPositionLabel) {
        Map map = simulation.getScenario().getMap();
        mapGrid.getChildren().clear();

        // Set background and border style for the grid
        mapGrid.setStyle("-fx-background-color: white; "
                + "-fx-border-color: #8C7853;"
                + "-fx-border-width: 4;");

        // Remove gaps for perfect alignment
        mapGrid.setHgap(0);
        mapGrid.setVgap(0);

        int rows = map.getPixelSize().getHeight();
        int cols = map.getPixelSize().getWidth();

        StackPane[][] cells = new StackPane[rows][cols];

        // Chessboard colors
        String lightBrown = "#F5DEB3";
        String mediumBrown = "#E4C79D";
        String borderColor = "#C2A177";

        // Add column labels (top), starting at 1
        for (int col = 0; col < cols; col++) {
            Label label = new Label(String.valueOf(col + 1));
            label.setPrefSize(CELL_SIZE, CELL_SIZE);
            label.setMinSize(CELL_SIZE, CELL_SIZE);
            label.setMaxSize(CELL_SIZE, CELL_SIZE);
            label.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
            mapGrid.add(label, col + 1, 0);
        }

        // Add row labels (left), starting at 1
        for (int row = 0; row < rows; row++) {
            Label label = new Label(String.valueOf(row + 1));
            label.setPrefSize(CELL_SIZE, CELL_SIZE);
            label.setMinSize(CELL_SIZE, CELL_SIZE);
            label.setMaxSize(CELL_SIZE, CELL_SIZE);
            label.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
            mapGrid.add(label, 0, row + 1);
        }

        // Create cells with a soft chessboard pattern and mouse event handlers
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                cell.setMinSize(CELL_SIZE, CELL_SIZE);
                cell.setMaxSize(CELL_SIZE, CELL_SIZE);

                if ((i + j) % 2 == 0) {
                    cell.setStyle("-fx-background-color: " + lightBrown + "; -fx-border-color: " + borderColor + "; -fx-border-width: 1;");
                } else {
                    cell.setStyle("-fx-background-color: " + mediumBrown + "; -fx-border-color: " + borderColor + "; -fx-border-width: 1;");
                }

                int finalRow = i;
                int finalCol = j;
                cell.setOnMouseEntered(event -> handleMouseHover(finalRow, finalCol, cell, actualPositionLabel));
                cell.setOnMouseClicked(event -> handleMouseClick(map, mapGrid, finalRow, finalCol));
                cell.setOnMouseExited(event -> resetCellStyle(finalRow, finalCol, cell));

                cells[i][j] = cell;
                mapGrid.add(cell, j + 1, i + 1);
            }
        }

        // Add images for map elements
        for (MapElement element : map.getMapElementsUsed()) {
            Image image = null;

            if (element instanceof City) {
                image = cityImg;
            } else if (element instanceof Station st) {
                switch (st.getStationType()) {
                    case "DEPOT" -> image = stationDepotImg;
                    case "STATION" -> image = stationStationImg;
                    case "TERMINAL" -> image = stationTerminalImg;
                }
            } else if (element instanceof pt.ipp.isep.dei.domain.Industry.Industry) {
                image = industryImg;
            } else if (element instanceof RailwayLine rl) {
                switch (rl.getRailwayType()) {
                    case SINGLE_NON_ELECTRIFIED -> image = railwayImgSne;
                    case SINGLE_ELECTRIFIED -> image = railwayImgSe;
                    case DOUBLE_NON_ELECTRIFIED -> image = railwayImgDne;
                    case DOUBLE_ELECTRIFIED -> image = railwayImgDe;
                }
            }

            if (image != null) {
                for (Position pos : element.getOccupiedPositions()) {
                    int y = pos.getY();
                    int x = pos.getX();
                    if (y >= 0 && y < rows && x >= 0 && x < cols) {
                        ImageView img = new ImageView(image);
                        img.setFitWidth(CELL_SIZE);
                        img.setFitHeight(CELL_SIZE);
                        cells[y][x].getChildren().add(img);
                    }
                }
            }
        }
    }

    /**
     * Handles mouse click events on the map grid.
     * Opens the appropriate details window for the clicked map element.
     *
     * @param map the map instance
     * @param mapGrid the GridPane of the map
     * @param finalRow the row index of the clicked cell
     * @param finalCol the column index of the clicked cell
     */
    private void handleMouseClick(Map map, GridPane mapGrid, int finalRow, int finalCol) {
        for (MapElement element : map.getMapElementsUsed()) {
            Position position = element.getPosition();
            if (position.getX() == finalCol && position.getY() == finalRow) {
                if (element instanceof PrimaryIndustry primaryIndustryMap) {
                    PrimaryIndustry primaryIndustryScenario = primaryIndustryMap.getClonedPrimaryIndustry(simulation.getScenario());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/industry/ShowPrimaryIndustryDetailsInMap.fxml"));
                        Parent root = loader.load();
                        ShowPrimaryIndustryDetailsInMapGUI showPrimaryIndustryDetailsInMapGUI = loader.getController();
                        showPrimaryIndustryDetailsInMapGUI.setIndustry(primaryIndustryMap, primaryIndustryScenario);
                        Stage stage = new Stage();
                        stage.setAlwaysOnTop(true);
                        stage.initOwner(mapGrid.getScene().getWindow());
                        stage.initModality(Modality.NONE);
                        stage.setResizable(false);
                        stage.setTitle("MABEC - Primary Industry Details");
                        stage.setScene(new Scene(root));
                        stage.show();
                        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(20));
                        delay.setOnFinished(event1 -> stage.close());
                        delay.play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (element instanceof TransformingIndustry transformingIndustryMap) {
                    TransformingIndustry transformingIndustryScenario = transformingIndustryMap.getClonedTransformingIndustry(simulation.getScenario());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/industry/ShowTransformingIndustryDetailsInMap.fxml"));
                        Parent root = loader.load();
                        ShowTransformingIndustryDetailsInMapGUI showTransformingIndustryDetailsInMapGUI = loader.getController();
                        showTransformingIndustryDetailsInMapGUI.setIndustry(transformingIndustryMap, transformingIndustryScenario);
                        Stage stage = new Stage();
                        stage.setAlwaysOnTop(true);
                        stage.initOwner(mapGrid.getScene().getWindow());
                        stage.initModality(Modality.NONE);
                        stage.setResizable(false);
                        stage.setTitle("MABEC - Transforming Industry Details");
                        stage.setScene(new Scene(root));
                        stage.show();
                        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(20));
                        delay.setOnFinished(event1 -> stage.close());
                        delay.play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (element instanceof MixedIndustry mixedIndustryMap) {
                    MixedIndustry mixedIndustryScenario = mixedIndustryMap.getClonedMixedIndustry(simulation.getScenario());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/industry/ShowMixedIndustryDetailsInMap.fxml"));
                        Parent root = loader.load();
                        ShowMixedIndustryDetailsInMapGUI showMixedIndustryDetailsInMapGUI = loader.getController();
                        showMixedIndustryDetailsInMapGUI.setIndustry(mixedIndustryMap, mixedIndustryScenario);
                        Stage stage = new Stage();
                        stage.setAlwaysOnTop(true);
                        stage.initOwner(mapGrid.getScene().getWindow());
                        stage.initModality(Modality.NONE);
                        stage.setResizable(false);
                        stage.setTitle("MABEC - Mixed Industry Details");
                        stage.setScene(new Scene(root));
                        stage.show();
                        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(20));
                        delay.setOnFinished(event1 -> stage.close());
                        delay.play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (element instanceof Station) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stationRelated/ShowStation/ShowStationDetailsInMap.fxml"));
                        Parent root = loader.load();
                        ShowStationDetailsInMapGUI showStationDetailsInMapGUI = loader.getController();
                        showStationDetailsInMapGUI.setStation((Station) element, simulation.getScenario());
                        Stage stage = new Stage();
                        stage.setAlwaysOnTop(true);
                        stage.initOwner(mapGrid.getScene().getWindow());
                        stage.initModality(Modality.NONE);
                        stage.setResizable(false);
                        stage.setTitle("MABEC - Station Details");
                        stage.setScene(new Scene(root));
                        stage.show();
                        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(20));
                        delay.setOnFinished(event1 -> stage.close());
                        delay.play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (element instanceof RailwayLine railwayLine) {
                for (Position position1 : railwayLine.getPositionsRailwayLine()) {
                    if (position1.getX() == finalCol && position1.getY() == finalRow) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/ShowRailwayLine/ShowRailwayLineDetailsInMap.fxml"));
                            Parent root = loader.load();
                            ShowRailwayLineDetailsInMapGUI showRailwayLineDetailsInMapGUI = loader.getController();
                            showRailwayLineDetailsInMapGUI.setRailwayLine(railwayLine, simulation);
                            Stage stage = new Stage();
                            stage.setAlwaysOnTop(true);
                            stage.initOwner(mapGrid.getScene().getWindow());
                            stage.initModality(Modality.NONE);
                            stage.setResizable(false);
                            stage.setTitle("MABEC - Railway Line Details");
                            stage.setScene(new Scene(root));
                            stage.show();
                            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(20));
                            delay.setOnFinished(event1 -> stage.close());
                            delay.play();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (element instanceof City) {
                for (HouseBlock houseBlock : ((City) element).getHouseBlocks()) {
                    if (houseBlock.getPosition().getX() == finalCol && houseBlock.getPosition().getY() == finalRow) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/houseBlock/ShowHouseBlockDetailsInMap.fxml"));
                            Parent root = loader.load();
                            ShowHouseBlockDetailsInMapGUI showHouseBlockDetailsInMapGUI = loader.getController();
                            showHouseBlockDetailsInMapGUI.setHouseBlock(houseBlock, simulation.getScenario());
                            Stage stage = new Stage();
                            stage.setAlwaysOnTop(true);
                            stage.initOwner(mapGrid.getScene().getWindow());
                            stage.initModality(Modality.NONE);
                            stage.setResizable(false);
                            stage.setTitle("MABEC - House Block Details");
                            stage.setScene(new Scene(root));
                            stage.show();
                            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(20));
                            delay.setOnFinished(event1 -> stage.close());
                            delay.play();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles mouse hover events on the map grid.
     * Highlights the cell and updates the position label.
     *
     * @param row the row index
     * @param col the column index
     * @param cell the StackPane cell
     * @param actualPositionLabel the label to update with the position
     */
    private void handleMouseHover(int row, int col, StackPane cell, Label actualPositionLabel) {
        cell.setStyle(cell.getStyle() + "; -fx-border-color: grey; -fx-border-width: 2;");
        actualPositionLabel.setText("Position: (" + (col + 1) + ", " + (row + 1) + ")");
    }

    /**
     * Resets the style of a cell to its original chessboard color.
     *
     * @param row the row index
     * @param col the column index
     * @param cell the StackPane cell
     */
    private void resetCellStyle(int row, int col, StackPane cell) {
        String lightBrown = "#F5DEB3";
        String mediumBrown = "#E4C79D";
        String borderColor = "#C2A177";
        String baseColor = ((row + col) % 2 == 0) ? lightBrown : mediumBrown;
        cell.setStyle("-fx-background-color: " + baseColor + "; -fx-border-color: " + borderColor + "; -fx-border-width: 1;");
    }
}