import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    private final ArrayList<Task> taskList;
    private final String line = "____________________________________________________________\n";
    private final String FILE_PATH = "./data/cow.txt";

    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public TaskManager(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void start() {
        // Load tasks from saved file
        this.loadTaskFromFile();
    }

    public boolean handleInput(String input) {
        String[] parts = input.split(" ", 2); // Split into at most 2 parts for now
        String request = parts[0].trim().toLowerCase();

        try {
            switch (request) {
            case "bye":
                printWithLine("Bye. Hope to see you again soon!");
                writeToFile();
                return true;
            case "list":
                this.listAllTask(); // List can even if there is extra words behind
                break;
            case "unmark": case "mark":
                if (parts.length != 2) {
                    printWithLine("Please provide an index for mark / unmark request.");
                    break;
                }
                this.handleMarkUnmark(request, parts[1]);
                break;
            case "todo": case "deadline": case "event":
                this.addNewTask(request, false, parts[1]);
                break;
            case "delete":
                this.deleteTask(parts[1]);
                break;
            default:
                printWithLine("Sorry, but I don't know what that means.\nPlease try again.");
            }
        } catch (NumberFormatException e) {
            printWithLine("Please provide a valid index. (For mark / unmark / delete requests)");
        } catch (ArrayIndexOutOfBoundsException e) {
            printWithLine("Error: Missing information. Please provide a proper request.");
        }
        return false;
    }

    public void addNewTask(String taskType, boolean isDone, String input) {
        Task task;

        switch (taskType) {
            case "todo":
                task = addTodoTask(input, isDone);
                break;
            case "deadline":
                try {
                    String[] parts = input.split("/by", 2);
                    LocalDateTime dateTime = parseDateTime(parts[1].trim());
                    task = addDeadlineTask(parts[0].trim(), isDone, dateTime);
                } catch (ArrayIndexOutOfBoundsException e) {
                    printWithLine("Error: Please provide task description or a deadline for Deadline task.");
                    return;
                } catch (DateTimeParseException e) {
                    printWithLine("Error: Please provide a valid deadline for Deadline task.");
                    return;
                }
                break;
            case "event":
                // TODO: Possible empty description / empty from date / empty to date
                int fromIndex = input.indexOf("/from");
                int toIndex = input.indexOf("/to");

                if (fromIndex == -1 || toIndex == -1) {
                    printWithLine("Error: Please provide /from data or /to date for Event task");
                    return;
                }
                String description = input.substring(0, fromIndex).trim();
                String fromDate = input.substring(fromIndex + 6, toIndex).trim(); // +6 to skip "/from "
                String toDate = input.substring(toIndex + 4).trim(); // +4 to skip "/to "

                task = addEventTask(description, isDone, fromDate, toDate);
                break;
            default:
                printWithLine("Error: Unknown task type. Please provide a valid task type (todo, deadline, event).");
                return;
        }

        // Print after adding task
        System.out.print(line + "Got it. I've added this task:\n");
        System.out.println(task);
        System.out.print("Now you have " + taskList.size() + " tasks in the list.\n" + line);
    }

    private Todo addTodoTask(String description, boolean isDone) {
        Todo tmp = new Todo(description, isDone);
        taskList.add(tmp);
        return tmp;
    }

    private Deadline addDeadlineTask(String description, boolean isDone, LocalDateTime dateTime) {
        Deadline tmp = new Deadline(description, isDone, dateTime);
        taskList.add(tmp);
        return tmp;
    }

    private Event addEventTask(String description, boolean isDone, String fromDate, String toDate) {
        Event tmp = new Event(description, isDone, fromDate, toDate);
        taskList.add(tmp);
        return tmp;
    }

    public void listAllTask() {
        System.out.print(line);
        if (taskList.isEmpty()) {
            System.out.println("There is no task in your list. Please add some to see the list.");
            System.out.println(line);
            return;
        }

        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < taskList.size(); i++) {
            Task tmp = taskList.get(i);
            System.out.println((i + 1) + "." + tmp);
        }
        System.out.print(line);
    }

    public void deleteTask(String index) {
        int i = Integer.parseInt(index) - 1;

        if (taskList.isEmpty()) {
            printWithLine("Error: Cannot delete from an empty list.");
            return;
        } else if (i < 0 || i >= taskList.size()) {
            printWithLine("Error: Index must be between 1 and " + taskList.size() + " for delete request.");
            return;
        }

        System.out.print(line + "Noted. I've removed this task:\n");
        Task tmp = taskList.remove(i);
        System.out.print(tmp + "\n");
        System.out.print("Now you have " + taskList.size() + " tasks in the list.\n" + line);
    }

    public void handleMarkUnmark(String request, String index) throws NumberFormatException {
        int i = Integer.parseInt(index) - 1;

        if (taskList.isEmpty()) {
            printWithLine("Error: Cannot " + request + " an empty list.");
            return;
        } else if (i < 0 || i >= taskList.size()) {
            printWithLine("Error: index is out of bounds for " + request + " request.");
            return;
        }

        if (request.equals("mark")) this.markTask(i);
        else this.unmarkTask(i);
    }

    private void markTask(int index) {
        System.out.print(line + "Nice! I've marked this task as done:\n");
        Task tmp = taskList.get(index);
        tmp.markDone();
        System.out.print(tmp + "\n" + line);
    }

    private void unmarkTask(int index) {
        System.out.print(line + "OK, I've marked this task as not done yet:\n");
        Task tmp = taskList.get(index);
        tmp.unmarkDone();
        System.out.print(tmp + "\n" + line);
    }

    public void loadTaskFromFile() {
        File file = new File(FILE_PATH);

        if (file.exists()) {
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
                            this.addTodoTask(parts[2], isDone);
                            break;
                        case "D":
                            this.addDeadlineTask(parts[2], isDone, parseDateTime(parts[3]));
                            break;
                        case "E":
                            this.addEventTask(parts[2], isDone, parts[3], parts[4]);
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error: missing information for the task");
                    }
                }
            } catch (IOException e) {
                System.err.println("An error occurred while reading the file");
                // delete and create new empty file?
            }
        } else { // file does not exist
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
    }

    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            boolean isFileEmpty = new java.io.File(FILE_PATH).length() == 0;

            for (Task task : taskList) {
                if (!isFileEmpty) writer.newLine();

                writer.write(task.getFormat());
                isFileEmpty = false; // Only add new line on the first write
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }

    private void printWithLine(String message) {
        System.out.print(line + message + "\n" + line);
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
