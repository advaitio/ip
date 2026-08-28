package nudge;

import java.io.IOException;

import nudge.exception.NudgeException;
import nudge.parser.CommandType;
import nudge.parser.Parser;
import nudge.storage.Storage;
import nudge.task.Task;
import nudge.task.TaskList;
import nudge.ui.Ui;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
    private final Ui ui;
    private TaskList tasks;

    /**
     * Creates a Nudge chatbot with a console user interface.
     */
    public Nudge() {
        ui = new Ui();
        tasks = new TaskList();
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
        ui.showWelcome();

        try {
            tasks = new TaskList(Storage.load());
        } catch (IOException | NudgeException exception) {
            ui.showMessage("I couldn't load your saved task list.");
            tasks = new TaskList();
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
                        ui.showTaskList(tasks.getTasks());
                        break;
                    case FIND:
                        ui.showMatchingTasks(tasks.find(Parser.parseFindKeyword(command)));
                        break;
                    case MARK:
                        markTask(Parser.parseTaskIndex(command, "mark"));
                        break;
                    case UNMARK:
                        unmarkTask(Parser.parseTaskIndex(command, "unmark"));
                        break;
                    case DELETE:
                        deleteTask(Parser.parseTaskIndex(command, "delete"));
                        break;
                    case TODO:
                        addTask(Parser.parseTodo(command));
                        break;
                    case DEADLINE:
                        addTask(Parser.parseDeadline(command));
                        break;
                    case EVENT:
                        addTask(Parser.parseEvent(command));
                        break;
                    case UNKNOWN:
                        throw new NudgeException("I don't recognize that command. "
                                + "Try: todo, deadline, event, list, find, mark, unmark, delete, "
                                + "or bye.");
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
     * @param task task to add.
     * @throws NudgeException if the task list cannot be written to disk.
     */
    private void addTask(Task task) throws NudgeException {
        tasks.add(task);
        try {
            saveTasks();
        } catch (NudgeException exception) {
            tasks.delete(tasks.getSize() - 1);
            throw exception;
        }
        ui.showTaskAdded(task, tasks.getSize());
    }

    /**
     * Marks a task as done, reverting the change if it cannot be saved.
     *
     * @param taskIndex zero-based index of the task to mark.
     * @throws NudgeException if the index is invalid or the task list cannot be saved.
     */
    private void markTask(int taskIndex) throws NudgeException {
        updateTaskStatus(taskIndex, true);
        ui.showTaskMarked(tasks.get(taskIndex));
    }

    /**
     * Marks a task as not done, reverting the change if it cannot be saved.
     *
     * @param taskIndex zero-based index of the task to unmark.
     * @throws NudgeException if the index is invalid or the task list cannot be saved.
     */
    private void unmarkTask(int taskIndex) throws NudgeException {
        updateTaskStatus(taskIndex, false);
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }

    /**
     * Updates a task's status, restoring its original status if saving fails.
     *
     * @param taskIndex zero-based index of the task to update.
     * @param shouldMark true to mark the task, or false to unmark it.
     * @throws NudgeException if the index is invalid or the task list cannot be saved.
     */
    private void updateTaskStatus(int taskIndex, boolean shouldMark) throws NudgeException {
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();
        if (shouldMark) {
            tasks.mark(taskIndex);
        } else {
            tasks.unmark(taskIndex);
        }
        try {
            saveTasks();
        } catch (NudgeException exception) {
            if (wasDone) {
                tasks.mark(taskIndex);
            } else {
                tasks.unmark(taskIndex);
            }
            throw exception;
        }
    }

    /**
     * Deletes a task, restoring it if the updated list cannot be saved.
     *
     * @param taskIndex zero-based index of the task to delete.
     * @throws NudgeException if the index is invalid or the task list cannot be saved.
     */
    private void deleteTask(int taskIndex) throws NudgeException {
        Task deletedTask = tasks.delete(taskIndex);
        try {
            saveTasks();
        } catch (NudgeException exception) {
            tasks.add(taskIndex, deletedTask);
            throw exception;
        }
        ui.showTaskDeleted(deletedTask, tasks.getSize());
    }

    /**
     * Saves the current task list and reports an error when it cannot be saved.
     *
     * @throws NudgeException if the task list cannot be written to disk.
     */
    private void saveTasks() throws NudgeException {
        try {
            Storage.save(tasks.getTasks());
        } catch (IOException exception) {
            throw new NudgeException("I couldn't save your task list.");
        }
    }

}
