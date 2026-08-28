package nudge.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nudge.exception.NudgeException;

/**
 * Tests the parsing and validation performed by {@link Parser}.
 */
class ParserTest {
    @Test
    void parseTaskIndex_validTaskNumber_returnsZeroBasedIndex() throws NudgeException {
        assertEquals(1, Parser.parseTaskIndex("mark 2", "mark"));
    }

    @Test
    void parseTaskIndex_missingTaskNumber_exceptionThrown() {
        NudgeException exception = assertThrows(NudgeException.class,
                () -> Parser.parseTaskIndex("mark", "mark"));

        assertEquals("`mark` needs a task number. Try: mark NUMBER", exception.getMessage());
    }

    @Test
    void parseTaskIndex_nonNumericTaskNumber_exceptionThrown() {
        NudgeException exception = assertThrows(NudgeException.class,
                () -> Parser.parseTaskIndex("mark two", "mark"));

        assertEquals("The task number must be a whole number. Try: mark NUMBER",
                exception.getMessage());
    }
}
