package task;

import parser.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Manages a list of tasks and provides methods to handle user input.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    private final String line = "____________________________________________________________\n";

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a new task to the task list based on the provided type and input.
     *
     * @param taskType The type of task (todo, deadline, event).
     * @param isDone   The completion status of the task.
     * @param input    The user input describing the task.
     */
    public void addNewTask(String taskType, boolean isDone, String input) {
        Task task;

        switch (taskType) {
        case "todo":
            task = addTodo(input, isDone);
            break;
        case "deadline":
            try {
                String[] parts = input.split("/by", 2);
                LocalDateTime dateTime = Parser.parseDateTime(parts[1].trim());
                task = addDeadline(parts[0].trim(), isDone, dateTime);
            } catch (ArrayIndexOutOfBoundsException e) {
                printWithLine("Error: Please provide task description or a deadline for task.Deadline task.");
                return;
            } catch (DateTimeParseException e) {
                printWithLine("Error: Please provide a valid deadline for task.Deadline task.");
                return;
            }
            break;
        case "event":
            // TODO: Possible empty description / empty from date / empty to date
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to");

            if (fromIndex == -1 || toIndex == -1) {
                printWithLine("Error: Please provide /from data or /to date for task.Event task");
                return;
            }
            String description = input.substring(0, fromIndex).trim();
            String fromDate = input.substring(fromIndex + 6, toIndex).trim(); // +6 to skip "/from "
            String toDate = input.substring(toIndex + 4).trim(); // +4 to skip "/to "

            task = addEvent(description, isDone, fromDate, toDate);
            break;
        default:
            printWithLine("Error: Unknown task type. Please provide a valid task type (todo, deadline, event).");
            return;
        }

        // Print after adding task
        System.out.print(line + "Got it. I've added this task:\n");
        System.out.println(task);
        System.out.print("Now you have " + tasks.size() + " tasks in the list.\n" + line);
    }

    public Todo addTodo(String description, boolean isDone) {
        Todo tmp = new Todo(description, isDone);
        tasks.add(tmp);
        return tmp;
    }

    public Deadline addDeadline(String description, boolean isDone, LocalDateTime dateTime) {
        Deadline tmp = new Deadline(description, isDone, dateTime);
        tasks.add(tmp);
        return tmp;
    }

    public Event addEvent(String description, boolean isDone, String fromDate, String toDate) {
        Event tmp = new Event(description, isDone, fromDate, toDate);
        tasks.add(tmp);
        return tmp;
    }

    /**
     * Prints out all the tasks in the task list.
     */
    public void listAllTasks() {
        System.out.print(line);

        if (tasks.isEmpty()) {
            System.out.println("There is no task in your list. Please add some to see the list.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }

        System.out.print(line);
    }

    /**
     * Deletes a task from the list by index.
     *
     * @param index The index of the task to delete.
     */
    public void deleteTask(int index) {
        Task tmp = tasks.remove(index);
        System.out.print(line + "Noted. I've removed this task:\n"
                + tmp + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n" + line);
    }

    public void markTask(int index) {
        Task task = tasks.get(index);
        task.markDone();
        System.out.print(line + "Nice! I've marked this task as done:\n"
            + task + "\n" + line);
    }

    public void unmarkTask(int index) {
        Task task = tasks.get(index);
        task.unmarkDone();
        System.out.print(line + "OK, I've marked this task as not done yet:\n"
            + task + "\n" + line);
    }

    public void findTask(String description) {
        List<String> matches = IntStream.range(0, tasks.size())
            .mapToObj(i -> tasks.get(i))
            .filter(t -> t.getDescription().toLowerCase().contains(description.toLowerCase()))
            .map(t -> (tasks.indexOf(t) + 1) + "." + t)
            .toList();

        printWithLine(matches.isEmpty()
            ? "No matching tasks found."
            : "Here are the matching tasks:\n" + String.join("\n", matches));
    }

    private void printWithLine(String message) {
        System.out.print(line + message + "\n" + line);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns a copy of all tasks in the task list.
     *
     * @return A new {@code ArrayList} containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(this.tasks);
    }
}
