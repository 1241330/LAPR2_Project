package pt.ipp.isep.dei.ui.gui.industry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.industry.ShowMixedIndustryDetailsInMapController;
import pt.ipp.isep.dei.domain.Industry.MixedIndustry;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for displaying detailed information about a MixedIndustry in the map view.
 * <p>
 * This GUI presents the industry name, position, assigned station, exported, imported,
 * and transformed resources, as well as the current inventory associated with a specific mixed industry.
 * </p>
 */
public class ShowMixedIndustryDetailsInMapGUI implements Initializable {

    private ShowMixedIndustryDetailsInMapController controller;

    @FXML
    private Label nameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label stationLabel;

    @FXML
    private ListView<String> exportedResourcesListView;

    @FXML
    private ListView<String> importedResourcesListView;

    @FXML
    private ListView<String> transformedResourcesListView;

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
        controller = new ShowMixedIndustryDetailsInMapController();
    }

    /**
     * Sets the current MixedIndustry (map and scenario) in the controller and updates the GUI accordingly.
     *
     * @param mixedIndustryMap      the mixed industry from the map
     * @param mixedIndustryScenario the mixed industry from the scenario
     */
    public void setIndustry(MixedIndustry mixedIndustryMap, MixedIndustry mixedIndustryScenario) {
        controller.setMixedIndustryMap(mixedIndustryMap);
        controller.setMixedIndustryScenario(mixedIndustryScenario);

        nameLabel.setText("Name: " + controller.getIndustryName());
        positionLabel.setText("Position: " + controller.getIndustryPosition());
        stationLabel.setText("Assigned Station: " + controller.getAssignedStationName());

        exportedResourcesListView.getItems().clear();
        exportedResourcesListView.getItems().addAll(controller.getExportedResourcesNames());
        exportedResourcesListView.getSelectionModel().clearSelection();

        importedResourcesListView.getItems().clear();
        importedResourcesListView.getItems().addAll(controller.getImportedResourcesNames());
        importedResourcesListView.getSelectionModel().clearSelection();

        transformedResourcesListView.getItems().clear();
        transformedResourcesListView.getItems().addAll(controller.getTransformedResourcesNames());
        transformedResourcesListView.getSelectionModel().clearSelection();

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
     * @return the ShowMixedIndustryDetailsInMapController
     */
    public ShowMixedIndustryDetailsInMapController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller the ShowMixedIndustryDetailsInMapController to set
     */
    public void setController(ShowMixedIndustryDetailsInMapController controller) {
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
     * Gets the exported resources list view.
     *
     * @return the exported resources list view
     */
    public ListView<String> getExportedResourcesListView() {
        return exportedResourcesListView;
    }

    /**
     * Sets the exported resources list view.
     *
     * @param exportedResourcesListView the exported resources list view to set
     */
    public void setExportedResourcesListView(ListView<String> exportedResourcesListView) {
        this.exportedResourcesListView = exportedResourcesListView;
    }

    /**
     * Gets the imported resources list view.
     *
     * @return the imported resources list view
     */
    public ListView<String> getImportedResourcesListView() {
        return importedResourcesListView;
    }

    /**
     * Sets the imported resources list view.
     *
     * @param importedResourcesListView the imported resources list view to set
     */
    public void setImportedResourcesListView(ListView<String> importedResourcesListView) {
        this.importedResourcesListView = importedResourcesListView;
    }

    /**
     * Gets the transformed resources list view.
     *
     * @return the transformed resources list view
     */
    public ListView<String> getTransformedResourcesListView() {
        return transformedResourcesListView;
    }

    /**
     * Sets the transformed resources list view.
     *
     * @param transformedResourcesListView the transformed resources list view to set
     */
    public void setTransformedResourcesListView(ListView<String> transformedResourcesListView) {
        this.transformedResourcesListView = transformedResourcesListView;
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