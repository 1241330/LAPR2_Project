package pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.ManageRoute;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RouteRelated.ManageRoute.ChooseTrainForRouteController;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Simulation.Route;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.ui.gui.simulation.RouteRelated.RouteRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for choosing a train to activate a specific route in a simulation.
 * <p>
 * This class handles the user interface for selecting a train, displaying its details,
 * and confirming the activation of a route for that train.
 */
public class ChooseTrainForRouteGUI implements Initializable {

    private ChooseTrainForRouteController controller;

    @FXML
    private transient TableView<Train> trainTableView;

    @FXML
    private transient TableColumn<Train, String> nameColumn;

    @FXML
    private transient TableColumn<Train, String> inventorySpaceColumn;

    @FXML private ImageView trainImageView;
    @FXML private Label trainName, trainLocomotive, trainInventorySpace, errorLabel;
    @FXML private transient ListView<String> trainCarriageListView;
    @FXML private transient ListView<String> trainInventoryListView;

    /**
     * Initializes the GUI controller and the associated business controller.
     *
     * @param url            The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseTrainForRouteController();
    }

    /**
     * Sets the simulation and route data needed for the GUI and initializes GUI components.
     *
     * @param simulation The current simulation.
     * @param route      The route for which a train should be chosen.
     */
    public void setData(Simulation simulation, Route route){
        controller.setSimulation(simulation);
        controller.setRoute(route);

        initializeGUIItems();
    }

    /**
     * Initializes GUI elements such as table columns, disables horizontal scroll bars,
     * loads available trains into the table, and sets a listener for train selection changes.
     */
    private void initializeGUIItems() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        inventorySpaceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaxInventorySpace() +""));

        // Deactivate horizontal scroll bar
        Platform.runLater(() -> {
            trainTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            for (Node node : trainTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar scrollBar && scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                    scrollBar.setDisable(true);
                    scrollBar.setOpacity(0);
                    scrollBar.setPrefHeight(0);
                    scrollBar.setMaxHeight(0);
                }
            }
        });

        trainTableView.setItems(controller.getAvailableTrains());

        trainTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal != null) updateTrainDetails(newVal);
        });
    }

    /**
     * Updates the detailed view with information about the selected train, including
     * name, locomotive, carriages, inventory items, inventory space, and image.
     *
     * @param train The train selected by the user.
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
            trainInventoryListView.getItems().add(r.getResourceType() + " - " + r.getQuantity() + " kg");
        }
        trainInventoryListView.setStyle("-fx-text-fill: black;");

        trainInventorySpace.setText(train.getMaxInventorySpace() + " kg");
        trainInventorySpace.setStyle("-fx-text-fill: black;");

        trainImageView.setImage(new Image(getClass().getResource(train.getLocomotive().getImagePath()).toExternalForm()));
    }

    /**
     * Handles the action triggered by the "Select" button.
     * <p>
     * Validates train selection and, if valid, opens a confirmation popup to activate the route for the selected train.
     *
     * @param event The action event from the button click.
     */
    public void handleSelectButtonOnAction(ActionEvent event){
        if (trainTableView.getSelectionModel().getSelectedItem() == null){
            errorLabel.setText("Select a train first!!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/PurchaseConfirmation2.fxml"));
            ConfirmRouteActivationPopGUI confirmationPopGUI = new ConfirmRouteActivationPopGUI();
            loader.setController(confirmationPopGUI);

            Parent root = loader.load();

            ConfirmRouteActivationPopGUI confirmRouteActivationPopGUI = loader.getController();
            confirmRouteActivationPopGUI.setData(controller.getSimulation(), controller.getRoute(),
                    trainTableView.getSelectionModel().getSelectedItem(),
                    (Stage) ((Node) event.getSource()).getScene().getWindow());

            confirmRouteActivationPopGUI.initializeGUIItems(
                    "Activate Route",
                    "The train: " + trainTableView.getSelectionModel().getSelectedItem().getName() + " will be activated too",
                    "This route will stay active until you deactivate it"
            );
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Create Route Confirmation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the "Cancel" button.
     * <p>
     * Returns the user to the main Route Related GUI.
     *
     * @param event The action event from the button click.
     */
    public void handleCancelButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/RouteRelated.fxml"));
            Parent root = loader.load();

            RouteRelatedGUI routeRelatedGUI = loader.getController();
            routeRelatedGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Route Related");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the "Back" button.
     * <p>
     * Returns the user to the "Manage Routes" screen.
     *
     * @param event The action event from the button click.
     */
    public void handleBackButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/routeRelated/MenageRoute/ChooseRoute.fxml"));
            Parent root = loader.load();

            ChooseRouteGUI chooseRouteGUI = loader.getController();
            chooseRouteGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Menage Routes");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
