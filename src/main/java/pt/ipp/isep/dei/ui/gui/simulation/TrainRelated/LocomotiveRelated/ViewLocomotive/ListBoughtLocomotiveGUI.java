package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.ViewLocomotive;

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
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct.ListLocomotiveController;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.ViewLocomotive.ListBoughtLocomotiveController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.Construct.ListCarriageGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.ConstructTrainRelatedGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.LocomotiveRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for listing all bought locomotives.
 * Handles the display and selection of locomotives, and navigation actions.
 */
public class ListBoughtLocomotiveGUI implements Initializable {

    private String unknownImagePath = "/images/Locomotive/UnknownImageLocomotive.png";
    private ListBoughtLocomotiveController controller;

    @FXML
    private TableView<Locomotive> listLocomotiveTableView;

    @FXML
    private Label errorLabel;

    @FXML
    private TableColumn<Locomotive, String> nameColumn;

    @FXML
    private TableColumn<Locomotive, String> priceColumn;

    @FXML
    private ImageView locomotiveImageView;

    @FXML
    private Label locomotiveName;

    @FXML
    private Label locomotivePower;

    @FXML
    private Label locomotiveAcceleration;

    @FXML
    private Label locomotiveTopSpeed;

    @FXML
    private Label locomotivePrice;

    @FXML
    private Label locomotiveFuel;

