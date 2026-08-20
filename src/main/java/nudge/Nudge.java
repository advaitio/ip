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
            if ("bye".equalsIgnoreCase(command)) {
                break;
            }
            if ("list".equalsIgnoreCase(command)) {
                printTaskList(tasks, taskCount);
                continue;
            }
            if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring("mark ".length())) - 1;
                tasks[taskIndex].markAsDone();
                printMarkedTask(tasks[taskIndex]);
                continue;
            }
            if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring("unmark ".length())) - 1;
                tasks[taskIndex].markAsNotDone();
                printUnmarkedTask(tasks[taskIndex]);
                continue;
            }
            if (command.startsWith("todo ")) {
                String description = command.substring("todo ".length());
                Task todo = new Todo(description);
                printNudgeMessage("added: " + todo);
                tasks[taskCount] = todo;
                taskCount++;
                continue;
            }
            if (command.startsWith("deadline ")) {
                String deadlineDetails = command.substring("deadline ".length());
                String[] deadlineParts = deadlineDetails.split(" /by ", 2);
                Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                printNudgeMessage("added: " + deadline);
                tasks[taskCount] = deadline;
                taskCount++;
                continue;
            }
            if (command.startsWith("event ")) {
                String eventDetails = command.substring("event ".length());
                String[] eventParts = eventDetails.split(" /from ", 2);
                String[] timeParts = eventParts[1].split(" /to ", 2);
                Task event = new Event(eventParts[0], timeParts[0], timeParts[1]);
                printNudgeMessage("added: " + event);
                tasks[taskCount] = event;
                taskCount++;
                continue;
            }
            printNudgeMessage("added: " + command);
            tasks[taskCount] = new Task(command);
            taskCount++;
        }

        printNudgeMessage("Okay, I'll leave you to it. I'll be here if you need another nudge!");
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
