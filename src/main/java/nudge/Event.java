package nudge;

/**
 * Represents a task that occurs between specified start and end times.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an incomplete event with the specified description and times.
     *
     * @param description description of the event.
     * @param from date or time at which the event starts.
     * @param to date or time at which the event ends.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the event's start time.
     *
     * @return start time.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the event's end time.
     *
     * @return end time.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the event formatted with its task type, status, and times.
     *
     * @return formatted event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
