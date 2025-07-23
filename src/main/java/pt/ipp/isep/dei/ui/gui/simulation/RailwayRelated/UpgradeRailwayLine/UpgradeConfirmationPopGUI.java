package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.UpgradeRailwayLine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway.UpgradeConfirmationPopController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeConfirmationPopGUI implements Initializable {

    private UpgradeConfirmationPopController controller;

    private Stage oldStage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label text1Label;

    @FXML
    private Label text2Label;

    /**
     * Initializes the controller for this GUI.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new UpgradeConfirmationPopController();
    }

    /**
     * Sets the simulation, selected railway line, new railway line type, upgrade cost, and previous stage for this popup.
     * @param simulation The simulation context.
     * @param selectedRailway The selected railway line to upgrade.
     * @param railwayLineType The new railway line type to set.
     * @param upgradeCost The cost of the upgrade.
     * @param oldStage The previous stage to return to after confirmation.
     */
    public void setData(Simulation simulation, RailwayLine selectedRailway, RailwayLineType railwayLineType, int upgradeCost, Stage oldStage){
        controller.setSimulation(simulation);
        controller.setRailwayLine(selectedRailway);
        controller.setNewRailwayLineType(railwayLineType);
        controller.setUpgradeCost(upgradeCost);

        this.oldStage = oldStage;
    }

    /**
     * Initializes the popup labels with the provided text.
     * @param title The title of the popup.
     * @param txt1 The first line of text.
     * @param txt2 The second line of text.
     */
    public void initializeGUIItems(String title, String txt1, String txt2){
        titleLabel.setText(title);
        text1Label.setText(txt1);
        text2Label.setText(txt2);
    }

    /**
     * Handles the event when the 'Yes' button is pressed.
     * Upgrades the railway line and navigates back to the Railway Related GUI.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void handleYesButtonOnAction(ActionEvent event){
        controller.modifyRailwayLineType();

        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/RailwayLineRelated.fxml"));
            Parent root = loader.load();

            RailwayRelatedGUI railwayRelatedGUI = loader.getController();
            railwayRelatedGUI.setSimulation(controller.getSimulation());

            oldStage.setTitle("MABEC - Simulation: Railway Line Related");
            oldStage.setScene(new Scene(root));
            oldStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the event when the 'No' button is pressed.
     * Closes the confirmation popup without upgrading the railway line.
     * @param event The ActionEvent triggered by the button press.
     */
    @FXML
    public void handleNoButtonOnAction(ActionEvent event){
        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
    }
}
