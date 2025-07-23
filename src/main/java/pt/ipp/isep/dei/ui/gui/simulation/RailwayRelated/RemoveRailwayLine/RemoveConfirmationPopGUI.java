package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RemoveRailwayLine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.RemoveRailwayLine.RemoveConfirmationPopController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for the confirmation popup when removing a railway line.
 * Handles user confirmation and navigation after the removal action.
 * Implements Initializable for JavaFX initialization.
 */
public class RemoveConfirmationPopGUI implements Initializable {

    private RemoveConfirmationPopController controller;

    private Stage oldStage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label text1Label;

    @FXML
    private Label text2Label;

    /**
     * Initializes the controller for this GUI.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new RemoveConfirmationPopController();
    }

    /**
     * Sets the simulation, selected railway line, and previous stage for this popup.
     *
     * @param simulation      The simulation context.
     * @param selectedRailway The selected railway line to remove.
     * @param oldStage        The previous stage to return to after confirmation.
     */
    public void setData(Simulation simulation, RailwayLine selectedRailway, Stage oldStage) {
        controller.setSimulation(simulation);
        controller.setRailwayLine(selectedRailway);

        this.oldStage = oldStage;
    }

    /**
     * Initializes the popup labels with the provided text.
     *
     * @param title The title of the popup.
     * @param txt1  The first line of text.
     * @param txt2  The second line of text.
     */
    public void initializeGUIItems(String title, String txt1, String txt2) {
        titleLabel.setText(title);
        text1Label.setText(txt1);
        text2Label.setText(txt2);
    }

    /**
     * Handles the event when the 'No' button is pressed.
     * Closes the confirmation popup without removing the railway line.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleNoButtonOnAction(ActionEvent event) {
        Stage popStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popStage.close();
    }

    /**
     * Handles the event when the 'Yes' button is pressed.
     * Removes the railway line and navigates back to the Railway Related GUI.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleYesButtonOnAction(ActionEvent event) {
        controller.removeRailwayLine();

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
}
