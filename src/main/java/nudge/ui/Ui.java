package nudge.ui;

import java.util.List;
import java.util.Scanner;

import nudge.task.Task;

/**
 * Handles console input and output for Nudge.
 */
public class Ui {
    private static final String OUTPUT_DETAIL_INDENTATION = "      ";
    private static final String OUTPUT_INDENTATION = "    > ";
    private static final String OUTPUT_SEPARATOR = "_".repeat(60);

    private final Scanner scanner;

    /**
     * Creates a UI that reads commands from the standard input stream.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns whether another command is available from the user.
     *
     * @return true if another command can be read.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return the next command.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Shows the application greeting.
     */
    public void showWelcome() {
        String banner = " _   _           _            \n"
                + "| \\ | |_   _  __| | __ _  ___ \n"
                + "|  \\| | | | |/ _` |/ _` |/ _ \\\n"
                + "| |\\  | |_| | (_| | (_| |  __/\n"
                + "|_| \\_|\\__,_|\\__,_|\\__, |\\___|\n"
                + "                   |___/\n";

        System.out.println(OUTPUT_SEPARATOR);
        System.out.print(banner);
        System.out.println(OUTPUT_INDENTATION + "Hey! I'm Nudge. How can I help you today?");
        System.out.println(OUTPUT_SEPARATOR);
    }

    /**
     * Prints all stored tasks in numbered order between separator lines.
     *
     * @param tasks stored tasks.
     */
    public void showTaskList(List<Task> tasks) {
        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_INDENTATION + "Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(OUTPUT_DETAIL_INDENTATION + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(OUTPUT_SEPARATOR);
    }

    /**
     * Prints matching tasks in numbered order between separator lines.
     *
     * @param tasks tasks that match a find keyword.
     */
    public void showMatchingTasks(List<Task> tasks) {
        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_INDENTATION + "Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(OUTPUT_DETAIL_INDENTATION + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(OUTPUT_SEPARATOR);
    }

    /**
     * Confirms that the specified task has been marked as done.
     *
     * @param task task marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(OUTPUT_DETAIL_INDENTATION + task);
        System.out.println(OUTPUT_SEPARATOR);
    }

    /**
     * Confirms that the specified task has been marked as not done.
     *
     * @param task task marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_INDENTATION + "OK, I've marked this task as not done yet:");
        System.out.println(OUTPUT_DETAIL_INDENTATION + task);
        System.out.println(OUTPUT_SEPARATOR);
    }

    /**
     * Confirms that a task has been deleted and reports the updated task count.
     *
     * @param task task that was deleted.
     * @param taskCount number of tasks currently stored.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        String taskLabel = taskCount == 1 ? "task" : "tasks";
        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_INDENTATION + "Noted. I've removed this task:");
        System.out.println(OUTPUT_DETAIL_INDENTATION + task);
        System.out.println(OUTPUT_INDENTATION + "You now have " + taskCount + " " + taskLabel
                + " on your radar.");
        System.out.println(OUTPUT_SEPARATOR);
    }

    /**
     * Confirms that a task has been added and reports the updated task count.
     *
     * @param task task that was added.
     * @param taskCount number of tasks currently stored.
     */
    public void showTaskAdded(Task task, int taskCount) {
        String taskLabel = taskCount == 1 ? "task" : "tasks";
        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_INDENTATION + "Nudge received! I've added:");
        System.out.println(OUTPUT_DETAIL_INDENTATION + task);
        System.out.println(OUTPUT_INDENTATION + "You now have " + taskCount + " " + taskLabel
                + " on your radar.");
        System.out.println(OUTPUT_SEPARATOR);
    }

    /**
     * Prints a message from Nudge between separator lines.
     *
     * @param message message to display.
     */
    public void showMessage(String message) {
        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_INDENTATION + message);
        System.out.println(OUTPUT_SEPARATOR);
    }
}
