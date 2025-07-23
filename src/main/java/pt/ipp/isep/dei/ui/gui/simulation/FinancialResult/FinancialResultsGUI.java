package pt.ipp.isep.dei.ui.gui.simulation.FinancialResult;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.FinancialResultsController;
import pt.ipp.isep.dei.domain.FinancialResult.YearFinancialResult;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for displaying and interacting with financial results of a simulation.
 * Handles the initialization of charts, tables, and labels for financial data visualization.
 */
public class FinancialResultsGUI implements Initializable {

    /**
     * Controller responsible for business logic related to financial results.
     */
    private FinancialResultsController controller;

    /**
     * AnchorPane container for the pie chart and histogram.
     */
    @FXML
    private AnchorPane graphContainer, histogramAnchorPane;

    /**
     * TableView displaying yearly financial results.
     */
    @FXML
    private TableView<YearFinancialResult> financialResultTableView;

    /**
     * TableColumn for displaying the year in the financial results table.
     */
    @FXML
    private TableColumn<YearFinancialResult, String> yearResultTableView;

    /**
     * Label for displaying earnings.
     */
    @FXML
    private Label earningLabel;

    /**
     * Label for displaying track maintenance costs.
     */
    @FXML
    private Label trackMaintenanceLabel;

    /**
     * Label for displaying train maintenance costs.
     */
    @FXML
    private Label trainMaintenanceLabel;

    /**
     * Label for displaying fuel costs.
     */
    @FXML
    private Label fuelCostLabel;

    /**
     * Label for displaying total expenses.
     */
    @FXML
    private Label totalExpensesLabel;

    /**
     * Label for displaying revenue results.
     */
    @FXML
    private Label revenueResultLabel;

    /**
     * PieChart for visualizing the financial breakdown.
     */
    private PieChart pieChart;

    /**
     * Initializes the GUI components and sets up the pie chart.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new FinancialResultsController();

        // Initialize the pie chart and add it to the container
        pieChart = new PieChart();
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);

        graphContainer.getChildren().add(pieChart);
        AnchorPane.setTopAnchor(pieChart, 0.0);
        AnchorPane.setBottomAnchor(pieChart, 0.0);
        AnchorPane.setLeftAnchor(pieChart, 0.0);
        AnchorPane.setRightAnchor(pieChart, 0.0);
    }

    /**
     * Sets the simulation and initializes the GUI items with its data.
     *
     * @param simulation The simulation whose financial results are to be displayed.
     */
    public void setData(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the table, listeners, and histogram for the financial results.
     */
    private void initializeGUIItems() {
        yearResultTableView.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear() + ""));

