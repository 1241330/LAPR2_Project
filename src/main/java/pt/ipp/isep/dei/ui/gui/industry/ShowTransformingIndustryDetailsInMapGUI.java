package pt.ipp.isep.dei.ui.gui.industry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.industry.ShowTransformingIndustryDetailsInMapController;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for displaying detailed information about a TransformingIndustry in the map view.
 * <p>
 * This GUI presents the industry name, position, assigned station, transforming resource,
 * primary resources, and the current inventory associated with a specific transforming industry.
 * </p>
 */
public class ShowTransformingIndustryDetailsInMapGUI implements Initializable {

    private ShowTransformingIndustryDetailsInMapController controller;

    @FXML
    private Label nameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label stationLabel;

    @FXML
    private Label transformingResourceLabel;

    @FXML
    private Label primaryResourcesLabel;

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
        controller = new ShowTransformingIndustryDetailsInMapController();
    }

    /**
     * Sets the current TransformingIndustry (map and scenario) in the controller and updates the GUI accordingly.
     *
     * @param transformingIndustryMap      the transforming industry from the map
     * @param transformingIndustryScenario the transforming industry from the scenario
     */
    public void setIndustry(TransformingIndustry transformingIndustryMap, TransformingIndustry transformingIndustryScenario) {
        controller.setTransformingIndustryMap(transformingIndustryMap);
        controller.setTransformingIndustryScenario(transformingIndustryScenario);

        nameLabel.setText("Name: " + controller.getIndustryName());
        positionLabel.setText("Position: " + controller.getIndustryPosition());
        stationLabel.setText("Assigned Station: " + controller.getAssignedStationName());
        transformingResourceLabel.setText("Transforming Resource: " + controller.getTransformingResourceName());
        primaryResourcesLabel.setText("Primary Resources: " + controller.getPrimaryResourcesNames());

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
     * @return the ShowTransformingIndustryDetailsInMapController
     */
    public ShowTransformingIndustryDetailsInMapController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller the ShowTransformingIndustryDetailsInMapController to set
     */
    public void setController(ShowTransformingIndustryDetailsInMapController controller) {
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
     * Gets the transforming resource label.
     *
     * @return the transforming resource label
     */
    public Label getTransformingResourceLabel() {
        return transformingResourceLabel;
    }

    /**
     * Sets the transforming resource label.
     *
     * @param transformingResourceLabel the transforming resource label to set
     */
    public void setTransformingResourceLabel(Label transformingResourceLabel) {
        this.transformingResourceLabel = transformingResourceLabel;
    }

    /**
     * Gets the primary resources label.
     *
     * @return the primary resources label
     */
    public Label getPrimaryResourcesLabel() {
        return primaryResourcesLabel;
    }

    /**
     * Sets the primary resources label.
     *
     * @param primaryResourcesLabel the primary resources label to set
     */
    public void setPrimaryResourcesLabel(Label primaryResourcesLabel) {
        this.primaryResourcesLabel = primaryResourcesLabel;
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