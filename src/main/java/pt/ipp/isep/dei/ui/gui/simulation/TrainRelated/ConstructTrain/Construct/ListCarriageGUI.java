package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.Construct;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.TrainRelated.ConstructTrain.Construct.ListCarriageController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.ConstructTrain.ConstructTrainRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GUI controller for listing and selecting carriages to construct a train.
 * Handles user interactions for selecting, saving, and canceling carriage selection.
 */
public class ListCarriageGUI implements Initializable {

    private String unknownImagePath = "/images/Carriage/UnknownImageCarriage.png";
    private ListCarriageController controller;
    private List<Carriage> selectedCarriages = new ArrayList<>();

    @FXML
    private Button selectCarriageButton;

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

    @FXML
    private Stage stage;

    @FXML
    private Label carriagesLeftChoose;

    @FXML
    private Button saveButton;

    /**
     * Initializes the controller class.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ListCarriageController();
    }

    /**
     * Sets the simulation and initializes GUI items.
     *
     * @param simulation The simulation to set.
     */
    @FXML
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Sets the data for the GUI, including simulation, locomotive, and stage.
     *
     * @param simulation The simulation to set.
     * @param locomotive The locomotive to set.
     * @param stage      The stage to set.
     */
    public void setData(Simulation simulation, Locomotive locomotive, Stage stage) {
        controller.setSimulation(simulation);
        controller.setLocomotive(locomotive);
        this.stage = stage;
        carriagesLeftChoose.setText(String.valueOf(locomotive.getMaxCarriages()));
        initializeGUIItems();
    }

