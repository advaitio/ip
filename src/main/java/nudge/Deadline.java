package nudge;

/**
 * Represents a task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates an incomplete deadline with the specified description and due time.
     *
     * @param description description of the deadline.
     * @param by date or time by which the deadline must be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline's due date or time.
     *
     * @return due date or time.
     */
    public String getBy() {
        return by;
    }

    /**
     * Returns the deadline formatted with its task type, status, and due time.
     *
     * @return formatted deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
