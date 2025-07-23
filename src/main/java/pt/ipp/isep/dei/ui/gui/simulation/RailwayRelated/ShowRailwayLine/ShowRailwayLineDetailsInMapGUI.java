package pt.ipp.isep.dei.ui.gui.simulation.RailwayRelated.ShowRailwayLine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.RailwayRelated.ShowRailwayLine.ShowRailwayLineDetailsInMapController;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Simulation.Simulation;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller para mostrar detalhes de uma RailwayLine no mapa.
 */
public class ShowRailwayLineDetailsInMapGUI implements Initializable {

    private ShowRailwayLineDetailsInMapController controller;

    @FXML
    private Label connectionNameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label distanceLabel;

    @FXML
    private Label constructionDateLabel;

    @FXML
    private Label nextMaintenanceDateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ShowRailwayLineDetailsInMapController();
    }

    public void setRailwayLine(RailwayLine railwayLine, Simulation simulation) {
        controller.setRailwayLine(railwayLine);
        controller.setSimulation(simulation);

        connectionNameLabel.setText("Connection: " + controller.getConnectionName());
        typeLabel.setText("Type: " + controller.getType());
        distanceLabel.setText("Distance: " + controller.getDistance());
        constructionDateLabel.setText("Construction Date: " + controller.getConstructionDate());
        nextMaintenanceDateLabel.setText("Next Maintenance Date: " + controller.getNextMaintenanceDate());
    }

    @FXML
    private void handleClose(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
