package nudge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
    private static final String DEADLINE_FORMAT = "deadline DESCRIPTION /by DATE_OR_TIME";
    private static final String EVENT_FORMAT = "event DESCRIPTION /from START /to END";
    private static final String DETAIL_INDENTATION = "      ";
    private static final String INDENTATION = "    > ";
    private static final String SEPARATOR = "_".repeat(60);

    /**
     * Runs the Nudge chatbot.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        String banner = " _   _           _            \n"
                + "| \\ | |_   _  __| | __ _  ___ \n"
                + "|  \\| | | | |/ _` |/ _` |/ _ \\\n"
                + "| |\\  | |_| | (_| | (_| |  __/\n"
                + "|_| \\_|\\__,_|\\__,_|\\__, |\\___|\n"
                + "                   |___/\n";

        System.out.println(SEPARATOR);
        System.out.print(banner);
        System.out.println(INDENTATION + "Hey! I'm Nudge. How can I help you today?");
        System.out.println(SEPARATOR);

        boolean shouldExit = false;
        while (!shouldExit && scanner.hasNextLine()) {
            String command = scanner.nextLine();
            try {
                CommandType commandType = CommandType.from(command);
                switch (commandType) {
                case BYE:
                    shouldExit = true;
                    break;
                case LIST:
                    printTaskList(tasks);
                    break;
                case MARK:
                    int taskIndex = parseTaskIndex(command, "mark", tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    saveTasks(tasks);
                    printMarkedTask(tasks.get(taskIndex));
                    break;
                case UNMARK:
                    int unmarkedTaskIndex = parseTaskIndex(command, "unmark", tasks.size());
                    tasks.get(unmarkedTaskIndex).markAsNotDone();
                    saveTasks(tasks);
                    printUnmarkedTask(tasks.get(unmarkedTaskIndex));
                    break;
                case DELETE:
                    int deletedTaskIndex = parseTaskIndex(command, "delete", tasks.size());
                    Task deletedTask = tasks.remove(deletedTaskIndex);
                    saveTasks(tasks);
                    printDeletedTask(deletedTask, tasks.size());
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
                printNudgeMessage(exception.getMessage());
            }
        }

        printNudgeMessage("Okay, I'll leave you to it. I'll be here if you need another nudge!");
    }

    /**
     * Adds a task and prints confirmation with the updated task count.
     *
     * @param tasks stored tasks.
     * @param task task to add.
     */
    private static void addTask(ArrayList<Task> tasks, Task task) throws NudgeException {
        tasks.add(task);
        saveTasks(tasks);
        printAddedTask(task, tasks.size());
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
        String by = deadlineParts[1].trim();
        if (description.isEmpty()) {
            throw new NudgeException("A deadline needs a description. Try: " + DEADLINE_FORMAT);
        }
        if (by.isEmpty()) {
            throw new NudgeException("A deadline needs a date or time after `/by`. Try: "
                    + DEADLINE_FORMAT);
        }
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

        String from = timeParts[0].trim();
        String to = timeParts[1].trim();
        if (from.isEmpty()) {
            throw new NudgeException("An event needs a start time after `/from`. Try: "
                    + EVENT_FORMAT);
        }
        if (to.isEmpty()) {
            throw new NudgeException("An event needs an end time after `/to`. Try: "
                    + EVENT_FORMAT);
        }
        return new Event(description, from, to);
    }

    /**
     * Prints all stored tasks in numbered order between separator lines.
     *
     * @param tasks stored tasks.
     */
    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(DETAIL_INDENTATION + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Confirms that the specified task has been marked as done.
     *
     * @param task task marked as done.
     */
    private static void printMarkedTask(Task task) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(DETAIL_INDENTATION + task);
        System.out.println(SEPARATOR);
    }

    /**
     * Confirms that the specified task has been marked as not done.
     *
     * @param task task marked as not done.
     */
    private static void printUnmarkedTask(Task task) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "OK, I've marked this task as not done yet:");
        System.out.println(DETAIL_INDENTATION + task);
        System.out.println(SEPARATOR);
    }

    /**
     * Confirms that a task has been deleted and reports the updated task count.
     *
     * @param task task that was deleted.
     * @param taskCount number of tasks currently stored.
     */
    private static void printDeletedTask(Task task, int taskCount) {
        String taskLabel = taskCount == 1 ? "task" : "tasks";
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "Noted. I've removed this task:");
        System.out.println(DETAIL_INDENTATION + task);
        System.out.println(INDENTATION + "You now have " + taskCount + " " + taskLabel + " on your radar.");
        System.out.println(SEPARATOR);
    }

    /**
     * Confirms that a task has been added and reports the updated task count.
     *
     * @param task task that was added.
     * @param taskCount number of tasks currently stored.
     */
    private static void printAddedTask(Task task, int taskCount) {
        String taskLabel = taskCount == 1 ? "task" : "tasks";
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "Nudge received! I've added:");
        System.out.println(DETAIL_INDENTATION + task);
        System.out.println(INDENTATION + "You now have " + taskCount + " " + taskLabel + " on your radar.");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a message from Nudge between separator lines.
     *
     * @param message message to display.
     */
    private static void printNudgeMessage(String message) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + message);
        System.out.println(SEPARATOR);
    }
}
