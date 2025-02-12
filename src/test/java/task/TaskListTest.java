package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testAddTodo() {
        String response = taskList.addTodo("Read book", false);
        assertEquals(1, taskList.size());
        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("[T][ ] Read book"));
    }

    @Test
    void testAddDeadline() {
        LocalDateTime deadline = LocalDateTime.of(2025, 3, 10, 23, 59);
        String response = taskList.addDeadline("Submit report", false, deadline);

        assertEquals(1, taskList.size());
        assertTrue(response.contains("[D][ ] Submit report (by: 2025-03-10 23:59)"));
    }

    @Test
    void testAddEvent() {
        LocalDateTime from = LocalDateTime.of(2025, 3, 10, 12, 0);
        LocalDateTime to = LocalDateTime.of(2025, 3, 10, 14, 0);
        String response = taskList.addEvent("Team meeting", false, from, to);

        assertEquals(1, taskList.size());
        assertTrue(response.contains("[E][ ] Team meeting (from: 2025-03-10 12:00 to: 2025-03-10 14:00)"));
    }

    @Test
    void testListAllTasks() {
        taskList.addTodo("Buy milk", false);
        taskList.addTodo("Read book", true);

        String expected = "Here are the tasks in your list:\n1.[T][ ] Buy milk\n2.[T][X] Read book";
        assertEquals(expected, taskList.listAllTasks());
    }

    @Test
    void testDeleteTask() {
        taskList.addTodo("Buy milk", false);
        taskList.addTodo("Read book", false);

        String response = taskList.deleteTask(0);
        assertEquals(1, taskList.size());
        assertTrue(response.contains("Noted. I've removed this task:"));
        assertTrue(response.contains("[T][ ] Buy milk"));
    }

    @Test
    void testMarkTask() {
        taskList.addTodo("Exercise", false);
        String response = taskList.markTask(0);

        assertTrue(response.contains("Nice! I've marked this task as done:"));
        assertTrue(response.contains("[T][X] Exercise"));
    }

    @Test
    void testUnmarkTask() {
        taskList.addTodo("Exercise", false);
        taskList.markTask(0);
        String response = taskList.unmarkTask(0);

        assertTrue(response.contains("OK, I've marked this task as not done yet:"));
        assertTrue(response.contains("[T][ ] Exercise"));
    }

    @Test
    void testFindTask() {
        taskList.addTodo("Buy milk", false);
        taskList.addTodo("Buy eggs", false);
        taskList.addTodo("Read book", false);

        String response = taskList.findTask("buy");
        assertTrue(response.contains("Here are the matching tasks:"));
        assertTrue(response.contains("1.[T][ ] Buy milk"));
        assertTrue(response.contains("2.[T][ ] Buy eggs"));
    }

    @Test
    void testFindTaskNoMatch() {
        taskList.addTodo("Buy milk", false);
        String response = taskList.findTask("exercise");
        assertEquals("No matching tasks found.", response);
    }

    @Test
    void testIsEmpty() {
        assertTrue(taskList.isEmpty());
        taskList.addTodo("Exercise", false);
        assertFalse(taskList.isEmpty());
    }

    @Test
    void testGetAllTasks() {
        taskList.addTodo("Read book", false);
        assertEquals(1, taskList.getAllTasks().size());
    }
}
