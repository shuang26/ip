package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testAddTodo() {
        Todo todo = taskList.addTodo("Read book", false);
        assertEquals(1, taskList.size());
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    void testAddDeadline() {
        LocalDateTime tmp = LocalDateTime.of(2025, 2, 10, 12, 0);
        Deadline deadline = taskList.addDeadline("Submit assignment", false, tmp);
        assertEquals(1, taskList.size());
        assertEquals("[D][ ] Submit assignment (by: 10 Feb 2025, 12:00pm)", deadline.toString());
    }

    @Test
    void testAddEvent() {
        Event event = taskList.addEvent("Project meeting", false, "Monday 10AM", "Monday 12PM");
        assertEquals(1, taskList.size());
        assertEquals("[E][ ] Project meeting (from: Monday 10AM to: Monday 12PM)", event.toString());
    }
}
