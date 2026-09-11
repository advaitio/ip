package nudge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests responses created by {@link NudgeResponse}.
 */
class NudgeResponseTest {
    @Test
    void withDetails_nullDetail_assertionFails() {
        List<String> detailsWithNull = new ArrayList<>();
        detailsWithNull.add(null);

        assertThrows(AssertionError.class, () -> {
            NudgeResponse.withDetails("Tasks found:", detailsWithNull);
        });
    }

    @Test
    void withDetails_multipleDetailArguments_preservesDetailsInOrder() {
        NudgeResponse response = NudgeResponse.withDetails(
                "Tasks found:", "1.[T][ ] Read book", "2.[T][ ] Return book");

        assertEquals("Tasks found:", response.getHeader());
        assertEquals(List.of("1.[T][ ] Read book", "2.[T][ ] Return book"),
                response.getDetails());
    }
}
