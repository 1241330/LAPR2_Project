package pt.ipp.isep.dei.ui.gui.houseBlock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.city.houseBlock.ShowHouseBlockDetailsInMapController;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Scenario.Scenario;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for displaying detailed information about a HouseBlock in the map view.
 * <p>
 * This GUI presents the city name, position, assigned station, current productions,
 * and inventory associated with a specific house block.
 * </p>
 */
public class ShowHouseBlockDetailsInMapGUI implements Initializable {

    private ShowHouseBlockDetailsInMapController controller;

    @FXML
    private Label cityNameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label stationLabel;

    @FXML
    private ListView<String> productionsListView;

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
        controller = new ShowHouseBlockDetailsInMapController();
    }

    /**
     * Sets the current HouseBlock and Scenario in the controller and updates the GUI accordingly.
     *
     * @param houseBlock the house block whose details are to be shown
     * @param scenario   the scenario containing context (map, stations, etc.)
     */
    public void setHouseBlock(HouseBlock houseBlock, Scenario scenario) {
        controller.setHouseBlock(houseBlock);
        controller.setScenario(scenario);

        // Update labels with data from the controller
        cityNameLabel.setText("City: " + controller.getCityName());
        positionLabel.setText("Position: " + controller.getPosition());
        stationLabel.setText("Assigned Station: " + controller.getAssignedStationName());

        // Populate productions list
        productionsListView.getItems().clear();
        productionsListView.getItems().addAll(controller.getHouseBlockProductions());
        productionsListView.getSelectionModel().clearSelection();

        // Populate inventory list
        inventoryListView.getItems().clear();
        inventoryListView.getItems().addAll(controller.getHouseBlockInventory());
        inventoryListView.getSelectionModel().clearSelection();
    }

    /**
     * Handles the "Close" button action. Closes the current window.
     *
     * @param event the ActionEvent triggered by the close button
     */
    @FXML
    private void handleClose(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Gets the city name label.
     *
     * @return the city name label
     */
    public Label getCityNameLabel() {
        return cityNameLabel;
    }

    /**
     * Sets the city name label.
     *
     * @param cityNameLabel the city name label to set
     */
    public void setCityNameLabel(Label cityNameLabel) {
        this.cityNameLabel = cityNameLabel;
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
     * Gets the productions list view.
     *
     * @return the productions list view
     */
    public ListView<String> getProductionsListView() {
        return productionsListView;
    }

    /**
     * Sets the productions list view.
     *
     * @param productionsListView the productions list view to set
     */
    public void setProductionsListView(ListView<String> productionsListView) {
        this.productionsListView = productionsListView;
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

    /**
     * Gets the controller.
     *
     * @return the ShowHouseBlockDetailsInMapController
     */
    public ShowHouseBlockDetailsInMapController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller the ShowHouseBlockDetailsInMapController to set
     */
    public void setController(ShowHouseBlockDetailsInMapController controller) {
        this.controller = controller;
    }
}