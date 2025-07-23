package pt.ipp.isep.dei.domain.Event;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a recurring event with a name, interval, and next generation date.
 * Implements Serializable for object serialization.
 */
public class Event implements Serializable {

    /** The interval between event generations. */
    private int interval;

    /** The name of the event. */
    private String name;

    /** The next generation date of the event. */
    private int nextGenerationDate;

    /**
     * Constructs an Event with the specified name, interval, and actual date.
     * The next generation date is calculated as actualDate + interval.
     *
     * @param name        the name of the event
     * @param interval    the interval between event generations
     * @param actualDate  the current date (used to calculate the next generation date)
     */
    public Event(String name, int interval, int actualDate) {
        this.name = name;
        this.interval = interval;
        this.nextGenerationDate = actualDate + this.interval;
    }

    /**
     * Gets the interval between event generations.
     *
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Sets the interval between event generations.
     *
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * Gets the name of the event.
     *
     * @return the event name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the event.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the next generation date of the event.
     *
     * @return the next generation date
     */
    public int getNextGenerationDate() {
        return nextGenerationDate;
    }

    /**
     * Sets the next generation date of the event.
     *
     * @param nextGenerationDate the next generation date to set
     */
    public void setNextGenerationDate(int nextGenerationDate) {
        this.nextGenerationDate = nextGenerationDate;
    }

    /**
     * Triggers the event.
     * This method should contain the event trigger logic.
     *
     * @return a list with a message indicating the event was triggered
     */
    public List<String> trigger() {
        // Event trigger logic goes here
        return List.of("Event " + name + " triggered at date: " + nextGenerationDate);
    }
}