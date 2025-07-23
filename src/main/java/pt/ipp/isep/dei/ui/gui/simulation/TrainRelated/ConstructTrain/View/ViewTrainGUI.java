package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.View.ViewTrainController;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.ConstructTrainRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for viewing trains in the simulation.
 * Handles the display and interaction logic for train details.
 */
public class ViewTrainGUI implements Initializable {

    private final String unknownImagePath = "/images/Locomotive/UnknownImageLocomotive.png";
    private ViewTrainController controller;

    @FXML private TableView<Train> listTrainTableView;
    @FXML private TableColumn<Train, String> nameColumn;
    @FXML private TableColumn<Train, String> inventorySpaceColumn;
    @FXML private ImageView trainImageView;
    @FXML private Label trainName;
    @FXML private Label trainLocomotive;
    @FXML private Label trainInventorySpace;
    @FXML private Label errorLabel;
    @FXML private ListView<String> trainCarriageListView;
    @FXML private ListView<String> trainInventoryListView;

    /**
     * Initializes the controller class. Sets up placeholders and styles for list views.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ViewTrainController();
        trainCarriageListView.setPlaceholder(new Label("No carriages available"));
        trainCarriageListView.setStyle("-fx-text-fill: red;");
        trainInventoryListView.setPlaceholder(new Label("No inventory items"));
        trainInventoryListView.setStyle("-fx-text-fill: red;");
    }

    /**
     * Sets the simulation for this GUI and initializes the GUI items.
     *
     * @param simulation The simulation to set.
     */
    @FXML
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the GUI items, including table columns and listeners.
     */
    private void initializeGUIItems() {
        trainImageView.setImage(new Image(getClass().getResource(unknownImagePath).toExternalForm()));

        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        inventorySpaceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMaxInventorySpace() + " \uD83C\uDF3E"));

        listTrainTableView.setItems(controller.getTrainList());

        listTrainTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) updateTrainDetails(newVal);
        });
    }

    /**
     * Updates the train details section with the selected train's information.
     *
     * @param train The selected train.
     */
    private void updateTrainDetails(Train train) {
        trainName.setText(train.getName());
        trainName.setStyle("-fx-text-fill: black;");

        trainLocomotive.setText(train.getLocomotive().getName());
        trainLocomotive.setStyle("-fx-text-fill: black;");

        trainCarriageListView.getItems().clear();
        for (Carriage c : train.getCarriages()) {
            trainCarriageListView.getItems().add(c.getName());
        }
        trainCarriageListView.setStyle("-fx-text-fill: black;");

        trainInventoryListView.getItems().clear();
        for (Resource r : train.getInventory().getAllResources()) {
            trainInventoryListView.getItems().add(r.getResourceType() + " - " + r.getQuantity() + " \uD83C\uDF3E");
        }
        trainInventoryListView.setStyle("-fx-text-fill: black;");

        trainInventorySpace.setText(train.getMaxInventorySpace() + " \uD83C\uDF3E");
        trainInventorySpace.setStyle("-fx-text-fill: black;");

        trainImageView.setImage(new Image(getClass().getResource(train.getLocomotive().getImagePath()).toExternalForm()));
    }

    /**
     * Handles the event when the user clicks the cancel button.
     * Returns to the previous screen.
     *
     * @param event The action event.
     */
    @FXML
    public void handleCancelButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/ConstructTrainRelated/ConstructTrainsRelated.fxml"));
            Parent root = loader.load();
            ConstructTrainRelatedGUI gui = loader.getController();
            gui.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Construct Train Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the train table view.
     *
     * @return The train table view.
     */
    public TableView<Train> getListTrainTableView() {
        return listTrainTableView;
    }

    /**
     * Sets the train table view.
     *
     * @param listTrainTableView The train table view to set.
     */
    public void setListTrainTableView(TableView<Train> listTrainTableView) {
        this.listTrainTableView = listTrainTableView;
    }

    /**
     * Gets the name column.
     *
     * @return The name column.
     */
    public TableColumn<Train, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The name column to set.
     */
    public void setNameColumn(TableColumn<Train, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the inventory space column.
     *
     * @return The inventory space column.
     */
    public TableColumn<Train, String> getInventorySpaceColumn() {
        return inventorySpaceColumn;
    }

    /**
     * Sets the inventory space column.
     *
     * @param inventorySpaceColumn The inventory space column to set.
     */
    public void setInventorySpaceColumn(TableColumn<Train, String> inventorySpaceColumn) {
        this.inventorySpaceColumn = inventorySpaceColumn;
    }

    /**
     * Gets the train image view.
     *
     * @return The train image view.
     */
    public ImageView getTrainImageView() {
        return trainImageView;
    }

    /**
     * Sets the train image view.
     *
     * @param trainImageView The train image view to set.
     */
    public void setTrainImageView(ImageView trainImageView) {
        this.trainImageView = trainImageView;
    }

    /**
     * Gets the train name label.
     *
     * @return The train name label.
     */
    public Label getTrainName() {
        return trainName;
    }

    /**
     * Sets the train name label.
     *
     * @param trainName The train name label to set.
     */
    public void setTrainName(Label trainName) {
        this.trainName = trainName;
    }

    /**
     * Gets the train locomotive label.
     *
     * @return The train locomotive label.
     */
    public Label getTrainLocomotive() {
        return trainLocomotive;
    }

    /**
     * Sets the train locomotive label.
     *
     * @param trainLocomotive The train locomotive label to set.
     */
    public void setTrainLocomotive(Label trainLocomotive) {
        this.trainLocomotive = trainLocomotive;
    }

    /**
     * Gets the train inventory space label.
     *
     * @return The train inventory space label.
     */
    public Label getTrainInventorySpace() {
        return trainInventorySpace;
    }

    /**
     * Sets the train inventory space label.
     *
     * @param trainInventorySpace The train inventory space label to set.
     */
    public void setTrainInventorySpace(Label trainInventorySpace) {
        this.trainInventorySpace = trainInventorySpace;
    }

    /**
     * Gets the error label.
     *
     * @return The error label.
     */
    public Label getErrorLabel() {
        return errorLabel;
    }

    /**
     * Sets the error label.
     *
     * @param errorLabel The error label to set.
     */
    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    /**
     * Gets the train carriage list view.
     *
     * @return The train carriage list view.
     */
    public ListView<String> getTrainCarriageListView() {
        return trainCarriageListView;
    }

    /**
     * Sets the train carriage list view.
     *
     * @param trainCarriageListView The train carriage list view to set.
     */
    public void setTrainCarriageListView(ListView<String> trainCarriageListView) {
        this.trainCarriageListView = trainCarriageListView;
    }

    /**
     * Gets the train inventory list view.
     *
     * @return The train inventory list view.
     */
    public ListView<String> getTrainInventoryListView() {
        return trainInventoryListView;
    }

    /**
     * Sets the train inventory list view.
     *
     * @param trainInventoryListView The train inventory list view to set.
     */
    public void setTrainInventoryListView(ListView<String> trainInventoryListView) {
        this.trainInventoryListView = trainInventoryListView;
    }
}