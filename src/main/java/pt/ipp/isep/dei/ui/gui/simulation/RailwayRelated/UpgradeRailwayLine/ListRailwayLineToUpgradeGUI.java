package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.UpgradeRailwayLine;

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
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.UpgradeRailway.ListRailwayLineToUpgradeController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.RailwayRelatedGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListRailwayLineToUpgradeGUI implements Initializable {

    private ListRailwayLineToUpgradeController controller;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<RailwayLine> listRailwayLinesTableView;

    @FXML
    private TableColumn<RailwayLine, String> nameColumn;

    @FXML
    private TableColumn<RailwayLine, String> typeColumn;

    /**
     * Initializes the controller for this GUI.
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ListRailwayLineToUpgradeController();
    }

    /**
     * Sets the simulation context and initializes the GUI items.
     * @param simulation The simulation to be used in this GUI.
     */
    public void setData(Simulation simulation){
        controller.setSimulation(simulation);
        initialiseGUIItems();
    }

    /**
     * Initializes the TableView columns and populates the table with railway lines.
     */
    public void initialiseGUIItems(){
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNameStation1() + " ⇄ " + cellData.getValue().getNameStation2())
        );
        typeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRailwayType().getType())
        );
        listRailwayLinesTableView.setItems(controller.getRailwayList());
    }

    /**
     * Handles the event when the back button is pressed.
     * Navigates back to the Railway Related GUI.
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleBackButtonOnAction(ActionEvent event){
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

    /**
     * Handles the event when the select button is pressed.
     * Opens the upgrade type selection for the selected railway line, if available.
     * @param event The ActionEvent triggered by the button press.
     */
    public void handleSelectButton(ActionEvent event){
        if (listRailwayLinesTableView.getSelectionModel().getSelectedItem() == null ){
            errorLabel.setText("Choose a Railway line first!!");
            return;
        }
        if (listRailwayLinesTableView.getSelectionModel().getSelectedItem().getRailwayType() == RailwayLineType.DOUBLE_ELECTRIFIED){
            errorLabel.setText("No more upgrades available!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/railwayLineRelated/UpgradeRailwayLine/ChooseNewRailwayType.fxml"));
            Parent root = loader.load();
            ChooseNewRailwayTypeGUI chooseNewRailwayTypeGUI = loader.getController();
            chooseNewRailwayTypeGUI.setData(controller.getSimulation(), listRailwayLinesTableView.getSelectionModel().getSelectedItem() );
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose the new Railway Line Type!!");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

