package nudge;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves Nudge tasks to the local data file.
 */
public final class Storage {
    private static final Path STORAGE_PATH = Path.of("data", "nudge.txt");

    private Storage() {
    }

    /**
     * Writes every task in the list to the storage file, one task per line.
     *
     * @param tasks tasks to save.
     * @throws IOException if the storage directory or file cannot be written.
     */
    public static void save(List<Task> tasks) throws IOException {
        Files.createDirectories(STORAGE_PATH.getParent());
        Files.write(STORAGE_PATH, tasks.stream().map(Task::toString).toList(),
                StandardCharsets.UTF_8);
    }

    /**
     * Loads tasks from the storage file when it exists.
     *
     * @return tasks represented in the storage file, or an empty list if it does not exist.
     * @throws IOException if the storage file cannot be read.
     * @throws NudgeException if a saved task has an invalid format.
     */
    public static List<Task> load() throws IOException, NudgeException {
        if (!Files.exists(STORAGE_PATH)) {
            return new ArrayList<>();
        }
        ArrayList<Task> tasks = new ArrayList<>();
        for (String savedTask : Files.readAllLines(STORAGE_PATH, StandardCharsets.UTF_8)) {
            tasks.add(deserializeTask(savedTask));
        }
        return tasks;
    }

    /**
     * Recreates a task from one line of the storage file.
     *
     * @param savedTask one saved task.
     * @return task represented by the line.
     * @throws NudgeException if the line is not a supported task format.
     */
    private static Task deserializeTask(String savedTask) throws NudgeException {
        if (savedTask.length() < 7 || savedTask.charAt(0) != '[' || savedTask.charAt(2) != ']'
                || savedTask.charAt(3) != '[' || savedTask.charAt(5) != ']'
                || savedTask.charAt(6) != ' ') {
            throw new NudgeException("The saved task list is invalid.");
        }

        char taskType = savedTask.charAt(1);
        char status = savedTask.charAt(4);
        String taskDetails = savedTask.substring(7);
        Task task;
        switch (taskType) {
        case 'T':
            task = new Todo(taskDetails);
            break;
        case 'D':
            task = deserializeDeadline(taskDetails);
            break;
        case 'E':
            task = deserializeEvent(taskDetails);
            break;
        default:
            throw new NudgeException("The saved task list is invalid.");
        }

        if (status == 'X') {
            task.markAsDone();
        } else if (status != ' ') {
            throw new NudgeException("The saved task list is invalid.");
        }
        return task;
    }

    /**
     * Recreates a deadline from its saved description and due-time details.
     *
     * @param taskDetails saved deadline details.
     * @return deadline represented by the details.
     * @throws NudgeException if the details are invalid.
     */
    private static Deadline deserializeDeadline(String taskDetails) throws NudgeException {
        int dueTimeStart = taskDetails.lastIndexOf(" (by: ");
        if (dueTimeStart < 0 || !taskDetails.endsWith(")")) {
            throw new NudgeException("The saved task list is invalid.");
        }
        String description = taskDetails.substring(0, dueTimeStart);
        String by = taskDetails.substring(dueTimeStart + " (by: ".length(),
                taskDetails.length() - 1);
        return new Deadline(description, by);
    }

    /**
     * Recreates an event from its saved description and time details.
     *
     * @param taskDetails saved event details.
     * @return event represented by the details.
     * @throws NudgeException if the details are invalid.
     */
    private static Event deserializeEvent(String taskDetails) throws NudgeException {
        int startTimeStart = taskDetails.lastIndexOf(" (from: ");
        int endTimeStart = taskDetails.lastIndexOf(" to: ");
        if (startTimeStart < 0 || endTimeStart < startTimeStart || !taskDetails.endsWith(")")) {
            throw new NudgeException("The saved task list is invalid.");
        }
        String description = taskDetails.substring(0, startTimeStart);
        String from = taskDetails.substring(startTimeStart + " (from: ".length(), endTimeStart);
        String to = taskDetails.substring(endTimeStart + " to: ".length(),
                taskDetails.length() - 1);
        return new Event(description, from, to);
    }
}
