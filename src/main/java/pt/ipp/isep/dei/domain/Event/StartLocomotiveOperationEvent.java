package pt.ipp.isep.dei.domain.Event;

import pt.ipp.isep.dei.domain.Simulation.Simulation;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;
import pt.ipp.isep.dei.domain.Train.Locomotive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Event that marks the start of a locomotive's operation in the simulation.
 * When triggered, the locomotive becomes available and a random alert message is generated.
 */
public class StartLocomotiveOperationEvent extends Event {

    private Simulation simulation;
    private Locomotive locomotive;
    private List<String> alertMessages = new ArrayList<>();

    /**
     * Constructs a StartLocomotiveOperationEvent.
     *
     * @param name        the name of the event
     * @param interval    the interval at which the event occurs
     * @param year        the year the event is scheduled
     * @param simulation  the simulation instance
     * @param locomotive  the locomotive to be made available
     */
    public StartLocomotiveOperationEvent(String name, int interval, int year, Simulation simulation, Locomotive locomotive) {
        super(name, interval, (new TimeDate(year, 1, 1).getTotalDays()));
        this.simulation = simulation;
        this.locomotive = locomotive;
        setAlertMessages();
    }

    /**
     * Initializes the list of alert messages for the event.
     */
    private void setAlertMessages() {
        String locomotiveName = locomotive.getName();
        alertMessages.add("🚂 The " + locomotiveName + " is now available - unlock it and dominate the rails!");
        alertMessages.add("🚨 New arrival: " + locomotiveName + "! Get ready to ride in power and style!");
        alertMessages.add("🚂 All aboard the " + locomotiveName + " - your next journey starts now!");
        alertMessages.add("🛤️ The " + locomotiveName + " has arrived—unlock it and lead the charge!");
        alertMessages.add("🚄 Feel the speed of the " + locomotiveName + " - now available in your garage!");
        alertMessages.add("🔓 The " + locomotiveName + " is ready to roll - claim it before it leaves the station!");
        alertMessages.add("🌟 Meet the " + locomotiveName + " - your ultimate locomotive upgrade is here!");
    }

    /**
     * Triggers the event, making the locomotive available and returning a random alert message.
     *
     * @return a list containing a single alert message
     */
    @Override
    public List<String> trigger() {
        List<String> newLogs = new ArrayList<>();
        newLogs.add(" ");
        simulation.addAvailableDateLocomotive(locomotive);
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
     * Gets the locomotive associated with this event.
     *
     * @return the locomotive
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Sets the locomotive for this event.
     *
     * @param locomotive the locomotive to set
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    /**
     * Gets the list of alert messages for this event.
     *
     * @return the list of alert messages
     */
    public List<String> getAlertMessages() {
        return alertMessages;
    }

    /**
     * Sets the list of alert messages for this event.
     *
     * @param alertMessages the list of alert messages to set
     */
    public void setAlertMessages(List<String> alertMessages) {
        this.alertMessages = alertMessages;
    }
}