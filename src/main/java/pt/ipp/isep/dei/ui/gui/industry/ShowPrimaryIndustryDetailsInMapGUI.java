package pt.ipp.isep.dei.ui.gui.industry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.industry.ShowPrimaryIndustryDetailsInMapController;
import pt.ipp.isep.dei.domain.Industry.PrimaryIndustry;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for displaying detailed information about a PrimaryIndustry in the map view.
 * <p>
 * This GUI presents the industry name, position, assigned station, primary resource,
 * maximum resources, interval between resource generation, quantity produced,
 * and the current inventory associated with a specific primary industry.
 * </p>
 */
public class ShowPrimaryIndustryDetailsInMapGUI implements Initializable {

    private ShowPrimaryIndustryDetailsInMapController controller;

    @FXML
    private Label nameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label stationLabel;

    @FXML
    private Label resourceLabel;

    @FXML
    private Label maxResourcesLabel;

    @FXML
    private Label intervalLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private ListView<String> inventoryListView;

    /**
     * Initializes the controller when the FXML is loaded.
     *
     * @param url            not used
     * @param resourceBundle not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowPrimaryIndustryDetailsInMapController();
    }

    /**
     * Sets the current PrimaryIndustry (map and scenario) in the controller and updates the GUI accordingly.
     *
     * @param primaryIndustryMap      the primary industry from the map
     * @param primaryIndustryScenario the primary industry from the scenario
     */
    public void setIndustry(PrimaryIndustry primaryIndustryMap, PrimaryIndustry primaryIndustryScenario) {
        controller.setPrimaryIndustryMap(primaryIndustryMap);
        controller.setPrimaryIndustryScenario(primaryIndustryScenario);

        nameLabel.setText("Name: " + controller.getIndustryName());
        positionLabel.setText("Position: " + controller.getIndustryPosition());
        stationLabel.setText("Assigned Station: " + controller.getAssignedStationName());
        resourceLabel.setText("Primary Resource: " + controller.getPrimaryResourceName());
        maxResourcesLabel.setText("Max Resources: " + controller.getMaxResources());
        intervalLabel.setText("Interval Between Generation: " + controller.getIntervalBetweenResourceGeneration());
        quantityLabel.setText("Quantity Produced: " + controller.getQuantityProduced());

        inventoryListView.getItems().clear();
        inventoryListView.getItems().addAll(controller.getIndustryInventory());
        inventoryListView.getSelectionModel().clearSelection();
    }

    /**
     * Handles the "Close" button action. Closes the current window.
     *
     * @param event the ActionEvent triggered by the close button
     */
    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Gets the controller.
     *
     * @return the ShowPrimaryIndustryDetailsInMapController
     */
    public ShowPrimaryIndustryDetailsInMapController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller the ShowPrimaryIndustryDetailsInMapController to set
     */
    public void setController(ShowPrimaryIndustryDetailsInMapController controller) {
        this.controller = controller;
    }

    /**
     * Gets the name label.
     *
     * @return the name label
     */
    public Label getNameLabel() {
        return nameLabel;
    }

    /**
     * Sets the name label.
     *
     * @param nameLabel the name label to set
     */
    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    /**
     * Gets the position label.
     *
     * @return the position label
     */
    public Label getPositionLabel() {
        return positionLabel;
    }

    /**
     * Sets the position label.
     *
     * @param positionLabel the position label to set
     */
    public void setPositionLabel(Label positionLabel) {
        this.positionLabel = positionLabel;
    }

    /**
     * Gets the station label.
     *
     * @return the station label
     */
    public Label getStationLabel() {
        return stationLabel;
    }

    /**
     * Sets the station label.
     *
     * @param stationLabel the station label to set
     */
    public void setStationLabel(Label stationLabel) {
        this.stationLabel = stationLabel;
    }

    /**
     * Gets the resource label.
     *
     * @return the resource label
     */
    public Label getResourceLabel() {
        return resourceLabel;
    }

    /**
     * Sets the resource label.
     *
     * @param resourceLabel the resource label to set
     */
    public void setResourceLabel(Label resourceLabel) {
        this.resourceLabel = resourceLabel;
    }

    /**
     * Gets the max resources label.
     *
     * @return the max resources label
     */
    public Label getMaxResourcesLabel() {
        return maxResourcesLabel;
    }

    /**
     * Sets the max resources label.
     *
     * @param maxResourcesLabel the max resources label to set
     */
    public void setMaxResourcesLabel(Label maxResourcesLabel) {
        this.maxResourcesLabel = maxResourcesLabel;
    }

    /**
     * Gets the interval label.
     *
     * @return the interval label
     */
    public Label getIntervalLabel() {
        return intervalLabel;
    }

    /**
     * Sets the interval label.
     *
     * @param intervalLabel the interval label to set
     */
    public void setIntervalLabel(Label intervalLabel) {
        this.intervalLabel = intervalLabel;
    }

    /**
     * Gets the quantity label.
     *
     * @return the quantity label
     */
    public Label getQuantityLabel() {
        return quantityLabel;
    }

    /**
     * Sets the quantity label.
     *
     * @param quantityLabel the quantity label to set
     */
    public void setQuantityLabel(Label quantityLabel) {
        this.quantityLabel = quantityLabel;
    }

    /**
     * Gets the inventory list view.
     *
     * @return the inventory list view
     */
    public ListView<String> getInventoryListView() {
        return inventoryListView;
    }

    /**
     * Sets the inventory list view.
     *
     * @param inventoryListView the inventory list view to set
     */
    public void setInventoryListView(ListView<String> inventoryListView) {
        this.inventoryListView = inventoryListView;
    }
}