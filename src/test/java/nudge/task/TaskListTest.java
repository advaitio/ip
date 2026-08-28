package nudge.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import nudge.exception.NudgeException;

/**
 * Tests task-list operations performed by {@link TaskList}.
 */
class TaskListTest {
    @Test
    void delete_validIndex_removesAndReturnsSelectedTask() throws NudgeException {
        Task firstTask = new Todo("read book");
        Task secondTask = new Todo("return book");
        TaskList tasks = new TaskList(List.of(firstTask, secondTask));

        Task deletedTask = tasks.delete(1);

        assertSame(secondTask, deletedTask);
        assertEquals(1, tasks.getSize());
        assertSame(firstTask, tasks.get(0));
    }

    @Test
    void delete_emptyList_exceptionThrown() {
        TaskList tasks = new TaskList();

        NudgeException exception = assertThrows(NudgeException.class, () -> tasks.delete(0));

        assertEquals("There are no tasks in your list yet.", exception.getMessage());
    }

    @Test
    void delete_negativeIndex_exceptionThrown() {
        TaskList tasks = new TaskList(List.of(new Todo("read book"), new Todo("return book")));

        NudgeException exception = assertThrows(NudgeException.class, () -> tasks.delete(-1));

        assertEquals("Choose a task number from 1 to 2.", exception.getMessage());
    }

    @Test
    void delete_indexEqualToSize_exceptionThrown() {
        TaskList tasks = new TaskList(List.of(new Todo("read book"), new Todo("return book")));

        NudgeException exception = assertThrows(NudgeException.class, () -> tasks.delete(2));

        assertEquals("Choose a task number from 1 to 2.", exception.getMessage());
    }

    @Test
    void delete_invalidIndexForSingleTask_exceptionThrown() {
        TaskList tasks = new TaskList(List.of(new Todo("read book")));

        NudgeException exception = assertThrows(NudgeException.class, () -> tasks.delete(1));

        assertEquals("Choose task number 1.", exception.getMessage());
    }

    @Test
    void find_matchingKeyword_returnsMatchingTasksInOriginalOrder() {
        Task firstMatchingTask = new Todo("read book");
        Task otherTask = new Todo("write code");
        Task secondMatchingTask = new Todo("return book");
        TaskList tasks = new TaskList(List.of(firstMatchingTask, otherTask, secondMatchingTask));

        assertEquals(List.of(firstMatchingTask, secondMatchingTask), tasks.find("book"));
    }

    @Test
    void find_absentKeyword_returnsEmptyList() {
        TaskList tasks = new TaskList(List.of(new Todo("read book")));

        assertEquals(List.of(), tasks.find("code"));
    }
}
