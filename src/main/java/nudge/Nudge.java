package nudge;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEADLINE_FORMAT = "deadline DESCRIPTION /by " + DATE_FORMAT;
    private static final String EVENT_FORMAT = "event DESCRIPTION /from " + DATE_FORMAT
            + " /to " + DATE_FORMAT;
    private final Ui ui;

    /**
     * Creates a Nudge chatbot with a console user interface.
     */
    public Nudge() {
        ui = new Ui();
    }

    /**
     * Runs the Nudge chatbot.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        new Nudge().run();
    }

    /**
     * Runs the command loop until the user exits or the input stream ends.
     */
    public void run() {
        ArrayList<Task> tasks = new ArrayList<>();
        ui.showWelcome();

        try {
            tasks.addAll(Storage.load());
        } catch (IOException | NudgeException exception) {
            ui.showMessage("I couldn't load your saved task list.");
        }

        boolean shouldExit = false;
        while (!shouldExit && ui.hasNextCommand()) {
            String command = ui.readCommand();
            try {
                CommandType commandType = CommandType.from(command);
                switch (commandType) {
                case BYE:
                    shouldExit = true;
                    break;
                case LIST:
                    ui.showTaskList(tasks);
                    break;
                case MARK:
                    int taskIndex = parseTaskIndex(command, "mark", tasks.size());
                    markTask(tasks, taskIndex);
                    break;
                case UNMARK:
                    int unmarkedTaskIndex = parseTaskIndex(command, "unmark", tasks.size());
                    unmarkTask(tasks, unmarkedTaskIndex);
                    break;
                case DELETE:
                    int deletedTaskIndex = parseTaskIndex(command, "delete", tasks.size());
                    deleteTask(tasks, deletedTaskIndex);
                    break;
                case TODO:
                    String description = command.substring("todo".length()).trim();
                    if (description.isEmpty()) {
                        throw new NudgeException("A todo needs a description. Try: todo DESCRIPTION");
                    }
                    Task todo = new Todo(description);
                    addTask(tasks, todo);
                    break;
                case DEADLINE:
                    Task deadline = parseDeadline(command);
                    addTask(tasks, deadline);
                    break;
                case EVENT:
                    Task event = parseEvent(command);
                    addTask(tasks, event);
                    break;
                case UNKNOWN:
                    throw new NudgeException("I don't recognize that command. "
                            + "Try: todo, deadline, event, list, mark, unmark, delete, or bye.");
                default:
                    assert false : "Unhandled command type: " + commandType;
                }
            } catch (NudgeException exception) {
                ui.showMessage(exception.getMessage());
            }
        }

        ui.showMessage("Okay, I'll leave you to it. I'll be here if you need another nudge!");
    }

    /**
     * Adds a task and prints confirmation with the updated task count.
     *
     * @param tasks stored tasks.
     * @param task task to add.
     */
    private void addTask(ArrayList<Task> tasks, Task task) throws NudgeException {
        tasks.add(task);
        try {
            saveTasks(tasks);
        } catch (NudgeException exception) {
            tasks.remove(tasks.size() - 1);
            throw exception;
        }
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Marks a task as done, reverting the change if it cannot be saved.
     *
     * @param tasks stored tasks.
     * @param taskIndex zero-based index of the task to mark.
     * @throws NudgeException if the task list cannot be written to disk.
     */
    private void markTask(ArrayList<Task> tasks, int taskIndex) throws NudgeException {
        updateTaskStatus(tasks, taskIndex, true);
        ui.showTaskMarked(tasks.get(taskIndex));
    }

    /**
     * Marks a task as not done, reverting the change if it cannot be saved.
     *
     * @param tasks stored tasks.
     * @param taskIndex zero-based index of the task to unmark.
     * @throws NudgeException if the task list cannot be written to disk.
     */
    private void unmarkTask(ArrayList<Task> tasks, int taskIndex) throws NudgeException {
        updateTaskStatus(tasks, taskIndex, false);
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }

    /**
     * Updates a task's status, restoring its original status if saving fails.
     *
     * @param tasks stored tasks.
     * @param taskIndex zero-based index of the task to update.
     * @param shouldMark true to mark the task, or false to unmark it.
     * @throws NudgeException if the task list cannot be written to disk.
     */
    private static void updateTaskStatus(ArrayList<Task> tasks, int taskIndex,
            boolean shouldMark) throws NudgeException {
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();
        if (shouldMark) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        try {
            saveTasks(tasks);
        } catch (NudgeException exception) {
            if (wasDone) {
                task.markAsDone();
            } else {
                task.markAsNotDone();
            }
            throw exception;
        }
    }

    /**
     * Deletes a task, restoring it if the updated list cannot be saved.
     *
     * @param tasks stored tasks.
     * @param taskIndex zero-based index of the task to delete.
     * @throws NudgeException if the task list cannot be written to disk.
     */
    private void deleteTask(ArrayList<Task> tasks, int taskIndex) throws NudgeException {
        Task deletedTask = tasks.remove(taskIndex);
        try {
            saveTasks(tasks);
        } catch (NudgeException exception) {
            tasks.add(taskIndex, deletedTask);
            throw exception;
        }
        ui.showTaskDeleted(deletedTask, tasks.size());
    }

    /**
     * Saves the current task list and reports an error when it cannot be saved.
     *
     * @param tasks stored tasks.
     * @throws NudgeException if the task list cannot be written to disk.
     */
    private static void saveTasks(ArrayList<Task> tasks) throws NudgeException {
        try {
            Storage.save(tasks);
        } catch (IOException exception) {
            throw new NudgeException("I couldn't save your task list.");
        }
    }

    /**
     * Parses and validates the task number supplied to a task command.
     *
     * @param command full user command.
     * @param commandWord command word that precedes the task number.
     * @param taskCount number of tasks currently stored.
     * @return zero-based index of the requested task.
     * @throws NudgeException if the task number is missing, invalid, or outside the task list.
     */
    private static int parseTaskIndex(String command, String commandWord,
            int taskCount) throws NudgeException {
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

        if (taskCount == 0) {
            throw new NudgeException("There are no tasks in your list yet.");
        }
        if (taskIndex < 0 || taskIndex >= taskCount) {
            String validRange = taskCount == 1
                    ? "Choose task number 1."
                    : "Choose a task number from 1 to " + taskCount + ".";
            throw new NudgeException(validRange);
        }
        return taskIndex;
    }

    /**
     * Parses and validates a deadline command.
     *
     * @param command full user command.
     * @return deadline described by the command.
     * @throws NudgeException if the description, delimiter, or due value is invalid.
     */
    private static Deadline parseDeadline(String command) throws NudgeException {
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
     * @throws NudgeException if the description, delimiters, or time values are invalid.
     */
    private static Event parseEvent(String command) throws NudgeException {
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

    /**
     * Parses a date written in Nudge's required input format.
     *
     * @param dateText date supplied by the user.
     * @param dateName user-facing name of the date being parsed.
     * @return parsed date.
     * @throws NudgeException if the date is not a valid {@code yyyy-MM-dd} value.
     */
    private static LocalDate parseDate(String dateText, String dateName) throws NudgeException {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw new NudgeException("The " + dateName + " must be a valid date in "
                    + DATE_FORMAT + " format.");
        }
    }

}
