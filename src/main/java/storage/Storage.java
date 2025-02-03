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

        File file = null;
        try {
            file = new File(filePath);

            if (!file.exists()) {
                createEmptyFile(file);
            } else {
                System.out.println("Reading from: " + file.getAbsolutePath());

                try (Scanner sc = new Scanner(new FileReader(file))) {
                    while (sc.hasNextLine()) {
                        try { // Parse line and add tasks into taskList
                            String[] parts = sc.nextLine().split("\\|"); // split by "|"

                            for (int i = 0; i < parts.length; i++) {
                                parts[i] = parts[i].trim();
                            }
                            String taskType = parts[0].trim();
                            boolean isDone = parts[1].trim().equals("1");
                            switch (taskType) {
                            case "T":
                                tasks.createTodo(parts[2], isDone);
                                break;
                            case "D":
                                tasks.createDeadline(parts[2], isDone, Parser.parseDateTime(parts[3]));
                                break;
                            case "E":
                                tasks.createEvent(parts[2], isDone, parts[3], parts[4]);
                                break;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Error: missing information for the task");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }
            }
        } catch (NullPointerException e) {
            System.out.println("File not found, creating an empty file");
            createEmptyFile(file);
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
            System.err.println("An error occurred while creating the file: " + e.getMessage());
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
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
