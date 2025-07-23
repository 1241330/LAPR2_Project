package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Event.Event;
import pt.ipp.isep.dei.ui.console.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for managing Event entities.
 * Provides methods to add, remove, retrieve, and check events.
 */
public class EventRepository implements Serializable {

    /**
     * List that stores all events.
     */
    private List<Event> events = new ArrayList<>();

    /**
     * Constructs an EventRepository and initializes the event list.
     */
    public EventRepository() {
    }

    /**
     * Adds an event to the repository.
     *
     * @param event the event to be added
     * @return true if the event was added successfully, false if the event name already exists
     * @throws IllegalArgumentException if the event is null
     */
    public boolean addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (eventNameExists(event.getName())) {
            return false;
        }
        events.add(event);
        return true;
    }

    /**
     * Removes an event from the repository.
     *
     * @param event the event to be removed
     * @return true if the event was removed successfully, false if the event does not exist
     * @throws IllegalArgumentException if the event is null
     */
    public boolean removeEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (!eventExists(event)) {
            Utils.printMessage("< Event doesn't exist >");
            return false;
        }
        events.remove(event);
        return true;
    }

    /**
     * Gets the total number of events in the repository.
     *
     * @return the number of events
     */
    public int getEventCount() {
        return events.size();
    }

    /**
     * Gets the list of all events.
     *
     * @return the list of events
     */
    public List<Event> getAllEvents() {
        return events;
    }

    /**
     * Checks if an event exists in the repository.
     *
     * @param actualEvent the event to check
     * @return true if the event exists, false otherwise
     */
    public boolean eventExists(Event actualEvent) {
        for (Event event : events) {
            if (event == actualEvent) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an event name already exists in the repository.
     *
     * @param name the name of the event to check
     * @return true if the event name exists, false otherwise
     */
    public boolean eventNameExists(String name) {
        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(name)) {
                Utils.printMessage("<Event name already exist>");
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of events.
     *
     * @return the list of events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events.
     *
     * @param events the list of events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }
}