package nudge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests responses created by {@link NudgeResponse}.
 */
class NudgeResponseTest {
    @Test
    void withDetails_multipleDetailArguments_preservesDetailsInOrder() {
        NudgeResponse response = NudgeResponse.withDetails(
                "Tasks found:", "1.[T][ ] Read book", "2.[T][ ] Return book");

        assertEquals("Tasks found:", response.getHeader());
        assertEquals(List.of("1.[T][ ] Read book", "2.[T][ ] Return book"),
                response.getDetails());
    }
}
