package task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEventCreation() {
        LocalDateTime from = LocalDateTime.of(2025, 2, 15, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 2, 15, 12, 0);
        Event event = new Event("Team Meeting", false, from, to);

        assertEquals("Team Meeting", event.getDescription());
        assertFalse(event.isDone());
        assertEquals(from, event.fromDate);
        assertEquals(to, event.toDate);
    }

    @Test
    void testGetFormat() {
        LocalDateTime from = LocalDateTime.of(2025, 2, 15, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 2, 15, 12, 0);
        Event event = new Event("Team Meeting", true, from, to);

        String expectedFormat = "E | 1 | Team Meeting | 15/02/2025 10:00 | 15/02/2025 12:00";
        assertEquals(expectedFormat, event.getFormat());
    }

    @Test
    void testToString() {
        LocalDateTime from = LocalDateTime.of(2025, 2, 15, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 2, 15, 12, 0);
        Event event = new Event("Team Meeting", false, from, to);

        String expectedString = "[E][ ] Team Meeting (from: 2025-02-15 10:00 to: 2025-02-15 12:00)";
        assertEquals(expectedString, event.toString());
    }
}
