package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.RailwayRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.PauseMenuGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.CreateRailwayLine.SelectInitialStationGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RemoveRailwayLine.ChooseRailwayToRemoveGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.ShowRailwayLine.ChooseRailwayLineGUI;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.UpgradeRailwayLine.ListRailwayLineToUpgradeGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class responsible for handling user interactions related to railway management during a simulation.
 * Provides navigation to create, remove, upgrade, and list railway lines, as well as returning to the pause menu.
 * It also displays the current simulation date and budget.
 */
public class RailwayRelatedGUI implements Initializable {

    private RailwayRelatedController controller;

    @FXML
    private Label actualBudgetLabel;

    @FXML
    private Label actualDateLabel;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url not used
     * @param resourceBundle not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new RailwayRelatedController();
    }

    /**
     * Sets the simulation and updates the GUI with the current date and available budget.
     *
     * @param simulation the current simulation instance
     */
    public void setSimulation(Simulation simulation){
        controller.setSimulation(simulation);

        actualDateLabel.setText(controller.getActualDate());
        actualDateLabel.setAlignment(Pos.CENTER);
        actualDateLabel.setTextAlignment(TextAlignment.CENTER);

        actualBudgetLabel.setText("BUDGET: " + controller.getActualMoney() + " \uD83D\uDCB0");
        actualBudgetLabel.setAlignment(Pos.CENTER);
        actualBudgetLabel.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * Handles the action when the "Create Railway Line" button is pressed.
     * Navigates to the initial station selection screen.
     *
     * @param event the action event
     */
    public void handleCreateRailwayLineButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/CreateRailwayLine/SelectInitialStation.fxml"));
            Parent root = loader.load();

            SelectInitialStationGUI selectInitialStationGUI = loader.getController();
            selectInitialStationGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Select initial Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Remove Railway Line" button is pressed.
     * Navigates to the railway line removal screen.
     *
     * @param event the action event
     */
    public void handleRemoveRailwayLineButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/DeleteRailwayLine/ChooseRailwayToRemove.fxml"));
            Parent root = loader.load();

            ChooseRailwayToRemoveGUI chooseRailwayToRemoveGUI = loader.getController();
            chooseRailwayToRemoveGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Select Station to remove");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Upgrade Railway Line" button is pressed.
     * Navigates to the railway upgrade selection screen.
     *
     * @param event the action event
     */
    public void handleUpgradeRailwayLineButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/UpgradeRailwayLine/ListRailwayLinesUpgrade.fxml"));
            Parent root = loader.load();

            ListRailwayLineToUpgradeGUI listRailwayLineToUpgradeGUI = loader.getController();
            listRailwayLineToUpgradeGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose a Railway Line to Upgrade");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "List Railway Lines" button is pressed.
     * Navigates to the railway line listing screen.
     *
     * @param event the action event
     */
    public void handleListRailwayLineButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/ListRailwayLines/ChooseRailwayLine.fxml"));
            Parent root = loader.load();

            ChooseRailwayLineGUI chooseRailwayLineGUI = loader.getController();
            chooseRailwayLineGUI.setData(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Select Station to View Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Return" button is pressed.
     * Navigates back to the simulation pause menu.
     *
     * @param event the action event
     */
    public void handleReturnButton(ActionEvent event){
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
}