    @FXML
    private Label locomotiveCarriages;

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ListBoughtLocomotiveController();
    }

    /**
     * Sets the simulation and initializes the GUI items.
     *
     * @param simulation The current simulation.
     */
    @FXML
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the GUI components and binds table columns.
     */
    private void initializeGUIItems() {
        locomotiveImageView.setImage(new Image(getClass().getResource(unknownImagePath).toExternalForm()));
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        priceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAcquisitionPrice() + " \uD83D\uDCB0")
        );

        listLocomotiveTableView.setItems(controller.getLocomotivesList());

        listLocomotiveTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateLocomotiveDetails(newValue);
            }
        });
    }

    /**
     * Updates the details section with the selected locomotive's information.
     *
     * @param locomotive The selected locomotive.
     */
    private void updateLocomotiveDetails(Locomotive locomotive) {
        locomotiveName.setText(locomotive.getName());
        locomotiveName.setStyle("-fx-text-fill: black;");
        locomotivePower.setText(locomotive.getPower() + " kW");
        locomotivePower.setStyle("-fx-text-fill: black;");
        locomotiveAcceleration.setText(locomotive.getAcceleration() + " m/s²");
        locomotiveAcceleration.setStyle("-fx-text-fill: black;");
        locomotiveTopSpeed.setText(locomotive.getTopSpeed() + " km/h");
        locomotiveTopSpeed.setStyle("-fx-text-fill: black;");
        locomotivePrice.setText(locomotive.getAcquisitionPrice() + " 💰");
        locomotivePrice.setStyle("-fx-text-fill: black;");
        locomotiveFuel.setText(locomotive.getFuelType().name());
        locomotiveFuel.setStyle("-fx-text-fill: black;");
        locomotiveCarriages.setText(String.valueOf(locomotive.getMaxCarriages()));
        locomotiveCarriages.setStyle("-fx-text-fill: black;");

        locomotiveImageView.setImage(new Image(getClass().getResource(locomotive.getImagePath()).toExternalForm()));
    }

    /**
     * Handles the action when the cancel button is pressed.
     * Navigates back to the Locomotive Related window.
     *
     * @param event The action event.
     */
    @FXML
    public void handleCancelButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TrainRelated/LocomotiveRelated/LocomotiveRelated.fxml"));
            Parent root = loader.load();
            LocomotiveRelatedGUI locomotiveRelatedGUI = loader.getController();
            locomotiveRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Locomotive Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Gets the locomotive image view.
     *
     * @return The locomotive image view.
     */
    public ImageView getLocomotiveImageView() {
        return locomotiveImageView;
    }

    /**
     * Sets the locomotive image view.
     *
     * @param locomotiveImageView The locomotive image view to set.
     */
    public void setLocomotiveImageView(ImageView locomotiveImageView) {
        this.locomotiveImageView = locomotiveImageView;
    }

    /**
     * Gets the locomotive name label.
     *
     * @return The locomotive name label.
     */
    public Label getLocomotiveName() {
        return locomotiveName;
    }

    /**
     * Sets the locomotive name label.
     *
     * @param locomotiveName The locomotive name label to set.
     */
    public void setLocomotiveName(Label locomotiveName) {
        this.locomotiveName = locomotiveName;
    }

    /**
     * Gets the locomotive power label.
     *
     * @return The locomotive power label.
     */
    public Label getLocomotivePower() {
        return locomotivePower;
    }

    /**
     * Sets the locomotive power label.
     *
     * @param locomotivePower The locomotive power label to set.
     */
    public void setLocomotivePower(Label locomotivePower) {
        this.locomotivePower = locomotivePower;
    }

    /**
     * Gets the locomotive acceleration label.
     *
     * @return The locomotive acceleration label.
     */
    public Label getLocomotiveAcceleration() {
        return locomotiveAcceleration;
    }

    /**
     * Sets the locomotive acceleration label.
     *
     * @param locomotiveAcceleration The locomotive acceleration label to set.
     */
    public void setLocomotiveAcceleration(Label locomotiveAcceleration) {
        this.locomotiveAcceleration = locomotiveAcceleration;
    }

    /**
     * Gets the locomotive top speed label.
     *
     * @return The locomotive top speed label.
     */
    public Label getLocomotiveTopSpeed() {
        return locomotiveTopSpeed;
    }

    /**
     * Sets the locomotive top speed label.
     *
     * @param locomotiveTopSpeed The locomotive top speed label to set.
     */
    public void setLocomotiveTopSpeed(Label locomotiveTopSpeed) {
        this.locomotiveTopSpeed = locomotiveTopSpeed;
    }

    /**
     * Gets the locomotive price label.
     *
     * @return The locomotive price label.
     */
    public Label getLocomotivePrice() {
        return locomotivePrice;
    }

    /**
     * Sets the locomotive price label.
     *
     * @param locomotivePrice The locomotive price label to set.
     */
    public void setLocomotivePrice(Label locomotivePrice) {
        this.locomotivePrice = locomotivePrice;
    }

    /**
     * Gets the locomotive fuel label.
     *
     * @return The locomotive fuel label.
     */
    public Label getLocomotiveFuel() {
        return locomotiveFuel;
    }

    /**
     * Sets the locomotive fuel label.
     *
     * @param locomotiveFuel The locomotive fuel label to set.
     */
    public void setLocomotiveFuel(Label locomotiveFuel) {
        this.locomotiveFuel = locomotiveFuel;
    }

    /**
     * Gets the locomotive carriages label.
     *
     * @return The locomotive carriages label.
     */
    public Label getLocomotiveCarriages() {
        return locomotiveCarriages;
    }

    /**
     * Sets the locomotive carriages label.
     *
     * @param locomotiveCarriages The locomotive carriages label to set.
     */
    public void setLocomotiveCarriages(Label locomotiveCarriages) {
        this.locomotiveCarriages = locomotiveCarriages;
    }

    /**
     * Gets the table view of locomotives.
     *
     * @return The table view of locomotives.
     */
    public TableView<Locomotive> getListLocomotiveTableView() {
        return listLocomotiveTableView;
    }

    /**
     * Sets the table view of locomotives.
     *
     * @param listLocomotiveTableView The table view to set.
     */
    public void setListLocomotiveTableView(TableView<Locomotive> listLocomotiveTableView) {
        this.listLocomotiveTableView = listLocomotiveTableView;
    }

    /**
     * Gets the name column.
     *
     * @return The name column.
     */
    public TableColumn<Locomotive, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The name column to set.
     */
    public void setNameColumn(TableColumn<Locomotive, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the price column.
     *
     * @return The price column.
     */
    public TableColumn<Locomotive, String> getPriceColumn() {
        return priceColumn;
    }

    /**
     * Sets the price column.
     *
     * @param priceColumn The price column to set.
     */
    public void setPriceColumn(TableColumn<Locomotive, String> priceColumn) {
        this.priceColumn = priceColumn;
    }

    /**
     * Gets the controller.
     *
     * @return The ListBoughtLocomotiveController.
     */
    public ListBoughtLocomotiveController getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller The ListBoughtLocomotiveController to set.
     */
    public void setController(ListBoughtLocomotiveController controller) {
        this.controller = controller;
    }

    /**
     * Gets the unknown image path.
     *
     * @return The unknown image path.
     */
    public String getUnknownImagePath() {
        return unknownImagePath;
    }

    /**
     * Sets the unknown image path.
     *
     * @param unknownImagePath The unknown image path to set.
     */
    public void setUnknownImagePath(String unknownImagePath) {
        this.unknownImagePath = unknownImagePath;
    }
}