    /**
     * Initializes the GUI items, including table columns and listeners.
     */
    private void initializeGUIItems() {
        carriageImageView.setImage(new Image(getClass().getResource(unknownImagePath).toExternalForm()));
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        priceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAcquisitionCost() + " \uD83D\uDCB0")
        );
        listCarriageTableView.setItems(controller.getAcquiredCarriagesList());
        listCarriageTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateCarriageDetails(newVal);
            }
        });
    }

    /**
     * Updates the label showing the number of carriages left to choose.
     */
    private void updateCarriagesLeftLabel() {
        int max = controller.getLocomotive().getMaxCarriages();
        int remaining = max - selectedCarriages.size();
        carriagesLeftChoose.setText(String.valueOf(remaining));
        if (remaining == 0) {
            selectCarriageButton.setVisible(false);
        }
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
     * Handles the action when the save button is clicked.
     * Opens a confirmation popup for train creation.
     *
     * @param event The action event.
     */
    @FXML
    public void handleSaveCarriageButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/ConstructTrainRelated/ConstructTrain/ConstructTrainPurchaseConfirmationPopup.fxml"));
            Parent root = loader.load();
            ConstructTrainPurchaseConfirmationPopupGUI constructTrainPurchaseConfirmationPopupGUI = loader.getController();
            constructTrainPurchaseConfirmationPopupGUI.setData(controller.getSimulation(), controller.getLocomotive(), selectedCarriages, stage);
            constructTrainPurchaseConfirmationPopupGUI.initializeGUIItems("Confirm Train Creation", "Are you sure you want to create this train?", "It will cost you nothing! 😄");
            Stage popupStage = new Stage();
            popupStage.setTitle(" \uD83E\uDD14 Confirm Train Creation");
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);
            popupStage.initOwner(stage);
            popupStage.centerOnScreen();
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the select carriage button is clicked.
     * Adds the selected carriage to the list and updates the GUI.
     *
     * @param event The action event.
     */
    @FXML
    public void handleSelectCarriageButton(ActionEvent event) {
        Carriage selected = listCarriageTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("You need to select a Carriage first!!");
            return;
        }
        if (!selectedCarriages.contains(selected)) {
            selectedCarriages.add(selected);
            listCarriageTableView.getItems().remove(selected);
            updateCarriagesLeftLabel();
            saveButton.setVisible(true);
            errorLabel.setText("");
        }
        resetCarriageDetailsToDefault();
        listCarriageTableView.getSelectionModel().clearSelection();
    }

    /**
     * Resets the carriage details section to default values.
     */
    private void resetCarriageDetailsToDefault() {
        carriageName.setText("❓");
        carriageName.setStyle("-fx-text-fill: red;");
        carriageResource.setText("❓");
        carriageResource.setStyle("-fx-text-fill: red;");
        carriagePrice.setText("❓");
        carriagePrice.setStyle("-fx-text-fill: red;");
        carriageImageView.setImage(new Image(getClass().getResource(unknownImagePath).toExternalForm()));
    }

    /**
     * Handles the action when the cancel button is clicked.
     * Navigates back to the ConstructTrainRelated GUI.
     *
     * @param event The action event.
     */
    @FXML
    public void handleCancelButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/ConstructTrainRelated/ConstructTrainsRelated.fxml"));
            Parent root = loader.load();
            ConstructTrainRelatedGUI constructTrainRelatedGUI = loader.getController();
            constructTrainRelatedGUI.setSimulation(controller.getSimulation());
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
     * Gets the ListCarriageController.
     *
     * @return The ListCarriageController.
     */
    public ListCarriageController getController() {
        return controller;
    }

    /**
     * Sets the ListCarriageController.
     *
     * @param controller The ListCarriageController to set.
     */
    public void setController(ListCarriageController controller) {
        this.controller = controller;
    }

    /**
     * Gets the list of selected carriages.
     *
     * @return The list of selected carriages.
     */
    public List<Carriage> getSelectedCarriages() {
        return selectedCarriages;
    }

    /**
     * Sets the list of selected carriages.
     *
     * @param selectedCarriages The list of selected carriages to set.
     */
    public void setSelectedCarriages(List<Carriage> selectedCarriages) {
        this.selectedCarriages = selectedCarriages;
    }

    /**
     * Gets the select carriage button.
     *
     * @return The select carriage button.
     */
    public Button getSelectCarriageButton() {
        return selectCarriageButton;
    }

    /**
     * Sets the select carriage button.
     *
     * @param selectCarriageButton The select carriage button to set.
     */
    public void setSelectCarriageButton(Button selectCarriageButton) {
        this.selectCarriageButton = selectCarriageButton;
    }

    /**
     * Gets the table view of carriages.
     *
     * @return The table view of carriages.
     */
    public TableView<Carriage> getListCarriageTableView() {
        return listCarriageTableView;
    }

    /**
     * Sets the table view of carriages.
     *
     * @param listCarriageTableView The table view of carriages to set.
     */
    public void setListCarriageTableView(TableView<Carriage> listCarriageTableView) {
        this.listCarriageTableView = listCarriageTableView;
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
     * @param carriageImageView The carriage image view to set.
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
     * @param carriageName The carriage name label to set.
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
     * @param carriageResource The carriage resource label to set.
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
     * @param carriagePrice The carriage price label to set.
     */
    public void setCarriagePrice(Label carriagePrice) {
        this.carriagePrice = carriagePrice;
    }

    /**
     * Gets the stage.
     *
     * @return The stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the label for carriages left to choose.
     *
     * @return The carriages left choose label.
     */
    public Label getCarriagesLeftChoose() {
        return carriagesLeftChoose;
    }

    /**
     * Sets the label for carriages left to choose.
     *
     * @param carriagesLeftChoose The carriages left choose label to set.
     */
    public void setCarriagesLeftChoose(Label carriagesLeftChoose) {
        this.carriagesLeftChoose = carriagesLeftChoose;
    }

    /**
     * Gets the save button.
     *
     * @return The save button.
     */
    public Button getSaveButton() {
        return saveButton;
    }

    /**
     * Sets the save button.
     *
     * @param saveButton The save button to set.
     */
    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }
}