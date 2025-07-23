package pt.ipp.isep.dei.ui.gui.simulation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.InSimulation.RunSimulationController;
import pt.ipp.isep.dei.domain.FinancialResult.YearFinancialResult;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI controller for running the simulation.
 * Handles simulation controls, chart updates, and log filtering.
 */
public class RunSimulationGUI implements Initializable {

    private RunSimulationController controller;
    private static final String EARNING_BAR_COLOR = "#00ff0f";
    private static final String EXPENSE_BAR_COLOR = "#ff0000";
    private boolean paused = false;

    @FXML
    private Label budgetLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label simulationSpeedLabel;

    @FXML
    private TextFlow simulationLogs;

    @FXML
    private AnchorPane pieChartAnchorPane, barChartAnchorPane;

    @FXML
    private HBox filtersContainer;

    private final List<CheckBox> filterCheckBoxes = new ArrayList<>();
    private PieChart pieChart;
    private BarChart<String, Number> barChart;
    private XYChart.Series<String, Number> earningsSeries;
    private XYChart.Series<String, Number> expensesSeries;

    /**
     * Initializes the GUI components and sets up charts and filters.
     *
     * @param url            the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new RunSimulationController();

        Platform.runLater(() -> {
            Stage stage = (Stage) budgetLabel.getScene().getWindow();
            stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
                if (!isNowFullScreen) {
                    stage.setWidth(1200);
                    stage.setHeight(800);
                    stage.centerOnScreen();
                }
            });
            stage.setFullScreenExitHint("Press F11 to return to full screen.");
        });

        setupCharts();
        setFilters();
    }

    /**
     * Sets the simulation instance for the controller and initializes the simulation thread.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeSimulation();
    }

    /**
     * Initializes and starts the simulation in a separate thread.
     */
    private void initializeSimulation() {
        Thread simulationThread = new Thread(this::runSimulation);
        simulationThread.setDaemon(true);
        simulationThread.start();
    }

