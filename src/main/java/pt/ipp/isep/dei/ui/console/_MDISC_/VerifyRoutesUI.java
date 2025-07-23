package pt.ipp.isep.dei.ui.console._MDISC_;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller._MDISC_.ReadCSV_Controller;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UI class responsible for verifying routes.
 * Allows the user to interact with different functionalities related to railway routes,
 * such as viewing matrices, checking connectivity, maintenance, and finding shortest routes.
 */
public class VerifyRoutesUI implements Runnable {

    /**
     * Runs the VerifyRoutesUI logic.
     * Handles the flow for reading CSV files and presenting the user with route-related options.
     */
    @Override
    public void run() {
        ReadCSV_UI readCSVUI = new ReadCSV_UI();
        readCSVUI.run();

        if (!ReadCSV_Controller.isValidRead()) {
            runPlayerGUI();
            return;
        }

        while (true) {
            List<Station> stationList = ReadCSV_Controller.getStationsList();
            List<RailwayLine> railwayLines = ReadCSV_Controller.getLinesList();
            List<MenuItem> options = new ArrayList<>();

            options.add(new MenuItem(
                    "View Adjacency Matrix",
                    new ViewAdjacencyMatrixUI(stationList, railwayLines, ReadCSV_Controller.getFileName())
            ));
            options.add(new MenuItem(
                    "View Transitive Matrix",
                    new ViewTransitiveMatrixUI(stationList, railwayLines, ReadCSV_Controller.getFileName())
            ));
            options.add(new MenuItem(
                    "Connectivity between Railway Lines",
                    new ConnectivityBetweenStationsUI(stationList, railwayLines, ReadCSV_Controller.getFileName())
            ));
            options.add(new MenuItem(
                    "Maintenance of Railway Lines",
                    new MaintenanceRailwayLineUI(stationList, railwayLines, ReadCSV_Controller.getFileName())
            ));
            options.add(new MenuItem(
                    "Find Shortest route",
                    new FindShortestRouteUI(stationList, railwayLines)
            ));

            int option = Utils.chooseOptionPrintMenuAndManualReturn(
                    "Verify Routes",
                    Utils.convertObjectsToDescriptions(options),
                    "Return",
                    "Choice"
            );

            if (option == 0) {
                runPlayerGUI();
                return;
            }
            options.get(option - 1).run();
        }
    }

    /**
     * Launches the Player Mode GUI using JavaFX.
     * If an IOException occurs, prints the stack trace.
     */
    private void runPlayerGUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu/PlayerMode.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("MABEC - Player Mode");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}