package nudge;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses and validates commands entered by the user.
 */
public final class Parser {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEADLINE_FORMAT = "deadline DESCRIPTION /by " + DATE_FORMAT;
    private static final String EVENT_FORMAT = "event DESCRIPTION /from " + DATE_FORMAT
            + " /to " + DATE_FORMAT;

    private Parser() {
    }

    /**
     * Identifies the type of the specified command.
     *
     * @param command full user command.
     * @return matching command type, or {@link CommandType#UNKNOWN} if unrecognized.
     */
    public static CommandType parseCommandType(String command) {
        if ("bye".equalsIgnoreCase(command)) {
            return CommandType.BYE;
        }
        if ("list".equalsIgnoreCase(command)) {
            return CommandType.LIST;
        }
        if (matchesCommand(command, "mark")) {
            return CommandType.MARK;
        }
        if (matchesCommand(command, "unmark")) {
            return CommandType.UNMARK;
        }
        if (matchesCommand(command, "delete")) {
            return CommandType.DELETE;
        }
        if (matchesCommand(command, "todo")) {
            return CommandType.TODO;
        }
        if (matchesCommand(command, "deadline")) {
            return CommandType.DEADLINE;
        }
        if (matchesCommand(command, "event")) {
            return CommandType.EVENT;
        }
        return CommandType.UNKNOWN;
    }

    /**
     * Parses and validates the task number supplied to a task command.
     *
     * @param command full user command.
     * @param commandWord command word that precedes the task number.
     * @return zero-based index of the requested task.
     * @throws NudgeException if the task number is missing or invalid.
     */
    public static int parseTaskIndex(String command, String commandWord) throws NudgeException {
        String taskNumber = command.substring(commandWord.length()).trim();
        if (taskNumber.isEmpty()) {
            throw new NudgeException("`" + commandWord + "` needs a task number. "
                    + "Try: " + commandWord + " NUMBER");
        }

        int taskIndex;
        try {
            taskIndex = Integer.parseInt(taskNumber) - 1;
        } catch (NumberFormatException exception) {
            throw new NudgeException("The task number must be a whole number. "
                    + "Try: " + commandWord + " NUMBER");
        }

        return taskIndex;
    }

    /**
     * Parses and validates a todo command.
     *
     * @param command full user command.
     * @return todo described by the command.
     * @throws NudgeException if the description is missing.
     */
    public static Todo parseTodo(String command) throws NudgeException {
        String description = command.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new NudgeException("A todo needs a description. Try: todo DESCRIPTION");
        }
        return new Todo(description);
    }

    /**
     * Parses and validates a deadline command.
     *
     * @param command full user command.
     * @return deadline described by the command.
     * @throws NudgeException if the description, delimiter, or due date is invalid.
     */
    public static Deadline parseDeadline(String command) throws NudgeException {
        String deadlineDetails = command.substring("deadline".length()).trim();
        if (deadlineDetails.isEmpty()) {
            throw new NudgeException("A deadline needs a description. Try: " + DEADLINE_FORMAT);
        }

        String[] deadlineParts = deadlineDetails.split("/by", -1);
        if (deadlineParts.length != 2) {
            throw new NudgeException("A deadline needs `/by` before its due date. Try: "
                    + DEADLINE_FORMAT);
        }

        String description = deadlineParts[0].trim();
        String byText = deadlineParts[1].trim();
        if (description.isEmpty()) {
            throw new NudgeException("A deadline needs a description. Try: " + DEADLINE_FORMAT);
        }
        if (byText.isEmpty()) {
            throw new NudgeException("A deadline needs a date after `/by`. Try: "
                    + DEADLINE_FORMAT);
        }
        LocalDate by = parseDate(byText, "deadline date");
        return new Deadline(description, by);
    }

    /**
     * Parses and validates an event command.
     *
     * @param command full user command.
     * @return event described by the command.
     * @throws NudgeException if the description, delimiters, or dates are invalid.
     */
    public static Event parseEvent(String command) throws NudgeException {
        String eventDetails = command.substring("event".length()).trim();
        if (eventDetails.isEmpty()) {
            throw new NudgeException("An event needs a description. Try: " + EVENT_FORMAT);
        }

        String[] eventParts = eventDetails.split("/from", -1);
        if (eventParts.length != 2) {
            throw new NudgeException("An event needs `/from` before its start time. Try: "
                    + EVENT_FORMAT);
        }

        String description = eventParts[0].trim();
        if (description.isEmpty()) {
            throw new NudgeException("An event needs a description. Try: " + EVENT_FORMAT);
        }

        String[] timeParts = eventParts[1].split("/to", -1);
        if (timeParts.length != 2) {
            throw new NudgeException("An event needs `/to` before its end time. Try: "
                    + EVENT_FORMAT);
        }

        String fromText = timeParts[0].trim();
        String toText = timeParts[1].trim();
        if (fromText.isEmpty()) {
            throw new NudgeException("An event needs a start date after `/from`. Try: "
                    + EVENT_FORMAT);
        }
        if (toText.isEmpty()) {
            throw new NudgeException("An event needs an end date after `/to`. Try: "
                    + EVENT_FORMAT);
        }
        LocalDate from = parseDate(fromText, "event start date");
        LocalDate to = parseDate(toText, "event end date");
        return new Event(description, from, to);
    }

    private static boolean matchesCommand(String command, String commandWord) {
        return commandWord.equals(command) || command.startsWith(commandWord + " ");
    }

    private static LocalDate parseDate(String dateText, String dateName) throws NudgeException {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw new NudgeException("The " + dateName + " must be a valid date in "
                    + DATE_FORMAT + " format.");
        }
    }
}
