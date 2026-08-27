package nudge;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Loads and saves Nudge tasks in a local data file.
 */
public final class Storage {
    private static final Path STORAGE_PATH = Path.of("data", "nudge.txt");

    private Storage() {
    }

    /**
     * Writes every task to the storage file with an atomic replacement when supported.
     *
     * @param tasks tasks to save.
     * @throws IOException if the storage directory or file cannot be written.
     */
    public static void save(List<Task> tasks) throws IOException {
        Files.createDirectories(STORAGE_PATH.getParent());
        Path temporaryFile = Files.createTempFile(STORAGE_PATH.getParent(), "nudge-", ".tmp");
        try {
            Files.write(temporaryFile, tasks.stream().map(Storage::serializeTask).toList(),
                    StandardCharsets.UTF_8);
            moveIntoPlace(temporaryFile);
        } finally {
            Files.deleteIfExists(temporaryFile);
        }
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
            if (!savedTask.isBlank()) {
                tasks.add(deserializeTask(savedTask));
            }
        }
        return tasks;
    }

    /**
     * Replaces the storage file using an atomic move when the file system supports it.
     *
     * @param temporaryFile completed temporary storage file.
     * @throws IOException if the storage file cannot be replaced.
     */
    private static void moveIntoPlace(Path temporaryFile) throws IOException {
        try {
            Files.move(temporaryFile, STORAGE_PATH, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporaryFile, STORAGE_PATH, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Converts a task into its stable, delimiter-safe storage representation.
     *
     * @param task task to serialize.
     * @return encoded storage line for the task.
     */
    private static String serializeTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Todo) {
            return "T|" + status + "|" + encode(task.getDescription());
        }
        if (task instanceof Deadline deadline) {
            return "D|" + status + "|" + encode(deadline.getDescription())
                    + "|" + encode(deadline.getBy());
        }
        if (task instanceof Event event) {
            return "E|" + status + "|" + encode(event.getDescription())
                    + "|" + encode(event.getFrom()) + "|" + encode(event.getTo());
        }
        throw new IllegalArgumentException("Unsupported task type");
    }

    /**
     * Recreates a task from one line of the storage file.
     *
     * @param savedTask one saved task.
     * @return task represented by the line.
     * @throws NudgeException if the line is not a supported task format.
     */
    private static Task deserializeTask(String savedTask) throws NudgeException {
        return savedTask.contains("|") ? deserializeCurrentTask(savedTask)
                : deserializeLegacyTask(savedTask);
    }

    /**
     * Recreates a task from the current delimiter-safe storage format.
     *
     * @param savedTask one current-format storage line.
     * @return task represented by the line.
     * @throws NudgeException if the line is invalid.
     */
    private static Task deserializeCurrentTask(String savedTask) throws NudgeException {
        String[] parts = savedTask.split("\\|", -1);
        if (parts.length < 3 || parts[0].length() != 1
                || !(parts[1].equals("0") || parts[1].equals("1"))) {
            throw invalidFile();
        }
        Task task;
        switch (parts[0]) {
        case "T":
            requirePartCount(parts, 3);
            task = new Todo(decode(parts[2]));
            break;
        case "D":
            requirePartCount(parts, 4);
            task = new Deadline(decode(parts[2]), decode(parts[3]));
            break;
        case "E":
            requirePartCount(parts, 5);
            task = new Event(decode(parts[2]), decode(parts[3]), decode(parts[4]));
            break;
        default:
            throw invalidFile();
        }
        if (parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Recreates a task from the original human-readable storage format.
     *
     * @param savedTask one legacy-format storage line.
     * @return task represented by the line.
     * @throws NudgeException if the line is invalid.
     */
    private static Task deserializeLegacyTask(String savedTask) throws NudgeException {
        if (savedTask.length() < 7 || savedTask.charAt(0) != '[' || savedTask.charAt(2) != ']'
                || savedTask.charAt(3) != '[' || savedTask.charAt(5) != ']'
                || savedTask.charAt(6) != ' ') {
            throw invalidFile();
        }
        Task task = switch (savedTask.charAt(1)) {
        case 'T' -> new Todo(savedTask.substring(7));
        case 'D' -> deserializeLegacyDeadline(savedTask.substring(7));
        case 'E' -> deserializeLegacyEvent(savedTask.substring(7));
        default -> throw invalidFile();
        };
        if (savedTask.charAt(4) == 'X') {
            task.markAsDone();
        } else if (savedTask.charAt(4) != ' ') {
            throw invalidFile();
        }
        return task;
    }

    /**
     * Validates the number of fields in a storage line.
     *
     * @param parts fields in the storage line.
     * @param expectedCount expected number of fields.
     * @throws NudgeException if the field count is incorrect.
     */
    private static void requirePartCount(String[] parts, int expectedCount) throws NudgeException {
        if (parts.length != expectedCount) {
            throw invalidFile();
        }
    }

    /**
     * Decodes one required Base64 task field.
     *
     * @param encoded encoded field.
     * @return decoded field.
     * @throws NudgeException if the field is empty or invalid.
     */
    private static String decode(String encoded) throws NudgeException {
        try {
            String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
            if (decoded.isEmpty()) {
                throw invalidFile();
            }
            return decoded;
        } catch (IllegalArgumentException exception) {
            throw invalidFile();
        }
    }

    /**
     * Encodes one task field so delimiters and line breaks are preserved safely.
     *
     * @param value task field to encode.
     * @return Base64-encoded field.
     */
    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static NudgeException invalidFile() {
        return new NudgeException("The saved task list is invalid.");
    }

    private static Deadline deserializeLegacyDeadline(String taskDetails) throws NudgeException {
        int dueTimeStart = taskDetails.lastIndexOf(" (by: ");
        if (dueTimeStart < 0 || !taskDetails.endsWith(")")) {
            throw invalidFile();
        }
        return new Deadline(taskDetails.substring(0, dueTimeStart),
                taskDetails.substring(dueTimeStart + " (by: ".length(), taskDetails.length() - 1));
    }

    private static Event deserializeLegacyEvent(String taskDetails) throws NudgeException {
        int startTimeStart = taskDetails.lastIndexOf(" (from: ");
        int endTimeStart = taskDetails.lastIndexOf(" to: ");
        if (startTimeStart < 0 || endTimeStart < startTimeStart || !taskDetails.endsWith(")")) {
            throw invalidFile();
        }
        return new Event(taskDetails.substring(0, startTimeStart),
                taskDetails.substring(startTimeStart + " (from: ".length(), endTimeStart),
                taskDetails.substring(endTimeStart + " to: ".length(), taskDetails.length() - 1));
    }
}