    /**
     * Handles the pause simulation button action.
     * Pauses the simulation and loads the pause menu.
     *
     * @param event the action event
     */
    @FXML
    public void handlePauseSimulationButton(ActionEvent event) {
        paused = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/PauseMenu.fxml"));
            Parent root = loader.load();
            PauseMenuGUI pauseMenuGUI = loader.getController();
            pauseMenuGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setFullScreen(false);
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("MABEC - Simulation: Pause Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the terminate simulation button action.
     * Pauses the simulation, shows a log, loads the pause menu and save simulation window.
     *
     * @param event the action event
     */
    @FXML
    public void handleTerminateSimulationButton(ActionEvent event) {
        paused = true;
        Platform.runLater(() -> addLogLine("Simulation terminated.", Color.GRAY));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/PauseMenu.fxml"));
            Parent root = loader.load();
            PauseMenuGUI pauseMenuGUI = loader.getController();
            pauseMenuGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setFullScreen(false);
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("MABEC - Simulation: Pause Menu");
            stage.show();

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/simulation/SaveSimulation.fxml"));
            Parent root2 = loader2.load();
            SaveSimulationGUI saveSimulationGUI = loader2.getController();
            saveSimulationGUI.setInformation(controller.getSimulation(), stage);
            Stage stage2 = new Stage();
            stage2.setResizable(false);
            stage2.setTitle("MABEC - Save Simulation");
            stage2.setScene(new Scene(root2));
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the increase speed button action.
     * Increases the simulation speed and updates the label.
     *
     * @param event the action event
     */
    @FXML
    public void handleIncreaseSpeedButton(ActionEvent event) {
        controller.increaseSimulationSpeed();
        simulationSpeedLabel.setText("Speed: " + controller.getSimulationSpeed() + "x");
        Platform.runLater(() -> addLogLine("Simulation speed increased.", Color.BLUE));
    }

    /**
     * Handles the decrease speed button action.
     * Decreases the simulation speed and updates the label.
     *
     * @param event the action event
     */
    @FXML
    public void handleDecreaseSpeedButton(ActionEvent event) {
        controller.decreaseSimulationSpeed();
        simulationSpeedLabel.setText("Speed: " + controller.getSimulationSpeed() + "x");
        Platform.runLater(() -> addLogLine("Simulation speed decreased.", Color.BLUE));
    }

    /**
     * Runs the simulation loop, updating logs, charts, and handling simulation events.
     */
    private void runSimulation() {
        controller.initializeMapModifications();
        controller.refreshEvents();

        while (controller.getCurrentTime() < controller.getMaxTime()) {
            if (paused) continue;

            if (controller.getActualDate().getDay() == 1 && controller.getActualDate().getMonth() == 1) {
                createNewYearFinancialResult();
                controller.updateDemand();
                Platform.runLater(this::updateCharts);
            }

            if (!controller.getSimulation().getTrainList().isEmpty()) {
                controller.trainMaintenanceCost();
            }

            if (!controller.getSimulation().getRailwayLines().isEmpty()) {
                controller.railwayLineMaintenanceCost();
            }

            controller.checkEvents();
            controller.refreshEvents();
            controller.setAllUpdateInventoryFalse();

            Platform.runLater(() -> {
                updateSimulationLogs();
                updateCharts();
            });

            try {
                Thread.sleep((long) (2000 / controller.getSimulationSpeed()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            controller.setCurrentTime(controller.getCurrentTime() + 1);
        }

        Platform.runLater(() -> addLogLine("Simulation finished.", Color.GRAY));
    }

    /**
     * Updates the simulation logs in the UI.
     */
    private void updateSimulationLogs() {
        simulationLogs.getChildren().clear();
        for (String log : controller.getLogs()) {
            if (log.contains("Simulation Day")) {
                addLogLine(log, Color.DARKORANGE);
            } else if (log.contains("Year Financial Report")) {
                addLogLine(log, Color.FORESTGREEN);
            } else if (log.contains("Export")) {
                addLogLine(log, Color.CRIMSON);
            } else if (log.contains("Production")) {
                addLogLine(log, Color.DODGERBLUE);
            } else if (log.contains("Manufacturing")) {
                addLogLine(log, Color.DARKVIOLET);
            } else if (log.endsWith("Route Event Triggered")) {
                addLogLine(log, Color.LIGHTGREEN);
            } else if (log.endsWith("!")) {
                addLogLine(log, Color.FIREBRICK);
            } else {
                addLogLine(log, Color.BLACK);
            }
        }
        budgetLabel.setText("💰 Budget: " + controller.getActualBudget() + "€");
        dateLabel.setText("📅 Date: " + controller.getActualDate().toSimpleString());
    }

    /**
     * Adds a line to the simulation logs with the specified color.
     *
     * @param text  the log text
     * @param color the color to use
     */
    private void addLogLine(String text, Color color) {
        Text line = new Text(text + "\n");
        line.setFill(color);
        simulationLogs.getChildren().add(line);
    }

    /**
     * Sets up the pie and bar charts for financial results.
     */
    private void setupCharts() {
        pieChart = new PieChart();
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);

        pieChartAnchorPane.getChildren().add(pieChart);
        AnchorPane.setTopAnchor(pieChart, 0.0);
        AnchorPane.setBottomAnchor(pieChart, 0.0);
        AnchorPane.setLeftAnchor(pieChart, 0.0);
        AnchorPane.setRightAnchor(pieChart, 0.0);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value (€)");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setCategoryGap(30);
        barChart.setBarGap(4);
        barChart.setAnimated(false);

        earningsSeries = new XYChart.Series<>();
        earningsSeries.setName("Earnings");

        expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Total Expenses");

        barChart.getData().addAll(earningsSeries, expensesSeries);

        barChartAnchorPane.getChildren().add(barChart);
        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);
    }

    /**
     * Updates the pie and bar charts with the latest financial results.
     */
    private void updateCharts() {
        List<YearFinancialResult> results = controller.getSimulation().getFinancialResults();
        if (results.isEmpty()) return;

        YearFinancialResult latest = results.get(results.size() - 1);

        // Update PieChart
        pieChartAnchorPane.getChildren().clear();
        pieChart = new PieChart(FXCollections.observableArrayList(
                new PieChart.Data("Earnings", latest.getEarning()),
                new PieChart.Data("Track Maintenance", -latest.getTrackMaintenance()),
                new PieChart.Data("Train Maintenance", -latest.getTrainMaintenance()),
                new PieChart.Data("Fuel Cost", -latest.getFuelCost())
        ));
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        pieChartAnchorPane.getChildren().add(pieChart);
        AnchorPane.setTopAnchor(pieChart, 0.0);
        AnchorPane.setBottomAnchor(pieChart, 0.0);
        AnchorPane.setLeftAnchor(pieChart, 0.0);
        AnchorPane.setRightAnchor(pieChart, 0.0);

        earningsSeries.getData().clear();
        expensesSeries.getData().clear();

        int startIndex = Math.max(results.size() - 5, 0);
        List<YearFinancialResult> lastFiveResults = results.subList(startIndex, results.size());

        for (YearFinancialResult result : lastFiveResults) {
            String year = String.valueOf(result.getYear());
            earningsSeries.getData().add(new XYChart.Data<>(year, result.getEarning()));
            expensesSeries.getData().add(new XYChart.Data<>(year, -result.getTotalExpenses()));
        }

        Platform.runLater(() -> {
            applyBarColor(expensesSeries, EXPENSE_BAR_COLOR);
            applyBarColor(earningsSeries, EARNING_BAR_COLOR);

            // Force legend colors
            for (Node legendItem : barChart.lookupAll(".chart-legend-item")) {
                Label legendLabel = (Label) legendItem.lookup(".label");
                if (legendLabel != null) {
                    String text = legendLabel.getText();
                    Node symbol = legendItem.lookup(".chart-legend-item-symbol");
                    if ("Total Expenses".equals(text) && symbol != null) {
                        symbol.setStyle("-fx-background-color: " + EXPENSE_BAR_COLOR + ";");
                    } else if ("Earnings".equals(text) && symbol != null) {
                        symbol.setStyle("-fx-background-color: " + EARNING_BAR_COLOR + ";");
                    }
                }
            }
        });
    }

    /**
     * Applies a color to all bars in a chart series.
     *
     * @param series   the chart series
     * @param colorHex the color in hex format
     */
    private void applyBarColor(XYChart.Series<String, Number> series, String colorHex) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: " + colorHex + ";");
                }
            });
            if (data.getNode() != null) {
                data.getNode().setStyle("-fx-bar-fill: " + colorHex + ";");
            }
        }
    }

    /**
     * Creates a new year financial result and logs the previous year's report.
     */
    public void createNewYearFinancialResult() {
        controller.getSimulation().getFinancialResults().add(new YearFinancialResult(controller.getActualDate().getYear()));

        if (controller.getSimulation().getFinancialResults().size() > 1) {
            List<YearFinancialResult> yearFinancialResultList = controller.getSimulation().getFinancialResults();
            YearFinancialResult oldYear = yearFinancialResultList.get(yearFinancialResultList.size() - 2);

            controller.addLogs("========================================");
            controller.addLogs("        📊 Year Financial Report        ");
            controller.addLogs("========================================");
            controller.addLogs(" Year:               " + oldYear.getYear());
            controller.addLogs("----------------------------------------");
            controller.addLogs(" Year Earnings:      💰 " + oldYear.getEarning());
            controller.addLogs(" Track Maintenance:  💰 " + oldYear.getTrackMaintenance());
            controller.addLogs(" Train Maintenance:  💰 " + oldYear.getTrainMaintenance());
            controller.addLogs(" Fuel Cost:          💰 " + oldYear.getFuelCost());
            controller.addLogs("----------------------------------------");
            controller.addLogs(" Total Expenses:     💰 " + oldYear.getTotalExpenses());
            controller.addLogs(" Revenue Result:     💰 " + oldYear.getRevenue());
            controller.addLogs("========================================\n");
        }
    }

    /**
     * Sets up the log filters in the UI.
     */
    public void setFilters() {
        addFilter("Date Alert", Color.DARKORANGE.toString());
        addFilter("Export", Color.CRIMSON.toString());
        addFilter("Manufacturing", Color.DARKVIOLET.toString());
        addFilter("Production", Color.DODGERBLUE.toString());
        addFilter("Route", Color.LIGHTGREEN.toString());
    }

    /**
     * Adds a filter checkbox to the UI.
     *
     * @param label the filter label
     * @param color the color for the label
     */
    private void addFilter(String label, String color) {
        CheckBox checkBox = new CheckBox(label);
        checkBox.setSelected(true);
        checkBox.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 14px;");
        checkBox.setOnAction(event -> handleToggleFilter(label, checkBox.isSelected()));
        filtersContainer.getChildren().add(checkBox);
        filterCheckBoxes.add(checkBox);
    }

    /**
     * Handles toggling of log filters.
     *
     * @param label    the filter label
     * @param selected whether the filter is selected
     */
    private void handleToggleFilter(String label, boolean selected) {
        switch (label) {
            case "Date Alert":
                controller.setDateAlertLogs(selected);
                break;
            case "Export":
                controller.setExportAlertLogs(selected);
                break;
            case "Manufacturing":
                controller.setTransformingAlertLogs(selected);
                break;
            case "Production":
                controller.setGenerationAlertLogs(selected);
                break;
            case "Route":
                controller.setRouteAlertLogs(selected);
                break;
        }
    }

    /**
     * Selects all log filters.
     */
    @FXML
    private void handleSelectAllFilters() {
        for (CheckBox checkBox : filterCheckBoxes) {
            checkBox.setSelected(true);
            handleToggleFilter(checkBox.getText(), true);
        }
    }

    /**
     * Clears all log filters.
     */
    @FXML
    private void handleClearAllFilters() {
        for (CheckBox checkBox : filterCheckBoxes) {
            checkBox.setSelected(false);
            handleToggleFilter(checkBox.getText(), false);
        }
    }

    /**
     * Gets the simulation controller.
     *
     * @return the RunSimulationController
     */
    public RunSimulationController getController() {
        return controller;
    }

    /**
     * Sets the simulation controller.
     *
     * @param controller the RunSimulationController to set
     */
    public void setController(RunSimulationController controller) {
        this.controller = controller;
    }

    /**
     * Gets the paused state.
     *
     * @return true if paused, false otherwise
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the paused state.
     *
     * @param paused the paused state to set
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Gets the budget label.
     *
     * @return the budgetLabel
     */
    public Label getBudgetLabel() {
        return budgetLabel;
    }

    /**
     * Sets the budget label.
     *
     * @param budgetLabel the Label to set
     */
    public void setBudgetLabel(Label budgetLabel) {
        this.budgetLabel = budgetLabel;
    }

    /**
     * Gets the date label.
     *
     * @return the dateLabel
     */
    public Label getDateLabel() {
        return dateLabel;
    }

    /**
     * Sets the date label.
     *
     * @param dateLabel the Label to set
     */
    public void setDateLabel(Label dateLabel) {
        this.dateLabel = dateLabel;
    }

    /**
     * Gets the simulation speed label.
     *
     * @return the simulationSpeedLabel
     */
    public Label getSimulationSpeedLabel() {
        return simulationSpeedLabel;
    }

    /**
     * Sets the simulation speed label.
     *
     * @param simulationSpeedLabel the Label to set
     */
    public void setSimulationSpeedLabel(Label simulationSpeedLabel) {
        this.simulationSpeedLabel = simulationSpeedLabel;
    }

    /**
     * Gets the simulation logs TextFlow.
     *
     * @return the simulationLogs
     */
    public TextFlow getSimulationLogs() {
        return simulationLogs;
    }

    /**
     * Sets the simulation logs TextFlow.
     *
     * @param simulationLogs the TextFlow to set
     */
    public void setSimulationLogs(TextFlow simulationLogs) {
        this.simulationLogs = simulationLogs;
    }

    /**
     * Gets the pie chart anchor pane.
     *
     * @return the pieChartAnchorPane
     */
    public AnchorPane getPieChartAnchorPane() {
        return pieChartAnchorPane;
    }

    /**
     * Sets the pie chart anchor pane.
     *
     * @param pieChartAnchorPane the AnchorPane to set
     */
    public void setPieChartAnchorPane(AnchorPane pieChartAnchorPane) {
        this.pieChartAnchorPane = pieChartAnchorPane;
    }

    /**
     * Gets the bar chart anchor pane.
     *
     * @return the barChartAnchorPane
     */
    public AnchorPane getBarChartAnchorPane() {
        return barChartAnchorPane;
    }

    /**
     * Sets the bar chart anchor pane.
     *
     * @param barChartAnchorPane the AnchorPane to set
     */
    public void setBarChartAnchorPane(AnchorPane barChartAnchorPane) {
        this.barChartAnchorPane = barChartAnchorPane;
    }

    /**
     * Gets the filters container HBox.
     *
     * @return the filtersContainer
     */
    public HBox getFiltersContainer() {
        return filtersContainer;
    }

    /**
     * Sets the filters container HBox.
     *
     * @param filtersContainer the HBox to set
     */
    public void setFiltersContainer(HBox filtersContainer) {
        this.filtersContainer = filtersContainer;
    }

    /**
     * Gets the list of filter checkboxes.
     *
     * @return the filterCheckBoxes
     */
    public List<CheckBox> getFilterCheckBoxes() {
        return filterCheckBoxes;
    }

    /**
     * Gets the pie chart.
     *
     * @return the pieChart
     */
    public PieChart getPieChart() {
        return pieChart;
    }

    /**
     * Sets the pie chart.
     *
     * @param pieChart the PieChart to set
     */
    public void setPieChart(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    /**
     * Gets the bar chart.
     *
     * @return the barChart
     */
    public BarChart<String, Number> getBarChart() {
        return barChart;
    }

    /**
     * Sets the bar chart.
     *
     * @param barChart the BarChart to set
     */
    public void setBarChart(BarChart<String, Number> barChart) {
        this.barChart = barChart;
    }

    /**
     * Gets the earnings series.
     *
     * @return the earningsSeries
     */
    public XYChart.Series<String, Number> getEarningsSeries() {
        return earningsSeries;
    }

    /**
     * Sets the earnings series.
     *
     * @param earningsSeries the Series to set
     */
    public void setEarningsSeries(XYChart.Series<String, Number> earningsSeries) {
        this.earningsSeries = earningsSeries;
    }

    /**
     * Gets the expenses series.
     *
     * @return the expensesSeries
     */
    public XYChart.Series<String, Number> getExpensesSeries() {
        return expensesSeries;
    }

    /**
     * Sets the expenses series.
     *
     * @param expensesSeries the Series to set
     */
    public void setExpensesSeries(XYChart.Series<String, Number> expensesSeries) {
        this.expensesSeries = expensesSeries;
    }
}