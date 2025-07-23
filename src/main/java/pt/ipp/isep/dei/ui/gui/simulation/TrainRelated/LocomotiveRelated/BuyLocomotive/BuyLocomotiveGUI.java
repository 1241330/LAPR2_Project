package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive;

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
import pt.ipp.isep.dei.controller.simulation.TrainRelated.LocomotiveRelated.BuyLocomotive.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.dto.LocomotiveDTO;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.LocomotiveRelated.LocomotiveRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Buy Locomotive GUI.
 * Handles the logic for displaying available locomotives for purchase,
 * showing their details, and managing the purchase process.
 */
public class BuyLocomotiveGUI implements Initializable {

    /** Path to the default image for unknown locomotives. */
    private String unknownImagePath = "/images/Locomotive/UnknownImageLocomotive.png";

    /** Controller for locomotive purchase operations. */
    private BuyLocomotiveController controller;

    /** TableView displaying the list of available locomotives. */
    @FXML
    private TableView<LocomotiveDTO> buyLocomotiveTableView;

    /** Label for displaying error messages. */
    @FXML
    private Label errorLabel;

    /** TableColumn for locomotive names. */
    @FXML
    private TableColumn<LocomotiveDTO, String> nameColumn;

    /** TableColumn for locomotive prices. */
    @FXML
    private TableColumn<LocomotiveDTO, String> priceColumn;

    /** ImageView for displaying the selected locomotive's image. */
    @FXML
    private ImageView locomotiveImageView;

    /** Label for the selected locomotive's name. */
    @FXML
    private Label locomotiveName;

    /** Label for the selected locomotive's power. */
    @FXML
    private Label locomotivePower;

    /** Label for the selected locomotive's acceleration. */
    @FXML
    private Label locomotiveAcceleration;

    /** Label for the selected locomotive's top speed. */
    @FXML
    private Label locomotiveTopSpeed;

    /** Label for the selected locomotive's maintenance cost. */
    @FXML
    private Label maintenanceCostLabel;

    /** Label for the selected locomotive's fuel type. */
    @FXML
    private Label locomotiveFuel;

    /** Label for the selected locomotive's maximum carriages. */
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
        controller = new BuyLocomotiveController();
    }

    /**
     * Sets the simulation and initializes the GUI items.
     *
     * @param simulation The simulation to set.
     */
    @FXML
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the GUI components, sets up the table columns and listeners.
     */
    private void initializeGUIItems() {
        locomotiveImageView.setImage(new Image(getClass().getResource(unknownImagePath).toExternalForm()));

        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        priceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAcquisitionPrice() + " \uD83D\uDCB0")
        );

        buyLocomotiveTableView.setItems(controller.getLocomotivesList());

        buyLocomotiveTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateLocomotiveDetails(newVal);
            }
        });
    }

    /**
     * Updates the details section with the selected locomotive's information.
     *
     * @param locomotive The selected locomotive DTO.
     */
    private void updateLocomotiveDetails(LocomotiveDTO locomotive) {
        locomotiveName.setText(locomotive.getName());
        locomotiveName.setStyle("-fx-text-fill: black;");
        locomotivePower.setText(locomotive.getPower() + " kW");
        locomotivePower.setStyle("-fx-text-fill: black;");
        locomotiveAcceleration.setText(locomotive.getAcceleration() + " m/s²");
        locomotiveAcceleration.setStyle("-fx-text-fill: black;");
        locomotiveTopSpeed.setText(locomotive.getTopSpeed() + " km/h");
        locomotiveTopSpeed.setStyle("-fx-text-fill: black;");
        maintenanceCostLabel.setText(locomotive.getMaintenanceCost() + " 💰");
        maintenanceCostLabel.setStyle("-fx-text-fill: black;");
        locomotiveFuel.setText(locomotive.getFuelType().name());
        locomotiveFuel.setStyle("-fx-text-fill: black;");
        locomotiveCarriages.setText(String.valueOf(locomotive.getMaxCarriages()));
        locomotiveCarriages.setStyle("-fx-text-fill: black;");

        locomotiveImageView.setImage(new Image(getClass().getResource(locomotive.getImagePath()).toExternalForm()));
    }

    /**
     * Handles the event when the user clicks the "Cancel" button.
     * Navigates back to the main locomotive related screen.
     *
     * @param event The action event.
     */
    @FXML
    public void handleCancelButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/LocomotiveRelated/LocomotiveRelated.fxml"));
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
     * Handles the event when the user clicks the "Buy Locomotive" button.
     * Opens a confirmation dialog for purchasing the selected locomotive.
     *
     * @param event The action event.
     */
    @FXML
    public void handleBuyLocomotiveButton(ActionEvent event) {
        LocomotiveDTO selectedDto = buyLocomotiveTableView.getSelectionModel().getSelectedItem();

        if (selectedDto == null) {
            errorLabel.setText("You need to select a Locomotive first!!");
            return;
        }

        if (controller.notEnoughMoney(selectedDto)) {
            errorLabel.setText("Not enough money to buy this locomotive!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/LocomotiveRelated/BuyLocomotive/LocomotivePurchaseConfirmation.fxml"));
            Parent root = loader.load();

            LocomotivePurchaseConfirmationGUI purchaseConfirmationGUI = loader.getController();
            purchaseConfirmationGUI.setData(
                    controller.getSimulation(),
                    controller.getDomainLocomotiveFromDTO(selectedDto),
                    (Stage) ((Node) event.getSource()).getScene().getWindow()
            );
            purchaseConfirmationGUI.setPurchaseMessage("Do you want to buy this Locomotive?");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Purchase Confirmation Locomotive");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the BuyLocomotiveController.
     *
     * @return The BuyLocomotiveController.
     */
    public BuyLocomotiveController getController() {
        return controller;
    }

    /**
     * Sets the BuyLocomotiveController.
     *
     * @param controller The BuyLocomotiveController to set.
     */
    public void setController(BuyLocomotiveController controller) {
        this.controller = controller;
    }

    /**
     * Gets the TableView of locomotives.
     *
     * @return The TableView of locomotives.
     */
    public TableView<LocomotiveDTO> getBuyLocomotiveTableView() {
        return buyLocomotiveTableView;
    }

    /**
     * Sets the TableView of locomotives.
     *
     * @param buyLocomotiveTableView The TableView to set.
     */
    public void setBuyLocomotiveTableView(TableView<LocomotiveDTO> buyLocomotiveTableView) {
        this.buyLocomotiveTableView = buyLocomotiveTableView;
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
     * Gets the name column.
     *
     * @return The name column.
     */
    public TableColumn<LocomotiveDTO, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The name column to set.
     */
    public void setNameColumn(TableColumn<LocomotiveDTO, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the price column.
     *
     * @return The price column.
     */
    public TableColumn<LocomotiveDTO, String> getPriceColumn() {
        return priceColumn;
    }

    /**
     * Sets the price column.
     *
     * @param priceColumn The price column to set.
     */
    public void setPriceColumn(TableColumn<LocomotiveDTO, String> priceColumn) {
        this.priceColumn = priceColumn;
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
     * Gets the maintenance cost label.
     *
     * @return The maintenance cost label.
     */
    public Label getMaintenanceCostLabel() {
        return maintenanceCostLabel;
    }

    /**
     * Sets the maintenance cost label.
     *
     * @param maintenanceCostLabel The maintenance cost label to set.
     */
    public void setMaintenanceCostLabel(Label maintenanceCostLabel) {
        this.maintenanceCostLabel = maintenanceCostLabel;
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
}