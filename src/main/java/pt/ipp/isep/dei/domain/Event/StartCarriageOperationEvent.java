package pt.ipp.isep.dei.domain.Event;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Train.Carriage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Event that represents the start of a carriage operation in the simulation.
 * When triggered, it makes a carriage available and generates a random alert message.
 */
public class StartCarriageOperationEvent extends Event {

    private Simulation simulation;
    private Carriage carriage;
    private List<String> alertMessages = new ArrayList<>();

    /**
     * Constructs a StartCarriageOperationEvent.
     *
     * @param name       the name of the event
     * @param interval   the interval at which the event occurs
     * @param year       the year the event starts
     * @param simulation the simulation instance
     * @param carriage   the carriage to be made available
     */
    public StartCarriageOperationEvent(String name, int interval, int year, Simulation simulation, Carriage carriage) {
        super(name, interval, (new TimeDate(year, 1, 1).getTotalDays()));
        this.simulation = simulation;
        this.carriage = carriage;
        setAlertMessages();
    }

    /**
     * Initializes the list of alert messages for the event.
     */
    private void setAlertMessages() {
        String carriageName = carriage.getName();
        alertMessages.add("🚃 The " + carriageName + " is now available – ready to join your train!");
        alertMessages.add("📦 New carriage unlocked: " + carriageName + "! Boost your transport capacity!");
        alertMessages.add("🚃 All aboard the " + carriageName + " – perfect for your next big haul!");
        alertMessages.add("🛤️ " + carriageName + " has rolled in – time to expand your fleet!");
        alertMessages.add("🚛 Meet the " + carriageName + " – strength and space combined!");
        alertMessages.add("🔓 The " + carriageName + " is ready to load – don’t keep it waiting!");
        alertMessages.add("🌟 Upgrade alert: " + carriageName + " is now part of your arsenal!");
    }

    /**
     * Triggers the event, making the carriage available and returning a random alert message.
     *
     * @return a list containing a single random alert message
     */
    @Override
    public List<String> trigger() {
        List<String> newLogs = new ArrayList<>();
        newLogs.add(" ");
        simulation.addAvailableDateCarriage(carriage);
        Random random = new Random();
        newLogs.add(alertMessages.get(random.nextInt(alertMessages.size())));
        return newLogs;
    }

    /**
     * Gets the simulation associated with this event.
     *
     * @return the simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Sets the simulation for this event.
     *
     * @param simulation the simulation to set
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Gets the carriage associated with this event.
     *
     * @return the carriage
     */
    public Carriage getCarriage() {
        return carriage;
    }

    /**
     * Sets the carriage for this event.
     *
     * @param carriage the carriage to set
     */
    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    /**
     * Gets the list of alert messages.
     *
     * @return the list of alert messages
     */
    public List<String> getAlertMessages() {
        return alertMessages;
    }

    /**
     * Sets the list of alert messages.
     *
     * @param alertMessages the list of alert messages to set
     */
    public void setAlertMessages(List<String> alertMessages) {
        this.alertMessages = alertMessages;
    }
}