package pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated;

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
import pt.ipp.isep.dei.controller.simulation.TrainRelated.CarriageRelated.CarriageRelatedController;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.BuyCarriage.BuyCarriageGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.CarriageRelated.ViewCarriages.ListBoughtCarriageGUI;
import pt.ipp.isep.dei.ui.gui.simulation.TrainRelated.TrainRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for carriage-related actions in the simulation.
 * <p>
 * Handles the display and navigation for buying and listing carriages,
 * as well as returning to the main train-related menu.
 */
public class CarriageRelatedGUI implements Initializable {

    private CarriageRelatedController controller;

    @FXML
    private Label actualDateLabel;

    @FXML
    private Label actuaBudgetLabel;

    /**
     * Initializes the controller when the GUI is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new CarriageRelatedController();
    }

    /**
     * Sets the simulation for this GUI and updates the date and budget labels.
     *
     * @param simulation The current {@link Simulation} instance.
     */
    public void setSimulation(Simulation simulation) {
        controller.setSimulation(simulation);

        actualDateLabel.setText(controller.getActualDate());
        actualDateLabel.setAlignment(Pos.CENTER);
        actualDateLabel.setTextAlignment(TextAlignment.CENTER);

        actuaBudgetLabel.setText("BUDGET: " + controller.getActualMoney() + " \uD83D\uDCB0");
        actuaBudgetLabel.setAlignment(Pos.CENTER);
        actuaBudgetLabel.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * Handles the action when the "Buy Carriage" button is clicked.
     * Loads the buy carriage GUI.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    public void handleBuyCarriageButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/CarriageRelated/BuyCarriage/BuyCarriage2.fxml"));
            Parent root = loader.load();
            BuyCarriageGUI buyCarriageGUI = loader.getController();
            buyCarriageGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Buy Carriage");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "List Carriages" button is clicked.
     * Loads the list of acquired carriages GUI.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    public void handleListCarriageButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/CarriageRelated/ViewAcquiredCarriages/ListBoughtCarriage.fxml"));
            Parent root = loader.load();
            ListBoughtCarriageGUI listBoughtCarriageGUI = loader.getController();
            listBoughtCarriageGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: List Acquired Carriages");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Return" button is clicked.
     * Returns to the main train-related GUI.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    public void handleReturnButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/trainRelated/TrainRelated.fxml"));
            Parent root = loader.load();
            TrainRelatedGUI trainRelatedGUI = loader.getController();
            trainRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Train Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the controller for carriage-related actions.
     *
     * @return The {@link CarriageRelatedController} instance.
     */
    public CarriageRelatedController getController() {
        return controller;
    }

    /**
     * Sets the controller for carriage-related actions.
     *
     * @param controller The {@link CarriageRelatedController} to set.
     */
    public void setController(CarriageRelatedController controller) {
        this.controller = controller;
    }

    /**
     * Gets the label displaying the actual date.
     *
     * @return The {@link Label} for the actual date.
     */
    public Label getActualDateLabel() {
        return actualDateLabel;
    }

    /**
     * Sets the label displaying the actual date.
     *
     * @param actualDateLabel The {@link Label} to set for the actual date.
     */
    public void setActualDateLabel(Label actualDateLabel) {
        this.actualDateLabel = actualDateLabel;
    }

    /**
     * Gets the label displaying the actual budget.
     *
     * @return The {@link Label} for the actual budget.
     */
    public Label getActuaBudgetLabel() {
        return actuaBudgetLabel;
    }

    /**
     * Sets the label displaying the actual budget.
     *
     * @param actuaBudgetLabel The {@link Label} to set for the actual budget.
     */
    public void setActuaBudgetLabel(Label actuaBudgetLabel) {
        this.actuaBudgetLabel = actuaBudgetLabel;
    }
}