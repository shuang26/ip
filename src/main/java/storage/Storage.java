package storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import parser.Parser;
import task.Task;
import task.TaskList;

/**
 * Handles loading and saving tasks to a file for storage.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path of the file used for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the specified file and returns them as a TaskList.
     * If the file does not exist, it creates an empty file.
     *
     * @return A TaskList containing tasks loaded from the file.
     */
    public TaskList loadTasksFromFile() {
        TaskList tasks = new TaskList();
        File file = new File(filePath);

        ensureFileExists(file);
        assert file.exists() : "File should exist after ensureFileExists() call";
        assert file.canRead() : "File should be readable";

        try (Scanner sc = new Scanner(new FileReader(file))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                parseAndAddTask(line, tasks);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves the current list of tasks to the file.
     *
     * @param tasks The TaskList to be saved.
     */
    public void saveTasksToFile(TaskList tasks) {
        assert tasks != null : "TaskList should not be null when saving";
        File file = new File(filePath);
        assert file.exists() : "File should exist before writing";
        assert file.canWrite() : "File should be writable";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writeTasksToFile(tasks, writer);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void writeTasksToFile(TaskList tasks, BufferedWriter writer) throws IOException {
        boolean isFileEmpty = new File(filePath).length() == 0;

        for (Task task : tasks.getAllTasks()) {
            assert task != null : "Task should not be null";
            if (!isFileEmpty) {
                writer.newLine();
            }
            writer.write(task.getFormat());
            isFileEmpty = false;
        }
    }

    /**
     * Ensures that the file exists; if it does not, an empty file is created.
     *
     * @param file The file to check and create if necessary.
     */
    private void ensureFileExists(File file) {
        if (!file.exists()) {
            try {
                createEmptyFile(file);
            } catch (IOException e) {
                System.err.println("Error creating empty file: " + e.getMessage());
                throw new RuntimeException("Unable to create an empty file, program will terminate.");
            }
        }
        assert file.exists() : "File should exist after ensureFileExists()";
    }

    /**
     * Creates an empty file at the specified location.
     * Ensures that the parent directory exists before creating the file.
     *
     * @param file The file to be created.
     */
    private void createEmptyFile(File file) throws IOException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!file.createNewFile()) {
            throw new IOException("Error creating empty file at: " + file.getAbsolutePath());
        }
        assert file.exists() : "File should exist after createNewFile()";
    }

    /**
     * Parses a task from a line and adds it to the task list.
     *
     * @param line  The line to parse.
     * @param tasks The task list to which the task will be added.
     */
    private void parseAndAddTask(String line, TaskList tasks) {
        try {
            Task task = Parser.parseTaskFromLine(line);
            assert task != null : "Parsed task should not be null";
            tasks.addTask(task);
        } catch (IllegalArgumentException e) {
            System.err.println("Skipping invalid task entry: " + e.getMessage());
        }
    }
}
