import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
                                tasks.addTodoTask(parts[2], isDone);
                                break;
                            case "D":
                                tasks.addDeadlineTask(parts[2], isDone, parseDateTime(parts[3]));
                                break;
                            case "E":
                                tasks.addEventTask(parts[2], isDone, parts[3], parts[4]);
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


    public void saveTasksToFile(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            boolean isFileEmpty = new java.io.File(filePath).length() == 0;

            for (Task task : tasks) {
                if (!isFileEmpty) writer.newLine();

                writer.write(task.getFormat());
                isFileEmpty = false; // Only add new line on the first write
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public LocalDateTime parseDateTime(String input) throws DateTimeParseException {
        input = input.replaceAll("[-/]", " "); // Normalize separators
        String[] parts = input.split("\\s+");

        if (parts.length < 4) {
            throw new DateTimeParseException("Invalid format", input, 0);
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d M yyyy");
        LocalDate date = LocalDate.parse(parts[0] + " " + parts[1] + " " + parts[2], dateFormatter);

        // Parse time (supports "HHmm" format)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(parts[3], timeFormatter);

        return LocalDateTime.of(date, time);
    }
}
