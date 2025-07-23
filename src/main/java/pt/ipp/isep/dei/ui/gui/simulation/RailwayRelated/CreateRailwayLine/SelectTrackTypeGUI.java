package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.CreateRailwayLine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.CreateRailwayLine.SelectTrackTypeController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class responsible for displaying the available railway line types
 * for creation during a simulation. Allows users to select one, based on cost and available funds.
 * Interacts with the {@link SelectTrackTypeController} to retrieve simulation data and prices.
 */
public class SelectTrackTypeGUI implements Initializable {

    private SelectTrackTypeController controller;

    @FXML
    private Label snePriceLabel;

    @FXML
    private Label sePriceLabel;

    @FXML
    private Label dnePriceLabel;

    @FXML
    private Label dePriceLabel;

    @FXML
    private Label errorLabel;

    /**
     * Initializes the GUI and sets up the controller and interface values.
     *
     * @param url not used
     * @param resourceBundle not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new SelectTrackTypeController();
        setGUIInformation();
    }

    /**
     * Sets the simulation and the two stations (origin and destination) for the railway line.
     *
     * @param simulation      the current simulation
     * @param beginningStation the starting station
     * @param endStation       the ending station
     */
    public void setData(Simulation simulation, Station beginningStation, Station endStation){
        controller.setSimulation(simulation);
        controller.setBeginningStation(beginningStation);
        controller.setArrivalStation(endStation);
    }

    /**
     * Handles selection of the Single Non-Electrified track type.
     * If the user has sufficient funds, opens the confirmation popup.
     *
     * @param event the action event
     */
    public void handleSneSelectButtonOnAction(ActionEvent event){
        if (!controller.verifyIfHasMoney(RailwayLineType.SINGLE_NON_ELECTRIFIED)){
            errorLabel.setText("You don't have enough money!");
            return;
        }
        callVerificationPopup(event, RailwayLineType.SINGLE_NON_ELECTRIFIED);
    }

    /**
     * Handles selection of the Single Electrified track type.
     *
     * @param event the action event
     */
    public void handleSeSelectButtonOnAction(ActionEvent event){
        if(!controller.verifyIfHasMoney(RailwayLineType.SINGLE_ELECTRIFIED)){
            errorLabel.setText("You don't have enough money!");
            return;
        }
        callVerificationPopup(event, RailwayLineType.SINGLE_ELECTRIFIED);
    }

    /**
     * Handles selection of the Double Non-Electrified track type.
     *
     * @param event the action event
     */
    public void handleDneSelectButtonOnAction(ActionEvent event){
        if(!controller.verifyIfHasMoney(RailwayLineType.DOUBLE_NON_ELECTRIFIED)){
            errorLabel.setText("You don't have enough money!");
            return;
        }
        callVerificationPopup(event ,RailwayLineType.DOUBLE_NON_ELECTRIFIED);
    }

    /**
     * Handles selection of the Double Electrified track type.
     *
     * @param event the action event
     */
    public void handleDeSelectButton(ActionEvent event){
        if(!controller.verifyIfHasMoney(RailwayLineType.DOUBLE_ELECTRIFIED)){
            errorLabel.setText("You don't have enough money!");
            return;
        }
        callVerificationPopup(event, RailwayLineType.DOUBLE_ELECTRIFIED);
    }

    /**
     * Handles the action to return to the pause menu screen.
     *
     * @param event the action event
     */
    public void handleExitButton(ActionEvent event){
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
     * Handles the action to go back to the previous screen (end station selection).
     *
     * @param event the action event
     */
    public void handleBackButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/CreateRailwayLine/SelectEndStation.fxml"));
            Parent root = loader.load();

            SelectEndStationGUI selectEndStationGUI = loader.getController();
            selectEndStationGUI.setData(controller.getSimulation(), controller.getBeginningStation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose final Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the confirmation popup for the selected railway line type.
     *
     * @param event            the action event
     * @param railwayLineType the type of railway line selected
     */
    private void callVerificationPopup(ActionEvent event, RailwayLineType railwayLineType) {
        try {
            boolean isElectric = railwayLineType.getId() == 0 || railwayLineType.getId() == 1;
            boolean isDoubleTrack = railwayLineType.getId() == 1 || railwayLineType.getId() == 3;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/PurchaseConfirmation2.fxml"));
            PurchaseConfirmationPopupGUI purchaseConfirmationPopupGUI = new PurchaseConfirmationPopupGUI();
            loader.setController(purchaseConfirmationPopupGUI);

            Parent root = loader.load();

            PurchaseConfirmationPopupGUI purchaseConfirmationGUI = loader.getController();
            purchaseConfirmationGUI.setData(
                    controller.getSimulation(),
                    controller.getBeginningStation(),
                    controller.getArrivalStation(),
                    controller.getPath(railwayLineType),
                    railwayLineType,
                    controller.returnRailwayCost(controller.getPath(railwayLineType), isDoubleTrack, isElectric),
                    (Stage) ((Node) event.getSource()).getScene().getWindow()
            );

            purchaseConfirmationGUI.initializeGUIItems(
                    "Create Railway Line",
                    controller.getBeginningStation().getName() + " ⟷ " + controller.getArrivalStation().getName(),
                    "Cost: " + controller.returnRailwayCost(controller.getPath(railwayLineType), isDoubleTrack, isElectric) + " \uD83D\uDCB0"
            );

            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Purchase Confirmation Locomotive");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the labels on the GUI with the corresponding prices for each railway line type.
     */
    private void setGUIInformation() {
        snePriceLabel.setText(controller.getSNEPrice() + "\uD83D\uDCB0");
        sePriceLabel.setText(controller.getSEPrice() + "\uD83D\uDCB0");
        dnePriceLabel.setText(controller.getDNEPrice() + "\uD83D\uDCB0");
        dePriceLabel.setText(controller.getDEPrice() + "\uD83D\uDCB0");
    }
}
