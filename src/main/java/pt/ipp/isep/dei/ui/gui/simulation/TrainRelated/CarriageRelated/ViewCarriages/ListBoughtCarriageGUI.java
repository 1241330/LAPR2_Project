package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.ViewCarriages;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.ViewCarriages.ListBoughtCarriageController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.CarriageRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for displaying the list of bought carriages in a table view.
 * <p>
 * Allows the user to view details of each carriage, including image, name, resource capacity, and price.
 * Integrates with JavaFX and communicates with the corresponding controller.
 */
public class ListBoughtCarriageGUI implements Initializable {

    private String unknownImagePath = "/images/Carriage/UnknownImageCarriage.png";
    private ListBoughtCarriageController controller;

    @FXML
    private TableView<Carriage> listCarriageTableView;

    @FXML
    private Label errorLabel;

    @FXML
    private TableColumn<Carriage, String> nameColumn;

    @FXML
    private TableColumn<Carriage, String> priceColumn;

    @FXML
    private ImageView carriageImageView;

    @FXML
    private Label carriageName;

    @FXML
    private Label carriageResource;

    @FXML
    private Label carriagePrice;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ListBoughtCarriageController();
    }

    /**
     * Sets the current simulation instance and updates the GUI accordingly.
     *
     * @param simulation The current {@link Simulation} instance.
     */
    @FXML
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the GUI items, sets up the table columns, and adds a listener for table selection.
     */
    private void initializeGUIItems() {
        carriageImageView.setImage(new Image(getClass().getResource(unknownImagePath).toExternalForm()));

        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        priceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAcquisitionCost() + " \uD83D\uDCB0")
        );

        listCarriageTableView.setItems(controller.getCarriagesList());

        listCarriageTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateCarriageDetails(newValue);
            }
        });
    }

    /**
     * Updates the carriage details section with the selected carriage's information.
     *
     * @param carriage The selected {@link Carriage}.
     */
    private void updateCarriageDetails(Carriage carriage) {
        carriageName.setText(carriage.getName());
        carriageName.setStyle("-fx-text-fill: black;");
        carriageResource.setText(carriage.getMaxResourceCapacity() + " \uD83C\uDF3E");
        carriageResource.setStyle("-fx-text-fill: black;");
        carriagePrice.setText(carriage.getAcquisitionCost() + " \uD83D\uDCB0");
        carriagePrice.setStyle("-fx-text-fill: black;");

        carriageImageView.setImage(new Image(getClass().getResource(carriage.getImagePath()).toExternalForm()));
    }

    /**
     * Handles the action for the cancel button.
     * Returns to the previous menu (CarriageRelatedGUI).
     *
     * @param event The action event triggered by clicking the cancel button.
     */
    @FXML
    public void handleCancelButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/CarriageRelated/CarriageRelated.fxml"));
            Parent root = loader.load();
            CarriageRelatedGUI carriageRelatedGUI = loader.getController();
            carriageRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Carriage Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the controller.
     *
     * @return The {@link ListBoughtCarriageController} instance.
     */
    public ListBoughtCarriageController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The {@link ListBoughtCarriageController} to set.
     */
    public void setController(ListBoughtCarriageController controller) {
        this.controller = controller;
    }

    /**
     * Gets the error label.
     *
     * @return The error {@link Label}.
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the error label.
     *
     * @param errorLabel The error {@link Label} to set.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the carriage table view.
     *
     * @return The {@link TableView} of carriages.
     */
    public TableView<Carriage> getListCarriageTableView() {
        return listCarriageTableView;
    }

    /**
     * Sets the carriage table view.
     *
     * @param listCarriageTableView The {@link TableView} to set.
     */
    public void setListCarriageTableView(TableView<Carriage> listCarriageTableView) {
        this.listCarriageTableView = listCarriageTableView;
    }

    /**
     * Gets the name column.
     *
     * @return The {@link TableColumn} for carriage names.
     */
    public TableColumn<Carriage, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The {@link TableColumn} to set for carriage names.
     */
    public void setNameColumn(TableColumn<Carriage, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the price column.
     *
     * @return The {@link TableColumn} for carriage prices.
     */
    public TableColumn<Carriage, String> getPriceColumn() {
        return priceColumn;
    }

    /**
     * Sets the price column.
     *
     * @param priceColumn The {@link TableColumn} to set for carriage prices.
     */
    public void setPriceColumn(TableColumn<Carriage, String> priceColumn) {
        this.priceColumn = priceColumn;
    }

    /**
     * Gets the carriage image view.
     *
     * @return The {@link ImageView} for carriage images.
     */
    public ImageView getCarriageImageView() {
        return carriageImageView;
    }

    /**
     * Sets the carriage image view.
     *
     * @param carriageImageView The {@link ImageView} to set.
     */
    public void setCarriageImageView(ImageView carriageImageView) {
        this.carriageImageView = carriageImageView;
    }

    /**
     * Gets the carriage name label.
     *
     * @return The {@link Label} for carriage name.
     */
    public Label getCarriageName() {
        return carriageName;
    }

    /**
     * Sets the carriage name label.
     *
     * @param carriageName The {@link Label} to set.
     */
    public void setCarriageName(Label carriageName) {
        this.carriageName = carriageName;
    }

    /**
     * Gets the carriage resource label.
     *
     * @return The {@link Label} for carriage resource.
     */
    public Label getCarriageResource() {
        return carriageResource;
    }

    /**
     * Sets the carriage resource label.
     *
     * @param carriageResource The {@link Label} to set.
     */
    public void setCarriageResource(Label carriageResource) {
        this.carriageResource = carriageResource;
    }

    /**
     * Gets the carriage price label.
     *
     * @return The {@link Label} for carriage price.
     */
    public Label getCarriagePrice() {
        return carriagePrice;
    }

    /**
     * Sets the carriage price label.
     *
     * @param carriagePrice The {@link Label} to set.
     */
    public void setCarriagePrice(Label carriagePrice) {
        this.carriagePrice = carriagePrice;
    }

    /**
     * Gets the unknown image path.
     *
     * @return The path to the unknown carriage image.
     */
    public String getUnknownImagePath() {
        return unknownImagePath;
    }

    /**
     * Sets the unknown image path.
     *
     * @param unknownImagePath The path to set for the unknown carriage image.
     */
    public void setUnknownImagePath(String unknownImagePath) {
        this.unknownImagePath = unknownImagePath;
    }
}