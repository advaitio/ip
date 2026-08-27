package nudge;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
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
                CommandType commandType = Parser.parseCommandType(command);
                switch (commandType) {
                case BYE:
                    shouldExit = true;
                    break;
                case LIST:
                    ui.showTaskList(tasks);
                    break;
                case MARK:
                    int taskIndex = Parser.parseTaskIndex(command, "mark", tasks.size());
                    markTask(tasks, taskIndex);
                    break;
                case UNMARK:
                    int unmarkedTaskIndex = Parser.parseTaskIndex(
                            command, "unmark", tasks.size());
                    unmarkTask(tasks, unmarkedTaskIndex);
                    break;
                case DELETE:
                    int deletedTaskIndex = Parser.parseTaskIndex(
                            command, "delete", tasks.size());
                    deleteTask(tasks, deletedTaskIndex);
                    break;
                case TODO:
                    addTask(tasks, Parser.parseTodo(command));
                    break;
                case DEADLINE:
                    addTask(tasks, Parser.parseDeadline(command));
                    break;
                case EVENT:
                    addTask(tasks, Parser.parseEvent(command));
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

}
