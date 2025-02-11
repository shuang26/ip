package storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.BeforeEach;

import task.TaskList;

class StorageTest {
    private static final String TEST_FILE_PATH = "test_tasks.txt";
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    void setUp() throws IOException {
        // Ensure test file is empty before each test
        Files.write(Path.of(TEST_FILE_PATH), new byte[0],
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        storage = new Storage(TEST_FILE_PATH);
        taskList = new TaskList();
    }
}
