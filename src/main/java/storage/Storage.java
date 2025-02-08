package storage;

import parser.Parser;
import task.Task;
import task.TaskList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import java.util.Scanner;

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

        if (!file.exists()) {
            createEmptyFile(file);
            return tasks;
        }

        try (Scanner sc = new Scanner(new FileReader(file))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                try {
                    Task task = Parser.parseTaskFromLine(line);
                    tasks.add(task);
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid task entry: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Creates an empty file at the specified location.
     * Ensures that the parent directory exists before creating the file.
     *
     * @param file The file to be created.
     */
    private void createEmptyFile(File file) {
        try {
            // Ensure the parent directory exists
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            file.createNewFile(); // Create the empty file
        } catch (IOException e) {
            System.err.println("Error creating new empty file: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of tasks to the file.
     *
     * @param tasks The TaskList to be saved.
     */
    public void saveTasksToFile(TaskList tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            boolean isFileEmpty = new java.io.File(filePath).length() == 0;

            for (Task task : tasks.getAllTasks()) {
                if (!isFileEmpty) {
                    writer.newLine();
                }

                writer.write(task.getFormat());
                isFileEmpty = false; // Only add new line on the first write
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
