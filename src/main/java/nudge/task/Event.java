package nudge.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that occurs between specified start and end dates.
 */
public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Creates an incomplete event with the specified description and dates.
     *
     * @param description description of the event.
     * @param from date on which the event starts.
     * @param to date on which the event ends.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the event's start date.
     *
     * @return start date.
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the event's end date.
     *
     * @return end date.
     */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Returns the event formatted with its task type, status, and dates.
     *
     * @return formatted event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY_FORMATTER)
                + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
}
