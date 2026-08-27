package nudge;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
}
