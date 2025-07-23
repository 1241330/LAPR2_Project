package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.UpgradeRailwayLine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway.ChooseNewRailwayTypeController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseNewRailwayTypeGUI implements Initializable {

    private ChooseNewRailwayTypeController controller;

    @FXML
    private Label errorLabel;

    @FXML
    private GridPane upgradeGridPane;

    @FXML
    private Label sePriceLabel;

    @FXML
    private Label dnePriceLabel;

    @FXML
    private Label dePriceLabel;

    /**
     * Initializes the controller for this GUI.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseNewRailwayTypeController();
    }

    /**
     * Sets the simulation and railway line for this GUI and initializes the GUI items.
     * @param simulation The simulation context.
     * @param railwayLine The railway line to upgrade.
     */
    public void setData(Simulation simulation, RailwayLine railwayLine){
        controller.setSimulation(simulation);
        controller.setRailwayLine(railwayLine);

        initializeGUIItems();
    }

    /**
     * Initializes the GUI items, setting the price labels and hiding rows as needed based on the railway type.
     */
    public void initializeGUIItems(){
        sePriceLabel.setText(controller.getSEPrice() + " \uD83D\uDCB0");
        dnePriceLabel.setText(controller.getDNEPrice() + " \uD83D\uDCB0");
        dePriceLabel.setText(controller.getDEPrice() + " \uD83D\uDCB0");

        if (controller.getRailwayLine().getRailwayType() == RailwayLineType.DOUBLE_NON_ELECTRIFIED){
            hideRow(upgradeGridPane,2);
            hideRow(upgradeGridPane,1);
        } else if (controller.getRailwayLine().getRailwayType() == RailwayLineType.SINGLE_ELECTRIFIED){
            hideRow(upgradeGridPane,2);
            hideRow(upgradeGridPane,1);
        }
    }

    /**
     * Hides a specific row in the given GridPane.
     * @param gridPane The GridPane to modify.
     * @param targetRow The row index to hide.
     */
    public void hideRow(GridPane gridPane, int targetRow) {
        for (Node node : gridPane.getChildren()) {
            Integer row = GridPane.getRowIndex(node);

            // Se o index não está definido, assume 0
            if (row == null) {
                row = 0;
            }

            if (row == targetRow) {
                node.setVisible(false);
                node.setManaged(false);
            }
        }
    }

    /**
     * Handles the event when the back button is pressed. Navigates back to the list of railway lines to upgrade.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    private void handleBackButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/UpgradeRailwayLine/ListRailwayLinesUpgrade.fxml"));
            Parent root = loader.load();

            ListRailwayLineToUpgradeGUI listRailwayLineToUpgradeGUI = loader.getController();
            listRailwayLineToUpgradeGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Railway Line to Upgrade");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the exit button is pressed. Navigates to the pause menu.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    private void handleExitButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/PauseMenu.fxml"));
            Parent root = loader.load();

            PauseMenuGUI pauseMenuGUI = loader.getController();
            pauseMenuGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Pause Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the Double Electrified option is selected. Opens the confirmation popup.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void handleDeSelectButtonOnAction(ActionEvent event){
        createConfirmationPopup(RailwayLineType.DOUBLE_ELECTRIFIED, event);
    }

    /**
     * Handles the event when the Double Non-Electrified option is selected. Opens the confirmation popup.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void handleDneSelectButtonOnAction(ActionEvent event){
        createConfirmationPopup(RailwayLineType.DOUBLE_NON_ELECTRIFIED, event);
    }

    /**
     * Handles the event when the Single Electrified option is selected. Opens the confirmation popup.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void handleSeSelectButtonOnAction(ActionEvent event){
        createConfirmationPopup(RailwayLineType.SINGLE_ELECTRIFIED, event);
    }

    /**
     * Creates and shows the confirmation popup for upgrading the railway line.
     * @param railwayLineType The type to upgrade to.
     * @param event The ActionEvent triggered by the button press.
     */
    private void createConfirmationPopup(RailwayLineType railwayLineType, ActionEvent event){
        if (!controller.verifyIfHasMoney(railwayLineType)){
            errorLabel.setText("You dont have enough Money! Total Cost: " + controller.getUpgradeCost(railwayLineType) + " \uD83D\uDCB0");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/PurchaseConfirmation2.fxml"));
            UpgradeConfirmationPopGUI upgradeConfirmationPopGUI1 = new UpgradeConfirmationPopGUI();
            loader.setController(upgradeConfirmationPopGUI1);
            Parent root = loader.load();
            UpgradeConfirmationPopGUI upgradeConfirmationPopGUI = loader.getController();
            upgradeConfirmationPopGUI.setData(controller.getSimulation(),  controller.getRailwayLine(), railwayLineType, controller.getUpgradeCost(railwayLineType),(Stage) ((Node) event.getSource()).getScene().getWindow());
            upgradeConfirmationPopGUI.initializeGUIItems("Upgrade Railway Line", controller.getStations(controller.getRailwayLine()), "The upgrade will cost: " + controller.getUpgradeCost(railwayLineType) + " \uD83D\uDCB0!");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Upgrade Railway Line Confirmation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

