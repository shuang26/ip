package storage;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.TaskList;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.List;

class StorageTest {
    private static final String TEST_FILE_PATH = "test_tasks.txt";
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    void setUp() throws IOException {
        // Ensure test file is empty before each test
        Files.write(Path.of(TEST_FILE_PATH), new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        storage = new Storage(TEST_FILE_PATH);
        taskList = new TaskList();
    }

    @Test
    void testLoadTasksFromFile_EmptyFile() {
        TaskList loadedTasks = storage.loadTasksFromFile();
        assertTrue(loadedTasks.isEmpty());
    }

    @Test
    void testLoadTasksFromFile_WithTasks() throws IOException {
        List<String> taskLines = List.of(
                "T | 0 | Read book",
                "D | 1 | Submit report | 10/2/2025 1800",
                "E | 0 | Team meeting | Monday 10AM | Monday 12PM"
        );
        Files.write(Path.of(TEST_FILE_PATH), taskLines);

        TaskList loadedTasks = storage.loadTasksFromFile();
        assertEquals(3, loadedTasks.size());
    }

    @Test
    void testSaveTasksToFile() {
        taskList.addTodoTask("Exercise", false);
        storage.saveTasksToFile(taskList);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        assertFalse(file.length() == 0);
    }
}