        Platform.runLater(() -> {
            financialResultTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : financialResultTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                }
            }
        });

        financialResultTableView.setItems(controller.getFinancialResults());

        financialResultTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateInfo(newSelection);
            }
        });

        createHistogram();
    }

    /**
     * Updates the labels and pie chart with the selected year's financial data.
     *
     * @param newSelection The selected YearFinancialResult.
     */
    private void updateInfo(YearFinancialResult newSelection) {
        earningLabel.setText(newSelection.getEarning() + " \uD83D\uDCB0");
        earningLabel.setStyle("-fx-text-fill: black;");
        trackMaintenanceLabel.setText(newSelection.getTrackMaintenance() + " \uD83D\uDCB0");
        trackMaintenanceLabel.setStyle("-fx-text-fill: black;");
        trainMaintenanceLabel.setText(newSelection.getTrainMaintenance() + " \uD83D\uDCB0");
        trainMaintenanceLabel.setStyle("-fx-text-fill: black;");
        fuelCostLabel.setText(newSelection.getFuelCost() + " \uD83D\uDCB0");
        fuelCostLabel.setStyle("-fx-text-fill: black;");
        totalExpensesLabel.setText(newSelection.getTotalExpenses() + " \uD83D\uDCB0");
        totalExpensesLabel.setStyle("-fx-text-fill: black;");
        revenueResultLabel.setText(newSelection.getRevenue() + " \uD83D\uDCB0");
        revenueResultLabel.setStyle("-fx-text-fill: black;");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Earnings", newSelection.getEarning()),
                new PieChart.Data("Track Maintenance", newSelection.getTrackMaintenance() * -1),
                new PieChart.Data("Train Maintenance", newSelection.getTrainMaintenance() * -1),
                new PieChart.Data("Fuel Cost", newSelection.getFuelCost() * -1)
        );
        pieChart.setData(pieChartData);

        updateHistogramAroundYear(newSelection.getYear());
    }

    /**
     * Updates the histogram to show up to 5 years around the selected year.
     *
     * @param selectedYear The year selected in the table.
     */
    private void updateHistogramAroundYear(int selectedYear) {
        ObservableList<YearFinancialResult> allResults = controller.getFinancialResults().sorted(
                (a, b) -> Integer.compare(a.getYear(), b.getYear())
        );

        int selectedIndex = -1;
        for (int i = 0; i < allResults.size(); i++) {
            if (allResults.get(i).getYear() == selectedYear) {
                selectedIndex = i;
                break;
            }
        }

        if (selectedIndex == -1) return;

        ObservableList<YearFinancialResult> visibleResults = FXCollections.observableArrayList();

        // Add up to 4 previous years
        for (int i = selectedIndex - 4; i < selectedIndex; i++) {
            if (i >= 0) {
                visibleResults.add(allResults.get(i));
            }
        }

        // Add the selected year
        visibleResults.add(allResults.get(selectedIndex));

        // Add next years if less than 5
        for (int i = selectedIndex + 1; visibleResults.size() < 5 && i < allResults.size(); i++) {
            visibleResults.add(allResults.get(i));
        }

        // Build the chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value (\uD83D\uDCB0)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setCategoryGap(30);
        barChart.setBarGap(4);

        XYChart.Series<String, Number> totalExpensesSeries = new XYChart.Series<>();
        totalExpensesSeries.setName("Total Expenses");

        XYChart.Series<String, Number> earningsSeries = new XYChart.Series<>();
        earningsSeries.setName("Earnings");

        for (YearFinancialResult result : visibleResults) {
            String year = String.valueOf(result.getYear());
            totalExpensesSeries.getData().add(new XYChart.Data<>(year, -result.getTotalExpenses()));
            earningsSeries.getData().add(new XYChart.Data<>(year, result.getEarning()));
        }

        barChart.getData().addAll(totalExpensesSeries, earningsSeries);

        histogramAnchorPane.getChildren().clear();
        histogramAnchorPane.getChildren().add(barChart);
        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);

        Platform.runLater(() -> {
            applyBarColor(totalExpensesSeries, "#ff0000");
            applyBarColor(earningsSeries, "#00ff0f");
        });
    }

    /**
     * Creates and displays a histogram (bar chart) for earnings and expenses over the years.
     */
    private void createHistogram() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value (\uD83D\uDCB0)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setCategoryGap(30);
        barChart.setBarGap(4);

        XYChart.Series<String, Number> totalExpensesSeries = new XYChart.Series<>();
        totalExpensesSeries.setName("Total Expenses");

        XYChart.Series<String, Number> earningsSeries = new XYChart.Series<>();
        earningsSeries.setName("Earnings");

        ObservableList<YearFinancialResult> allResults = controller.getFinancialResults().sorted(
                (a, b) -> Integer.compare(a.getYear(), b.getYear())
        );

        int size = allResults.size();
        ObservableList<YearFinancialResult> lastFiveResults = FXCollections.observableArrayList(
                allResults.subList(Math.max(size - 5, 0), size)
        );

        for (YearFinancialResult result : lastFiveResults) {
            String year = String.valueOf(result.getYear());
            totalExpensesSeries.getData().add(new XYChart.Data<>(year, -result.getTotalExpenses()));
            earningsSeries.getData().add(new XYChart.Data<>(year, result.getEarning()));
        }

        barChart.getData().addAll(totalExpensesSeries, earningsSeries);

        histogramAnchorPane.getChildren().clear();
        histogramAnchorPane.getChildren().add(barChart);
        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);

        Platform.runLater(() -> {
            applyBarColor(totalExpensesSeries, "#ff0000");
            applyBarColor(earningsSeries, "#00ff0f");
        });
    }

    /**
     * Applies a color to the bars of a given series in the bar chart.
     *
     * @param series   The data series to color.
     * @param colorHex The color in hexadecimal format.
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
     * Handles the action of the exit button, returning to the pause menu.
     *
     * @param event The ActionEvent triggered by clicking the exit button.
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