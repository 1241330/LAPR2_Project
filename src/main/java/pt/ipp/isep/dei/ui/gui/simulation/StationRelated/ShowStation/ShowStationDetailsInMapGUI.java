package pt.ipp.isep.dei.ui.gui.simulation.StationRelated.ShowStation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.StationRelated.ShowStation.ShowStationDetailsInMapController;
import pt.ipp.isep.dei.domain.FinancialResult.Demand;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for displaying the details of a station on the map.
 * Shows station information, associations, requested resources, and a demand bar chart.
 */
public class ShowStationDetailsInMapGUI implements Initializable {

    private ShowStationDetailsInMapController controller;

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private ListView<String> associationsListView;

    @FXML
    private ListView<String> resourcesRequestedListView;

    @FXML
    private AnchorPane graphContainer;

    /**
     * Initializes the controller for showing station details in the map.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowStationDetailsInMapController();
    }

    /**
     * Sets the station and scenario to be displayed.
     * Initializes the GUI items with the station's data.
     *
     * @param station The station to display.
     * @param scenario The scenario context.
     */
    public void setStation(Station station, Scenario scenario) {
        controller.setStation(station);
        controller.setScenario(scenario);
        initializeGUIItems();
    }

    /**
     * Initializes the GUI components with the station's information.
     * Populates labels, lists, and the demand bar chart.
     */
    private void initializeGUIItems() {
        nameLabel.setText("Name: " + controller.getStationName());
        typeLabel.setText("Type: " + controller.getStationType());
        positionLabel.setText("Position: " + controller.getStationPosition());

        associationsListView.getItems().clear();
        associationsListView.getItems().addAll(controller.getAssociations());
        associationsListView.setSelectionModel(null);

        resourcesRequestedListView.getItems().clear();
        resourcesRequestedListView.getItems().addAll(controller.getResourcesRequested());
        resourcesRequestedListView.getSelectionModel().clearSelection();

        // Create BarChart for demand
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setCategoryGap(20);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Demand");

        for (Demand demand : controller.getStation().getDemandList()) {
            String resourceType = demand.getResourcesType().getName();
            int demandGrade = demand.getDemandGrade();
            series.getData().add(new XYChart.Data<>(resourceType, demandGrade));
        }

        barChart.getData().add(series);

        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
        scrollPane.setContent(barChart);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(false);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.ALWAYS);

        barChart.setPrefWidth(Math.max(800, series.getData().size() * 60));

        graphContainer.getChildren().clear();
        graphContainer.getChildren().add(scrollPane);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
    }

    /**
     * Handles the close button action, closing the current window.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    private void handleClose(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Gets the controller for showing station details in the map.
     *
     * @return The ShowStationDetailsInMapController instance.
     */
    public ShowStationDetailsInMapController getController() {
        return controller;
    }

    /**
     * Sets the controller for showing station details in the map.
     *
     * @param controller The ShowStationDetailsInMapController to set.
     */
    public void setController(ShowStationDetailsInMapController controller) {
        this.controller = controller;
    }

    /**
     * Gets the name label.
     *
     * @return The name label.
     */
    public Label getNameLabel() {
        return nameLabel;
    }

    /**
     * Sets the name label.
     *
     * @param nameLabel The name label to set.
     */
    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    /**
     * Gets the type label.
     *
     * @return The type label.
     */
    public Label getTypeLabel() {
        return typeLabel;
    }

    /**
     * Sets the type label.
     *
     * @param typeLabel The type label to set.
     */
    public void setTypeLabel(Label typeLabel) {
        this.typeLabel = typeLabel;
    }

    /**
     * Gets the position label.
     *
     * @return The position label.
     */
    public Label getPositionLabel() {
        return positionLabel;
    }

    /**
     * Sets the position label.
     *
     * @param positionLabel The position label to set.
     */
    public void setPositionLabel(Label positionLabel) {
        this.positionLabel = positionLabel;
    }

    /**
     * Gets the associations list view.
     *
     * @return The associations list view.
     */
    public ListView<String> getAssociationsListView() {
        return associationsListView;
    }

    /**
     * Sets the associations list view.
     *
     * @param associationsListView The associations list view to set.
     */
    public void setAssociationsListView(ListView<String> associationsListView) {
        this.associationsListView = associationsListView;
    }

    /**
     * Gets the resources requested list view.
     *
     * @return The resources requested list view.
     */
    public ListView<String> getResourcesRequestedListView() {
        return resourcesRequestedListView;
    }

    /**
     * Sets the resources requested list view.
     *
     * @param resourcesRequestedListView The resources requested list view to set.
     */
    public void setResourcesRequestedListView(ListView<String> resourcesRequestedListView) {
        this.resourcesRequestedListView = resourcesRequestedListView;
    }

    /**
     * Gets the graph container anchor pane.
     *
     * @return The graph container.
     */
    public AnchorPane getGraphContainer() {
        return graphContainer;
    }

    /**
     * Sets the graph container anchor pane.
     *
     * @param graphContainer The graph container to set.
     */
    public void setGraphContainer(AnchorPane graphContainer) {
        this.graphContainer = graphContainer;
    }
}