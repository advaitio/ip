package nudge.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nudge.exception.NudgeException;

/**
 * Tests the parsing and validation performed by {@link Parser}.
 */
class ParserTest {
    @Test
    void parseCommandType_sortCommandWithSurroundingWhitespace_returnsSort() {
        assertEquals(CommandType.SORT, Parser.parseCommandType("  sort  "));
    }

    @Test
    void parseCommandType_sortWithoutCommandBoundary_returnsUnknown() {
        assertEquals(CommandType.UNKNOWN, Parser.parseCommandType("sortdeadline"));
    }

    @Test
    void parseCommandType_uppercaseSort_returnsUnknown() {
        assertEquals(CommandType.UNKNOWN, Parser.parseCommandType("Sort"));
    }

    @Test
    void validateSortCommand_withoutArguments_doesNotThrow() {
        assertDoesNotThrow(() -> Parser.validateSortCommand("  sort  "));
    }

    @Test
    void validateSortCommand_withArguments_exceptionThrown() {
        NudgeException exception = assertThrows(
                NudgeException.class, () -> Parser.validateSortCommand("sort deadline"));

        assertEquals("`sort` does not take any arguments. Try: sort", exception.getMessage());
    }

    @Test
    void parseTaskIndex_validTaskNumber_returnsZeroBasedIndex() throws NudgeException {
        assertEquals(1, Parser.parseTaskIndex("mark 2", "mark"));
    }

    @Test
    void parseTaskIndex_missingTaskNumber_exceptionThrown() {
        NudgeException exception = assertThrows(NudgeException.class, () -> Parser.parseTaskIndex("mark", "mark"));

        assertEquals("`mark` needs a task number. Try: mark NUMBER", exception.getMessage());
    }

    @Test
    void parseTaskIndex_nonNumericTaskNumber_exceptionThrown() {
        NudgeException exception = assertThrows(
                NudgeException.class, () -> Parser.parseTaskIndex("mark two", "mark"));

        assertEquals("The task number must be a whole number. Try: mark NUMBER",
                exception.getMessage());
    }

    @Test
    void parseFindKeyword_validKeyword_returnsKeyword() throws NudgeException {
        assertEquals("book", Parser.parseFindKeyword("find book"));
    }

    @Test
    void parseFindKeyword_missingKeyword_exceptionThrown() {
        NudgeException exception = assertThrows(NudgeException.class, () -> Parser.parseFindKeyword("find"));

        assertEquals("`find` needs a keyword. Try: find KEYWORD", exception.getMessage());
    }
}
