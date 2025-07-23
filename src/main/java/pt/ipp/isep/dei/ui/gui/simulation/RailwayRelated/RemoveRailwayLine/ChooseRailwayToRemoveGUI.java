package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RemoveRailwayLine;

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
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.RemoveRailwayLine.ChooseRailwayToRemoveController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI class for choosing a railway line to remove in the simulation.
 * Handles the display and selection of railway lines, and navigation to confirmation dialogs.
 * Implements Initializable for JavaFX initialization.
 */
public class ChooseRailwayToRemoveGUI implements Initializable {

    /**
     * Controller for handling the business logic of removing railway lines.
     */
    private ChooseRailwayToRemoveController controller;

    /**
     * Label for displaying error messages to the user.
     */
    @FXML
    private Label errorLabel;

    /**
     * TableView displaying the list of railway lines.
     */
    @FXML
    private TableView<RailwayLine> listRailwayLinesTableView;

    /**
     * TableColumn for displaying the name of the railway line.
     */
    @FXML
    private TableColumn<RailwayLine, String> nameColumn;

    /**
     * TableColumn for displaying the type of the railway line.
     */
    @FXML
    private TableColumn<RailwayLine, String> typeColumn;

    /**
     * Initializes the controller for this GUI.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ChooseRailwayToRemoveController();
    }

    /**
     * Sets the simulation context and initializes the GUI items.
     * @param simulation The simulation to be used in this GUI.
     */
    public void setData(Simulation simulation){
        controller.setSimulation(simulation);
        initializeGUIItems();
    }

    /**
     * Initializes the TableView columns and populates the table with railway lines.
     */
    private void initializeGUIItems() {
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNameStation1() + " ⇄ " + cellData.getValue().getNameStation2())
        );
        typeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRailwayType().getType())
        );
        listRailwayLinesTableView.setItems(controller.getRailwayList());
    }

    /**
     * Handles the event when the select button is pressed.
     * Opens a confirmation dialog for removing the selected railway line.
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleSelectButton(ActionEvent event){
        if (listRailwayLinesTableView.getSelectionModel().getSelectedItem() == null ){
            errorLabel.setText("Choose a Railway line first!!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/message/PurchaseConfirmation2.fxml"));
            // set controller
            RemoveConfirmationPopGUI removeConfirmationPopGUI = new RemoveConfirmationPopGUI();
            loader.setController(removeConfirmationPopGUI);
            Parent root = loader.load();
            RemoveConfirmationPopGUI removeConfirmationGUI = loader.getController();
            removeConfirmationGUI.setData(controller.getSimulation(),  listRailwayLinesTableView.getSelectionModel().getSelectedItem(), (Stage) ((Node) event.getSource()).getScene().getWindow());
            removeConfirmationGUI.initializeGUIItems("Remove Railway Line", controller.getStations(listRailwayLinesTableView.getSelectionModel().getSelectedItem()), "Are you Sure?");
            Stage stage = new Stage();
            stage.setTitle("MABEC - Simulation: Remove Railway Line Confirmation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the exit button is pressed.
     * Navigates back to the Railway Related GUI.
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleExitButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/RailwayLineRelated.fxml"));
            Parent root = loader.load();
            RailwayRelatedGUI railwayRelatedGUI = loader.getController();
            railwayRelatedGUI.setSimulation(controller.getSimulation());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Railway Related");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
