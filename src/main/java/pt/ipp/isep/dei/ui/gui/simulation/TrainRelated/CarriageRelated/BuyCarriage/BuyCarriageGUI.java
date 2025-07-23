package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.BuyCarriage;

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
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.BuyCarriage.BuyCarriageController;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.CarriageRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for buying carriages in the simulation.
 * Handles the display and purchase logic for carriages.
 */
public class BuyCarriageGUI implements Initializable {

    private String unknownImagePath = "/images/Carriage/UnknownImageCarriage.png";
    private BuyCarriageController controller;

    @FXML
    private TableView<Carriage> buyCarriageTableView;
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
        controller = new BuyCarriageController();
    }

    /**
     * Sets the simulation instance and initializes the GUI items.
     *
     * @param simulation The current simulation instance.
     */
    @FXML
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the GUI components, sets up table columns and listeners.
     */
    private void initializeGUIItems() {
        carriageImageView.setImage(new Image(getClass().getResource(unknownImagePath).toExternalForm()));
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        priceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAcquisitionCost() + " \uD83D\uDCB0")
        );

        buyCarriageTableView.setItems(controller.getCarriagesList());

        buyCarriageTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateCarriageDetails(newValue);
            }
        });
    }

    /**
     * Updates the carriage details section with the selected carriage's information.
     *
     * @param carriage The selected carriage.
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
     * Navigates back to the CarriageRelated screen.
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
     * Handles the action for the buy carriage button.
     * Opens a confirmation dialog if a carriage is selected and there is enough money.
     *
     * @param event The action event triggered by clicking the buy carriage button.
     */
    @FXML
    public void handleBuyCarriageButton(ActionEvent event) {
        Carriage selectedCarriage = buyCarriageTableView.getSelectionModel().getSelectedItem();

        if (selectedCarriage == null) {
            errorLabel.setText("You need to select a Carriage first!!");
            return;
        }

        if (controller.notEnoughMoney(selectedCarriage)) {
            errorLabel.setText("Not enough money to buy this carriage!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/CarriageRelated/BuyCarriage/CarriagePurchaseConfirmation.fxml"));
            Parent root = loader.load();

            CarriagePurchaseConfirmationGUI purchaseConfirmationGUI = loader.getController();
            purchaseConfirmationGUI.setData(controller.getSimulation(), selectedCarriage, (Stage) ((Node) event.getSource()).getScene().getWindow());
            purchaseConfirmationGUI.setPurchaseMessage("Do you want to buy this Carriage?");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Purchase Confirmation Carriage");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the BuyCarriageController instance.
     *
     * @return The BuyCarriageController.
     */
    public BuyCarriageController getController() {
        return controller;
    }

    /**
     * Sets the BuyCarriageController instance.
     *
     * @param controller The BuyCarriageController to set.
     */
    public void setController(BuyCarriageController controller) {
        this.controller = controller;
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
     * Gets the TableView for carriages.
     *
     * @return The TableView of carriages.
     */
    public TableView<Carriage> getBuyCarriageTableView() {
        return buyCarriageTableView;
    }

    /**
     * Sets the TableView for carriages.
     *
     * @param buyCarriageTableView The TableView to set.
     */
    public void setBuyCarriageTableView(TableView<Carriage> buyCarriageTableView) {
        this.buyCarriageTableView = buyCarriageTableView;
    }

    /**
     * Gets the name column.
     *
     * @return The name column.
     */
    public TableColumn<Carriage, String> getNameColumn() {
        return nameColumn;
    }

    /**
     * Sets the name column.
     *
     * @param nameColumn The name column to set.
     */
    public void setNameColumn(TableColumn<Carriage, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     * Gets the price column.
     *
     * @return The price column.
     */
    public TableColumn<Carriage, String> getPriceColumn() {
        return priceColumn;
    }

    /**
     * Sets the price column.
     *
     * @param priceColumn The price column to set.
     */
    public void setPriceColumn(TableColumn<Carriage, String> priceColumn) {
        this.priceColumn = priceColumn;
    }

    /**
     * Gets the carriage image view.
     *
     * @return The carriage image view.
     */
    public ImageView getCarriageImageView() {
        return carriageImageView;
    }

    /**
     * Sets the carriage image view.
     *
     * @param carriageImageView The image view to set.
     */
    public void setCarriageImageView(ImageView carriageImageView) {
        this.carriageImageView = carriageImageView;
    }

    /**
     * Gets the carriage name label.
     *
     * @return The carriage name label.
     */
    public Label getCarriageName() {
        return carriageName;
    }

    /**
     * Sets the carriage name label.
     *
     * @param carriageName The label to set.
     */
    public void setCarriageName(Label carriageName) {
        this.carriageName = carriageName;
    }

    /**
     * Gets the carriage resource label.
     *
     * @return The carriage resource label.
     */
    public Label getCarriageResource() {
        return carriageResource;
    }

    /**
     * Sets the carriage resource label.
     *
     * @param carriageResource The label to set.
     */
    public void setCarriageResource(Label carriageResource) {
        this.carriageResource = carriageResource;
    }

    /**
     * Gets the carriage price label.
     *
     * @return The carriage price label.
     */
    public Label getCarriagePrice() {
        return carriagePrice;
    }

    /**
     * Sets the carriage price label.
     *
     * @param carriagePrice The label to set.
     */
    public void setCarriagePrice(Label carriagePrice) {
        this.carriagePrice = carriagePrice;
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
     * @param unknownImagePath The path to set.
     */
    public void setUnknownImagePath(String unknownImagePath) {
        this.unknownImagePath = unknownImagePath;
    }
}