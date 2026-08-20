package nudge;

import java.util.Scanner;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
    private static final String DEADLINE_FORMAT = "deadline DESCRIPTION /by DATE_OR_TIME";
    private static final int MAX_TASKS = 100;
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
        Task[] tasks = new Task[MAX_TASKS];

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

        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            try {
                if ("bye".equalsIgnoreCase(command)) {
                    break;
                }
                if ("list".equalsIgnoreCase(command)) {
                    printTaskList(tasks, taskCount);
                    continue;
                }
                if ("mark".equals(command) || command.startsWith("mark ")) {
                    int taskIndex = parseTaskIndex(command, "mark", taskCount);
                    tasks[taskIndex].markAsDone();
                    printMarkedTask(tasks[taskIndex]);
                    continue;
                }
                if ("unmark".equals(command) || command.startsWith("unmark ")) {
                    int taskIndex = parseTaskIndex(command, "unmark", taskCount);
                    tasks[taskIndex].markAsNotDone();
                    printUnmarkedTask(tasks[taskIndex]);
                    continue;
                }
                if ("todo".equals(command) || command.startsWith("todo ")) {
                    String description = command.substring("todo".length()).trim();
                    if (description.isEmpty()) {
                        throw new NudgeException("A todo needs a description. Try: todo DESCRIPTION");
                    }
                    Task todo = new Todo(description);
                    taskCount = addTask(tasks, taskCount, todo);
                    continue;
                }
                if ("deadline".equals(command) || command.startsWith("deadline ")) {
                    Task deadline = parseDeadline(command);
                    taskCount = addTask(tasks, taskCount, deadline);
                    continue;
                }
                if ("event".equals(command) || command.startsWith("event ")) {
                    Task event = parseEvent(command);
                    taskCount = addTask(tasks, taskCount, event);
                    continue;
                }
                throw new NudgeException("I don't recognize that command. "
                        + "Try: todo, deadline, event, list, mark, unmark, or bye.");
            } catch (NudgeException exception) {
                printNudgeMessage(exception.getMessage());
            }
        }

        printNudgeMessage("Okay, I'll leave you to it. I'll be here if you need another nudge!");
    }

    /**
     * Adds a task when space remains and returns the updated task count.
     *
     * @param tasks stored tasks.
     * @param taskCount number of tasks currently stored.
     * @param task task to add.
     * @return updated number of stored tasks.
     * @throws NudgeException if the task list has reached its capacity.
     */
    private static int addTask(Task[] tasks, int taskCount, Task task) throws NudgeException {
        if (taskCount >= MAX_TASKS) {
            throw new NudgeException("Your task list is full. I can keep track of at most "
                    + MAX_TASKS + " tasks.");
        }
        tasks[taskCount] = task;
        int updatedTaskCount = taskCount + 1;
        printAddedTask(task, updatedTaskCount);
        return updatedTaskCount;
    }

    /**
     * Parses and validates the task number supplied to a task status command.
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
     * @param taskCount number of tasks currently stored.
     */
    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(DETAIL_INDENTATION + (i + 1) + "." + tasks[i]);
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
