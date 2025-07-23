package pt.ipp.isep.dei.domain.Event;

/**
 * Represents a historical event that extends the base Event class.
 * Stores information about the event's name, interval, and actual date.
 */
public class HistoricalEvent extends Event {

    /**
     * Constructs a HistoricalEvent with the specified name, interval, and actual date.
     *
     * @param name       the name of the historical event
     * @param interval   the interval of the event
     * @param actualDate the actual date of the event
     */
    public HistoricalEvent(String name, int interval, int actualDate) {
        super(name, interval, actualDate);
    }

    /**
     * Gets the name of the historical event.
     *
     * @return the name of the event
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Sets the name of the historical event.
     *
     * @param name the new name of the event
     */
    @Override
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Gets the interval of the historical event.
     *
     * @return the interval of the event
     */
    @Override
    public int getInterval() {
        return super.getInterval();
    }

    /**
     * Sets the interval of the historical event.
     *
     * @param interval the new interval of the event
     */
    @Override
    public void setInterval(int interval) {
        super.setInterval(interval);
    }
}