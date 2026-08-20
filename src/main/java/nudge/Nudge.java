package nudge;

import java.util.Scanner;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
    private static final int MAX_TASKS = 100;
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
                    tasks[taskCount] = todo;
                    taskCount++;
                    printAddedTask(todo, taskCount);
                    continue;
                }
                if (command.startsWith("deadline ")) {
                    String deadlineDetails = command.substring("deadline ".length());
                    String[] deadlineParts = deadlineDetails.split(" /by ", 2);
                    Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                    tasks[taskCount] = deadline;
                    taskCount++;
                    printAddedTask(deadline, taskCount);
                    continue;
                }
                if (command.startsWith("event ")) {
                    String eventDetails = command.substring("event ".length());
                    String[] eventParts = eventDetails.split(" /from ", 2);
                    String[] timeParts = eventParts[1].split(" /to ", 2);
                    Task event = new Event(eventParts[0], timeParts[0], timeParts[1]);
                    tasks[taskCount] = event;
                    taskCount++;
                    printAddedTask(event, taskCount);
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
