package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoTest {
    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo("Read a book", false);
    }

    @Test
    void testTodoCreation() {
        assertNotNull(todo);
        assertEquals("Read a book", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    void testMarkDone() {
        todo.markDone();
        assertTrue(todo.isDone());
    }

    @Test
    void testUnmarkDone() {
        todo.markDone(); // Mark done first
        todo.unmarkDone();
        assertFalse(todo.isDone());
    }

    @Test
    void testGetFormat() {
        assertEquals("T | 0 | Read a book", todo.getFormat());
        todo.markDone();
        assertEquals("T | 1 | Read a book", todo.getFormat());
    }

    @Test
    void testToString() {
        assertEquals("[T][ ] Read a book", todo.toString());
        todo.markDone();
        assertEquals("[T][X] Read a book", todo.toString());
    }
}

