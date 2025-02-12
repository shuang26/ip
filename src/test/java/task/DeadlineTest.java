package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class DeadlineTest {

    @Test
    void testDeadlineCreation() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 10, 23, 59);
        Deadline deadlineTask = new Deadline("Submit Assignment", false, deadline);

        assertEquals("Submit Assignment", deadlineTask.getDescription());
        assertFalse(deadlineTask.isDone());
        assertEquals(deadline, deadlineTask.deadline);
    }

    @Test
    void testGetFormat() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 10, 23, 59);
        Deadline deadlineTask = new Deadline("Submit Assignment", true, deadline);

        String expectedFormat = "D | 1 | Submit Assignment | 10/03/2025 23:59";
        assertEquals(expectedFormat, deadlineTask.getFormat());
    }

    @Test
    void testToString() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 10, 23, 59);
        Deadline deadlineTask = new Deadline("Submit Assignment", false, deadline);

        String expectedString = "[D][ ] Submit Assignment (by: 2025-03-10 23:59)";
        assertEquals(expectedString, deadlineTask.toString());
    }
}
