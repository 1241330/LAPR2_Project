package pt.ipp.isep.dei.ui.gui.simulation.InitializeSimulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.TypesSimulationSelectionController;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.ui.gui.message.ErrorMessageGUI;
import pt.ipp.isep.dei.ui.gui.simulation.ChooseSimulationGUI;
import pt.ipp.isep.dei.ui.gui.simulation.CreateSimulationGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI controller for selecting the type of simulation operation (create, choose, or delete) for a given scenario.
 * Handles navigation between simulation setup screens and error display for simulation selection.
 */
public class TypesSimulationSelectionGUI implements Initializable {

    /**
     * Controller responsible for business logic related to simulation type selection.
     */
    private TypesSimulationSelectionController controller;

    /**
     * Initializes the controller for simulation type selection.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new TypesSimulationSelectionController();
    }

    /**
     * Sets the scenario for which the simulation type is being selected.
     * @param scenario The scenario to set.
     */
    public void setScenario(Scenario scenario) {
        controller.setSelectedScenario(scenario);
    }

    /**
     * Handles the action of the Back button, returning to the scenario selection screen.
     * @param event The ActionEvent triggered by clicking the Back button.
     */
    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/ChooseScenario.fxml"));
            Parent root = loader.load();
            ChooseScenarioGUI chooseScenarioGUI = loader.getController();
            chooseScenarioGUI.setSelectedMap(controller.getMapFromScenario());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Simulation: Choose Scenario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Create Simulation button. Navigates to the create simulation screen.
     * @param event The ActionEvent triggered by clicking the Create Simulation button.
     */
    @FXML
    public void handleCreateSimulationButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/CreateSimulation.fxml"));
            Parent root = loader.load();
            CreateSimulationGUI createSimulationGUI = loader.getController();
            createSimulationGUI.setSelectedScenario(controller.getSelectedScenario());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("MABEC - Create Simulation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Choose Simulation button. Navigates to the choose simulation screen or shows an error if none exist.
     * @param event The ActionEvent triggered by clicking the Choose Simulation button.
     */
    @FXML
    public void handleChooseSimulationButton(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/ChooseSimulation.fxml"));
            Parent root = loader.load();
            ChooseSimulationGUI chooseSimulationGUI = loader.getController();
            chooseSimulationGUI.setInformation(controller.getSelectedScenario(),true);
            if (chooseSimulationGUI.confirmationContinueAction()) {
                stage.setTitle("MABEC - Choose Simulation");
                stage.setScene(new Scene(root));
                stage.show();
            } else dontExistSimulationsMessageError(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the Delete Simulation button. Navigates to the delete simulation screen or shows an error if none exist.
     * @param event The ActionEvent triggered by clicking the Delete Simulation button.
     */
    @FXML
    public void handleDeleteSimulationButton(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation/ChooseSimulation.fxml"));
            Parent root = loader.load();
            ChooseSimulationGUI chooseSimulationGUI = loader.getController();
            chooseSimulationGUI.setInformation(controller.getSelectedScenario(),false);
            if (chooseSimulationGUI.confirmationContinueAction()) {
                stage.setTitle("MABEC - Choose Simulation");
                stage.setScene(new Scene(root));
                stage.show();
            } else dontExistSimulationsMessageError(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an error message if there are no simulations available for the selected scenario.
     * @param stage The current stage to display the error message.
     */
    private void dontExistSimulationsMessageError(Stage stage) {
        try {
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/simulation/TypesSimulationSelection.fxml"));
            Parent root3 = loader3.load();
            TypesSimulationSelectionGUI typesSimulationSelectionGUI = loader3.getController();
            typesSimulationSelectionGUI.setScenario(controller.getSelectedScenario());
            stage.setTitle("MABEC - Types of Simulation Selection");
            stage.setScene(new Scene(root3));
            stage.show();

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/message/ErrorMessage.fxml"));
            Parent root2 = loader2.load();
            ErrorMessageGUI errorMessageGUI = loader2.getController();
            errorMessageGUI.setErrorMessage("There aren't simulations available to select.\nPlease create a simulation first.");
            Stage stage2 = new Stage();
            stage2.setTitle("MABEC - Error Message");
            stage2.setScene(new Scene(root2));
            stage2.show();
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5));
            delay.setOnFinished(event1 -> stage2.close());
            delay.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

