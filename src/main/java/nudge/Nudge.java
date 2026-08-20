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
        String[] tasks = new String[MAX_TASKS];
        boolean[] isTaskDone = new boolean[MAX_TASKS];

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
                printTaskList(tasks, isTaskDone, taskCount);
                continue;
            }
            if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring("mark ".length())) - 1;
                isTaskDone[taskIndex] = true;
                printMarkedTask(tasks[taskIndex]);
                continue;
            }
            printNudgeMessage("added: " + command);
            tasks[taskCount] = command;
            taskCount++;
        }

        printNudgeMessage("Okay, I'll leave you to it. I'll be here if you need another nudge!");
    }

    /**
     * Prints all stored tasks in numbered order between separator lines.
     *
     * @param tasks stored task descriptions.
     * @param isTaskDone completion state of each stored task.
     * @param taskCount number of tasks currently stored.
     */
    private static void printTaskList(String[] tasks, boolean[] isTaskDone, int taskCount) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            String statusIcon = isTaskDone[i] ? "X" : " ";
            System.out.println(DETAIL_INDENTATION + (i + 1) + ".[" + statusIcon + "] " + tasks[i]);
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Confirms that the specified task has been marked as done.
     *
     * @param task description of the task marked as done.
     */
    private static void printMarkedTask(String task) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(DETAIL_INDENTATION + "[X] " + task);
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
